package com.SweetDream.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.SweetDream.Adapter.MyBooksAdapter;
import com.SweetDream.Model.ItemsBook;
import com.SweetDream.R;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MyBookActivity extends Fragment {

    SwipeMenuListView list;
    List<ItemsBook> itemBooks;
    List<ParseObject> itemBooks1;
    MyBooksAdapter adapterBookStories;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_books, container, false);

        list = (SwipeMenuListView) view.findViewById(R.id.listItemMyBook);
        itemBooks = new ArrayList<>();
        itemBooks1 = new ArrayList<>();
        //adapterFavoriteStories1 = new FavoritesStoryAdapter1(getActivity(), itemFavorites1);
        adapterBookStories = new MyBooksAdapter(getActivity(), itemBooks);
        getMyBookStories();
        list.setAdapter(adapterBookStories);
        //list.setAdapter(adapterFavoriteStories1);
        return view;
    }


    private void getMyBookStories() {
        //processingDialog = ProgressDialog.show(super.getActivity(), "", "Loading data...", true);
        // suppose we have a book object
        ParseUser user = ParseUser.getCurrentUser();

// create a relation based on the authors key
        ParseRelation relation = user.getRelation("StoryPaid");

// generate a query based on that relation
        ParseQuery query = relation.getQuery();
        /*try {
            itemFavorites1 = query.find();
            adapterFavoriteStories1 = new FavoritesStoryAdapter1(getActivity(), itemFavorites1);
            list.setAdapter(adapterFavoriteStories1);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
// now execute the query
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> postList, ParseException e) {
                if (e == null) {
                    //processingDialog.dismiss();

                    // if there results, update the list of posts
                    for (ParseObject post : postList) {
                        ParseFile fileObject = (ParseFile) post.get("Image");
                        ItemsBook answer = new ItemsBook(post.getObjectId(),post.getString("StoryName"),post.getString("Author"),fileObject);
                        itemBooks.add(answer);
                    }
                    adapterBookStories.notifyDataSetChanged();
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }

            }
        });
    }


}