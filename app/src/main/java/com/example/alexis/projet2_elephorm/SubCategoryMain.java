package com.example.alexis.projet2_elephorm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.alexis.projet2_elephorm.adapter.CustomFormationAdapter;
import com.example.alexis.projet2_elephorm.adapter.CustomVideoAdapter;
import com.example.alexis.projet2_elephorm.app.AppController;
import com.example.alexis.projet2_elephorm.model.Formation;
import com.example.alexis.projet2_elephorm.model.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Alexis on 13/08/2015.
 */
public class SubCategoryMain extends Activity {

    private static final String TAG = SubCategoryMain.class.getSimpleName();
    private static final String url = "http://eas.elephorm.com/api/v1/subcategories/";

    private ListView listView;
    private ListView listViewVideo;
    private List<Formation> formationsList = new ArrayList<Formation>();
    private List<Video> formationsListVideo = new ArrayList<Video>();
    private CustomFormationAdapter adapter;
    private CustomVideoAdapter videoAdapter;

    private static String urlFinal = "";
    private ProgressDialog pDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_category_main);

        Bundle extras = getIntent().getExtras();
        final String id = extras.getString("idSubCat");
        urlFinal = url.concat(id).concat("/trainings");

        listView = (ListView) findViewById(R.id.formation);
        listViewVideo = (ListView) findViewById(R.id.videoList);
        adapter = new CustomFormationAdapter(this, formationsList);
        videoAdapter = new CustomVideoAdapter(this, formationsListVideo);
        listView.setAdapter(adapter);
        listViewVideo.setAdapter(videoAdapter);

        listViewVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
                CustomVideoAdapter cva = (CustomVideoAdapter) parent.getAdapter();
                Video woid = (Video) cva.getItem(position);

                if(woid.getField_video() != "") {
                    Intent i = new Intent(SubCategoryMain.this, VideoMain.class);
                    i.putExtra("urlVideo", woid.getField_video());
                    startActivity(i);
                }
            }
        });

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest formationReq = new JsonArrayRequest(urlFinal,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        try {

                            JSONObject obj = response.getJSONObject(0);
                            Formation formation = new Formation();
                                //formation.setId(obj.getString("_id"));
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
                                formation.setItems(obj.getString("items"));
                                // gestion des liens vidéo
                                JSONArray jrItems = obj.getJSONArray("items");
                                for(int i=0;i<jrItems.length();i++) {
                                    Video video = new Video();
                                    JSONObject item = jrItems.getJSONObject(i);
                                    video.setTitle(item.getString("title"));
                                    video.setType(item.getString("type"));
                                    //url de vidéo
                                    if(item.getString("field_video") != "[]" && item.getString("field_video") != null) {
                                        JSONArray jrVideo = item.getJSONArray("field_video");

                                        for (int j = 0; j < jrVideo.length(); j++){
                                            JSONObject objVideo = jrVideo.getJSONObject(j);
                                            video.setField_video(objVideo.getString("filepath"));
                                        }
                                    }else{
                                        video.setField_video("");
                                    }
                                    formationsListVideo.add(video);
                                }

                                //récupération de l'image taille moyenne
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
                        videoAdapter.notifyDataSetChanged();

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
