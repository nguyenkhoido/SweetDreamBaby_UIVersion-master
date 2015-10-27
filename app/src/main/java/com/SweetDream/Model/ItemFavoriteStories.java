package com.SweetDream.Model;

import com.parse.ParseFile;

/**
 * Created by nguye_000 on 14/10/2015.
 */
public class ItemFavoriteStories {
    private String objectID;
    private String titleBook;
    private String authorStory;
    private Number priceStory;
    private ParseFile image;
    private String linkURL;
    private boolean isBuy;
    private Number likeCout;
    private Number downloadCout;
    private String description;
    public ItemFavoriteStories(String objectID,String titleBook, String authorStory,Number priceStory, ParseFile image, String linkURL, boolean isBuy,Number likeCout, Number downloadCout, String description){
        this.objectID = objectID;
        this.titleBook = titleBook;
        this.authorStory = authorStory;
        this.priceStory = priceStory;
        this.image = image;
        this.linkURL = linkURL;
        this.isBuy = isBuy;
        this.likeCout = likeCout;
        this.downloadCout = downloadCout;
        this.description = description;

    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public String getTitleBook() {
        return titleBook;
    }

    public void setTitleBook(String titleBook) {
        this.titleBook = titleBook;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Number getDownloadCout() {
        return downloadCout;
    }

    public void setDownloadCout(Number downloadCout) {
        this.downloadCout = downloadCout;
    }

    public Number getLikeCout() {
        return likeCout;
    }

    public void setLikeCout(Number likeCout) {
        this.likeCout = likeCout;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setIsBuy(boolean isBuy) {
        this.isBuy = isBuy;
    }

    public ParseFile getImage() {
        return image;
    }

    public void setImage(ParseFile image) {
        this.image = image;
    }

    public String getLinkURL() {
        return linkURL;
    }

    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }

    public Number getPriceStory() {
        return priceStory;
    }

    public void setPriceStory(Number priceStory) {
        this.priceStory = priceStory;
    }

    public String getAuthorStory() {
        return authorStory;
    }

    public void setAuthorStory(String authorStory) {
        this.authorStory = authorStory;
    }
}
