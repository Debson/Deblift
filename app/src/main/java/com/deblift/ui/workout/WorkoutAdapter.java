package com.deblift.ui.workout;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.deblift.R;
import com.deblift.database.AppRoomDatabase;
import com.deblift.ui.history.WorkoutExercise;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.MyViewHolder> {

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView workoutName;
        TextView workoutDate;
        TextView workoutExercises;
        Button moreButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            workoutName = itemView.findViewById(R.id.workout_nameText);
            workoutDate = itemView.findViewById(R.id.workout_date);
            workoutExercises = itemView.findViewById(R.id.workout_exercises);
            moreButton = itemView.findViewById(R.id.workout_moreButton);
        }
    }

    private Button button;
    private PopupMenu popup;
    private Context context;

    private WorkoutFragment workoutFragment;
    private WorkoutEntity[] workoutEntities;

    private int workoutCount = 5;


    public WorkoutAdapter(WorkoutFragment workoutFragment, WorkoutEntity[] workoutEntities) {
        this.workoutFragment = workoutFragment;
        this.workoutEntities = workoutEntities;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_workouts_listview, parent, false);

        context = root.getContext();

        return new MyViewHolder(root);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.workoutName.setText(workoutEntities[position].getWorkoutName());
        String dateStr = prepareWorkoutDateString(position);
        holder.workoutDate.setText(dateStr);

        String exercisesStr = prepareWorkoutExercisesString(position);
        holder.workoutExercises.setText(exercisesStr);


        holder.moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup = new PopupMenu(context, v);
                popup.getMenuInflater().inflate(R.menu.workout_menu_button, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch(item.getItemId()) {
                            case R.id.workout_menu_delete: {
                                // get workout name from textfield
                                // delete workout from database, update

                                // FIX for a bug where there is only one workout left and function provides wrong position for it
                                int pos = position;
                                if(pos >= workoutEntities.length)
                                    pos = position - 1;

                                try {
                                    AppRoomDatabase appDb = AppRoomDatabase.getInstance(workoutFragment.getActivity());
                                    Log.d("WorkoutAdapter", Integer.toString(pos));
                                    appDb.workoutTemplateDao().deleteWorkout(workoutEntities[pos]);
                                    workoutEntities = appDb.workoutTemplateDao().loadAllWorkouts(WorkoutEntity.WORKOUT_TEMPLATE);

                                    notifyItemRemoved(pos);
                                } catch(Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                            // TO BE IMPLEMENTED
                            /*case R.id.workout_menu_duplicate: {

                                notifyDataSetChanged();
                                break;
                            }
                            case R.id.workout_menu_edit: {

                                notifyDataSetChanged();
                                break;
                            }*/
                        }

                        return false;
                    }
                });

                popup.show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = position;
                if(pos >= workoutEntities.length)
                    pos = position - 1;
                workoutFragment.goToWorkoutPage(workoutEntities[position].getWorkoutId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutEntities.length;
    }


    public void updateWorkoutTemplates(WorkoutEntity[] workoutEntities) {
        this.workoutEntities = workoutEntities;

        notifyDataSetChanged();
    }

    private String prepareWorkoutExercisesString(int position) {
        String str = "";
        for(WorkoutExercise w : workoutEntities[position].workoutExercisesList) {
            str += w.getSetCount() + " x ";
            str += w.getExercise();
            str += '\n';
        }

        return str;
    }

    private String prepareWorkoutDateString(int position) {
        String str = "";

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(workoutEntities[position].getWorkoutDate());

        str = new SimpleDateFormat("d MMM yyyy  HH:MM").format(calendar.getTime());

        return str;
    }
}
