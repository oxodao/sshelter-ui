package fr.oxodao.sshelter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.oxodao.sshelter.api.model.AuthenticationData;

import javax.swing.*;
import java.io.*;

public class Config {
    private static File configFile = null;
    private static Config config = null;

    private String serverUrl;
    private boolean autosync = true;
    private AuthenticationData authenticationData;

    public String getServerUrl() {
        return serverUrl;
    }

    public Config setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
        return this;
    }

    public AuthenticationData getAuthenticationData() {
        return this.authenticationData;
    }

    public Config setAuthenticationData(AuthenticationData data) {
        this.authenticationData = data;
        return this;
    }

    public boolean hasServerURL() {
        return this.serverUrl != null && this.serverUrl.length() > 0;
    }

    public boolean isAutosync() {
        return this.autosync;
    }

    public Config setAutosync(boolean autosync) {
        this.autosync = autosync;
        return this;
    }

    public void save() {
        if (Sshelter.api != null) {
            this.authenticationData = Sshelter.api.Auth().getAuthenticationData();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fw = new FileWriter(Config.configFile)){
            gson.toJson(this, fw);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not save the config: " + e.getMessage(), "Sshelter: Could not write the config", JOptionPane.ERROR_MESSAGE);
        }
    }

    //#region Static utils functions
    public static void load() {
        try {
            Config.configFile = new File(Utils.getConfigFolder(), "sshelter.json");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Sshelter: Could not load config", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        Gson gson = new Gson();
        try {
            Config.config = gson.fromJson(new InputStreamReader(new FileInputStream(Config.configFile)), Config.class);
        } catch (Exception e) {
            Config.config = new Config();
        }
    }

    public static Config getConfig() {
        if (Config.config == null) {
            Config.load();
        }

        return Config.config;
    }
    //#endregion
}
