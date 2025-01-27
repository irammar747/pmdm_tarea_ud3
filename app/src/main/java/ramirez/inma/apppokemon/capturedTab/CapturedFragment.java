package ramirez.inma.apppokemon.capturedTab;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import ramirez.inma.apppokemon.databinding.FragmentPokemonCapturedBinding;
import ramirez.inma.apppokemon.modelo.PokemonDetail;
import ramirez.inma.apppokemon.modelo.Sprites;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CapturedFragment extends Fragment {

    private FragmentPokemonCapturedBinding binding;
    private ArrayList<PokemonDetail> pokemons;
    private CapturedAdapter adapter;

    private ItemTouchHelper itemTouchHelper;


    public CapturedFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPokemonCapturedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();

        adapter = new CapturedAdapter(pokemons, getActivity());
        binding.capturedRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.capturedRecyclerview.setAdapter(adapter);

        // Configurar el ItemTouchHelper para el deslizamiento a la izquierda
        DeletePokemonTouchHelper swipeToDelete = new DeletePokemonTouchHelper(adapter);
        itemTouchHelper = new ItemTouchHelper(swipeToDelete);

        // Verificar si la eliminación está habilitada al inicio
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        boolean isDeleteEnabled = sharedPreferences.getBoolean("delete_pokemon_enabled", true);
        toggleItemTouchHelper(isDeleteEnabled);

        // Escuchar los cambios en SharedPreferences
        sharedPreferences.registerOnSharedPreferenceChangeListener((sharedPrefs, key) -> {
            if ("delete_pokemon_enabled".equals(key)) {
                boolean isEnabled = sharedPrefs.getBoolean(key, false);
                toggleItemTouchHelper(isEnabled);
            }
        });
    }

    // Método para activar o desactivar el ItemTouchHelper
    private void toggleItemTouchHelper(boolean isEnabled) {
        if (isEnabled) {
            itemTouchHelper.attachToRecyclerView(binding.capturedRecyclerview);
        } else {
            itemTouchHelper.attachToRecyclerView(null);
        }
    }


    private void loadData() {
        pokemons = new ArrayList<>();
        // Llenar la lista con datos de los pokemons
        //Creamos una instancia de la base de datos
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("pokemon")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PokemonDetail p = new PokemonDetail();
                                p.setName(document.getString("name"));
                                p.setWeight(document.getDouble("weight"));
                                p.setHeight(document.getDouble("height"));
                                p.setSprites(new Sprites(document.getString("frontShinySprite")));
                                p.setIndex(((Long)document.get("index")).intValue());
                                p.setId(document.getId());

                                CapturedFragment.this.pokemons.add(p);
                                // Notificar al adaptador que los datos han cambiado
                                adapter.notifyDataSetChanged();

                            }
                        } else {
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}