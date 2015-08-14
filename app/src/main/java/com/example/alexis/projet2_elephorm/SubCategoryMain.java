package com.example.alexis.projet2_elephorm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.alexis.projet2_elephorm.adapter.CustomFormationAdapter;
import com.example.alexis.projet2_elephorm.adapter.CustomListAdapter;
import com.example.alexis.projet2_elephorm.app.AppController;
import com.example.alexis.projet2_elephorm.model.Formation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexis on 13/08/2015.
 */
public class SubCategoryMain extends Activity {

    private static final String TAG = SubCategoryMain.class.getSimpleName();
    private static final String url = "http://eas.elephorm.com/api/v1/subcategories/";

    private ListView listView;
    private List<Formation> formationsList = new ArrayList<Formation>();
    private CustomFormationAdapter adapter;

    private static String urlFinal = "";
    private ProgressDialog pDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_category_main);
        Bundle extras = getIntent().getExtras();
        final String id = extras.getString("idSubCat");
        urlFinal = url.concat(id).concat("/trainings");

        listView = (ListView) findViewById(R.id.formation);
        adapter = new CustomFormationAdapter(this, formationsList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        final TextView tv = new TextView(this);
        JsonArrayRequest formationReq = new JsonArrayRequest(urlFinal,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        //sharedPref = SET toute les category
                       /* SharedPreferences.Editor catEditor = preferences.edit();
                        catEditor.putString("ALL_CAT", response.toString());
                        catEditor.commit();*/

                        // Parsing json
                            try {

                                JSONObject obj = response.getJSONObject(0);
                                Formation formation = new Formation();
                                formation.setId(obj.getString("_id"));
                                formation.setTitle(obj.getString("title"));
                                formation.setDescription(obj.getString("description"));
                                formation.setSubtitle(obj.getString("subtitle"));
                                formation.setProduct_url(obj.getString("product_url"));
                                formation.setEan13(obj.getString("ean13"));
                                formation.setPrice(obj.getInt("price"));
                                formation.setDuration(obj.getInt("duration"));
                                formation.setObjectives(obj.getString("objectives"));
                                formation.setQcm(obj.getString("qcm"));
                                formation.setTeaser_text(obj.getString("teaser_text"));
                                formation.setTeaser(obj.getString("teaser"));
                                formation.setPublishedDate(obj.getString("publishedDate"));

                                String image = obj.getString("images");
                                JSONObject objImage = new JSONObject(image);
                                String landscapes = objImage.getString("landscapes");
                                JSONObject imageMini = new JSONObject(landscapes);
                                String imageFinale = imageMini.getString("medium");
                                formation.setPoster(imageFinale);

                                // adding formation to formation array
                                formationsList.add(formation);

                               // tv.setText("Hello, " + obj.getString("poster") + " !!");
                               // setContentView(tv);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                        hidePDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });
        AppController.getInstance().addToRequestQueue(formationReq);

    }
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
