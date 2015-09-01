package com.example.alexis.projet2_elephorm.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alexis.projet2_elephorm.R;
import com.example.alexis.projet2_elephorm.model.Video;

import java.util.List;

/**
 * Created by Alexis on 27/08/2015.
 */
public class CustomVideoAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Video> formationsItems;

    public CustomVideoAdapter(Activity activity, List<Video> formationsItems) {
        this.activity = activity;
        this.formationsItems = formationsItems;
    }

    @Override
    public int getCount() {
        return formationsItems.size();
    }

    @Override
    public Object getItem(int location) {
        return formationsItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_video, null);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView url = (TextView) convertView.findViewById(R.id.url);

        Video m = formationsItems.get(position);

        title.setText(m.getTitle());
        url.setText(m.getField_video());

        return convertView;
    }

}