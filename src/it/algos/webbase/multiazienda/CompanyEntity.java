package it.algos.webbase.multiazienda;

import it.algos.webbase.domain.company.BaseCompany;
import it.algos.webbase.web.entity.BaseEntity;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class CompanyEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    // @NotNull - NotNull l'ho dovuto togliere, se no da' constraint violation
    // anche quando non è nullo (???) 28 nov 2014
    private BaseCompany company;

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

    public BaseCompany getCompany() {
        return company;
    }

    public void setCompany(BaseCompany company) {
        this.company = company;
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//
//        CompanyEntity that = (CompanyEntity) o;
//
//        return !(company != null ? !company.equals(that.company) : that.company != null);
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = super.hashCode();
//        result = 31 * result + (company != null ? company.hashCode() : 0);
//        return result;
//    }
}
