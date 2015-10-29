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
import android.widget.Toast;

import com.SweetDream.Adapter.FavoritesStoryAdapter;
import com.SweetDream.Adapter.FavoritesStoryAdapter1;
import com.SweetDream.Model.ItemFavoriteStories;
import com.SweetDream.Model.ItemFreeStory;
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
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by nguye_000 on 28/09/2015.
 */
public class FavoritesActivity extends Fragment {
    SwipeMenuListView list;
    List<ItemFavoriteStories> itemFavorites;
    List<ParseObject> itemFavorites1;
    FavoritesStoryAdapter adapterFavoriteStories;
    FavoritesStoryAdapter1 adapterFavoriteStories1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favorites, container, false);

        list = (SwipeMenuListView) view.findViewById(R.id.listItemFavorites);
        itemFavorites = new ArrayList<>();
        itemFavorites1 = new ArrayList<>();
        //adapterFavoriteStories1 = new FavoritesStoryAdapter1(getActivity(), itemFavorites1);
        getFavoriteStories();
        //list.setAdapter(adapterFavoriteStories1);
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
                //ItemFavoriteStories item = itemFavorites.get(position);
                ParseObject item = itemFavorites1.get(position);
                Log.e("vi tri click: ",""+position);
                Log.e("objectId: ", "" + item.getObjectId());
                itemFavorites1.remove(position);

// delete app

                ParseObject object = ParseObject.createWithoutData("Story", item.getObjectId());
//processingDialog = ProgressDialog.show(super.getActivity(), "", "Loading data...", true);
                // suppose we have a book object
                ParseUser user = ParseUser.getCurrentUser();

// create a relation based on the authors key
                ParseRelation relation = user.getRelation("StoryLove");
                relation.remove(object);
                // user save relation
                user.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getActivity(), "Your story now is removed!!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(),
                                    "Error removing: " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                });

                list.setAdapter(adapterFavoriteStories1);
                return true;
            }
        });
        return view;
    }

    private void deleteRelationShip(ItemFavoriteStories item) {

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
        // suppose we have a book object
        ParseUser user = ParseUser.getCurrentUser();

// create a relation based on the authors key
        ParseRelation relation = user.getRelation("StoryLove");

// generate a query based on that relation
        ParseQuery query = relation.getQuery();
        try {
            itemFavorites1 = query.find();
            adapterFavoriteStories1 = new FavoritesStoryAdapter1(getActivity(), itemFavorites1);
            list.setAdapter(adapterFavoriteStories1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
// now execute the query
        /*query.findInBackground(new FindCallback<ParseObject>() {
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
        });*/
    }


}