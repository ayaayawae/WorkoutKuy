package id.ac.umn.workoutkuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    public boolean checkPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        FrameLayout nav_host = findViewById(R.id.fragment_container);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if(user == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else {
            rootNode = FirebaseDatabase.getInstance("https://workoutkuy-default-rtdb.asia-southeast1.firebasedatabase.app/");
            reference = rootNode.getReference("users").child(signInAccount.getId());

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!snapshot.exists()){
                        reference.child("email").setValue(signInAccount.getEmail());
                        reference.child("name").setValue(signInAccount.getDisplayName());
                        reference.child("url_picture").setValue(signInAccount.getPhotoUrl().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            setCheckPlan(signInAccount.getId(),nav_host.getId());
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if(user == null) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            } else {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = checkPlan ? new HomeFragmentSet() : new HomeFragment() ;break;
                    case R.id.nav_fitness:
                        selectedFragment = checkPlan ? new FitnessFragment() : new FitnessFragmentNotSet(); break;
                    case R.id.nav_profile:
                        selectedFragment = new ProfileFragment(); break;
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(nav_host.getId(), selectedFragment).commit();
            }
            return true;
        });
    }

//    @Override
    protected void setCheckPlan(String id, int navHostId) {
        rootNode = FirebaseDatabase.getInstance("https://workoutkuy-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = rootNode.getReference("users").child(id);
        reference.child("plan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                checkPlan = !snapshot.exists() ?  false : true;
                getSupportFragmentManager().beginTransaction()
                    .replace(navHostId, snapshot.exists() ? new HomeFragmentSet() : new HomeFragment()).commitAllowingStateLoss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}