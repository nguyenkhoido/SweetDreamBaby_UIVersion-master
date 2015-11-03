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

        ImageView imgView = (ImageView)row.findViewById(R.id.imgBooks);
        TextView txt1=(TextView)row.findViewById(R.id.titleBook);
        TextView txt2 = (TextView)row.findViewById(R.id.authorBook);

        ItemsBook item = itemBooks.get(position);

        loadImages(item.getImage(),imgView);
        txt1.setText(item.getTitleBook());
        txt2.setText(item.getTypeBook());

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
