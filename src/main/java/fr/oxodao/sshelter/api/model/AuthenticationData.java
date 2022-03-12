package fr.oxodao.sshelter.api.model;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;

public class AuthenticationData {

    private String username;
    private transient Token parsedToken;
    private String token;
    private String refreshToken;

    public String getUsername() {
        return username;
    }

    public AuthenticationData setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getToken() {
        return token;
    }

    public AuthenticationData setToken(String token) {
        this.token = token;
        this.parseToken();
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public AuthenticationData setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    private void parseToken() {
        if (this.token == null || this.token.length() == 0) {
            this.parsedToken = null;
            return;
        }

        Gson gson = new Gson();
        if (this.token == null || this.token.length() == 0) {
            this.parsedToken = null;
            return;
        }

        String[] tokenIndexes = this.token.split("\\.");
        if (tokenIndexes.length != 3) {
            this.parsedToken = null;
            return;
        }

        try {
            String decoded = new String(Base64.getDecoder().decode(tokenIndexes[1]), StandardCharsets.UTF_8);
            this.parsedToken = gson.fromJson(decoded, Token.class);
        } catch (Exception e) {
            this.parsedToken = null;
        }
    }

    public boolean isValid() {
        if (this.username == null || this.username.length() == 0) {
            return false;
        }

        if (this.token == null || this.token.length() == 0 || this.refreshToken == null || this.refreshToken.length() == 0) {
            return false;
        }

        if (this.isExpired()) {
            return false;
        }
        // @TODO check if the refreshtoken is not expired

        return true;
    }

    public boolean isExpired() {
        if (this.parsedToken == null) {
            this.parseToken();
            if (this.parsedToken == null) {
                return true;
            }
        }

        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, 30);

        return (now.getTimeInMillis()/1000) > this.parsedToken.expiresAt;
    }
}
