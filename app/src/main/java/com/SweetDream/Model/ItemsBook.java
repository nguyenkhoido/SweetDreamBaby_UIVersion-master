package com.SweetDream.Model;

/**
 * Created by nguye_000 on 21/09/2015.
 */
public class ItemsBook {
    private String titleBook;
    private String typeBook;
    private int image;

    public ItemsBook(String titleBook, String typeBook, int image) {
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


}
