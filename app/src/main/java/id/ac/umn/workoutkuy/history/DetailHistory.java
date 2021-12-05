package id.ac.umn.workoutkuy.history;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import id.ac.umn.workoutkuy.R;

public class DetailHistory extends AppCompatActivity {
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
    }
}