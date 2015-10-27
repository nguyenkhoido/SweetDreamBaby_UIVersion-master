package com.SweetDream.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.SweetDream.Adapter.FavoritesStoryAdapter;
import com.SweetDream.Model.ItemFavoriteStories;
import com.SweetDream.R;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
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
    SwipeMenuListView list;
    List<ItemFavoriteStories> itemFavorites;
    FavoritesStoryAdapter adapterFavoriteStories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favorites, container, false);

        list = (SwipeMenuListView) view.findViewById(R.id.listItemFavorites);
        itemFavorites = new ArrayList<>();
        adapterFavoriteStories = new FavoritesStoryAdapter(getActivity(), itemFavorites);
        getFavoriteStories();
        list.setAdapter(adapterFavoriteStories);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
/*
            // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
*/

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        list.setMenuCreator(creator);
        list.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                ItemFavoriteStories item = itemFavorites.get(position);


                deleteRelationShip(item);
                itemFavorites.remove(position);
                adapterFavoriteStories.notifyDataSetChanged();


                return false;
            }
        });
        return view;
    }

    private void deleteRelationShip(ItemFavoriteStories item) {
        // delete app
        try {
            Intent intent = new Intent(Intent.ACTION_DELETE);

            startActivity(intent);
        } catch (Exception e) {
        }
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
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
                        ItemFavoriteStories answer = new ItemFavoriteStories(post.getObjectId(),post.getString("StoryName"), post.getString("Author"), post.getNumber("Price"), fileObject, "", true, 1, 2, "");
                        itemFavorites.add(answer);
                    }
                    adapterFavoriteStories.notifyDataSetChanged();
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }

            }
        });
    }


}