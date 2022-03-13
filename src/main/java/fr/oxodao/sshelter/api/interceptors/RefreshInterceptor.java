package fr.oxodao.sshelter.api.interceptors;

import fr.oxodao.sshelter.api.SshelterApi;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class RefreshInterceptor implements Interceptor {

    private final SshelterApi api;

    public RefreshInterceptor(SshelterApi api) {
        this.api = api;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request rq = chain.request();
        String url = rq.url().url().toString().substring(this.api.getServerUrl().length());
        if (url.startsWith("auth")) {
            return chain.proceed(rq);
        }

        if (this.api.Auth().getAuthenticationData().isExpired() && rq.headers().get("Authorization") != null) {
            try {
                this.api.Auth().refresh();
                if (this.api.Auth().getAuthenticationUpdatedCallback() != null) {
                    this.api.Auth().getAuthenticationUpdatedCallback().onAuthenticationUpdated(this.api.Auth().getAuthenticationData());
                }
            } catch (Exception e) {
                if (this.api.Auth().getAuthenticationRequiredCallback() == null) {
                    throw new IOException("Could not re-authenticate ! You need to set a authentication required callback !");
                }

                this.api.Auth().getAuthenticationRequiredCallback().onAuthenticationRequired();
            }
        }

        return chain.proceed(rq);
    }

}
