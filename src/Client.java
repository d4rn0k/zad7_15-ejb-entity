import pl.jrj.game.IGameRemote;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 * Client application Class.
 *
 * @author Konrad Szwedo
 * @version 0.7L
 */
public class Client {

    /**
     * Car name for query.
     */
    private static String carName;

    /**
     * Date for query.
     */
    private static Date questionedDate;

    /**
     * Game Remote Bean.
     */
    @EJB
    private static IGameRemote gameRemoteBean;

    /**
     * Main method for Client app.
     *
     * @param args program arguments array.
     */
    public static void main(String[] args) {

        float answer = 10f;

        try {
            long allClientsCount;
            long clientsWithInsuranceCount;
            EntityManagerFactory entityManagerFactory;
            EntityManager entityManager;

            Client client = new Client();
            client.getParamsFromFile(args[0]);

            gameRemoteBean = (IGameRemote) new InitialContext()
                    .lookup("java:global/" +
                            "ejb-project/GameManager!pl.jrj.game.IGameRemote");

            gameRemoteBean.register(7, "98123");

            entityManagerFactory = Persistence
                    .createEntityManagerFactory("persistence98123");
            entityManager = entityManagerFactory.createEntityManager();

            allClientsCount = client.getAllClientsCount(entityManager);
            clientsWithInsuranceCount = client
                    .getClientsWithInsuranceCount(entityManager);

            answer = (float) (allClientsCount - clientsWithInsuranceCount) /
                    (float) allClientsCount * 100;
        } catch (Exception exception) {
            answer = 20f;
        } finally {
            System.out.format(Locale.US, "Wynik : %.1f%%\n", answer);
        }
    }

    /**
     * Returns all clients from Database count.
     *
     * @param em EntityManager object to perform query.
     * @return long clients count.
     * @throws Exception when fail casting or fail retrieve data.
     */
    private long getAllClientsCount(EntityManager em) throws Exception {
        Query allClients = em.createQuery("" +
                "SELECT COUNT(c) " +
                "FROM TbCustomerEntity c");

        return (long) allClients.getSingleResult();
    }

    /**
     * Returns clients with insurance on specific car, with requested date
     * from Database count.
     *
     * @param em EntityManager object to perform query.
     * @return clients with insurance count.
     * @throws Exception when fail casting or fail retrieve data.
     */
    private long getClientsWithInsuranceCount(EntityManager em) throws
            Exception {
        Query clientsWithInsurance = em.createQuery("" +
                "SELECT COUNT( DISTINCT c.id ) " +
                "FROM TbCustomerEntity c " +
                "JOIN TbInsuranceEntity i ON i.customerId=c.id " +
                "JOIN TbModelEntity m ON i.modelId=m.id " +
                "WHERE m.model =:carName " +
                "AND :questionedDate BETWEEN i.dateFrom AND i.dateTo");

        clientsWithInsurance.setParameter("carName", carName);
        clientsWithInsurance.setParameter("questionedDate",
                questionedDate);

        return (long) clientsWithInsurance.getSingleResult();
    }

    /**
     * Open file with specified filename and parse car model
     * and questioned date.
     *
     * @param fileName file to open.
     * @return this object reference.
     * @throws FileNotFoundException When file not found.
     * @throws ParseException        When parse fail.
     */
    private Client getParamsFromFile(String fileName)
            throws FileNotFoundException, ParseException {

        String dateStringFromFile;
        Scanner scanner = new Scanner(new File(fileName));

        carName = scanner.nextLine();
        dateStringFromFile = scanner.nextLine();

        DateFormat dfLocal = DateFormat
                .getDateInstance(DateFormat.DEFAULT, Locale.getDefault());

        DateFormat df1 = new SimpleDateFormat("dd/MM/YYYY");
        DateFormat df2 = new SimpleDateFormat("MM/dd/YYYY");

        questionedDate = tryParseDate(dfLocal, dateStringFromFile);

        if (questionedDate == null) {
            questionedDate = tryParseDate(df1, dateStringFromFile);
        }

        if (questionedDate == null) {
            questionedDate = tryParseDate(df2, dateStringFromFile);
        }

        return this;
    }

    /**
     * Tries parse date with input date format, otherwise returns null.
     *
     * @param df                 DateFormat object with date format pattern
     * @param dateStringFromFile String with date
     * @return Date object
     */
    private Date tryParseDate(DateFormat df, String dateStringFromFile) {
        Date returnDate;
        try {
            returnDate = df.parse(dateStringFromFile);
        } catch (Exception exc) {
            returnDate = null;
        }
        return returnDate;
    }
}
