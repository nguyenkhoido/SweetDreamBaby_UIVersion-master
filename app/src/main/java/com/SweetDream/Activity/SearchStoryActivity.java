package com.SweetDream.Activity;

import com.SweetDream.Adapter.SearchStoryAdapter;
import com.SweetDream.Model.ItemFavoriteStories;
import com.SweetDream.Model.ItemFreeStory;
import com.SweetDream.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchStoryActivity extends AppCompatActivity {

    List<ItemFavoriteStories> itemSearchStory;
    ArrayList<ItemFavoriteStories> arrayList = new ArrayList<>();

    ListView listSearch;
    SearchStoryAdapter adapter;

    ViewStub viewStub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_story);
        itemSearchStory = new ArrayList<>();
        getSearchStory();
        listSearch = (ListView) findViewById(R.id.listSearch);
        adapter = new SearchStoryAdapter(this, itemSearchStory);
        listSearch.setTextFilterEnabled(true);
        listSearch.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        // associate searchable configuration with the searchview
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                itemSearchStory.clear();
                if (newText.length() == 0) {
                    //itemSearchStory.addAll(arrayList);
                } else {
                    for (ItemFavoriteStories wp : arrayList) {
                        if (wp.getTitleBook().toLowerCase(Locale.getDefault())
                                .contains(newText )) {
                            itemSearchStory.add(wp);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        return true;
    }

    // when search open filter list view
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_search:
                onSearchRequested();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // this guy will get all your story information
    private void getSearchStory() {
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
                        ItemFavoriteStories answer = new ItemFavoriteStories(post.getString("StoryName"), post.getString("Author"), post.getNumber("Price"), fileObject, "", true, 1, 2, "");
                        itemSearchStory.add(answer);

                    }

                    //arrayList.addAll(itemSearchStory);
                    for (ItemFavoriteStories wp : itemSearchStory) {
                            arrayList.add(wp);
                        }

                    //adapter.notifyDataSetChanged();
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }

            }
        });
    }
}
