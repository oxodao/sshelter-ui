package fr.oxodao.sshelter.api.model;

import com.google.gson.annotations.SerializedName;

class ForwardedPort {
    @SerializedName("local_hostname")
    public String LocalHostname;

    @SerializedName("local_port")
    public int LocalPort;

    @SerializedName("remote_hostname")
    public String RemoteHostname;

    @SerializedName("remote_port")
    public int RemotePort;
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
}
