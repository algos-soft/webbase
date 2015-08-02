package it.algos.webbase.test;

import it.algos.webbase.web.lib.LibDate;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Creato by gac on 17 giu 2015.
 */
public class LibDateTest {

    // alcune date di riferimento
    private static Date DATA_UNO = new Date(1413868320000L); // 21 ottobre 2014, 7 e 12
    private static Date DATA_DUE = new Date(1398057120000L); // 21 aprile 2014, 7 e 12
    private static Date DATA_TRE = new Date(1412485920000L); // 5 ottobre 2014, 7 e 12
    private static Date DATA_QUATTRO = new Date(1394259124000L); // 8 marzo 2014, 7 e 12 e 4
    private static int GIORNO = 12;
    private static int MESE = 7;
    private static int ANNO = 2004;

    // alcuni parametri utilizzati
    private String previsto = "";
    private String ottenuto = "";
    private Date dataPrevista = null;
    private Date dataOttenuta = null;
    private int numPrevisto = 0;
    private int numOttenuto = 0;
    private boolean boolPrevisto;
    private boolean boolOttenuto;


    @Test
    /**
     * Converte una data in stringa nel formato dd-MMMM-YYYY
     * <p>
     * Returns a string representation of the date <br>
     * Using leading zeroes in day <br>
     * Alphabetic representation of month in long descrition <br>
     * Four numbers for year <b>
     *
     * @param date da convertire
     * @return la data sotto forma di stringa
     */
    public void toStringDDMMMMYYYY() {
        previsto = "21-ottobre-2014";
        ottenuto = LibDate.toStringDDMMMMYYYY(DATA_UNO);
        assertEquals(ottenuto, previsto);

        previsto = "21-aprile-2014";
        ottenuto = LibDate.toStringDDMMMMYYYY(DATA_DUE);
        assertEquals(ottenuto, previsto);

        previsto = "05-ottobre-2014";
        ottenuto = LibDate.toStringDDMMMMYYYY(DATA_TRE);
        assertEquals(ottenuto, previsto);

        previsto = "08-marzo-2014";
        ottenuto = LibDate.toStringDDMMMMYYYY(DATA_QUATTRO);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Converte una data in stringa nel formato dd-MMM-YYYY
     * <p>
     * Returns a string representation of the date <br>
     * Using leading zeroes in day <br>
     * Alphabetic representation of month <br>
     * Four numbers for year <b>
     *
     * @param date da convertire
     * @return la data sotto forma di stringa
     */
    public void toStringDDMMMYYYY() {
        previsto = "21-ott-2014";
        ottenuto = LibDate.toStringDDMMMYYYY(DATA_UNO);
        assertEquals(ottenuto, previsto);

        previsto = "21-apr-2014";
        ottenuto = LibDate.toStringDDMMMYYYY(DATA_DUE);
        assertEquals(ottenuto, previsto);

        previsto = "05-ott-2014";
        ottenuto = LibDate.toStringDDMMMYYYY(DATA_TRE);
        assertEquals(ottenuto, previsto);

        previsto = "08-mar-2014";
        ottenuto = LibDate.toStringDDMMMYYYY(DATA_QUATTRO);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Converte una data in stringa nel formato d-MMM-YY
     * <p>
     * Returns a string representation of the date <br>
     * Suppressing leading zeroes in day <br>
     * Alphabetic representation of month <br>
     * Two numbers for year <b>
     *
     * @param date da convertire
     * @return la data sotto forma di stringa
     */
    public void toStringDMMMYY() {
        previsto = "21-ott-14";
        ottenuto = LibDate.toStringDMMMYY(DATA_UNO);
        assertEquals(ottenuto, previsto);

        previsto = "21-apr-14";
        ottenuto = LibDate.toStringDMMMYY(DATA_DUE);
        assertEquals(ottenuto, previsto);

        previsto = "5-ott-14";
        ottenuto = LibDate.toStringDMMMYY(DATA_TRE);
        assertEquals(ottenuto, previsto);

        previsto = "8-mar-14";
        ottenuto = LibDate.toStringDMMMYY(DATA_QUATTRO);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @Test
    /**
     * Converte una data in stringa nel formato d-M-YY
     * <p>
     * Returns a string representation of the date <br>
     * Suppressing leading zeroes in day and month <br>
     * Numeric representation of month <br>
     * Two numbers for year <b>
     *
     * @param date da convertire
     * @return la data sotto forma di stringa
     */
    public void toStringDMYY() {
        previsto = "21-10-14";
        ottenuto = LibDate.toStringDMYY(DATA_UNO);
        assertEquals(ottenuto, previsto);

        previsto = "21-4-14";
        ottenuto = LibDate.toStringDMYY(DATA_DUE);
        assertEquals(ottenuto, previsto);

        previsto = "5-10-14";
        ottenuto = LibDate.toStringDMYY(DATA_TRE);
        assertEquals(ottenuto, previsto);

        previsto = "8-3-14";
        ottenuto = LibDate.toStringDMYY(DATA_QUATTRO);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Converte una data in stringa nel formato d-M-YYYY
     * <p>
     * Returns a string representation of the date <br>
     * Suppressing leading zeroes in day and month <br>
     * Numeric representation of month <br>
     * Four numbers for year <b>
     *
     * @param date da convertire
     * @return la data sotto forma di stringa
     * @deprecated
     */
    public void toStringDMYYYY() {
        previsto = "21-10-2014";
        ottenuto = LibDate.toStringDMYYYY(DATA_UNO);
        assertEquals(ottenuto, previsto);

        previsto = "21-4-2014";
        ottenuto = LibDate.toStringDMYYYY(DATA_DUE);
        assertEquals(ottenuto, previsto);

        previsto = "5-10-2014";
        ottenuto = LibDate.toStringDMYYYY(DATA_TRE);
        assertEquals(ottenuto, previsto);

        previsto = "8-3-2014";
        ottenuto = LibDate.toStringDMYYYY(DATA_QUATTRO);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @Test
    /**
     * Converte una data in stringa nel formato dd-MM-YYYY
     * <p>
     * Returns a string representation of the date <br>
     * Using leading zeroes in day <br>
     * Using leading zeroes in month <br>
     * Numeric representation of month <br>
     * Four numbers for year <b>
     *
     * @param date da convertire
     * @return la data sotto forma di stringa
     * @deprecated
     */
    public void toStringDDMMYYYY() {
        previsto = "21-10-2014";
        ottenuto = LibDate.toStringDDMMYYYY(DATA_UNO);
        assertEquals(ottenuto, previsto);

        previsto = "21-04-2014";
        ottenuto = LibDate.toStringDDMMYYYY(DATA_DUE);
        assertEquals(ottenuto, previsto);

        previsto = "05-10-2014";
        ottenuto = LibDate.toStringDDMMYYYY(DATA_TRE);
        assertEquals(ottenuto, previsto);

        previsto = "08-03-2014";
        ottenuto = LibDate.toStringDDMMYYYY(DATA_QUATTRO);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Converte una data in stringa nel formato dd-MM-YYYY-HH:mm
     * <p>
     * Returns a string representation of the date <br>
     * Suppressing leading zeroes in day <br>
     * Numeric representation of month <br>
     * Two numbers for year <b>
     * Using leading zeroes in hour <br>
     * Using leading zeroes in minute <br>
     *
     * @param date da convertire
     * @return la data sotto forma di stringa
     */
    public void toStringDDMMYYYYHHMM() {
        previsto = "21-10-2014 07:12";
        ottenuto = LibDate.toStringDDMMYYYYHHMM(DATA_UNO);
        assertEquals(ottenuto, previsto);

        previsto = "21-04-2014 07:12";
        ottenuto = LibDate.toStringDDMMYYYYHHMM(DATA_DUE);
        assertEquals(ottenuto, previsto);

        previsto = "05-10-2014 07:12";
        ottenuto = LibDate.toStringDDMMYYYYHHMM(DATA_TRE);
        assertEquals(ottenuto, previsto);

        previsto = "08-03-2014 07:12";
        ottenuto = LibDate.toStringDDMMYYYYHHMM(DATA_QUATTRO);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Converte una data in stringa nel formato d-MMM-YY-HH:mm
     * <p>
     * Returns a string representation of the date <br>
     * Suppressing leading zeroes in day <br>
     * Alphabetic representation of month in short description <br>
     * Two numbers for year <b>
     * Using leading zeroes in hour <br>
     * Using leading zeroes in minute <br>
     *
     * @param date da convertire
     * @return la data sotto forma di stringa
     */
    public void toStringDMMMYYHHMM() {
        previsto = "21-ott-14 07:12";
        ottenuto = LibDate.toStringDMMMYYHHMM(DATA_UNO);
        assertEquals(ottenuto, previsto);

        previsto = "21-apr-14 07:12";
        ottenuto = LibDate.toStringDMMMYYHHMM(DATA_DUE);
        assertEquals(ottenuto, previsto);

        previsto = "5-ott-14 07:12";
        ottenuto = LibDate.toStringDMMMYYHHMM(DATA_TRE);
        assertEquals(ottenuto, previsto);

        previsto = "8-mar-14 07:12";
        ottenuto = LibDate.toStringDMMMYYHHMM(DATA_QUATTRO);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @Test
    /**
     * Converte una data in stringa nel formato d-M-YY-HH:mm
     * <p>
     * Returns a string representation of the date <br>
     * Suppressing leading zeroes in day and month <br>
     * Two numbers for year <b>
     * Using leading zeroes in hour <br>
     * Using leading zeroes in minute <br>
     *
     * @param date da convertire
     * @return la data sotto forma di stringa
     */
    public void toStringDMYYHHMM() {
        previsto = "21-10-14 07:12";
        ottenuto = LibDate.toStringDMYYHHMM(DATA_UNO);
        assertEquals(ottenuto, previsto);

        previsto = "21-4-14 07:12";
        ottenuto = LibDate.toStringDMYYHHMM(DATA_DUE);
        assertEquals(ottenuto, previsto);

        previsto = "5-10-14 07:12";
        ottenuto = LibDate.toStringDMYYHHMM(DATA_TRE);
        assertEquals(ottenuto, previsto);

        previsto = "8-3-14 07:12";
        ottenuto = LibDate.toStringDMYYHHMM(DATA_QUATTRO);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @Test
    /**
     * Converte una data in stringa nel formato HH:mm
     * <p>
     * Returns a string representation of the time only <br>
     * Using leading zeroes in hour <br>
     * Using leading zeroes in minute <br>
     *
     * @param date da convertire
     * @return la data sotto forma di stringa
     */
    public void toStringHHMM() {
        previsto = "07:12";
        ottenuto = LibDate.toStringHHMM(DATA_UNO);
        assertEquals(ottenuto, previsto);

        previsto = "07:12";
        ottenuto = LibDate.toStringHHMM(DATA_DUE);
        assertEquals(ottenuto, previsto);

        previsto = "07:12";
        ottenuto = LibDate.toStringHHMM(DATA_TRE);
        assertEquals(ottenuto, previsto);

        previsto = "07:12";
        ottenuto = LibDate.toStringHHMM(DATA_QUATTRO);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Converte una data in stringa nel formato HH:mm:ss
     * <p>
     * Returns a string representation of the time only <br>
     * Using leading zeroes in hour <br>
     * Using leading zeroes in minute <br>
     * Using leading zeroes in second <br>
     *
     * @param date da convertire
     * @return la data sotto forma di stringa
     */
    public void toStringHHMMSS() {
        previsto = "07:12:00";
        ottenuto = LibDate.toStringHHMMSS(DATA_UNO);
        assertEquals(ottenuto, previsto);

        previsto = "07:12:04";
        ottenuto = LibDate.toStringHHMMSS(DATA_QUATTRO);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @Test
    /**
     * Converte una data in stringa nel formato dd-MM-YY HH:mm:ss
     * <p>
     * Returns a string representation of the date <br>
     * Using leading zeroes in day <br>
     * Using leading zeroes in month <br>
     * Numeric representation of month <br>
     * Two numbers for year <b>
     * Using leading zeroes in hour <br>
     * Using leading zeroes in minute <br>
     * Using leading zeroes in second <br>
     *
     * @param date da convertire
     * @return la data sotto forma di stringa
     */
    public void toStringDDMMYYHHMMSS() {
        int ora = 0;
        int minuto = 0;
        int secondo = 0;

        previsto = "21-10-14 07:12:00";
        ottenuto = LibDate.toStringDDMMYYHHMMSS(DATA_UNO);
        assertEquals(ottenuto, previsto);

        previsto = "08-03-14 07:12:04";
        ottenuto = LibDate.toStringDDMMYYHHMMSS(DATA_QUATTRO);
        assertEquals(ottenuto, previsto);

        ora = 21;
        minuto = 0;
        secondo = 15;
        previsto = "12-07-04 21:00:15";
        dataOttenuta = LibDate.creaData(GIORNO, MESE, ANNO, ora, minuto, secondo);
        ottenuto = LibDate.toStringDDMMYYHHMMSS(dataOttenuta);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @Test
    /**
     * Restituisce la data corrente
     * <p>
     * Ore e minuti e secondi regolati alla mezzanotte
     *
     * @return la data in formato data
     */
    public void today() {
        Date dataCorrente = new Date(); //ore e minuti attuali
        Date dataOttenuta = null; //ore e minuti a mezzanotte

        dataOttenuta = LibDate.today();
        assertFalse(dataOttenuta == dataCorrente); //sono uguali solo a mezzanotte

        previsto = "00:00";
        ottenuto = LibDate.toStringHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @Test
    /**
     * Restituisce la data del primo giorno del mese dell'anno
     * <p>
     * Ore e minuti e secondi regolati subito dopo la mezzanotte
     *
     * @param numMeseDellAnno L'anno parte da gennaio che è il mese numero 1
     * @param anno di riferimento
     * @return la data in formato data
     */
    public void fromInizioMeseAnno() {
        Date dataOttenuta = null;
        int mese;
        int anno;

        mese = 9;
        anno = 2013;
        dataOttenuta = LibDate.fromInizioMeseAnno(mese, anno);
        previsto = "1-set-13 00:00";
        ottenuto = LibDate.toStringDMMMYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);
        previsto = "1-9-13 00:00";
        ottenuto = LibDate.toStringDMYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Restituisce la data del primo giorno del mese dell'anno
     * <p>
     * Ore e minuti e secondi regolati subito dopo la mezzanotte
     *
     * @param nomeBreveLungo (descrizione short o long)
     * @param anno di riferimento
     * @return la data in formato data
     */
    public void fromInizioMeseAnno2() {
        Date dataOttenuta = null;
        String nomeBreveLungo;
        int anno;

        nomeBreveLungo = "set";
        anno = 2013;
        dataOttenuta = LibDate.fromInizioMeseAnno(nomeBreveLungo, anno);
        previsto = "1-set-13 00:00";
        ottenuto = LibDate.toStringDMMMYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);
        previsto = "1-9-13 00:00";
        ottenuto = LibDate.toStringDMYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);

        anno = 2015;
        previsto = "1-9-15 00:00";
        nomeBreveLungo = "settembre";
        dataOttenuta = LibDate.fromInizioMeseAnno(nomeBreveLungo, anno);
        ottenuto = LibDate.toStringDMYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);

        nomeBreveLungo = "Set";
        dataOttenuta = LibDate.fromInizioMeseAnno(nomeBreveLungo, anno);
        ottenuto = LibDate.toStringDMYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);

        nomeBreveLungo = "Settembre";
        dataOttenuta = LibDate.fromInizioMeseAnno(nomeBreveLungo, anno);
        ottenuto = LibDate.toStringDMYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @Test
    /**
     * Restituisce la data dell'ultimo giorno del mese dell'anno
     * <p>
     * Ore e minuti e secondi regolati subito prima della mezzanotte
     *
     * @param numMeseDellAnno L'anno parte da gennaio che è il mese numero 1
     * @param anno di riferimento
     * @return la data in formato data
     */
    public void fromFineMeseAnno() {
        Date dataOttenuta = null;
        int mese;
        int anno;

        mese = 9;
        anno = 2013;
        dataOttenuta = LibDate.fromFineMeseAnno(mese, anno);
        previsto = "30-set-13 23:59";
        ottenuto = LibDate.toStringDMMMYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);
        previsto = "30-9-13 23:59";
        ottenuto = LibDate.toStringDMYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Restituisce la data dell'ultimo giorno del mese dell'anno
     * <p>
     * Ore e minuti e secondi regolati subito prima della mezzanotte
     *
     * @param nomeBreveLungo (descrizione short o long)
     * @param anno di riferimento
     * @return la data in formato data
     */
    public void fromFineMeseAnno2() {
        Date dataOttenuta = null;
        String nomeBreveLungo;
        int anno;

        nomeBreveLungo = "set";
        anno = 2013;
        dataOttenuta = LibDate.fromFineMeseAnno(nomeBreveLungo, anno);
        previsto = "30-set-13 23:59";
        ottenuto = LibDate.toStringDMMMYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);
        previsto = "30-9-13 23:59";
        ottenuto = LibDate.toStringDMYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);

        anno = 2008;
        previsto = "30-9-08 23:59";
        nomeBreveLungo = "settembre";
        dataOttenuta = LibDate.fromFineMeseAnno(nomeBreveLungo, anno);
        ottenuto = LibDate.toStringDMYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);

        nomeBreveLungo = "Set";
        dataOttenuta = LibDate.fromFineMeseAnno(nomeBreveLungo, anno);
        ottenuto = LibDate.toStringDMYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);

        nomeBreveLungo = "Settembre";
        dataOttenuta = LibDate.fromFineMeseAnno(nomeBreveLungo, anno);
        ottenuto = LibDate.toStringDMYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Aggiunge (o sottrae) ad un una data i mesi indicati.
     * <p>
     * Se i mesi sono negativi, li sottrae <br>
     * Esegue solo se la data è valida (non nulla e non vuota) <br>
     *
     * @param data di riferimento
     * @param mesi da aggiungere
     * @return la data risultante
     */
    public void addMonths() {
        Date dataOriginale;
        int mesiDifferenza = 0;

        dataOriginale = DATA_DUE;
        mesiDifferenza = 6;
        dataPrevista = DATA_UNO;
        dataOttenuta = LibDate.addMonths(dataOriginale, mesiDifferenza);
        assertEquals(dataOttenuta, dataPrevista);
    }// end of single test

    @Test
    /**
     * Aggiunge (o sottrae) ad un una data i giorni indicati.
     * <p>
     * Se i giorni sono negativi, li sottrae <br>
     * Esegue solo se la data è valida (non nulla e non vuota) <br>
     *
     * @param data di riferimento
     * @param giorni da aggiungere
     * @return la data risultante
     */
    public void addDays() {
        Date dataOriginale;
        int giorniDifferenza = 0;

        dataOriginale = DATA_TRE;
        giorniDifferenza = 16;
        dataPrevista = DATA_UNO;
        dataOttenuta = LibDate.add(dataOriginale, giorniDifferenza);
        assertEquals(dataOttenuta, dataPrevista);

        dataOriginale = DATA_UNO;
        giorniDifferenza = -16;
        dataPrevista = DATA_TRE;
        dataOttenuta = LibDate.add(dataOriginale, giorniDifferenza);
        assertEquals(dataOttenuta, dataPrevista);

        dataOriginale = DATA_DUE;
        giorniDifferenza = 183;
        dataPrevista = DATA_UNO;
        dataOttenuta = LibDate.add(dataOriginale, giorniDifferenza);
        assertEquals(dataOttenuta, dataPrevista);
    }// end of single test


    @Test
    /**
     * Aggiunge (o sottrae) ad un una data le ore indicate.
     * <p>
     * Se le ore sono negative, le sottrae <br>
     * Esegue solo se la data è valida (non nulla e non vuota) <br>
     *
     * @param data   di riferimento
     * @param ore da aggiungere
     * @return la data risultante
     */
    public void addHour() {
        Date dataOriginale;
        int oreDifferenza = 0;

        dataOriginale = LibDate.creaData(GIORNO, MESE, ANNO, 3, 0, 0);
        dataPrevista = LibDate.creaData(GIORNO, MESE, ANNO, 8, 0, 0);
        oreDifferenza = 5;
        dataOttenuta = LibDate.addHour(dataOriginale, oreDifferenza);
        assertEquals(dataOttenuta, dataPrevista);

    }// end of single test


    @Test
    /**
     * Ritorna il numero della settimana dell'anno di una data fornita.
     * <p>
     *
     * @param data fornita
     * @return il numero della settimana dell'anno
     */
    public void getWeekYear() {
        numPrevisto = 1;
        dataOttenuta = LibDate.getPrimoGennaio(ANNO);
        numOttenuto = LibDate.getWeekYear(dataOttenuta);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 43;
        numOttenuto = LibDate.getWeekYear(DATA_UNO);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 17;
        numOttenuto = LibDate.getWeekYear(DATA_DUE);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 40;
        numOttenuto = LibDate.getWeekYear(DATA_TRE);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 10;
        numOttenuto = LibDate.getWeekYear(DATA_QUATTRO);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 2;
        dataOttenuta = LibDate.creaData(14, 1, 1965);
        numOttenuto = LibDate.getWeekYear(dataOttenuta);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 53;
        dataOttenuta = LibDate.getTrentunoDicembre(ANNO);
        numOttenuto = LibDate.getWeekYear(dataOttenuta);
        assertEquals(numOttenuto, numPrevisto);

    }// end of method

    @Test
    /**
     * Ritorna il numero della settimana del mese di una data fornita.
     * <p>
     *
     * @param data fornita
     * @return il numero della settimana del mese
     */
    public void getWeekMonth() {
        numPrevisto = 1;
        dataOttenuta = LibDate.getPrimoGennaio(ANNO);
        numOttenuto = LibDate.getWeekMonth(dataOttenuta);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 4;
        numOttenuto = LibDate.getWeekMonth(DATA_UNO);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 4;
        numOttenuto = LibDate.getWeekMonth(DATA_DUE);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 1;
        numOttenuto = LibDate.getWeekMonth(DATA_TRE);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 1;
        numOttenuto = LibDate.getWeekMonth(DATA_QUATTRO);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 2;
        dataOttenuta = LibDate.creaData(14, 1, 1965);
        numOttenuto = LibDate.getWeekMonth(dataOttenuta);
        assertEquals(numOttenuto, numPrevisto);
    }// end of method


    @Test
    /**
     * Ritorna il numero del giorno dell'anno di una data fornita.
     * <p>
     *
     * @param data fornita
     * @return il numero del giorno dell'anno
     */
    public void getDayYear() {
        numPrevisto = 1;
        dataOttenuta = LibDate.getPrimoGennaio(ANNO);
        numOttenuto = LibDate.getDayYear(dataOttenuta);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 67;
        numOttenuto = LibDate.getDayYear(DATA_QUATTRO);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 14;
        dataOttenuta = LibDate.creaData(14, 1, 1965);
        numOttenuto = LibDate.getDayYear(dataOttenuta);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 365;
        dataOttenuta = LibDate.getTrentunoDicembre(2014);
        numOttenuto = LibDate.getDayYear(dataOttenuta);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 366;
        dataOttenuta = LibDate.getTrentunoDicembre(ANNO);
        numOttenuto = LibDate.getDayYear(dataOttenuta);
        assertEquals(numOttenuto, numPrevisto);
    }// end of single test

    @Test
    /**
     * Ritorna il numero del giorno del mese di una data fornita.
     * <p>
     *
     * @param data fornita
     * @return il numero del giorno del mese
     */
    public void getDayMonth() {
        numPrevisto = 21;
        numOttenuto = LibDate.getDayMonth(DATA_UNO);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 21;
        numOttenuto = LibDate.getDayMonth(DATA_DUE);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 5;
        numOttenuto = LibDate.getDayMonth(DATA_TRE);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 8;
        numOttenuto = LibDate.getDayMonth(DATA_QUATTRO);
        assertEquals(numOttenuto, numPrevisto);
    }// end of single test

    @Test
    /**
     * Ritorna il numero del giorno della settimana di una data fornita.
     * <p>
     *
     * @param data fornita
     * @return il numero del giorno della settimana (1=dom, 7=sab)
     */
    public void getDayWeek() {
        numPrevisto = 3;
        numOttenuto = LibDate.getDayWeek(DATA_UNO);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 2;
        numOttenuto = LibDate.getDayWeek(DATA_DUE);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 1;
        numOttenuto = LibDate.getDayWeek(DATA_TRE);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 7;
        numOttenuto = LibDate.getDayWeek(DATA_QUATTRO);
        assertEquals(numOttenuto, numPrevisto);
    }// end of single test


    @Test
    /**
     * Ritorna il numero delle ore di una data fornita.
     * <p>
     *
     * @param data fornita
     * @return il numero delle ore
     */
    public void getOre() {
        numPrevisto = 7;
        numOttenuto = LibDate.getOre(DATA_UNO);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 0;
        dataOttenuta = LibDate.fromInizioMeseAnno(MESE, ANNO);
        numOttenuto = LibDate.getOre(dataOttenuta);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 23;
        dataOttenuta = LibDate.fromFineMeseAnno(MESE, ANNO);
        numOttenuto = LibDate.getOre(dataOttenuta);
        assertEquals(numOttenuto, numPrevisto);
    }// end of single test


    @Test
    /**
     * Ritorna il numero dei minuti di una data fornita.
     * <p>
     *
     * @param data fornita
     * @return il numero dei minuti
     */
    public void getMinuti() {
        numPrevisto = 12;
        numOttenuto = LibDate.getMinuti(DATA_TRE);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 0;
        dataOttenuta = LibDate.fromInizioMeseAnno(MESE, ANNO);
        numOttenuto = LibDate.getMinuti(dataOttenuta);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 59;
        dataOttenuta = LibDate.fromFineMeseAnno(MESE, ANNO);
        numOttenuto = LibDate.getMinuti(dataOttenuta);
        assertEquals(numOttenuto, numPrevisto);
    }// end of single test

    @Test
    /**
     * Ritorna il numero dei secondi di una data fornita.
     * <p>
     *
     * @param data fornita
     * @return il numero dei secondi
     */
    public void getSecondi() {
        numPrevisto = 0;
        numOttenuto = LibDate.getSecondi(DATA_DUE);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 0;
        dataOttenuta = LibDate.fromInizioMeseAnno(MESE, ANNO);
        numOttenuto = LibDate.getSecondi(dataOttenuta);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 59;
        dataOttenuta = LibDate.fromFineMeseAnno(MESE, ANNO);
        numOttenuto = LibDate.getSecondi(dataOttenuta);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = 4;
        numOttenuto = LibDate.getSecondi(DATA_QUATTRO);
        assertEquals(numOttenuto, numPrevisto);
    }// end of single test


    @Test
    /**
     * Crea una data.
     * <p>
     *
     * @param giorno il giorno del mese (1 per il primo)
     * @param numMeseDellAnno   il mese dell'anno (1 per gennaio)
     * @param anno   l'anno
     * @return la data creata
     */
    public void creaData() {
        previsto = "12-07-2004 00:00";
        dataOttenuta = LibDate.creaData(GIORNO, MESE, ANNO);
        ottenuto = LibDate.toStringDDMMYYYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);

        previsto = "04-01-2015 00:00";
        dataOttenuta = LibDate.creaData(4, 1, 2015);
        ottenuto = LibDate.toStringDDMMYYYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);

        previsto = "04-01-2015 00:00";
        dataOttenuta = LibDate.creaData(4, 0, 2015);
        ottenuto = LibDate.toStringDDMMYYYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Crea una data.
     * <p>
     *
     * @param giorno il giorno del mese (1 per il primo)
     * @param numMeseDellAnno   il mese dell'anno (1 per gennaio)
     * @param anno   l'anno
     * @return la data creata
     */
    public void creaData2() {
        int ora = 0;
        int minuto = 0;
        int secondo = 0;

        previsto = "12-07-04 00:00:00";
        dataOttenuta = LibDate.creaData(GIORNO, MESE, ANNO, ora, minuto, secondo);
        ottenuto = LibDate.toStringDDMMYYHHMMSS(dataOttenuta);
        assertEquals(ottenuto, previsto);

        ora = 9;
        minuto = 24;
        secondo = 43;
        previsto = "12-07-04 09:24:43";
        dataOttenuta = LibDate.creaData(GIORNO, MESE, ANNO, ora, minuto, secondo);
        ottenuto = LibDate.toStringDDMMYYHHMMSS(dataOttenuta);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Elimina ore/minuti/secondi da una data.
     * <p>
     *
     * @param dateIn la data dalla quale eliminare ore/minuti/secondi
     * @return la data senza ore/minuti/secondi
     */
    public void dropTime() {
        previsto = "21-04-2014 00:00";
        dataOttenuta = LibDate.dropTime(DATA_DUE);
        ottenuto = LibDate.toStringDDMMYYYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Forza la data all'ultimo millisecondo.
     * <p>
     *
     * @param dateIn la data da forzare
     * @return la data con ore/minuti/secondi/millisecondi al valore massimo
     */
    public void lastTime() {
        previsto = "21-04-2014 23:59";
        dataOttenuta = LibDate.lastTime(DATA_DUE);
        ottenuto = LibDate.toStringDDMMYYYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @Test
    /**
     * Costruisce la data per il 1° gennaio dell'anno indicato.
     * <p>
     *
     * @param anno di riferimento
     * @return primo gennaio dell'anno
     */
    public void getPrimoGennaio() {
        previsto = "01-01-2004 00:00";
        dataOttenuta = LibDate.getPrimoGennaio(ANNO);
        ottenuto = LibDate.toStringDDMMYYYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Costruisce la data per il 31° dicembre dell'anno indicato.
     * <p>
     *
     * @param anno di riferimento
     * @return ultimo dell'anno
     */
    public void getTrentunoDicembre() {
        previsto = "31-12-2004 23:59";
        dataOttenuta = LibDate.getTrentunoDicembre(ANNO);
        ottenuto = LibDate.toStringDDMMYYYYHHMM(dataOttenuta);
        assertEquals(ottenuto, previsto);
    }// end of single test

    @Test
    /**
     * Differenza in giorni tra le date indicate.
     * <p>
     * Esegue solo se le data sono valide
     * Se le due date sono uguali la differenza è zero Se le due date sono consecutive la differenza è uno, ecc...
     *
     * @param dataIniziale data iniziale
     * @param dataFinale   data finale
     * @return il numero di giorni passati tra le due date (positivo se la prima data e' precedente la seconda,
     * altrimenti negativo)
     */
    public void diffDays() {
        Date dataUno = null;
        Date dataDue = null;
        int ora = 0;
        int minuto = 0;
        int secondo = 0;

        dataUno = LibDate.creaData(GIORNO, MESE, ANNO);
        dataDue = LibDate.creaData(GIORNO, MESE, ANNO);
        numPrevisto = 0;
        numOttenuto = LibDate.diffDays(dataUno, dataDue);
        assertEquals(numOttenuto, numPrevisto);

        dataUno = LibDate.creaData(4, MESE, ANNO);
        dataDue = LibDate.creaData(7, MESE, ANNO);
        numPrevisto = 3;
        numOttenuto = LibDate.diffDays(dataUno, dataDue);
        assertEquals(numOttenuto, numPrevisto);

        dataUno = LibDate.creaData(1, 4, ANNO);
        dataDue = LibDate.creaData(1, 5, ANNO);
        numPrevisto = 30;
        numOttenuto = LibDate.diffDays(dataUno, dataDue);
        assertEquals(numOttenuto, numPrevisto);

        dataUno = LibDate.creaData(1, 5, ANNO);
        dataDue = LibDate.creaData(1, 6, ANNO);
        numPrevisto = 31;
        numOttenuto = LibDate.diffDays(dataUno, dataDue);
        assertEquals(numOttenuto, numPrevisto);

        dataUno = LibDate.creaData(1, 4, ANNO);
        dataDue = LibDate.creaData(7, 5, ANNO);
        numPrevisto = 36;
        numOttenuto = LibDate.diffDays(dataUno, dataDue);
        assertEquals(numOttenuto, numPrevisto);

        dataUno = LibDate.creaData(1, 4, ANNO);
        dataDue = LibDate.creaData(7, 5, ANNO);
        numPrevisto = -36;
        numOttenuto = LibDate.diffDays(dataDue, dataUno);
        assertEquals(numOttenuto, numPrevisto);

        dataUno = LibDate.creaData(2, MESE, ANNO, ora, minuto, secondo);
        dataDue = LibDate.creaData(7, MESE, ANNO, ora, minuto, secondo);
        numPrevisto = 5;
        numOttenuto = LibDate.diffDays(dataUno, dataDue);
        assertEquals(numOttenuto, numPrevisto);

        dataUno = LibDate.creaData(2, MESE, ANNO, 3, minuto, secondo);
        dataDue = LibDate.creaData(7, MESE, ANNO, 19, minuto, secondo);
        numPrevisto = 5;
        numOttenuto = LibDate.diffDays(dataUno, dataDue);
        assertEquals(numOttenuto, numPrevisto);

        dataUno = LibDate.creaData(30, 4, ANNO);
        dataDue = LibDate.creaData(1, 5, ANNO);
        numPrevisto = 1;
        numOttenuto = LibDate.diffDays(dataUno, dataDue);
        assertEquals(numOttenuto, numPrevisto);

        dataUno = LibDate.creaData(30, 5, ANNO);
        dataDue = LibDate.creaData(1, 6, ANNO);
        numPrevisto = 2;
        numOttenuto = LibDate.diffDays(dataUno, dataDue);
        assertEquals(numOttenuto, numPrevisto);
    }// end of single test

    @Test
    /**
     * Differenza effettiva in ore tra le date indicate.
     * <p>
     * Esegue solo se le data sono valide
     *
     * @param dataIniziale data iniziale
     * @param dataFinale   data finale
     * @return ore effettive di differenza
     */
    public void diffHours() {
        Date dataUno = null;
        Date dataDue = null;

        dataUno = LibDate.creaData(GIORNO, MESE, ANNO, 4, 0, 0);
        dataDue = LibDate.creaData(GIORNO, MESE, ANNO, 21, 0, 0);
        numPrevisto = 17;
        numOttenuto = LibDate.diffHours(dataUno, dataDue);
        assertEquals(numOttenuto, numPrevisto);

        numPrevisto = -17;
        numOttenuto = LibDate.diffHours(dataDue, dataUno);
        assertEquals(numOttenuto, numPrevisto);

        dataUno = LibDate.creaData(GIORNO, MESE, ANNO, 4, 0, 0);
        dataDue = LibDate.creaData(GIORNO, MESE, ANNO, 21, 59, 0);
        numPrevisto = 17;
        numOttenuto = LibDate.diffHours(dataUno, dataDue);
        assertEquals(numOttenuto, numPrevisto);

        dataUno = LibDate.creaData(12, MESE, ANNO, 4, 0, 0);
        dataDue = LibDate.creaData(13, MESE, ANNO, 21, 59, 0);
        numPrevisto = 41;
        numOttenuto = LibDate.diffHours(dataUno, dataDue);
        assertEquals(numOttenuto, numPrevisto);
    }// end of single test

    @Test
    /**
     * Differenza effettiva in minuti tra le date indicate.
     * <p>
     * Esegue solo se le data sono valide
     *
     * @param dataIniziale data iniziale
     * @param dataFinale   data finale
     * @return minuti effettivi di differenza
     */
    public void diffMinutes() {
        Date dataUno = null;
        Date dataDue = null;

        dataUno = LibDate.creaData(GIORNO, MESE, ANNO, 8, 0, 0);
        dataDue = LibDate.creaData(GIORNO, MESE, ANNO, 8, 14, 0);
        numPrevisto = 14;
        numOttenuto = LibDate.diffMinutes(dataUno, dataDue);
        assertEquals(numOttenuto, numPrevisto);

        dataUno = LibDate.creaData(GIORNO, MESE, ANNO, 8, 0, 0);
        dataDue = LibDate.creaData(GIORNO, MESE, ANNO, 9, 14, 0);
        numPrevisto = 74;
        numOttenuto = LibDate.diffMinutes(dataUno, dataDue);
        assertEquals(numOttenuto, numPrevisto);
    }// end of single test

    @Test
    /**
     * Differenza effettiva in secondi tra le date indicate.
     * <p>
     * Esegue solo se le data sono valide
     *
     * @param dataIniziale data iniziale
     * @param dataFinale   data finale
     * @return secondi effettivi di differenza
     */
    public void diffSeconds() {
        Date dataUno = null;
        Date dataDue = null;

        dataUno = LibDate.creaData(GIORNO, MESE, ANNO, 8, 12, 5);
        dataDue = LibDate.creaData(GIORNO, MESE, ANNO, 8, 12, 31);
        numPrevisto = 26;   // da 5 a 31
        numOttenuto = LibDate.diffSeconds(dataUno, dataDue);
        assertEquals(numOttenuto, numPrevisto);

        dataUno = LibDate.creaData(GIORNO, MESE, ANNO, 8, 12, 5);
        dataDue = LibDate.creaData(GIORNO, MESE, ANNO, 8, 13, 31);
        numPrevisto = 86;   // da 5 a 31 + 1 minuto
        numOttenuto = LibDate.diffSeconds(dataUno, dataDue);
        assertEquals(numOttenuto, numPrevisto);

        dataUno = LibDate.creaData(GIORNO, MESE, ANNO, 8, 12, 5);
        dataDue = LibDate.creaData(GIORNO, MESE, ANNO, 9, 13, 31);
        numPrevisto = 3686; // da 5 a 31 + 1 minuto + 1 ora
        numOttenuto = LibDate.diffSeconds(dataUno, dataDue);
        assertEquals(numOttenuto, numPrevisto);
    }// end of single test

    @Test
    /**
     * Controlla se una data è precedente ad una data di riferimento.
     * <p>
     * Esegue solo se le date sono valide (non nulle e non vuote) <br>
     * Gli estremi NON sono compresi <br>
     * Se le date coincidono, la risposta è FALSE <br>
     *
     * @param dataRif  di riferiemento
     * @param dataTest da controllare
     * @return vero se la seconda data è precedente alla prima
     */
    public void isPrecedente() {
        Date dataUno = null;
        Date dataDue = null;

        boolPrevisto = true;
        boolOttenuto = LibDate.isPrecedente(DATA_QUATTRO, DATA_DUE);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = false;
        boolOttenuto = LibDate.isPrecedente(DATA_DUE, DATA_QUATTRO);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = false;
        boolOttenuto = LibDate.isPrecedente(DATA_UNO, DATA_UNO);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = true;
        dataUno = LibDate.creaData(17, 8, ANNO);
        dataDue = LibDate.creaData(4, 9, ANNO);
        boolOttenuto = LibDate.isPrecedente(dataUno, dataDue);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = false;
        dataUno = LibDate.creaData(GIORNO, MESE, ANNO);
        dataDue = LibDate.creaData(GIORNO, MESE, ANNO);
        boolOttenuto = LibDate.isPrecedente(dataUno, dataDue);
        assertEquals(boolOttenuto, boolPrevisto);
    }// end of single test

    @Test
    /**
     * Controlla se una data è precedente ad una data di riferimento.
     * <p>
     * Esegue solo se le date sono valide (non nulle e non vuote) <br>
     * Gli estremi NON sono compresi <br>
     * Se le date coincidono, la risposta è FALSE <br>
     *
     * @param dataRif  di riferiemento
     * @param dataTest da controllare
     * @return vero se la seconda data è precedente alla prima
     */
    public void isPrecedenteEsclusi() {
        Date dataUno = null;
        Date dataDue = null;

        boolPrevisto = true;
        boolOttenuto = LibDate.isPrecedenteEsclusi(DATA_QUATTRO, DATA_DUE);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = false;
        boolOttenuto = LibDate.isPrecedenteEsclusi(DATA_DUE, DATA_QUATTRO);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = false;
        boolOttenuto = LibDate.isPrecedenteEsclusi(DATA_UNO, DATA_UNO);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = true;
        dataUno = LibDate.creaData(17, 8, ANNO);
        dataDue = LibDate.creaData(4, 9, ANNO);
        boolOttenuto = LibDate.isPrecedenteEsclusi(dataUno, dataDue);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = false;
        dataUno = LibDate.creaData(GIORNO, MESE, ANNO);
        dataDue = LibDate.creaData(GIORNO, MESE, ANNO);
        boolOttenuto = LibDate.isPrecedenteEsclusi(dataUno, dataDue);
        assertEquals(boolOttenuto, boolPrevisto);
    }// end of single test

    @Test
    /**
     * Controlla se una data è precedente ad una data di riferimento.
     * <p>
     * Esegue solo se le date sono valide (non nulle e non vuote) <br>
     * Gli estremi SONO compresi <br>
     * Se le date coincidono, la risposta è TRUE <br>
     *
     * @param dataTest da controllare
     * @param dataRif  di riferimento
     * @return vero se la seconda data è precedente alla prima
     */
    public void isPrecedenteUguale() {
        Date dataUno = null;
        Date dataDue = null;

        boolPrevisto = true;
        boolOttenuto = LibDate.isPrecedenteUguale(DATA_QUATTRO, DATA_DUE);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = false;
        boolOttenuto = LibDate.isPrecedenteUguale(DATA_DUE, DATA_QUATTRO);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = true;
        boolOttenuto = LibDate.isPrecedenteUguale(DATA_UNO, DATA_UNO);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = true;
        dataUno = LibDate.creaData(17, 8, ANNO);
        dataDue = LibDate.creaData(4, 9, ANNO);
        boolOttenuto = LibDate.isPrecedenteUguale(dataUno, dataDue);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = true;
        dataUno = LibDate.creaData(GIORNO, MESE, ANNO);
        dataDue = LibDate.creaData(GIORNO, MESE, ANNO);
        boolOttenuto = LibDate.isPrecedenteUguale(dataUno, dataDue);
        assertEquals(boolOttenuto, boolPrevisto);
    }// end of single test


    @Test
    /**
     * Controlla se una data è successiva ad una data di riferimento.
     * <p>
     * Esegue solo se le date sono valide (non nulle e non vuote) <br>
     * Gli estremi NON sono compresi <br>
     * Se le date coincidono, la risposta è FALSE <br>
     *
     * @param dataTest da controllare
     * @param dataRif  di riferimento
     * @return vero se la seconda data è successiva alla prima
     */
    public void isSuccessiva() {
        Date dataUno = null;
        Date dataDue = null;

        boolPrevisto = false;
        boolOttenuto = LibDate.isSuccessiva(DATA_QUATTRO, DATA_DUE);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = true;
        boolOttenuto = LibDate.isSuccessiva(DATA_DUE, DATA_QUATTRO);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = false;
        boolOttenuto = LibDate.isSuccessiva(DATA_UNO, DATA_UNO);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = true;
        dataUno = LibDate.creaData(3, 11, ANNO);
        dataDue = LibDate.creaData(25, 9, ANNO);
        boolOttenuto = LibDate.isSuccessiva(dataUno, dataDue);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = false;
        dataUno = LibDate.creaData(GIORNO, MESE, ANNO);
        dataDue = LibDate.creaData(GIORNO, MESE, ANNO);
        boolOttenuto = LibDate.isSuccessiva(dataUno, dataDue);
        assertEquals(boolOttenuto, boolPrevisto);
    }// end of single test

    @Test
    /**
     * Controlla se una data è successiva ad una data di riferimento.
     * <p>
     * Esegue solo se le date sono valide (non nulle e non vuote) <br>
     * Gli estremi NON sono compresi <br>
     * Se le date coincidono, la risposta è FALSE <br>
     *
     * @param dataTest da controllare
     * @param dataRif  di riferimento
     * @return vero se la seconda data è successiva alla prima
     */
    public void isSuccessivaEsclusi() {
        Date dataUno = null;
        Date dataDue = null;

        boolPrevisto = false;
        boolOttenuto = LibDate.isSuccessivaEsclusi(DATA_QUATTRO, DATA_DUE);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = true;
        boolOttenuto = LibDate.isSuccessivaEsclusi(DATA_DUE, DATA_QUATTRO);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = false;
        boolOttenuto = LibDate.isSuccessivaEsclusi(DATA_UNO, DATA_UNO);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = true;
        dataUno = LibDate.creaData(3, 11, ANNO);
        dataDue = LibDate.creaData(25, 9, ANNO);
        boolOttenuto = LibDate.isSuccessivaEsclusi(dataUno, dataDue);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = false;
        dataUno = LibDate.creaData(GIORNO, MESE, ANNO);
        dataDue = LibDate.creaData(GIORNO, MESE, ANNO);
        boolOttenuto = LibDate.isSuccessivaEsclusi(dataUno, dataDue);
        assertEquals(boolOttenuto, boolPrevisto);
    }// end of single test

    @Test
    /**
     * Controlla se una data è successiva ad una data di riferimento.
     * <p>
     * Esegue solo se le date sono valide (non nulle e non vuote) <br>
     * Gli estremi SONO compresi <br>
     * Se le date coincidono, la risposta è TRUE <br>
     *
     * @param dataTest da controllare
     * @param dataRif  di riferimento
     * @return vero se la seconda data è successiva alla prima
     */
    public void isSuccessivaUguale() {
        Date dataUno = null;
        Date dataDue = null;

        boolPrevisto = false;
        boolOttenuto = LibDate.isSuccessivaUguale(DATA_QUATTRO, DATA_DUE);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = true;
        boolOttenuto = LibDate.isSuccessivaUguale(DATA_DUE, DATA_QUATTRO);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = true;
        boolOttenuto = LibDate.isSuccessivaUguale(DATA_UNO, DATA_UNO);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = true;
        dataUno = LibDate.creaData(3, 11, ANNO);
        dataDue = LibDate.creaData(25, 9, ANNO);
        boolOttenuto = LibDate.isSuccessivaUguale(dataUno, dataDue);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = true;
        dataUno = LibDate.creaData(GIORNO, MESE, ANNO);
        dataDue = LibDate.creaData(GIORNO, MESE, ANNO);
        boolOttenuto = LibDate.isSuccessivaUguale(dataUno, dataDue);
        assertEquals(boolOttenuto, boolPrevisto);
    }// end of single test


    @Test
    /**
     * Controlla se una data è compresa tra due date di riferimento.
     * <p>
     * Esegue solo se le date sono valide (non nulle e non vuote) <br>
     * Gli estremi NON sono compresi <br>
     * Se la data coincide con uno degli estremi, la risposta è FALSE <br>
     *
     * @param dataTest data da controllare
     * @param dataInizio   data di riferimento iniziale dell'intervallo
     * @param dataFine     data di riferimento finale dell'intervallo
     * @return vero se la data è compresa nell'intervallo di date
     */
    public void isCompresa() {
        Date dataUno = null;
        Date dataDue = null;
        Date dataTre = null;

        boolPrevisto = true;
        boolOttenuto = LibDate.isCompresa(DATA_DUE, DATA_QUATTRO, DATA_UNO);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = false;
        boolOttenuto = LibDate.isCompresa(DATA_DUE, DATA_UNO, DATA_QUATTRO);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = false;
        boolOttenuto = LibDate.isCompresa(DATA_DUE, DATA_DUE, DATA_UNO);
        assertEquals(boolOttenuto, boolPrevisto);

        dataUno = LibDate.creaData(17, 5, ANNO);
        dataDue = LibDate.creaData(1, 5, ANNO);
        dataTre = LibDate.creaData(30, 5, ANNO);
        boolPrevisto = true;
        boolOttenuto = LibDate.isCompresa(dataUno, dataDue, dataTre);
        assertEquals(boolOttenuto, boolPrevisto);

        dataUno = LibDate.creaData(17, 11, ANNO);
        dataDue = LibDate.creaData(1, 5, ANNO);
        dataTre = LibDate.creaData(30, 5, ANNO);
        boolPrevisto = false;
        boolOttenuto = LibDate.isCompresa(dataUno, dataDue, dataTre);
        assertEquals(boolOttenuto, boolPrevisto);
    }// end of single test

    @Test
    /**
     * Controlla se una data è compresa tra due date di riferimento.
     * <p>
     * Esegue solo se le date sono valide (non nulle e non vuote) <br>
     * Gli estremi NON sono compresi <br>
     * Se la data coincide con uno degli estremi, la risposta è FALSE <br>
     *
     * @param dataTest data da controllare
     * @param dataInizio   data di riferimento iniziale dell'intervallo
     * @param dataFine     data di riferimento finale dell'intervallo
     * @return vero se la data è compresa nell'intervallo di date
     */
    public void isCompresaEsclusi() {
        Date dataUno = null;
        Date dataDue = null;
        Date dataTre = null;

        boolPrevisto = true;
        boolOttenuto = LibDate.isCompresaEsclusi(DATA_DUE, DATA_QUATTRO, DATA_UNO);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = false;
        boolOttenuto = LibDate.isCompresaEsclusi(DATA_DUE, DATA_UNO, DATA_QUATTRO);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = false;
        boolOttenuto = LibDate.isCompresaEsclusi(DATA_DUE, DATA_DUE, DATA_UNO);
        assertEquals(boolOttenuto, boolPrevisto);

        dataUno = LibDate.creaData(17, 5, ANNO);
        dataDue = LibDate.creaData(1, 5, ANNO);
        dataTre = LibDate.creaData(30, 5, ANNO);
        boolPrevisto = true;
        boolOttenuto = LibDate.isCompresaEsclusi(dataUno, dataDue, dataTre);
        assertEquals(boolOttenuto, boolPrevisto);

        dataUno = LibDate.creaData(17, 11, ANNO);
        dataDue = LibDate.creaData(1, 5, ANNO);
        dataTre = LibDate.creaData(30, 5, ANNO);
        boolPrevisto = false;
        boolOttenuto = LibDate.isCompresaEsclusi(dataUno, dataDue, dataTre);
        assertEquals(boolOttenuto, boolPrevisto);
    }// end of single test


    @Test
    /**
     * Controlla se una data è compresa tra due date di riferimento.
     * <p>
     * Esegue solo se le date sono valide (non nulle e non vuote) <br>
     * Gli estremi SONO compresi <br>
     * Se la data coincide con uno degli estremi, la risposta è TRUE <br>
     *
     * @param dataTest data da controllare
     * @param dataInizio   data di riferimento iniziale dell'intervallo
     * @param dataFine     data di riferimento finale dell'intervallo
     * @return vero se la data è compresa nell'intervallo di date
     */
    public void isCompresaUguale() {
        Date dataUno = null;
        Date dataDue = null;
        Date dataTre = null;

        boolPrevisto = true;
        boolOttenuto = LibDate.isCompresaUguale(DATA_DUE, DATA_QUATTRO, DATA_UNO);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = false;
        boolOttenuto = LibDate.isCompresaUguale(DATA_DUE, DATA_UNO, DATA_QUATTRO);
        assertEquals(boolOttenuto, boolPrevisto);

        boolPrevisto = true;
        boolOttenuto = LibDate.isCompresaUguale(DATA_DUE, DATA_DUE, DATA_UNO);
        assertEquals(boolOttenuto, boolPrevisto);

        dataUno = LibDate.creaData(17, 5, ANNO);
        dataDue = LibDate.creaData(1, 5, ANNO);
        dataTre = LibDate.creaData(30, 5, ANNO);
        boolPrevisto = true;
        boolOttenuto = LibDate.isCompresaUguale(dataUno, dataDue, dataTre);
        assertEquals(boolOttenuto, boolPrevisto);

        dataUno = LibDate.creaData(17, 11, ANNO);
        dataDue = LibDate.creaData(1, 5, ANNO);
        dataTre = LibDate.creaData(30, 5, ANNO);
        boolPrevisto = false;
        boolOttenuto = LibDate.isCompresaUguale(dataUno, dataDue, dataTre);
        assertEquals(boolOttenuto, boolPrevisto);
    }// end of single test


    @Test
    /**
     * Ritorna la data del primo giorno del mese relativo a una data fornita.
     * <p>
     *
     * @param dataIn fornita
     * @return la data rappresentante il giorno del mese
     */
    public void getInizioMese() {
        Date dataSorgente = LibDate.creaData(GIORNO, MESE, ANNO);

        dataPrevista = LibDate.creaData(1, MESE, ANNO);
        dataOttenuta = LibDate.getInizioMese(dataSorgente);
        assertEquals(dataOttenuta, dataPrevista);
    }// end of method

    @Test
    /**
     * Ritorna la data dell'ultimo giorno del mese relativo a una data fornita.
     * <p>
     *
     * @param dataIn fornita
     * @return la data rappresentante l'ultimo giorno del mese
     */
    public void getFineMese() {
        Date dataSorgente = LibDate.creaData(GIORNO, MESE, ANNO);

        dataPrevista = LibDate.creaData(31, MESE, ANNO);
        dataOttenuta = LibDate.getFineMese(dataSorgente);
        assertEquals(dataOttenuta, dataPrevista);
    }// end of method

}// end of test class