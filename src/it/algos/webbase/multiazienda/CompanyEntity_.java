package it.algos.webbase.multiazienda;

import it.algos.webbase.domain.company.Company;
import it.algos.webbase.web.entity.BaseEntity_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CompanyEntity.class)
public abstract class CompanyEntity_ extends BaseEntity_{
	
	public static volatile SingularAttribute<CompanyEntity, Company> company;

}// end of class
