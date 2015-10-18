package com.SweetDream.Model;

import com.parse.ParseFile;

/**
 * Created by Minh Color on 10/11/2015.
 */
//
public class ItemFreeStory {
    private String titleBook;
    private String authorBook;

    private ParseFile image;
    //private int image;

    public ItemFreeStory(String titleBook, String authorBook, int price, ParseFile image) {
        this.titleBook = titleBook;
        this.authorBook = authorBook;
        this.image = image;
    }

    public String getTitleBook() {
        return titleBook;
    }

    public void setTitleBook(String titleBook) {
        this.titleBook = titleBook;
    }

    public String getAuthorBook() {
        return authorBook;
    }

    public void setAuthorBook(String authorBook) {
        this.authorBook = authorBook;
    }

    public ParseFile getImage() {
        return image;
    }

    public void setImage(ParseFile image) {
        this.image = image;
    }
}
