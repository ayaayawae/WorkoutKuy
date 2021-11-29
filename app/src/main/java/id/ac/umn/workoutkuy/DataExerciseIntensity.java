package id.ac.umn.workoutkuy;

import java.util.ArrayList;

public class DataExerciseIntensity {
    int intensity;
    ArrayList<DataExerciseGender> dataExerciseGender;

    public int getIntensity() {
        return intensity;
    }

    public ArrayList<DataExerciseGender> getDataExerciseGender() {
        return dataExerciseGender;
    }

    public DataExerciseIntensity(int intensity, ArrayList<DataExerciseGender> dataExerciseGender) {
        this.intensity = intensity;
        this.dataExerciseGender = dataExerciseGender;
    }

}
