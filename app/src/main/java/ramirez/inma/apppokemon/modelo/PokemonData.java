package ramirez.inma.apppokemon.modelo;

import com.google.gson.annotations.SerializedName;

public class PokemonData {

    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
    private int index;
    private boolean capturado;

    public PokemonData(String name, String url) {
        this.name = name;
        this.url = url;
        this.capturado = false; //Lo inicializamos a false
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isCapturado() {
        return capturado;
    }

    public void setCapturado(boolean capturado) {
        this.capturado = capturado;
    }
}
