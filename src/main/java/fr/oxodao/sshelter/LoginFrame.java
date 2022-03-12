package fr.oxodao.sshelter;

import fr.oxodao.sshelter.api.SshelterApi;

import javax.swing.*;

public class LoginFrame {

    private final JPanel panel;

    private final JTextField serverField;
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginFrame() {
        Config cfg = Config.getConfig();
        this.serverField = new JTextField(cfg.getServerUrl());
        this.usernameField = new JTextField(cfg.getAuthenticationData() != null ? cfg.getAuthenticationData().getUsername() : "");
        this.passwordField = new JPasswordField();

        JPanel panel = new JPanel();
        BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(bl);

        panel.add(new JLabel("Server hostname:"));
        panel.add(this.serverField);
        panel.add(new JLabel("Username: "));
        panel.add(this.usernameField);
        panel.add(new JLabel("Password: "));
        panel.add(this.passwordField);

        this.panel = panel;
    }

    public void show() {
        boolean shouldRetry = false;

        do {
            int result = JOptionPane.showConfirmDialog(null, this.panel, "Sshelter: Authentication", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.CANCEL_OPTION) {
                System.out.println("Exiting..");
                System.exit(0);
            }

            try {
                Config.getConfig().setServerUrl(this.serverField.getText()).save();

                SshelterApi api = new SshelterApi(this.serverField.getText());
                api.Auth().onAuthenticationUpdated((ad) -> Config.getConfig().setAuthenticationData(ad).save());
                api.Auth().login(
                        this.usernameField.getText(),
                        new String(this.passwordField.getPassword())
                );

                shouldRetry = false;
            } catch (Exception e) {
                shouldRetry = true;
                this.passwordField.setText("");
                JOptionPane.showMessageDialog(null, "Could not authenticate: " + e.getMessage(), "Sshelter: failed authentication", JOptionPane.ERROR_MESSAGE);
            }
        } while (shouldRetry);
    }
}
