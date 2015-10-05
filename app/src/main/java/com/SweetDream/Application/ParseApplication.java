package com.SweetDream.Application;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

/**
 * Created by Minh Color on 10/5/2015.
 */
public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Khai bao cai dat keyid, clientid cho toan ung dung
        Parse.initialize(this, "e7sgVQA7cLjKIT0x56W3h5VZFPDbVy3Hl7iPEdiA", "pS4yCy20nxKhmBtFJYeHf1z6oBNuwB9lZPxp9bt6");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        // Thu tu User dang ky
        //ParseUser.getCurrentUser().increment("RunCount");
        //ParseUser.getCurrentUser().saveInBackground();

        // Thiet lap ACL mac dinh de bao mat thoi diem dang nhap cua 1 user
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }
}
