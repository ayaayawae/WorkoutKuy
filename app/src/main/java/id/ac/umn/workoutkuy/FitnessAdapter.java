package id.ac.umn.workoutkuy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FitnessAdapter extends ArrayAdapter<DataExercise> {
    private Context mContext;
    private int mResource;
    public FitnessAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DataExercise> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource, parent,false );
        TextView taskName = convertView.findViewById(R.id.taskName);
        TextView taskDetail = convertView.findViewById(R.id.taskDetail);

        taskName.setText(getItem(position).getTaskName());
        if(getItem(position).getReps() == 0){
            taskDetail.setText(getItem(position).getTime() + " seconds x " + getItem(position).sets + " sets");
        }else{
            taskDetail.setText(getItem(position).getReps() + " reps x " + getItem(position).sets + " sets");
        }
        return convertView;
    }
}
