package com.SweetDream.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.SweetDream.Adapter.FreeStoryAdapter;
import com.SweetDream.Adapter.PaidStoryAdapter;
import com.SweetDream.Model.ItemFreeStory;
import com.SweetDream.Model.ItemPaidStory;
import com.SweetDream.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    Button btnLogin;

    ImageButton btnLogOut;
    TextView txtUserNameFB, txtUserEmailFB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUserNameFB = (TextView) findViewById(R.id.txtUserNameFacebook);
        txtUserEmailFB = (TextView) findViewById(R.id.txtUserEmailFacebook);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.SweetDream",
                    PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        final ParseUser currentUser = ParseUser.getCurrentUser();
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setText("Login");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadLoginView();
            }
        });



        btnLogOut = (ImageButton) findViewById(R.id.btnLogOut);

        if (currentUser != null) {
            btnLogin.setVisibility(View.GONE);

            //Set Text UserName Email when login Parse Or Facebook-------------Error, Need to resolve!
            txtUserNameFB.setText(currentUser.getUsername());
            txtUserEmailFB.setText(currentUser.getEmail());
            txtUserEmailFB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, MyProfileActivity.class);
                    startActivity(i);
                }
            });

            btnLogOut.setVisibility(View.VISIBLE);

            //Phan dang nhap xong cho phep luu tru noi dung private bat ki cua user nay
            /*ParseObject privateNote = new ParseObject("Note");
            privateNote.put("content", "This note is private!");
            privateNote.setACL(new ParseACL(ParseUser.getCurrentUser()));
            privateNote.saveInBackground();*/

            btnLogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ParseUser.logOut();

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                }
            });
        }
        if (currentUser == null) {
            btnLogin.setVisibility(View.VISIBLE);
            txtUserEmailFB.setVisibility(View.GONE);
            txtUserNameFB.setVisibility(View.GONE);
            btnLogOut.setVisibility(View.GONE);
        }


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_features:
                        fragment = new FeaturePage();
                        break;

                    case R.id.navigation_item_favorites:
                        fragment = new FavoritesActivity();
                        break;

                    case R.id.navigation_item_myBooks:
                        fragment = new MyBookActivity();
                        break;


                }
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.mainFrameLayout, fragment).commit();

                    // update selected item and title, then close the drawer

                } else {
                    // error in creating fragment
                    Log.e("UserActivity", "Error in creating fragment");
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

    private void loadLoginView() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

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

        //Declare list from parse and adapter of Recyclerview
        List<ItemFreeStory> itemsFreeStoryList;
        List<ItemPaidStory> itemsPaidStoryList;
        FreeStoryAdapter adapterFreeStory;
        PaidStoryAdapter adapterPaidStory;
        private Dialog processingDialog;

        // in contructor create list from parse
        public DesignDemoFragment() {
            itemsFreeStoryList = new ArrayList<>();
            itemsPaidStoryList = new ArrayList<>();
            adapterFreeStory = new FreeStoryAdapter(itemsFreeStoryList);
            adapterPaidStory = new PaidStoryAdapter(itemsPaidStoryList);
            //processingDialog = new ProgressDialog(getActivity());

        }

        // this guy will get all your story information
        private void getFreeStory(){
            //processingDialog = ProgressDialog.show(super.getActivity(), "", "Loading data...", true);
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Story");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> postList, ParseException e) {
                    if (e == null) {
                        //processingDialog.dismiss();
                        // if there results, update the list of posts
                        for (ParseObject post : postList) {
                            ParseFile fileObject = (ParseFile) post.get("Image");
                            ItemFreeStory answer = new ItemFreeStory(post.getString("StoryName"),post.getString("Author"), post.getInt("Price"), fileObject);
                            itemsFreeStoryList.add(answer);
                        }
                        adapterFreeStory.notifyDataSetChanged();
                    } else {
                        Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                    }

                }
            });
        }

        // this guy will get all your story information
        private void getPaidStory(){
            //processingDialog = ProgressDialog.show(super.getActivity(), "", "Loading data...", true);
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Story");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> postList, ParseException e) {
                    if (e == null) {
                        //processingDialog.dismiss();
                        // if there results, update the list of posts
                        for (ParseObject post : postList) {
                            ParseFile fileObject = (ParseFile) post.get("Image");
                            ItemPaidStory answer = new ItemPaidStory(post.getString("StoryName"),post.getString("Author"),post.getNumber("Price"),fileObject);
                            itemsPaidStoryList.add(answer);
                        }
                        adapterPaidStory.notifyDataSetChanged();
                    } else {
                        Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                    }

                }
            });
        }

        // this guy is get postion of the tablayout to setview
        public static DesignDemoFragment newInstance(int tabPosition) {
            DesignDemoFragment fragment = new DesignDemoFragment();
            Bundle args = new Bundle();
            args.putInt(TAB_POSITION, tabPosition);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle args = getArguments();
            int tabPosition = args.getInt(TAB_POSITION);

            // define view to set our tablayout
            View story_view = inflater.inflate(R.layout.free_story_tab, container, false);
            RecyclerView recyclerView = (RecyclerView) story_view.findViewById(R.id.recycler_view_freeStoryTab);

            Display display = getActivity().getWindowManager().getDefaultDisplay();
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);

            float density  = getResources().getDisplayMetrics().density;
            float dpWidth  = outMetrics.widthPixels / density;
            int columns = Math.round(dpWidth/300);
            recyclerView.setHasFixedSize(true);
            // The number of Columns
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),columns));

            if (tabPosition == 0) {
                //Call get free story method
                getFreeStory();
                // get Adapter above and set to recyclerview

                recyclerView.setAdapter(adapterFreeStory);
                return story_view;
            }
            if (tabPosition == 1) {

                //Call get paid story method
                getPaidStory();

                recyclerView.setAdapter(adapterPaidStory);
                return story_view;
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

            if (position == 0) {
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
