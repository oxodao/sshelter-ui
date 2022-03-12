package fr.oxodao.sshelter.api;

import com.google.gson.Gson;
import fr.oxodao.sshelter.api.interceptors.RefreshInterceptor;
import fr.oxodao.sshelter.api.model.AuthenticationData;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SshelterApi {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    protected static Gson gson = new Gson();
    protected OkHttpClient client;
    protected String serverUrl;
    protected AuthenticationData authData;

    private final Authentication auth;
    private final Machines machines;

    public SshelterApi() {
        this.serverUrl = null;
        this.client = new OkHttpClient.Builder().addInterceptor(new RefreshInterceptor(this)).build();
        this.authData = new AuthenticationData();

        this.auth = new Authentication(this);
        this.machines = new Machines(this);
    }

    public SshelterApi(String serverUrl) {
        this();
        if (!serverUrl.endsWith("/")) {
            serverUrl += "/";
        }

        if (!serverUrl.endsWith("/api/")) {
            serverUrl += "api/";
        }

        this.serverUrl = serverUrl;
    }

    public SshelterApi(String serverUrl, AuthenticationData data) {
        this(serverUrl);
        this.authData = data;
    }

    public Authentication Auth() {
        return this.auth;
    }

    public String getServerUrl() {
        return this.serverUrl;
    }

    public Machines Machines() {
        return this.machines;
    }

    protected Request prepare(String method, String endpoint, RequestBody body, boolean authenticated) {
        if (endpoint.startsWith("/")) {
            endpoint = endpoint.substring(1);
        }

        var req = new Request.Builder()
                .url(this.serverUrl + endpoint)
                .header("Accept", "application/ld+json")
                .header("Content-Type", "application/json")
                .method(method, body);

        if (authenticated) {
            req = req.header("Authorization", "Bearer " + this.authData.getToken());
        }

        return req.build();
    }

    protected Request prepare(String method, String endpoint, RequestBody body) {
        return this.prepare(method, endpoint, body, true);
    }

    protected Request prepare(String method, String endpoint) {
        return this.prepare(method, endpoint, null);
    }
}
