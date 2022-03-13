package fr.oxodao.sshelter.api.model;

import com.google.gson.annotations.SerializedName;

public class Machine {
    @SerializedName("@id")
    public String id = null;

    @SerializedName("name")
    public String name = "";

    @SerializedName("shortName")
    public String shortName = "";

    @SerializedName("hostname")
    public String hostname = "";

    @SerializedName("username")
    public String username = "";

    @SerializedName("port")
    public int port = 22;

    @SerializedName("otherSettings")
    public String otherSettings = "";

    @SerializedName("forwardedPorts")
    public ForwardedPort[] forwardedPorts = new ForwardedPort[0];

    private String eachLineTabbed(String str) {
        String[] lines = str.split("\n");
        for (int i = 0; i < lines.length; i++) {
            lines[i] = "\t" + lines[i];
        }
        return String.join("\n", lines);
    }

    public String toSshString() {
        StringBuilder sb = new StringBuilder();

        if (this.shortName != null && this.shortName.length() > 0) {
            sb.append("Host " + this.shortName + "\n");
        } else {
            sb.append("Host " + this.hostname + "\n");
        }

        if (this.hostname != null && this.hostname.length() > 0) {
            sb.append("\tHostname " + this.hostname + "\n");
        }

        sb.append("\tPort " + this.port + "\n");

        if (this.username != null && this.username.length() > 0) {
            sb.append("\tUser " + this.username + "\n");
        }

        if (this.otherSettings != null && this.otherSettings.length() > 0) {
            sb.append(eachLineTabbed(this.otherSettings) + "\n");
        }

        if (this.forwardedPorts != null) {
            for (ForwardedPort forwardedPort : this.forwardedPorts) {
                sb.append(eachLineTabbed(forwardedPort.toSshString()) + "\n");
            }
        }

        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        var hasName = this.name != null && this.name.length() > 0;
        var hasShortName = this.shortName != null && this.shortName.length() > 0;

        if (hasName) {
            sb.append(this.name);
        }

        if (hasName && hasShortName) {
            sb.append(" (");
        }

        if (hasShortName) {
            sb.append(this.shortName);
        }

        if (hasName && hasShortName) {
            sb.append(")");
        }

        return sb.toString();
    }
}
