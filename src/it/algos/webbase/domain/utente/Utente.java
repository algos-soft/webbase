package it.algos.webbase.domain.utente;

import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.query.AQuery;
import it.algos.webbase.web.query.FilterFactory;
import com.vaadin.data.Container.Filter;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Utente extends BaseEntity {

	private static final long serialVersionUID = -8963424744780350658L;

	@NotEmpty
	private String nickname = "";

	private String password = "";

//	private String company = "";

	private boolean enabled = true;

	public Utente() {
		this("", "");
	}// end of constructor

	public Utente(String nickname, String password) {
		this.setNickname(nickname);
		this.setPassword(password);
	}// end of constructor

//	public Utente(String nickname, String password, String company) {
//		super();
//		this.setNickname(nickname);
//		this.setPassword(password);
////		this.setCompany(company);
//	}// end of constructor

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return nickname;
	}// end of method

	/**
	 * @return the nome
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname
	 *            the nome to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

//	/**
//	 * @return the company
//	 */
//	public String getCompany() {
//		return company;
//	}
//
//	/**
//	 * @param company
//	 *            the company to set
//	 */
//	public void setCompany(String company) {
//		this.company = company;
//	}

	/**
	 * Recupera l'utente usando la query specifica
	 * 
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
	 * @return l'utente, null se non trovato
	 */
	public static Utente read(String nome) {
		Utente instance = null;
		BaseEntity entity = AQuery.queryOne(Utente.class, Utente_.nickname, nome);

		if (entity != null) {
			if (entity instanceof Utente) {
				instance = (Utente) entity;
			}// end of if cycle
		}// end of if cycle

		return instance;
	}// end of method

//	/**
//	 * Recupera l'utente usando la query specifica
//	 *
//	 * @return l'utente, null se non trovato
//	 */
//	public static Utente read(String company, String nome) {
//		Utente instance = null;
//		BaseEntity entity = null;
//		Filter filtroAzienda = FilterFactory.create(Utente_.company, company);
//		Filter filtroNome = FilterFactory.create(Utente_.nickname, nome);
//
//		if (company.equals("")) {
//			entity = AQuery.getEntity(Utente.class, filtroNome);
//		} else {
//			entity = AQuery.getEntity(Utente.class, filtroNome, filtroAzienda);
//		}// end of if/else cycle
//
//		if (entity != null) {
//			if (entity instanceof Utente) {
//				instance = (Utente) entity;
//			}// end of if cycle
//		}// end of if cycle
//
//		return instance;
//	}// end of method


	/**
	 * Valida nome e password e ritorna l'utente corrispondente.
	 * Se le credenziali sono errate o l'utente è disabilitato ritorna null
	 */
	public static Utente validate(String username, String password){
		Utente user=null;
		Utente aUser = (Utente)AQuery.queryOne(Utente.class, Utente_.nickname, username);
		if(aUser.isEnabled()){
			if(aUser.getPassword().equals(password)){
				user=aUser;
			}
		}
		return user;
	}


}// end of entity class
