package com.example.alexis.projet2_elephorm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddCreationActivity extends NavigationDrawerSetup {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navView;
    private EditText mCreationTitleET;
    private EditText mCreationUrlET;
    private Spinner mCreationCategorySpinner;
    private SharedPreferences preferences;
    private ArrayList<String> categoryArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_creation);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Find our navigation view
        navView = (NavigationView) findViewById(R.id.nvView);

        configureDrawer(toolbar, mDrawer, AddCreationActivity.this, navView);
        setupDrawerContent(navView);


        mCreationTitleET = (EditText) findViewById(R.id.creationTitle);
        mCreationUrlET = (EditText) findViewById(R.id.creationUrl);
        mCreationCategorySpinner = (Spinner) findViewById(R.id.creationCategorySpinner);


        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String allCat = preferences.getString("ALL_CAT", "");
        try {
            JSONArray allCatArray = new JSONArray(allCat);
            for(int i=0, len=allCatArray.length();i<len;i++) {
                JSONObject currentCategory = (JSONObject) allCatArray.get(i);
                categoryArray.add((String) currentCategory.get("title"));
            }

            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoryArray);
            mCreationCategorySpinner.setAdapter(spinnerArrayAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void addCreation(View view) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseObject newCreation = new ParseObject("creation");
        newCreation.put("title", mCreationTitleET.getText().toString());
        newCreation.put("url", mCreationUrlET.getText().toString());
        newCreation.put("likes", 0);
        newCreation.put("user", ParseObject.createWithoutData(ParseUser.class, currentUser.getObjectId()));
        newCreation.put("category", mCreationCategorySpinner.getSelectedItem().toString());
        newCreation.saveInBackground();
        Intent intent = new Intent(this, CreationsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_creation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
