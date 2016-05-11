package it.algos.webbase.domain.company;

import it.algos.webbase.multiazienda.CompanySessionLib;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.entity.DefaultSort;
import it.algos.webbase.web.query.EntityQuery;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

@Entity
@MappedSuperclass
@Table(name = "COMPANY")
@DefaultSort({"companyCode"})
public class BaseCompany extends BaseEntity {

    private static final long serialVersionUID = 8238775575826490450L;
    public static EntityQuery<BaseCompany> query = new EntityQuery(BaseCompany.class);

    @Column(unique = true)
    @NotEmpty
    private String companyCode= "";

    @NotEmpty
    private String name = "";

    @Email
    @NotEmpty
    private String email= "";

    private String username= "";

    private String password= "";

    private String address1= "";

    private String address2= "";

    private String contact= "";

    private String contractType= "";

    @Temporal(TemporalType.DATE)
    private Date contractStart;

    @Temporal(TemporalType.DATE)
    private Date contractEnd;

    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public BaseCompany() {
        this("", "");
    }// end of constructor

    /**
     * Costruttore minimo con tutte le properties obbligatorie
     *
     * @param companyCode sigla di riferimento interna (obbligatoria)
     * @param name        descrizione della company (obbligatoria)
     */
    public BaseCompany(String companyCode, String name) {
        super();
        this.setCompanyCode(companyCode);
        this.setName(name);
    }// end of constructor

    /**
     * Ritorna la Company corrente.
     *
     * @return la Company corrente
     */
    public static BaseCompany getCurrent() {
        return CompanySessionLib.getCompany();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * @param address1 the address1 to set
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * @return the address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * @param address2 the address2 to set
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * @return the contractType
     */
    public String getContractType() {
        return contractType;
    }

    /**
     * @param contractType the contractType to set
     */
    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    /**
     * @return the contractStart
     */
    public Date getContractStart() {
        return contractStart;
    }

    /**
     * @param contractStart the contractStart to set
     */
    public void setContractStart(Date contractStart) {
        this.contractStart = contractStart;
    }

    /**
     * @return the contractEnd
     */
    public Date getContractEnd() {
        return contractEnd;
    }

    /**
     * @param contractEnd the contractEnd to set
     */
    public void setContractEnd(Date contractEnd) {
        this.contractEnd = contractEnd;
    }

    @Override
    public String toString() {
        return getName();
    }// end of method

    ;

    public void createDemoData() {
    }

    /**
     * Elimina tutti i dati di questa azienda.
     * <p>
     * L'ordine di cancellazione è critico per l'integrità referenziale
     */
    public void deleteAllData() {

        // elimina le tabelle

        // elimina gli utenti

        // elimina le preferenze

    }

}// end of entity class



