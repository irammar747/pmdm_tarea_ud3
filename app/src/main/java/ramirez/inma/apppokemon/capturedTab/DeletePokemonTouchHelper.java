package ramirez.inma.apppokemon.capturedTab;

import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class DeletePokemonTouchHelper extends ItemTouchHelper.Callback {

    private final CapturedAdapter adapter;  // Adaptador del RecyclerView

    public DeletePokemonTouchHelper(CapturedAdapter mAdapter) {
        this.adapter = mAdapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //Aquí se habilitan los movimientos
        return makeMovementFlags(0, ItemTouchHelper.LEFT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false; //No queremos permitir mover elementos
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        //Definimos lo que pasa cuando deslizamos hacia la izquierda
        if (direction == ItemTouchHelper.LEFT) {
            // Seleccionamos la posición del elemento que deslizamos
            int position = viewHolder.getAdapterPosition();
            adapter.deletePokemon(position);  // Borramos el pokemon que está en la posición que hemos deslizado
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
