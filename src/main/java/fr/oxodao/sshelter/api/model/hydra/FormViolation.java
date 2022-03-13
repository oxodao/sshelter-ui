package fr.oxodao.sshelter.api.model.hydra;

import com.google.gson.annotations.SerializedName;

public class FormViolation {

    @SerializedName("propertyPath")
    public String propertyPath;

    @SerializedName("message")
    public String message;

    @SerializedName("code")
    public String code;

}
