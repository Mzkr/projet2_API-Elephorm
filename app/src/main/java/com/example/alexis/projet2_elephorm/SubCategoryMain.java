package com.example.alexis.projet2_elephorm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.alexis.projet2_elephorm.app.AppController;

import org.json.JSONArray;

/**
 * Created by Alexis on 13/08/2015.
 */
public class SubCategoryMain extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String url = "http://eas.elephorm.com/api/v1/subcategories/";

    private static String urlFinal = "";
    private ProgressDialog pDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_category_main);
        Bundle extras = getIntent().getExtras();
        final String id = extras.getString("idSubCat");
        urlFinal = url.concat(id).concat("/trainings");

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
                        hidePDialog();
                        //sharedPref = SET toute les category
                       /* SharedPreferences.Editor catEditor = preferences.edit();
                        catEditor.putString("ALL_CAT", response.toString());
                        catEditor.commit();*/


                        tv.setText("Hello, "+response.toString()+" !!");
                        setContentView(tv);


                        // Parsing json
                       /* for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Formation formation = new Formation();
                                formation.setId(obj.getString("_id"));
                                formation.setTitle(obj.getString("title"));
                                formation.setDescription(obj.getString("description"));


                                // adding movie to movies array
                                //formationsList.add(formation);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }*/

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        //adapter.notifyDataSetChanged();
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
