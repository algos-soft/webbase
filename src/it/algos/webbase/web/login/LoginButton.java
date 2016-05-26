package it.algos.webbase.web.login;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import it.algos.webbase.domain.utente.Utente;
import it.algos.webbase.web.AlgosApp;

import java.util.ArrayList;

/**
 * Menubar di gestione login
 * <p>
 * Se si è loggati, mostra il nome dell'utente ed un popup per modifica e logout <br>
 * Se non si è loggati, mostra un bottone per fare il login <br>
 */
public class LoginButton extends MenuBar {

    private MenuBar.MenuItem loginItem; // il menuItem di login

    /**
     * Constructor
     */
    public LoginButton() {

        Login login = Login.getLogin();

        login.addLoginListener(new LoginListener() {
            @Override
            public void onUserLogin(UserIF user, boolean remember) {
                updateUI();
            }
        });

        login.addLogoutListener(new LogoutListener() {
            @Override
            public void onUserLogout(UserIF user) {
                updateUI();
            }
        });

        loginItem = addItem("", null, null);
        updateUI();
    }

    /**
     * Updates the UI based on the current Login state
     */
    private void updateUI() {
        UserIF user = Login.getLogin().getUser();
        Resource exitIcon;

        if (user == null) {

            loginItem.setText("Login");
            loginItem.setIcon(FontAwesome.SIGN_IN);
            loginItem.setCommand(new MenuBar.Command() {
                @Override
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    loginCommandSelected();
                }
            });

        } else {

            String username = user.toString();
            loginItem.setCommand(null);
            loginItem.setText(username);
            loginItem.setIcon(FontAwesome.USER);
            loginItem.removeChildren();
            exitIcon = FontAwesome.SIGN_OUT;
            loginItem.addItem("Logout", exitIcon, new MenuBar.Command() {
                @Override
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    logoutCommandSelected();
                }// end of method
            });

        }

    }


    /**
     * Login button pressed
     */
    protected void loginCommandSelected() {
        Login.getLogin().showLoginForm();
    }


    /**
     * Logout button pressed
     */
    private void logoutCommandSelected() {
        Login.getLogin().logout();
        updateUI();
    }


}

