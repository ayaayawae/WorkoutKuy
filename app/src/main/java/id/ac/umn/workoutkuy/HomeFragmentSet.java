package id.ac.umn.workoutkuy;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

public class HomeFragmentSet extends Fragment {
    private Button setPlanBtn;
    private ImageView planGenderImg;
    private CardView intensityBtn;
    private TextView intensity;
    public int intensityLvl, genderPlan = 0;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

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

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());

        rootNode = FirebaseDatabase.getInstance("https://workoutkuy-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = rootNode.getReference("users").child(signInAccount.getId());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("plan").child("gender").exists() && snapshot.child("plan").child("intensity").exists()){
                    if(snapshot.child("plan").child("gender").getValue(Integer.class) == 1) {
                        planGenderImg.setImageResource(R.drawable.image4);
                    } else {
                        planGenderImg.setImageResource(R.drawable.image5);
                    }
                    intensityLvl = snapshot.child("plan").child("intensity").getValue(Integer.class);
                    if(intensityLvl == 0) {
                        intensity.setText("Beginner");
                    } else if(intensityLvl == 1) {
                        intensity.setText("Intermediate");
                    } else if(intensityLvl == 2) {
                        intensity.setText("Advanced");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}