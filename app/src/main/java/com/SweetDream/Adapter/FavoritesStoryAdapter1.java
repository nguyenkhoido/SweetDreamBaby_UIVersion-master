package com.SweetDream.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.SweetDream.Model.ItemFavoriteStories;
import com.SweetDream.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Minh Color on 10/28/2015.
 */
public class FavoritesStoryAdapter1 extends ArrayAdapter<ParseObject> {
    private Context context;

    List<ParseObject> itemFavorites;


    public FavoritesStoryAdapter1(Context c, List<ParseObject> list)
    {
        super(c, R.layout.custom_favoritesbook_adapter,list);
        context =  c;
        this.itemFavorites = list;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.custom_favoritesbook_adapter, null);

        ImageView imgView = (ImageView)row.findViewById(R.id.imgFavorites);
        TextView txt1=(TextView)row.findViewById(R.id.titleFavorites);
        TextView txt2 = (TextView)row.findViewById(R.id.authorFavorites);

        //ItemFavoriteStories item = itemFavorites.get(position);
        ParseObject item = itemFavorites.get(position);

        loadImages(item.getParseFile("Image"),imgView);
        txt1.setText(item.getString("StoryName"));
        txt2.setText(item.getString("Author"));

        return row;
    }
    private void loadImages(ParseFile thumbnail, final ImageView img) {

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
}
