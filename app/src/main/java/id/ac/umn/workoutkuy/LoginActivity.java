package id.ac.umn.workoutkuy;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {
    private boolean showPassword = false;
    public EditText etPass;
    public ImageView eyeIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPass = findViewById(R.id.etPassword);
        eyeIcon = findViewById(R.id.show_pass_btn);
    }

    public void ShowHidePass(View view) {
        if(!showPassword){
            showPassword = !showPassword;
            ((ImageView) eyeIcon).setImageResource(R.drawable.visibility);
            etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            showPassword = !showPassword;
            eyeIcon.setImageResource(R.drawable.hidden);
            etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}