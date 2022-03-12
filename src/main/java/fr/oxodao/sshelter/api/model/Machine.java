package fr.oxodao.sshelter.api.model;

import com.google.gson.annotations.SerializedName;

class ForwardedPort {
    @SerializedName("local_hostname")
    public String localHostname;

    @SerializedName("local_port")
    public int localPort;

    @SerializedName("remote_hostname")
    public String remoteHostname;

    @SerializedName("remote_port")
    public int remotePort;

    @SerializedName("reversed")
    public boolean reversed;

    public String toSshString() {
        StringBuilder sb = new StringBuilder();

        if (this.localHostname != null) {
            sb.append(this.localHostname + ":");
        } else {
            sb.append("localhost:");
        }

        sb.append(this.localPort + " ");

        if (this.remoteHostname != null) {
            sb.append(this.remoteHostname + ":");
        } else {
            sb.append("localhost:");
        }

        sb.append(this.remotePort);

        return (this.reversed ? "RemoteForward " : "LocalForward ") + sb;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (localHostname != null) {
            sb.append(localHostname + ":");
        }

        sb.append(localPort);
        sb.append(this.reversed ? " <- " : " -> ");

        if (remoteHostname != null) {
            sb.append(remoteHostname + ":");
        }

        sb.append(remotePort);

        return sb + " (Mapped " + (this.reversed ? "remotely" : "locally") + ")";
    }
}

public class Machine {
    @SerializedName("@id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("shortName")
    public String shortName;

    @SerializedName("hostname")
    public String hostname;

    @SerializedName("username")
    public String username;

    @SerializedName("port")
    public int port;

    @SerializedName("otherSettings")
    public String otherSettings;

    @SerializedName("forwardedPorts")
    public ForwardedPort[] forwardedPorts;

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
