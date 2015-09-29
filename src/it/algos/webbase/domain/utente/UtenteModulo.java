package it.algos.webbase.domain.utente;

import it.algos.webbase.web.form.AForm;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.security.LoginLogic;

import javax.persistence.metamodel.Attribute;

import com.vaadin.data.Item;
import com.vaadin.ui.Notification;

@SuppressWarnings("serial")
public class UtenteModulo extends ModulePop {

	public UtenteModulo() {
		super(Utente.class);
	}// end of constructor

	/**
	 * Titolo (caption) dei dialogo nuovo record. <br>
	 * Come default usa il titolo standard <br>
	 * Può essere sovrascritto nelle sottoclassi specifiche <br>
	 */
	@Override
	protected String getCaptionNew() {
		return "Nuovo utente";
	}// end of method

	/**
	 * Titolo (caption) dei dialogo di modifica. <br>
	 * Come default usa il titolo standard <br>
	 * Può essere sovrascritto nelle sottoclassi specifiche <br>
	 */
	@Override
	protected String getCaptionEdit() {
		return "Modifica utente";
	}// end of method

	/**
	 * Titolo (caption) dei dialogo di ricerca. <br>
	 * Come default usa il titolo standard <br>
	 * Può essere sovrascritto nelle sottoclassi specifiche <br>
	 */
	@Override
	protected String getCaptionSearch() {
		return "Ricerca utenti";
	}// end of method



	@Override
	protected Attribute<?, ?>[] creaFieldsList() {
		return new Attribute[] { Utente_.nickname, Utente_.enabled };
	}

	/**
	 * Returns the form used to edit an item. <br>
	 * The concrete subclass must override for a specific Form.
	 * 
	 * @return the Form
	 */
	public AForm createForm(Item item) {
		return new UtenteForm(this, item);
	}// end of method

}// end of class
