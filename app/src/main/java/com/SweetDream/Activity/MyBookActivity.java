package com.SweetDream.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.SweetDream.Adapter.MyBooksAdapter;
import com.SweetDream.R;

/**
 * Created by nguye_000 on 25/09/2015.
 */
public class MyBookActivity extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_books, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_myBooksList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyBooksAdapter myBooksAdapter = new MyBooksAdapter();
        recyclerView.setAdapter(myBooksAdapter);
        return view;
    }

}
