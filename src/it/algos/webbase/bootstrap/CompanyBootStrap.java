package it.algos.webbase.bootstrap;

import it.algos.webbase.domain.company.BaseCompany;
import it.algos.webbase.web.lib.LibDate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Date;

/**
 * Created by gac on 19 lug 2016.
 * <p>
 * Executed on container startup
 * Setup non-UI logic here
 * <p>
 * Classe eseguita solo quando l'applicazione viene caricata/parte nel server (Tomcat od altri) <br>
 * Eseguita quindi ad ogni avvio/riavvio del server e NON ad ogni sessione <br>
 * È OBBLIGATORIO aggiungere questa classe nei listeners del file web.WEB-INF.web.xml
 */
public class CompanyBootStrap implements ServletContextListener {

    // usati nel test
    public final static String COMPANY_ALGOS = "algos";
    public final static String COMPANY_CIA = "cia";

    /**
     * Executed on container startup
     * Setup non-UI logic here
     * <p>
     * This method is called prior to the servlet context being
     * initialized (when the Web application is deployed).
     * You can initialize servlet context related data here.
     */
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {

        // Cancellazione iniziale di tutte le (eventuali) company
        this.eliminaRecords();

        // Creazione iniziale di alcune company di prova
        this.creaRecords();
    }// end of method

    /**
     * Cancellazione iniziale di tutti i records
     * Usa un DB di prova NON in linea (webbase)
     */
    private void eliminaRecords() {
        BaseCompany.deleteAll();
    }// end of method


    /**
     * Creazione iniziale di alcuni records di prova
     * Li crea SOLO se non esistono già
     * Vengono creati in un DB di prova NON in linea (webbase)
     * Servono per visualizzare una lista e per la classe di test
     */
    private void creaRecords() {
        this.creaRecord(COMPANY_ALGOS,
                "Algos s.r.l.",
                "via Soderini,17 - Milano",
                "info@algos.it",
                "alex",
                LibDate.creaData(1, 1, 2016),
                LibDate.creaData(31, 12, 2016)
        );
        this.creaRecord(COMPANY_CIA,
                "Central Intelligence Agency",
                "Roma",
                "gates@win.com",
                "bob",
                LibDate.creaData(21, 6, 2016),
                LibDate.creaData(31, 12, 2016)
        );
    }// end of method


    /**
     * Creazione di un singolo record
     * Lo crea SOLO se non esiste già
     * Viene creato in un DB di prova NON in linea (webbase)
     *
     * @param companyCode sigla di riferimento interna (obbligatoria)
     * @param companyName descrizione della company (obbligatoria)
     */
    private void creaRecord(String companyCode, String companyName) {
        BaseCompany.crea(companyCode, companyName);
    }// end of method


    /**
     * Creazione di un singolo record
     * Lo crea SOLO se non esiste già
     * Viene creato in un DB di prova NON in linea (webbase)
     *
     * @param companyCode   sigla di riferimento interna (obbligatoria)
     * @param companyName   descrizione della company (obbligatoria)
     * @param address1      principale della company (facoltativa)
     * @param email         della company (facoltativa)
     * @param contact       persona di riferimento della company (facoltativa)
     * @param contractStart inizio del contratto (eventuale)
     * @param contractEnd   fine del contratto (eventuale)
     */
    private void creaRecord(
            String companyCode,
            String companyName,
            String address1,
            String email,
            String contact,
            Date contractStart,
            Date contractEnd) {
        BaseCompany.crea(companyCode, companyName, address1, email, contact, "", contractStart, contractEnd);
    }// end of method


    /**
     * This method is invoked when the Servlet Context
     * (the Web application) is undeployed or
     * WebLogic Server shuts down.
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }// end of method

}// end of bootstrap class
