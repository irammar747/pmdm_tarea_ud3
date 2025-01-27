package ramirez.inma.apppokemon.capturedTab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import ramirez.inma.apppokemon.R;
import ramirez.inma.apppokemon.databinding.ItemCardviewPokemonBinding;
import ramirez.inma.apppokemon.modelo.PokemonDetail;

public class CapturedAdapter extends RecyclerView.Adapter<CapturedViewHolder> {
    // Lista de objetos PersonajeData que contienen los datos de los personajes
    private final ArrayList<PokemonDetail> pokemons;
    // Contexto de la actividad en la que se está utilizando el RecyclerView
    private final Context context;


    public CapturedAdapter(ArrayList<PokemonDetail> pokemons, Context context) {
        this.pokemons = pokemons;
        this.context = context;
    }

    @NonNull
    @Override
    public CapturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardviewPokemonBinding binding = ItemCardviewPokemonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CapturedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CapturedViewHolder holder, int position) {
        PokemonDetail currentPok = this.pokemons.get(position);
        holder.bind(currentPok);
    }

    @Override
    public int getItemCount() {
        return this.pokemons.size();
    }

    public void deletePokemon(int position) {
        PokemonDetail pokemonDelete = this.pokemons.get(position);

        //Recuperamos el id del pokemon seleccionado
        String id = pokemonDelete.getId();

        //Una vez obtenido el pokemon, lo borramos de la BD
        //Creamos una instancia de la base de datos
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("pokemon").document(id)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    this.pokemons.remove(position);  // Eliminamos de la lista
                    notifyItemRemoved(position);  // Notificamos al RecyclerView
                    Toast.makeText(context, R.string.pokemon_delete, Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, R.string.pokemon_no_delete, Toast.LENGTH_SHORT).show();
                });
    }
}
