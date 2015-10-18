package com.SweetDream.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.SweetDream.Audio.SongsManager;
import com.SweetDream.Audio.Utilities;
import com.SweetDream.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nguye_000 on 07/10/2015.
 */
public class PlayingPage extends AppCompatActivity {
   private ImageButton btnBackActivity,btnRandom,btnBack,btnPrevious,btnPause,btnPlay,btnForward,btnNext,btnLoop;
    private SeekBar songProgressBar;
    private TextView songTitleLabel ,songCurrentDurationLabel,songTotalDurationLabel;
    private MediaPlayer mp;
    private Handler mHandler = new Handler();;
    private SongsManager songManager;
    private Utilities utils;
    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
    private int currentSongIndex = 0;
    private boolean isShuffle = false;
    private boolean isRepeat = false;
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playing_page);

        SharedPreferences myprefs= getSharedPreferences("user", MODE_WORLD_READABLE);
        String session_id= myprefs.getString("session_id", null);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Story");
        query.whereEqualTo("objectId", session_id);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (parseObject != null) {
                    Toast.makeText(PlayingPage.this,"Name Song is: " + parseObject.getString("LinkSong"), Toast.LENGTH_LONG).show();
                    //ParseFile file = parseObject.getParseFile("Image");

                } else {
                    Toast.makeText(PlayingPage.this, "Data load fail", Toast.LENGTH_LONG).show();
                }
            }
        });
        //Toast.makeText(this, "Data load fail" + session_id, Toast.LENGTH_LONG).show();
        // final ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.times));
      /*  btnBackActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/



    }
    public void onBackPressed() {


        Intent myIntent = new Intent(PlayingPage.this, MainActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
        startActivity(myIntent);
        finish();
        return;
    }
}
