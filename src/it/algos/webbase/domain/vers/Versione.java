package it.algos.webbase.domain.vers;
/**
 * Created by Gac on 17 lug 2015.
 * Using specific Templates (Entity, Domain, Modulo)
 */

import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.query.AQuery;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
public class Versione extends BaseEntity {

    private int numero;

    @NotEmpty
    private String titolo = "";

    private String descrizione;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date giorno = new Date();


    public Versione() {
        this("");
    }// end of constructor

    public Versione(String titolo) {
        super();
        this.titolo = titolo;
    }// end of constructor

    /**
     * Recupera una istanza di Versione usando la query specifica
     *
     * @return istanza di Versione, null se non trovata
     */
    public static Versione find(long id) {
        Versione instance = null;
        BaseEntity entity = AQuery.queryById(Versione.class, id);

        if (entity != null) {
            if (entity instanceof Versione) {
                instance = (Versione) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Versione usando la query specifica
     *
     * @return istanza di Versione, null se non trovata
     */
    public static Versione find(String titolo) {
        Versione instance = null;
        BaseEntity entity = AQuery.queryOne(Versione.class, Versione_.titolo, titolo);

        if (entity != null) {
            if (entity instanceof Versione) {
                instance = (Versione) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    public synchronized static int count() {
        int totRec = 0;
        long totTmp = AQuery.getCount(Versione.class);

        if (totTmp > 0) {
            totRec = (int) totTmp;
        }// fine del blocco if

        return totRec;
    }// end of method

    public synchronized static List<Versione> findAll() {
        return (List<Versione>) AQuery.getList(Versione.class);
    }// end of method

    @Override
    public String toString() {
        return titolo;
    }// end of method

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Date getGiorno() {
        return giorno;
    }

    public void setGiorno(Date giorno) {
        this.giorno = giorno;
    }

    @Override
    public Versione clone() throws CloneNotSupportedException {
        try {
            return (Versione) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }// fine del blocco try-catch
    }// end of method

}// end of entity class
