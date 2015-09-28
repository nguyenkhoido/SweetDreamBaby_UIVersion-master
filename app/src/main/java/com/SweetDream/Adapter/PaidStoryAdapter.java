package com.SweetDream.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.SweetDream.Activity.MyBookActivity;
import com.SweetDream.Model.ItemsBook;
import com.SweetDream.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguye_000 on 25/09/2015.
 */
public class PaidStoryAdapter extends RecyclerView.Adapter<PaidStoryAdapter.ViewHolder> {
    List<ItemsBook> itemsPaidBooks;

    public PaidStoryAdapter() {
        super();
        itemsPaidBooks = new ArrayList<ItemsBook>();

        ItemsBook item = new ItemsBook("Iron Man","Free",R.drawable.ironman2);

        itemsPaidBooks.add(item);

        ItemsBook item1 = new ItemsBook("Iron Man","Free",R.drawable.ironman2);

        itemsPaidBooks.add(item1);

        ItemsBook item2 = new ItemsBook("Iron Man","Free",R.drawable.ironman2);

        itemsPaidBooks.add(item2);

        ItemsBook item3 = new ItemsBook("Iron Man","Free",R.drawable.ironman2);

        itemsPaidBooks.add(item3);

        ItemsBook item4 = new ItemsBook("Iron Man","Free",R.drawable.ironman2);

        itemsPaidBooks.add(item4);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_cardview_tapstory_adapter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ItemsBook item = itemsPaidBooks.get(i);
        viewHolder.txtTitle.setText(item.getTitleBook());
        viewHolder.txtType.setText(item.getTypeBook());
        viewHolder.imgPaidStory.setImageResource(item.getImage());

        viewHolder.imgPaidStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                context.startActivity(new Intent(context, MyBookActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsPaidBooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgPaidStory;
        public TextView txtTitle;
        public TextView txtType;
        Button btnTabFreeBooks;

        public ViewHolder(View itemView) {
            super(itemView);
            imgPaidStory = (ImageView) itemView.findViewById(R.id.imgTabFreeBooks);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitleTabFreeBooks);
            txtType = (TextView) itemView.findViewById(R.id.txtTypeTabFreeBooks);

            btnTabFreeBooks = (Button) itemView.findViewById(R.id.btnTabFreeBooks);
            btnTabFreeBooks.setVisibility(itemView.GONE);
        }
    }
}
