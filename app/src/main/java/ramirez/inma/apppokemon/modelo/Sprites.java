package ramirez.inma.apppokemon.modelo;

import com.google.gson.annotations.SerializedName;

public class Sprites {
    @SerializedName("front_shiny")
    private String frontShiny;

    public Sprites(String frontShiny){
        this.frontShiny = frontShiny;
    }
    public void setFrontShiny(String frontShiny) {
        this.frontShiny = frontShiny;
    }

    public String getFrontShiny() {
        return frontShiny;
    }
}
