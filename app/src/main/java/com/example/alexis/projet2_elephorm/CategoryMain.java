package com.example.alexis.projet2_elephorm;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alexis.projet2_elephorm.adapter.CustomListAdapter;
import com.example.alexis.projet2_elephorm.model.Formation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexis on 11/08/2015.
 */
public class CategoryMain extends Activity {
    private SharedPreferences preferences;
    private CustomListAdapter adapter;
    private List<Formation> formationsListSubCat = new ArrayList<Formation>();
    private ListView listViewSubCat;
    private JSONArray newJArraySubCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String all_cat = preferences.getString("ALL_CAT", "");
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("idCat");

        try {
            JSONArray newJArrayAllCat = new JSONArray(all_cat);
            for (int i = 0; i < newJArrayAllCat.length(); i++) {
                try {

                    JSONObject obj = newJArrayAllCat.getJSONObject(i);
                    if(obj.getString("_id").equals(id)){

                        TextView title = (TextView) findViewById(R.id.titleCat);
                        TextView description = (TextView) findViewById(R.id.descriptCat);
                        listViewSubCat = (ListView) findViewById(R.id.subCat);
                        title.setText(obj.getString("title"));
                        description.setText(Html.fromHtml(obj.getString("description")));
                        newJArraySubCat = new JSONArray(obj.getString("subcategories"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            adapter = new CustomListAdapter(this, formationsListSubCat);
            listViewSubCat.setAdapter(adapter);
            listViewSubCat.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
                    CustomListAdapter ca = (CustomListAdapter) parent.getAdapter();
                    Formation woid = (Formation) ca.getItem(position);

                    Intent i = new Intent(CategoryMain.this, SubCategoryMain.class);
                    i.putExtra("idSubCat", woid.getId());
                    startActivity(i);
                }
            });

            for (int i = 0; i < newJArraySubCat.length(); i++) {

                JSONObject objSubCat = newJArraySubCat.getJSONObject(i);
                Formation formationSubCat = new Formation();
                formationSubCat.setId(objSubCat.getString("_id"));
                formationSubCat.setTitle(objSubCat.getString("title"));
                formationSubCat.setDescription(objSubCat.getString("description"));


                // adding subCat to subCat array
                formationsListSubCat.add(formationSubCat);

            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

       /* TextView tv = new TextView(this);
        tv.setText("Hello, "+jsonArray+" !!");
        setContentView(tv);*/


    }
}
