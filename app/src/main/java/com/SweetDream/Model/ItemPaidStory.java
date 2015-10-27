package com.SweetDream.Model;

import com.parse.ParseFile;

/**
 * Created by nguye_000 on 12/10/2015.
 */
public class ItemPaidStory {
    private String objectId;
    private String titleBook;
    private String authorStory;
    private Number priceStory;
    private ParseFile image;

    //private int image;

    public ItemPaidStory(String objectId, String titleBook, String authorStory, Number priceStory, ParseFile image) {
        this.objectId = objectId;
        this.titleBook = titleBook;
        this.authorStory = authorStory;
        this.priceStory = priceStory;
        this.image = image;
    }

    public String getTitleBook() {
        return titleBook;
    }

    public void setTitleBook(String titleBook) {
        this.titleBook = titleBook;
    }

    public String getAuthorStory() {
        return authorStory;
    }

    public void setAuthorStory(String authorStory) {
        this.authorStory = authorStory;
    }

    public Number getPriceStory() {
        return priceStory;
    }

    public void setPriceStory(Number priceStory) {
        this.priceStory = priceStory;
    }

    public ParseFile getImage() {
        return image;
    }

    public void setImage(ParseFile image) {
        this.image = image;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
