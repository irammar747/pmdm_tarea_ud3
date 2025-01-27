package ramirez.inma.apppokemon.pokemonApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ramirez.inma.apppokemon.modelo.PokemonData;

public class PokemonResponse {
    @SerializedName("results")
    private List<PokemonData> results;

    public List<PokemonData> getResults() {
        return results;
    }

    public void setResults(List<PokemonData> results) {
        this.results = results;
    }
}
