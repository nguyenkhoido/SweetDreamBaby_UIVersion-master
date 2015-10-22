package com.SweetDream.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.SweetDream.R;

/**
 * Created by nguye_000 on 22/10/2015.
 */
public class FragmentAbout extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.about_fragment,container,false);
        ScrollView scrV = (ScrollView) view.findViewById(R.id.scrViewAbout);
        scrV.setVerticalScrollBarEnabled(false);
        return view;
    }
}
