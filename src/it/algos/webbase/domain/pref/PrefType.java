package it.algos.webbase.domain.pref;

import java.nio.charset.Charset;

/**
 * Created by gac on 30 lug 2016.
 * Enum dei tipi di preferenza supportati
 */
public enum PrefType {

    string("stringa") {
        @Override
        public byte[] objectToBytes(Object obj) {
            byte[] bytes = new byte[0];
            if (obj instanceof String) {
                String stringa = (String) obj;
                bytes = stringa.getBytes(Charset.forName("UTF-8"));
            }// end of if cycle
            return bytes;
        }// end of method

        @Override
        public Object bytesToObject(byte[] bytes) {
            Object obj = null;
            obj = new String(bytes, Charset.forName("UTF-8"));
            return obj;
        }// end of method
    },// end of single enumeration

    bool("booleano") {
        @Override
        public byte[] objectToBytes(Object obj) {
            byte[] bytes = new byte[0];
            if (obj instanceof Boolean) {
                boolean bool = (boolean) obj;
                bytes = new byte[]{(byte) (bool ? 1 : 0)};
            }// end of if cycle
            return bytes;
        }// end of method

        @Override
        public Object bytesToObject(byte[] bytes) {
            Object obj = null;
            if (bytes.length > 0) {
                byte b = bytes[0];
                obj = new Boolean(b == (byte) 0b00000001);
            } else {
                obj = new Boolean(false);
            }// end of if/else cycle
            return obj;
        }// end of method
    },// end of single enumeration

    integer("intero"),
    decimal("decimale"),
    date("data"),
    bytes("blog");

    String nome;


    PrefType(String nome) {
        this.setNome(nome);
    }// fine del costruttore

    /**
     * Converte un valore Object in ByteArray per questa preferenza.
     * Sovrascritto
     *
     * @param obj il valore Object
     * @return il valore convertito in byte[]
     */
    public byte[] objectToBytes(Object obj) {
        return null;
    }// end of method


    /**
     * Converte un byte[] in Object del tipo adatto per questa preferenza.
     * Sovrascritto
     *
     * @param bytes il valore come byte[]
     * @return il valore convertito nell'oggetto del tipo adeguato
     */
    public Object bytesToObject(byte[] bytes) {
        return null;
    }// end of method

    /**
     * Writes a value in the storage for this type of preference
     * Sovrascritto
     *
     * @param value the value
     */
    public void put(Object value) {
    }// end of method

    /**
     * Retrieves the value of this preference's type
     * Sovrascritto
     */
    public Object get() {
        return null;
    }// end of method


    public String getNome() {
        return nome;
    }// end of getter method

    public void setNome(String nome) {
        this.nome = nome;
    }//end of setter method

}// end of enumeration class
