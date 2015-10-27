package com.SweetDream.Data;

import com.parse.ParseException;
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
