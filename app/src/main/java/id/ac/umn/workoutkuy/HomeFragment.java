package id.ac.umn.workoutkuy;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import androidx.core.content.ContextCompat;
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

public class HomeFragment extends Fragment {
    private Button setPlanBtn;
    private ImageView male, female;
    private CardView intensityBtn;
    private TextView intensity;
    private int intensityLvl, genderPlan = 0;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        male = (ImageView) view.findViewById(R.id.maleImg);
        female = (ImageView) view.findViewById(R.id.femaleImg);
        intensityBtn = view.findViewById(R.id.intensityBtn);
        intensity = view.findViewById(R.id.intensity);
        setPlanBtn = view.findViewById(R.id.setPlan_button);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male.setForeground(null);
                female.setForeground(new ColorDrawable(getContext().getResources().getColor(R.color.transparentBlack)));
                genderPlan = 1; //male
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                female.setForeground(null);
                male.setForeground(new ColorDrawable(getContext().getResources().getColor(R.color.transparentBlack)));
                genderPlan = 2; //female
            }
        });

        intensityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(intensity.getText().equals("Beginner")) {
                    intensity.setText("Intermediate");
                    intensityLvl = 1;
                } else if(intensity.getText().equals("Intermediate")) {
                    intensity.setText("Advanced");
                    intensityLvl = 2;
                } else {
                    intensity.setText("Beginner");
                    intensityLvl = 0;
                }
            }
        });

        setPlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(genderPlan == 0){
                    Toast.makeText(getContext(),"Harap pilih gender!",Toast.LENGTH_LONG).show();

                }else{
                    rootNode = FirebaseDatabase.getInstance("https://workoutkuy-default-rtdb.asia-southeast1.firebasedatabase.app/");
                    reference = rootNode.getReference("users").child(signInAccount.getId());

                    reference.child("plan").child("gender").setValue(genderPlan);
                    reference.child("plan").child("intensity").setValue(intensityLvl);
                    Toast.makeText(getContext(),"Plan berhasil di-set, semangat!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static void pause(double seconds)
    {
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {}
    }
}