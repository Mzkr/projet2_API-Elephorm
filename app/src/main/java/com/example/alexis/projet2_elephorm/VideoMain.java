package com.example.alexis.projet2_elephorm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by Alexis on 01/09/2015.
 */
public class VideoMain  extends NavigationDrawerSetup {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navView;

    private ProgressDialog pDialog;
    private MediaController media_Controller;
    private static final String STORAGE_DATA = "storageData";
    private SharedPreferences storageDataVideo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_main);
        setTitle(R.string.title_video_main);
        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Find our navigation view
        navView = (NavigationView) findViewById(R.id.nvView);

        configureDrawer(toolbar, mDrawer, VideoMain.this, navView);
        setupDrawerContent(navView);

        Bundle extras = getIntent().getExtras();
        final String idVideo = extras.getString("idVideo");
        String urlVideo = extras.getString("urlVideo");
        final String idSubCat = extras.getString("idSubCat");
        final String idCat = extras.getString("idCat");

        media_Controller = new MediaController(this);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Chargement...");
        pDialog.show();

        final VideoView teaser =(VideoView)findViewById(R.id.video_view);
        // video
        teaser.setMediaController(media_Controller);
        teaser.setVideoPath(urlVideo);
        teaser.start();
        teaser.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
               hidePDialog();
                teaser.setFocusable(true);
                teaser.start();
            }
        });

        // stockage des id de categories dans les sharedpref
        final SharedPreferences.Editor editor = getSharedPreferences(STORAGE_DATA, MODE_PRIVATE).edit();
        storageDataVideo = getSharedPreferences(STORAGE_DATA, MODE_PRIVATE);
        final String listVideoView = storageDataVideo.getString("ObjetVideoView", null);

        if (listVideoView != null && !listVideoView.equals("")) {
            teaser.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    try {
                        JSONObject objVideo = new JSONObject(listVideoView);
                        JSONArray thisCat =  objVideo.getJSONArray(idCat);
                        for (int i = 0; i < thisCat.length(); i++) {
                            JSONObject row = thisCat.getJSONObject(i);
                            for (Iterator iterator = row.keys(); iterator.hasNext();) {
                                Object cleSubCat = iterator.next();
                                Object val = row.get(String.valueOf(cleSubCat));
                                if (cleSubCat.equals(idSubCat)) {
                                    JSONArray jaIdVideo = new JSONArray();
                                    if(String.valueOf(val).equals("")){
                                        jaIdVideo.put(idVideo);
                                    }else{
                                        jaIdVideo = new JSONArray(String.valueOf(val));
                                        jaIdVideo.put(idVideo);
                                    }
                                    row.put(idSubCat, jaIdVideo);
                                    break;
                                }
                            }
                        }
                        objVideo.put(idCat, thisCat);
                        editor.putString("ObjetVideoView", String.valueOf(objVideo));
                        editor.commit();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
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
