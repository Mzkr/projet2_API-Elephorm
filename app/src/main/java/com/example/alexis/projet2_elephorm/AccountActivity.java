package com.example.alexis.projet2_elephorm;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

public class AccountActivity extends NavigationDrawerSetup {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Find our navigation view
        navView = (NavigationView) findViewById(R.id.nvView);

        configureDrawer(toolbar, mDrawer, AccountActivity.this, navView);
        setupDrawerContent(navView);


        TextView username = (TextView) findViewById(R.id.username);
        username.setTypeface(null, Typeface.BOLD);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            username.setText(currentUser.getString("username"));
        } else {
            Toast.makeText(getApplicationContext(), "veuillez vous connecter", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(AccountActivity.this, ConnexionActivity.class);
            startActivity(i);
        }

    }

    public void logout(View view) {
        ParseUser.logOut();
        Intent i = new Intent(AccountActivity.this, ConnexionActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account, menu);
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
