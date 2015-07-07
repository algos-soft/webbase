package it.algos.test;

import it.algos.web.lib.Lib;
import it.algos.web.lib.LibDate;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LibTest {

    @Test
    /**
      * Tries to convert an Object in int.
      *
      * @param obj to convert
      * @return the corresponding int
      */
    public void getInt() {
        Object srgNullo = null;
        short srgShort = 24;
        int srgInt = 24500;
        long srgLong = 35L;
        float srgFloat = 25f;
        double srgDouble = 249.87d;
        boolean srgBool = true;
        char[] srgChar = {'a', 'b', 'c', 'd', 'e'};
        char srgChar2 = '\u0000';
        String srgStringValida = "235";
        String srgStringErrata = "nonSonoUnNumero";
        Date srgDate = new Date();
        int zero = 0;

        Object sorgente = null;
        int previsto = 0;
        Object ottenuto = null;

        // nullo (valore non valido)
        ottenuto = Lib.getInt(srgNullo);
        assertThat(ottenuto, instanceOf(Integer.class));
        assertEquals(ottenuto, zero);

        // short
        previsto = 24;
        ottenuto = Lib.getInt(srgShort);
        assertThat(ottenuto, instanceOf(Integer.class));
        assertEquals(ottenuto, previsto);

        // int
        previsto = srgInt;
        ottenuto = Lib.getInt(srgInt);
        assertThat(ottenuto, instanceOf(Integer.class));
        assertEquals(ottenuto, previsto);

        // long
        previsto = 35;
        ottenuto = Lib.getInt(srgLong);
        assertThat(ottenuto, instanceOf(Integer.class));
        assertEquals(ottenuto, previsto);

        // float
        previsto = 25;
        ottenuto = Lib.getInt(srgFloat);
        assertThat(ottenuto, instanceOf(Integer.class));
        assertEquals(ottenuto, previsto);

        // double
        previsto = 249;
        ottenuto = Lib.getInt(srgDouble);
        assertThat(ottenuto, instanceOf(Integer.class));
        assertEquals(ottenuto, previsto);

        // boolean (valore non valido)
        ottenuto = Lib.getInt(srgBool);
        assertThat(ottenuto, instanceOf(Integer.class));
        assertEquals(ottenuto, zero);

        // char[] (valore non valido)
        ottenuto = Lib.getInt(srgChar);
        assertThat(ottenuto, instanceOf(Integer.class));
        assertEquals(ottenuto, zero);

        // char (valore non valido)
        ottenuto = Lib.getInt(srgChar2);
        assertThat(ottenuto, instanceOf(Integer.class));
        assertEquals(ottenuto, zero);

        // string
        previsto = 235;
        ottenuto = Lib.getInt(srgStringValida);
        assertThat(ottenuto, instanceOf(Integer.class));
        assertEquals(ottenuto, previsto);

        // string (valore non valido)
        ottenuto = Lib.getInt(srgStringErrata);
        assertThat(ottenuto, instanceOf(Integer.class));
        assertEquals(ottenuto, zero);

        // date (valore non valido)
        ottenuto = Lib.getInt(srgDate);
        assertThat(ottenuto, instanceOf(Integer.class));
        assertEquals(ottenuto, zero);

    }// end of single test

    @Test
    public void toStringDDMMYYYY() {
        Date date = null;
        String previsto = "";
        String ottenuto = "";
        // Date dateTmp = new Date(114, 2, 8, 7, 12);
        // long lungoTmp = dateTmp.getTime();
        long lungoData = 1413868320000L; // 21 ottobre 2014, 7 e 12
        long lungoData2 = 1398057120000L; // 21 aprile 2014, 7 e 12
        long lungoData3 = 1412485920000L; // 5 aprile 2014, 7 e 12
        long lungoData4 = 1394259120000L; // 8 marzo 2014, 7 e 12

        // metodo toStringDDMMYYYY
        date = new Date(lungoData);
        previsto = "21-10-2014";
        ottenuto = LibDate.toStringDDMMYYYY(date);
        assertEquals(ottenuto, previsto);

        date = new Date(lungoData2);
        previsto = "21-04-2014";
        ottenuto = LibDate.toStringDDMMYYYY(date);
        assertEquals(ottenuto, previsto);

        date = new Date(lungoData3);
        previsto = "05-10-2014";
        ottenuto = LibDate.toStringDDMMYYYY(date);
        assertEquals(ottenuto, previsto);

        date = new Date(lungoData4);
        previsto = "08-03-2014";
        ottenuto = LibDate.toStringDDMMYYYY(date);
        assertEquals(ottenuto, previsto);

        // metodo toStringDMYYYY
        date = new Date(lungoData);
        previsto = "21-10-2014";
        ottenuto = LibDate.toStringDMYYYY(date);
        assertEquals(ottenuto, previsto);

        date = new Date(lungoData2);
        previsto = "21-4-2014";
        ottenuto = LibDate.toStringDMYYYY(date);
        assertEquals(ottenuto, previsto);

        date = new Date(lungoData3);
        previsto = "5-10-2014";
        ottenuto = LibDate.toStringDMYYYY(date);
        assertEquals(ottenuto, previsto);

        date = new Date(lungoData4);
        previsto = "8-3-2014";
        ottenuto = LibDate.toStringDMYYYY(date);
        assertEquals(ottenuto, previsto);
    }// end of method

//    @Test
//    public void toStringDDMMYYYYHHMM() {
//        Date date = null;
//        String previsto = "";
//        String ottenuto = "";
//        // Date dateTmp = new Date(114, 2, 8, 7, 12);
//        // long lungoTmp = dateTmp.getTime();
//        long lungoData = 1413868320000L; // 21 ottobre 2014, 7 e 12
//
//        date = new Date(lungoData);
//        previsto = "21-10-2014 07:12";
//        ottenuto = LibDate.toStringDDMMYYYYHHMM(date);
//        assertEquals(ottenuto, previsto);
//    }// end of method

    @Test
    public void toStringHHMM() {
        Date date = null;
        String previsto = "";
        String ottenuto = "";
        // Date dateTmp = new Date(114, 2, 8, 7, 12);
        // long lungoTmp = dateTmp.getTime();
        long lungoData = 1413868320000L; // 21 ottobre 2014, 7 e 12

        date = new Date(lungoData);
        previsto = "07:12";
        ottenuto = LibDate.toStringHHMM(date);
        assertEquals(ottenuto, previsto);
    }// end of method

    @Test
    public void toStringDMMMYY() {
        Date date = null;
        String previsto = "";
        String ottenuto = "";
        // Date dateTmp = new Date(114, 2, 8, 7, 12);
        // long lungoTmp = dateTmp.getTime();
        long lungoData = 1394259120000L; // 8 marzo 2014, 7 e 12

        date = new Date(lungoData);
        previsto = "8-mar-14";
        ottenuto = LibDate.toStringDMMMYY(date);
        assertEquals(ottenuto, previsto);
    }// end of method

    @Test
    public void fromInizioMeseAnno() {
        Date date = null;
        String mese = "";
        int anno = 0;
        String ottenuto = "";
        String previsto = "";

//        mese = "agosto";
//        anno = 2014;
//        previsto = "01-08-2014 00:00";
//        date = LibDate.fromInizioMeseAnno(mese, anno);
//        ottenuto = LibDate.toStringDDMMYYYYHHMM(date);
//        assertEquals(ottenuto, previsto);

        mese = "dicembre";
        anno = 2014;
        previsto = "1-dic-14";
        date = LibDate.fromInizioMeseAnno(mese, anno);
        ottenuto = LibDate.toStringDMMMYY(date);
        assertEquals(ottenuto, previsto);

        mese = "gennaio";
        anno = 2015;
        previsto = "1-gen-15";
        date = LibDate.fromInizioMeseAnno(mese, anno);
        ottenuto = LibDate.toStringDMMMYY(date);
        assertEquals(ottenuto, previsto);

        mese = "febbraio";
        anno = 2015;
        previsto = "1-feb-15";
        date = LibDate.fromInizioMeseAnno(mese, anno);
        ottenuto = LibDate.toStringDMMMYY(date);
        assertEquals(ottenuto, previsto);

    }// end of method

    @Test
    public void fromFineMeseAnno() {
        Date date = null;
        String mese = "";
        int anno = 0;
        String ottenuto = "";
        String previsto = "";

        mese = "novembre";
        anno = 2014;
        previsto = "30-11-2014 23:59:59";
        date = LibDate.fromFineMeseAnno(mese, anno);
        ottenuto = new DateTime(date).toString("dd-MM-YYYY HH:mm:ss");
        assertEquals(ottenuto, previsto);

        mese = "dicembre";
        anno = 2014;
        previsto = "31-dic-14";
        date = LibDate.fromFineMeseAnno(mese, anno);
        ottenuto = LibDate.toStringDMMMYY(date);
        assertEquals(ottenuto, previsto);

        mese = "gennaio";
        anno = 2015;
        previsto = "31-gen-15";
        date = LibDate.fromFineMeseAnno(mese, anno);
        ottenuto = LibDate.toStringDMMMYY(date);
        assertEquals(ottenuto, previsto);

        mese = "febbraio";
        anno = 2015;
        previsto = "28-feb-15";
        date = LibDate.fromFineMeseAnno(mese, anno);
        ottenuto = LibDate.toStringDMMMYY(date);
        assertEquals(ottenuto, previsto);
    }// end of method

    @Test
    public void toStringDMYY() {
        Date date = null;
        String previsto = "";
        String ottenuto = "";
        // Date dateTmp = new Date(114, 2, 8, 7, 12);
        // long lungoTmp = dateTmp.getTime();
        long lungoData = 1394259120000L; // 8 marzo 2014, 7 e 12

        date = new Date(lungoData);
        previsto = "8-3-14";
        ottenuto = LibDate.toStringDMYY(date);
        assertEquals(ottenuto, previsto);

        date = new Date(lungoData);
        previsto = "8-3-14 07:12";
        ottenuto = LibDate.toStringDMYYHHMM(date);
        assertEquals(ottenuto, previsto);
    }// end of method

}// end of test class