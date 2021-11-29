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
    String photo, photoGif;

    public DataExercise(String taskName, int gender, int intensity, int reps, int sets, int time, String photo, String photoGif) {
        this.taskName = taskName;
        this.gender = gender;
        this.intensity = intensity;
        this.reps = reps;
        this.sets = sets;
        this.time = time;
        this.photo = photo;
        this.photoGif = photoGif;
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

    public String getPhoto() {
        return photo;
    }

    public String getPhotoGif() {
        return photoGif;
    }
}
