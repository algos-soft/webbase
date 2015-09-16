package it.algos.webbase.domain.pref;

import java.util.ArrayList;

/**
 * Created by Gac on 17 lug 2015.
 * Typi usati nelle preferenze
 */
public enum TypePref {
    booleano("booleano", "booleano", "booleano"),
    stringa("stringa", "stringa", "stringa"),
    intero("intero", "intero", "intero"),
    lista("array", "array", "array"),
    lungo("lungo", "lungo", "lungo"),
    reale("reale", "reale", "reale"),
    doppio("doppio", "doppio", "doppio"),
    decimale("decimale", "decimale", "decimale"),
    data("data", "data", "data"),
    testo("testo", "testo", "testo");

    String nomeDB;

    String nomeListView;

    String nomeFormView;

    TypePref(String nomeDB, String nomeListView, String nomeFormView) {
        this.setNomeDB(nomeDB);
        this.setNomeListView(nomeListView);
        this.setNomeFormView(nomeFormView);
    }// fine del costruttore

    String getNomeDB() {
        return nomeDB;
    }

    void setNomeDB(String nomeDB) {
        this.nomeDB = nomeDB;
    }

    public static ArrayList<String> allForm() {
        ArrayList<String> lista = new ArrayList<>();

        for (TypePref pref : values()) {
            lista.add(pref.getNomeFormView());
        } // fine del ciclo for-each

        return lista;
    }

    String getNomeListView() {
        return nomeListView;
    }

    void setNomeListView(String nomeListView) {
        this.nomeListView = nomeListView;
    }

    String getNomeFormView() {
        return nomeFormView;
    }

    void setNomeFormView(String nomeFormView) {
        this.nomeFormView = nomeFormView;
    }
}// fine della classe Enumeration
