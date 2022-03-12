package fr.oxodao.sshelter;

import java.io.File;

public class Utils {
    public enum OS {
        WINDOWS,
        LINUX,
        OSX,
    }

    public static OS getOS() throws Exception {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return OS.WINDOWS;
        }

        if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            return OS.LINUX;
        }

        if (os.contains("mac")) {
            return OS.OSX;
        }

        throw new Exception("Your OS (" + os + ") is not compatible with Sshelter");
    }

    public static File getConfigFolder() throws Exception {
        OS os = Utils.getOS();

        File folderPath = null;
        switch(os) {
            case OSX:
            case LINUX:
                folderPath = new File(System.getProperty("user.home"), ".config/sshelter");
                break;
            case WINDOWS:
                folderPath = new File(System.getenv("APPDATA"), "sshelter");
                break;
        };

        if (!folderPath.exists()) {
            if (!folderPath.mkdir()) {
                throw new Exception("Could not create config folder !");
            }
        }

        return folderPath;
    }
}
