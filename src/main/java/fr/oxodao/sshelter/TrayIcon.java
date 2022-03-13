package fr.oxodao.sshelter;

import dorkbox.systemTray.*;
import fr.oxodao.sshelter.api.model.Machine;
import fr.oxodao.sshelter.ui.EditMachinesFrame;

import javax.swing.*;

public class TrayIcon {

    public static TrayIcon instance = null;

    private ThreadSync syncThread;
    private SystemTray trayIcon;
    private Menu machines;

    protected Checkbox autosyncMenuItem;

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
        menu.add(this.autosyncMenuItem = new Checkbox("Autosync", (evt) -> {
            if (this.syncThread != null) {
                this.syncThread.setAutosync(this.autosyncMenuItem.getChecked());
                Config.getConfig().setAutosync(this.autosyncMenuItem.getChecked()).save();
            }
        }));
        menu.add(new MenuItem("Exit", (evt) -> {
            this.trayIcon.shutdown();
            System.exit(0);
        }));

        this.autosyncMenuItem.setChecked(Config.getConfig().isAutosync());
        this.syncThread = new ThreadSync(this);
        this.syncThread.setAutosync(Config.getConfig().isAutosync());
    }

    public void setMachines(Machine[] machines) {
        Entry e = this.machines.get(0);
        while (e != null) {
            this.machines.remove(e);
            e = this.machines.get(0);
        }

        for (Machine machine : machines) {
            this.machines.add(new MenuItem(machine.toString(), (evt) -> {
                System.out.println("Connecting to " + machine);
            }));
        }

        this.machines.add(new Separator());
        this.machines.add(new MenuItem("Edit machines...", (evt) -> SwingUtilities.invokeLater(() -> new EditMachinesFrame(machines))));
        SshConfig.updateSshConfig(machines);
    }

    public static TrayIcon get() {
        if (instance == null) {
            instance = new TrayIcon();
        }

        return instance;
    }
}
