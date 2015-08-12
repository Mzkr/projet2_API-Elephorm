package com.example.alexis.projet2_elephorm;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Damien on 11/08/15.
 */
public class NavigationDrawerSetup extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar mtoolbar;
    private Activity activity;
    private NavigationView nvView;
    private String actualItemMenu;

    public void configureDrawer(Toolbar toolbar, DrawerLayout drawerLayout, Activity currentActivity, NavigationView navView){
        mtoolbar = toolbar;
        mDrawer = drawerLayout;
        activity = currentActivity;
        setSupportActionBar(toolbar);
        ArrayList<String> runningactivities = new ArrayList<String>();

        ActivityManager activityManager = (ActivityManager)getBaseContext().getSystemService (Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> services = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (int i1 = 0; i1 < services.size(); i1++) {
            runningactivities.add(0,services.get(i1).topActivity.toString());
        }


        if(runningactivities.contains("ComponentInfo{com.example.alexis.projet2_elephorm/com.example.alexis.projet2_elephorm.MainActivity}")==true){
            actualItemMenu = "formations";
        }else if(runningactivities.contains("ComponentInfo{com.example.alexis.projet2_elephorm/com.example.alexis.projet2_elephorm.SecondActivity}")==true){
            actualItemMenu = "créations";
        }else{
            actualItemMenu = "other";
        }

        // Set the menu icon instead of the launcher icon.
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("•••••", "click on " + item);
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectItem(int position){
        Intent intent;
        switch (position) {
            case 0:
                Log.d("•••••", "position" + position);
                //intent = new Intent(activity, SecondActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //activity.startActivity(intent);
            case 1:
                Log.d("•••••", "position" + position);
        }
    }


    public void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {

        Intent intent;
        ArrayList<String> runningactivities = new ArrayList<String>();

        ActivityManager activityManager = (ActivityManager)getBaseContext().getSystemService (Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> services = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (int i1 = 0; i1 < services.size(); i1++) {
            runningactivities.add(0,services.get(i1).topActivity.toString());
        }


        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                mDrawer.closeDrawers();
                if(runningactivities.contains("ComponentInfo{com.example.alexis.projet2_elephorm/com.example.alexis.projet2_elephorm.MainActivity}")==true){
                    break;
                }
                intent = new Intent(activity, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
                break;
            case R.id.nav_second_fragment:
                mDrawer.closeDrawers();
                if(runningactivities.contains("ComponentInfo{com.example.alexis.projet2_elephorm/com.example.alexis.projet2_elephorm.SecondActivity}")==true){
                    break;
                }
                intent = new Intent(activity, SecondActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
                break;
            case R.id.nav_third_fragment:
                mDrawer.closeDrawers();
                //if(runningactivities.contains("ComponentInfo{com.example.alexis.projet2_elephorm/com.example.alexis.projet2_elephorm.thirdActivityName}")==true){
                //    break;
                //}
                //intent = new Intent(activity, thirdActivityName.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //activity.startActivity(intent);
                break;
            default:
        }

        // Highlight the selected item, update the title, and close the drawer
        //menuItem.setChecked(true);
        //setTitle(menuItem.getTitle());
    }

}
