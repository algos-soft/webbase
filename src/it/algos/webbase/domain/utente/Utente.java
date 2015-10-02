package it.algos.webbase.domain.utente;

import it.algos.webbase.domain.ruolo.Ruolo;
import it.algos.webbase.domain.utenteruolo.UtenteRuolo;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.login.Login;
import it.algos.webbase.web.query.AQuery;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class Utente extends BaseEntity {

    private static final long serialVersionUID = -8963424744780350658L;

    @NotEmpty
    private String nickname = "";

    private String password = "";

    private boolean enabled = true;

    public Utente() {
        this("", "");
    }// end of constructor

    public Utente(String nickname, String password) {
        this.setNickname(nickname);
        this.setPassword(password);
    }// end of constructor

    /**
     * Recupera l'utente usando la query specifica
     *
     * @param id key id
     * @return l'utente, null se non trovato
     */
    public static Utente read(long id) {
        Utente instance = null;
        BaseEntity entity = AQuery.queryById(Utente.class, id);

        if (entity != null) {
            if (entity instanceof Utente) {
                instance = (Utente) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera l'utente usando la query specifica
     *
     * @param nickname dell'utente
     * @return l'utente, null se non trovato
     */
    public static Utente read(String nickname) {
        Utente instance = null;
        BaseEntity entity = AQuery.queryOne(Utente.class, Utente_.nickname, nickname);

        if (entity != null) {
            if (entity instanceof Utente) {
                instance = (Utente) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Valida nome e password e ritorna l'utente corrispondente.
     * Se le credenziali sono nulle, errate o l'utente è disabilitato ritorna null
     *
     * @param nickname dell'utente
     * @param password dell'utente
     * @return l'utente, null se non trovato
     */
    public static Utente validate(String nickname, String password) {
        Utente user = null;
        Utente aUser = read(nickname);

        if ((!nickname.equals("")) && (!password.equals(""))) {
            if (aUser != null) {
                if (aUser.isEnabled()) {
                    if (aUser.getPassword().equals(password)) {
                        user = aUser;
                    }// end of if cycle
                }// end of if cycle
            }// end of if cycle
        }// end of if cycle

        return user;
    }// end of method


    @Override
    public String toString() {
        return nickname;
    }// end of method

    public String getNickname() {
        return nickname;
    }// end of getter method

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }//end of setter method

    public String getPassword() {
        return password;
    }// end of getter method

    public void setPassword(String password) {
        this.password = password;
    }//end of setter method

    public boolean isEnabled() {
        return enabled;
    }// end of getter method

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }//end of setter method


    /**
     * Checks if this user has a given Role.
     *
     * @param role the role
     * @return true if this user has the role
     */
    public boolean hasRole(Ruolo role) {
        // controlla se l'utente ha ruolo di admin
        boolean found=false;
        if (role!=null){
            ArrayList<UtenteRuolo> urs = UtenteRuolo.findUtente(this);
            if(urs.size()>0){
                for(UtenteRuolo uruolo : urs){
                    if(uruolo.getRuolo().equals(role)){
                        found=true;
                        break;
                    }
                }
            }
        }

        return found;

    }

}// end of entity class
