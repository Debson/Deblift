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
import com.deblift.ui.history.WorkoutExercise;

public class TemplateExerciseAdapter extends RecyclerView.Adapter<TemplateExerciseAdapter.MyViewHolder> {

    private WorkoutEntity workoutEntity;

    private PopupMenu popup;


    class MyViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerView;
        private TextView exerciseName;
        private Button addSetButton;
        private Button editExerciseButton;
        private ItemTouchHelper itemTouchHelper;
        private TemplateSetAdapter templateSetAdapter;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise_name_text);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.template_exercise_set_list);
            addSetButton = itemView.findViewById(R.id.add_set_button);
            editExerciseButton = itemView.findViewById(R.id.edit_exercise_button);
            templateSetAdapter = new TemplateSetAdapter();

            recyclerView.setAdapter(templateSetAdapter);
            itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallbackTemplate(templateSetAdapter));
            itemTouchHelper.attachToRecyclerView(recyclerView);

            recyclerView.setItemViewCacheSize(Integer.MAX_VALUE);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }

    public TemplateExerciseAdapter(WorkoutEntity workoutEntity) {
        this.workoutEntity = workoutEntity;
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

        holder.exerciseName.setText(workoutEntity.workoutExercisesList.get(position).getExercise());

        holder.templateSetAdapter.setParentIndex(position);
        holder.templateSetAdapter.setSets(workoutEntity.workoutExercisesList.get(position).getSets());


        // Handle buttons
        holder.addSetButton.setTag(position);
        holder.addSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.templateSetAdapter.addItem();
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
                                removeItem(holder.getAdapterPosition());

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
        return workoutEntity.workoutExercisesList.size();
    }


    public void addItem(WorkoutExercise workoutExercise) {
        workoutEntity.workoutExercisesList.add(workoutExercise);

        notifyDataSetChanged();
        notifyItemInserted(workoutEntity.workoutExercisesList.size() - 1);
    }

    public void removeItem(int pos) {
        workoutEntity.workoutExercisesList.remove(pos);

        notifyItemRemoved(pos);
    }


    public WorkoutEntity getWorkoutEntity() {
        return workoutEntity;
    }
}
