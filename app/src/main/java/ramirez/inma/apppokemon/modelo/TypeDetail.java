package ramirez.inma.apppokemon.modelo;

import com.google.gson.annotations.SerializedName;

public class TypeDetail {
    @SerializedName("name")
    private String name;

    public TypeDetail(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
