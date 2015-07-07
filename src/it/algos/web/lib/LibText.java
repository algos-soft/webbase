package it.algos.web.lib;

public class LibText {

    public static final String REF = "<ref>";
    public static final String NOTE = "<!--";
    public static final String GRAFFE = "{{";

    /**
     * Converts multiple spaces to single spaces.
     *
     * @param string in entrata
     * @return string in uscita
     */
    public static String zapMultiSpaces(String string) {
        return string.replaceAll("\\s+", " ");
    }// end of method

    /**
     * Removes heading and trailing spaces and converts multiple spaces to single spaces.
     *
     * @param string in entrata
     * @return string in uscita
     */
    public static String fixSpaces(String string) {
        string = string.trim();
        string = zapMultiSpaces(string);
        return string;
    }// end of method


    /**
     * Forza il primo carattere della stringa secondo il flag
     * <p>
     * Se la stringa è nulla, ritorna un nullo
     * Se la stringa è vuota, ritorna una stringa vuota
     *
     * @param entrata stringa in ingresso
     * @param flag    maiuscolo o minuscolo
     * @return uscita string in uscita
     */
    private static String primoCarattere(String entrata, PrimoCarattere flag) {
        String uscita = entrata;
        String primo;
        String rimanente;

        if (entrata != null && !entrata.equals("")) {
            primo = entrata.substring(0, 1);
            switch (flag) {
                case maiuscolo:
                    primo = primo.toUpperCase();
                    break;
                case minuscolo:
                    primo = primo.toLowerCase();
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch
            rimanente = entrata.substring(1);
            uscita = primo + rimanente;
        }// fine del blocco if

        return uscita;
    } // fine del metodo

    /**
     * Forza il primo carattere della stringa a maiuscolo
     * <p>
     * Se la stringa è nulla, ritorna un nullo
     * Se la stringa è vuota, ritorna una stringa vuota
     *
     * @param entrata stringa in ingresso
     * @return uscita string in uscita
     */
    public static String primaMaiuscola(String entrata) {
        return primoCarattere(entrata, PrimoCarattere.maiuscolo);
    } // fine del metodo

    /**
     * Forza il primo carattere della stringa a minuscolo
     * <p>
     * Se la stringa è nulla, ritorna un nullo
     * Se la stringa è vuota, ritorna una stringa vuota
     *
     * @param entrata stringa in ingresso
     * @return uscita string in uscita
     */
    public static String primaMinuscola(String entrata) {
        return primoCarattere(entrata, PrimoCarattere.minuscolo);
    } // fine del metodo


    /**
     * Elimina la testa iniziale della stringa, se esiste. <br>
     * <p>
     * Esegue solo se la stringa è valida. <br>
     * Se manca la testa, restituisce la stringa. <br>
     * Elimina spazi vuoti iniziali e finali. <br>
     *
     * @param entrata stringa in ingresso
     * @param testa   da eliminare
     * @return uscita stringa convertita
     */
    public static String levaTesta(String entrata, String testa) {
        String uscita = entrata;

        if (entrata != null) {
            uscita = entrata.trim();
            if (testa != null) {
                testa = testa.trim();
                if (uscita.startsWith(testa)) {
                    uscita = uscita.substring(testa.length());
                    uscita = uscita.trim();
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        return uscita;
    } // fine del metodo

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
    public static String levaCoda(String entrata, String coda) {
        String uscita = entrata;

        if (entrata != null) {
            uscita = entrata.trim();
            if (coda != null) {
                coda = coda.trim();
                if (uscita.endsWith(coda)) {
                    uscita = uscita.substring(0, uscita.length() - coda.length());
                    uscita = uscita.trim();
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        return uscita;
    } // fine del metodo


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
    public static String levaDopo(String entrata, String tagFinale) {
        String uscita = entrata;
        int pos;

        if (uscita != null && tagFinale != null) {
            uscita = entrata.trim();
            if (uscita.contains(tagFinale)) {
                pos = uscita.indexOf(tagFinale);
                uscita = uscita.substring(0, pos);
                uscita = uscita.trim();
            }// fine del blocco if
        }// fine del blocco if

        return uscita;
    } // fine del metodo

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
    public static String levaDopoRef(String entrata) {
        return levaDopo(entrata, REF);
    } // fine del metodo

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
    public static String levaDopoNote(String entrata) {
        return levaDopo(entrata, NOTE);
    } // fine del metodo

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
    public static String levaDopoGraffe(String entrata) {
        return levaDopo(entrata, GRAFFE);
    } // fine del metodo

    /**
     * Trova nel testo, la prima occorrenza di un tag compreso nella lista di tag
     *
     * @param testo    da controllare
     * @param listaTag tag da trovare (uno o più)
     * @return posizione nel testo del primo tag trovato, zero se non ce ne sono
     */
    public static int trovaPrimo(String testo, String... listaTag) {
        int pos = 0;
        int max = testo.length();
        int singlePos;

        if (listaTag != null) {
            pos = max;
            for (String singleTag : listaTag) {
                singlePos = testo.indexOf(singleTag);
                if (singlePos > 0) {
                    pos = Math.min(pos, singlePos);
                }// fine del blocco if
            }// end of for cycle

            if (pos == max) {
                pos = 0;
            }// fine del blocco if
        }// fine del blocco if

        return pos;
    }// fine del metodo

    /**
     * Enumeration locale per il flag del primo carattere
     */
    private enum PrimoCarattere {
        maiuscolo, minuscolo
    }// fine della classe interna

}// end of static class
