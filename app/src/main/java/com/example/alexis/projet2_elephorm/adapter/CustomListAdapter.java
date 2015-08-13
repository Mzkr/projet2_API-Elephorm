package com.example.alexis.projet2_elephorm.adapter;

/**
 * Created by Alexis on 16/06/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alexis.projet2_elephorm.model.Category;
import com.example.alexis.projet2_elephorm.R;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Category> formationsItems;

    public CustomListAdapter(Activity activity, List<Category> formationsItems) {
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
            convertView = inflater.inflate(R.layout.list_row, null);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView description = (TextView) convertView.findViewById(R.id.description);


        // getting movie data for the row
        Category m = formationsItems.get(position);

        // title
        title.setText(m.getTitle());
        // description
        description.setText(Html.fromHtml(m.getDescription()));

        return convertView;
    }

}