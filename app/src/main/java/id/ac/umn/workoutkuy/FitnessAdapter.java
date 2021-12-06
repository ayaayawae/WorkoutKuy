package id.ac.umn.workoutkuy;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FitnessAdapter extends ArrayAdapter<DataExercise> {
    private Context mContext;
    private int mResource;

    public FitnessAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DataExercise> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource, parent,false );
        TextView taskName = convertView.findViewById(R.id.taskName);
        TextView taskDetail = convertView.findViewById(R.id.taskDetail);
        ImageView taskPhoto = (ImageView) convertView.findViewById(R.id.taskPhoto);

        CardView containerItemFitness = convertView.findViewById(R.id.containerItemFitness);

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());

        rootNode = FirebaseDatabase.getInstance("https://workoutkuy-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = rootNode.getReference("users").child(signInAccount.getId()).child("progress");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(String.valueOf(position+1)).exists()){
                    containerItemFitness.setCardBackgroundColor(Color.parseColor("#5b6e29"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        taskPhoto.setImageResource(Integer.parseInt(getItem(position).getPhoto()));
        taskName.setText(getItem(position).getTaskName());


        if(getItem(position).getReps() == 0){
            taskDetail.setText(getItem(position).getTime() + " seconds x " + getItem(position).sets + " sets");
        }else{
            taskDetail.setText(getItem(position).getReps() + " reps x " + getItem(position).sets + " sets");
        }
        return convertView;
    }
}
