package id.ac.umn.workoutkuy;

public class DataExercise {
    String taskName;
    int reps;
    int sets;


    public DataExercise(String taskName, int reps, int sets) {
        this.taskName = taskName;
        this.reps = reps;
        this.sets = sets;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }



}
