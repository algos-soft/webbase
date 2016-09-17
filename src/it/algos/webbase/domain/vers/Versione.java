package it.algos.webbase.domain.vers;
/**
 * Created by Gac on 17 lug 2015.
 * Using specific Templates (Entity, Domain, Modulo)
 */

import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.lib.LibTime;
import it.algos.webbase.web.query.AQuery;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Versione extends BaseEntity {

    private int ordine;

    @NotEmpty
    private String titolo = "";

    private String descrizione = "";

    @NotNull
    private Timestamp timestamp = LibTime.adesso();


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
        return (List<Versione>) AQuery.getListOld(Versione.class);
    }// end of method

    @Override
    public String toString() {
        return titolo;
    }// end of method

    public int getOrdine() {
        return ordine;
    }

    public void setOrdine(int numero) {
        this.ordine = numero;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Delete all the records for the Entity class
     * Bulk delete records with CriteriaDelete
     */
    public static void deleteAll() {
        EntityManager manager = EM.createEntityManager();
        deleteAll(manager);
        manager.close();
    }// end of static method

    /**
     * Delete all the records for the Entity class
     * Bulk delete records with CriteriaDelete
     *
     * @param manager the EntityManager to use
     */
    public static void deleteAll(EntityManager manager) {
        AQuery.deleteAll(Versione.class, manager);
    }// end of static method

    @Override
    public Versione clone() throws CloneNotSupportedException {
        try {
            return (Versione) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }// fine del blocco try-catch
    }// end of method

}// end of entity class
