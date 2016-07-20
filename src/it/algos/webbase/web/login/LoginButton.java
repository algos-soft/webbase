package it.algos.webbase.web.login;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.MenuBar;

/**
 * Menubar di gestione login
 * <p>
 * Se si è loggati, mostra il nome dell'utente ed un popup per modifica e logout <br>
 * Se non si è loggati, mostra un bottone per fare il login <br>
 */
public class LoginButton extends MenuBar {

    private MenuBar.MenuItem loginItem; // il menuItem di login
    private Login login;

    /**
     * Constructor
     * @param login l'oggetto Login da usare
     */
    public LoginButton(Login login) {
        this.login=login;

        this.login.addLoginListener(new LoginListener() {
            @Override
            public void onUserLogin(LoginEvent e) {
                updateUI();
            }
        });

        this.login.addLogoutListener(new LogoutListener() {
            @Override
            public void onUserLogout(LogoutEvent e) {
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
        UserIF user = login.getUser();
        Resource exitIcon;

        if (user == null) {

            loginItem.setText("Login");
            loginItem.removeChildren();
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
        login.showLoginForm();
    }


    /**
     * Logout button pressed
     */
    private void logoutCommandSelected() {
        login.logout();
        updateUI();
    }

    public Login getLogin() {
        return login;
    }

}

