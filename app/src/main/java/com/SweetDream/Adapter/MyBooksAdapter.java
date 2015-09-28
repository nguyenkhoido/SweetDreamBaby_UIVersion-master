package com.SweetDream.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.SweetDream.Model.ItemsBook;
import com.SweetDream.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguye_000 on 16/09/2015.
 */
public class MyBooksAdapter extends RecyclerView.Adapter<MyBooksAdapter.ViewHolder> {

    List<ItemsBook> itemMyBooks;

    public MyBooksAdapter() {
        super();
        itemMyBooks = new ArrayList<ItemsBook>();

        itemMyBooks = new ArrayList<ItemsBook>();

        ItemsBook item = new ItemsBook("Iron Man","Free",R.drawable.ironman2);

        itemMyBooks.add(item);

        ItemsBook item1 = new ItemsBook("Iron Man","Free",R.drawable.ironman2);

        itemMyBooks.add(item1);

        ItemsBook item2 = new ItemsBook("Iron Man","Free",R.drawable.ironman2);

        itemMyBooks.add(item2);

        ItemsBook item3 = new ItemsBook("Iron Man","Free",R.drawable.ironman2);

        itemMyBooks.add(item3);

        ItemsBook item4 = new ItemsBook("Iron Man","Free",R.drawable.ironman2);

        itemMyBooks.add(item4);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_cardview_bookslist_adapter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ItemsBook item = itemMyBooks.get(i);
        viewHolder.txtTitle.setText(item.getTitleBook());
        viewHolder.txtType.setText(item.getTypeBook());
        viewHolder.imgCardView.setImageResource(item.getImage());
    }

    @Override
    public int getItemCount() {
        return itemMyBooks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgCardView;
        public TextView txtTitle;
        public TextView txtType;
        Button btnBooksListPage;
        public ViewHolder(View itemView) {
            super(itemView);
            imgCardView = (ImageView) itemView.findViewById(R.id.imgCardView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitleBooksListPage);
            txtType = (TextView) itemView.findViewById(R.id.txtTypeBooksListPage);

            btnBooksListPage = (Button)itemView.findViewById(R.id.btnBooksListPage);
            btnBooksListPage.setVisibility(itemView.GONE);
        }
    }
}
