package com.SweetDream.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.SweetDream.Adapter.MyBooksAdapter;
import com.SweetDream.R;

/**
 * Created by nguye_000 on 25/09/2015.
 */
public class MyBookActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view_myBooksList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        MyBooksAdapter myBooksAdapter = new MyBooksAdapter();
        recyclerView.setAdapter(myBooksAdapter);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.action_settings_mybook:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
