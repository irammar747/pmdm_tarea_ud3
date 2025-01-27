package ramirez.inma.apppokemon.pokedexTab;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import ramirez.inma.apppokemon.R;
import ramirez.inma.apppokemon.modelo.PokemonData;
import ramirez.inma.apppokemon.databinding.ItemNombrePokemonBinding;

public class PokedexViewHolder extends RecyclerView.ViewHolder {

    // Binding que permite acceder a las vistas del item (ImageView, TextView, etc.)
    private final ItemNombrePokemonBinding binding;

    public PokedexViewHolder(ItemNombrePokemonBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Este método se utiliza para enlazar los datos del pokemon con las vistas del item.
     *
     * @param pokemon El objeto PokemonData que contiene los datos del pokemon.
     */
    public void bind(PokemonData pokemon){
        binding.itemNombrePokemon.setText(pokemon.getName());

        // Cambiar color de fondo si el estado es "capturado"
        if (pokemon.isCapturado()) {
            int colorFondo = ContextCompat.getColor(binding.itemNombrePokemon.getContext(), R.color.capturado_color_fondo);
            int colorTexto = ContextCompat.getColor(binding.itemNombrePokemon.getContext(), R.color.capturado_color_texto);
            binding.itemNombrePokemon.setBackgroundColor(colorFondo);
            binding.itemNombrePokemon.setTextColor(colorTexto);

        }

    }
}
