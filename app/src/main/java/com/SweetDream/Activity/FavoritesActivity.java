package com.SweetDream.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.SweetDream.Adapter.FavoritesStoryAdapter;
import com.SweetDream.Model.ItemFavoriteStories;
import com.SweetDream.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguye_000 on 28/09/2015.
 */
public class FavoritesActivity extends Fragment {
    ListView list;
    List<ItemFavoriteStories> itemFavorites;
    FavoritesStoryAdapter adapterFavoriteStories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favorites, container, false);
        list = (ListView) view.findViewById(R.id.listItemFavorites);
        itemFavorites = new ArrayList<>();
        adapterFavoriteStories = new FavoritesStoryAdapter(getActivity(), itemFavorites);
        getFavoriteStories();
        list.setAdapter(adapterFavoriteStories);
        return view;
    }

    private void getFavoriteStories() {
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
                        itemFavorites.add(answer);
                    }
                    adapterFavoriteStories.notifyDataSetChanged();
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
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

        switch (id) {

            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}