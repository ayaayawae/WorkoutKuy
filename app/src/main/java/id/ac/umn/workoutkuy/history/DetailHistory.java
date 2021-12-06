package id.ac.umn.workoutkuy.history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.ac.umn.workoutkuy.DataExercise;
import id.ac.umn.workoutkuy.FitnessAdapter;
import id.ac.umn.workoutkuy.R;

public class DetailHistory extends AppCompatActivity {
    String date;
    ArrayList<DataExercise> arr = new ArrayList<>();
    TextView dateTitle;
    ListView listView;


    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    DatabaseReference reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        rootNode = FirebaseDatabase.getInstance("https://workoutkuy-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = rootNode.getReference("users").child(signInAccount.getId());
        reference2 = rootNode.getReference("dataExercise");

        listView = findViewById(R.id.listView);
        dateTitle = findViewById(R.id.dateTitle);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("history").child(date).exists()){
                    dateTitle.setText(snapshot.child("history").child(date).child("date").getValue(String.class));

                    if(snapshot.child("plan").child("gender").exists() && snapshot.child("plan").child("intensity").exists()) {
                        String gender    = snapshot.child("plan").child("gender").getValue(Integer.class) == 1 ? "male" : "female";
                        String intensity = snapshot.child("plan").child("intensity").getValue(Integer.class) == 0 ? "beginner"
                                         : snapshot.child("plan").child("intensity").getValue(Integer.class) == 1 ? "intermediate" : "advanced";

                        reference2.child(gender).child(intensity).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot item : snapshot.child("history").child(date).child("detail").getChildren()){
                                    System.out.println(item.getKey());
                                    if(dataSnapshot.child(item.getKey()).exists()){
                                        arr.add(new DataExercise(
                                                dataSnapshot.child(item.getKey()).child("taskName").getValue(String.class),
                                                snapshot.child("plan").child("gender").getValue(int.class),
                                                snapshot.child("plan").child("intensity").getValue(int.class),
                                                dataSnapshot.child(item.getKey()).child("reps").getValue(Integer.class),
                                                dataSnapshot.child(item.getKey()).child("sets").getValue(Integer.class),
                                                dataSnapshot.child(item.getKey()).child("time").getValue(Integer.class),
                                                getUri(dataSnapshot.child(item.getKey()).child("photo").getValue(String.class)),
                                                getUri(dataSnapshot.child(item.getKey()).child("photoGif").getValue(String.class))
                                        ));
                                    }
                                }
                                DetailHistoryAdapter detailHistoryAdapter = new DetailHistoryAdapter(getApplicationContext(),R.layout.item_fitness, arr);
                                listView.setAdapter(detailHistoryAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public String getUri(String fileName){
        return String.valueOf(getResources().getIdentifier(fileName,"drawable", getApplicationContext().getPackageName()));
    }

    public void finishActivity(View v){
        finish();
    }

}