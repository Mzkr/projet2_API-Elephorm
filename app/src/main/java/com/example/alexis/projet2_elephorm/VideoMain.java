package com.example.alexis.projet2_elephorm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by Alexis on 01/09/2015.
 */
public class VideoMain  extends Activity {
    private ProgressDialog pDialog;
    private MediaController media_Controller;
    private static final String STORAGE_DATA = "storageData";
    private SharedPreferences storageDataVideo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_main);
        Bundle extras = getIntent().getExtras();
        String urlVideo = extras.getString("urlVideo");
        media_Controller = new MediaController(this);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        VideoView teaser =(VideoView)findViewById(R.id.video_view);
        // video
        teaser.setMediaController(media_Controller);
        teaser.setVideoPath(urlVideo);
        teaser.start();
        hidePDialog();

        // stockage des id de categories dans les sharedpref
        SharedPreferences.Editor editor = getSharedPreferences(STORAGE_DATA, MODE_PRIVATE).edit();
        storageDataVideo = getSharedPreferences(STORAGE_DATA, MODE_PRIVATE);
        String listVideoView = storageDataVideo.getString("ObjetVideoView", null);
        Log.i("???§§§§§",listVideoView);
        if (!listVideoView.equals("") ) {

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
