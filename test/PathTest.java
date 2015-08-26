import it.algos.webbase.web.converter.*;
import it.algos.webbase.web.lib.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by gac on 02 ago 2015.
 *
 */
public class PathTest {

    @Test
    /**
     * Estrae il nome significativo di una classe
     * Esclude tutto il path precdente il nome significativo, fino all'ultimo . (punto) del percorso
     *
     * @param classe in entrata
     * @return nome significativo della class
     */
    public void getClassName() {
        String previsto = "";
        String ottenuto = "";
        StringToBigDecimalConverter classe = new StringToBigDecimalConverter();

        previsto = "StringToBigDecimalConverter";
        ottenuto = LibPath.getClassName(classe.getClass());
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Estrae il nome significativo di una classe
     * Esclude tutto il path precdente il nome significativo, fino all'ultimo . (punto) del percorso
     *
     * @param classe in entrata
     * @return nome significativo della class
     */
    public void getClassName2() {
        String previsto = "";
        String ottenuto = "";
        StringToBigDecimalConverter classe = new StringToBigDecimalConverter();

        previsto = "StringToBigDecimalConverter";
        ottenuto = LibPath.getClassName(classe);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Estrae il nome significativo di una classe
     * Esclude tutto il path precdente il nome significativo, fino all'ultimo . (punto) del percorso
     *
     * @param classe in entrata
     * @return nome significativo della class
     */
    public void getClassName3() {
        String previsto = "";
        String ottenuto = "";

        previsto = "StringToBigDecimalConverter";
        ottenuto = LibPath.getClassName(new StringToBigDecimalConverter());
        assertEquals(ottenuto, previsto);
    }// end of single test

}// end of test class