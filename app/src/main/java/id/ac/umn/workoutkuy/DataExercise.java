package id.ac.umn.workoutkuy;

import java.io.Serializable;
import java.util.ArrayList;

public class DataExercise implements Serializable {
    String taskName;
    int gender;
    int intensity;
    int reps;
    int sets;
    int time;

    public DataExercise(String taskName, int gender, int intensity, int reps, int sets, int time) {
        this.taskName = taskName;
        this.gender = gender;
        this.intensity = intensity;
        this.reps = reps;
        this.sets = sets;
        this.time = time;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getGender() {
        return gender;
    }

    public int getIntensity() {
        return intensity;
    }

    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }

    public int getTime() {
        return time;
    }
}
