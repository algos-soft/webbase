import it.algos.webbase.web.lib.LibText;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Creato by gac on 17 giu 2015.
 */
public class LibTextTest {

    // alcuni valori di riferimento
    private static String SPAZI_MULTIPLI = "Abc    def ghi   lmn";
    private static String SPAZI_MULTIPLI_PIU_ESTERNI = " Abc    def ghi   lmn ";
    private static String SPAZIO_SINGOLO = "Abc def ghi lmn";
    private static String SPAZIO_SINGOLO_PIU_ESTERNI = " Abc def ghi lmn ";

    // alcuni parametri utilizzati
    private String sorgente = "";
    private String previsto = "";
    private String ottenuto = "";
    private String tag = "";
    private int numPrevisto = 0;
    private int numOttenuto = 0;

    @Test
    /**
     * Converts multiple spaces to single spaces.
     *
     * @param string in entrata
     * @return string in uscita
     */
    public void zapMultiSpaces() {
        sorgente = SPAZI_MULTIPLI;
        previsto = SPAZIO_SINGOLO;
        ottenuto = LibText.zapMultiSpaces(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = SPAZI_MULTIPLI_PIU_ESTERNI;
        previsto = SPAZIO_SINGOLO_PIU_ESTERNI;
        ottenuto = LibText.zapMultiSpaces(sorgente);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Removes heading and trailing spaces and converts multiple spaces to single spaces.
     *
     * @param string in entrata
     * @return string in uscita
     */
    public void fixSpaces() {
        sorgente = SPAZI_MULTIPLI;
        previsto = SPAZIO_SINGOLO;
        ottenuto = LibText.fixSpaces(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = SPAZI_MULTIPLI_PIU_ESTERNI;
        previsto = SPAZIO_SINGOLO;
        ottenuto = LibText.fixSpaces(sorgente);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @Test
    @SuppressWarnings("all")
    /**
     * Forza il primo carattere della stringa a maiuscolo
     * <p>
     * Se la stringa è nulla, ritorna un nullo
     * Se la stringa è vuota, ritorna una stringa vuota
     *
     * @param entrata stringa in ingresso
     * @return uscita string in uscita
     */
    public void primaMaiuscola() {
        String misto = "4prova";

        sorgente = null;
        previsto = null;
        ottenuto = LibText.primaMaiuscola(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = "";
        previsto = "";
        ottenuto = LibText.primaMaiuscola(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = "tuttominuscolo";
        previsto = "Tuttominuscolo";
        ottenuto = LibText.primaMaiuscola(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = "Tuttominuscolo";
        previsto = "Tuttominuscolo";
        ottenuto = LibText.primaMaiuscola(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = "parteMinuscolo";
        previsto = "ParteMinuscolo";
        ottenuto = LibText.primaMaiuscola(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = "4numero";
        previsto = "4numero";
        ottenuto = LibText.primaMaiuscola(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = "numero4";
        previsto = "Numero4";
        ottenuto = LibText.primaMaiuscola(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = SPAZIO_SINGOLO_PIU_ESTERNI;
        previsto = SPAZIO_SINGOLO_PIU_ESTERNI;
        ottenuto = LibText.primaMaiuscola(sorgente);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    @SuppressWarnings("all")
    /**
     * Forza il primo carattere della stringa a minuscolo
     * <p>
     * Se la stringa è nulla, ritorna un nullo
     * Se la stringa è vuota, ritorna una stringa vuota
     *
     * @param entrata stringa in ingresso
     * @return uscita string in uscita
     */
    public void primaMinuscola() {
        String misto = "4prova";

        sorgente = null;
        previsto = null;
        ottenuto = LibText.primaMinuscola(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = "";
        previsto = "";
        ottenuto = LibText.primaMinuscola(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = "Tuttominuscolo";
        previsto = "tuttominuscolo";
        ottenuto = LibText.primaMinuscola(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = "tuttominuscolo";
        previsto = "tuttominuscolo";
        ottenuto = LibText.primaMinuscola(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = "ParteMinuscolo";
        previsto = "parteMinuscolo";
        ottenuto = LibText.primaMinuscola(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = "4numero";
        previsto = "4numero";
        ottenuto = LibText.primaMinuscola(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = "Numero4";
        previsto = "numero4";
        ottenuto = LibText.primaMinuscola(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = SPAZIO_SINGOLO_PIU_ESTERNI;
        previsto = SPAZIO_SINGOLO_PIU_ESTERNI;
        ottenuto = LibText.primaMaiuscola(sorgente);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @Test
    @SuppressWarnings("all")
    /**
     * Elimina la testa iniziale della stringa, se esiste. <br>
     * <p>
     * Esegue solo se la stringa è valida. <br>
     * Se manca la testa, restituisce la stringa. <br>
     * Elimina spazi vuoti iniziali e finali. <br>
     *
     * @param entrata stringa in ingresso
     * @param testa    da eliminare
     * @return uscita stringa convertita
     */
    public void levaTesta() {
        String testa;

        sorgente = SPAZIO_SINGOLO_PIU_ESTERNI;
        testa = null;
        previsto = SPAZIO_SINGOLO;
        ottenuto = LibText.levaTesta(sorgente, testa);
        assertEquals(ottenuto, previsto);

        sorgente = SPAZIO_SINGOLO_PIU_ESTERNI;
        testa = "";
        previsto = SPAZIO_SINGOLO;
        ottenuto = LibText.levaTesta(sorgente, testa);
        assertEquals(ottenuto, previsto);

        sorgente = " due Ancora  ";
        testa = "due";
        previsto = "Ancora";
        ottenuto = LibText.levaTesta(sorgente, testa);
        assertEquals(ottenuto, previsto);

        sorgente = " due Ancora  ";
        testa = "ora";
        previsto = "due Ancora";
        ottenuto = LibText.levaTesta(sorgente, testa);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    @SuppressWarnings("all")
    /**
     * Elimina la coda terminale della stringa, se esiste. <br>
     * <p>
     * Esegue solo se la stringa è valida. <br>
     * Se manca la coda, restituisce la stringa. <br>
     * Elimina spazi vuoti iniziali e finali. <br>
     *
     * @param entrata stringa in ingresso
     * @param coda    da eliminare
     * @return uscita stringa convertita
     */
    public void levaCoda() {
        String coda;

        sorgente = SPAZIO_SINGOLO_PIU_ESTERNI;
        previsto = SPAZIO_SINGOLO;
        ottenuto = LibText.levaCoda(sorgente, "");
        assertEquals(ottenuto, previsto);

        sorgente = " Ancora due ";
        coda = "due";
        previsto = "Ancora";
        ottenuto = LibText.levaCoda(sorgente, coda);
        assertEquals(ottenuto, previsto);

        sorgente = " Ancora due ";
        coda = "ora";
        previsto = "Ancora due";
        ottenuto = LibText.levaCoda(sorgente, coda);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Elimina la parte di stringa successiva al tag, se esiste.
     * <p>
     * Esegue solo se la stringa è valida
     * Se manca il tag, restituisce la stringa
     * Elimina spazi vuoti iniziali e finali
     *
     * @param entrata   stringa in ingresso
     * @param tagFinale dopo il quale eliminare
     * @return uscita stringa ridotta
     */
    public void levaDopo() {
        sorgente = "Prova di tag varii tipo <ref>";
        tag = "<ref>";
        previsto = "Prova di tag varii tipo";
        ottenuto = LibText.levaDopo(sorgente, tag);
        assertEquals(ottenuto, previsto);

        sorgente = "Prova di tag varii tipo <ref> con testo seguente";
        tag = "<ref>";
        previsto = "Prova di tag varii tipo";
        ottenuto = LibText.levaDopo(sorgente, tag);
        assertEquals(ottenuto, previsto);

        sorgente = " TestoConSpaziTagSoloEsterni ";
        tag = "tag";
        previsto = "TestoConSpaziTagSoloEsterni";
        ottenuto = LibText.levaDopo(sorgente, tag);
        assertEquals(ottenuto, previsto);

        sorgente = " TestoConSpaziTagSoloEsterni ";
        tag = "Tag";
        previsto = "TestoConSpazi";
        ottenuto = LibText.levaDopo(sorgente, tag);
        assertEquals(ottenuto, previsto);
    } // fine del metodo

    @Test
    /**
     * Elimina la parte di stringa successiva al tag <ref>, se esiste.
     * <p>
     * Esegue solo se la stringa è valida
     * Se manca il tag, restituisce la stringa
     * Elimina spazi vuoti iniziali e finali
     *
     * @param entrata stringa in ingresso
     * @return uscita stringa ridotta
     */
    public void levaDopoRef() {
        sorgente = "Prova di tag varii tipo <ref> con testo seguente";
        previsto = "Prova di tag varii tipo";
        ottenuto = LibText.levaDopoRef(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = " Prova di tag varii tipo con testo seguente e spazi esterni ";
        previsto = "Prova di tag varii tipo con testo seguente e spazi esterni";
        ottenuto = LibText.levaDopoRef(sorgente);
        assertEquals(ottenuto, previsto);
    } // fine del metodo

    @Test
    /**
     * Elimina la parte di stringa successiva al tag <!--, se esiste.
     * <p>
     * Esegue solo se la stringa è valida
     * Se manca il tag, restituisce la stringa
     * Elimina spazi vuoti iniziali e finali
     *
     * @param entrata stringa in ingresso
     * @return uscita stringa ridotta
     */
    public void levaDopoNote() {
        sorgente = "Prova di tag varii tipo <!-- con testo seguente";
        previsto = "Prova di tag varii tipo";
        ottenuto = LibText.levaDopoNote(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = " Prova di tag varii tipo <!- con testo seguente ";
        previsto = "Prova di tag varii tipo <!- con testo seguente";
        ottenuto = LibText.levaDopoNote(sorgente);
        assertEquals(ottenuto, previsto);
    } // fine del metodo

    @Test
    /**
     * Elimina la parte di stringa successiva al tag {{, se esiste.
     * <p>
     * Esegue solo se la stringa è valida
     * Se manca il tag, restituisce la stringa
     * Elimina spazi vuoti iniziali e finali
     *
     * @param entrata stringa in ingresso
     * @return uscita stringa ridotta
     */
    public void levaDopoGraffe() {
        sorgente = "Prova di tag varii tipo {{mario}} con testo seguente";
        previsto = "Prova di tag varii tipo";
        ottenuto = LibText.levaDopoGraffe(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = " Prova di tag varii tipo con testo seguente e spazi esterni ";
        previsto = "Prova di tag varii tipo con testo seguente e spazi esterni";
        ottenuto = LibText.levaDopoGraffe(sorgente);
        assertEquals(ottenuto, previsto);
    } // fine del metodo

    @Test
    /**
       * Trova nel testo, la prima occorrenza di un tag compreso nella lista di tag
       *
       * @param testo    da controllare
       * @param listaTag tag da trovare (uno o più)
       * @return posizione nel testo del primo tag trovato, zero se non ce ne sono
       */
    public void trovaPrimo() {
        String originale = "Prova di tag varii tipo{{";
        String tag = "varii";
        String tag2 = "tipo";
        String tag3 = "di";
        String tag4 = "non esiste";
        String tag5 = "forse";

        numPrevisto = 13;
        numOttenuto = LibText.trovaPrimo(originale, tag);
        assertEquals(numOttenuto, numOttenuto);

        numPrevisto = 19;
        numOttenuto = LibText.trovaPrimo(originale, tag2);
        assertEquals(numOttenuto, numOttenuto);

        numPrevisto = 6;
        numOttenuto = LibText.trovaPrimo(originale, tag3);
        assertEquals(numOttenuto, numOttenuto);

        numPrevisto = 13;
        numOttenuto = LibText.trovaPrimo(originale, tag, tag2);
        assertEquals(numOttenuto, numOttenuto);

        numPrevisto = 6;
        numOttenuto = LibText.trovaPrimo(originale, tag3, tag2);
        assertEquals(numOttenuto, numOttenuto);

        numPrevisto = 6;
        numOttenuto = LibText.trovaPrimo(originale, tag3, tag);
        assertEquals(numOttenuto, numOttenuto);

        numPrevisto = 13;
        numOttenuto = LibText.trovaPrimo(originale, tag2, tag);
        assertEquals(numOttenuto, numOttenuto);

        numPrevisto = 6;
        numOttenuto = LibText.trovaPrimo(originale, tag, tag2, tag3);
        assertEquals(numOttenuto, numOttenuto);
        numOttenuto = LibText.trovaPrimo(originale, tag2, tag3, tag);
        assertEquals(numOttenuto, numOttenuto);

        numPrevisto = 0;
        numOttenuto = LibText.trovaPrimo(originale, tag4);
        assertEquals(numOttenuto, numOttenuto);

        numPrevisto = 0;
        numOttenuto = LibText.trovaPrimo(originale, tag4, tag5);
        assertEquals(numOttenuto, numOttenuto);
    }// fine del metodo

}// end of test class
