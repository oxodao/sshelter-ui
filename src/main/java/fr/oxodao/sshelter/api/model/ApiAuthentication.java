package fr.oxodao.sshelter.api.model;

import com.google.gson.annotations.SerializedName;

public class ApiAuthentication {
    @SerializedName(value = "token")
    public String token;

    @SerializedName(value = "refresh_token")
    public String refreshToken;
}
