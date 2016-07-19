import it.algos.webbase.domain.company.BaseCompany;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.entity.EM;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertNotNull;

/**
 * Created by gac on 19 lug 2016.
 * Test delle funzioni base di persistence sulla tavola Pref
 * Usa il DB reale
 */
public class PrefTest extends BaseTest {

//    @Before
//    public void startup() {
//        EntityManager manager = EM.createEntityManager();
//        company = BaseCompany.find(1);
//        assertNotNull(ottenuto);
//    } // end of startup

    @Test
    /**
     * Recupera il totale dei records di Pref
     * Filtrato sulla azienda corrente.
     *
     * @return numero totale di records di Pref
     */
    public void count() {
        int a= Pref.count();
        int b=87;
    }// end of single test


    @Test
    /**
     * Recupera il totale dei records di Pref
     * Filtrato sulla azienda passata come parametro.
     *
     * @param company company di appartenenza
     * @return numero totale di records di Pref
     */
    public void count2() {
    }// end of method


    @Test
    /**
     * Recupera il totale dei records di Pref
     * Senza filtri.
     *
     * @return numero totale di records di Pref
     */
    public void countAll() {
    }// end of method

}// end of test class
