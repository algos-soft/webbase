package it.algos.web;

import java.lang.reflect.Method;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Logger;

import it.algos.domain.ruolo.Ruolo;
import it.algos.domain.ruolo.TipoRuolo;
import it.algos.domain.utente.Utente;
import it.algos.domain.utenteruolo.UtenteRuolo;
import it.algos.web.entity.EM;
import it.algos.web.query.AQuery;

import java.util.logging.Level;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Executed on container startup
 * <p>
 * Setup non-UI logic here <br>
 * Classe eseguita solo quando l'applicazione viene caricata/parte nel server (Tomcat od altri) <br>
 * Eseguita quindi ad ogni avvio/riavvio del server e NON ad ogni sessione <br>
 * Viene normalmente creata la sottoclasse <br>
 */
public class BootStrap implements ServletContextListener {

	private static ServletContext context;
	private final static Logger logger = Logger.getLogger(BootStrap.class.getName());
	private DBPingRunnable dbPingRunnable;
	private Thread dbPingThread;
	protected String ruoloUtente = TipoRuolo.user.toString();
	protected String ruoloAdmin = TipoRuolo.admin.toString();
	protected String ruoloDeveloper = TipoRuolo.developer.toString();

	/**
	 * Executed on container startup
	 * <p>
	 * Setup non-UI logic here <br>
	 * Viene normalmente sovrascritta dalla sottoclasse per regolare alcuni flag dell'applicazione <br>
	 * Deve (DEVE) richiamare anche il metodo della superclasse (questo) <br>
	 */
	public void contextInitialized(ServletContextEvent contextEvent) {

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
		 * Controllo esistenza dei ruoli standard <br>
		 */
		if (Application.USE_SECURITY) {
			SecurityBootStrap.bootStrapRuoli();
		}// end of if cycle

		/**
		 * Creazione al volo di alcuni utenti <br>
		 */
		if (Application.USE_SECURITY) {
			creaUtenti();
		}// end of if cycle

		context = contextEvent.getServletContext();
		System.out.println("Context Created");
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
				}// end of while cycle
				logger.log(Level.INFO, "database ping thread stopped");
			}// end of if cycle
		}// end of if cycle

		// shut down MySQL AbandonedConnectionCleanupThread (if found)
		try {
			Class<?> cls = Class.forName("com.mysql.jdbc.AbandonedConnectionCleanupThread");
			Method mth = (cls == null ? null : cls.getMethod("shutdown"));
			if (mth != null) {
				logger.log(Level.INFO, "MySQL connection cleanup thread shutdown");
				mth.invoke(null);
				logger.log(Level.INFO, "MySQL connection cleanup thread shutdown successful");
			}// end of if cycle
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
		}// end of while cycle

		System.out.println("Context Destroyed");
	}// end of method

	public static ServletContext getServletContext() {
		return context;
	}// end of method

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

			}// end of while cycle

		}// end of method

		public void stop() {
			stop = true;
		}// end of method

	}// end of innerr class

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
		SecurityBootStrap.creaUtente(nickName, password, nomeRuolo);
	}// end of method

}// end of class
