package com.SweetDream.Model;

import com.parse.ParseFile;

/**
 * Created by Minh Color on 10/11/2015.
 */
public class ItemFreeStory {
    private String objectId;
    private String titleBook;
    private String authorBook;

    private ParseFile image;

    private String audioFile;
    //private int image;

    public ItemFreeStory(String objectId, String titleBook, String authorBook, int price, ParseFile image, String audioFile) {
        this.objectId = objectId;
        this.titleBook = titleBook;
        this.authorBook = authorBook;
        this.image = image;
        this.audioFile = audioFile;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }
}
