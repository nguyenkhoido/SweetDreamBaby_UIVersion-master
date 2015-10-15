package com.SweetDream.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.SweetDream.R;

/**
 * Created by nguye_000 on 07/10/2015.
 */
public class PlayingPage extends AppCompatActivity {
    ImageButton btnBackActivity,btnRandom,btnBack,btnPrevious,btnPause,btnPlay,btnForward,btnNext,btnLoop;
    //Button btnTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playing_page);


        btnBackActivity = (ImageButton)findViewById(R.id.imgBtnBackActivity);
        btnRandom = (ImageButton) findViewById(R.id.btnRandom);
        btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
        btnBack = (ImageButton) findViewById(R.id.btnBackward);
        btnPause = (ImageButton) findViewById(R.id.btnPause);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnForward = (ImageButton) findViewById(R.id.btnForward);
        btnLoop = (ImageButton) findViewById(R.id.btnLoop);

       // final ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.times));
        btnBackActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }
    public void onBackPressed() {


        Intent myIntent = new Intent(PlayingPage.this, MainActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
        startActivity(myIntent);
        finish();
        return;
    }
}
