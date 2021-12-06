package id.ac.umn.workoutkuy.history;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import id.ac.umn.workoutkuy.DataExercise;
import id.ac.umn.workoutkuy.R;

public class DetailHistoryAdapter extends ArrayAdapter<DataExercise> {
    private Context mContext;
    private int mResource;

    public DetailHistoryAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DataExercise> objects) {
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
        ImageView taskPhoto = (ImageView) convertView.findViewById(R.id.taskPhoto);

        taskPhoto.setImageResource(Integer.parseInt(getItem(position).getPhoto()));
        taskName.setText(getItem(position).getTaskName());


        if(getItem(position).getReps() == 0){
            taskDetail.setText(getItem(position).getTime() + " seconds x " + getItem(position).getSets() + " sets");
        }else{
            taskDetail.setText(getItem(position).getReps() + " reps x " + getItem(position).getSets() + " sets");
        }
        return convertView;
    }
}
