import com.vaadin.ui.Label;
import it.algos.webbase.web.lib.LibArray;
import it.algos.webbase.web.lib.LibTime;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Gac on 24 ago 2015.
 * .
 */
public class LibArrayTest {

    private static int SIZE = 7;
    private static int NEW_SIZE = 8;
    private static int SIZE_UNICI = 3;
    protected boolean boolPrevisto = false;
    protected boolean boolOttenuto = false;
    protected int intPrevisto = 0;
    protected int intOttenuto = 0;
    private int primoInt = 8;
    private int due = 15;
    private int tre = 27;
    private Object primoObj = 17L;
    private long primoLungo = 27L;
    private String primoTxt = "alfa";
    private String secondo = "beta";
    private String terzo = "gamma";
    @SuppressWarnings("all")
    private String altraStringa = "valore";
    private String[] stringArray = {primoTxt, "aB", "c", "0", "2", "1Ad", "a10"};
    private int[] intArray = {primoInt, 25, 4, 816, 0, -1, 99};
    private long[] longArray = {primoLungo, 25L, 4L, 816L, 0L, -1L, 99L};
    @SuppressWarnings("all")
    private Object[] objArray = {primoObj, "testo", 43, "stringa", 122L, -1L, 99};
    @SuppressWarnings("all")
    private int altroNumero = 457;
    @SuppressWarnings("all")
    private long altroLungo = 457L;
    @SuppressWarnings("all")
    private String[] valoriDoppiTxt = {terzo, primoTxt, secondo, primoTxt, terzo, secondo};
    @SuppressWarnings("all")
    private int[] valoriDoppiNum = {due, tre, primoInt, due, primoInt, due, primoInt};
    @SuppressWarnings("all")
    private long[] valoriDoppiLong = {primoLungo, primoInt, due, primoInt, due, primoInt};
    @SuppressWarnings("all")
    private Object[] valoriDoppiObj = {primoTxt, primoLungo, primoTxt, primoInt, primoInt, primoTxt};
    private ArrayList<String> listaUno = LibArray.fromString(stringArray);
    private String prevista = "";
    private String ottenuta = "";
    private ArrayList ottenuto;
    private int k = 0;

    @Test
    /**
     * Convert a stringArray to ArrayList
     *
     * @param stringArray to convert
     * @return the corresponding casted ArrayList
     */
    public void fromString() {
        List<String> ottenuto;

        ottenuto = LibArray.fromString(stringArray);
        assertNotNull(ottenuto);
        assertEquals(ottenuto.size(), SIZE);
        assertTrue(ottenuto.get(0).equals(primoTxt));

        ottenuto.add(altraStringa);
        assertEquals(stringArray.length, SIZE); // per essere sicuri che non venga modificata la matrice originaria
        assertEquals(ottenuto.size(), NEW_SIZE);
    } // end of single test

    @Test
    /**
     * Convert a intArray to ArrayList
     *
     * @param intArray to convert
     * @return the corresponding casted ArrayList
     */
    public void fromInt() {
        List<Integer> ottenuto;

        ottenuto = LibArray.fromInt(intArray);
        assertNotNull(ottenuto);
        assertEquals(ottenuto.size(), SIZE);
        assertTrue(ottenuto.get(0) == primoInt);

        ottenuto.add(altroNumero);
        assertEquals(intArray.length, SIZE); // per essere sicuri che non venga modificata la matrice originaria
        assertEquals(ottenuto.size(), NEW_SIZE);
    } // end of single test

    @Test
    /**
     * Convert a longArray to ArrayList
     *
     * @param longArray to convert
     * @return the corresponding casted ArrayList
     */
    public void fromLong() {
        List<Long> ottenuto;

        ottenuto = LibArray.fromLong(longArray);
        assertNotNull(ottenuto);
        assertEquals(ottenuto.size(), SIZE);
        assertTrue(ottenuto.get(0) == primoLungo);

        ottenuto.add(altroLungo);
        assertEquals(longArray.length, SIZE); // per essere sicuri che non venga modificata la matrice originaria
        assertEquals(ottenuto.size(), NEW_SIZE);
    } // end of single test

    @Test
    /**
     * Convert a objArray to ArrayList
     *
     * @param objArray to convert
     * @return the corresponding casted ArrayList
     */
    public void fromObj() {
        List<Object> ottenuto;

        ottenuto = LibArray.fromObj(objArray);
        assertNotNull(ottenuto);
        assertEquals(ottenuto.size(), SIZE);
        assertTrue(ottenuto.get(0) == primoObj);

        ottenuto.add(new Date());
        assertEquals(objArray.length, SIZE); // per essere sicuri che non venga modificata la matrice originaria
        assertEquals(ottenuto.size(), NEW_SIZE);
    } // end of single test

    @Test
    /**
     * Estrae i valori unici da un lista con (eventuali) valori doppi
     *
     * @param listaValoriDoppi in ingresso
     * @return valoriUnici NON ordinati, null se listaValoriDoppi è null
     */
    public void valoriUniciDisordinati() {
        ottenuto = LibArray.fromString(valoriDoppiTxt);
        System.out.println("");
        System.out.println("Lista valori di testo doppi");
        k = 0;
        for (Object obj : ottenuto) {
            System.out.println(++k + ") " + obj);
        } // fine del ciclo for-each

        ottenuto = LibArray.valoriUniciDisordinati(ottenuto);
        assertTrue(ottenuto instanceof ArrayList);
        assertEquals(ottenuto.size(), SIZE_UNICI);
        assertEquals(ottenuto.get(0), terzo);
        assertEquals(ottenuto.get(1), primoTxt);
        assertEquals(ottenuto.get(2), secondo);

        System.out.println("");
        System.out.println("Lista valori unici disordinati - stringa");
        k = 0;
        for (Object obj : ottenuto) {
            System.out.println(++k + ") " + obj);
        } // fine del ciclo for-each

        ottenuto = LibArray.fromInt(valoriDoppiNum);
        System.out.println("");
        System.out.println("Lista valori di interi doppi");
        k = 0;
        for (Object obj : ottenuto) {
            System.out.println(++k + ") " + obj);
        } // fine del ciclo for-each

        ottenuto = LibArray.valoriUniciDisordinati(ottenuto);
        assertTrue(ottenuto instanceof ArrayList);
        assertEquals(ottenuto.size(), SIZE_UNICI);
        assertEquals(ottenuto.get(0), due);
        assertEquals(ottenuto.get(1), tre);
        assertEquals(ottenuto.get(2), primoInt);

        System.out.println("");
        System.out.println("Lista valori unici disordinati - intero");
        k = 0;
        for (Object obj : ottenuto) {
            System.out.println(++k + ") " + obj);
        } // fine del ciclo for-each

        ottenuto = LibArray.fromObj(valoriDoppiObj);
        System.out.println("");
        System.out.println("Lista valori di oggetti");
        k = 0;
        for (Object obj : ottenuto) {
            System.out.println(++k + ") " + obj + " (" + obj.getClass().getSimpleName() + ")");
        } // fine del ciclo for-each

        ottenuto = LibArray.valoriUniciDisordinati(ottenuto);
        assertTrue(ottenuto instanceof ArrayList);
        assertEquals(ottenuto.size(), SIZE_UNICI);
        assertEquals(ottenuto.get(0), primoTxt);
        assertEquals(ottenuto.get(1), primoLungo);
        assertEquals(ottenuto.get(2), primoInt);

        System.out.println("");
        System.out.println("Lista valori unici disordinati - oggetti");
        k = 0;
        for (Object obj : ottenuto) {
            System.out.println(++k + ") " + obj + " (" + obj.getClass().getSimpleName() + ")");
        } // fine del ciclo for-each
    } // fine del test

    @Test
    /**
     * Estrae i valori unici da un matrice (objArray) con (eventuali) valori doppi
     *
     * @param objArray in ingresso
     * @return valoriUnici NON ordinati, null se objArray è null
     */
    public void valoriUniciDisordinati2() {
        ottenuto = LibArray.valoriUniciDisordinati(valoriDoppiTxt);
        assertTrue(ottenuto instanceof ArrayList);
        assertEquals(ottenuto.size(), SIZE_UNICI);
        assertEquals(ottenuto.get(0), terzo);
        assertEquals(ottenuto.get(1), primoTxt);
        assertEquals(ottenuto.get(2), secondo);

        ottenuto = LibArray.valoriUniciDisordinati(valoriDoppiObj);
        assertTrue(ottenuto instanceof ArrayList);
        assertEquals(ottenuto.size(), SIZE_UNICI);
        assertEquals(ottenuto.get(0), primoTxt);
        assertEquals(ottenuto.get(1), primoLungo);
        assertEquals(ottenuto.get(2), primoInt);
    } // fine del test

    @Test
    /**
     * Estrae i valori unici da un lista con (eventuali) valori doppi
     * Ordina l'array secondo la classe utilizzata:
     * alfabetico per le stringhe
     * numerico per i numeri
     * L'ordinamento funziona SOLO se la lista è omogenea (oggetti della stessa classe)
     *
     * @param listaValoriDoppi in ingresso
     * @return valoriUnici ORDINATI, null se listaValoriDoppi è null
     */
    public void valoriUnici() {
        ottenuto = LibArray.fromString(valoriDoppiTxt);
        System.out.println("");
        System.out.println("Lista valori di testo doppi");
        k = 0;
        for (Object obj : ottenuto) {
            System.out.println(++k + ") " + obj);
        } // fine del ciclo for-each

        ottenuto = LibArray.valoriUnici(ottenuto);
        assertTrue(ottenuto instanceof ArrayList);
        assertEquals(ottenuto.size(), SIZE_UNICI);
        assertEquals(ottenuto.get(0), primoTxt);
        assertEquals(ottenuto.get(1), secondo);
        assertEquals(ottenuto.get(2), terzo);

        System.out.println("");
        System.out.println("Lista valori unici ORDINATI - stringa");
        k = 0;
        for (Object obj : ottenuto) {
            System.out.println(++k + ") " + obj);
        } // fine del ciclo for-each

        ottenuto = LibArray.fromInt(valoriDoppiNum);
        System.out.println("");
        System.out.println("Lista valori di interi doppi");
        k = 0;
        for (Object obj : ottenuto) {
            System.out.println(++k + ") " + obj);
        } // fine del ciclo for-each

        ottenuto = LibArray.valoriUnici(ottenuto);
        assertTrue(ottenuto instanceof ArrayList);
        assertEquals(ottenuto.size(), SIZE_UNICI);
        assertEquals(ottenuto.get(0), primoInt);
        assertEquals(ottenuto.get(1), due);
        assertEquals(ottenuto.get(2), tre);

        System.out.println("");
        System.out.println("Lista valori unici ORDINATI - intero");
        k = 0;
        for (Object obj : ottenuto) {
            System.out.println(++k + ") " + obj);
        } // fine del ciclo for-each

        ottenuto = LibArray.fromObj(valoriDoppiObj);
        System.out.println("");
        System.out.println("Lista valori di oggetti doppi");
        k = 0;
        for (Object obj : ottenuto) {
            System.out.println(++k + ") " + obj);
        } // fine del ciclo for-each

        ottenuto = LibArray.valoriUnici(ottenuto);
        assertTrue(ottenuto instanceof ArrayList);
        assertEquals(ottenuto.size(), 3);
        assertEquals(ottenuto.get(0), primoTxt);
        assertEquals(ottenuto.get(1), primoLungo);
        assertEquals(ottenuto.get(2), primoInt);
    } // fine del test

    @Test
    /**
     * Estrae i valori unici da un matrice (objArray) con (eventuali) valori doppi
     * Ordina l'array secondo la classe utilizzata:
     * alfabetico per le stringhe
     * numerico per i numeri
     * L'ordinamento funziona SOLO se la lista è omogenea (oggetti della stessa classe)
     *
     * @param objArray in ingresso
     * @return valoriUnici ORDINATI, null se listaValoriDoppi è null
     */
    public void valoriUnici2() {
        ottenuto = LibArray.valoriUnici(valoriDoppiTxt);
        assertTrue(ottenuto instanceof ArrayList);
        assertEquals(ottenuto.size(), SIZE_UNICI);
        assertEquals(ottenuto.get(0), primoTxt);
        assertEquals(ottenuto.get(1), secondo);
        assertEquals(ottenuto.get(2), terzo);

        System.out.println("");
        System.out.println("Matrice unici ORDINATI - stringa");
        k = 0;
        for (Object obj : ottenuto) {
            System.out.println(++k + ") " + obj + " (" + obj.getClass().getSimpleName() + ")");
        } // fine del ciclo for-each

        ottenuto = LibArray.valoriUnici(valoriDoppiNum);
        assertNotNull(ottenuto);
        assertEquals(ottenuto.size(), SIZE_UNICI);
        assertEquals(ottenuto.get(0), primoInt);
        assertEquals(ottenuto.get(1), due);
        assertEquals(ottenuto.get(2), tre);

        System.out.println("");
        System.out.println("Matrice unici ORDINATI - intero");
        k = 0;
        for (Object obj : ottenuto) {
            System.out.println(++k + ") " + obj + " (" + obj.getClass().getSimpleName() + ")");
        } // fine del ciclo for-each

        ottenuto = LibArray.valoriUnici(valoriDoppiLong);
        assertNotNull(ottenuto);
        assertEquals(ottenuto.size(), SIZE_UNICI);
        assertEquals(ottenuto.get(0), (long) primoInt);
        assertEquals(ottenuto.get(1), (long) due);
        assertEquals(ottenuto.get(2), (long) tre);

        System.out.println("");
        System.out.println("Matrice unici ORDINATI - lungo");
        k = 0;
        for (Object obj : ottenuto) {
            System.out.println(++k + ") " + obj + " (" + obj.getClass().getSimpleName() + ")");
        } // fine del ciclo for-each
    } // fine del test

    @Test
    /**
     * Aggiunge un elemento alla lista solo se non già esistente
     *
     * @param lista
     * @param elemento
     * @return vero se l'elemento è stato aggiunto
     */
    public void add() {
        ottenuto = LibArray.fromString(stringArray);
        assertEquals(ottenuto.size(), SIZE);

        LibArray.add(ottenuto, primoTxt);
        assertEquals(ottenuto.size(), SIZE);

        LibArray.add(ottenuto, secondo);
        assertEquals(ottenuto.size(), NEW_SIZE);
    } // fine del test

    @Test
    /**
     * Somma due array (liste) e restituisce una lista disordinata
     * <p>
     * Almeno uno dei due array in ingresso deve essere non nullo
     * Normalmente si usa di meno la somma disordinata
     * <p>
     * Se entrambi i parametri sono nulli, restituisce un nullo
     * Se uno dei parametri è nullo, restituisce l'altro
     * La lista di valori in uscita è unica (quindi la dimensione può essere minore dalla somma delle due)
     *
     * @param arrayPrimo   - prima lista
     * @param arraySecondo - seconda lista
     * @return arraySomma disordinata
     */
    public void sommaDisordinata() {
        int size = 9;
        ArrayList<String> arrayPrimo = LibArray.fromString(stringArray);
        ArrayList<String> arraySecondo = LibArray.fromString(valoriDoppiTxt);
        int sizePrimo = arrayPrimo.size();
        int sizeSecondo = arraySecondo.size();

        ottenuto = LibArray.sommaDisordinata(null, null);
        assertNull(ottenuto);

        ottenuto = LibArray.sommaDisordinata(arrayPrimo, null);
        assertEquals(ottenuto.size(), SIZE);
        assertEquals(arrayPrimo.size(), sizePrimo);

        ottenuto = LibArray.sommaDisordinata(null, arraySecondo);
        assertEquals(ottenuto.size(), SIZE_UNICI);
        assertEquals(arraySecondo.size(), sizeSecondo);

        ottenuto = LibArray.sommaDisordinata(arrayPrimo, arraySecondo);
        assertEquals(ottenuto.size(), size);
        assertEquals(arrayPrimo.size(), sizePrimo);
        assertEquals(arraySecondo.size(), sizeSecondo);

        System.out.println("");
        System.out.println("Somma");
        k = 0;
        for (Object obj : ottenuto) {
            System.out.println(++k + ") " + obj);
        } // fine del ciclo for-each
    } // fine del test

    @Test
    public void sommaDisordinata2() {
        ArrayList<String> arrayPrimo = new ArrayList<String>();
        ArrayList<String> arraySecondo = new ArrayList<String>();
        int cicli = 50000;
        long inizio = System.currentTimeMillis();
        String primoStep;
        String secondStep;


        for (Integer k = 0; k < cicli; k++) {
            arrayPrimo.add(k.toString());
        }// end of for cycle

        for (Integer k = cicli; k < cicli * 2; k++) {
            arraySecondo.add(k.toString());
        }// end of for cycle

        primoStep = LibTime.difText(inizio);
        System.out.println("primoStep: " + primoStep);

        ottenuto = LibArray.sommaDisordinata(arrayPrimo, arraySecondo);

        secondStep = LibTime.difText(inizio);
        System.out.println("secondStep: " + secondStep);
        int a = 87;
    } // fine del test

    @Test
    /**
     * Somma due array (liste) e restituisce una lista ordinata
     * <p>
     * Almeno uno dei due array in ingresso deve essere non nullo
     * Normalmente si usa di più la somma ordinata
     * <p>
     * Se entrambi i parametri sono nulli, restituisce un nullo
     * Se uno dei parametri è nullo, restituisce l'altro
     * La lista di valori in uscita è unica (quindi la dimensione può essere minore dalla somma delle due)
     *
     * @param arrayPrimo   - prima lista
     * @param arraySecondo - seconda lista
     * @return arraySomma ordinata
     */
    public void somma() {
        int size = 9;
        ArrayList<String> arrayPrimo = LibArray.fromString(stringArray);
        ArrayList<String> arraySecondo = LibArray.fromString(valoriDoppiTxt);
        int sizePrimo = arrayPrimo.size();
        int sizeSecondo = arraySecondo.size();

        ottenuto = LibArray.somma(null, null);
        assertNull(ottenuto);

        ottenuto = LibArray.somma(arrayPrimo, null);
        assertEquals(ottenuto.size(), SIZE);
        assertEquals(arrayPrimo.size(), sizePrimo);

        ottenuto = LibArray.somma(null, arraySecondo);
        assertEquals(ottenuto.size(), SIZE_UNICI);
        assertEquals(arraySecondo.size(), sizeSecondo);
        ottenuto = LibArray.somma(arrayPrimo, arraySecondo);
        assertEquals(ottenuto.size(), size);
        assertEquals(arrayPrimo.size(), sizePrimo);
        assertEquals(arraySecondo.size(), sizeSecondo);

        System.out.println("");
        System.out.println("Somma ordinata");
        k = 0;
        for (Object obj : ottenuto) {
            System.out.println(++k + ") " + obj);
        } // fine del ciclo for-each
    } // fine del test

    @Test
    /**
     * Differenza tra due array (liste) e restituisce una lista
     * <p>
     * Il primo array in ingresso deve essere non nullo e deve essere una lista
     * I valori negli array sono unici
     * Normalmente si usa di meno la differenza disordinata
     * <p>
     * Se entrambi i parametri sono liste della stessa classe, restituisce la differenza
     * Se entrambi i parametri sono liste ma di classe diversa, restituisce un nullo
     * Se il primo parametro è nullo, restituisce un nullo
     *
     * @param arrayPrimo   - prima lista
     * @param arraySecondo - seconda lista
     * @return arrayDifferenza disordinato
     */
    public void differenzaDisordinata() {
        int[] primoNum = {7, 87, 4, 25, 1, 12};
        int[] secondoNum = {11, 7, 55, 4};
        int[] richiestoNum = {87, 25, 1, 12};
        int sizeNumPrimo = 6;
        int sizeNum = 4;
        ArrayList<Integer> primoArrayNum = LibArray.fromInt(primoNum);
        ArrayList<Integer> richiestoArrayNum = LibArray.fromInt(richiestoNum);
        int[] arrayVuoto = {};
        String[] primoStr = {"due", "otto", "beta", "alfa", "omicron"};
        String[] secondoStr = {"otto", "gamma", "due"};
        String[] richiestoStr = {"beta", "alfa", "omicron"};
        int sizeStr = 3;
        ArrayList<String> richiestoArrayStr = LibArray.fromString(richiestoStr);
        ArrayList ottenuto;

        // entrambi i parametri sono nulli, restituisce un nullo
        ottenuto = LibArray.differenzaDisordinata(null, null);
        assertNull(ottenuto);

        // il primo parametro è nullo, restituisce un nullo
        ottenuto = LibArray.differenzaDisordinata(null, (ArrayList) LibArray.fromInt(secondoNum));
        assertNull(ottenuto);

        // entrambi i parametri sono liste ma di classe diversa, restituisce un nullo
        ottenuto = LibArray.differenzaDisordinata((ArrayList) LibArray.fromInt(primoNum), (ArrayList) LibArray.fromString(secondoStr));
        assertNull(ottenuto);

        // entrambi i parametri sono liste ma di classe diversa, restituisce un nullo
        ottenuto = LibArray.differenzaDisordinata((ArrayList) LibArray.fromString(primoStr), (ArrayList) LibArray.fromInt(secondoNum));
        assertNull(ottenuto);

        // entrambi i parametri sono liste della stessa classe, restituisce la differenza
        ottenuto = LibArray.differenzaDisordinata((ArrayList) LibArray.fromInt(primoNum), (ArrayList) LibArray.fromInt(secondoNum));
        assertEquals(ottenuto.size(), sizeNum);
        assertEquals(ottenuto.get(0), richiestoArrayNum.get(0));
        assertEquals(ottenuto.get(1), richiestoArrayNum.get(1));
        assertEquals(ottenuto.get(2), richiestoArrayNum.get(2));
        assertEquals(ottenuto.get(3), richiestoArrayNum.get(3));

        // entrambi i parametri sono liste della stessa classe, restituisce la differenza
        ottenuto = LibArray.differenzaDisordinata((ArrayList) LibArray.fromString(primoStr), (ArrayList) LibArray.fromString(secondoStr));
        assertEquals(ottenuto.size(), sizeStr);
        assertEquals(ottenuto.get(0), richiestoArrayStr.get(0));
        assertEquals(ottenuto.get(1), richiestoArrayStr.get(1));
        assertEquals(ottenuto.get(2), richiestoArrayStr.get(2));

        // il secondo parametro è nullo, restituisce il primo array
        ottenuto = LibArray.differenzaDisordinata((ArrayList) LibArray.fromInt(primoNum), null);
        assertEquals(ottenuto.size(), sizeNumPrimo);
        assertEquals(ottenuto.get(0), primoArrayNum.get(0));
        assertEquals(ottenuto.get(1), primoArrayNum.get(1));
        assertEquals(ottenuto.get(2), primoArrayNum.get(2));
        assertEquals(ottenuto.get(3), primoArrayNum.get(3));
        assertEquals(ottenuto.get(4), primoArrayNum.get(4));
        assertEquals(ottenuto.get(5), primoArrayNum.get(5));

        // il secondo parametro è una lista vuota (zero elementi), restituisce il primo array
        ottenuto = LibArray.differenzaDisordinata((ArrayList) LibArray.fromInt(primoNum), (ArrayList) LibArray.fromInt(arrayVuoto));
        assertEquals(ottenuto.size(), sizeNumPrimo);
        assertEquals(ottenuto.get(0), primoArrayNum.get(0));
        assertEquals(ottenuto.get(1), primoArrayNum.get(1));
        assertEquals(ottenuto.get(2), primoArrayNum.get(2));
        assertEquals(ottenuto.get(3), primoArrayNum.get(3));
        assertEquals(ottenuto.get(4), primoArrayNum.get(4));
        assertEquals(ottenuto.get(5), primoArrayNum.get(5));
    }// fine tests

    @Test
    /**
     * Costruisce una stringa con i singoli valori divisi da un pipe
     * <p>
     *
     * @param array lista di valori
     * @return stringa con i singoli valori divisi da un separatore
     */
    public void toStringa() {
        int size = 7;
        String ottenuta;
        String richiestaPipe = "alfa|aB|c|0|2|1Ad|a10";
        String richiestaVirgola = "alfa,aB,c,0,2,1Ad,a10";
        ArrayList lista = LibArray.fromString(stringArray);
        assertEquals(lista.size(), size);

        ottenuta = LibArray.toStringaPipe(lista);
        assertEquals(ottenuta, richiestaPipe);

        ottenuta = LibArray.toStringaVirgola(lista);
        assertEquals(ottenuta, richiestaVirgola);
    }// fine tests

    @Test
    /**
     * Costruisce una stringa con i singoli valori divisi da un separatore
     * <p>
     *
     * @param stringArray to convert
     * @param sep         carattere separatore
     * @return stringa con i singoli valori divisi da un separatore
     */
    public void fromStringToStringa() {
        String ottenuta;
        String richiestaPipe = "alfa|aB|c|0|2|1Ad|a10";
        String richiestaVirgola = "alfa,aB,c,0,2,1Ad,a10";

        ottenuta = LibArray.fromStringToStringaPipe(stringArray);
        assertEquals(ottenuta, richiestaPipe);

        ottenuta = LibArray.fromStringToStringaVirgola(stringArray);
        assertEquals(ottenuta, richiestaVirgola);
    }// fine tests

    @Test
    /**
     * Costruisce una stringa con i singoli valori divisi da un pipe
     * <p>
     *
     * @param array lista di valori
     * @return stringa con i singoli valori divisi da un separatore
     */
    public void testToStringaPipe() throws Exception {
        prevista = "alfa|aB|c|0|2|1Ad|a10";
        ottenuta = LibArray.toStringaPipe(listaUno);
        assertEquals(ottenuta, prevista);
    }// fine tests

    @Test
    /**
     * Costruisce una stringa con i singoli valori divisi da una virgola
     * <p>
     *
     * @param array lista di valori
     * @return stringa con i singoli valori divisi da un separatore
     */
    public void testToStringaVirgola() throws Exception {
        prevista = "alfa,aB,c,0,2,1Ad,a10";
        ottenuta = LibArray.toStringaVirgola(listaUno);
        assertEquals(ottenuta, prevista);
    }// fine tests

    @Test
    /**
     * Costruisce una stringa con i singoli valori divisi da un pipe
     * <p>
     *
     * @param stringArray to convert
     * @return stringa con i singoli valori divisi da un separatore
     */
    public void testFromStringToStringaPipe() throws Exception {
        prevista = "alfa|aB|c|0|2|1Ad|a10";
        ottenuta = LibArray.fromStringToStringaPipe(stringArray);
        assertEquals(ottenuta, prevista);
    }// fine tests

    @Test
    /**
     * Costruisce una stringa con i singoli valori divisi da una virgola
     * <p>
     *
     * @param stringArray to convert
     * @return stringa con i singoli valori divisi da un separatore
     */
    public void testFromStringToStringaVirgola() throws Exception {
        prevista = "alfa,aB,c,0,2,1Ad,a10";
        ottenuta = LibArray.fromStringToStringaVirgola(stringArray);
        assertEquals(ottenuta, prevista);
    }// fine tests


    @Test
    /**
     * Controlla l'eguaglianza di due array
     * <p>
     * Confronta tutti i valori, INDIPENDENTEMENTE dall'ordine in cui li trova
     *
     * @param expected previsto
     * @param actual   effettivo
     * @return vero, se gli array sono lunghi uguali ed hanno gli stessi valori (disordinati)
     */
    public void isArrayEquals() {
        ArrayList expected;
        ArrayList actual;
        ArrayList errataLunghezza;
        ArrayList errataChiave;
        ArrayList erratoValore;
        String chiave1 = "xyz";
        String chiave2 = "postCommit";
        String chiave3 = "forse";
        String chiave4 = "nonrilevante";
        String chiave5 = "nessuna";
        String chiaveErrata = "zzz";
        long value1 = 4567;
        String value2 = "secondovalorestringa";
        boolean value3 = false;
        Label value4 = new Label();
        Date value5 = new Date();
        String valueErrato = "xxx";

        expected = new ArrayList();
        expected.add(chiave1);
        expected.add(chiave2);
        expected.add(chiave3);
        expected.add(chiave4);
        expected.add(chiave5);

        actual = new ArrayList();
        actual.add(chiave1);
        actual.add(chiave3);
        actual.add(chiave2);
        actual.add(chiave5);
        actual.add(chiave4);

        boolOttenuto = LibArray.isArrayEquals(expected, actual);
        assertTrue(boolOttenuto);

        expected = new ArrayList();
        expected.add(value1);
        expected.add(value2);
        expected.add(value3);
        expected.add(value4);
        expected.add(value5);

        actual = new ArrayList();
        actual.add(value1);
        actual.add(value3);
        actual.add(value2);
        actual.add(value5);
        actual.add(value4);

        boolOttenuto = LibArray.isArrayEquals(expected, actual);
        assertTrue(boolOttenuto);

        errataLunghezza = new ArrayList();
        errataLunghezza.add(chiave1);
        errataLunghezza.add(chiave2);
        errataLunghezza.add(chiave4);
        errataLunghezza.add(chiave5);
        boolOttenuto = LibArray.isArrayEquals(errataLunghezza, actual);
        assertFalse(boolOttenuto);

        errataLunghezza = new ArrayList();
        errataLunghezza.add(value1);
        errataLunghezza.add(value3);
        errataLunghezza.add(value5);
        errataLunghezza.add(value4);
        boolOttenuto = LibArray.isArrayEquals(errataLunghezza, actual);
        assertFalse(boolOttenuto);

        errataChiave = new ArrayList();
        errataChiave.add(chiave1);
        errataChiave.add(chiave2);
        errataChiave.add(chiaveErrata);
        errataChiave.add(chiave4);
        errataChiave.add(chiave5);
        boolOttenuto = LibArray.isArrayEquals(errataChiave, actual);
        assertFalse(boolOttenuto);

        erratoValore = new ArrayList();
        erratoValore.add(value1);
        erratoValore.add(value2);
        erratoValore.add(valueErrato);
        erratoValore.add(value4);
        erratoValore.add(value5);
        boolOttenuto = LibArray.isArrayEquals(erratoValore, actual);
        assertFalse(boolOttenuto);

    }// fine tests

    @Test
    /**
     * Controlla l'eguaglianza di due array
     * <p>
     * Confronta tutti i valori ORDINATI
     *
     * @param expected previsto
     * @param actual   effettivo
     * @return vero, se gli array sono lunghi uguali ed hanno gli stessi valori (ordinati)
     */
    public void isArrayEqualsOrdinati() {
        ArrayList expected;
        ArrayList actual;
        ArrayList errataLunghezza;
        ArrayList errataChiave;
        ArrayList erratoValore;
        String chiave1 = "xyz";
        String chiave2 = "postCommit";
        String chiave3 = "forse";
        String chiave4 = "nonrilevante";
        String chiave5 = "nessuna";
        String chiaveErrata = "zzz";
        long value1 = 4567;
        String value2 = "secondovalorestringa";
        boolean value3 = false;
        Label value4 = new Label();
        Date value5 = new Date();
        String valueErrato = "xxx";

        expected = new ArrayList();
        expected.add(chiave1);
        expected.add(chiave2);
        expected.add(chiave3);
        expected.add(chiave4);
        expected.add(chiave5);

        actual = new ArrayList();
        actual.add(chiave2);
        actual.add(chiave5);
        actual.add(chiave1);
        actual.add(chiave3);
        actual.add(chiave4);

        boolOttenuto = LibArray.isArrayEquals(expected, actual);
        assertTrue(boolOttenuto);

        boolOttenuto = LibArray.isArrayEqualsOrdinati(expected, actual);
        assertFalse(boolOttenuto);

        expected = new ArrayList();
        expected.add(value1);
        expected.add(value2);
        expected.add(value3);
        expected.add(value4);
        expected.add(value5);

        actual = new ArrayList();
        actual.add(value1);
        actual.add(value3);
        actual.add(value2);
        actual.add(value5);
        actual.add(value4);

        boolOttenuto = LibArray.isArrayEquals(expected, actual);
        assertTrue(boolOttenuto);

        boolOttenuto = LibArray.isArrayEqualsOrdinati(expected, actual);
        assertFalse(boolOttenuto);

        errataLunghezza = new ArrayList();
        errataLunghezza.add(chiave1);
        errataLunghezza.add(chiave2);
        errataLunghezza.add(chiave4);
        errataLunghezza.add(chiave5);
        boolOttenuto = LibArray.isArrayEqualsOrdinati(errataLunghezza, actual);
        assertFalse(boolOttenuto);

        errataLunghezza = new ArrayList();
        errataLunghezza.add(value1);
        errataLunghezza.add(value3);
        errataLunghezza.add(value5);
        errataLunghezza.add(value4);
        boolOttenuto = LibArray.isArrayEqualsOrdinati(errataLunghezza, actual);
        assertFalse(boolOttenuto);

        errataChiave = new ArrayList();
        errataChiave.add(chiave1);
        errataChiave.add(chiave2);
        errataChiave.add(chiaveErrata);
        errataChiave.add(chiave4);
        errataChiave.add(chiave5);
        boolOttenuto = LibArray.isArrayEqualsOrdinati(errataChiave, actual);
        assertFalse(boolOttenuto);

        erratoValore = new ArrayList();
        erratoValore.add(value1);
        erratoValore.add(value2);
        erratoValore.add(valueErrato);
        erratoValore.add(value4);
        erratoValore.add(value5);
        boolOttenuto = LibArray.isArrayEqualsOrdinati(erratoValore, actual);
        assertFalse(boolOttenuto);

    }// fine tests

    @Test
    /**
     * Controlla l'eguaglianza di due mappe
     * <p>
     *
     * @param expected prevista
     * @param actual   effettiva
     * @return vero, se le mappe sono lunghe uguali, hanno le stesse chiavi e gli stessi valori
     */
    public void isMapEquals() {
        String chiave1 = "xyz";
        String chiave2 = "postCommit";
        String chiave3 = "forse";
        String chiave4 = "nonrilevante";
        String chiave5 = "nessuna";
        String chiaveErrata = "zzz";
        long value1 = 4567;
        String value2 = "secondovalorestringa";
        boolean value3 = false;
        Label value4 = new Label();
        Date value5 = new Date();
        String valueErrato = "xxx";

        HashMap<String, Object> expected = new HashMap<String, Object>();
        expected.put(chiave1, value1);
        expected.put(chiave2, value2);
        expected.put(chiave3, value3);
        expected.put(chiave4, value4);
        expected.put(chiave5, value5);

        HashMap<String, Object> actual = new HashMap<String, Object>();
        actual.put(chiave1, value1);
        actual.put(chiave2, value2);
        actual.put(chiave3, value3);
        actual.put(chiave4, value4);
        actual.put(chiave5, value5);

        HashMap<String, Object> errataLunghezza = new HashMap<String, Object>();
        errataLunghezza.put(chiave1, value1);
        errataLunghezza.put(chiave2, value2);
        errataLunghezza.put(chiave4, value4);
        errataLunghezza.put(chiave5, value5);

        HashMap<String, Object> errataChiave = new HashMap<String, Object>();
        errataChiave.put(chiave1, value1);
        errataChiave.put(chiave2, value2);
        errataChiave.put(chiaveErrata, value3);
        errataChiave.put(chiave4, value4);
        errataChiave.put(chiave5, value5);

        HashMap<String, Object> errataValore = new HashMap<String, Object>();
        errataValore.put(chiave1, value1);
        errataValore.put(chiave2, value2);
        errataValore.put(chiave3, valueErrato);
        errataValore.put(chiave4, value4);
        errataValore.put(chiave5, value5);

        boolOttenuto = LibArray.isMapEquals(expected, actual);
        assertTrue(boolOttenuto);

        boolOttenuto = LibArray.isMapEquals(errataLunghezza, actual);
        assertFalse(boolOttenuto);

        boolOttenuto = LibArray.isMapEquals(errataChiave, actual);
        assertFalse(boolOttenuto);

        boolOttenuto = LibArray.isMapEquals(errataValore, actual);
        assertFalse(boolOttenuto);
    }// fine tests


    @Test
    /**
     * Numero di cicli
     *
     * @param totale da dividere
     * @param blocco divisore
     * @return numero di cicli
     */
    public void numCicli() {
        int blocco = 500;

        intPrevisto = 0;
        intOttenuto = LibArray.numCicli(1250, -23);
        assertEquals(intOttenuto, intPrevisto);

        intPrevisto = 1250;
        intOttenuto = LibArray.numCicli(1250, 0);
        assertEquals(intOttenuto, intPrevisto);

        intPrevisto = 1;
        intOttenuto = LibArray.numCicli(375, blocco);
        assertEquals(intOttenuto, intPrevisto);

        intPrevisto = 2;
        intOttenuto = LibArray.numCicli(999, blocco);
        assertEquals(intOttenuto, intPrevisto);

        intPrevisto = 2;
        intOttenuto = LibArray.numCicli(1000, blocco);
        assertEquals(intOttenuto, intPrevisto);

        intPrevisto = 3;
        intOttenuto = LibArray.numCicli(1001, blocco);
        assertEquals(intOttenuto, intPrevisto);
    }// fine tests


    @Test
    /**
     * Estra un subset dalla lista
     *
     * @param listatTotale  da suddividere
     * @param dimBlocco     di suddivisione
     * @param cicloCorrente attuale
     * @return sublista corrente del ciclo
     */
    public void estraeSublistaLong() {
        ArrayList<Long> sublista;
        ArrayList<Long> listatTotale = new ArrayList<Long>();
        int dimListaOriginale = 1234;
        int dimBlocco = 500;

        sublista = LibArray.estraeSublistaLong(null, dimBlocco, 3);
        assertNull(sublista);

        sublista = LibArray.estraeSublistaLong(listatTotale, dimBlocco, 3);
        assertNull(sublista);

        for (long k = 0; k < dimListaOriginale; k++) {
            listatTotale.add(k);
        }// end of for cycle
        assertEquals(listatTotale.size(), dimListaOriginale);

        sublista = LibArray.estraeSublistaLong(listatTotale, dimBlocco, 0);
        assertNotNull(sublista);
        assertTrue(sublista.size() == dimBlocco);
        assertTrue(sublista.get(0) == 0);

        sublista = LibArray.estraeSublistaLong(listatTotale, dimBlocco, 1);
        assertNotNull(sublista);
        assertTrue(sublista.size() == dimBlocco);
        assertTrue(sublista.get(0) == 500);

        sublista = LibArray.estraeSublistaLong(listatTotale, dimBlocco, 2);
        assertNotNull(sublista);
        assertTrue(sublista.size() == 234);
        assertTrue(sublista.get(0) == 1000);

    }// fine tests

//
//    void testUnivoco() {
//        def listaNum = [7, 87, 4, 25, 1, 12]
//        def listaStr = ['due', 'otto', 'beta', 'alfa', 'omicron']
//        def richiestoNum = [7, 87, 4, 25, 1, 12, 17]
//        def richiestoStr = ['due', 'otto', 'beta', 'alfa', 'omicron', 'gamma']
//        int numOld = 4
//        int numNew = 17
//        String strOld = 'beta'
//        String strNew = 'gamma'
//
//        if (!listaNum.contains(numOld)) {
//            listaNum.add(numOld)
//        }// fine del blocco if
//        assert listaNum.size() == 6
//        assert listaNum == listaNum
//
//        if (!listaNum.contains(numNew)) {
//            listaNum.add(numNew)
//        }// fine del blocco if
//        assert listaNum.size() == 7
//        assert listaNum == richiestoNum
//
//        if (!listaStr.contains(strOld)) {
//            listaStr.add(strOld)
//        }// fine del blocco if
//        assert listaStr.size() == 5
//        assert listaStr == listaStr
//
//        if (!listaStr.contains(strNew)) {
//            listaStr.add(strNew)
//        }// fine del blocco if
//        assert listaStr.size() == 6
//        assert listaStr == richiestoStr
//    }// fine tests
//
//    /**
//     * Aggiunge un elemento alla lista solo se non già esistente
//     *
//     * @param array
//     * @param elemento
//     * @return vero se l'elemento è stato aggiunto
//     */
//    void testAdd() {
//        def listaNum = [7, 87, 4, 25, 1, 12]
//        def listaStr = ['due', 'otto', 'beta', 'alfa', 'omicron']
//        def richiestoNum = [7, 87, 4, 25, 1, 12, 17]
//        def richiestoStr = ['due', 'otto', 'beta', 'alfa', 'omicron', 'gamma']
//        int numOld = 4
//        int numNew = 17
//        String strOld = 'beta'
//        String strNew = 'gamma'
//        boolean aggiunto
//
//        aggiunto = Array.add(listaNum, numOld)
//        assert !aggiunto
//        assert listaNum.size() == 6
//        assert listaNum == listaNum
//
//        aggiunto = Array.add(listaNum, numNew)
//        assert aggiunto
//        assert listaNum.size() == 7
//        assert listaNum == richiestoNum
//
//        aggiunto = Array.add(listaStr, strOld)
//        assert !aggiunto
//        assert listaStr.size() == 5
//        assert listaStr == listaStr
//
//        aggiunto = Array.add(listaStr, strNew)
//        assert aggiunto
//        assert listaStr.size() == 6
//        assert listaStr == richiestoStr
//    }// fine tests


    @Test
    /**
     * Ordina la lista
     * L'ordinamento funziona SOLO se la lista è omogenea (oggetti della stessa classe)
     *
     * @param listaDisordinata in ingresso
     * @return lista ordinata, null se listaDisordinata è null
     */
    public void testSort() throws Exception {
        List ottenuta;
        ArrayList<String> disordinata;
        ArrayList<Integer> disordinati;
        ArrayList prevista;
        disordinata = new ArrayList();
        disordinata.add("Beta");
        disordinata.add("Mercoledi");
        disordinata.add("Alfa");
        disordinati = new ArrayList();
        disordinati.add(27);
        disordinati.add(1235);
        disordinati.add(4);
        disordinati.add(15);

        prevista = new ArrayList();
        prevista.add("Alfa");
        prevista.add("Beta");
        prevista.add("Mercoledi");
        ottenuta = LibArray.sort(disordinata);
        assertEquals(ottenuta, prevista);

        prevista = new ArrayList();
        prevista.add(4);
        prevista.add(15);
        prevista.add(27);
        prevista.add(1235);
        ottenuta = LibArray.sort(disordinati);
        assertEquals(ottenuta, prevista);
    }// fine tests


    @Test
    /**
     * Ordina la lista
     * L'ordinamento funziona SOLO se la lista è omogenea (oggetti della stessa classe)
     *
     * @param listaDisordinata in ingresso
     * @return lista ordinata, null se listaDisordinata è null
     */
    public void testSort2() throws Exception {
        ArrayList ottenuta;
        ArrayList<String> disordinata;
        ArrayList<Integer> disordinati;
        ArrayList prevista;
        disordinata = new ArrayList();
        disordinata.add("Beta");
        disordinata.add("Álvaro");
        disordinata.add("Venerdi");
        disordinati = new ArrayList();

        prevista = new ArrayList();
        prevista.add("Álvaro");
        prevista.add("Beta");
        prevista.add("Venerdi");

        ottenuta = LibArray.sort(disordinata);
        boolOttenuto = LibArray.isArrayEquals(ottenuta, prevista);
        assertTrue(boolOttenuto);
        boolOttenuto = LibArray.isArrayEqualsOrdinati(ottenuta, prevista);
        assertFalse(boolOttenuto);

        ottenuta = LibArray.sortAccentiSensibile(disordinata);
        boolOttenuto = LibArray.isArrayEquals(ottenuta, prevista);
        assertTrue(boolOttenuto);
        boolOttenuto = LibArray.isArrayEqualsOrdinati(ottenuta, prevista);
        assertTrue(boolOttenuto);
    }// fine tests

    @Test
    /**
     * Recupera una lista delle chiavi di una mappa
     *
     * @param mappa in ingresso
     * @return lista delle chiavi
     */
    public void getKeyFromMap() {
        String chiave1 = "xyz";
        String chiave2 = "postCommit";
        String chiave3 = "forse";
        String chiave4 = "nonrilevante";
        String chiave5 = "nessuna";
        long value1 = 4567;
        String value2 = "secondovalorestringa";
        boolean value3 = false;
        Label value4 = new Label();
        Date value5 = new Date();
        ArrayList listaPrevista = new ArrayList();
        ArrayList listaOttenuta;
        listaPrevista.add(chiave1);
        listaPrevista.add(chiave2);
        listaPrevista.add(chiave3);
        listaPrevista.add(chiave4);
        listaPrevista.add(chiave5);

        HashMap<String, Object> mappa = new HashMap<String, Object>();
        mappa.put(chiave1, value1);
        mappa.put(chiave2, value2);
        mappa.put(chiave3, value3);
        mappa.put(chiave4, value4);
        mappa.put(chiave5, value5);

        listaOttenuta = LibArray.getKeyFromMap(mappa);
        boolOttenuto = LibArray.isArrayEquals(listaOttenuta, listaPrevista);
        assertTrue(boolOttenuto);

    }// fine tests


    @Test
    /**
     * Ordina una mappa secondo le chiavi
     *
     * Una HashMap è -automaticamente- ordinata secondo le proprie chiavi
     * Viceversa una LinkedHashMap ha un -proprio ordine interno- fissato alla creazione
     *
     * @param mappaIn ingresso da ordinare
     *
     * @return mappa ordinata
     */
    public void ordinaMappa() {
        HashMap mappa;
        LinkedHashMap mappaOrdinata;
        LinkedHashMap mappaOttenuta;
        String strUno = "alfa";
        String strDue = "beta";
        String strTre = "delta";

        // mappa semplice non ordinata in creazione e che si ordina secondo le chiavi
        mappa = new HashMap();
        mappa.put(strTre, strTre);
        mappa.put(strDue, strDue);
        mappa.put(strUno, strUno);

        mappaOrdinata = new LinkedHashMap();
        mappaOrdinata.put(strUno, strUno);
        mappaOrdinata.put(strDue, strDue);
        mappaOrdinata.put(strTre, strTre);

        mappaOttenuta = LibArray.ordinaMappa(mappa);
        assert mappaOttenuta.size() == 3;
        for (Object key : mappaOttenuta.keySet()) {
            assert mappaOttenuta.get(key) == mappaOrdinata.get(key);
        }// end of for cycle

        mappa = new HashMap();
        mappa.put(3, strTre);
        mappa.put(2, strDue);
        mappa.put(1, strUno);

        mappaOrdinata = new LinkedHashMap();
        mappaOrdinata.put(1, strUno);
        mappaOrdinata.put(2, strDue);
        mappaOrdinata.put(3, strTre);

        mappaOttenuta = LibArray.ordinaMappa(mappa);
        assert mappaOttenuta.size() == 3;
        for (Object key : mappaOttenuta.keySet()) {
            assert mappaOttenuta.get(key) == mappaOrdinata.get(key);
        }// end of for cycle

    }// fine tests


    @Test
    /**
     * Ordina una mappa secondo le chiavi
     *
     * Una HashMap è -automaticamente- ordinata secondo le proprie chiavi
     * Viceversa una LinkedHashMap ha un -proprio ordine interno- fissato alla creazione
     *
     * @param mappaIn ingresso da ordinare
     *
     * @return mappa ordinata
     */
    public void ordinaMappa2() {
        HashMap mappa;
        LinkedHashMap mappaOrdinata;
        LinkedHashMap mappaOttenuta;
        String strUno = "Beta";
        String strDue = "Álvaro";
        String strTre = "Venerdi";

        // mappa semplice non ordinata in creazione e che si ordina secondo le chiavi
        mappa = new HashMap();
        mappa.put(strTre, strTre);
        mappa.put(strDue, strDue);
        mappa.put(strUno, strUno);

        mappaOrdinata = new LinkedHashMap();
        mappaOrdinata.put(strDue, strDue);
        mappaOrdinata.put(strUno, strUno);
        mappaOrdinata.put(strTre, strTre);

        mappaOttenuta = LibArray.ordinaMappaAccentiSensibile(mappa);
        assert mappaOttenuta.size() == 3;
        Set set = mappaOttenuta.keySet();
        Set set2 = mappaOrdinata.keySet();
        Object[] matrice = set.toArray();
        Object[] matrice2 = set2.toArray();
        ArrayList lista = LibArray.fromObj(matrice);
        ArrayList lista2 = LibArray.fromObj(matrice2);

        boolOttenuto = LibArray.isArrayEquals(lista, lista2);
        assertTrue(boolOttenuto);
        boolOttenuto = LibArray.isArrayEqualsOrdinati(lista, lista2);
        assertTrue(boolOttenuto);

    }// fine tests

//    /**
//     * Utility di conversione di una stringa.
//     *
//     * Crea una lista da un testo usando una stringa come separatore
//     * Di solito la stringa è sempre di 1 carattere
//     * Elementi nulli o vuoti non vengono aggiunti alla lista
//     * Vengono eliminati gli spazi vuoti iniziali e finali
//     * Se il separatore è vuoto o nullo, restituisce una lista di un elemento uguale al testo
//     * ricevuto
//     *
//     * @param testo da suddividere
//     * @param sep carattere stringa di separazione
//     *
//     * @return una lista contenente le parti di stringa separate
//     */
//    void testCreaLista() {
//        ArrayList<String> lista
//        String sep = ','
//        String sepNullo = ''
//        String strUno = ' alfa,beta,delta '
//        String strDue = 'alfa,beta,delta'
//        String strTre = 'alfa , beta , delta'
//        String strQuattro = 'alfa;beta,delta'
//        String strCinque = 'alfa,,delta'
//
//        // lista normale
//        lista = Array.creaLista(strUno, sep)
//        assert lista.size() == 3
//        assert lista.get(1) == 'beta'
//
//        lista = Array.creaLista(strDue, sep)
//        assert lista.size() == 3
//        assert lista.get(1) == 'beta'
//
//        lista = Array.creaLista(strTre, sep)
//        assert lista.size() == 3
//        assert lista.get(1) == 'beta'
//
//        // lista ridotta
//        lista = Array.creaLista(strQuattro, sep)
//        assert lista.size() == 2
//        assert lista.get(1) == 'delta'
//
//        lista = Array.creaLista(strCinque, sep)
//        assert lista.size() == 2
//        assert lista.get(1) == 'delta'
//
//        lista = Array.creaLista(strDue, sepNullo)
//        assert lista.size() == 1
//        assert lista.get(0) == strDue
//    }// fine tests
//
//    /**
//     * Utility di conversione di una stringa.
//     *
//     * Crea una lista da un testo usando una stringa come separatore
//     * Di solito la stringa è sempre di 1 carattere
//     * Elementi nulli o vuoti non vengono aggiunti alla lista
//     * Vengono eliminati gli spazi vuoti iniziali e finali
//     * Se il separatore è vuoto o nullo, restituisce una lista di un elemento uguale al testo
//     * ricevuto
//     *
//     * @param testo da suddividere
//     * @param sep carattere stringa di separazione
//     *
//     * @return una lista contenente le parti di stringa separate
//     */
//    void testCreaLista2() {
//        ArrayList<String> lista
//        String strUno = ' alfa,beta,delta '
//        String strDue = 'alfa,beta,delta'
//        String strTre = 'alfa , beta , delta'
//        String strQuattro = 'alfa;beta,delta'
//        String strCinque = 'alfa,,delta'
//
//        // lista normale
//        lista = Array.creaLista(strUno)
//        assert lista.size() == 3
//        assert lista.get(1) == 'beta'
//
//        lista = Array.creaLista(strDue)
//        assert lista.size() == 3
//        assert lista.get(1) == 'beta'
//
//        lista = Array.creaLista(strTre)
//        assert lista.size() == 3
//        assert lista.get(1) == 'beta'
//
//        // lista ridotta
//        lista = Array.creaLista(strQuattro)
//        assert lista.size() == 2
//        assert lista.get(1) == 'delta'
//
//        lista = Array.creaLista(strCinque)
//        assert lista.size() == 2
//        assert lista.get(1) == 'delta'
//    }// fine tests
//
//    /**
//     * Converte un array di stringhe in una lista di stringhe.
//     * <p/>
//     * Esegue solo se l'array non è nullo <br>
//     *
//     * @param array da convertire
//     *
//     * @return lista di stringhe contenente gli elementi dell'array
//     */
//    void testCreaLista3() {
//        String[] array = new String[3]
//        ArrayList<String> lista
//        array[0] = 'alfa'
//        array[1] = 'beta'
//        array[2] = 'delta'
//
//        lista = Array.creaLista(array)
//        assert lista.size() == 3
//        assert lista.get(1) == array[1]
//    }// fine tests
//
//    // Estrae le righe da un testo
//    // Esegue solo se il testo è valido
//    // Se arriva un oggetto non stringa, restituisce null
//    //
//    // @param testo in ingresso
//    // @return array di righe
//    void testRighe() {
//        String testo = 'primaRiga\nSeconda\nTerza'
//        def ottenuto
//
//        ottenuto = LibArray.getRigheTrim(testo)
//        assert ottenuto != null
//        assert ottenuto instanceof ArrayList
//        assert ottenuto.size() == 3
//        assert ottenuto[0] == 'primaRiga'
//        assert ottenuto[1] == 'Seconda'
//        assert ottenuto[2] == 'Terza'
//    }// fine tests
//
//    void testRighe2() {
//        String testo = '\tprimaRiga\t\n\t\tSeconda\nTerza'
//        def ottenuto
//
//        ottenuto = LibArray.getRigheTrim(testo)
//        assert ottenuto != null
//        assert ottenuto instanceof ArrayList
//        assert ottenuto.size() == 3
//        assert ottenuto[0] == 'primaRiga'
//        assert ottenuto[1] == 'Seconda'
//        assert ottenuto[2] == 'Terza'
//    }// fine tests
//
//
//    void testRighe3() {
//        String testo = '\tprimaRiga\t\n\t\tSeconda\tTerza'
//        def ottenuto
//
//        ottenuto = LibArray.getRigheTrim(testo)
//        assert ottenuto != null
//        assert ottenuto instanceof ArrayList
//        assert ottenuto.size() == 2
//        assert ottenuto[0] == 'primaRiga'
//        assert ottenuto[1] == 'Seconda\tTerza'
//    }// fine tests
//
//    void testParole() {
//        String testo = 'this is a test'
//        def ottenuto
//
//        ottenuto = LibArray.getWords(testo)
//        assert ottenuto != null
//        assert ottenuto instanceof ArrayList
//        assert ottenuto.size() == 4
//        assert ottenuto[0] == 'this'
//        assert ottenuto[1] == 'is'
//        assert ottenuto[2] == 'a'
//        assert ottenuto[3] == 'test'
//    }// fine tests
//
//
//    void testBlocchi() {
//        String testo = 'this is a test'
//        def ottenuto
//
//        ottenuto = LibArray.getBlocchi(testo, ' ')
//        assert ottenuto != null
//        assert ottenuto instanceof ArrayList
//        assert ottenuto.size() == 4
//        assert ottenuto[0] == 'this'
//        assert ottenuto[1] == 'is'
//        assert ottenuto[2] == 'a'
//        assert ottenuto[3] == 'test'
//    }// fine tests
//
//
//    void testBlocchi2() {
//        String testo = 'primaRiga\nSeconda\nTerza'
//        def ottenuto
//
//        ottenuto = LibArray.getBlocchi(testo, '\n')
//        assert ottenuto != null
//        assert ottenuto instanceof ArrayList
//        assert ottenuto.size() == 3
//        assert ottenuto[0] == 'primaRiga'
//        assert ottenuto[1] == 'Seconda'
//        assert ottenuto[2] == 'Terza'
//    }// fine tests
//
//    void testsplitArray() {
//        ArrayList listaTxt = ['uno', 'due', 'tre', 'quattro', 'cinque', 'sei', 'sette', 'otto', 'nove', 'dieci']
//        ArrayList listaNum = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
//        int dim
//        String richiesto
//        def ottenuto
//
//        dim = 3
//        ottenuto = LibArray.splitArray(listaTxt, dim)
//        assert ottenuto != null
//        assert ottenuto instanceof ArrayList
//        assert ottenuto.size() == 4
//        assert ottenuto[0] != null
//        assert ottenuto[0] instanceof ArrayList
//        assert ottenuto[0].size() == dim
//        assert ottenuto[0][2] == 'tre'
//
//        dim = 3
//        ottenuto = LibArray.splitArray(listaNum, dim)
//        assert ottenuto != null
//        assert ottenuto instanceof ArrayList
//        assert ottenuto.size() == 4
//        assert ottenuto[0] != null
//        assert ottenuto[0] instanceof ArrayList
//        assert ottenuto[0].size() == dim
//        assert ottenuto[0][2] == 3
//
//        dim = 12
//        ottenuto = LibArray.splitArray(listaTxt, dim)
//        assert ottenuto != null
//        assert ottenuto instanceof ArrayList
//        assert ottenuto.size() == 1
//        assert ottenuto[0] != null
//        assert ottenuto[0] instanceof ArrayList
//        assert ottenuto[0].size() == 10
//        assert ottenuto[0][2] == 'tre'
//
//        dim = 10
//        ottenuto = LibArray.splitArray(listaTxt, dim)
//        assert ottenuto != null
//        assert ottenuto instanceof ArrayList
//        assert ottenuto.size() == 1
//        assert ottenuto[0] != null
//        assert ottenuto[0] instanceof ArrayList
//        assert ottenuto[0].size() == 10
//        assert ottenuto[0][2] == 'tre'
//
//        dim = 4
//        ottenuto = LibArray.splitArray(listaTxt, dim)
//        assert ottenuto != null
//        assert ottenuto instanceof ArrayList
//        assert ottenuto.size() == 3
//        assert ottenuto[0] != null
//        assert ottenuto[0] instanceof ArrayList
//        assert ottenuto[0].size() == dim
//        assert ottenuto[0][2] == 'tre'
//        assert ottenuto[1].size() == dim
//        assert ottenuto[2].size() == 2
//    }// fine tests
//
//    void testCreaStringa() {
//        ArrayList lista = ['primaRiga', 'Seconda', 'Terza']
//        String richiesto = 'primaRiga|Seconda|Terza'
//        def ottenuto
//
//        ottenuto = LibArray.creaStringaPipe(lista)
//
//        assert ottenuto != null
//        assert ottenuto instanceof String
//        assert ottenuto == richiesto
//    }// fine tests
//
//    void testEstrae() {
//        ArrayList lista = ['primaRiga', 'Seconda', 'Terza']
//        def ottenuto
//
//        ottenuto = LibArray.estraArray(lista, 0)
//        assert ottenuto == null
//
//        ottenuto = LibArray.estraArray(lista, 1)
//        assert ottenuto != null
//        assert ottenuto instanceof ArrayList
//        assert ottenuto.size() == 1
//        assert ottenuto == ['primaRiga']
//
//        ottenuto = LibArray.estraArray(lista, 2)
//        assert ottenuto != null
//        assert ottenuto instanceof ArrayList
//        assert ottenuto.size() == 2
//        assert ottenuto == ['primaRiga', 'Seconda']
//
//        ottenuto = LibArray.estraArray(lista, 3)
//        assert ottenuto != null
//        assert ottenuto instanceof ArrayList
//        assert ottenuto.size() == 3
//        assert ottenuto == ['primaRiga', 'Seconda', 'Terza']
//
//        ottenuto = LibArray.estraArray(lista, 4)
//        assert ottenuto != null
//        assert ottenuto instanceof ArrayList
//        assert ottenuto.size() == 3
//        assert ottenuto == ['primaRiga', 'Seconda', 'Terza']
//    }// fine tests
//
//
//    void testEstraeTempi() {
//        ArrayList lista = new ArrayList()
//        def ottenuto
//        int dim = 100000
//        long inizio
//        long fine
//        long trascorso
//        long secondi
//
//        for (int k = 0; k < dim; k++) {
//            lista.add(k)
//        } // fine del ciclo for
//
//        inizio = System.currentTimeMillis()
//        ottenuto = LibArray.estraArray(lista, 50000)
//        assert ottenuto != null
//
//        fine = System.currentTimeMillis()
//        trascorso = fine - inizio
//        secondi = trascorso / 1000
//        println('Secondi trascorsi: ' + secondi)
//    }// fine tests
//
//    void testDifferenzeTempi() {
//        ArrayList listaA = new ArrayList()
//        ArrayList listaB = new ArrayList()
//        def ottenuto
//        int dim = 10000
//        int meta = dim / 2
//        long inizio
//        long fine
//        long trascorso
//        long secondi
//
//        for (int k = 0; k < dim; k++) {
//            listaA.add(k)
//        } // fine del ciclo for
//
//        for (int k = meta; k < dim; k++) {
//            listaB.add(k)
//        } // fine del ciclo for
//
//        inizio = System.currentTimeMillis()
//        ottenuto = LibArray.differenza(listaA, listaB)
//        assert ottenuto != null
//        assert ottenuto instanceof ArrayList
//        assert ottenuto.size() == meta
//
//        fine = System.currentTimeMillis()
//        trascorso = fine - inizio
//        secondi = trascorso / 1000
//        println('Long trascorsi metodo interno: ' + trascorso)
//
//        inizio = System.currentTimeMillis()
//        ottenuto = listaA - listaB
//        assert ottenuto != null
//        assert ottenuto instanceof ArrayList
//        assert ottenuto.size() == meta
//
//        fine = System.currentTimeMillis()
//        trascorso = fine - inizio
//        secondi = trascorso / 1000
//        println('Long trascorsi metodo java: ' + trascorso)
//    }// fine tests
}// end of testing class