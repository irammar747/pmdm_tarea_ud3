package ramirez.inma.apppokemon.modelo;

import com.google.gson.annotations.SerializedName;

public class Type {
    @SerializedName("type")
    private TypeDetail type;

    public Type(TypeDetail type) {
        this.type = type;
    }

    public void setType(TypeDetail type) {
        this.type = type;
    }

    public String getTypeName() {
        return type != null ? type.getName() : null;
    }
}
