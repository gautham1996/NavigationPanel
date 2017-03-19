package com.cfgb.finalnavi;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ViewAnimator;

import com.cfgb.finalnavi.common.logger.Log;
import com.cfgb.finalnavi.common.logger.LogFragment;
import com.cfgb.finalnavi.common.logger.LogWrapper;
import com.cfgb.finalnavi.common.logger.MessageOnlyLogFilter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG = "MainActivity";

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );

        Button button = (Button)findViewById( R.id.button);
        button.setTypeface(font);
        Button button2 = (Button)findViewById( R.id.button2);
        button2.setTypeface(font);
        Button button8 = (Button)findViewById( R.id.button3);
        button8.setTypeface(font);
        Button button4 = (Button)findViewById( R.id.button4);
        button4.setTypeface(font);
        Button button5 = (Button)findViewById( R.id.button5);
        button5.setTypeface(font);
        Button button6 = (Button)findViewById( R.id.button6);
        button6.setTypeface(font);
        Button button7 = (Button)findViewById( R.id.button7);
        button7.setTypeface(font);


        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SlidingTabsBasicFragment fragment = new SlidingTabsBasicFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
        //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        //toolbar.setNavigationIcon(null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final Button button3 = (Button) findViewById(R.id.lol);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override


    @SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.button) {
            // Handle the camera action
            //    } else if (id == R.id.nav_gallery) {

            //  } else if (id == R.id.nav_slideshow) {

            //} else if (id == R.id.nav_manage) {

            // } else if (id == R.id.nav_share) {

            //  } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    //public boolean onOptionsItemSelected(MenuItem item) {
      /*  switch(item.getItemId()) {
            case R.id.menu_toggle_log:
                mLogShown = !mLogShown;
                ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
                if (mLogShown) {
                    output.setDisplayedChild(1);
                } else {
                    output.setDisplayedChild(0);
                }
                supportInvalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }
            switch (item.getItemId()) {
                case R.id.menu_toggle_log:
                    mLogShown = !mLogShown;
                    ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
                    if (mLogShown) {
                        output.setDisplayedChild(1);
                    } else {
                        output.setDisplayedChild(0);
                    }
                    supportInvalidateOptionsMenu();
                    return true;
            }


            return super.onOptionsItemSelected(item);
        }

        /** Create a chain of targets that will receive log data */

        public void initializeLogging() {
            // Wraps Android's native log framework.
            LogWrapper logWrapper = new LogWrapper();
            // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
            Log.setLogNode(logWrapper);

            // Filter strips out everything except the message text.
            MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
            logWrapper.setNext(msgFilter);

            // On screen logging via a fragment with a TextView.
            LogFragment logFragment = (LogFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.log_fragment);
            msgFilter.setNext(logFragment.getLogView());

            Log.i(TAG, "Ready");
        }

    }
