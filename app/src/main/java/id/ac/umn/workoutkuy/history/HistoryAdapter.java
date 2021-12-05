package id.ac.umn.workoutkuy.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import id.ac.umn.workoutkuy.R;

public class HistoryAdapter extends ArrayAdapter<DataHistory> {
    private Context mContext;
    private int mResource;

    public HistoryAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DataHistory> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent,false );

        TextView dateList = convertView.findViewById(R.id.dateList);

        dateList.setText(getItem(position).getDate2());
        return convertView;
    }
}