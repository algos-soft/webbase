package it.algos.webbase.web;

import it.algos.webbase.domain.ruolo.Ruolo;
import it.algos.webbase.domain.ruolo.TipoRuolo;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.query.AQuery;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.reflect.Method;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Executed on container startup
 * Setup non-UI logic here
 * <p>
 * Classe eseguita solo quando l'applicazione viene caricata/parte nel server (Tomcat od altri) <br>
 * Eseguita quindi ad ogni avvio/riavvio del server e NON ad ogni sessione <br>
 * È OBBLIGATORIO creare la sottoclasse per regolare il valore della persistence unit che crea l'EntityManager <br>
 * È OBBLIGATORIO aggiungere questa classe nei listeners del file web.WEB-INF.web.xml <br>
 */
public abstract class ABootStrap implements ServletContextListener {

    private final static Logger logger = Logger.getLogger(ABootStrap.class.getName());
    private static ServletContext context;
    protected String ruoloUtente = TipoRuolo.user.toString();
    protected String ruoloAdmin = TipoRuolo.admin.toString();
    protected String ruoloDeveloper = TipoRuolo.developer.toString();
    private DBPingRunnable dbPingRunnable;
    private Thread dbPingThread;


    public static ServletContext getServletContext() {
        return context;
    }// end of method

    /**
     * Executed on container startup
     * Setup non-UI logic here
     * <p>
     * Viene normalmente sovrascritta dalla sottoclasse:
     *  - per registrare nella xxxApp, il servlet context non appena è disponibile
     *  - per regolare eventualmente alcuni flag dell'applicazione <br>
     *  - per lanciare eventualmente gli schedulers in background <br>
     *  - per regolare eventualmente una versione demo <br>
     *  - per controllare eventualmente l'esistenza di utenti abilitati all'accesso <br>
     * Deve (DEVE) richiamare anche il metodo della superclasse (questo)
     * prima (PRIMA) delle regolazioni specifiche della sottoclasse <br>
     */
    public void contextInitialized(ServletContextEvent contextEvent) {
        setPersistenceEntity();

        /**
         * Create a dummy new EntityManager to trigger initialization of the EntityManagerFactory <br>
         * EntityManagerFactory is lazily initialized and until this is done, <br>
         * the static metamodel is not available. <br>
         */
        EM.createEntityManager();

        /**
         * Starts a thread which periodically queries the database <br>
         * to avoid connection timeouts (on MySQL wait-timeout is 8 hrs) <br>
         */
        dbPingRunnable = new DBPingRunnable();
        dbPingThread = new Thread(dbPingRunnable);
        dbPingThread.start();
        logger.log(Level.INFO, "database ping thread started");

        /**
         * Eventuale uso della security <br>
         */
        if (AlgosApp.USE_SECURITY) {
            regolaSecurity();
        }// fine del blocco if

        context = contextEvent.getServletContext();
        System.out.println("Context Created");
    }// end of method

    /**
     * Regola la security
     * <p>
     * Controllo esistenza dei ruoli standard <br>
     * Creazione al volo di alcuni utenti <br>
     */
    protected void regolaSecurity() {
//        SecurityBootStrap.bootStrapRuoli();
        creaUtenti();
    }// end of method

    /**
     * Executed on container shutdown
     * <p>
     * Clean stuff here <br>
     * Può essere sovrascritta dalla sottoclasse <br>
     * Deve (DEVE) richiamare anche il metodo della superclasse (questo) <br>
     */
    public void contextDestroyed(ServletContextEvent contextEvent) {
        // ferma il thread di database ping
        if (dbPingRunnable != null) {

            // stop the runnable
            dbPingRunnable.stop();

            // wait for thread end
            if (dbPingThread != null) {
                while (dbPingThread.isAlive()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }// end of try/catch
                }// fine del ciclo while
                logger.log(Level.INFO, "database ping thread stopped");
            }// fine del blocco if
        }// fine del blocco if

        // shut down MySQL AbandonedConnectionCleanupThread (if found)
        try {
            Class<?> cls = Class.forName("com.mysql.jdbc.AbandonedConnectionCleanupThread");
            Method mth = (cls == null ? null : cls.getMethod("shutdown"));
            if (mth != null) {
                logger.log(Level.INFO, "MySQL connection cleanup thread shutdown");
                mth.invoke(null);
                logger.log(Level.INFO, "MySQL connection cleanup thread shutdown successful");
            }// fine del blocco if
        } catch (Throwable thr) {
            logger.log(Level.SEVERE, "Failed to shutdown SQL connection cleanup thread: " + thr.getMessage());
            thr.printStackTrace();
        }// end of try/catch

        // deregister JDBC drivers
        // This manually deregisters JDBC driver, which prevents Tomcat 7
        // from complaining about memory leaks wrto this class
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.log(Level.INFO, String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                logger.log(Level.SEVERE, String.format("Error deregistering driver %s", driver), e);
            }// end of try/catch
        }// fine del ciclo while

        System.out.println("Context Destroyed");
    }// end of method

    /**
     * Crea utenti con ruolo connesso
     * <p>
     * Può essere sovrascritta dalla sottoclasse <br>
     */
    protected void creaUtenti() {
    }// end of method

    /**
     * Crea un utente con ruolo connesso <br>
     * Invocato dalla sottoclasse <br>
     */
    protected void creaUtente(String nickName, String password, String nomeRuolo) {
//        SecurityBootStrap.creaUtente(nickName, password, nomeRuolo);
    }// end of method

    /**
     * Regola il valore della persistence unit per crearae l'EntityManager <br>
     * DEVE essere sovrascritto (obbligatorio) nella sottoclasse del progetto <br>
     */
    public abstract void setPersistenceEntity();

    /**
     * Runnable to timely ping the database to avoid connection timeouts <br>
     */
    private class DBPingRunnable implements Runnable {
        private static final int INTERVAL_SECONDS = 600;
        private boolean stop = false;

        @Override
        public void run() {

            while (!stop) {
                // simple query here
                AQuery.getCount(Ruolo.class);

                // sleep time
                try {
                    for (int i = 0; i < 1000; i++) {
                        Thread.sleep(INTERVAL_SECONDS);
                        if (stop) {
                            break;
                        }// end of if cycle
                    }// end of for cycle
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }// end of try/catch

            }// fine del ciclo while
        }// end of method

        public void stop() {
            stop = true;
        }// end of method

    }// end of innerr class

}// end of abstract class
