package fr.oxodao.sshelter.api;

import fr.oxodao.sshelter.Sshelter;
import fr.oxodao.sshelter.api.callbacks.OnAuthenticationRequired;
import fr.oxodao.sshelter.api.callbacks.OnAuthenticationUpdated;
import fr.oxodao.sshelter.api.exceptions.InvalidCredentialsException;
import fr.oxodao.sshelter.api.model.ApiAuthentication;
import fr.oxodao.sshelter.api.model.AuthenticationData;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;

import static fr.oxodao.sshelter.api.SshelterApi.JSON;

public class Authentication {

    private OnAuthenticationUpdated authenticationUpdatedCallback;
    private OnAuthenticationRequired authenticationRequiredCallback;
    private final SshelterApi api;

    public Authentication(SshelterApi api) {
        this.api = api;
    }

    public void login(String username, String password) throws Exception {
        var m = new HashMap<String, String>();
        m.put("username", username);
        m.put("password", password);

        var body = RequestBody.create(SshelterApi.gson.toJson(m), JSON);

        Response call = this.api.client.newCall(this.api.prepare("POST", "/auth/login", body, false)).execute();
        if (call.code() == 200) {
            var ad = SshelterApi.gson.fromJson(call.body().string(), ApiAuthentication.class);
            this.api.authData = (new AuthenticationData())
                    .setUsername(username)
                    .setToken(ad.token)
                    .setRefreshToken(ad.refreshToken);

            if (this.authenticationUpdatedCallback != null) {
                this.authenticationUpdatedCallback.onAuthenticationUpdated(this.api.authData);
            }

            return;
        }

        if (call.code() == 401) {
            throw new InvalidCredentialsException();
        }

        throw new Exception("Code: " + call.code() + " / Response: " + call.body().string());
    }

    public void refresh() throws Exception {
        var m = new HashMap<String, String>();
        m.put("refresh_token", this.api.authData.getRefreshToken());

        var body = RequestBody.create(SshelterApi.gson.toJson(m), JSON);

        try {
            Response call = this.api.client.newCall(this.api.prepare("POST", "/auth/refresh", body, false)).execute();
            if (call.code() == 200) {
                var ad = SshelterApi.gson.fromJson(call.body().string(), ApiAuthentication.class);
                this.api.authData = this.api.authData
                        .setToken(ad.token)
                        .setRefreshToken(ad.refreshToken);

                if (this.authenticationUpdatedCallback != null) {
                    this.authenticationUpdatedCallback.onAuthenticationUpdated(this.api.authData);
                }

                return;
            }

            if (call.code() == 401) {
                throw new InvalidCredentialsException();
            }

            throw new Exception("Code: " + call.code() + " / Response: " + call.body().string());
        } catch (Exception e) {
            throw e;
        }
    }

    public AuthenticationData getAuthenticationData() {
        return this.api.authData;
    }

    public void onAuthenticationUpdated(OnAuthenticationUpdated callback) {
        this.authenticationUpdatedCallback = callback;
    }

    public void onAuthenticationRequired(OnAuthenticationRequired callback) {
        this.authenticationRequiredCallback = callback;
    }

    public OnAuthenticationUpdated getAuthenticationUpdatedCallback() {
        return this.authenticationUpdatedCallback;
    }

    public OnAuthenticationRequired getAuthenticationRequiredCallback() {
        return this.authenticationRequiredCallback;
    }
}
