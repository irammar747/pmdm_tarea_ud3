package ramirez.inma.apppokemon.capturedTab;

import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import ramirez.inma.apppokemon.R;
import ramirez.inma.apppokemon.databinding.ItemCardviewPokemonBinding;
import ramirez.inma.apppokemon.databinding.ItemNombrePokemonBinding;
import ramirez.inma.apppokemon.modelo.PokemonData;
import ramirez.inma.apppokemon.modelo.PokemonDetail;

public class CapturedViewHolder extends RecyclerView.ViewHolder{

    private ItemCardviewPokemonBinding binding;

    public CapturedViewHolder(ItemCardviewPokemonBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Este método se utiliza para enlazar los datos del pokemon con las vistas del item.
     *
     * @param pokemon El objeto PokemonData que contiene los datos del pokemon.
     */
    public void bind(PokemonDetail pokemon){
        binding.name.setText(pokemon.getName());
        binding.height.setText("Peso: " + String.valueOf(pokemon.getHeight()));
        binding.weight.setText("Altura: " + String.valueOf(pokemon.getWeight()));
        binding.index.setText("Índice: " + String.valueOf(pokemon.getIndex()));

        Glide.with(binding.pokemonImage.getContext())
                .load(pokemon.getFrontShinySprite())  // Imagen en caso de error (opcional)
                .into(binding.pokemonImage);


        for (String name:pokemon.getTypesNames()){
            binding.type.append(name+" ");
        }
        binding.executePendingBindings(); // Asegura que se apliquen los cambios de inmediato
    }
}
