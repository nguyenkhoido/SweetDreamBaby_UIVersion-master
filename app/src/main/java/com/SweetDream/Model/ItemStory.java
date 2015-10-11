package com.SweetDream.Model;

import com.parse.ParseFile;

/**
 * Created by Minh Color on 10/11/2015.
 */
public class ItemStory {
    private String titleBook;
    private String typeBook;

    private ParseFile image;
    //private int image;

    public ItemStory(String titleBook, String typeBook, ParseFile image) {
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
