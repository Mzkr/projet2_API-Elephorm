package com.example.alexis.projet2_elephorm.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.alexis.projet2_elephorm.R;
import com.example.alexis.projet2_elephorm.app.AppController;
import com.example.alexis.projet2_elephorm.model.Formation;

import java.util.List;

/**
 * Created by Alexis on 13/08/2015.
 */
public class CustomFormationAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Formation> formationsItems;
    private static final String TAG = CustomFormationAdapter.class.getSimpleName();

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomFormationAdapter(Activity activity, List<Formation> formationsItems) {
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

        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_formation, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView poster = (NetworkImageView) convertView.findViewById(R.id.poster);

        //initialisation des vues xml
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView subtitle = (TextView) convertView.findViewById(R.id.subtitle);
        TextView description = (TextView) convertView.findViewById(R.id.description);

        // récupération liste info de la ligne sélectionné
        Formation m = formationsItems.get(position);
        String url = m.getPoster();

        // title
        title.setText(m.getTitle());
        subtitle.setText(m.getSubtitle());
        // description
        description.setText(Html.fromHtml(m.getDescription()));

        //poster
        poster.setImageUrl(url, imageLoader);

        return convertView;
    }


}
