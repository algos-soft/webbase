import it.algos.webbase.domain.pref.Pref;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gac on 19 lug 2016.
 * Test delle funzioni base di persistence sulla tavola Pref
 * Usa un DB di prova NON in linea (webbase)
 */
public class PrefTest extends BaseTest {


    private static String SIGLA = "flag di prova";
    private static String CLASSE = "classe stringa";


    @BeforeClass
    /**
     * SetUp eseguito UNA sola volta e NON prima di ogni test
     */
    public static void setUp() {
        deleteAll();
        creaSetup();
    } // end of static setUp

    /**
     * Cancellazione iniziale di tutte le preferenze
     * Usa un DB di prova NON in linea (webbase)
     */
    public static void deleteAll() {
        for (Pref pref : Pref.getListAll()) {
            pref.delete();
        }// end of for cycle
    }// end of static method

    /**
     * Creazione iniziale di una preferenza
     * Usa un DB di prova NON in linea (webbase)
     */
    public static void creaSetup() {
        Pref.crea(SIGLA, CLASSE);
    }// end of static method


//    @Test
//    /**
//     * Recupera una lista di records di Pref
//     * Senza filtri.
//     *
//     * @return lista di Pref
//     */
//    public void getListAll() {
////        ArrayList<Pref> lista;
////
////        lista = Pref.getListAll();
////
////        for (Pref pref : lista) {
////            pref.delete();
////        }// end of for cycle
////
////        int a = 87;
//    }// end of method


    @Test
    /**
     * Recupera il totale dei records di Pref
     * Filtrato sulla azienda corrente.
     *
     * @return numero totale di records di Pref
     */
    public void count() {
        int totRecords;

        totRecords = Pref.count();
        assertEquals(totRecords, 1);
    }// end of single test


//    @Test
//    /**
//     * Recupera il totale dei records di Pref
//     * Filtrato sulla azienda passata come parametro.
//     *
//     * @param company company di appartenenza
//     * @return numero totale di records di Pref
//     */
//    public void count2() {
//    }// end of method


//    @Test
//    /**
//     * Recupera il totale dei records di Pref
//     * Senza filtri.
//     *
//     * @return numero totale di records di Pref
//     */
//    public void countAll() {
//    }// end of method


//    @Test
//    /**
//     * Recupera una istanza di Pref usando la query specifica
//     *
//     * @return istanza di Pref, null se non trovata
//     */
//    public void find() {
//        long idTmp;
//        Pref pref;
//
//        idTmp = 3744;
//        pref = Pref.find(idTmp);
//        assertNull(pref);
//
//        idTmp = 18;
//        pref = Pref.find(idTmp);
//        assertNotNull(pref);
//    }// end of method

    @Test
    /**
     * Recupera una istanza di Pref usando la query della property chiave
     * Filtrato sulla azienda corrente.
     *
     * @param code sigla di riferimento interna (obbligatoria)
     * @return istanza di Pref, null se non trovata
     */
    public void findByCode() {
        Pref pref = Pref.findByCode(SIGLA);
        assertNotNull(pref);
    }// end of method


    @Test
    /**
      * Creazione iniziale di una preferenza
      * La crea SOLO se non esiste già
      *
      * @param code   sigla di riferimento interna (obbligatoria)
      * @param classe nome della classe del tipo di dato (obbligatoria)
      * @return istanza di Pref
      */
    public void crea() {
        Pref.crea(SIGLA, CLASSE);
    }// end of static method

}// end of test class
