package id.ac.umn.workoutkuy.history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.ac.umn.workoutkuy.DataExercise;
import id.ac.umn.workoutkuy.FitnessAdapter;
import id.ac.umn.workoutkuy.R;

public class HistoryFitness extends AppCompatActivity {

    ListView listView;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    ArrayList<DataHistory> arrHistory = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_fitness);

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        listView = findViewById(R.id.listView);

        setData(signInAccount.getId());
    }

    public void setData(String id){
        rootNode = FirebaseDatabase.getInstance("https://workoutkuy-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = rootNode.getReference("users").child(id);

        reference.child("history").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot item : snapshot.getChildren()){
                        arrHistory.add(new DataHistory(item.getKey(), item.child("date").getValue(String.class)));
                    }
                }
                HistoryAdapter historyAdapter = new HistoryAdapter(getApplicationContext(),R.layout.item_history, arrHistory);
                listView.setAdapter(historyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}