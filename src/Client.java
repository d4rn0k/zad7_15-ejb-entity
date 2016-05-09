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
            long clientsWithoutInsuranceCount;
            EntityManagerFactory entityManagerFactory;
            EntityManager entityManager;

            Client client = new Client();
            client.getParamsFromFile(args[0]);

            gameRemoteBean = (IGameRemote) new InitialContext()
                    .lookup("java:global/" +
                            "ejb-project/GameRemote!pl.jrj.game.IGameRemote");
            gameRemoteBean.register(7, "98123");

            entityManagerFactory = Persistence
                    .createEntityManagerFactory("persistence98123");
            entityManager = entityManagerFactory.createEntityManager();

            allClientsCount = client.getAllClientsCount(entityManager);
            clientsWithoutInsuranceCount = client
                    .getClientsWithoutCount(entityManager);

            answer = (float) clientsWithoutInsuranceCount /
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
     * Returns clients without insurance from Database count.
     *
     * @param em EntityManager object to perform query.
     * @return clients without insurance count.
     * @throws Exception when fail casting or fail retrieve data.
     */
    private long getClientsWithoutCount(EntityManager em) throws Exception {
        Query clientsWithoutInsurance = em.createQuery("" +
                "SELECT COUNT(c) FROM TbCustomerEntity c " +
                "JOIN TbInsuranceEntity i ON i.customerId=c.id " +
                "JOIN TbModelEntity m ON i.modelId=m.id " +
                "WHERE m.model =:carName " +
                "AND NOT :questionedDate BETWEEN i.dateFrom AND i.dateTo");

        clientsWithoutInsurance.setParameter("carName", carName);
        clientsWithoutInsurance.setParameter("questionedDate",
                questionedDate);

        return (long) clientsWithoutInsurance.getSingleResult();
    }

    /**
     * Opens file with specified filename and parse car model
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

        questionedDate = DateFormat
                .getDateInstance(DateFormat.DEFAULT, Locale.getDefault())
                .parse(dateStringFromFile);

        return this;
    }
}
