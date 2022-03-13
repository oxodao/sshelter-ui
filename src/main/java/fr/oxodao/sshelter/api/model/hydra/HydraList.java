package fr.oxodao.sshelter.api.model.hydra;

import com.google.gson.annotations.SerializedName;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

class View {
    @SerializedName(value = "@id")
    public String id;

    @SerializedName(value = "@type")
    public String type;

    @SerializedName(value = "hydra:first")
    public String first;

    @SerializedName(value = "hydra:last")
    public String last;

    @SerializedName(value = "hydra:previous")
    public String previous;

    @SerializedName(value = "hydra:next")
    public String next;
}

class SearchMapping {
    @SerializedName(value = "@type")
    public String type;

    @SerializedName(value = "variable")
    public String variable;

    @SerializedName(value = "property")
    public String property;

    @SerializedName(value = "required")
    public boolean required;
}

class Search {
    @SerializedName(value = "@type")
    public String type;

    @SerializedName(value = "hydra:template")
    public String template;

    @SerializedName(value = "hydra:variableRepresentation")
    public String variableRepresentation;

    @SerializedName(value = "hydra:mapping")
    public SearchMapping[] mapping;
}

public class HydraList<T> {

    @SerializedName(value = "hydra:member")
    public T[] elements;

    @SerializedName(value = "hydra:totalItems")
    public int count;

    @SerializedName(value = "hydra:view")
    public View view;

    @SerializedName(value = "hydra:search")
    public Search search;

    public static Type getType(Class<?> parameter) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] {parameter};
            }

            @Override
            public Type getRawType() {
                return HydraList.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }
}
