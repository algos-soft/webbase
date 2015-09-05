package it.algos.webbase.domain.ruolo;

import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.query.AQuery;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Ruolo extends BaseEntity {

//	private static final long serialVersionUID = 4597582364331646601L;

	@NotEmpty
	private String nome = "";

	public Ruolo() {
		this("");
	}// end of constructor

	public Ruolo(String nome) {
		super();
		this.setNome(nome);
	}// end of constructor

	@Override
	public String toString() {
		return nome;
	}// end of method

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Recupera il ruolo usando la query specifica
	 * 
	 * @return il ruolo, null se non trovato
	 */
	public static Ruolo read(long id) {
		Ruolo instance = null;
		BaseEntity entity = AQuery.queryById(Ruolo.class, id);

		if (entity != null) {
			if (entity instanceof Ruolo) {
				instance = (Ruolo) entity;
			}// end of if cycle
		}// end of if cycle

		return instance;
	}// end of method

	/**
	 * Recupera il ruolo usando la query specifica
	 * 
	 * @return il ruolo, null se non trovato
	 */
	public static Ruolo read(String nome) {
		Ruolo instance = null;
		BaseEntity entity = AQuery.queryOne(Ruolo.class, Ruolo_.nome, nome);

		if (entity != null) {
			if (entity instanceof Ruolo) {
				instance = (Ruolo) entity;
			}// end of if cycle
		}// end of if cycle

		return instance;
	}// end of method

	/**
	 * Recupera il ruolo usando la query specifica
	 * 
	 * @return il ruolo, null se non trovato
	 */
	public static Ruolo read(TipoRuolo tipo) {
		return read(tipo.toString());
	}// end of method

}// end of entity class