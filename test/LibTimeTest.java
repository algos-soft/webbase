import it.algos.webbase.web.lib.LibTime;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Gac on 10 set 2015.
 * .
 */
public class LibTimeTest {

    private static final long MAX_MILLISEC = 1000;
    private static final long MAX_SECONDI = MAX_MILLISEC * 60;
    private static final long MAX_MINUTI = MAX_SECONDI * 60;
    private static final long MAX_ORE = MAX_MINUTI * 24;

    private static final long DELTA = 2L;
    private long longPrevisto;
    private long longOttenuto;

    // alcuni parametri utilizzati
    private Timestamp timePrevisto;
    private Timestamp timeOttenuto;
    private long sorgente;
    private String previsto;
    private String ottenuto;

    @Before
    public void s() {
        longPrevisto = System.currentTimeMillis();
        timePrevisto = new Timestamp(System.currentTimeMillis());
    } // end of single test

    @Test
    /**
     * Tempo attuale (current) espresso come mumero long
     *
     * @return currentTimeMillis
     */
    public void currentLong() {
        longOttenuto = LibTime.currentLong();
        assertTrue(longOttenuto - longPrevisto < DELTA);
    } // end of single test

    @Test
    /**
     * Tempo attuale (current) espresso come mumero long
     *
     * @return currentTimeMillis
     */
    public void adessoLong() {
        longOttenuto = LibTime.adessoLong();
        assertTrue(longOttenuto - longPrevisto < DELTA);
    } // end of single test

    @Test
    /**
     * Tempo attuale (current) espresso come mumero long
     *
     * @return currentTimeMillis
     */
    public void getLong() {
        longOttenuto = LibTime.getLong();
        assertTrue(longOttenuto - longPrevisto < DELTA);
    } // end of single test

    @Test
    /**
     * Tempo attuale (current) espresso in Timestamp
     *
     * @return current Timestamp
     */
    public void current() {
        timeOttenuto = LibTime.current();
        assertTrue(timeOttenuto.getTime() - timePrevisto.getTime() < DELTA);
    } // end of single test

    @Test
    /**
     * Tempo attuale (current) espresso in Timestamp
     *
     * @return current Timestamp
     */
    public void adesso() {
        timeOttenuto = LibTime.adesso();
        assertTrue(timeOttenuto.getTime() - timePrevisto.getTime() < DELTA);
    } // end of single test

    @Test
    /**
     * Tempo attuale (current) espresso in Timestamp
     *
     * @return current Timestamp
     */
    public void get() {
        timeOttenuto = LibTime.get();
        assertTrue(timeOttenuto.getTime() - timePrevisto.getTime() < DELTA);
    } // end of single test

    @Test
    /**
     * Restituisce come stringa (intelligente) un durata espressa in long
     *
     * @return tempo in forma leggibile
     */
    public void toText() {
        sorgente = 500;
        previsto = "meno di un sec.";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 27 * MAX_MILLISEC;
        previsto = "27 sec.";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 27200;
        previsto = "27 sec.";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 27900;
        previsto = "28 sec.";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 59000;
        previsto = "59 sec.";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 59200;
        previsto = "59 sec.";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 59500;
        previsto = "1 min.";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 60000;
        previsto = "1 min.";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 124 * MAX_MILLISEC;
        previsto = "2 min.";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 170 * MAX_MILLISEC;
        previsto = "3 min.";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 3599 * MAX_MILLISEC;
        previsto = "1 ora";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 60 * MAX_SECONDI;
        previsto = "1 ora";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 115 * MAX_SECONDI;
        previsto = "2 ore";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 120 * MAX_SECONDI;
        previsto = "2 ore";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 125 * MAX_SECONDI;
        previsto = "2 ore";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 1380 * MAX_SECONDI;
        previsto = "23 ore";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 1430 * MAX_SECONDI;
        previsto = "1 giorno";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 24 * MAX_MINUTI;
        previsto = "1 giorno";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 25 * MAX_MINUTI;
        previsto = "1 giorno";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 35 * MAX_MINUTI;
        previsto = "1 giorno";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 36 * MAX_MINUTI;
        previsto = "2 gg.";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 365 * MAX_ORE;
        previsto = "1 anno";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 700 * MAX_ORE;
        previsto = "2 anni";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 720 * MAX_ORE;
        previsto = "2 anni";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 730 * MAX_ORE;
        previsto = "2 anni";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 910 * MAX_ORE;
        previsto = "2 anni";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);

        sorgente = 930 * MAX_ORE;
        previsto = "3 anni";
        ottenuto = LibTime.toText(sorgente);
        assertEquals(ottenuto, previsto);
    } // end of single test

    @Test
    /**
     * Restituisce come stringa (intelligente) la differenza tra due date
     *
     * @return tempo in forma leggibile
     */
    public void difText() {
        GregorianCalendar calUno = new GregorianCalendar(2015, 3, 8, 17, 12, 14);
        GregorianCalendar calDue = new GregorianCalendar(2015, 3, 8, 17, 12, 25);
        GregorianCalendar calTre = new GregorianCalendar(2015, 3, 8, 17, 27, 34);
        GregorianCalendar calQuattro = new GregorianCalendar(2015, 3, 8, 21, 27, 34);
        GregorianCalendar calCinque = new GregorianCalendar(2015, 3, 25, 21, 27, 34);
        Date dataUno = calUno.getTime();
        Date dataDue = calDue.getTime();
        Date dataTre = calTre.getTime();
        Date dataQuattro = calQuattro.getTime();
        Date dataCinque = calCinque.getTime();
        Timestamp timeUno = new Timestamp(dataUno.getTime());
        Timestamp timeDue = new Timestamp(dataDue.getTime());
        Timestamp timeTre = new Timestamp(dataTre.getTime());
        Timestamp timeQuattro = new Timestamp(dataQuattro.getTime());
        Timestamp timeCinque = new Timestamp(dataCinque.getTime());

        previsto = "11 sec.";
        ottenuto = LibTime.difText(dataUno, dataDue);
        assertEquals(ottenuto, previsto);
        ottenuto = LibTime.difText(timeUno, timeDue);
        assertEquals(ottenuto, previsto);

        previsto = "15 min.";
        ottenuto = LibTime.difText(dataUno, dataTre);
        assertEquals(ottenuto, previsto);
        ottenuto = LibTime.difText(timeUno, timeTre);
        assertEquals(ottenuto, previsto);

        previsto = "4 ore";
        ottenuto = LibTime.difText(dataUno, dataQuattro);
        assertEquals(ottenuto, previsto);
        ottenuto = LibTime.difText(timeUno, timeQuattro);
        assertEquals(ottenuto, previsto);

        previsto = "17 gg.";
        ottenuto = LibTime.difText(dataUno, dataCinque);
        assertEquals(ottenuto, previsto);
        ottenuto = LibTime.difText(timeUno, timeCinque);
        assertEquals(ottenuto, previsto);
    } // end of single test

    @Test
    /**
     * Restituisce come stringa (intelligente) la differenza tra una data e il momento attuale
     *
     * @param inizio del controllo
     *
     * @return tempo in forma leggibile
     */
    public void difText2() {
        previsto = "meno di un sec.";
        ottenuto = LibTime.difText(longPrevisto);
        assertEquals(ottenuto, previsto);
    } // end of single test

}// end of test class
