package com.example.alexis.projet2_elephorm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by Damien on 13/08/15.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<ParseObject> creationsList;

    public ImageAdapter(Context c, List<ParseObject> creations) {
        mContext = c;
        creationsList = creations;
    }

    public int getCount() {
        return creationsList.size();
        //return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0,0,0,0);
        } else {
            imageView = (ImageView) convertView;
        }

        try {
            Picasso.with(mContext).load(creationsList.get(position).getString("url")).resize((int) mContext.getResources().getDimension(R.dimen.creation_thumb_width), (int) mContext.getResources().getDimension(R.dimen.creation_thumb_height)).centerCrop().into(imageView);
        }catch(Exception e){
            Log.d("•••••", "message : " + e.getMessage());
        }


        //imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
}
