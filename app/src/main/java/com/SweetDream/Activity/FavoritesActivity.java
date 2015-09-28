package com.SweetDream.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.SweetDream.Adapter.FavoritesStoryAdapter;
import com.SweetDream.Model.ItemsBook;
import com.SweetDream.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguye_000 on 28/09/2015.
 */
public class FavoritesActivity extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        itemsFreeBooks = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getFavorite();


        FavoritesStoryAdapter adapter = new FavoritesStoryAdapter(this,itemsFreeBooks);

        grid = (GridView)findViewById(R.id.grid_Favorites);
        grid.setAdapter(adapter);

       /* RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view_favorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        FavoriteBooksAdapter adapter = new FavoriteBooksAdapter();
        recyclerView.setAdapter(adapter);*/
    }
    void getFavorite(){
        for (int i: image){
        itemsFreeBooks.add(new ItemsBook("Title","Author",i));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
