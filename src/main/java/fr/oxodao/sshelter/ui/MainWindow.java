package fr.oxodao.sshelter.ui;

import fr.oxodao.sshelter.ui.icon_menu.IconMenu;
import fr.oxodao.sshelter.ui.icon_menu.IconMenuItem;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private IconMenu menu = new IconMenu();

    public MainWindow() {
        this.setTitle("Sshelter");
        this.setSize(800, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(false);

        this.setLayout(new BorderLayout());
        this.add(this.menu, BorderLayout.WEST);

        this.menu.addItem(new IconMenuItem("SSH", "terminal", null));
        this.menu.addItem(new IconMenuItem("Port forwarding", "port", null));
        this.menu.addItem(new IconMenuItem("File transfer", "files", null));
    }

}
