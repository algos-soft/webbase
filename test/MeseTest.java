import it.algos.webbase.web.lib.MeseEnum;
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
        ottenuto = MeseEnum.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // errore
        numMeseDellAnno = 0;
        previsto = 0;
        ottenuto = MeseEnum.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // errore
        numMeseDellAnno = 13;
        previsto = 0;
        ottenuto = MeseEnum.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // gennaio
        numMeseDellAnno = 1;
        previsto = 31;
        ottenuto = MeseEnum.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // febbraio non bisestile
        numMeseDellAnno = 2;
        previsto = 28;
        ottenuto = MeseEnum.getGiorni(numMeseDellAnno, 2015);
        assertEquals(ottenuto, previsto);

        // febbraio non bisestile
        numMeseDellAnno = 2;
        previsto = 29;
        ottenuto = MeseEnum.getGiorni(numMeseDellAnno, 2016);
        assertEquals(ottenuto, previsto);

        // marzo
        numMeseDellAnno = 3;
        previsto = 31;
        ottenuto = MeseEnum.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // aprile
        numMeseDellAnno = 4;
        previsto = 30;
        ottenuto = MeseEnum.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // maggio
        numMeseDellAnno = 5;
        previsto = 31;
        ottenuto = MeseEnum.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // giugno
        numMeseDellAnno = 6;
        previsto = 30;
        ottenuto = MeseEnum.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // luglio
        numMeseDellAnno = 7;
        previsto = 31;
        ottenuto = MeseEnum.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // agosto
        numMeseDellAnno = 8;
        previsto = 31;
        ottenuto = MeseEnum.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // settembre
        numMeseDellAnno = 9;
        previsto = 30;
        ottenuto = MeseEnum.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // ottobre
        numMeseDellAnno = 10;
        previsto = 31;
        ottenuto = MeseEnum.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // novembre
        numMeseDellAnno = 11;
        previsto = 30;
        ottenuto = MeseEnum.getGiorni(numMeseDellAnno, anno);
        assertEquals(ottenuto, previsto);

        // dicembre
        numMeseDellAnno = 12;
        previsto = 31;
        ottenuto = MeseEnum.getGiorni(numMeseDellAnno, anno);
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
        MeseEnum previsto = null;
        MeseEnum ottenuto = null;
        int numMeseDellAnno = 0;

        // errore
        numMeseDellAnno = -1;
        ottenuto = MeseEnum.getMese(numMeseDellAnno);
        assertNull(ottenuto);

        // errore
        numMeseDellAnno = 0;
        ottenuto = MeseEnum.getMese(numMeseDellAnno);
        assertNull(ottenuto);

        // errore
        numMeseDellAnno = 13;
        ottenuto = MeseEnum.getMese(numMeseDellAnno);
        assertNull(ottenuto);

        // gennaio
        numMeseDellAnno = 1;
        previsto = MeseEnum.gennaio;
        ottenuto = MeseEnum.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // febbraio
        numMeseDellAnno = 2;
        previsto = MeseEnum.febbraio;
        ottenuto = MeseEnum.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // marzo
        numMeseDellAnno = 3;
        previsto = MeseEnum.marzo;
        ottenuto = MeseEnum.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // aprile
        numMeseDellAnno = 4;
        previsto = MeseEnum.aprile;
        ottenuto = MeseEnum.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // maggio
        numMeseDellAnno = 5;
        previsto = MeseEnum.maggio;
        ottenuto = MeseEnum.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // giugno
        numMeseDellAnno = 6;
        previsto = MeseEnum.giugno;
        ottenuto = MeseEnum.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // luglio
        numMeseDellAnno = 7;
        previsto = MeseEnum.luglio;
        ottenuto = MeseEnum.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // agosto
        numMeseDellAnno = 8;
        previsto = MeseEnum.agosto;
        ottenuto = MeseEnum.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // settembre
        numMeseDellAnno = 9;
        previsto = MeseEnum.settembre;
        ottenuto = MeseEnum.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // ottobre
        numMeseDellAnno = 10;
        previsto = MeseEnum.ottobre;
        ottenuto = MeseEnum.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // novembre
        numMeseDellAnno = 11;
        previsto = MeseEnum.novembre;
        ottenuto = MeseEnum.getMese(numMeseDellAnno);
        assertEquals(ottenuto, previsto);

        // dicembre
        numMeseDellAnno = 12;
        previsto = MeseEnum.dicembre;
        ottenuto = MeseEnum.getMese(numMeseDellAnno);
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
        MeseEnum ottenuto = null;
        String nomeBreveLungo = "";

        // errore
        ottenuto = MeseEnum.getMese(null);
        assertNull(ottenuto);

        // errore
        nomeBreveLungo = "";
        ottenuto = MeseEnum.getMese(nomeBreveLungo);
        assertNull(ottenuto);

        // errore
        nomeBreveLungo = "prova";
        ottenuto = MeseEnum.getMese(nomeBreveLungo);
        assertNull(ottenuto);

        // mesi validi
        this.checkMese(MeseEnum.gennaio, "gen", "gennaio", "Gen", "Gennaio");
        this.checkMese(MeseEnum.febbraio, "feb", "febbraio", "Feb", "Febbraio");
        this.checkMese(MeseEnum.marzo, "mar", "marzo", "Mar", "Marzo");
        this.checkMese(MeseEnum.aprile, "apr", "aprile", "Apr", "Aprile");
        this.checkMese(MeseEnum.maggio, "mag", "maggio", "Mag", "Maggio");
        this.checkMese(MeseEnum.giugno, "giu", "giu", "Giu", "Giugno");
        this.checkMese(MeseEnum.luglio, "lug", "luglio", "Lug", "Luglio");
        this.checkMese(MeseEnum.agosto, "ago", "agosto", "Ago", "Agosto");
        this.checkMese(MeseEnum.settembre, "set", "settembre", "Set", "Settembre");
        this.checkMese(MeseEnum.ottobre, "ott", "ottobre", "Ott", "Ottobre");
        this.checkMese(MeseEnum.novembre, "nov", "novembre", "Nov", "Novembre");
        this.checkMese(MeseEnum.dicembre, "dic", "dicembre", "Dic", "Dicembre");
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
        ottenuto = MeseEnum.getOrd(null);
        assertEquals(0, ottenuto);

        // errore
        nomeBreveLungo = "";
        ottenuto = MeseEnum.getOrd(nomeBreveLungo);
        assertEquals(0, ottenuto);

        // errore
        nomeBreveLungo = "prova";
        ottenuto = MeseEnum.getOrd(nomeBreveLungo);
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
        ottenuto = MeseEnum.getShort(-1);
        assertEquals(previsto, ottenuto);

        // errore
        ottenuto = MeseEnum.getShort(0);
        assertEquals(previsto, ottenuto);

        // errore
        ottenuto = MeseEnum.getShort(13);
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
        ottenuto = MeseEnum.getLong(-1);
        assertEquals(previsto, ottenuto);

        // errore
        ottenuto = MeseEnum.getLong(0);
        assertEquals(previsto, ottenuto);

        // errore
        ottenuto = MeseEnum.getLong(13);
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
        String ottenuto = MeseEnum.getAllShortString();
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
        String ottenuto = MeseEnum.getAllLongString();
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
        ArrayList<String> ottenuto = MeseEnum.getAllShortList();
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
        ArrayList<String> ottenuto = MeseEnum.getAllLongList();
        assertEquals(ottenuto, previsto);
    }// end of single test


    private void checkMese(MeseEnum previsto, String... listaNomi) {
        MeseEnum ottenuto = null;

        if (previsto != null && listaNomi != null) {
            for (String nomeBreveLungo : listaNomi) {
                ottenuto = MeseEnum.getMese(nomeBreveLungo);
                assertEquals(ottenuto, previsto);
            }// end of for cycle
        }// fine del blocco if
    }// end of method

    private void checkMeseOrd(int previsto, String... listaNomi) {
        int ottenuto = 0;

        if (previsto > 0 && listaNomi != null) {
            for (String nomeBreveLungo : listaNomi) {
                ottenuto = MeseEnum.getOrd(nomeBreveLungo);
                assertEquals(ottenuto, previsto);
            }// end of for cycle
        }// fine del blocco if
    }// end of method

    private void checkShort(int numMeseDellAnno, String previsto) {
        String ottenuto = "";

        if (numMeseDellAnno > 0 && previsto != null && !previsto.equals("")) {
            ottenuto = MeseEnum.getShort(numMeseDellAnno);
            assertEquals(ottenuto, previsto);
        }// fine del blocco if
    }// end of method

    private void checkLong(int numMeseDellAnno, String previsto) {
        String ottenuto = "";

        if (numMeseDellAnno > 0 && previsto != null && !previsto.equals("")) {
            ottenuto = MeseEnum.getLong(numMeseDellAnno);
            assertEquals(ottenuto, previsto);
        }// fine del blocco if
    }// end of method


}// end of test class