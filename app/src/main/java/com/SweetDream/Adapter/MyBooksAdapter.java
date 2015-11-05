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

import com.SweetDream.Model.ItemsBook;
import com.SweetDream.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;

import java.util.List;

public class MyBooksAdapter extends ArrayAdapter<ItemsBook> {

    private Context context;

    List<ItemsBook> itemBooks;


    public MyBooksAdapter(Context c, List<ItemsBook> list)
    {
        super(c,R.layout.custom_cardview_bookslist_adapter,list);
        context =  c;
        this.itemBooks = list;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.custom_cardview_bookslist_adapter, null);

        ParseImageView imgView = (ParseImageView)row.findViewById(R.id.imgBooks);
        TextView txt1=(TextView)row.findViewById(R.id.titleBook);
        TextView txt2 = (TextView)row.findViewById(R.id.authorBook);

        ItemsBook item = itemBooks.get(position);

        loadImages(item.getImage(),imgView);
        txt1.setText(item.getTitleBook());
        txt2.setText(item.getTypeBook());

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
