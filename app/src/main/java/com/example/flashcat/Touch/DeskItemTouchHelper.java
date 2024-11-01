package com.example.flashcat.Touch;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcat.Adapter.HomeDeskAdapter;
import com.example.flashcat.R;
import com.example.flashcat.Touch.ItemTouchHelperListener;

public class DeskItemTouchHelper extends ItemTouchHelper.SimpleCallback{
    private ItemTouchHelperListener listener;
    public DeskItemTouchHelper(int dragDirs, int swipeDirs, ItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if(listener!=null){
            listener.onSwiped(viewHolder); //de call back su kien onSwiped ra mainActivity de xu ly
        }
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder!=null)
        {
            View foregroundView = ((HomeDeskAdapter.DeskViewHolder) viewHolder).itemView.findViewById(R.id.layout_foreground_desk);
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundView = ((HomeDeskAdapter.DeskViewHolder) viewHolder).itemView.findViewById(R.id.layout_foreground_desk);
        getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundView = ((HomeDeskAdapter.DeskViewHolder) viewHolder).itemView.findViewById(R.id.layout_foreground_desk);
        getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View foregroundView = ((HomeDeskAdapter.DeskViewHolder) viewHolder).itemView.findViewById(R.id.layout_foreground_desk);
        getDefaultUIUtil().clearView(foregroundView);
    }
}
