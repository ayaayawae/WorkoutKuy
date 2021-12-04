package id.ac.umn.workoutkuy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    DatabaseReference reference2;


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
        reference2 = rootNode.getReference("dataExercise");

        reference.child("plan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    reference2
                            .child(snapshot.child("gender").getValue(Integer.class) == 1 ? "male" : "female")
                            .child(snapshot.child("intensity").getValue(Integer.class) == 0 ? "beginner"
                                    : snapshot.child("intensity").getValue(Integer.class) == 1 ? "intermediate"
                                    : "advanced")
                            .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                for (DataSnapshot item : dataSnapshot.getChildren()){
                                    System.out.println(item.getKey());
                                    arr.add(new DataExercise(
                                            item.child("taskName").getValue(String.class),
                                            snapshot.child("gender").getValue(int.class),
                                            snapshot.child("intensity").getValue(int.class),
                                            item.child("reps").getValue(Integer.class),
                                            item.child("sets").getValue(Integer.class),
                                            item.child("time").getValue(Integer.class),
                                            item.child("photo").getValue(String.class),
                                            item.child("photoGif").getValue(String.class)));
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

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
//                        Toast.makeText(getContext(),position,Toast.LENGTH_LONG);
                        Intent detailFitness = new Intent(getActivity(), FitnessDetail.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", arr);
                        bundle.putSerializable("num", String.valueOf(position));
                        detailFitness.putExtra("extra", bundle);
                        startActivity(detailFitness);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}