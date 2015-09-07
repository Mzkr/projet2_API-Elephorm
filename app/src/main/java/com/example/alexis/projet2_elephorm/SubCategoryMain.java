package com.example.alexis.projet2_elephorm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
public class SubCategoryMain extends NavigationDrawerSetup {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navView;

    private static final String TAG = SubCategoryMain.class.getSimpleName();
    private static final String url = "http://eas.elephorm.com/api/v1/subcategories/";

    private ListView listView;
    private ListView listViewVideo;
    private List<Formation> formationsList = new ArrayList<Formation>();
    private List<Video> formationsListVideo = new ArrayList<Video>();
    private CustomFormationAdapter adapter;
    private CustomVideoAdapter videoAdapter;
    private static final String STORAGE_DATA = "storageData";
    private SharedPreferences storageDataVideo;

    private String idCat = null;
    private String idSubCat = null;

    private static String urlFinal = "";
    private ProgressDialog pDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_category_main);
        setTitle(R.string.title_SubCategory_main);
        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Find our navigation view
        navView = (NavigationView) findViewById(R.id.nvView);

        configureDrawer(toolbar, mDrawer, SubCategoryMain.this, navView);
        setupDrawerContent(navView);

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

                // stockage des id de categories dans les sharedpref
                SharedPreferences.Editor editor = getSharedPreferences(STORAGE_DATA, MODE_PRIVATE).edit();
                storageDataVideo = getSharedPreferences(STORAGE_DATA, MODE_PRIVATE);
                String listVideoView = storageDataVideo.getString("ObjetVideoView", null);
                if (listVideoView != null && !listVideoView.equals("")) {
                    JSONObject objVideo = null;
                    try {
                        objVideo = new JSONObject(listVideoView);
                        Boolean catExist = false;
                        Boolean subCatExist = false;
                        for (Iterator iterator = objVideo.keys(); iterator.hasNext();) {
                            Object cle = iterator.next();
                            Object val = objVideo.get(String.valueOf(cle));
                            if(cle.equals(idCat)){
                                catExist = true;
                                JSONArray objSubCat = new JSONArray(String.valueOf(val));
                                for (int i = 0; i < objSubCat.length(); i++) {
                                    JSONObject row = objSubCat.getJSONObject(i);
                                    for (Iterator iterator2 = row.keys(); iterator2.hasNext();) {
                                        Object cleSubCat = iterator2.next();
                                        if (cleSubCat.equals(idSubCat)) {
                                            subCatExist = true;
                                        }
                                    }
                                }
                            }
                        }

                        if(catExist && !subCatExist){
                            JSONArray thisCatList =  objVideo.getJSONArray(idCat);
                            JSONObject thisCatObj = new JSONObject();
                            thisCatObj.put(idSubCat, "");
                            thisCatList.put(thisCatObj);
                            objVideo.put(idCat,thisCatList);
                            editor.putString("ObjetVideoView", String.valueOf(objVideo));
                            editor.commit();

                        }else if(!catExist){
                            JSONObject sbCat = new JSONObject();
                            sbCat.put(idSubCat, "");
                            JSONArray jaVideo = new JSONArray();
                            jaVideo.put(sbCat);
                            //création objet final pour sharedPref
                            objVideo.put(idCat,jaVideo);
                            editor.putString("ObjetVideoView", String.valueOf(objVideo));
                            editor.commit();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    //création array des sous-cat pour id vidéo
                    JSONObject sbCat = new JSONObject();
                    try {
                        sbCat.put(idSubCat, "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray jaVideo = new JSONArray();
                    jaVideo.put(sbCat);
                    //création objet final pour sharedPref
                    JSONObject objVideo = new JSONObject();
                    try {
                        objVideo.put(idCat,jaVideo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    editor.putString("ObjetVideoView", String.valueOf(objVideo));
                    editor.commit();
                }

                if(woid.getField_video() != "") {
                    Intent i = new Intent(SubCategoryMain.this, VideoMain.class);
                    i.putExtra("urlVideo", woid.getField_video());
                    i.putExtra("idVideo", woid.getId());
                    i.putExtra("idCat", idCat);
                    i.putExtra("idSubCat", idSubCat);
                    startActivity(i);
                }
            }
        });

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Chargement...");
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
                                idCat = obj.getString("category");
                                idSubCat = obj.getString("subcategory");
                                formation.setTitle(obj.getString("title"));
                                formation.setSubtitle(obj.getString("subtitle"));
                                formation.setDescription(obj.getString("description"));
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
                                    video.setId(item.getString("_id"));
                                    video.setTitle(item.getString("title"));
                                    video.setType(item.getString("type"));
                                    //url de vidéo
                                    if(item.getString("field_video") != "[]" && item.getString("field_video") != null) {
                                        JSONArray jrVideo = item.getJSONArray("field_video");
                                        Boolean pathExist = false;
                                        for (int j = 0; j < jrVideo.length(); j++){
                                            JSONObject objVideo = jrVideo.getJSONObject(j);
                                            video.setField_video(objVideo.getString("filepath"));
                                            if(objVideo.getString("filepath") != null && objVideo.getString("filepath") != ""){
                                                pathExist = true;
                                            }
                                        }
                                        if(pathExist){
                                            formationsListVideo.add(video);
                                        }
                                    }
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
