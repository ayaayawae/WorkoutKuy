package id.ac.umn.workoutkuy;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    private Button setPlanBtn;
    private ImageView male, female;
    private CardView intensityBtn;
    private TextView intensity;
    private int intensityLvl, genderPlan;

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

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male.setForeground(null);
                female.setForeground(new ColorDrawable(getContext().getResources().getColor(R.color.transparentBlack)));
                genderPlan = 0; //male
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                female.setForeground(null);
                male.setForeground(new ColorDrawable(getContext().getResources().getColor(R.color.transparentBlack)));
                genderPlan = 1; //female
            }
        });

        intensityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(intensity.getText().equals("Beginner")) {
                    intensity.setText("Intermediate");
                    intensityLvl = 0;
                } else if(intensity.getText().equals("Intermediate")) {
                    intensity.setText("Advanced");
                    intensityLvl = 1;
                } else {
                    intensity.setText("Beginner");
                    intensityLvl = 2;
                }
            }
        });
    }
}