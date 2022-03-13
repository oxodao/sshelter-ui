package fr.oxodao.sshelter.api.model;

import com.google.gson.annotations.SerializedName;

public class ForwardedPort {
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
