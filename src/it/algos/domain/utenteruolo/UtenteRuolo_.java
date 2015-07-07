package it.algos.domain.utenteruolo;

import it.algos.domain.ruolo.Ruolo;
import it.algos.domain.utente.Utente;
import it.algos.web.entity.BaseEntity_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UtenteRuolo.class)
public class UtenteRuolo_ extends BaseEntity_ {
	public static volatile SingularAttribute<UtenteRuolo, Utente> utente;
	public static volatile SingularAttribute<UtenteRuolo, Ruolo> ruolo;
}// end of entity class
