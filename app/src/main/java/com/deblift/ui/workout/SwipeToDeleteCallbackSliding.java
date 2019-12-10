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

public class SwipeToDeleteCallbackSliding extends ItemTouchHelper.SimpleCallback {

    private SlidingSetAdapter templateSetAdapter;
    private SlidingExerciseAdapter parentAdapter;
    private final ColorDrawable background;

    public SwipeToDeleteCallbackSliding(SlidingSetAdapter adapter, SlidingExerciseAdapter parentAdapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        templateSetAdapter = adapter;
        this.parentAdapter = parentAdapter;

        background = new ColorDrawable(Color.RED);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        templateSetAdapter.removeItem(position);


        /*
         * This method is being called first in sequence of set/exercise deletion.
         * First of all, delete set, if its the last set in the exercise(feature for SlidiingPanel only)
         * then delete the exercise. Deletion of exercise must be performed after the set is deleted, otherwise
         * SlidingSetAdapter will apply changes to the wrong adapter;
         */
        if(position <= 0)
            parentAdapter.notifyDataSetChanged();

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
