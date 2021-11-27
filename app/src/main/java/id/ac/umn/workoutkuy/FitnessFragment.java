package id.ac.umn.workoutkuy;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FitnessFragment extends Fragment {
    ListView listView;
    String mTaskName[] = {"Sit Up", "Push Up"};
    String mTaskDetail[] = {"2x2", "4x4"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fitness, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listView);


        ArrayList<DataExercise> arrayList = new ArrayList<>();

        arrayList.add(new DataExercise("Sit Up",25,10));
        arrayList.add(new DataExercise("Push Up",20,20));
        arrayList.add(new DataExercise("Sit Up",25,10));
        arrayList.add(new DataExercise("Push Up",20,20));
        arrayList.add(new DataExercise("Sit Up",25,10));
        arrayList.add(new DataExercise("Push Up",20,20));
        arrayList.add(new DataExercise("Sit Up",25,10));
        arrayList.add(new DataExercise("Push Up",20,20));
        arrayList.add(new DataExercise("Sit Up",25,10));
        arrayList.add(new DataExercise("Push Up",20,20));
        arrayList.add(new DataExercise("Sit Up",25,10));
        arrayList.add(new DataExercise("Push Up",20,20));
        arrayList.add(new DataExercise("Sit Up",25,10));
        arrayList.add(new DataExercise("Push Up",20,20));
        arrayList.add(new DataExercise("Sit Up",25,10));
        arrayList.add(new DataExercise("Push Up",20,20));
        arrayList.add(new DataExercise("Sit Up",25,10));
        arrayList.add(new DataExercise("Push Up",20,20));



        FitnessAdapter fitnessAdapter = new FitnessAdapter(getContext(),R.layout.row, arrayList);

        listView.setAdapter(fitnessAdapter);
    }


}