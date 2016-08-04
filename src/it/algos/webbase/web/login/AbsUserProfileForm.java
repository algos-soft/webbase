package it.algos.webbase.web.login;

import com.vaadin.ui.Window;
import it.algos.webbase.web.dialog.ConfirmDialog;

/**
 * Abstract User Profile form
 * Created by alex on 04/08/16.
 */
public abstract class AbsUserProfileForm extends ConfirmDialog {

    public AbsUserProfileForm() {
        super(null);
    }

    /**
     * Fills the fields in the form with the data from a user
     * @param user the user
     */
    public abstract void setUser(UserIF user);

    public Window getWindow() {
        return this;
    }



}
