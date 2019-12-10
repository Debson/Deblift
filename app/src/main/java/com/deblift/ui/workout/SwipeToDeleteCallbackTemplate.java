/*
 *   CLASS BASED ON A TUTORIAL
 *
 *   Link: https://medium.com/@zackcosborn/step-by-step-recyclerview-swipe-to-delete-and-undo-7bbae1fce27e
 *
 *   Description: Tutorial shows how to implement widget that will allow to Swipe left or right item in a List.
 *
 */


package com.deblift.ui.workout;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDeleteCallbackTemplate extends ItemTouchHelper.SimpleCallback {

    private TemplateSetAdapter templateSetAdapter;
    private final ColorDrawable background;

    public SwipeToDeleteCallbackTemplate(TemplateSetAdapter adapter) {
        super(0, ItemTouchHelper.LEFT);
        templateSetAdapter = adapter;
        background = new ColorDrawable(Color.argb(200,255, 0, 0));
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        templateSetAdapter.removeItem(position);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        // Swipe left
        if (dX < 0) {
            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else {
            // No swipe
            background.setBounds(0, 0, 0, 0);
        }
        background.draw(c);
    }


}
