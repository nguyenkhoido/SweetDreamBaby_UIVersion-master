package com.SweetDream.Data;

import android.app.ProgressDialog;
import android.util.Log;

import com.SweetDream.Adapter.FreeStoryAdapter;
import com.SweetDream.Model.ItemFreeStory;
import com.SweetDream.Model.ItemPaidStory;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Minh Color on 10/24/2015.
 */
public class StoryList {
    List<ParseObject> itemsFreeStoryList;

    public List<ParseObject> getFreeStory() {
        itemsFreeStoryList = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Story");
        query.whereEqualTo("Price", 0);
        try {
            itemsFreeStoryList = query.find();



        } catch (ParseException e) {
            e.printStackTrace();
        }
        return itemsFreeStoryList;
    }

    List<ParseObject> itemsPaidStoryList;

    public List<ParseObject> getPaidStory() {
        itemsPaidStoryList = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Story");
        query.whereNotEqualTo("Price", 0);
        try {
            itemsPaidStoryList = query.find();



        } catch (ParseException e) {
            e.printStackTrace();
        }
        return itemsPaidStoryList;
    }

}
