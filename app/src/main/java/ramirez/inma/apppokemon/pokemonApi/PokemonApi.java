package ramirez.inma.apppokemon.pokemonApi;

import ramirez.inma.apppokemon.modelo.PokemonDetail;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface PokemonApi {
    @GET("pokemon")
    Call<PokemonResponse> getPokemonList(
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @GET
    Call<PokemonDetail> getPokemonDetail(@Url String url);
}
