package it.algos.webbase.web.toolbar;

import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.VerticalLayout;
import it.algos.webbase.web.lib.LibResource;

/**
 * Base toolbar composed by:<br>
 * - a panel for commands
 * - a helper panel for other stuff
 * <p>
 * Use addButton() to add a button to the command panel<br>
 * Use addCommandComponent to add your command to the command panel<br>
 * Use addRightComponent to add a component to the helper panel.
 */
@SuppressWarnings("serial")
public abstract class Toolbar extends VerticalLayout {

    private static final boolean DEBUG_GUI = false;
    public static int ALTEZZA_BOTTONI = 32;
    public static int LARGHEZZA_BOTTONI = 100;
    protected HorizontalLayout commandLayout = new HorizontalLayout();
    protected HorizontalLayout helperLayout = new HorizontalLayout();

    public Toolbar() {
        addStyleName("toolbar");
        setMargin(true);
        setSpacing(true);

        // set layout properties
        commandLayout.setSpacing(true);
        helperLayout.setSpacing(true);

        // adds components
        addComponent(helperLayout);
        addComponent(commandLayout);

        // set alignments
//		setComponentAlignment(commandLayout, Alignment.MIDDLE_LEFT);
//		setComponentAlignment(rightLayout, Alignment.MIDDLE_RIGHT);

        if (DEBUG_GUI) {
            commandLayout.addStyleName("greenBg");
            helperLayout.addStyleName("yellowBg");
            addStyleName("pinkBg");
        }// end of if cycle

    }// end of constructor


    /**
     * Adds a component to the internal layout containing commands.
     * <p>
     */
    public void addCommandComponent(Component c) {
        commandLayout.addComponent(c);
    }// end of method

    public MenuBar.MenuItem addButton(String caption, Resource icon, Command command) {
        MenuBar.MenuItem item = addButton(caption, icon, LARGHEZZA_BOTTONI, command);
        return item;
    }// end of method

    public MenuBar.MenuItem addButton(String caption, Resource icon, int wPixel, Command command) {
        MenuBar menubar = new MenuBar();
        menubar.setWidth(wPixel, Unit.PIXELS);
        menubar.setHeight(ALTEZZA_BOTTONI, Unit.PIXELS);

        // if no icon, use empty icon to keep the same vertical spacing
        if (icon == null) {
            icon = new ThemeResource("img/action_null.png");
        }// end of if cycle

        MenuBar.MenuItem item = menubar.addItem(caption, icon, command);

        addCommandComponent(menubar);
        return item;
    }// end of method

    public MenuBar.MenuItem addButton(String caption, Command command) {
        return addButton(caption, null, command);
    }// end of method

    /**
     * Adds a component to the helper panel.
     * <p>
     */
    public void addHelperComponent(Component c) {
        helperLayout.addComponent(c);
    }// end of method


}// end of class
