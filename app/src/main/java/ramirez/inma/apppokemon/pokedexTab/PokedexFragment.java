package ramirez.inma.apppokemon.pokedexTab;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import ramirez.inma.apppokemon.R;
import ramirez.inma.apppokemon.modelo.PokemonDetail;
import ramirez.inma.apppokemon.pokemonApi.PokemonApi;
import ramirez.inma.apppokemon.modelo.PokemonData;
import ramirez.inma.apppokemon.pokemonApi.PokemonResponse;
import ramirez.inma.apppokemon.pokemonApi.RetrofitClient;
import ramirez.inma.apppokemon.databinding.FragmentPokedexBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PokedexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PokedexFragment extends Fragment {

    private FragmentPokedexBinding binding; // Binding para el layout
    private ArrayList<PokemonData> pokemons; // Lista de personajes
    private PokedexAdapter adapter; // Adaptador del RecyclerView

    // Método estático para crear una nueva instancia del fragmento
    public static PokedexFragment newInstance() {
        return new PokedexFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPokedexBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializa la lista de personajes
        loadData(); // Cargar los pokemons

        // Configurar el RecyclerView
        adapter = new PokedexAdapter(pokemons, getActivity(), this::loadPokemonDetail);
        binding.pokedexRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.pokedexRecyclerview.setAdapter(adapter);

    }

    private void loadData() {
        this.pokemons = new ArrayList<>();

        PokemonApi api = RetrofitClient.getRetrofitInstance().create(PokemonApi.class);
        Call<PokemonResponse> call = api.getPokemonList(0, 50);

        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(@NonNull Call<PokemonResponse> call, @NonNull Response<PokemonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PokemonData> fetchedPokemon = response.body().getResults();

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    //Verificamos cuáles están capturados consultandolo en la BD

                    for(PokemonData pokemon : fetchedPokemon){
                        db.collection("pokemon")
                                .whereEqualTo("name", pokemon.getName())
                                .get()
                                .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful() && !task1.getResult().isEmpty()) {
                                                // Si se encuentra en Firestore, actualizamos el atributo capturado a true
                                                pokemon.setCapturado(true);
                                            }
                                });
                        adapter.addData(fetchedPokemon);
                    }

                } else {
                    Toast.makeText(getContext(), getString(R.string.error_detalles), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PokemonResponse> call, @NonNull Throwable t) {
                if (getContext() != null) {
                    Toast.makeText(getContext(), getString(R.string.error_detalles), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void loadPokemonDetail(PokemonData pokemon) {
        PokemonApi api = RetrofitClient.getRetrofitInstance().create(PokemonApi.class);
        api.getPokemonDetail(pokemon.getUrl()).enqueue(new Callback<PokemonDetail>() {
            @Override
            public void onResponse(Call<PokemonDetail> call, Response<PokemonDetail> response) {
                if (response.isSuccessful()) {
                    PokemonDetail pokemonDetail = response.body();
                    pokemonDetail.setIndex(pokemon.getIndex());
                    savePokemonDetailToFirebase(pokemonDetail);
                }
            }

            @Override
            public void onFailure(Call<PokemonDetail> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.error_detalles), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void savePokemonDetailToFirebase(PokemonDetail pokemonDetail) {

        //Creamos una instancia de la base de datos
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Consulta para verificar si ya existe un Pokémon con el mismo nombre
        db.collection("pokemon")
                .whereEqualTo("name", pokemonDetail.getName()) // Busca por el campo 'name'
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            // Si encuentra el pokémon informamos y no añadimos
                            Toast.makeText(getActivity(), getString(R.string.pokemon_ya_capturado), Toast.LENGTH_SHORT).show();
                        } else {
                            // Si no se encuentra, se añade a la BD
                            db.collection("pokemon").add(pokemonDetail)
                                    .addOnSuccessListener(
                                            runnable -> {
                                                Toast.makeText(getActivity(), getString(R.string.pokemon_capturado), Toast.LENGTH_SHORT).show();
                                            })
                                    .addOnFailureListener(runnable ->
                                            Toast.makeText(getActivity(), getString(R.string.pokemon_escapo), Toast.LENGTH_SHORT).show());
                        }
                    } else {
                        // Error al realizar la consulta
                        Toast.makeText(getActivity(), getString(R.string.pokemon_error_campro_capt), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}