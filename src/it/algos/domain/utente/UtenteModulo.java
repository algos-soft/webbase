package it.algos.domain.utente;

import it.algos.web.form.AForm;
import it.algos.web.module.ModulePop;
import it.algos.web.security.LoginLogic;

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

	// come default spazzola tutti i campi della Entity
	// non garantisce l'ordine con cui vengono presentati i campi
	// può essere sovrascritto nelle sottoclassi specifiche (garantendo l'ordine)
	// può mostrare anche il campo ID, oppure no
	// se si vuole differenziare tra Table, Form e Search, sovrascrivere
	// creaFieldsList, creaFieldsForm e creaFieldsSearch
	protected Attribute<?, ?>[] creaFieldsAll() {
		return new Attribute[] { Utente_.nickname, Utente_.password, Utente_.enabled };
	}// end of method

	/**
	 * Returns the form used to edit an item. <br>
	 * The concrete subclass must override for a specific Form.
	 * 
	 * @return the Form
	 */
	public AForm createForm(Item item) {
		Utente uti = LoginLogic.getUtente();
		if (uti != null) {
			Notification.show("Utente loggato sempbra proprio che sia: " + uti.getNickname(),
					Notification.Type.HUMANIZED_MESSAGE);
		} else {
			Notification.show("Non c'è proprio nessuno loggato !", Notification.Type.HUMANIZED_MESSAGE);
		}// end of if/else cycle
		
		return super.createForm(item);
	}// end of method

}// end of class
