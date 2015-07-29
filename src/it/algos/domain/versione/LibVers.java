package it.algos.domain.versione;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class LibVers {

    private static int getVersione() {
        int numero = 0;
        List<Versione> lista;

        lista = Versione.findAll();
        if (lista != null && lista.size() > 0) {
            numero = lista.get(0).numero;
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
                numero = Math.max(numero,vers.numero);
            } // fine del ciclo for-each
        }// fine del blocco if

        return numero;
    }// fine del metodo statico

    //--controlla la versione installata
    public static boolean installaVersione(int numeroVersioneDaInstallare) {
        boolean installa = false;
        int numeroVersioneEsistente = LibVers.getMaxVersione();

        if (numeroVersioneDaInstallare > numeroVersioneEsistente) {
            installa = true;
        }// fine del blocco if

        return installa;
    }// fine del metodo statico

    //--crea una versione
    //--la crea SOLO se non esiste già
    public static void newVersione(String titolo, String descrizione) {
        Versione versione;
        int numero = getVersione() + 1;
        Date giorno = new Date();

        versione = new Versione();
        versione.numero = numero;
        versione.giorno = giorno;
        versione.titolo = titolo;
        versione.descrizione = descrizione;
        versione.save();
    }// fine del metodo statico

} // fine della classe astratta
