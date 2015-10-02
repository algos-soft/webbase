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
public class BaseLoginForm extends ConfirmDialog implements LoginForm {

    private TextField nameField;
    private PasswordField passField;
    private CheckBoxField rememberField;
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
        rememberField=new CheckBoxField("Ricordami su questo computer");

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
     * Informa (tramite listener) chi è interessato <br>
     */
    private void utenteLoggato(Utente utente) {
        if(loginListener!=null){
            loginListener.onUserLogin(utente, rememberField.getValue());
        }
    }

    @Override
    public void setLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }

    @Override
    public Window getWindow() {
        return this;
    }

    @Override
    public void setUsername(String name) {
        nameField.setValue(name);
    }

    @Override
    public void setPassword(String password) {
        passField.setValue(LibCrypto.decrypt(password));
    }

    @Override
    public void setRemember(boolean remember) {
        rememberField.setValue(remember);
    }


}

