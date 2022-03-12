package fr.oxodao.sshelter;

import fr.oxodao.sshelter.api.model.Machine;

import java.io.*;

public class SshConfig {
    private static final String regex = "(?s)\\n# SSHELTER CONFIG(.*?)# SSHELTER END CONFIG";

    public static void updateSshConfig(Machine[] machines) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(Utils.getSshConfig()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();

            String config = sb.toString();

            StringBuilder newConfig = new StringBuilder(config.replaceAll(SshConfig.regex, ""));

            if (!newConfig.toString().endsWith("\n")) {
                newConfig.append("\n");
            }

            newConfig.append("# SSHELTER CONFIG\n");
            newConfig.append("# Please do not edit this section. Any changes here will be overwritten or might break sshelter.\n");
            for (Machine machine : machines) {
                newConfig.append(machine.toSshString() + "\n");
            }
            newConfig.append("# SSHELTER END CONFIG\n");

            BufferedWriter bw = new BufferedWriter(new FileWriter(Utils.getSshConfig()));
            bw.write(newConfig.toString());
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
