package fr.oxodao.sshelter.ui.icon_menu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;

public class IconMenuItem extends JPanel {
    private JLabel titleLabel;

    public IconMenuItem(String title, String iconName, ActionListener onClick) {
        this.titleLabel = new JLabel(title);

        this.setLayout(new BorderLayout());
        this.add(this.titleLabel, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(80, 80));

        try {
            BufferedImage bi = ImageIO.read(getClass().getResourceAsStream("/icons/" + iconName + ".png"));
            this.titleLabel.setIcon(new ImageIcon(bi));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (onClick != null) {
                    onClick.actionPerformed(null);
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {}

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {}

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}

            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        });
    }

}
