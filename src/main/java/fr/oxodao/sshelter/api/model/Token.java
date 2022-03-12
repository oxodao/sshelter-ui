package fr.oxodao.sshelter.api.model;

import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName(value = "iat")
    public long issuedAt;

    @SerializedName(value = "exp")
    public long expiresAt;

    @SerializedName(value = "roles")
    public String[] roles;

    @SerializedName(value = "username")
    public String username;

}
