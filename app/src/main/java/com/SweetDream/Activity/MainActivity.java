package com.SweetDream.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.SweetDream.Adapter.FreeStoryAdapter;
import com.SweetDream.Adapter.PaidStoryAdapter;
import com.SweetDream.R;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_myBooks:
                        Intent intentMyBooks = new Intent(MainActivity.this, MyBookActivity.class);
                        startActivity(intentMyBooks);
                        break;

                    case R.id.navigation_item_favorites:
                        Intent intentFavorites = new Intent(MainActivity.this, FavoritesActivity.class);
                        startActivity(intentFavorites);
                        break;
                }


                return true;
            }
        });


        DesignDemoPagerAdapter adapter = new DesignDemoPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
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

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class DesignDemoFragment extends Fragment {
        private static final String TAB_POSITION = "tab_position";

        public DesignDemoFragment() {

        }

        public static DesignDemoFragment newInstance(int tabPosition) {
            DesignDemoFragment fragment = new DesignDemoFragment();
            Bundle args = new Bundle();
            args.putInt(TAB_POSITION, tabPosition);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle args = getArguments();
            int tabPosition = args.getInt(TAB_POSITION);
            //Set Layout For TabLayout
            //View v =  inflater.inflate(R.layout.activity_main, container, false);

            if (tabPosition == 0) {
                View free_story_layout = inflater.inflate(R.layout.free_story_tab, container, false);
                RecyclerView recyclerView = (RecyclerView) free_story_layout.findViewById(R.id.recycler_view_freeStoryTab);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                FreeStoryAdapter adapterFreeStory = new FreeStoryAdapter();
                recyclerView.setAdapter(adapterFreeStory);
                return free_story_layout;
            }
            if (tabPosition == 1) {
                View paid_story_layout = inflater.inflate(R.layout.free_story_tab, container, false);
                RecyclerView recyclerView = (RecyclerView) paid_story_layout.findViewById(R.id.recycler_view_freeStoryTab);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                PaidStoryAdapter adapterPaidStory = new PaidStoryAdapter();
                recyclerView.setAdapter(adapterPaidStory);
                return paid_story_layout;
            }
            return null;
        }
    }

    static class DesignDemoPagerAdapter extends FragmentStatePagerAdapter {

        public DesignDemoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DesignDemoFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";

            if (position == 0)

            {
                title = "Free Story";
            }

            if (position == 1) {
                title = "Paid Story";
            }

            if (position == 2) {
                title = "Best Free Story";
            }

            if (position == 3) {
                title = "Best Paid Story";
            }
            return title;
        }

    }

}
