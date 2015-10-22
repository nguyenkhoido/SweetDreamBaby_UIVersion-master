package com.SweetDream.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.Toast;

import com.SweetDream.Extends.LoadImageAudioParse;
import com.SweetDream.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;


import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by nguye_000 on 07/10/2015.
 */
public class PlayingPage extends AppCompatActivity implements MediaPlayer.OnCompletionListener{
    ImageButton btnBackActivity,btnRandom,btnBack,btnPrevious,btnPause,btnPlay, btnForward, btnNext,btnLoop;
    ImageView storyImageView;
    TextView tvDescription;

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.playing_page);

        Intent intent = getIntent();
        String objectId = intent.getStringExtra("objectId");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Story");
        query.whereEqualTo("objectId", objectId);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (parseObject != null) {
                    // GET DETAIL STORY FROM PARSE
                    String storyName = parseObject.getString("StoryName");

                    ParseFile imageFile = parseObject.getParseFile("Image");
                    //String storyImage = imageFile.getUrl();
                    //Toast.makeText(PlayingPage.this, ""+storyImage,Toast.LENGTH_LONG).show();
                    String storyAuthor = parseObject.getString("Author");

                    String storyDescription = parseObject.getString("Description");

                    // GET FILE MP3 RESOUCE FROM PARSE
                    ParseFile song = parseObject.getParseFile("Source");
                    String audiofile = song.getUrl();

                    // PERFORM PLAY AND LOAD DETAIL STORY
                    PlayMedia(storyName, imageFile, storyAuthor, storyDescription, audiofile);

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

    public void PlayMedia(String storyName, ParseFile storyImage, String storyAuthor, String storyDescription,String audioFile){
        // CREATE A MEDIA PLAYER
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // TRY TO LOAD DATA AND PLAY
        try {

            // GIVE DATA TO MEDIAPLAYER
            mediaPlayer.setDataSource(audioFile);
            // MEDIA PLAYER ASYNCHRONOUS PREPARATION
            mediaPlayer.prepareAsync();

            // CREATE A PROGRESS DIALOG (WAITING MEDIA PLAYER PREPARATION)
            final ProgressDialog dialog = new ProgressDialog(PlayingPage.this);

            // SET MESSAGE OF THE DIALOG
            dialog.setMessage("Loading Mp3");

            // PREVENT DIALOG TO BE CANCELED BY BACK BUTTON PRESS
            dialog.setCancelable(false);

            // SHOW DIALOG AT THE BOTTOM
            dialog.getWindow().setGravity(Gravity.CENTER);

            // SHOW DIALOG
            dialog.show();


            // INFLATE LAYOUT
            setContentView(R.layout.playing_page);

            // DISPLAY TITLE
            ((TextView)findViewById(R.id.tvNowPlaying)).setText(storyName+" - "+storyAuthor);
            btnPlay = (ImageButton) findViewById(R.id.btnPlay);

            /// LOAD COVER IMAGE
            // GET IMAGE VIEW
            storyImageView = (ImageView) findViewById(R.id.storyImage);

            // Image url
            LoadImageAudioParse loadImageAudioParse = new LoadImageAudioParse();
            loadImageAudioParse.loadImages(storyImage,storyImageView);

            // GET DESCRIPTION
            tvDescription =(TextView) findViewById(R.id.tvStoryDescription);
            tvDescription.setText(storyDescription);


            // EXECUTE THIS CODE AT THE END OF ASYNCHRONOUS MEDIA PLAYER PREPARATION
            mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                public void onPrepared(final MediaPlayer mp) {


                    //START MEDIA PLAYER
                    mp.start();

                    // LINK SEEKBAR TO BAR VIEW
                    seekBar = (SeekBar) findViewById(R.id.songProgressBar);

                    //UPDATE SEEKBAR
                    mRunnable.run();

                    //DISMISS DIALOG
                    dialog.dismiss();
                }
            });

            mediaPlayer.setOnCompletionListener(this);

        } catch (IOException e) {
            Activity a = this;
            a.finish();
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
        }

    }

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            if(mediaPlayer != null) {

                //SET MAX VALUE
                int mDuration = mediaPlayer.getDuration();
                seekBar.setMax(mDuration);

                //UPDATE TOTAL TIME TEXT VIEW
                TextView totalTime = (TextView) findViewById(R.id.songTotalDurationLabel);
                totalTime.setText(getTimeString(mDuration));

                //SET PROGRESS TO CURRENT POSITION
                int mCurrentPosition = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(mCurrentPosition);

                //UPDATE CURRENT TIME TEXT VIEW
                TextView currentTime = (TextView) findViewById(R.id.songCurrentDurationLabel);
                currentTime.setText(getTimeString(mCurrentPosition));

                //handle drag on seekbar
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        //btnPlay.setImageResource(R.drawable.btn_pause);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        //btnPlay.setImageResource(R.drawable.btn_play);
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if(mediaPlayer != null && fromUser){
                            mediaPlayer.seekTo(progress);
                        }
                    }


                });


            }


            //repeat above code every second
            mHandler.postDelayed(this, 10);
        }


    };

    public void play(View view){
// check for already playing
        if(mediaPlayer.isPlaying()){
            if(mediaPlayer!=null){
                mediaPlayer.pause();
                // Changing button image to play button
                btnPlay.setImageResource(R.drawable.btn_play);
            }
        }else{
            // Resume song
            if(mediaPlayer!=null){
                mediaPlayer.start();
                // Changing button image to pause button
                btnPlay.setImageResource(R.drawable.btn_pause);
            }
        }

    }


    public void pause(View view){

        mediaPlayer.pause();

    }

    public void stop(View view){

        mediaPlayer.seekTo(0);
        mediaPlayer.pause();

    }


    public void seekForward(View view){

        //set seek time
        int seekForwardTime = 5000;

        // get current song position
        int currentPosition = mediaPlayer.getCurrentPosition();
        // check if seekForward time is lesser than song duration
        if(currentPosition + seekForwardTime <= mediaPlayer.getDuration()){
            // forward song
            mediaPlayer.seekTo(currentPosition + seekForwardTime);
        }else{
            // forward to end position
            mediaPlayer.seekTo(mediaPlayer.getDuration());
        }

    }

    public void seekBackward(View view){

        //set seek time
        int seekBackwardTime = 5000;

        // get current song position
        int currentPosition = mediaPlayer.getCurrentPosition();
        // check if seekBackward time is greater than 0 sec
        if(currentPosition - seekBackwardTime >= 0){
            // forward song
            mediaPlayer.seekTo(currentPosition - seekBackwardTime);
        }else{
            // backward to starting position
            mediaPlayer.seekTo(0);
        }

    }




    public void onBackPressed(){
        super.onBackPressed();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        finish();
    }

    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        long hours = millis / (1000*60*60);
        long minutes = ( millis % (1000*60*60) ) / (1000*60);
        long seconds = ( ( millis % (1000*60*60) ) % (1000*60) ) / 1000;

        buf
                .append(String.format("%02d", hours))
                .append(":")
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        btnPlay.setImageResource(R.drawable.btn_play);
    }
}
