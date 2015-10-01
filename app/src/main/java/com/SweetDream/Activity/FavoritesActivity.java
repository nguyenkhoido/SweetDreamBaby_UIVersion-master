package com.SweetDream.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.SweetDream.Adapter.FavoritesStoryAdapter;
import com.SweetDream.Model.ItemsBook;
import com.SweetDream.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguye_000 on 28/09/2015.
 */
public class FavoritesActivity extends Fragment {
    Integer[] image = {
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar


    };
    GridView grid;
    List<ItemsBook> itemsFreeBooks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favorites, container, false);

        itemsFreeBooks = new ArrayList<>();

        getFavorite();


        FavoritesStoryAdapter adapter = new FavoritesStoryAdapter(getActivity(), itemsFreeBooks);

        grid = (GridView) view.findViewById(R.id.grid_Favorites);
        grid.setAdapter(adapter);
        return view;
    }

    void getFavorite() {
        for (int i : image) {
            itemsFreeBooks.add(new ItemsBook("Title", "Author", i));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}