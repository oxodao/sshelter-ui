package fr.oxodao.sshelter;

import dorkbox.systemTray.Menu;
import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.SystemTray;

import javax.swing.*;

public class TrayIcon {

    private SystemTray trayIcon;
    private Menu machines;

    public TrayIcon() {
        this.trayIcon = SystemTray.get();
        if (this.trayIcon == null) {
            JOptionPane.showMessageDialog(null, "It seems like your system does not handle TrayIcon", "Sshelter - Unable to start", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        try {
            this.trayIcon.setImage(this.getClass().getResourceAsStream("/icon.png"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to set the tray icon's image", "Sshelter - Unable to start", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        this.trayIcon.setTooltip("Sshelter");

        this.machines = new Menu("Machines");

        Menu menu = this.trayIcon.getMenu();
        menu.add(this.machines);
        menu.add(new MenuItem("Exit", (evt) -> this.trayIcon.shutdown()));
    }

}
