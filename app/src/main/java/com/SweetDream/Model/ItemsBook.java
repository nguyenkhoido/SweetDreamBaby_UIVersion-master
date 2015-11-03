package com.SweetDream.Model;

import com.parse.ParseFile;

/**
 * Created by nguye_000 on 21/09/2015.
 */
public class ItemsBook {
    public String getObjectID() {
        return ObjectID;
    }

    public void setObjectID(String objectID) {
        ObjectID = objectID;
    }

    private String ObjectID;
    private String titleBook;
    private String typeBook;
    private ParseFile image;

    public ItemsBook(String objectid ,String titleBook, String typeBook, ParseFile image) {
        this.ObjectID = objectid;
        this.titleBook = titleBook;
        this.typeBook = typeBook;
        this.image = image;
    }

    public String getTitleBook() {
        return titleBook;
    }

    public void setTitleBook(String titleBook) {
        this.titleBook = titleBook;
    }

    public String getTypeBook() {
        return typeBook;
    }

    public void setTypeBook(String typeBook) {
        this.typeBook = typeBook;
    }

    public ParseFile getImage() {
        return image;
    }

    public void setImage(ParseFile image) {
        this.image = image;
    }



}
