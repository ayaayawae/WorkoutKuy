package id.ac.umn.workoutkuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

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
}