package it.algos.webbase.multiazienda;

import it.algos.webbase.domain.company.Company;
import it.algos.webbase.web.entity.BaseEntity;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class CompanyEntity extends BaseEntity {

    @ManyToOne
    // @NotNull - NotNull l'ho dovuto togliere, se no da' constraint violation
    // anche quando non è nullo (???) 28 nov 2014
    private Company company;

    /**
     * Gli oggetti di questa classe e sottoclassi vengono sempre costruiti con
     * l'azienda corrente
     */
    public CompanyEntity() {
        super();

        // se manca la company la prende dalla sessione
        if (getCompany() == null) {
            setCompany(CompanySessionLib.getCompany());
        }

    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


}
