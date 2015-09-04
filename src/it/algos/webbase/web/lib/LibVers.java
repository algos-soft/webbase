package it.algos.webbase.web.lib;

import it.algos.webbase.domain.vers.Versione;

import java.util.Date;
import java.util.List;

public abstract class LibVers {

    private static int getVersione() {
        int numero = 0;
        List<Versione> lista;

        lista = Versione.findAll();
        if (lista != null && lista.size() > 0) {
            numero = lista.get(0).getNumero();
        }// fine del blocco if

        return numero;
    }// fine del metodo statico

    //--patch
    //--mancando la possibilità (29-7-15) di un sort della lista
    //--controllo il valore massimo, per sicurezza (dovrebbe essere l'ultimo)
    private static int getMaxVersione() {
        int numero = 0;
        List<Versione> lista;

        lista = Versione.findAll();
        if (lista != null) {
            for (Versione vers : lista) {
                numero = Math.max(numero, vers.getNumero());
            } // fine del ciclo for-each
        }// fine del blocco if

        return numero;
    }// fine del metodo statico

    //--controlla la versione installata
    public static boolean installa(int numeroVersioneDaInstallare) {
        boolean installa = false;
        int numeroVersioneEsistente = LibVers.getMaxVersione();

        if (numeroVersioneDaInstallare > numeroVersioneEsistente) {
            installa = true;
        }// fine del blocco if

        return installa;
    }// fine del metodo statico

    //--crea una versione
    //--la crea SOLO se non esiste già
    public static void nuova(String titolo, String descrizione) {
        int numero = getMaxVersione() + 1;
        nuova(numero, titolo, descrizione);
    }// fine del metodo statico

    //--crea una versione
    //--la crea SOLO se non esiste già
    public static void nuova(int numero, String titolo, String descrizione) {
        Versione versione;
        Date giorno = new Date();

        versione = new Versione();
        versione.setNumero(numero);
        versione.setGiorno(giorno);
        versione.setTitolo(titolo);
        versione.setDescrizione(descrizione);
        versione.save();
    }// fine del metodo statico

} // fine della classe astratta
