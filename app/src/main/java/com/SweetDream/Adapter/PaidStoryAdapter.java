package com.SweetDream.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.SweetDream.Activity.StoryDetails;
import com.SweetDream.Model.ItemPaidStory;
import com.SweetDream.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.util.List;

/**
 * Created by nguye_000 on 25/09/2015.
 */
public class PaidStoryAdapter extends RecyclerView.Adapter<PaidStoryAdapter.ViewHolder> {
    // A list story not real to make background
    List<ItemPaidStory> itemsPaidBooks;

    public PaidStoryAdapter(List<ItemPaidStory> itemsBookList) {
        super();
        // we setup list story to use this from MainActivity
        this.itemsPaidBooks = itemsBookList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_cardview_tapstory_adapter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final ItemPaidStory item = itemsPaidBooks.get(i);
        viewHolder.txtTitle.setText(item.getTitleBook());
        viewHolder.txtAuthor.setText(item.getAuthorStory());
        viewHolder.txtPrice.setText(item.getPriceStory().toString()+" Coin");
        //viewHolder.imgPaidStory.setImageResource(item.getImage());
        loadImages(item.getImage(),viewHolder.imgPaidStory);
        viewHolder.imgPaidStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context,StoryDetails.class);
                Bundle bundle = new Bundle();
                bundle.putInt("currentStory", i);
                bundle.putString("objectId", item.getObjectId());
                bundle.putString("result", "paid");
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsPaidBooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgPaidStory;
        public TextView txtTitle,txtAuthor,txtPrice;


        public ViewHolder(View itemView) {
            super(itemView);
            imgPaidStory = (ImageView) itemView.findViewById(R.id.imgTabFreeBooks);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitleTabFreeBooks);
            txtAuthor = (TextView) itemView.findViewById(R.id.txtTypeTabFreeBooks);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPriceStory);
            //btnTabFreeBooks.setVisibility(itemView.GONE);
        }
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
