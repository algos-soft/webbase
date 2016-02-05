import it.algos.webbase.web.lib.Mese;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MeseTest {

    @Test
    /**
     * Numero di giorni del mese
     *
     * @param numMeseDellAnno L'anno parte da gennaio che è il mese numero 1
     * @return Numero di giorni del mese
     */
    public void getGiorni() {
        int previsto = 0;
        int ottenuto = 0;
        int numMeseDellAnno = 0;
        int anno = 1984;

        // errore
        numMeseDellAnno = -1;
        previsto = 0;
        ottenuto = Mese.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // errore
        numMeseDellAnno = 0;
        previsto = 0;
        ottenuto = Mese.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // errore
        numMeseDellAnno = 13;
        previsto = 0;
        ottenuto = Mese.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // gennaio
        numMeseDellAnno = 1;
        previsto = 31;
        ottenuto = Mese.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // febbraio non bisestile
        numMeseDellAnno = 2;
        previsto = 28;
        ottenuto = Mese.getGiorni(numMeseDellAnno, 2015);
        assertEquals(ottenuto, previsto);

        // febbraio non bisestile
        numMeseDellAnno = 2;
        previsto = 29;
        ottenuto = Mese.getGiorni(numMeseDellAnno, 2016);
        assertEquals(ottenuto, previsto);

        // marzo
        numMeseDellAnno = 3;
        previsto = 31;
        ottenuto = Mese.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // aprile
        numMeseDellAnno = 4;
        previsto = 30;
        ottenuto = Mese.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // maggio
        numMeseDellAnno = 5;
        previsto = 31;
        ottenuto = Mese.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // giugno
        numMeseDellAnno = 6;
        previsto = 30;
        ottenuto = Mese.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // luglio
        numMeseDellAnno = 7;
        previsto = 31;
        ottenuto = Mese.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // agosto
        numMeseDellAnno = 8;
        previsto = 31;
        ottenuto = Mese.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // settembre
        numMeseDellAnno = 9;
        previsto = 30;
        ottenuto = Mese.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // ottobre
        numMeseDellAnno = 10;
        previsto = 31;
        ottenuto = Mese.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // novembre
        numMeseDellAnno = 11;
        previsto = 30;
        ottenuto = Mese.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // dicembre
        numMeseDellAnno = 12;
        previsto = 31;
        ottenuto = Mese.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Mese
     *
     * @param numMeseDellAnno L'anno parte da gennaio che è il mese numero 1
     * @return Mese
     */
    public void getMese() {
        Mese previsto = null;
        Mese ottenuto = null;
        int numMeseDellAnno = 0;

        // errore
        numMeseDellAnno = -1;
        ottenuto = Mese.getMese(numMeseDellAnno);
        assertNull(ottenuto);

        // errore
        numMeseDellAnno = 0;
        ottenuto = Mese.getMese(numMeseDellAnno);
        assertNull(ottenuto);

        // errore
        numMeseDellAnno = 13;
        ottenuto = Mese.getMese(numMeseDellAnno);
        assertNull(ottenuto);

        // gennaio
        numMeseDellAnno = 1;
        previsto = Mese.gennaio;
        ottenuto = Mese.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // febbraio
        numMeseDellAnno = 2;
        previsto = Mese.febbraio;
        ottenuto = Mese.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // marzo
        numMeseDellAnno = 3;
        previsto = Mese.marzo;
        ottenuto = Mese.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // aprile
        numMeseDellAnno = 4;
        previsto = Mese.aprile;
        ottenuto = Mese.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // maggio
        numMeseDellAnno = 5;
        previsto = Mese.maggio;
        ottenuto = Mese.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // giugno
        numMeseDellAnno = 6;
        previsto = Mese.giugno;
        ottenuto = Mese.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // luglio
        numMeseDellAnno = 7;
        previsto = Mese.luglio;
        ottenuto = Mese.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // agosto
        numMeseDellAnno = 8;
        previsto = Mese.agosto;
        ottenuto = Mese.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // settembre
        numMeseDellAnno = 9;
        previsto = Mese.settembre;
        ottenuto = Mese.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // ottobre
        numMeseDellAnno = 10;
        previsto = Mese.ottobre;
        ottenuto = Mese.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // novembre
        numMeseDellAnno = 11;
        previsto = Mese.novembre;
        ottenuto = Mese.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // dicembre
        numMeseDellAnno = 12;
        previsto = Mese.dicembre;
        ottenuto = Mese.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Mese
     *
     * @param nomeBreveLungo Nome breve o lungo del mese
     * @return Mese
     */
    public void getMese2() {
        Mese ottenuto = null;
        String nomeBreveLungo = "";

        // errore
        ottenuto = Mese.getMese(null);
        assertNull(ottenuto);

        // errore
        nomeBreveLungo = "";
        ottenuto = Mese.getMese(nomeBreveLungo);
        assertNull(ottenuto);

        // errore
        nomeBreveLungo = "prova";
        ottenuto = Mese.getMese(nomeBreveLungo);
        assertNull(ottenuto);

        // mesi validi
        this.checkMese(Mese.gennaio, "gen", "gennaio", "Gen", "Gennaio");
        this.checkMese(Mese.febbraio, "feb", "febbraio", "Feb", "Febbraio");
        this.checkMese(Mese.marzo, "mar", "marzo", "Mar", "Marzo");
        this.checkMese(Mese.aprile, "apr", "aprile", "Apr", "Aprile");
        this.checkMese(Mese.maggio, "mag", "maggio", "Mag", "Maggio");
        this.checkMese(Mese.giugno, "giu", "giu", "Giu", "Giugno");
        this.checkMese(Mese.luglio, "lug", "luglio", "Lug", "Luglio");
        this.checkMese(Mese.agosto, "ago", "agosto", "Ago", "Agosto");
        this.checkMese(Mese.settembre, "set", "settembre", "Set", "Settembre");
        this.checkMese(Mese.ottobre, "ott", "ottobre", "Ott", "Ottobre");
        this.checkMese(Mese.novembre, "nov", "novembre", "Nov", "Novembre");
        this.checkMese(Mese.dicembre, "dic", "dicembre", "Dic", "Dicembre");
    }// end of single test

    @Test
   /**
      * Numero del mese nell'anno
      *
      * @param nomeBreveLungo L'anno parte da gennaio che è il mese numero 1
      * @return Numero del mese
      */
    public void getOrd() {
        int ottenuto = 0;
        String nomeBreveLungo = "";

        // errore
        ottenuto = Mese.getOrd(null);
        assertEquals(0, ottenuto);

        // errore
        nomeBreveLungo = "";
        ottenuto = Mese.getOrd(nomeBreveLungo);
        assertEquals(0, ottenuto);

        // errore
        nomeBreveLungo = "prova";
        ottenuto = Mese.getOrd(nomeBreveLungo);
        assertEquals(0, ottenuto);

        // mesi validi
        this.checkMeseOrd(1, "gen", "gennaio", "Gen", "Gennaio");
        this.checkMeseOrd(2, "feb", "febbraio", "Feb", "Febbraio");
        this.checkMeseOrd(3, "mar", "marzo", "Mar", "Marzo");
        this.checkMeseOrd(4, "apr", "aprile", "Apr", "Aprile");
        this.checkMeseOrd(5, "mag", "maggio", "Mag", "Maggio");
        this.checkMeseOrd(6, "giu", "giu", "Giu", "Giugno");
        this.checkMeseOrd(7, "lug", "luglio", "Lug", "Luglio");
        this.checkMeseOrd(8, "ago", "agosto", "Ago", "Agosto");
        this.checkMeseOrd(9, "set", "settembre", "Set", "Settembre");
        this.checkMeseOrd(10, "ott", "ottobre", "Ott", "Ottobre");
        this.checkMeseOrd(11, "nov", "novembre", "Nov", "Novembre");
        this.checkMeseOrd(12, "dic", "dicembre", "Dic", "Dicembre");
    }// end of single test

    @Test
    /**
      * Nome breve del mese
      *
      * @param numMeseDellAnno L'anno parte da gennaio che è il mese numero 1
      * @return Nome breve del mese
      */
    public void getShort() {
        String previsto = "";
        String ottenuto = "";

        // errore
        ottenuto = Mese.getShort(-1);
        assertEquals(previsto, ottenuto);

        // errore
        ottenuto = Mese.getShort(0);
        assertEquals(previsto, ottenuto);

        // errore
        ottenuto = Mese.getShort(13);
        assertEquals(previsto, ottenuto);

        // mesi validi
        this.checkShort(1, "gen");
        this.checkShort(2, "feb");
        this.checkShort(3, "mar");
        this.checkShort(4, "apr");
        this.checkShort(5, "mag");
        this.checkShort(6, "giu");
        this.checkShort(7, "lug");
        this.checkShort(8, "ago");
        this.checkShort(9, "set");
        this.checkShort(10, "ott");
        this.checkShort(11, "nov");
        this.checkShort(12, "dic");
    }// end of single test

    @Test
    /**
      * Nome completo del mese
      *
      * @param numMeseDellAnno L'anno parte da gennaio che è il mese numero 1
      * @return Nome breve del mese
      */
    public void getLong() {
        String previsto = "";
        String ottenuto = "";

        // errore
        ottenuto = Mese.getLong(-1);
        assertEquals(previsto, ottenuto);

        // errore
        ottenuto = Mese.getLong(0);
        assertEquals(previsto, ottenuto);

        // errore
        ottenuto = Mese.getLong(13);
        assertEquals(previsto, ottenuto);

        // mesi validi
        this.checkLong(1, "gennaio");
        this.checkLong(2, "febbraio");
        this.checkLong(3, "marzo");
        this.checkLong(4, "aprile");
        this.checkLong(5, "maggio");
        this.checkLong(6, "giugno");
        this.checkLong(7, "luglio");
        this.checkLong(8, "agosto");
        this.checkLong(9, "settembre");
        this.checkLong(10, "ottobre");
        this.checkLong(11, "novembre");
        this.checkLong(12, "dicembre");
    }// end of single test

    @Test
    /**
     * Elenco di tutti i nomi in forma breve
     *
     * @return Stringa dei nomi brevi separati da virgola
     */
    public void getAllShortString() {
        String previsto = "gen, feb, mar, apr, mag, giu, lug, ago, set, ott, nov, dic";
        String ottenuto = Mese.getAllShortString();
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Elenco di tutti i nomi in forma completa
     *
     * @return Stringa dei nomi completi separati da virgola
     */
    public void getAllLongString() {
        String previsto = "gennaio, febbraio, marzo, aprile, maggio, giugno, luglio, agosto, settembre, ottobre, novembre, dicembre";
        String ottenuto = Mese.getAllLongString();
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Elenco di tutti i nomi in forma breve
     *
     * @return Array dei nomi brevi
     */
    public void getAllShortList() {
        ArrayList<String> previsto = new ArrayList(Arrays.asList("gen", "feb", "mar", "apr", "mag", "giu", "lug",
                "ago", "set", "ott", "nov", "dic"));
        ArrayList<String> ottenuto = Mese.getAllShortList();
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
      * Elenco di tutti i nomi in forma breve
      *
      * @return Array dei nomi brevi
      */
    public void getAllLongList() {
        ArrayList<String> previsto = new ArrayList(Arrays.asList("gennaio", "febbraio", "marzo", "aprile", "maggio",
                "giugno", "luglio", "agosto", "settembre", "ottobre", "novembre", "dicembre"));
        ArrayList<String> ottenuto = Mese.getAllLongList();
        assertEquals(ottenuto, previsto);
    }// end of single test


    private void checkMese(Mese previsto, String... listaNomi) {
        Mese ottenuto = null;

        if (previsto != null && listaNomi != null) {
            for (String nomeBreveLungo : listaNomi) {
                ottenuto = Mese.getMese(nomeBreveLungo);
                assertEquals(ottenuto, previsto);
            }// end of for cycle
        }// fine del blocco if
    }// end of method

    private void checkMeseOrd(int previsto, String... listaNomi) {
        int ottenuto = 0;

        if (previsto > 0 && listaNomi != null) {
            for (String nomeBreveLungo : listaNomi) {
                ottenuto = Mese.getOrd(nomeBreveLungo);
                assertEquals(ottenuto, previsto);
            }// end of for cycle
        }// fine del blocco if
    }// end of method

    private void checkShort(int numMeseDellAnno, String previsto) {
        String ottenuto = "";

        if (numMeseDellAnno > 0 && previsto != null && !previsto.equals("")) {
            ottenuto = Mese.getShort(numMeseDellAnno);
            assertEquals(ottenuto, previsto);
        }// fine del blocco if
    }// end of method

    private void checkLong(int numMeseDellAnno, String previsto) {
        String ottenuto = "";

        if (numMeseDellAnno > 0 && previsto != null && !previsto.equals("")) {
            ottenuto = Mese.getLong(numMeseDellAnno);
            assertEquals(ottenuto, previsto);
        }// fine del blocco if
    }// end of method


}// end of test class