package it.algos.web.lib;

/**
 * Created by gac on 02 ago 2015.
 *
 */
public class LibPath {

    /**
     * Estrae il nome significativo di una classe
     * Esclude tutto il path precdente il nome significativo, fino all'ultimo . (punto) del percorso
     *
     * @param classe in entrata
     * @return nome significativo della class
     */
    public static String getClassName(Object classe) {
        return getClassName(classe.getClass());
    } // fine del metodo

    /**
     * Estrae il nome significativo di una classe
     * Esclude tutto il path precdente il nome significativo, fino all'ultimo . (punto) del percorso
     *
     * @param claz in entrata
     * @return nome significativo della class
     */
    public static String getClassName(Class claz) {
        String nomeSignificativo = "";
        String nomeCanonico = "";
        String tag = ".";

        if (claz != null) {
            nomeCanonico = claz.getCanonicalName();
        }// end of if cycle

        if (!nomeCanonico.equals("")) {
            if (nomeCanonico.contains(tag)) {
                nomeSignificativo = nomeCanonico.substring(nomeCanonico.lastIndexOf(tag) + 1);
            } else {
                nomeSignificativo = nomeCanonico;
            }// fine del blocco if-else
        }// fine del blocco if

        return nomeSignificativo;
    } // fine del metodo

}// end of static class
