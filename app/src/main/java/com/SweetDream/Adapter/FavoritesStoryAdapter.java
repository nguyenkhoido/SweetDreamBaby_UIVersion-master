package com.SweetDream.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.SweetDream.Model.ItemsBook;
import com.SweetDream.R;

import java.util.List;

/**
 * Created by nguye_000 on 28/09/2015.
 */
public class FavoritesStoryAdapter extends ArrayAdapter<ItemsBook> {

    private Context context;

    List<ItemsBook> itemsFreeBooks;


    public FavoritesStoryAdapter(Context c, List<ItemsBook> list)
    {
        super(c,R.layout.custom_favoritesbook_adapter,list);
        context =  c;
        this.itemsFreeBooks = list;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.custom_favoritesbook_adapter, null);

        ImageView img = (ImageView)row.findViewById(R.id.imgFavorites);
        TextView txt1=(TextView)row.findViewById(R.id.titleFavorites);
        TextView txt2 = (TextView)row.findViewById(R.id.authorFavorites);
        ItemsBook item = itemsFreeBooks.get(position);
        img.setImageResource(item.getImage());
        txt1.setText(item.getTitleBook());
        txt2.setText(item.getTypeBook());
        return row;
    }
}
