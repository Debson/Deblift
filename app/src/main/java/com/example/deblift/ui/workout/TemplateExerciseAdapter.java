package com.example.deblift.ui.workout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deblift.R;

import java.util.ArrayList;
import com.example.deblift.utils.MyViewHolder;

public class TemplateExerciseAdapter extends RecyclerView.Adapter {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<TemplateSetAdapter> adapterList = new ArrayList<>();

    private Button addSetButton;

    private Button editExerciseButton;
    private PopupMenu popup;

    private int itemCount = 2;

    public TemplateExerciseAdapter() {
        for(int i = 0; i < itemCount; i++)
            adapterList.add(new TemplateSetAdapter());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_workout_template_exercise_listview, parent, false);

        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        recyclerView = holder.itemView.findViewById(R.id.template_exercise_set_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(holder.itemView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter = adapterList.get(position);

        //setupListView(recyclerView);
        recyclerView.setAdapter(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallbackTemplate(adapterList.get(position)));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        addSetButton = holder.itemView.findViewById(R.id.add_set_button);
        addSetButton.setTag(position);
        setupAddSetButton(addSetButton);

        editExerciseButton = holder.itemView.findViewById(R.id.edit_exercise_button);
        editExerciseButton.setTag(position);
        setupEditExerciseButton(editExerciseButton, holder);

    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    private void setupAddSetButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapterList.get((Integer)v.getTag()).addItem((Integer)v.getTag());
                //adapter.notifyDataSetChanged();

                Log.d("Add set: ", " button pressed " + v.getTag());
            }
        });
    }

    private void setupEditExerciseButton(Button button, final RecyclerView.ViewHolder holder)
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup = new PopupMenu(holder.itemView.getContext(), v);
                popup.getMenuInflater().inflate(R.menu.exercise_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch(item.getItemId()) {
                            case R.id.exercise_menu_remove: {
                                removeItem(0);
                                break;
                            }
                        }

                        return false;
                    }
                });

                popup.show();
            }
        });

    }

    public void addItem() {
        itemCount++;
        adapterList.add(new TemplateSetAdapter());

        notifyDataSetChanged();
    }

    public void removeItem(int pos) {
        itemCount--;

        notifyDataSetChanged();
    }
}
