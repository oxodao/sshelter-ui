package fr.oxodao.sshelter.api.callbacks;

import fr.oxodao.sshelter.api.model.AuthenticationData;

public interface OnAuthenticationUpdated {

    void onAuthenticationUpdated(AuthenticationData data);

}
