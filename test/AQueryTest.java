import it.algos.webbase.domain.vers.Versione;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by gac on 13 set 2016.
 * <p>
 * I test vengono eseguiti su un DB di prova, usando un apposito EntityManager
 * Viene usata una Entity che NON usa Company: Versione
 */
public class AQueryTest {

    protected static EntityManager MANAGER;

    private Versione versioneUno;
    private Versione versioneDue;
    private Versione versioneTre;
    private Versione versioneQuattro;
    private Versione versioneCinque;

    private List<Versione> listaUno;
    private List<Versione> listaDue;
    private List<Versione> listaTre;

    /**
     * SetUp iniziale eseguito solo una volta alla creazione della classe
     */
    @BeforeClass
    public static void setUpInizialeStaticoEseguitoSoloUnaVoltaAllaCreazioneDellaClasse() {

    } // end of setup statico iniziale della classe


    /**
     * CleanUp finale eseguito solo una volta alla chiusura della classe
     */
    @AfterClass
    public static void cleanUpFinaleStaticoEseguitoSoloUnaVoltaAllaChiusuraDellaClasse() {
    } // end of cleaup statico finale della classe

    /**
     * Crea alcuni records temporanei per effettuare le prove delle query
     * Uso la tavola Versione
     * Creo 5 nuovi records
     */
    private static void creaRecords() {
    }// end of static method

    /**
     * SetUp eseguito prima dell'esecuzione di ogni metodo
     */
    @Before
    public void setUp() {
        reset();
    } // end of setup prima di ogni metodo di test

    /**
     * CleanUp eseguito dopo l'esecuzione di ogni metodo
     */
    @After
    public void cleanUp() {
        reset();
    } // end of cleaup dopo ogni metodo di test

    /**
     * Creazione di un MANAGER specifico
     * DEVE essere chiuso (must be close by caller method)
     */
    protected static void creaManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("WAMTEST");

        if (factory != null) {
            MANAGER = factory.createEntityManager();
        }// end of if cycle
    }// end of static method

    /**
     * Azzera le variabili d'istanza
     */
    private void reset() {
        versioneUno = null;
        versioneDue = null;
        versioneTre = null;
        versioneQuattro = null;
        versioneCinque = null;
    } // end of cleaup finale

    @Test
    /**
     * Ritorna il numero totale di records della Entity specificata
     * Senza filtri.
     *
     * @param clazz the Entity class
     * @return il numero totale di records nella Entity
     */
    public void count() {
    }// end of single test

}// end of test class