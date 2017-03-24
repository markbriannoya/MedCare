package com.example.marknoya.medcare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import static com.example.marknoya.medcare.R.layout.fragment_doctors;
import static com.example.marknoya.medcare.R.layout.fragment_home;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AppBarLayout.OnOffsetChangedListener {

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;

    private ImageView mProfileImage;
    private int mMaxScrollSize;

    private Database db;
    private Session session;

    TabLayout tabLayout;
    ViewPager viewPager;


    String[] account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new Session(this);
        db = new Database(this);
        account = db.getUserinfo(session.getUserid());

        TextView name = (TextView)findViewById(R.id.name);
        name.setText(account[0]);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.page);
        viewPager.setAdapter(new HomePageAdapter
                (getSupportFragmentManager()));

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //FOR NAVIGATION VIEW
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mProfileImage = (ImageView) findViewById(R.id.materialup_profile_image);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onBackPressed();
            }
        });

        AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.materialup_appbar);
        appbarLayout.addOnOffsetChangedListener(this);
        mMaxScrollSize = appbarLayout.getTotalScrollRange();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;
            mProfileImage.animate().scaleY(0).scaleX(0).setDuration(200).start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            mProfileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

            Toast.makeText(this,"Working",Toast.LENGTH_LONG);
            Intent openNearHospitals = new Intent(this, NearHospitalsActivity.class);
            startActivity(openNearHospitals);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bp)
        {
            Toast.makeText(this,"Working",Toast.LENGTH_LONG);
            //Intent openNearHospitals = new Intent(this, Near.class);
            //startActivity(openNearHospitals);

        }

        else if (id == R.id.nav_meals)
        {
            Intent openNearHospitals = new Intent(this, NearHospitalsActivity.class);
            startActivity(openNearHospitals);
        }

        else if (id == R.id.nav_medicine)
        {
            Intent openNearHospitals = new Intent(this, NearHospitalsActivity.class);
            startActivity(openNearHospitals);
        }

        else if (id == R.id.nav_family)
        {
            Intent openNearHospitals = new Intent(this, NearHospitalsActivity.class);
            startActivity(openNearHospitals);
        }

        else if (id == R.id.nav_doctors)
        {
            Intent openNearHospitals = new Intent(this, NearHospitalsActivity.class);
            startActivity(openNearHospitals);
        }

        else if (id == R.id.nav_symptoms)
        {
            Intent openNearHospitals = new Intent(this, NearHospitalsActivity.class);
            startActivity(openNearHospitals);
        }

        else if (id == R.id.nav_hospitals)
        {
            Intent openNearHospitals = new Intent(this, NearHospitalsActivity.class);
            startActivity(openNearHospitals);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class HomePageAdapter extends FragmentPagerAdapter {

        private String fragments[] = {"Home", "Doctors", "Family"};
        public HomePageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem (int position)
        {
            switch (position)
            {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new DoctorsFragment();
                case 2:
                    return new FamilyFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount()
        {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle (int position)
        {
            return fragments[position];
        }

    }


}

