package id.ac.umn.workoutkuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class FitnessDetail extends AppCompatActivity {
    public int num;
    ArrayList<DataExercise> data = new ArrayList<>();
    TextView taskNum, taskName, taskDetail, taskTime;
    Button taskNext, taskBack, taskStart;
    GifImageView taskGIF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("extra");
        num = Integer.parseInt(bundle.getSerializable("num").toString());
        data = (ArrayList<DataExercise>) bundle.getSerializable("data");

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

    public void setContent(){
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
            }
        }.start();

    }
}