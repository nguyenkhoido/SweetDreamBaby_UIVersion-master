package com.SweetDream.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
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
import com.parse.ParseImageView;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by nguye_000 on 28/09/2015.
 */
public class FavoritesStoryAdapter extends ArrayAdapter<ItemFavoriteStories> {

    private Context context;

    List<ItemFavoriteStories> itemFavorites;


    public FavoritesStoryAdapter(Context c, List<ItemFavoriteStories> list)
    {
        super(c,R.layout.custom_favoritesbook_adapter,list);
        context =  c;
        this.itemFavorites = list;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.custom_favoritesbook_adapter, null);

        ParseImageView imgView = (ParseImageView)row.findViewById(R.id.imgFavorites);
        TextView txt1=(TextView)row.findViewById(R.id.titleFavorites);
        TextView txt2 = (TextView)row.findViewById(R.id.authorFavorites);

        ItemFavoriteStories item = itemFavorites.get(position);

        loadImages(item.getImage(),imgView);
        txt1.setText(item.getTitleBook());
        txt2.setText(item.getAuthorStory());

        return row;
    }
    // load file from parse and set it to imageview (not real)
    private void loadImages(ParseFile thumbnail, final ParseImageView img) {

        if (thumbnail != null) {
            img.setParseFile(thumbnail);
            img.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    // nothing to do
                }
            });
        } else {
            img.setImageResource(R.drawable.thor);
        }
    }// load image

}
