package id.ac.umn.workoutkuy;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FitnessFragment extends Fragment {
    ListView listView;
    ArrayList<DataExercise> arr = new ArrayList<>();
    boolean getDataStatus = false;

    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fitness, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());

        listView = view.findViewById(R.id.listView);
        setData(signInAccount.getId());
    }

    public void setData(String id){
        // Gender (1 = Man; 2 = Woman)
        // Intensity (0 : Beginner; 1 : Intermediate; 2 : Advanced)

        rootNode = FirebaseDatabase.getInstance("https://workoutkuy-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = rootNode.getReference("users").child(id);
        reference.child("plan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("gender").getValue(int.class) == 1){
                    if(snapshot.child("intensity").getValue(int.class) == 0){
                        arr.add(new DataExercise("Mountain Climber",    1,0,0,  2,20));
                        arr.add(new DataExercise("Squats",              1,0,10, 2,0));
                        arr.add(new DataExercise("High Stepping",       1,0,0,  2,20));
                        arr.add(new DataExercise("Push-Ups",            1,0,8,  2,0));
                        arr.add(new DataExercise("Reverse Crunches",    1,0,10, 2,0));
                        arr.add(new DataExercise("Plank",               1,0,0,  2,15));
                        arr.add(new DataExercise("Squats",              1,0,10, 2,0));
                        arr.add(new DataExercise("High-Stepping",       1,0,0,  2,20));
                        arr.add(new DataExercise("Sit-Ups",             1,0,8,  2,0));
                        arr.add(new DataExercise("Reverse Crunches",    1,0,10, 2,0));
                        arr.add(new DataExercise("Plank",               1,0,0,  2,15));
                        arr.add(new DataExercise("Cobra Stretch",       1,0,0,  1,25));
                    }else if(snapshot.child("intensity").getValue(int.class) == 1){
                        arr.add(new DataExercise("Mountain Climber",    1,1,0,  2,30));
                        arr.add(new DataExercise("Squats",              1,1,16, 2,0));
                        arr.add(new DataExercise("High Stepping",       1,1,0,  2,30));
                        arr.add(new DataExercise("Push-Ups",            1,1,10, 2,0));
                        arr.add(new DataExercise("Reverse Crunches",    1,1,16, 2,0));
                        arr.add(new DataExercise("Plank",               1,1,0,  2,15));
                        arr.add(new DataExercise("Squats",              1,1,16, 2,0));
                        arr.add(new DataExercise("High-Stepping",       1,1,0,  2,30));
                        arr.add(new DataExercise("Sit-Ups",             1,1,10, 2,0));
                        arr.add(new DataExercise("Reverse Crunches",    1,1,16, 2,0));
                        arr.add(new DataExercise("Plank",               1,1,0,  2,30));
                        arr.add(new DataExercise("Cobra Stretch",       1,1,0,  1,30));
                    }else{
                        arr.add(new DataExercise("Mountain Climber",    1,2,0,  2,30));
                        arr.add(new DataExercise("Squats",              1,2,16, 2,0));
                        arr.add(new DataExercise("High Stepping",       1,2,0,  2,30));
                        arr.add(new DataExercise("Push-Ups",            1,2,15, 2,0));
                        arr.add(new DataExercise("Reverse Crunches",    1,2,16, 2,0));
                        arr.add(new DataExercise("Plank",               1,2,0,  2,30));
                        arr.add(new DataExercise("Squats",              1,2,16, 2,0));
                        arr.add(new DataExercise("High-Stepping",       1,2,0,  2,30));
                        arr.add(new DataExercise("Sit-Ups",             1,2,15, 2,0));
                        arr.add(new DataExercise("Reverse Crunches",    1,2,16, 2,0));
                        arr.add(new DataExercise("Plank",               1,2,0,  2,40));
                        arr.add(new DataExercise("Cobra Stretch",       1,2,0,  1,30));
                    }
                }else{
                    if(snapshot.child("intensity").getValue(int.class) == 0){
                        arr.add(new DataExercise("Squats",                          2,0,10,2,0));
                        arr.add(new DataExercise("Incline Push-Ups",                2,0,8, 2,0));
                        arr.add(new DataExercise("Mountain Climber",                2,0,0, 2,20));
                        arr.add(new DataExercise("Russian Twist",                   2,0,10,2,0));
                        arr.add(new DataExercise("Reverse Crunches",                2,0,12,2,0));
                        arr.add(new DataExercise("Backward Lunge-front kick left",  2,0,4, 2,0));
                        arr.add(new DataExercise("Cat Cow Pose",                    2,0,0, 2,20));
                        arr.add(new DataExercise("Plank",                           2,0,0, 2,20));
                        arr.add(new DataExercise("Cobra Stretch",                   2,0,0, 1,20));
                    }else if(snapshot.child("intensity").getValue(int.class) == 1){
                        arr.add(new DataExercise("Squats",                          2,1,14,2,0));
                        arr.add(new DataExercise("Incline Push-Ups",                2,1,10,2,0));
                        arr.add(new DataExercise("Mountain Climber",                2,1,0, 2,25));
                        arr.add(new DataExercise("Russian Twist",                   2,1,12,2,0));
                        arr.add(new DataExercise("Reverse Crunches",                2,1,14,2,0));
                        arr.add(new DataExercise("Backward Lunge-front kick left",  2,1,5, 2,0));
                        arr.add(new DataExercise("Cat Cow Pose",                    2,1,0, 2,25));
                        arr.add(new DataExercise("Plank",                           2,1,0, 2,25));
                        arr.add(new DataExercise("Cobra Stretch",                   2,1,0, 1,25));
                    }else{
                        arr.add(new DataExercise("Squats",                          2,1,18,2,0));
                        arr.add(new DataExercise("Incline Push-Ups",                2,1,12,2,0));
                        arr.add(new DataExercise("Mountain Climber",                2,1,0, 2,30));
                        arr.add(new DataExercise("Russian Twist",                   2,1,16,2,0));
                        arr.add(new DataExercise("Reverse Crunches",                2,1,16,2,0));
                        arr.add(new DataExercise("Backward Lunge-front kick left",  2,1,6, 2,0));
                        arr.add(new DataExercise("Cat Cow Pose",                    2,1,0, 2,30));
                        arr.add(new DataExercise("Plank",                           2,1,0, 2,30));
                        arr.add(new DataExercise("Cobra Stretch",                   2,1,0, 1,30));
                    }
                }
                FitnessAdapter fitnessAdapter = new FitnessAdapter(getContext(),R.layout.row, arr);
                listView.setAdapter(fitnessAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}