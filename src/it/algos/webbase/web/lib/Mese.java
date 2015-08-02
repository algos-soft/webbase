package it.algos.webbase.web.lib;

import java.util.ArrayList;

public enum Mese {

    gennaio("gen", "gennaio", 31),
    febbraio("feb", "febbraio", 28),
    marzo("mar", "marzo", 31),
    aprile("apr", "aprile", 30),
    maggio("mag", "maggio", 31),
    giugno("giu", "giugno", 30),
    luglio("lug", "luglio", 31),
    agosto("ago", "agosto", 31),
    settembre("set", "settembre", 30),
    ottobre("ott", "ottobre", 31),
    novembre("nov", "novembre", 30),
    dicembre("dic", "dicembre", 31);

    String breve;
    String lungo;
    int giorni;


    /**
     * Costruttore interno dell'Enumeration
     */
    Mese(String breve, String lungo, int giorni) {
        this.breve = breve;
        this.lungo = lungo;
        this.giorni = giorni;
    }// fine del costruttore interno


    /**
     * Numero di giorni del mese
     *
     * @param numMeseDellAnno L'anno parte da gennaio che è il mese numero 1
     * @return Numero di giorni del mese
     */
    public static int getGiorni(int numMeseDellAnno) {
        int giorniDelMese = 0;
        Mese mese = getMese(numMeseDellAnno);

        if (mese != null) {
            giorniDelMese = mese.giorni;
        }// fine del blocco if

        return giorniDelMese;
    }// fine del metodo statico


    /**
     * Mese
     *
     * @param numMeseDellAnno L'anno parte da gennaio che è il mese numero 1
     * @return Mese
     */
    public static Mese getMese(int numMeseDellAnno) {
        Mese mese = null;

        if (numMeseDellAnno > 0 && numMeseDellAnno < 13) {
            numMeseDellAnno = numMeseDellAnno - 1;
            for (Mese meseTmp : Mese.values()) {
                if (meseTmp.ordinal() == numMeseDellAnno) {
                    mese = meseTmp;
                }// fine del blocco if
            }// end of for cycle
        }// fine del blocco if

        return mese;
    }// fine del metodo statico


    /**
     * Mese
     *
     * @param nomeBreveLungo Nome breve o lungo del mese
     * @return Mese
     */
    public static Mese getMese(String nomeBreveLungo) {
        Mese mese = null;
        String nomeBreveLungoMinuscolo;

        if (nomeBreveLungo != null && !nomeBreveLungo.equals("")) {
            nomeBreveLungoMinuscolo = nomeBreveLungo.toLowerCase();
            for (Mese meseTmp : Mese.values()) {
                if (meseTmp.breve.equals(nomeBreveLungoMinuscolo) || meseTmp.lungo.equals(nomeBreveLungoMinuscolo)) {
                    mese = meseTmp;
                }// fine del blocco if
            }// end of for cycle
        }// fine del blocco if

        return mese;
    }// fine del metodo statico


    /**
     * Numero del mese nell'anno
     *
     * @param nomeBreveLungo L'anno parte da gennaio che è il mese numero 1
     * @return Numero del mese
     */
    public static int getOrd(String nomeBreveLungo) {
        int numMeseDellAnno = 0;
        Mese mese = getMese(nomeBreveLungo);

        if (mese != null) {
            numMeseDellAnno = mese.ordinal();
            numMeseDellAnno = numMeseDellAnno + 1;
        }// fine del blocco if

        return numMeseDellAnno;
    }// fine del metodo statico


    // l'anno parte da gennaio che è il numero 1
    private static String getMese(int ord, boolean flagBreve) {
        String nome = "";
        Mese mese = null;

        mese = getMese(ord);
        if (mese != null) {
            if (flagBreve) {
                nome = mese.breve;
            } else {
                nome = mese.lungo;
            }// fine del blocco if-else
        }// fine del blocco if

        return nome;
    }// fine del metodo statico


    /**
     * Nome breve del mese
     *
     * @param numMeseDellAnno L'anno parte da gennaio che è il mese numero 1
     * @return Nome breve del mese
     */
    public static String getShort(int numMeseDellAnno) {
        return getMese(numMeseDellAnno, true);
    }// fine del metodo statico


    /**
     * Nome completo del mese
     *
     * @param numMeseDellAnno L'anno parte da gennaio che è il mese numero 1
     * @return Nome breve del mese
     */
    public static String getLong(int numMeseDellAnno) {
        return getMese(numMeseDellAnno, false);
    }// fine del metodo statico


    /**
     * Elenco di tutti i nomi in forma breve
     *
     * @return Stringa dei nomi brevi separati da virgola
     */
    public static String getAllShortString() {
        String stringa = "";
        String sep = ", ";

        for (Mese mese : Mese.values()) {
            stringa += mese.breve;
            stringa += sep;
        }// end of for cycle
        stringa = LibText.levaCoda(stringa, sep);

        return stringa;
    }// fine del metodo statico


    /**
     * Elenco di tutti i nomi in forma completa
     *
     * @return Stringa dei nomi completi separati da virgola
     */
    public static String getAllLongString() {
        String stringa = "";
        String sep = ", ";

        for (Mese mese : Mese.values()) {
            stringa += mese.lungo;
            stringa += sep;
        }// end of for cycle
        stringa = LibText.levaCoda(stringa, sep);

        return stringa;
    }// fine del metodo statico


    /**
     * Elenco di tutti i nomi in forma breve
     *
     * @return Array dei nomi brevi
     */
    public static ArrayList<String> getAllShortList() {
        ArrayList<String> lista = new ArrayList<String>();

        for (Mese mese : Mese.values()) {
            lista.add(mese.breve);
        }// end of for cycle

        return lista;
    }// fine del metodo statico


    /**
     * Elenco di tutti i nomi in forma completa
     *
     * @return Array dei nomi completi
     */
    public static ArrayList<String> getAllLongList() {
        ArrayList<String> lista = new ArrayList<String>();

        for (Mese mese : Mese.values()) {
            lista.add(mese.lungo);
        }// end of for cycle

        return lista;
    }// fine del metodo statico


    public int getOrd() {
        return this.ordinal() + 1;
    }// fine del metodo

}// fine della classe Enumeration
