package id.ac.umn.workoutkuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class FitnessDetail extends AppCompatActivity {
    public int num;
    ArrayList<DataExercise> data = new ArrayList<>();
    TextView taskNum, taskName, taskDetail, taskTime;
    Button taskNext, taskBack, taskStart;
    GifImageView taskGIF;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("extra");
        num = Integer.parseInt(bundle.getSerializable("num").toString());
        data = (ArrayList<DataExercise>) bundle.getSerializable("data");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        rootNode = FirebaseDatabase.getInstance("https://workoutkuy-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = rootNode.getReference("users").child(signInAccount.getId());

        taskNum = findViewById(R.id.taskNum);
        taskName = findViewById(R.id.taskName);
        taskDetail = findViewById(R.id.taskDetail);
        taskTime = findViewById(R.id.taskTime);
        taskBack = findViewById(R.id.taskBack);
        taskNext = findViewById(R.id.taskNext);
        taskStart = findViewById(R.id.taskStart);
        taskGIF = findViewById(R.id.taskGIF);
        setContent();

        taskBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num != 0){
                    num = num - 1;
                    setContent();
                }
            }
        });

        taskNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num != data.size()-1){
                    num = num + 1;
                    setContent();
                }
            }
        });
    }

    public void pushHistory(){
        Date date = new Date();
        SimpleDateFormat DMY = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat MDY = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        String dateDMY = DMY.format(date); // Buat nampilin aja
        String dateMDY = MDY.format(date); // Buat ngurutin
        reference.child("history")
                .child(dateMDY)
                .child(String.valueOf(data.get(num).getGender()))
                .child(String.valueOf(data.get(num).getIntensity()))
                .child(String.valueOf(num+1))
                .setValue(data.get(num).getTaskName());
        reference.child("history").child(dateMDY).child("date").setValue(dateDMY);
    }

    public void setContent(){
        pushHistory();

        taskNum.setText("Step " + (num+1));
        taskName.setText(data.get(num).getTaskName());
        taskGIF.setImageResource(Integer.parseInt(data.get(num).getPhotoGif()));
        if(data.get(num).getTime() == 0){
            taskDetail.setText(data.get(num).getReps() + " reps x " + data.get(num).getSets() + " sets");
            taskTime.setText("");
            taskStart.setEnabled(false);
            taskStart.setVisibility(View.GONE);
        }else{
            taskDetail.setText(data.get(num).getSets() + " sets");
            taskTime.setText(data.get(num).getTime()+" seconds");
            taskStart.setEnabled(true);
            taskStart.setVisibility(View.VISIBLE);
        }
    }

    public void startTimer(View view){
        new CountDownTimer(data.get(num).getTime()*1000, 1000) {
            public void onTick(long millisUntilFinished) {
                taskStart.setEnabled(false);
                taskBack.setEnabled(false);
                taskNext.setEnabled(false);
                taskTime.setText(millisUntilFinished / 1000 + " seconds");
            }

            public void onFinish() {
                taskStart.setEnabled(true);
                taskBack.setEnabled(true);
                taskNext.setEnabled(true);
                taskTime.setText("done!");
                pause(1);
                taskTime.setText(data.get(num).getTime()+" seconds");
            }
        }.start();

    }

    public static void pause(double seconds)
    {
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {}
    }
}