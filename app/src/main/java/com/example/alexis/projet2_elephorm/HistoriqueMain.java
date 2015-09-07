package com.example.alexis.projet2_elephorm;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.example.alexis.projet2_elephorm.adapter.CustomHistoriqueAdapter;
import com.example.alexis.projet2_elephorm.model.Historique;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Alexis on 07/09/2015.
 */
public class HistoriqueMain extends NavigationDrawerSetup {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navView;

    private ProgressDialog pDialog;
    private static final String STORAGE_DATA = "storageData";
    private SharedPreferences storageDataVideo;
    private SharedPreferences all_cat_pref;
    private List<Historique> historiqueListCat = new ArrayList<Historique>();

    private ListView listViewCat;
    private CustomHistoriqueAdapter adapter;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historique_main);
        setTitle(R.string.title_historique_main);
        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Find our navigation view
        navView = (NavigationView) findViewById(R.id.nvView);

        configureDrawer(toolbar, mDrawer, HistoriqueMain.this, navView);
        setupDrawerContent(navView);

        all_cat_pref = PreferenceManager.getDefaultSharedPreferences(this);
        String all_cat = all_cat_pref.getString("ALL_CAT", "");
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Chargement...");
        pDialog.show();

        listViewCat = (ListView) findViewById(R.id.list_historique_main);
        adapter = new CustomHistoriqueAdapter(this, historiqueListCat);
        listViewCat.setAdapter(adapter);

        storageDataVideo = getSharedPreferences(STORAGE_DATA, MODE_PRIVATE);
        final String listVideoView = storageDataVideo.getString("ObjetVideoView", null);
        if (listVideoView != null && !listVideoView.equals("")) {
            try {
                JSONArray newJArrayAllCat = new JSONArray(all_cat);
                JSONObject objetHistorique = new JSONObject(listVideoView);
                for (Iterator iterator = objetHistorique.keys(); iterator.hasNext();) {
                    //id de la catégorie vue
                    Object catId = iterator.next();
                    //id des sousCat et Video
                    Object catVal = objetHistorique.get(String.valueOf(catId));
                    String listSubCat = "";
                    //récupération des id de subCat
                    JSONArray jaSubCatVue = new JSONArray(String.valueOf(catVal));
                    //end id subCat
                    for (int i = 0; i < newJArrayAllCat.length(); i++) {
                        //toutes les catégories
                        JSONObject item = newJArrayAllCat.getJSONObject(i);
                        if(item.getString("_id").equals(catId)){
                            Historique titleCat = new Historique();
                            //ajout du titre de la catégorie
                            titleCat.setTitle(item.getString("title"));
                            //objet contenant les subCat de la Cat
                            JSONArray newJArrayAllSubCat = new JSONArray(item.getString("subcategories"));
                            for (int j = 0; j < jaSubCatVue.length(); j++) {
                                JSONObject itemSubCatVue = jaSubCatVue.getJSONObject(j);
                                for (Iterator iterator2 = itemSubCatVue.keys(); iterator2.hasNext();) {
                                    //id de subCat
                                    Object subCatIdVue = iterator2.next();
                                    for (int k = 0; k < newJArrayAllSubCat.length(); k++) {
                                        //toutes les souscatégories
                                        JSONObject itemSubCatAll = newJArrayAllSubCat.getJSONObject(k);
                                        if(itemSubCatAll.getString("_id").equals(subCatIdVue)){
                                            Log.i("trolololololol",listSubCat);
                                            if(listSubCat.equals("")){
                                                listSubCat = itemSubCatAll.getString("title");
                                            }else{
                                                listSubCat = listSubCat + ", " + itemSubCatAll.getString("title");
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                            titleCat.setListSubCat(listSubCat);
                            historiqueListCat.add(titleCat);
                        }
                    }
                }

                adapter.notifyDataSetChanged();
                hidePDialog();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
