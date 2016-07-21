import it.algos.webbase.bootstrap.CompanyBootStrap;
import it.algos.webbase.domain.company.BaseCompany;
import it.algos.webbase.web.lib.LibDate;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by gac on 19 lug 2016.
 * Test delle funzioni base di persistence sulla tavola Pref
 * Usa un DB di prova NON in linea (webbase)
 */
public class CompanyTest extends BaseTest {


    @BeforeClass
    /**
     * SetUp eseguito UNA sola volta e NON prima di ogni test
     */
    public static void setUp() {
        deleteAll();
        creaSetup();
    } // end of static setUp


    /**
     * Cancellazione iniziale di tutte i records
     * Usa un DB di prova NON in linea (webbase)
     */
    public static void deleteAll() {
        BaseCompany.deleteAll();
    }// end of static method


    /**
     * Creazione iniziale di uno o più record
     * Usa un DB di prova NON in linea (webbase)
     */
    public static void creaSetup() {
        BaseCompany.crea(CompanyBootStrap.COMPANY_ALGOS, "Algos s.r.l.");
    }// end of static method


    @Test
    /**
     * Recupera il totale dei records della classe
     * Senza filtri.
     *
     * @return numero totale di records
     */
    public void count() {
        int totRecords = 0;

        totRecords = BaseCompany.count();
        assertEquals(totRecords, 1);
    }// end of single test

    @Test
    /**
     * Recupera una lista di tutti i records
     * Senza filtri.
     *
     * @return lista di istanze della classe
     */
    public void getList() {
        BaseCompany company;
        ArrayList<BaseCompany> lista = BaseCompany.getList();

        assertNotNull(lista);
        assertTrue(lista.size() > 0);

        company = lista.get(0);
        assertNotNull(company);
        assertEquals(company.getCompanyCode(), CompanyBootStrap.COMPANY_ALGOS);
    }// end of single test


    @Test
    /**
     * Controlla l'esistenza del record usando la property unica
     *
     * @param companyCode sigla di riferimento interna (obbligatoria ed unica)
     * @return istanza della classe, null se non trovata
     */
    public void isEsiste() {
        boolOttenuto = BaseCompany.isEsiste(CompanyBootStrap.COMPANY_ALGOS);
        assertTrue(boolOttenuto);
    }// end of single test


    @Test
    /**
     * Recupera una istanza della classe usando la primary key
     *
     * @return istanza della classe, null se non trovata
     */
    public void find() {
        ArrayList<BaseCompany> lista;
        BaseCompany companyPrevista;
        BaseCompany companyOttenuta;
        long id;

        lista = BaseCompany.getList();

        assertNotNull(lista);
        assertEquals(lista.size(), 1);

        companyPrevista = lista.get(0);
        id = companyPrevista.getId();

        companyOttenuta = BaseCompany.find(id);
        assertEquals(companyOttenuta, companyPrevista);
    }// end of single test


    @Test
    /**
     * Recupera una istanza della classe usando la query specifica
     *
     * @return istanza della classe, null se non trovata
     */
    public void findByCode() {
        BaseCompany company;

        company = BaseCompany.findByCode(CompanyBootStrap.COMPANY_ALGOS);

        assertNotNull(company);
        assertEquals(company.getCompanyCode(), CompanyBootStrap.COMPANY_ALGOS);
    }// end of single test


    @Test
    /**
     * Creazione iniziale di una istanza della classe
     * La crea SOLO se non esiste già
     *
     * @param companyCode sigla di riferimento interna (obbligatoria)
     * @param companyName descrizione della company (obbligatoria)
     * @return istanza della classe
     */
    public void crea() {
        int totRecordsAnte = 0;
        int totRecordsPost = 0;

        //--questo non lo crea, perché c'è già
        totRecordsAnte = BaseCompany.count();
        BaseCompany.crea(CompanyBootStrap.COMPANY_ALGOS, "nonServe");
        totRecordsPost = BaseCompany.count();
        assertEquals(totRecordsPost, totRecordsAnte);

        //--questo lo crea, perché manca
        BaseCompany.crea(CompanyBootStrap.COMPANY_CIA, "nonServe");
        totRecordsPost = BaseCompany.count();
        assertEquals(totRecordsPost, totRecordsAnte + 1);

        //--questo lo crea completo (con un companyCode diverso)
        totRecordsAnte = BaseCompany.count();
        BaseCompany.crea(CompanyBootStrap.COMPANY_ALGOS + "-bis",
                "Algos s.r.l.",
                "via Soderini,17 - Milano",
                "info@algos.it",
                "alex",
                "franchising",
                LibDate.creaData(1, 1, 2016),
                LibDate.creaData(31, 12, 2016));
        totRecordsPost = BaseCompany.count();
        assertEquals(totRecordsPost, totRecordsAnte + 1);
    }// end of single test

}// end of test class