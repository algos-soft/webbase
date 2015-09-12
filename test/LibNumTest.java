import it.algos.webbase.web.lib.LibNum;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by gac on 12 set 2015.
 * .
 */
public  class LibNumTest extends BaseTest {


    @Test
    /**
     * Formattazione di un numero.
     * <p/>
     * Il numero può arrivare come stringa, intero o double
     * Se la stringa contiene punti e virgole, viene pulita
     * Se la stringa non è convertibile in numero, viene restituita uguale
     * Inserisce il punto separatore ogni 3 cifre
     * Se arriva un oggetto non previsto, restituisce null

     * @param numero da formattare (stringa, intero o double)
     * @return stringa formattata
     */
    public void format() {
        ArrayList lista = new ArrayList();
        lista.add("primo");
        lista.add(2);

        sorgente = "123g456";
        previsto = "123g456";
        ottenuto = LibNum.format(sorgente);
        assertEquals(ottenuto, previsto);

        ottenuto = LibNum.format(null);
        assertNull(ottenuto);

        ottenuto = LibNum.format(lista);
        assertNull(ottenuto);

        previsto = "123.456";
        numSorgente = 123456;
        ottenuto = LibNum.format(numSorgente);
        assertEquals(ottenuto, previsto);
        sorgente = "123456";
        ottenuto = LibNum.format(sorgente);
        assertEquals(ottenuto, previsto);
        sorgente = "123,456";
        ottenuto = LibNum.format(sorgente);
        assertEquals(ottenuto, previsto);
        sorgente = "123.456";
        ottenuto = LibNum.format(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = "123456,0";
        previsto = "1.234.560";
        ottenuto = LibNum.format(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = "123456789";
        previsto = "123.456.789";
        ottenuto = LibNum.format(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = "1234567890";
        previsto = "1.234.567.890";
        ottenuto = LibNum.format(sorgente);
        assertEquals(ottenuto, previsto);
    }// end of single test

}// end of test class
