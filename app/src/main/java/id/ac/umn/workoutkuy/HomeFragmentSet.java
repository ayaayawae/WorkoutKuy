package id.ac.umn.workoutkuy;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import id.ac.umn.workoutkuy.history.HistoryFitness;

public class HomeFragmentSet extends Fragment {
    private Button setPlanBtn, resetPlanBtn, fullHistory;
    private ImageView planGenderImg;
    private CardView intensityBtn;
    private TextView intensity;
    public int intensityLvl, genderPlan = 0;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    DatabaseReference reference2;

    TextView history1, history2, history3, history4, history5, history6;
    ArrayList<String> history = new ArrayList<>();
    TextView progress;
    ProgressBar progressBar;

    int totalTask, taskDone;
    String gender2, intensity2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_set, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        planGenderImg = (ImageView) view.findViewById(R.id.planGenderImg);
        intensity = view.findViewById(R.id.intensity);
        resetPlanBtn = view.findViewById(R.id.resetPlan);
        history1 = view.findViewById(R.id.history1);
        history2 = view.findViewById(R.id.history2);
        history3 = view.findViewById(R.id.history3);
        history4 = view.findViewById(R.id.history4);
        history5 = view.findViewById(R.id.history5);
        history6 = view.findViewById(R.id.history6);
        fullHistory = view.findViewById(R.id.fullHistory);
        progress = view.findViewById(R.id.progress);
        progressBar = view.findViewById(R.id.progressBar);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());

        rootNode = FirebaseDatabase.getInstance("https://workoutkuy-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = rootNode.getReference("users").child(signInAccount.getId());
        reference2 = rootNode.getReference("dataExercise");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("plan").child("gender").exists() && snapshot.child("plan").child("intensity").exists()){
                    if(snapshot.child("plan").child("gender").getValue(Integer.class) == 1) {
                        planGenderImg.setImageResource(R.drawable.image4);
                        gender2 = "male";
                    } else {
                        planGenderImg.setImageResource(R.drawable.image5);
                        gender2 = "female";
                    }
                    intensityLvl = snapshot.child("plan").child("intensity").getValue(Integer.class);
                    if(intensityLvl == 0) {
                        intensity.setText("Beginner");
                        intensity2 = "beginner";
                    } else if(intensityLvl == 1) {
                        intensity.setText("Intermediate");
                        intensity2 = "intermediate";
                    } else if(intensityLvl == 2) {
                        intensity.setText("Advanced");
                        intensity2 = "advanced";
                    }
                }

                Date date = new Date();
                SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());



                if(snapshot.child("history").exists()){
                    System.out.println(YMD.format(date));
                    if(snapshot.child("history").child(YMD.format(date)).exists()){
                        taskDone = (int) snapshot.child("history").child(YMD.format(date)).child("detail").getChildrenCount();
                        reference2.child(gender2).child(intensity2).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                totalTask = (int) dataSnapshot.getChildrenCount();
                                progress.setText(taskDone + "/" + totalTask + " Task Completed");
                                progressBar.setMax(totalTask);
                                progressBar.setProgress((taskDone));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                    history.clear();
                    for(DataSnapshot item : snapshot.child("history").getChildren()){
                        history.add(item.child("date").getValue(String.class));
                    }
                    Collections.reverse(history);
                    int i = 0;
                    for(String item : history){
                        if(i<6){
                            switch (i){
                                case 0: history1.setText(item); break;
                                case 1: history2.setText(item); break;
                                case 2: history3.setText(item); break;
                                case 3: history4.setText(item); break;
                                case 4: history5.setText(item); break;
                                case 5: history6.setText(item); break;
                            }
                            i++;
                        }
                    }


                }else{
                    history1.setText("- No History -");
                    fullHistory.setEnabled(false);
                    fullHistory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fullHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HistoryFitness.class);
                startActivity(intent);
            }
        });

        resetPlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = rootNode.getReference("users").child(signInAccount.getId());
                reference.child("plan").removeValue();
                reference.child("progress").removeValue();
                reference.child("history").removeValue();

            }
        });

    }
}