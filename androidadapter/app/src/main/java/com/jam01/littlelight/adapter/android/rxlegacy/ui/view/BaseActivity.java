package com.jam01.littlelight.adapter.android.rxlegacy.ui.view;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.jam01.littlelight.R;


/**
 * Created by jam01 on 4/10/16.
 * See:
 * http://stackoverflow.com/questions/19451715/same-navigation-drawer-in-different-activities/19451842#19451842
 * http://androiddeveloperdemo.blogspot.com/2014/08/android-navigation-drawer-with-multiple.html
 */
public class BaseActivity extends AppCompatActivity {
    protected static int position;
    public DrawerLayout drawerLayout;
    public ListView drawerList;
    public String[] drawerArray;
    protected FrameLayout activityContainer;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        activityContainer = (FrameLayout) findViewById(R.id.content_frame);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        drawerArray = getResources().getStringArray(R.array.nav_drawer_items);

        drawerList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, drawerArray));

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                openActivity(pos);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        drawerToggle = new ActionBarDrawerToggle((Activity) this, drawerLayout, 0, 0) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(R.string.app_name);
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(R.string.menu);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }


    /**
     * @param position Launching activity when any list item is clicked.
     */
    protected void openActivity(int position) {

        /**
         * We can set title & itemChecked here but as this BaseActivity is parent for other activity,
         * So whenever any activity is going to launch this BaseActivity is also going to be called and
         * it will reset this value because of initialization in onCreate method.
         * So that we are setting this in child activity.
         */

        drawerLayout.closeDrawer(drawerList);
        //Setting currently selected position in this field so that it will be available in our child activities.
        BaseActivity.position = position;

        switch (position) {
            case 0:
                startActivity(new Intent(this, LegendActivity.class));
                finish();
                break;
            case 1:
                startActivity(new Intent(this, InventoryActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


}
