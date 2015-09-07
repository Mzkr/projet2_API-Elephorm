package com.example.alexis.projet2_elephorm;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.alexis.projet2_elephorm.model.Category;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class ConnexionActivity extends NavigationDrawerSetup {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navView;
    private String mUsername;
    private String mPassword;
    private String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Find our navigation view
        navView = (NavigationView) findViewById(R.id.nvView);

        configureDrawer(toolbar, mDrawer, ConnexionActivity.this, navView);
        setupDrawerContent(navView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_connexion, menu);
        return true;
    }

    public void connect(View view) {

        EditText usernameET = (EditText) findViewById(R.id.username);
        mUsername = usernameET.getText().toString().trim();
        EditText passwordET = (EditText) findViewById(R.id.password);
        mPassword = usernameET.getText().toString().trim();
        if(findViewById(R.id.email) != null) {
            EditText emailET = (EditText) findViewById(R.id.email);
            mEmail = emailET.getText().toString().trim();
        }

        ParseUser.logInInBackground(mUsername, mPassword, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Intent i = new Intent(ConnexionActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Log.d("•••••", "error : " + e.getMessage());
                }
            }
        });
    }

    public void createAccount(View view) {

        EditText usernameET = (EditText) findViewById(R.id.username);
        mUsername = usernameET.getText().toString().trim();
        EditText passwordET = (EditText) findViewById(R.id.password);
        mPassword = usernameET.getText().toString().trim();
        if(findViewById(R.id.email) != null) {
            EditText emailET = (EditText) findViewById(R.id.email);
            mEmail = emailET.getText().toString().trim();
        }

        ParseUser user = new ParseUser();
        user.setUsername(mUsername);
        user.setPassword(mPassword);
        user.setEmail(mEmail);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Intent i = new Intent(ConnexionActivity.this, CategoryMain.class);
                    startActivity(i);
                } else {
                   Log.d("•••••", "error : " + e.getMessage());
                }
            }
        });
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
