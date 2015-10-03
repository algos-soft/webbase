package it.algos.webbase.web.login;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import it.algos.webbase.domain.utente.Utente;
import it.algos.webbase.web.dialog.ConfirmDialog;
import it.algos.webbase.web.field.CheckBoxField;
import it.algos.webbase.web.field.PasswordField;
import it.algos.webbase.web.field.TextField;
import it.algos.webbase.web.form.AFormLayout;
import it.algos.webbase.web.lib.LibCrypto;

/**
 * Base Login form (UI).
 */
public class BaseLoginForm extends ConfirmDialog  {

    private TextField nameField;
    private PasswordField passField;
    private CheckBoxField rememberField;

    /**
     * Login gestisce il form ed alla chiusura controlla la validità del nuovo utente
     * Lancia il fire di questo evento, se l'utente è valido.
     * Si registra qui il solo listener di Login perché BaseLoginForm e Login sono 1=1
     * Login a sua volta rilancia l'evento per i propri listeners
     * (che si registrano a Login che è singleton nella sessione, mentre BaseLoginForm può essere instanziata diverse volte)
     */
    private LoginListener loginListener;

    /**
     * Constructor
     */
    public BaseLoginForm() {
        super(null);
        init();
    }// end of constructor

    /**
     * Initialization <br>
     */
    private void init() {
        FormLayout layout = new AFormLayout();
        layout.setSpacing(true);

        // crea i campi
        nameField = new TextField("Username");
        nameField.setWidthUndefined();
        passField = new PasswordField("Password");
        passField.setWidthUndefined();
        rememberField = new CheckBoxField("Ricordami su questo computer");

        // aggiunge i campi al layout
        layout.addComponent(nameField);
        layout.addComponent(passField);
        layout.addComponent(rememberField);

        addComponent(layout);
    }// end of method


    @Override
    protected void onConfirm() {
        String nome = nameField.getValue();
        String password = passField.getValue();
        Utente utente = Utente.validate(nome, password);
        if (utente != null) {
            super.onConfirm();
            utenteLoggato(utente);
        } else {
            Notification.show("Login fallito", Notification.Type.WARNING_MESSAGE);
        }// end of if/else cycle
    }// end of method


    /**
     * Evento generato quando si modifica l'utente loggato <br>
     * <p>
     * Informa (tramite listener) chi è interessato (solo la classe Login, che poi rilancia) <br>
     */
    private void utenteLoggato(Utente utente) {
        if (loginListener != null) {
            loginListener.onUserLogin(utente, rememberField.getValue());
        }// end of if cycle
    }// end of method

    public void setLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }

    public Window getWindow() {
        return this;
    }

    public void setUsername(String name) {
        nameField.setValue(name);
    }

    public void setPassword(String password) {
        passField.setValue(LibCrypto.decrypt(password));
    }

    public void setRemember(boolean remember) {
        rememberField.setValue(remember);
    }

}// end of class
