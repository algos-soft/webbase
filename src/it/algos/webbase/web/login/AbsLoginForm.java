package it.algos.webbase.web.login;

import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import it.algos.webbase.web.dialog.ConfirmDialog;
import it.algos.webbase.web.field.CheckBoxField;
import it.algos.webbase.web.field.PasswordField;
import it.algos.webbase.web.field.TextField;
import it.algos.webbase.web.form.AFormLayout;

/**
 * Abstract Login form.
 */
public abstract class AbsLoginForm extends ConfirmDialog  {

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
    public AbsLoginForm() {
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
        Component nameCompo = createUsernameComponent();
        passField = new PasswordField("Password");
//        passField = new TextField("Password");
        passField.setWidthUndefined();
        rememberField = new CheckBoxField("Ricordami su questo computer");

        // aggiunge i campi al layout
        layout.addComponent(nameCompo);
        layout.addComponent(passField);
        layout.addComponent(rememberField);

        addComponent(layout);
    }// end of method


    /**
     * Create the component to input the username.
     * @return the username component
     */
    abstract Component createUsernameComponent();


    @Override
    protected void onConfirm() {
        UserIF user = getSelectedUser();
        if(user!=null){
            String password = passField.getValue();
            if(user.validatePassword(password)){
                super.onConfirm();
                utenteLoggato(user);
            }else{
                Notification.show("Login fallito", Notification.Type.WARNING_MESSAGE);
            }
        }
    }// end of method


    /**
     * @return the selected user
     */
    abstract UserIF getSelectedUser();

    /**
     * Evento generato quando si modifica l'utente loggato <br>
     * <p>
     * Informa (tramite listener) chi è interessato (solo la classe Login, che poi rilancia) <br>
     */
    private void utenteLoggato(UserIF utente) {
        if (loginListener != null) {
            loginListener.onUserLogin(utente, rememberField.getValue());
        }
    }

    public void setLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }

    public Window getWindow() {
        return this;
    }

    abstract void setUsername(String name);

    public void setPassword(String password) {
        passField.setValue(password);
    }

    public void setRemember(boolean remember) {
        rememberField.setValue(remember);
    }

}// end of class

