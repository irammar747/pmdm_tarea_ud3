package ramirez.inma.apppokemon.pokedexTab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ramirez.inma.apppokemon.MainActivity;
import ramirez.inma.apppokemon.R;
import ramirez.inma.apppokemon.modelo.PokemonData;
import ramirez.inma.apppokemon.databinding.ItemNombrePokemonBinding;

public class PokedexAdapter extends RecyclerView.Adapter<PokedexViewHolder> {
    // Lista de objetos PokemonData que contienen los datos de los pokemon
    private final ArrayList<PokemonData> pokemons;
    // Contexto de la actividad en la que se está utilizando el RecyclerView
    private final Context context;
    private OnItemClickListener listener;
    ItemNombrePokemonBinding binding;

    public PokedexAdapter(ArrayList<PokemonData> pokemons, Context context, OnItemClickListener listener) {
        this.pokemons = pokemons;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PokedexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemNombrePokemonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PokedexViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PokedexViewHolder holder, int position) {
        PokemonData currentPokemon = this.pokemons.get(position);
        holder.bind(currentPokemon);

        //Manejar el evento de clic
        holder.itemView.setOnClickListener(view -> {
            if(listener != null){
                String[] partes = currentPokemon.getUrl().split("/");
                currentPokemon.setIndex(Integer.parseInt(partes[6]));
                listener.onItemClick(currentPokemon);

                int colorFondo = ContextCompat.getColor(binding.itemNombrePokemon.getContext(), R.color.capturado_color_fondo);
                int colorTexto = ContextCompat.getColor(binding.itemNombrePokemon.getContext(), R.color.capturado_color_texto);
                binding.itemNombrePokemon.setBackgroundColor(colorFondo);
                binding.itemNombrePokemon.setTextColor(colorTexto);

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.pokemons.size();
    }

    public void addData(List<PokemonData> newPokemon) {
        this.pokemons.clear();
        this.pokemons.addAll(newPokemon);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(PokemonData pokemon); // Recibe la URL del Pokémon
    }
}
