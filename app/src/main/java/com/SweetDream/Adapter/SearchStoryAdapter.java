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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Minh Color on 10/15/2015.
 */
public class SearchStoryAdapter extends ArrayAdapter<ItemFavoriteStories>{
    private Context context;

    List<ItemFavoriteStories> itemSearch = null;
    ArrayList<ItemFavoriteStories> arraylist;


    public SearchStoryAdapter(Context c, List<ItemFavoriteStories> list)
    {
        super(c, R.layout.custom_search_item,list);
        context =  c;
        this.itemSearch = list;
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(itemSearch);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.custom_search_item, null);

        ImageView imgView = (ImageView)row.findViewById(R.id.imgSearch);
        TextView txt1=(TextView)row.findViewById(R.id.titleSearch);
        TextView txt2 = (TextView)row.findViewById(R.id.authorSearch);

        ItemFavoriteStories item = itemSearch.get(position);

        loadImages(item.getImage(), imgView);
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

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        itemSearch.clear();
        if (charText.length() == 0) {
            itemSearch.addAll(arraylist);
        } else {
            for (ItemFavoriteStories wp : arraylist) {
                if (wp.getTitleBook().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    itemSearch.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
