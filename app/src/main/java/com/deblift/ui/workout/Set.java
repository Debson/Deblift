package com.deblift.ui.workout;

public class Set {

    private int position;
    private float weight;
    private int reps;
    private boolean checked;

    public Set(int position, float weight, int reps, boolean checked) {
        this.position = position;
        this.weight = weight;
        this.reps = reps;
        this.checked = checked;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
