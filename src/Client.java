import pl.jrj.game.IGameRemote;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.persistence.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by Konrad on 04.05.2016.
 */
public class Client {

    private static String carName;
    private static Date questionedDate;

    //@EJB(mappedName = "java:global/ejb-project/GameManager")
    @EJB
    private static IGameRemote gameRemoteBean;

    public static void main(String[] args) {
        float answer = 0;
        String fileName;

        try {
            Client client = new Client();

            InitialContext ctx = new InitialContext();
            System.out.println("Ustawiony InitialContext!");

            gameRemoteBean = (IGameRemote) ctx.lookup("java:global/" +
                    "ejb-project/GameRemote!pl.jrj.game.IGameRemote");

            System.out.println("Mamy beana!");
            fileName = args[0];
            System.out.println(fileName);
            client.getDateAndModelNameFromFile(fileName);

            gameRemoteBean.register(6, "98123");

            EntityManagerFactory emF = Persistence
                    .createEntityManagerFactory("persistence98123");
            EntityManager em = emF.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            String baseQueryString = "" +
                    "select count(customer) from TbCustomerEntity customer " +
                    "JOIN TbInsuranceEntity insurance ON insurance." +
                    " TbModelEntity model " +
                    "WHERE model.model =:carName ";

            String clientsWithInsurance = baseQueryString +
                    "AND insurance.dateFrom >=:questionedDate " +
                    "AND insurance.dateTo <=:questionedDate ";

            String clientsWithoutInsurance = baseQueryString +
                    "AND insurance.dateFrom >=:questionedDate " +
                    "OR  insurance.dateTo <=:questionedDate";

            Query carsWithoutInsurance = em.createQuery(clientsWithoutInsurance);
            carsWithoutInsurance.setParameter("carName", carName);
            carsWithoutInsurance.setParameter("questionedDate", questionedDate);

            Query carsWithInsurance = em.createQuery(clientsWithInsurance);
            carsWithInsurance.setParameter("carName", carName);
            carsWithInsurance.setParameter("questionedDate", questionedDate);

            long withoutInsuranceCount =
                    (Long) carsWithoutInsurance.getSingleResult();
            long withInsuranceCount =
                    (Long) carsWithInsurance.getSingleResult();


            answer = withoutInsuranceCount / withInsuranceCount + withoutInsuranceCount;

        } catch (Exception exception) {
            answer = 1f;
            exception.printStackTrace();
        } finally {
            System.out.format(Locale.US, "Wynik : %.1f", answer);
        }

    }

    private Client getDateAndModelNameFromFile(String fileName)
            throws FileNotFoundException, ParseException {

        Scanner scanner = new Scanner(new File(fileName));
        carName = scanner.nextLine();

        Locale systemLocale = new Locale(System.getProperty("user.language"));

        String s = DateFormat
                .getDateInstance(DateFormat.MEDIUM, systemLocale)
                .format(new Date());

        questionedDate = DateFormat
                .getDateInstance(DateFormat.MEDIUM, systemLocale)
                .parse(s);

        System.out.format("data w tym: [%s] formacie to: %s\n\n", systemLocale, questionedDate);

        return this;
    }


}
