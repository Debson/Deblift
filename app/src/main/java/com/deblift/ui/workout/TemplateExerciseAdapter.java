package com.deblift.ui.workout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deblift.R;

import java.util.ArrayList;

import com.deblift.ui.exercises.Exercise;

public class TemplateExerciseAdapter extends RecyclerView.Adapter<TemplateExerciseAdapter.MyViewHolder> {

    private static ArrayList<TemplateSetAdapter> adapterList = new ArrayList<>();
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private ArrayList<Integer> sets = new ArrayList<>();

    private PopupMenu popup;

    private int itemCount = 0;

    class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        private Button addSetButton;
        private Button editExerciseButton;
        private ItemTouchHelper itemTouchHelper;
        TemplateSetAdapter templateSetAdapter;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.template_exercise_set_list);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemViewCacheSize(20);
            addSetButton = itemView.findViewById(R.id.add_set_button);
            editExerciseButton = itemView.findViewById(R.id.edit_exercise_button);
            templateSetAdapter = new TemplateSetAdapter();
            adapterList.add(templateSetAdapter);

            recyclerView.setAdapter(templateSetAdapter);
            itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallbackTemplate(templateSetAdapter));
            itemTouchHelper.attachToRecyclerView(recyclerView);


            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }

    public TemplateExerciseAdapter(Context context) {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_workout_template_exercise_listview, parent, false);

        return new MyViewHolder(root);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        TextView exerciseName = holder.itemView.findViewById(R.id.exercise_name_text);
        exerciseName.setText(exercises.get(position).getExerciseName());

        // Setup sets recycler view and adapter

        /*RecyclerView recyclerView = holder.itemView.findViewById(R.id.template_exercise_set_list);

        layoutManager = new LinearLayoutManager(holder.itemView.getContext());
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(adapterList.get(position));
        adapterList.get(position).attachTouchHelperToRecyclerView(recyclerView);*/

        sets.add(position, holder.templateSetAdapter.getItemCount());


        holder.addSetButton.setTag(position);
        holder.addSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.templateSetAdapter.addItem(position);
            }
        });

        holder.editExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup = new PopupMenu(holder.itemView.getContext(), v);
                popup.getMenuInflater().inflate(R.menu.exercise_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch(item.getItemId()) {
                            case R.id.exercise_menu_remove: {
                                removeItem(position);
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

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public void saveSets() {
        sets.clear();
        for(int i = 0; i < adapterList.size(); i++)
            sets.add(i, adapterList.get(i).getItemCount());
    }

    public void addItem(Exercise exercise) {
        exercises.add(exercise);
        notifyItemInserted(exercises.size() + 1);
    }

    public void removeItem(int pos) {
        exercises.remove(pos);
        notifyItemRemoved(pos);
    }

    public ArrayList<TemplateSetAdapter> getAdapterList() {
        return adapterList;
    }

    public ArrayList<Exercise> getExerciseList() {
        return exercises;
    }

    public ArrayList<Integer> getSets() {
        return sets;
    }
}
