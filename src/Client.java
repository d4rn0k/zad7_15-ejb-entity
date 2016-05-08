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
//import javax.persistence.*;

/**
 * Created by Konrad on 04.05.2016.
 */
public class Client {

    private static String carName;
    private static Date questionedDate;

    @EJB
    private static IGameRemote gameRemoteBean;

    public static void main(String[] args) {
        float answer = 10f;

        try {
            Client client = new Client();
            client.getParamsFromFile(args[0]);

            InitialContext ctx = new InitialContext();
            //Działa, zakomentowane bo wolno szuka beana lolololo
//            gameRemoteBean = (IGameRemote) ctx.lookup("java:global/" +
//                    "ejb-project/GameRemote!pl.jrj.game.IGameRemote");
//
//            gameRemoteBean.register(6, "98123");

            EntityManagerFactory emF = Persistence
                    .createEntityManagerFactory("persistence98123");
            EntityManager em = emF.createEntityManager();

            Query allClients = em.createQuery("" +
                    "SELECT count(c) " +
                    "FROM TbCustomerEntity c");

            Query clientsWithoutInsurance = em.createQuery("" +
                    "SELECT COUNT(c) FROM TbCustomerEntity c " +
                    "JOIN TbInsuranceEntity i ON i.customerId=c.id " +
                    "JOIN TbModelEntity m ON i.modelId=m.id " +
                    "WHERE m.model =:carName " +
                    "AND :questionedDate NOT BETWEEN i.dateFrom AND i.dateTo");

            clientsWithoutInsurance.setParameter("carName", carName);
            clientsWithoutInsurance.setParameter("questionedDate", questionedDate);

            // count(*) w query zwraca liczbe typu long TAKIE CZARY
            // http://stackoverflow.com/a/3574441/2141854
            long allClientsCount =
                    (long) allClients.getSingleResult();

            long clientsWithoutInsuranceCount =
                    (long) clientsWithoutInsurance.getSingleResult();

            answer = (float) clientsWithoutInsuranceCount /
                    (float) allClientsCount * 100;

        } catch (Exception exception) {
            answer = 20f;
            exception.printStackTrace();
        } finally {
            System.out.format(Locale.US, "Wynik : %.1f%%\n", answer);
        }
    }

    private Client getParamsFromFile(String fileName)
            throws FileNotFoundException, ParseException {

        Scanner scanner = new Scanner(new File(fileName));

        carName = scanner.nextLine();
        String dateStringFromFile = scanner.nextLine();

        Locale systemLocale = new Locale(System.getProperty("user.language"));

        questionedDate = DateFormat
                .getDateInstance(DateFormat.DEFAULT, systemLocale)
                .parse(dateStringFromFile);

        DateFormat fmt = DateFormat.getDateInstance(
                DateFormat.DEFAULT,
                systemLocale);

        if (fmt instanceof SimpleDateFormat) {
            SimpleDateFormat sfmt = (SimpleDateFormat) fmt;
            String pattern = sfmt.toPattern();
            String localizedPattern = sfmt.toLocalizedPattern();
            System.out.println(pattern);
            System.out.println(localizedPattern);
        }

        System.err.format("data w tym: [%s] formacie to: %s\n\n", systemLocale,
                questionedDate);

        return this;
    }
}
