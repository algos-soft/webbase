package it.algos.webbase.web.lib;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by gac on 06 set 2015.
 * .
 */
public abstract class LibTime {

    private static final String INFERIORE_SECONDO = "meno di un sec.";
    private static final String SECONDI = " sec.";
    private static final String MINUTI = " min.";
    private static final String ORA = " ora";
    private static final String ORE = " ore";
    private static final String GIORNO = " giorno";
    private static final String GIORNI = " gg.";
    private static final String ANNO = " anno";
    private static final String ANNI = " anni";

    private static final long MAX_MILLISEC = 1000;
    private static final long MAX_SECONDI = MAX_MILLISEC * 60;
    private static final long MAX_MINUTI = MAX_SECONDI * 60;
    private static final long MAX_ORE = MAX_MINUTI * 24;
    private static final long MAX_GIORNI = MAX_ORE * 365;

    /**
     * Tempo attuale (current) espresso come mumero long
     *
     * @return currentTimeMillis
     */
    public static long currentLong() {
        return getBaseLong();
    }// end of static method

    /**
     * Tempo attuale (current) espresso come mumero long
     *
     * @return currentTimeMillis
     */
    public static long adessoLong() {
        return getBaseLong();
    }// end of static method

    /**
     * Tempo attuale (current) espresso come mumero long
     *
     * @return currentTimeMillis
     */
    public static long getLong() {
        return getBaseLong();
    }// end of static method

    /**
     * Tempo attuale (current) espresso in Timestamp
     *
     * @return current Timestamp
     */
    public static Timestamp current() {
        return getBase();
    }// end of static method

    /**
     * Tempo attuale (current) espresso in Timestamp
     *
     * @return current Timestamp
     */
    public static Timestamp adesso() {
        return getBase();
    }// end of static method

    /**
     * Tempo attuale (current) espresso in Timestamp
     *
     * @return current Timestamp
     */
    public static Timestamp get() {
        return getBase();
    }// end of static method

    /**
     * Restituisce come stringa (intelligente) la differenza tra una data e il momento attuale
     *
     * @param inizio del controllo
     *
     * @return tempo in forma leggibile
     */
    public static String difText(long inizio) {
        return toText(getLong() - inizio);
    }// end of static method

    /**
     * Restituisce come stringa (intelligente) la differenza tra due date
     *
     * @return tempo in forma leggibile
     */
    public static String difText(Date prima, Date seconda) {
        return toText(seconda.getTime() - prima.getTime());
    }// end of static method

    /**
     * Restituisce come stringa (intelligente) la differenza tra due timestamp
     *
     * @return tempo in forma leggibile
     */
    public static String difText(Timestamp primo, Timestamp secondo) {
        return toText(secondo.getTime() - primo.getTime());
    }// end of static method

    /**
     * Restituisce come stringa (intelligente) un durata espressa in long
     *
     * @return tempo in forma leggibile
     */
    public static String toText(long durata) {
        String tempo = "null";
        long div;
        long mod;

        if (durata < MAX_MILLISEC) {
            tempo = INFERIORE_SECONDO;
        } else {
            if (durata < MAX_SECONDI) {
                div = Math.floorDiv(durata, MAX_MILLISEC);
                mod = Math.floorMod(durata, MAX_MILLISEC);
                if (mod >= MAX_MILLISEC / 2) {
                    div++;
                }// fine del blocco if
                if (div < 60) {
                    tempo = div + SECONDI;
                } else {
                    tempo = "1" + MINUTI;
                }// fine del blocco if-else
            } else {
                if (durata < MAX_MINUTI) {
                    div = Math.floorDiv(durata, MAX_SECONDI);
                    mod = Math.floorMod(durata, MAX_SECONDI);
                    if (mod >= MAX_SECONDI / 2) {
                        div++;
                    }// fine del blocco if
                    if (div < 60) {
                        tempo = div + MINUTI;
                    } else {
                        tempo = "1" + ORA;
                    }// fine del blocco if-else
                } else {
                    if (durata < MAX_ORE) {
                        div = Math.floorDiv(durata, MAX_MINUTI);
                        mod = Math.floorMod(durata, MAX_MINUTI);
                        if (mod >= MAX_MINUTI / 2) {
                            div++;
                        }// fine del blocco if
                        if (div < 24) {
                            if (div == 1) {
                                tempo = div + ORA;
                            } else {
                                tempo = div + ORE;
                            }// fine del blocco if-else
                        } else {
                            tempo = "1" + GIORNO;
                        }// fine del blocco if-else
                    } else {
                        if (durata < MAX_GIORNI) {
                            div = Math.floorDiv(durata, MAX_ORE);
                            mod = Math.floorMod(durata, MAX_ORE);
                            if (mod >= MAX_ORE / 2) {
                                div++;
                            }// fine del blocco if
                            if (div < 365) {
                                if (div == 1) {
                                    tempo = div + GIORNO;
                                } else {
                                    tempo = div + GIORNI;
                                }// fine del blocco if-else
                            } else {
                                tempo = "1" + ANNO;
                            }// fine del blocco if-else
                        } else {
                            div = Math.floorDiv(durata, MAX_GIORNI);
                            mod = Math.floorMod(durata, MAX_GIORNI);
                            if (mod >= MAX_GIORNI / 2) {
                                div++;
                            }// fine del blocco if
                            if (div == 1) {
                                tempo = div + ANNO;
                            } else {
                                tempo = div + ANNI;
                            }// fine del blocco if-else
                        }// fine del blocco if-else
                    }// fine del blocco if-else
                }// fine del blocco if-else
            }// fine del blocco if-else
        }// fine del blocco if-else

        return tempo;
    }// end of static method

    private static Timestamp getBase() {
        return new Timestamp(getBaseLong());
    }// end of static method

    private static long getBaseLong() {
        return System.currentTimeMillis();
    }// end of static method

}// end of abstract static class
