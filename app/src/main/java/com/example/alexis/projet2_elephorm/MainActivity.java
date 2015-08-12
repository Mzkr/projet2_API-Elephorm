package com.example.alexis.projet2_elephorm;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.alexis.projet2_elephorm.adapter.CustomListAdapter;
import com.example.alexis.projet2_elephorm.app.AppController;
import com.example.alexis.projet2_elephorm.model.Formation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends NavigationDrawerSetup {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navView;


    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String url = "http://eas.elephorm.com/api/v1/categories";
    private static final String ALL_CAT = "AllCategory";
    private TextView mTextView;
    private ProgressDialog pDialog;
    private List<Formation> formationsList = new ArrayList<Formation>();
    private ListView listView;
    private CustomListAdapter adapter;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title_activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Find our navigation view
        navView = (NavigationView) findViewById(R.id.nvView);

        configureDrawer(toolbar, mDrawer, MainActivity.this, navView);
        setupDrawerContent(navView);
        //sharedPref = GET toute les category
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String all_cat = preferences.getString("ALL_CAT", "");
        if (!all_cat.equals("") ) {

        }
        else {

        }

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, formationsList);
        listView.setAdapter(adapter);
        // Click event for single list row
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
                CustomListAdapter ca = (CustomListAdapter)parent.getAdapter();
                Formation woid = (Formation)ca.getItem(position);

                Intent i = new Intent(MainActivity.this, CategoryMain.class);
                i.putExtra("idCat", woid.getId());
                startActivity(i);
            }
        });

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // changing action bar color
        // getActionBar().setBackgroundDrawable(
        //       new ColorDrawable(Color.parseColor("#1b1b1b")));

        // Creating volley request obj
        JsonArrayRequest formationReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        //sharedPref = SET toute les category
                        SharedPreferences.Editor catEditor = preferences.edit();
                        catEditor.putString("ALL_CAT", response.toString());
                        catEditor.commit();
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Formation formation = new Formation();
                                formation.setId(obj.getString("_id"));
                                formation.setTitle(obj.getString("title"));
                                formation.setDescription(obj.getString("description"));


                                // adding movie to movies array
                                formationsList.add(formation);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(formationReq);
    }

    @Override
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



//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // The action bar home/up action should open or close the drawer.
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                mDrawer.openDrawer(GravityCompat.START);
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
