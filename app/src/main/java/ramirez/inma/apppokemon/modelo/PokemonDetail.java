package ramirez.inma.apppokemon.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PokemonDetail {

    private String id; //Campo id de firebase
        @SerializedName("sprites")
        private Sprites sprites;
        @SerializedName("name")
        private String name;
        @SerializedName("weight")
        private double weight;
        @SerializedName("height")
        private double height;
        private int index;
        @SerializedName("types")
        private List<Type> types;



    public PokemonDetail() {
        this.types = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public String getFrontShinySprite() {
        return sprites != null ? this.sprites.getFrontShiny() : null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<String> getTypesNames() {
        // Mapea solo los nombres de los tipos
        if (types != null) {
            List<String> typeNames = new ArrayList<>();
            for (Type type : types) {
                typeNames.add(type.getTypeName());
            }
            return typeNames;
        }
        return null;
    }

}

