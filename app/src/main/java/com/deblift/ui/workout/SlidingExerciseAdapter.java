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

import com.deblift.database.AppRoomDatabase;
import com.deblift.ui.exercises.Exercise;
import com.deblift.ui.history.WorkoutExercise;

public class SlidingExerciseAdapter extends RecyclerView.Adapter<SlidingExerciseAdapter.MyViewHolder> {

    private static ArrayList<SlidingSetAdapter> adapterList = new ArrayList<>();
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private ArrayList<Integer> sets = new ArrayList<>();

    private WorkoutEntity workoutEntity;

    private Context context;

    private PopupMenu popup;

    class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        private TextView exerciseName;
        private Button addSetButton;
        private Button editExerciseButton;
        private ItemTouchHelper itemTouchHelper;
        SlidingSetAdapter slidingSetAdapter;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise_name_text);;
            recyclerView = (RecyclerView) itemView.findViewById(R.id.sliding_exercise_set_list);
            addSetButton = itemView.findViewById(R.id.add_set_button);
            editExerciseButton = itemView.findViewById(R.id.edit_exercise_button);
            slidingSetAdapter = new SlidingSetAdapter();
            adapterList.add(slidingSetAdapter);

            recyclerView.setAdapter(slidingSetAdapter);
            itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallbackSliding(slidingSetAdapter));
            itemTouchHelper.attachToRecyclerView(recyclerView);


            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }


    public SlidingExerciseAdapter() {


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_sliding_workout_exercise_listview, parent, false);


        context = parent.getContext();

        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.exerciseName.setText(exercises.get(position).getExerciseName());


        holder.slidingSetAdapter.setParentIndex(position);
        holder.slidingSetAdapter.setParentAdapter(this);

        // That block will initialize set counter with values obtained in "updateAdapterWithWorkout" method
        if(!sets.isEmpty() && position < sets.size()) {
            holder.slidingSetAdapter.setSetCounter(sets.get(position));
            sets.set(position, holder.slidingSetAdapter.getItemCount());
        }


        // Handle buttons
        holder.addSetButton.setTag(position);
        holder.addSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.slidingSetAdapter.addItem(position);
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
        notifyDataSetChanged();
        //notifyItemInserted(exercises.size() + 1);
    }

    public void removeItem(int pos) {
        exercises.remove(pos);
        notifyItemRemoved(pos);
    }

    public ArrayList<SlidingSetAdapter> getAdapterList() {
        return adapterList;
    }

    public ArrayList<Exercise> getExerciseList() {
        return exercises;
    }

    public ArrayList<Integer> getSets() {
        return sets;
    }



    public void setWorkoutEntity(WorkoutEntity workoutEntity) {
        this.workoutEntity = workoutEntity;

        updateAdapterWithWorkout();
    }

    public void updateAdapterWithWorkout()
    {
        exercises.clear();
        sets.clear();
        AppRoomDatabase appDb = AppRoomDatabase.getInstance(context);
        for(WorkoutExercise w : workoutEntity.workoutExercisesList) {
            Exercise ex = appDb.exercisesDao().loadExercise(w.getExercise());
            exercises.add(ex);

            sets.add(w.getSetCount());

        };

    }
}
