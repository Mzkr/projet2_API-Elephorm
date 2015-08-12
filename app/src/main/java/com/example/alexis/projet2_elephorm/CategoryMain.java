package com.example.alexis.projet2_elephorm;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alexis on 11/08/2015.
 */
public class CategoryMain extends Activity {
    private ProgressDialog pDialog;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String all_cat = preferences.getString("ALL_CAT", "");
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("idCat");

        try {
            JSONArray newJArray = new JSONArray(all_cat);
            for (int i = 0; i < newJArray.length(); i++) {
                try {

                    JSONObject obj = newJArray.getJSONObject(i);
                    if(obj.getString("_id").equals(id)){

                        TextView title = (TextView) findViewById(R.id.titleCat);
                        TextView description = (TextView) findViewById(R.id.descriptCat);
                        title.setText(obj.getString("title"));
                        description.setText(Html.fromHtml(obj.getString("description")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

       /* TextView tv = new TextView(this);
        tv.setText("Hello, "+jsonArray+" !!");
        setContentView(tv);*/


    }
}
