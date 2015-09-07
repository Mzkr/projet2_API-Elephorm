package com.example.alexis.projet2_elephorm;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.alexis.projet2_elephorm.app.AppController;
import com.example.alexis.projet2_elephorm.util.ProxyParseObject;
import com.parse.ParseObject;

public class CreationDetailActivity extends NavigationDrawerSetup {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navView;
    public ProxyParseObject mCreation;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_detail);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Find our navigation view
        navView = (NavigationView) findViewById(R.id.nvView);

        configureDrawer(toolbar, mDrawer, CreationDetailActivity.this, navView);
        setupDrawerContent(navView);

        Intent intent = getIntent();
        ProxyParseObject creation = (ProxyParseObject) intent.getSerializableExtra("creation");
        mCreation = creation;

        if (imageLoader == null){
            imageLoader = AppController.getInstance().getImageLoader();
        }

        NetworkImageView image = (NetworkImageView) findViewById(R.id.creationImage);
        TextView creationTitle = (TextView) findViewById(R.id.creationTitle);
        creationTitle.setTypeface(null, Typeface.BOLD);
        TextView creationAuthor = (TextView) findViewById(R.id.creationAuthor);
        TextView creationLikeCount = (TextView) findViewById(R.id.creationLikeCount);
        TextView creationLikeLabel = (TextView) findViewById(R.id.creationLikeLabel);
        TextView creationCategory = (TextView) findViewById(R.id.creationCategories);
        creationCategory.setTypeface(null, Typeface.BOLD);
        TextView creationDescription = (TextView) findViewById(R.id.creationDescription);

        creationTitle.setText(creation.getString("title"));
        creationAuthor.setText(creation.getParseUser("user").getString("username"));
        creationLikeCount.setText("" + creation.getInt("likes"));
        if(creation.getInt("likes") > 1){
            creationLikeLabel.setText("likes");
        }
        creationCategory.setText(creation.getString("category"));
        creationDescription.setText(creation.getString("description"));
        image.setImageUrl(creation.getString("url"), imageLoader);
        //android.view.ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
        //layoutParams.height = image.getMeasuredWidth();
        //image.setLayoutParams(layoutParams);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_creation_detail, menu);
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
