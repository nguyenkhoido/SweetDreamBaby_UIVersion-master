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

        ImageView imgView = (ImageView)row.findViewById(R.id.imgFavorites);
        TextView txt1=(TextView)row.findViewById(R.id.titleFavorites);
        TextView txt2 = (TextView)row.findViewById(R.id.authorFavorites);

        ItemFavoriteStories item = itemFavorites.get(position);

        loadImages(item.getImage(),imgView);
        txt1.setText(item.getTitleBook());
        txt2.setText(item.getAuthorStory());

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
