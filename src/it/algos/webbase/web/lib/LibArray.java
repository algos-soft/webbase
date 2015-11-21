package it.algos.webbase.web.lib;

import java.util.*;


/**
 * Created by Gac on 24 ago 2015.
 * Classe statica di libreria
 * <p>
 * 1) First and Major difference between Array and ArrayList in Java is that Array is a fixed length data structure
 * while ArrayList is a variable length Collection class.
 * You can not change length of Array once created in Java but ArrayList re-size itself when gets full depending upon capacity
 * and load factor. Since ArrayList is internally backed by Array in Java, any resize operation in ArrayList will slow down performance
 * as it involves creating new Array and copying content from old array to new array.
 * 2) Another difference between Array and ArrayList in Java is that you can not use Generics along with Array,
 * as Array instance knows about what kind of type it can hold and throws ArrayStoreException,
 * if you try to store type which is not convertible into type of Array. ArrayList allows you to use Generics to ensure type-safety.
 * 3) You can also compare Array vs ArrayList on How to calculate length of Array or size of ArrayList.
 * All kinds of Array provides length variable which denotes length of Array while ArrayList provides size()
 * method to calculate size of ArrayList in Java.
 * 4) One more major difference between ArrayList and Array is that, you can not store primitives in ArrayList, it can only contain Objects.
 * While Array can contain both primitives and Objects in Java. Though Autoboxing of Java 5 may give you an impression
 * of storing primitives in ArrayList, it actually automatically converts primitives to Object. e.g.
 * <p>
 * Resizable :   Array is static in size that is fixed length data structure, One can not change the length after creating the Array object.
 * Performance : Performance of Array and ArrayList depends on the operation you are performing :
 * Primitives :  ArrayList can not contains primitive data types (like int , float , double) it can only contains Object while
 * Array can contain both primitive data types as well as objects.
 * Iterating the values : We can use iterator  to iterate through ArrayList
 * Type-Safety :  In Java , one can ensure Type Safety through Generics. while Array is a homogeneous data structure ,
 * thus it will contain objects of specific class or primitives of specific  data type.
 * Length :  Length of the ArrayList is provided by the size()
 * Adding elements : We can insert elements into the arraylist object using the add() method while
 * in array we insert elements using the assignment operator.
 * Multi-dimensional :  Array can be multi dimensional , while ArrayList is always single dimensional.
 */
public abstract class LibArray {

    /**
     * Convert a stringArray to ArrayList
     *
     * @param stringArray to convert
     * @return the corresponding casted ArrayList
     */
    public static ArrayList<String> fromString(String[] stringArray) {
        return new ArrayList<String>(Arrays.asList(stringArray));
    } // end of static method

    /**
     * Convert a intArray to ArrayList
     *
     * @param intArray to convert
     * @return the corresponding casted ArrayList
     */
    public static ArrayList<Integer> fromInt(int[] intArray) {
        ArrayList<Integer> intList = new ArrayList<Integer>();

        for (Integer intero : intArray) {
            intList.add(intero);
        } // fine del ciclo for-each

        return intList;
    } // end of static method

    /**
     * Convert a longArray to ArrayList
     *
     * @param longArray to convert
     * @return the corresponding casted ArrayList
     */
    public static ArrayList<Long> fromLong(long[] longArray) {
        ArrayList<Long> longList = new ArrayList<Long>();

        for (Long lungo : longArray) {
            longList.add(lungo);
        } // fine del ciclo for-each

        return longList;
    } // end of static method


    /**
     * Convert a objArray to ArrayList
     *
     * @param objArray to convert
     * @return the corresponding casted ArrayList
     */
    public static ArrayList<Object> fromObj(Object[] objArray) {
        ArrayList<Object> objList = new ArrayList<Object>();

        for (Object lungo : objArray) {
            objList.add(lungo);
        } // fine del ciclo for-each

        return objList;
    } // end of static method


    /**
     * Estrae i valori unici da un lista con (eventuali) valori doppi
     *
     * @param listaValoriDoppi in ingresso
     * @return valoriUnici NON ordinati, null se listaValoriDoppi è null
     */
    public static ArrayList valoriUniciDisordinati(ArrayList listaValoriDoppi) {
        return valoriUniciBase(listaValoriDoppi, false);
    }// end of static method


    /**
     * Estrae i valori unici da un matrice (objArray) con (eventuali) valori doppi
     *
     * @param objArray in ingresso
     * @return valoriUnici NON ordinati, null se objArray è null
     */
    public static ArrayList valoriUniciDisordinati(Object[] objArray) {
        return valoriUniciDisordinati(fromObj(objArray));
    }// end of static method


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
    public static ArrayList valoriUnici(ArrayList listaValoriDoppi) {
        return valoriUniciBase(listaValoriDoppi, true);
    }// end of static method


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
    public static ArrayList valoriUnici(Object[] objArray) {
        return valoriUnici(fromObj(objArray));
    }// end of static method

    /**
     * Estrae i valori unici da un matrice (objArray) con (eventuali) valori doppi
     * Ordina l'array secondo la classe utilizzata:
     * alfabetico per le stringhe
     * numerico per i numeri
     * L'ordinamento funziona SOLO se la lista è omogenea (oggetti della stessa classe)
     *
     * @param intArray in ingresso
     * @return valoriUnici ORDINATI, null se listaValoriDoppi è null
     */
    public static ArrayList valoriUnici(int[] intArray) {
        return valoriUnici(fromInt(intArray));
    }// end of static method

    /**
     * Estrae i valori unici da un matrice (objArray) con (eventuali) valori doppi
     * Ordina l'array secondo la classe utilizzata:
     * alfabetico per le stringhe
     * numerico per i numeri
     * L'ordinamento funziona SOLO se la lista è omogenea (oggetti della stessa classe)
     *
     * @param longArray in ingresso
     * @return valoriUnici ORDINATI, null se listaValoriDoppi è null
     */
    public static ArrayList valoriUnici(long[] longArray) {
        return valoriUnici(fromLong(longArray));
    }// end of static method


    /**
     * Estrae i valori unici da un lista con (eventuali) valori doppi
     * Eventualmente (tag booleano) ordina l'array secondo la classe utilizzata:
     * alfabetico per le stringhe
     * numerico per i numeri
     *
     * @param listaValoriDoppi in ingresso
     * @param ordina           tag per forzare l'ordinamento
     * @return valoriUnici disordinati oppure ordinati, null se listaValoriDoppi è null
     */
    @SuppressWarnings("all")
    private static ArrayList valoriUniciBase(ArrayList listaValoriDoppi, boolean ordina) {
        ArrayList listaValoriUniciNonOrdinati = null;
        Set set;

        if (listaValoriDoppi != null) {
            set = new LinkedHashSet((List) listaValoriDoppi);
            listaValoriUniciNonOrdinati = new ArrayList(set);
            if (ordina) {
                return sort(listaValoriUniciNonOrdinati);
            } else {
                return listaValoriUniciNonOrdinati;
            }// fine del blocco if-else
        }// fine del blocco if

        return null;
    }// end of static method

    /**
     * Ordina la lista
     * L'ordinamento funziona SOLO se la lista è omogenea (oggetti della stessa classe)
     *
     * @param listaDisordinata in ingresso
     * @return lista ordinata, null se listaDisordinata è null
     */
    public static ArrayList sort(ArrayList listaDisordinata) {
        ArrayList<Object> objList;
        Object[] objArray = listaDisordinata.toArray();

        try { // prova ad eseguire il codice
            Arrays.sort(objArray);
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch
        objList = fromObj(objArray);

        return objList;
    }// end of static method


    /**
     * Aggiunge un elemento alla lista solo se non già esistente
     *
     * @param lista    che viene modificata
     * @param elemento da inserire, se manca
     * @return vero se l'elemento è stato aggiunto
     */
    @SuppressWarnings("all")
    public static boolean add(ArrayList lista, Object elemento) {
        boolean aggiunto = false;

        if (!lista.contains(elemento)) {
            aggiunto = lista.add(elemento);
        }// fine del blocco if

        return aggiunto;
    }// end of static method

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
    public static ArrayList sommaDisordinata(ArrayList arrayPrimo, ArrayList arraySecondo) {
        ArrayList arraySomma = null;

        if (arrayPrimo != null && arraySecondo != null) {
            for (Object ogg : arraySecondo) {
                arrayPrimo.add(ogg);
            } // fine del ciclo for-each
            arrayPrimo= valoriUniciBase(arrayPrimo,false);
            return arrayPrimo;
        }// fine del blocco if

        if (arrayPrimo == null ) {
            arraySecondo= valoriUniciBase(arraySecondo,false);
            return arraySecondo;
        }// fine del blocco if

        if (arraySecondo == null ) {
            arrayPrimo= valoriUniciBase(arrayPrimo,false);
            return arrayPrimo;
        }// fine del blocco if

//        if (arrayPrimo != null || arraySecondo != null) {
//            arraySomma = new ArrayList();
//        }// fine del blocco if
//
//        if (arrayPrimo != null) {
//            for (Object ogg : arrayPrimo) {
//                add(arraySomma, ogg);
//            } // fine del ciclo for-each
//        }// fine del blocco if
//
//        if (arraySecondo != null) {
//            for (Object ogg : arraySecondo) {
//                add(arraySomma, ogg);
//            } // fine del ciclo for-each
//        }// fine del blocco if

        return arraySomma;
    }// end of static method


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
    public static ArrayList somma(ArrayList arrayPrimo, ArrayList arraySecondo) {
        ArrayList arraySomma = sommaDisordinata(arrayPrimo, arraySecondo);

        if (arraySomma != null) {
            arraySomma = sort(arraySomma);
        }// fine del blocco if

        return arraySomma;
    }// end of static method


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
    @SuppressWarnings("all")
    public static ArrayList differenzaDisordinata(ArrayList arrayPrimo, ArrayList arraySecondo) {
        ArrayList arrayDifferenza = null;
        if (arraySecondo == null) {
            return arrayPrimo;
        }// fine del blocco if

        if (arraySecondo.size() == 0) {
            return arrayPrimo;
        }// fine del blocco if

        if (arrayPrimo != null) {
            if (arrayPrimo.get(0).getClass() == arraySecondo.get(0).getClass()) {
                arrayDifferenza = new ArrayList();
                for (Object obj : arrayPrimo) {
                    if (!arraySecondo.contains(obj)) {
                        arrayDifferenza.add(obj);
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if
        }// fine del blocco if

        return arrayDifferenza;
    }// end of static method

    /**
     * Costruisce una stringa con i singoli valori divisi da un pipe
     * <p>
     *
     * @param array lista di valori
     * @return stringa con i singoli valori divisi da un separatore
     */
    public static String toStringaPipe(ArrayList array) {
        return toStringa(array, "|");
    }// end of static method

    /**
     * Costruisce una stringa con i singoli valori divisi da una virgola
     * <p>
     *
     * @param array lista di valori
     * @return stringa con i singoli valori divisi da un separatore
     */
    public static String toStringaVirgola(ArrayList array) {
        return toStringa(array, ",");
    }// end of static method

    /**
     * Costruisce una stringa con i singoli valori divisi da un separatore
     * <p>
     *
     * @param array lista di valori
     * @param sep   carattere separatore
     * @return stringa con i singoli valori divisi da un separatore
     */
    public static String toStringa(List array, String sep) {
        String testo;
        StringBuilder textBuffer = new StringBuilder();

        for (Object obj : array) {
            textBuffer.append(obj.toString());
            textBuffer.append(sep);
        } // fine del ciclo for-each
        testo = textBuffer.toString();
        testo = LibText.levaCoda(testo, sep);

        return testo;
    }// end of static method

    /**
     * Costruisce una stringa con i singoli valori divisi da un pipe
     * <p>
     *
     * @param stringArray to convert
     * @return stringa con i singoli valori divisi da un separatore
     */
    public static String fromStringToStringaPipe(String[] stringArray) {
        return fromStringToStringa(stringArray, "|");
    }// end of static method

    /**
     * Costruisce una stringa con i singoli valori divisi da una virgola
     * <p>
     *
     * @param stringArray to convert
     * @return stringa con i singoli valori divisi da un separatore
     */
    public static String fromStringToStringaVirgola(String[] stringArray) {
        return fromStringToStringa(stringArray, ",");
    }// end of static method

    /**
     * Costruisce una stringa con i singoli valori divisi da un separatore
     * <p>
     *
     * @param stringArray to convert
     * @param sep         carattere separatore
     * @return stringa con i singoli valori divisi da un separatore
     */
    public static String fromStringToStringa(String[] stringArray, String sep) {
        List array = LibArray.fromString(stringArray);
        return toStringa(array, sep);
    }// end of static method

    /**
     * Recupera una lista delle chiavi di una mappa
     *
     * @param mappa in ingresso
     * @return lista delle chiavi
     */
    public static ArrayList getKeyFromMap(HashMap mappa) {
        ArrayList listaKeys = null;

        if (mappa != null) {
            listaKeys = new ArrayList();
            for (Object obj : mappa.keySet()) {
                listaKeys.add(obj);
            }// end of for cycle
        }// end of if cycle

        return listaKeys;
    }// end of static method

    /**
     * Recupera una lista dei valori di una mappa
     *
     * @param mappa in ingresso
     * @return lista delle chiavi
     */
    public static ArrayList getValueFromMap(HashMap mappa) {
        ArrayList listaKeys = null;

        if (mappa != null) {
            listaKeys = new ArrayList();
            for (Object obj : mappa.keySet()) {
                listaKeys.add(mappa.get(obj));
            }// end of for cycle
        }// end of if cycle

        return listaKeys;
    }// end of static method

    /**
     * Controlla l'eguaglianza di due array
     * <p>
     * Confronta tutti i valori, INDIPENDENTEMENTE dall'ordine in cui li trova
     *
     * @param expected previsto
     * @param actual   effettivo
     * @return vero, se gli array sono lunghi uguali ed hanno gli stessi valori (disordinati)
     */
    public static boolean isArrayEquals(ArrayList expected, ArrayList actual) {
        boolean uguali = true;

        if (expected == null || actual == null) {
            return false;
        }// end of if cycle

        if (expected.size() != actual.size()) {
            return false;
        }// end of if cycle

        for (Object obj : expected) {
            if (!actual.contains(obj)) {
                uguali = false;
            }// end of if cycle
        }// end of for cycle

        return uguali;
    }// end of static method

    /**
     * Controlla l'eguaglianza di due mappe
     * <p>
     * Confronta la lunghezza
     * Confronta tutte le chiavi
     * Confronta tutti i valori
     *
     * @param expected prevista
     * @param actual   effettiva
     * @return vero, se le mappe sono lunghe uguali, hanno le stesse chiavi e gli stessi valori
     */
    public static boolean isMapEquals(HashMap expected, HashMap actual) {
        boolean uguali = true;
        boolean continua = true;
        ArrayList keysExpected;
        ArrayList keysActual;
        ArrayList valueExpected;
        ArrayList valueActual;

        if (expected == null || actual == null) {
            return false;
        }// end of if cycle

        if (expected.size() != actual.size()) {
            return false;
        }// end of if cycle

        keysExpected = LibArray.getKeyFromMap(expected);
        keysActual = LibArray.getKeyFromMap(actual);
        if (!LibArray.isArrayEquals(keysExpected, keysActual)) {
            return false;
        }// end of if cycle

        valueExpected = LibArray.getValueFromMap(expected);
        valueActual = LibArray.getValueFromMap(actual);
        if (!LibArray.isArrayEquals(valueExpected, valueActual)) {
            return false;
        }// end of if cycle

        return uguali;
    }// end of static method

}// end of abstract static class
