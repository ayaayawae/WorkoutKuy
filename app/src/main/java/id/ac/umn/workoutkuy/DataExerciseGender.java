package id.ac.umn.workoutkuy;

import java.util.ArrayList;

public class DataExerciseGender {
    char gender;
    DataExerciseRepSet dataExerciseRepSet;

    public char getGender() {
        return gender;
    }

    public DataExerciseRepSet getDataExerciseRepSet() {
        return dataExerciseRepSet;
    }

    public DataExerciseGender(char gender, DataExerciseRepSet dataExerciseRepSet) {
        this.gender = gender;
        this.dataExerciseRepSet = dataExerciseRepSet;
    }
}
