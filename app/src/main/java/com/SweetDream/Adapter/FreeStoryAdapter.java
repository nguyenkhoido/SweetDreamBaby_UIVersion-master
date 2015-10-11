package com.SweetDream.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.SweetDream.Activity.StoryDetails;
import com.SweetDream.Model.ItemStory;
import com.SweetDream.Model.ItemsBook;
import com.SweetDream.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguye_000 on 25/09/2015.
 */
public class FreeStoryAdapter extends RecyclerView.Adapter<FreeStoryAdapter.ViewHolder> {

    // A list story not real to make background
    List<ItemStory> itemsFreeBooks;

    public FreeStoryAdapter(List<ItemStory> itemsBookList)
    {
        super();
        // we setup list story to use this from MainActivity
        this.itemsFreeBooks = itemsBookList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_cardview_tapstory_adapter,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ItemStory item = itemsFreeBooks.get(i);
        viewHolder.txtTitle.setText(item.getTitleBook());
        viewHolder.txtType.setText(item.getTypeBook());
        // we load image from parse to imageview
        loadImages(item.getImage(),viewHolder.imgFreeStory);

        viewHolder.imgFreeStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                context.startActivity(new Intent(context,StoryDetails.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsFreeBooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgFreeStory;
        public TextView txtTitle;
        public TextView txtType;
        Button btnTabFreeBooks;
        public ViewHolder(View itemView) {
            super(itemView);
            imgFreeStory = (ImageView) itemView.findViewById(R.id.imgTabFreeBooks);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitleTabFreeBooks);
            txtType = (TextView) itemView.findViewById(R.id.txtTypeTabFreeBooks);

            btnTabFreeBooks = (Button)itemView.findViewById(R.id.btnTabFreeBooks);
            btnTabFreeBooks.setVisibility(itemView.GONE);
        }
    }

    // load file from parse and set it to imageview (not real)
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
