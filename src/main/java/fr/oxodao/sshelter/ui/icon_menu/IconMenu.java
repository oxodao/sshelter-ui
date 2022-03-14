package fr.oxodao.sshelter.ui.icon_menu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class IconMenu extends JPanel {

    private ArrayList<IconMenuItem> items = new ArrayList<>();

    public IconMenu() {
        this.setPreferredSize(new Dimension(64, 200));
        this.setBackground(Color.white);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    }

    public void addItem(IconMenuItem item) {
        items.add(item);
        this.add(item);
    }

    public void removeItem(IconMenuItem item) {
        items.remove(item);
        this.remove(item);
    }
}
