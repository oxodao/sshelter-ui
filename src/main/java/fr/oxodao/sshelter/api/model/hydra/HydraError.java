package fr.oxodao.sshelter.api.model.hydra;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class HydraError {
    @SerializedName("@context")
    public String context;

    @SerializedName("@type")
    public String type;

    @SerializedName("hydra:title")
    public String title;

    @SerializedName("hydra:description")
    public String description;

    @SerializedName("violations")
    public FormViolation[] violations;

    @Override
    public String toString() {
        return title + ": " + description + (this.violations.length > 0 ? "\n" + Arrays.toString(Arrays.stream(violations).map((e) -> e.message).toArray()) : "");
    }
}
