package com.SweetDream.Extends;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.SweetDream.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.net.URL;

/**
 * Created by Minh Color on 10/17/2015.
 */
public class LoadImageAudioParse {
    // load file from parse and set it to imageview (not real)
    public void loadImages(ParseFile thumbnail, final ImageView img) {

        if (thumbnail != null) {
            thumbnail.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (e == null) {

                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                        img.setImageBitmap(bmp);

                    } else {
                    }
                }
            });
        } else {
            img.setImageResource(R.drawable.thor);
        }
    }// load image

    public void LoadImagesUrlParse(String directory, final ImageView img){
        if(!directory.equalsIgnoreCase("")){
            try {
                URL url = new URL(directory);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                img.setImageBitmap(bmp);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            img.setImageResource(R.drawable.thor);
        }

    }
}
