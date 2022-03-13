package fr.oxodao.sshelter;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import fr.oxodao.sshelter.api.SshelterApi;

import javax.swing.*;

/**
 * <a target="_blank" href="https://icones8.fr/icon/w8vzomb1oP2W/console">Console</a> icône par <a target="_blank" href="https://icones8.fr">Icons8</a>
 */

public class Sshelter {
    public static SshelterApi api;

    public static void main(String[] args) {
        try {
            FlatLightLaf.setup();
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch(Exception e) {
            System.out.println("Could not set custom look'n'feel. Falling back on system LnF: " + e.getMessage());
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e2) {
                System.out.println("Could not set look'n'feel: " + e2.getMessage());
            }
        }

        Config.load();
        if (!Config.getConfig().hasServerURL() || Config.getConfig().getAuthenticationData() == null || !Config.getConfig().getAuthenticationData().isValid()) {
            (new LoginFrame()).show();
        }
        Sshelter.api = new SshelterApi(Config.getConfig().getServerUrl(), Config.getConfig().getAuthenticationData());
        Sshelter.api.Auth().onAuthenticationUpdated((data) -> Config.getConfig().setAuthenticationData(data).save());
        Sshelter.api.Auth().onAuthenticationRequired(() -> {
            (new LoginFrame()).show();
        });

        TrayIcon.get();
    }

}
