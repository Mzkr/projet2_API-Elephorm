package com.example.alexis.projet2_elephorm;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.alexis.projet2_elephorm.util.ProxyParseObject;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CreationsActivity extends NavigationDrawerSetup {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navView;
    private List<ParseObject> creationsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creations);

        creationsList = new ArrayList<ParseObject>();

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Find our navigation view
        navView = (NavigationView) findViewById(R.id.nvView);

        configureDrawer(toolbar, mDrawer, CreationsActivity.this, navView);
        setupDrawerContent(navView);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("creation");
        query.include("user");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if(list.size() > 0){
                        for (int i = 0; i < list.size(); i++) {
                            creationsList.add(i, list.get(i));
                        }
                        createGridImagesView();
                    }
                } else {
                    Log.e("•••••", "error : " + e.getMessage());
                }
            }
        });

        ParseUser currentUser = ParseUser.getCurrentUser();
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addButton);
        if (currentUser != null) {
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(CreationsActivity.this, AddCreationActivity.class);
                    startActivity(i);
                }
            });

        } else {
            addButton.setVisibility(View.INVISIBLE);
        }

    }

    public void createGridImagesView(){
        GridView gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(new ImageAdapter(this, creationsList));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Log.d("•••••", "click on item #" + position + " detected");
                ParseObject creation = creationsList.get(position);
                ProxyParseObject ppoCreation = new ProxyParseObject(creation);
                Intent i = new Intent(CreationsActivity.this, CreationDetailActivity.class);
                i.putExtra("creation", ppoCreation);
                startActivity(i);
            }
        });
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
