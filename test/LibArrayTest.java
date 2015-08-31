import it.algos.webbase.web.lib.LibArray;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Gac on 24 ago 2015.
 * .
 */
public class LibArrayTest {

    private static int SIZE = 7;
    private static int NEW_SIZE = 8;
    private static int SIZE_UNICI = 3;
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
    private String[] valoriDoppiTxt = {terzo, primoTxt, secondo, secondo, primoTxt, terzo, secondo};
    @SuppressWarnings("all")
    private int[] valoriDoppiNum = {due, tre, primoInt, due, primoInt, due, primoInt};
    @SuppressWarnings("all")
    private long[] valoriDoppiLong = {primoLungo, primoInt, due, primoInt, due, primoInt};
    @SuppressWarnings("all")
    private Object[] valoriDoppiObj = {primoTxt, primoLungo, primoTxt, primoInt, primoInt, primoTxt};

    private List ottenuto;
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
        List<String> arrayPrimo = LibArray.fromString(stringArray);
        List<String> arraySecondo = LibArray.fromString(valoriDoppiTxt);

        ottenuto = LibArray.sommaDisordinata(null, null);
        assertNull(ottenuto);

        ottenuto = LibArray.sommaDisordinata(null, arraySecondo);
        assertEquals(ottenuto.size(), SIZE_UNICI);

        ottenuto = LibArray.sommaDisordinata(arrayPrimo, null);
        assertEquals(ottenuto.size(), SIZE);

        ottenuto = LibArray.sommaDisordinata(arrayPrimo, arraySecondo);
        assertEquals(ottenuto.size(), size);

        System.out.println("");
        System.out.println("Somma");
        k = 0;
        for (Object obj : ottenuto) {
            System.out.println(++k + ") " + obj);
        } // fine del ciclo for-each
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
        List<String> arrayPrimo = LibArray.fromString(stringArray);
        List<String> arraySecondo = LibArray.fromString(valoriDoppiTxt);

        ottenuto = LibArray.somma(null, null);
        assertNull(ottenuto);

        ottenuto = LibArray.somma(null, arraySecondo);
        assertEquals(ottenuto.size(), SIZE_UNICI);

        ottenuto = LibArray.somma(arrayPrimo, null);
        assertEquals(ottenuto.size(), SIZE);

        ottenuto = LibArray.somma(arrayPrimo, arraySecondo);
        assertEquals(ottenuto.size(), size);

        System.out.println("");
        System.out.println("Somma ordinata");
        k = 0;
        for (Object obj : ottenuto) {
            System.out.println(++k + ") " + obj);
        } // fine del ciclo for-each
    } // fine del test


//    /**
//     * Somma due array (liste)
//     *
//     * Almeno uno dei due array in ingresso deve essere non nullo
//     * I valori negli array sono unici
//     * Normalmente si usa di meno la somma disordinata
//     *
//     * Se entrambi i parametri sono liste della stessa classe, restituisce la somma
//     * Se entrambi i parametri sono liste ma di classe diversa, restituisce un nullo
//     * Se entrambi i parametri sono nulli, restituisce un nullo
//     * Se uno dei parametri è nullo e l'altro è una lista, restituisce la lista
//     * Se uno dei parametri è nullo e l'altro non è una lista, restituisce un nullo
//     * Se uno dei parametri è una lista, l'altro non è una lista ma è della stessa classe, restituisce la somma
//     * Se uno dei parametri è una lista, l'altro non è una lista ma non è della stessa classe, restituisce un nullo
//     *
//     * @param arrayPrimo
//     * @param arraySecondo
//     * @return arraySomma disordinato
//     */
//    void testSommaDisordinata() {
//        def primoNum = [7, 87, 4, 25, 1, 12]
//        def secondoNum = [11, 7, 55, 4]
//        def primoStr = ['due', 'otto', 'alfa']
//        def secondoStr = ['otto', 'gamma', 'due']
//        int num = 999
//        String stringa = 'ultima'
//        def ottenuto
//        def richiestoNum = [7, 87, 4, 25, 1, 12, 11, 55]
//        def richiestoStr = ['due', 'otto', 'alfa', 'gamma']
//        int numRichiesto = 8
//        int strRichiesto = 4
//        int primoRichiesto = 7
//
//        // entrambi i parametri sono nulli, restituisce un nullo
//        ottenuto = Array.sommaDisordinata(null, null)
//        assert ottenuto == null
//
//        // uno dei parametri è nullo e l'altro è una lista, restituisce la lista
//        ottenuto = Array.sommaDisordinata(primoNum, null)
//        assert ottenuto instanceof List
//        assert ottenuto == primoNum
//
//        // uno dei parametri è nullo e l'altro è una lista, restituisce la lista
//        ottenuto = Array.sommaDisordinata(null, secondoNum)
//        assert ottenuto instanceof List
//        assert ottenuto == secondoNum
//
//        // entrambi i parametri sono liste della stessa classe, restituisce la somma
//        ottenuto = Array.sommaDisordinata(primoNum, secondoNum)
//        assert ottenuto instanceof List
//        assert ottenuto.size() == numRichiesto
//        assert ottenuto == richiestoNum
//
//        // entrambi i parametri sono liste della stessa classe, restituisce la somma
//        ottenuto = Array.sommaDisordinata(primoStr, secondoStr)
//        assert ottenuto instanceof List
//        assert ottenuto.size() == strRichiesto
//        assert ottenuto == richiestoStr
//
//        // entrambi i parametri sono liste ma di classe diversa, restituisce un nullo
//        ottenuto = Array.sommaDisordinata(primoNum, secondoStr)
//        assert ottenuto == null
//
//        // entrambi i parametri sono liste ma di classe diversa, restituisce un nullo
//        ottenuto = Array.sommaDisordinata(primoStr, secondoNum)
//        assert ottenuto == null
//
//        // uno dei parametri è una lista, l'altro non è una lista ma è della stessa classe, restituisce la somma
//        ottenuto = Array.sommaDisordinata(primoNum, num)
//        assert ottenuto instanceof List
//        assert ottenuto.size() == primoRichiesto
//
//        // uno dei parametri è una lista, l'altro non è una lista ma è della stessa classe, restituisce la somma
//        ottenuto = Array.sommaDisordinata(primoStr, stringa)
//        assert ottenuto instanceof List
//        assert ottenuto.size() == strRichiesto
//
//        // uno dei parametri è una lista, l'altro non è una lista ma non è della stessa classe, restituisce un nullo
//        ottenuto = Array.sommaDisordinata(primoNum, stringa)
//        assert ottenuto == null
//
//        // uno dei parametri è una lista, l'altro non è una lista ma non è della stessa classe, restituisce un nullo
//        ottenuto = Array.sommaDisordinata(primoStr, num)
//        assert ottenuto == null
//    }// fine tests
//
//    /**
//     * Somma due array (liste) e restituisce una lista ordinata
//     *
//     * Almeno uno dei due array in ingresso deve essere non nullo
//     * I valori nel array risultante, sono unici
//     * Normalmente si usa di più la somma ordinata
//     *
//     * Se entrambi i parametri sono liste della stessa classe, restituisce la somma ordinata
//     * Se entrambi i parametri sono liste ma di classe diversas, restituisce un nullo
//     * Se entrambi i parametri sono nulli, restituisce un nullo
//     * Se uno dei parametri è nullo e l'altro è una lista, restituisce la lista ordinata
//     * Se uno dei parametri è nullo e l'altro non è una lista, restituisce un nullo
//     * Se uno dei parametri è una lista, l'altro non è una lista ma è della stessa classe, restituisce la somma ordinata
//     * Se uno dei parametri è una lista, l'altro non è una lista ma non è della stessa classe, restituisce un nullo
//     *
//     * @param arrayPrimo
//     * @param arraySecondo
//     * @return arraySomma ordinato
//     */
//    void testSomma() {
//        def primoNum = [7, 87, 4, 25, 1, 12]
//        def primoNumOrd = [1, 4, 7, 12, 25, 87]
//        def secondoNum = [11, 7, 55, 4]
//        def secondoNumOrd = [4, 7, 11, 55]
//        def richiestoNum = [1, 4, 7, 11, 12, 25, 55, 87]
//        def primoStr = ['due', 'otto', 'alfa']
//        def secondoStr = ['otto', 'gamma', 'due']
//        def richiestoStr = ['alfa', 'due', 'gamma', 'otto']
//        int num = 999
//        def richiestoPrimoPiuNum = [1, 4, 7, 12, 25, 87, 999]
//        String stringa = 'beta'
//        def richiestoPrimoPiuStr = ['alfa', 'beta', 'due', 'otto']
//        def ottenuto
//
//        // entrambi i parametri sono nulli, restituisce un nullo
//        ottenuto = Array.somma(null, null)
//        assert ottenuto == null
//
//        // uno dei parametri è nullo e l'altro è una lista, restituisce la lista ordinata
//        ottenuto = Array.somma(primoNum, null)
//        assert ottenuto instanceof List
//        assert ottenuto == primoNumOrd
//
//        // uno dei parametri è nullo e l'altro è una lista, restituisce la lista ordinata
//        ottenuto = Array.somma(null, secondoNum)
//        assert ottenuto instanceof List
//        assert ottenuto == secondoNumOrd
//
//        // entrambi i parametri sono liste della stessa classe, restituisce la somma ordinata
//        ottenuto = Array.somma(primoNum, secondoNum)
//        assert ottenuto instanceof List
//        assert ottenuto == richiestoNum
//
//        // entrambi i parametri sono liste della stessa classe, restituisce la somma ordinata
//        ottenuto = Array.somma(primoStr, secondoStr)
//        assert ottenuto instanceof List
//        assert ottenuto == richiestoStr
//
//        // entrambi i parametri sono liste ma di classe diversa, restituisce un nullo
//        ottenuto = Array.somma(primoNum, secondoStr)
//        assert ottenuto == null
//
//        // entrambi i parametri sono liste ma di classe diversa, restituisce un nullo
//        ottenuto = Array.somma(primoStr, secondoNum)
//        assert ottenuto == null
//
//        // uno dei parametri è una lista, l'altro non è una lista ma è della stessa classe, restituisce la somma ordinata
//        ottenuto = Array.somma(primoNum, num)
//        assert ottenuto instanceof List
//        assert ottenuto == richiestoPrimoPiuNum
//
//        // uno dei parametri è una lista, l'altro non è una lista ma è della stessa classe, restituisce la somma ordinata
//        ottenuto = Array.somma(primoStr, stringa)
//        assert ottenuto instanceof List
//        assert ottenuto == richiestoPrimoPiuStr
//
//        // uno dei parametri è una lista, l'altro non è una lista ma non è della stessa classe, restituisce un nullo
//        ottenuto = Array.somma(primoNum, stringa)
//        assert ottenuto == null
//
//        // uno dei parametri è una lista, l'altro non è una lista ma non è della stessa classe, restituisce un nullo
//        ottenuto = Array.somma(primoStr, num)
//        assert ottenuto == null
//    }// fine tests
//
//    /**
//     * Differenza tra due array (liste) e restituisce una lista
//     *
//     * Il primo array in ingresso deve essere non nullo e deve essere una lista
//     * I valori negli array sono unici
//     * Normalmente si usa di meno la differenza disordinata
//     *
//     * Se entrambi i parametri sono liste della stessa classe, restituisce la differenza
//     * Se entrambi i parametri sono liste ma di classe diversa, restituisce un nullo
//     * Se il primo parametro è nullo, restituisce un nullo
//     * Se il primo parametro non è una lista, restituisce un nullo
//     * Se entrambi i parametri sono nulli, restituisce un nullo
//     * Se il secondo parametro è nullo, oppure una lista vuota (zero elementi), restituisce il primo array
//     * Se il secondo parametro non è una lista, ma è della stessa classe del primo, restituisce la differenza
//     * Se il secondo parametro non è una lista, ed è di classe diversa dal primo, restituisce un nullo
//     *
//     * @param arrayPrimo
//     * @param arraySecondo
//     * @return arrayDifferenza disordinata
//     */
//    void testDifferenzaDisordinata() {
//        def primoNum = [7, 87, 4, 25, 1, 12]
//        def secondoNum = [11, 7, 55, 4]
//        def richiestoNum = [87, 25, 1, 12]
//        def arrayVuoto = []
//        def primoStr = ['due', 'otto', 'beta', 'alfa', 'omicron']
//        def secondoStr = ['otto', 'gamma', 'due']
//        def richiestoStr = ['beta', 'alfa', 'omicron']
//        int num = 4
//        def richiestoPrimoMenoNum = [7, 87, 25, 1, 12]
//        String stringa = 'beta'
//        def richiestoPrimoMenoStr = ['due', 'otto', 'alfa', 'omicron']
//        def ottenuto
//
//        // entrambi i parametri sono nulli, restituisce un nullo
//        ottenuto = Array.differenzaDisordinata(null, null)
//        assert ottenuto == null
//
//        // il primo parametro è nullo, restituisce un nullo
//        ottenuto = Array.differenzaDisordinata(null, secondoNum)
//        assert ottenuto == null
//
//        // il primo parametro non è una lista, restituisce un nullo
//        ottenuto = Array.differenzaDisordinata(num, secondoNum)
//        assert ottenuto == null
//
//        // il primo parametro non è una lista, restituisce un nullo
//        ottenuto = Array.differenzaDisordinata(stringa, secondoStr)
//        assert ottenuto == null
//
//        // entrambi i parametri sono liste ma di classe diversa, restituisce un nullo
//        ottenuto = Array.differenzaDisordinata(primoNum, secondoStr)
//        assert ottenuto == null
//
//        // entrambi i parametri sono liste ma di classe diversa, restituisce un nullo
//        ottenuto = Array.differenzaDisordinata(primoStr, secondoNum)
//        assert ottenuto == null
//
//        // entrambi i parametri sono liste della stessa classe, restituisce la differenza
//        ottenuto = Array.differenzaDisordinata(primoNum, secondoNum)
//        assert ottenuto instanceof List
//        assert ottenuto == richiestoNum
//
//        // entrambi i parametri sono liste della stessa classe, restituisce la differenza
//        ottenuto = Array.differenzaDisordinata(primoStr, secondoStr)
//        assert ottenuto instanceof List
//        assert ottenuto == richiestoStr
//
//        // il secondo parametro è nullo, restituisce la differenza,restituisce il primo array
//        ottenuto = Array.differenzaDisordinata(primoNum, null)
//        assert ottenuto instanceof List
//        assert ottenuto == primoNum
//
//        // il secondo parametro è una lista vuota (zero elementi), restituisce il primo array
//        ottenuto = Array.differenzaDisordinata(primoNum, arrayVuoto)
//        assert ottenuto instanceof List
//        assert ottenuto == primoNum
//
//        // il secondo parametro non è una lista, ma è della stessa classe del primo, restituisce la differenza
//        ottenuto = Array.differenzaDisordinata(primoNum, num)
//        assert ottenuto instanceof List
//        assert ottenuto == richiestoPrimoMenoNum
//
//        // il secondo parametro non è una lista, ma è della stessa classe del primo, restituisce la differenza
//        ottenuto = Array.differenzaDisordinata(primoStr, stringa)
//        assert ottenuto instanceof List
//        assert ottenuto == richiestoPrimoMenoStr
//
//        // il secondo parametro non è una lista, ed è di classe diversa dal primo, restituisce un nullo
//        ottenuto = Array.differenzaDisordinata(primoNum, stringa)
//        assert ottenuto == null
//
//        // il secondo parametro non è una lista, ed è di classe diversa dal primo, restituisce un nullo
//        ottenuto = Array.differenzaDisordinata(primoStr, num)
//        assert ottenuto == null
//    }// fine tests
//
//    /**
//     * Differenza tra due array (liste) e restituisce una lista ordinata
//     *
//     * Il primo array in ingresso deve essere non nullo e deve essere una lista
//     * I valori negli array sono unici
//     * Normalmente si usa di più la differenza ordinata
//     *
//     * Se entrambi i parametri sono liste della stessa classe, restituisce la differenza ordinata
//     * Se entrambi i parametri sono liste ma di classe diversa, restituisce un nullo
//     * Se il primo parametro è nullo, restituisce un nullo
//     * Se il primo parametro non è una lista, restituisce un nullo
//     * Se entrambi i parametri sono nulli, restituisce un nullo
//     * Se il secondo parametro non è una lista, ma è della stessa classe del primo, restituisce la differenza ordinata
//     * Se il secondo parametro non è una lista, ed è di classe diversa dal primo, restituisce un nullo
//     *
//     * @param arrayPrimo
//     * @param arraySecondo
//     * @return arrayDifferenza ordinata
//     */
//    void testDifferenza() {
//        def primoNum = [7, 87, 4, 25, 1, 12]
//        def secondoNum = [11, 7, 55, 4]
//        def richiestoNum = [1, 12, 25, 87]
//        def primoStr = ['due', 'otto', 'beta', 'alfa', 'omicron']
//        def secondoStr = ['otto', 'gamma', 'due']
//        def richiestoStr = ['alfa', 'beta', 'omicron']
//        int num = 4
//        def richiestoPrimoMenoNum = [1, 7, 12, 25, 87]
//        String stringa = 'beta'
//        def richiestoPrimoMenoStr = ['alfa', 'due', 'omicron', 'otto']
//        def ottenuto
//
//        // entrambi i parametri sono nulli, restituisce un nullo
//        ottenuto = Array.differenza(null, null)
//        assert ottenuto == null
//
//        // il primo parametro è nullo, restituisce un nullo
//        ottenuto = Array.differenza(null, secondoNum)
//        assert ottenuto == null
//
//        // il primo parametro non è una lista, restituisce un nullo
//        ottenuto = Array.differenza(num, secondoNum)
//        assert ottenuto == null
//
//        // il primo parametro non è una lista, restituisce un nullo
//        ottenuto = Array.differenza(stringa, secondoStr)
//        assert ottenuto == null
//
//        // entrambi i parametri sono liste ma di classe diversa, restituisce un nullo
//        ottenuto = Array.differenza(primoNum, secondoStr)
//        assert ottenuto == null
//
//        // entrambi i parametri sono liste ma di classe diversa, restituisce un nullo
//        ottenuto = Array.differenza(primoStr, secondoNum)
//        assert ottenuto == null
//
//        // entrambi i parametri sono liste della stessa classe, restituisce la differenza
//        ottenuto = Array.differenza(primoNum, secondoNum)
//        assert ottenuto instanceof List
//        assert ottenuto == richiestoNum
//
//        // entrambi i parametri sono liste della stessa classe, restituisce la differenza
//        ottenuto = Array.differenza(primoStr, secondoStr)
//        assert ottenuto instanceof List
//        assert ottenuto == richiestoStr
//
//        // il secondo parametro non è una lista, ma è della stessa classe del primo, restituisce la differenza
//        ottenuto = Array.differenza(primoNum, num)
//        assert ottenuto instanceof List
//        assert ottenuto == richiestoPrimoMenoNum
//
//        // il secondo parametro non è una lista, ma è della stessa classe del primo, restituisce la differenza
//        ottenuto = Array.differenza(primoStr, stringa)
//        assert ottenuto instanceof List
//        assert ottenuto == richiestoPrimoMenoStr
//
//        // il secondo parametro non è una lista, ed è di classe diversa dal primo, restituisce un nullo
//        ottenuto = Array.differenza(primoNum, stringa)
//        assert ottenuto == null
//
//        // il secondo parametro non è una lista, ed è di classe diversa dal primo, restituisce un nullo
//        ottenuto = Array.differenza(primoStr, num)
//        assert ottenuto == null
//    }// fine tests
//
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
//
//    /**
//     * Ordina una mappa secondo le chiavi
//     *
//     * Una HashMap è -automaticamente- ordinata secondo le proprie chiavi
//     * Viceversa una LinkedHashMap ha un -proprio ordine interno- fissato alla creazione
//     *
//     * @param mappaIn ingresso da ordinare
//     *
//     * @return mappa ordinata
//     */
//    void testOrdina() {
//        HashMap mappa = new HashMap()
//        LinkedHashMap mappaOrdinata = new LinkedHashMap()
//        LinkedHashMap ottenuta
//        Set insieme
//        List lista
//        String strUno = 'alfa'
//        String strDue = 'beta'
//        String strTre = 'delta'
//
//        // mappa semplice non ordinata in creazione e che si ordina secondo le chiavi
//        mappa.put(strTre, null)
//        mappa.put(strDue, null)
//        mappa.put(strUno, null)
//
//        ottenuta = Array.ordinaMappa(mappa)
//        assert ottenuta.size() == 3
//        insieme = ottenuta.keySet()
//        lista = insieme.asList()
//        assert lista.get(1) == strDue
//
//        // mappa  ordinata
//        mappaOrdinata.put(strTre, null)
//        mappaOrdinata.put(strDue, null)
//        mappaOrdinata.put(strUno, null)
//
//        ottenuta = Array.ordinaMappa(mappa)
//        assert ottenuta.size() == 3
//        insieme = ottenuta.keySet()
//        lista = insieme.asList()
//        assert lista.get(1) == strDue
//
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