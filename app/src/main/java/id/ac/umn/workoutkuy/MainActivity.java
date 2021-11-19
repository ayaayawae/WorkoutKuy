package id.ac.umn.workoutkuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        FrameLayout nav_host = findViewById(R.id.fragment_container);

        getSupportFragmentManager().beginTransaction()
                .replace(nav_host.getId(), new HomeFragment()).commit();

        Log.d("MainActivity","tesLogin berhasil");

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomeFragment(); break;
                case R.id.nav_fitness:
                    selectedFragment = new FitnessFragment(); break;
                case R.id.nav_profile:
                    selectedFragment = new ProfileFragment(); break;
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(nav_host.getId(), selectedFragment).commit();

            return true;
        });
    }

    public void setPlan(View view) {
//        Log.d("MainActivity", "SiNI");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }
}