package com.SweetDream.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.SweetDream.Audio.Utilities;
import com.SweetDream.Data.StoryList;
import com.SweetDream.Extends.LoadImageAudioParse;
import com.SweetDream.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
import java.util.List;
import java.util.Random;


public class PlayingPage extends AppCompatActivity implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {
    private ImageButton btnPlay;
    private ImageButton btnForward;
    private ImageButton btnBackward;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private ImageButton btnPlaylist;
    private ImageButton btnRepeat;
    private ImageButton btnTime;
    private ImageButton btnShuffle;
    private SeekBar songProgressBar;

    private Button btnDg5, btnDg1, btnAdd5, btnAdd1;

    private TextView btnSpeed;
    private TextView songTitleLabel;
    private TextView songDescription;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    private TextView timeView;
    private ImageView storyImage;
    // Media Player
    private MediaPlayer mp;
    // Handler to update UI timer, progress bar etc,.
    private Handler mHandler = new Handler();
    ;
    private StoryList songManager;
    private Utilities utils;
    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
    private int currentSongIndex = 0;
    private boolean isShuffle = false;
    private boolean isRepeat = false;

    private List<ParseObject> songsList;


    String result, objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playing_page);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //objectId = bundle.getString("objectId");
        currentSongIndex = bundle.getInt("currentStory");
        objectId = bundle.getString("objectId");
        result = bundle.getString("result");


        Toast.makeText(getApplicationContext(), "" + currentSongIndex, Toast.LENGTH_LONG).show();
        //playSong(0);

        // All player buttons
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
        btnForward = (ImageButton) findViewById(R.id.btnForward);
        btnBackward = (ImageButton) findViewById(R.id.btnBackward);
        btnTime = (ImageButton) findViewById(R.id.btnTime);
        //btnPlaylist = (ImageButton) findViewById(R.id.btnPlaylist);

        btnSpeed = (TextView) findViewById(R.id.btnSpeed);
        btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
        btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
        songTitleLabel = (TextView) findViewById(R.id.tvNowPlaying);
        //songDescription = (TextView) findViewById(R.id.tvStoryDescription);
        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);

        //storyImage = (ImageView) findViewById(R.id.storyImage);

        ImageButton imgBtnBackActivity = (ImageButton) findViewById(R.id.imgBtnBackActivity);
        imgBtnBackActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // Mediaplayer
        mp = new MediaPlayer();
        songManager = new StoryList();
        utils = new Utilities();

        // Listeners
        songProgressBar.setOnSeekBarChangeListener(this); // Important
        mp.setOnCompletionListener(this); // Important

        // Getting all songs list
        if (result.equals("free")) {
            songsList = songManager.getFreeStory();
            String url = songsList.get(currentSongIndex).getParseFile("Source").getUrl();
            //Toast.makeText(getApplicationContext(),""+url,Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
            // By default play first song
            playSong(currentSongIndex);
        }
        if (result.equals("paid")) {

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
                        playSong(storyName, imageFile, storyAuthor, storyDescription, audiofile);


                    } else {
                        Toast.makeText(PlayingPage.this, "Data load fail", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


        /**
         * Play button click event
         * plays a song and changes button to pause image
         * pauses a song and changes button to play image
         * */
        btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // check for already playing
                if (mp.isPlaying()) {
                    if (mp != null) {
                        mp.pause();
                        // Changing button image to play button
                        btnPlay.setImageResource(R.drawable.btn_play);
                    }
                } else {
                    // Resume song
                    if (mp != null) {
                        mp.start();
                        // Changing button image to pause button
                        btnPlay.setImageResource(R.drawable.btn_pause);
                    }
                }

            }
        });

        /**
         * Forward button click event
         * Forwards song specified seconds
         * */
        btnForward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // get current song position
                int currentPosition = mp.getCurrentPosition();
                // check if seekForward time is lesser than song duration
                if (currentPosition + seekForwardTime <= mp.getDuration()) {
                    // forward song
                    mp.seekTo(currentPosition + seekForwardTime);
                } else {
                    // forward to end position
                    mp.seekTo(mp.getDuration());
                }
            }
        });

        /**
         * Backward button click event
         * Backward song to specified seconds
         **/
        btnBackward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // get current song position
                int currentPosition = mp.getCurrentPosition();
                // check if seekBackward time is greater than 0 sec
                if (currentPosition - seekBackwardTime >= 0) {
                    // forward song
                    mp.seekTo(currentPosition - seekBackwardTime);
                } else {
                    // backward to starting position
                    mp.seekTo(0);
                }

            }
        });

        /**
         * Next button click event
         * Plays next song by taking currentSongIndex + 1
         * */
        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // check if next song is there or not
                if (currentSongIndex < (songsList.size() - 1)) {
                    playSong(currentSongIndex + 1);
                    currentSongIndex = currentSongIndex + 1;
                } else {
                    // play first song
                    playSong(0);
                    currentSongIndex = 0;
                }

            }
        });

        /**
         * Back button click event
         * Plays previous song by currentSongIndex - 1
         * */
        btnPrevious.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (currentSongIndex > 0) {
                    playSong(currentSongIndex - 1);
                    currentSongIndex = currentSongIndex - 1;
                } else {
                    // play last song
                    playSong(songsList.size() - 1);
                    currentSongIndex = songsList.size() - 1;
                }

            }
        });

        /**
         * Button Click event for Repeat button
         * Enables repeat flag to true
         * */
        btnRepeat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (isRepeat) {
                    isRepeat = false;
                    Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
                    btnRepeat.setImageResource(R.drawable.btn_repeat);
                } else {
                    // make repeat to true
                    isRepeat = true;
                    Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isShuffle = false;
                    btnRepeat.setImageResource(R.drawable.btn_repeat_focused);
                    btnShuffle.setImageResource(R.drawable.btn_shuffle);
                }
            }
        });

        /**
         * Button Click event for Shuffle button
         * Enables shuffle flag to true
         * */
        btnShuffle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (isShuffle) {
                    isShuffle = false;
                    Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
                    btnShuffle.setImageResource(R.drawable.btn_shuffle);
                } else {
                    // make repeat to true
                    isShuffle = true;
                    Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isRepeat = false;
                    btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
                    btnRepeat.setImageResource(R.drawable.btn_repeat);
                }
            }
        });

	/*	*
         * Button Click event for Play list click event
		 * Launches list activity which displays list of songs
		 * */
        /*btnPlaylist.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(), PlayListActivity.class);
				startActivityForResult(i, 100);
			}
		});*/
        btnSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence[] time = {"0x", "1x", "2x", "4x"};
                showDialog(PlayingPage.this, "Time Speed", time);

            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sleepTimesDialog(PlayingPage.this, "Sleep Time", "Ok", "Cancel");

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    /**
     * Receiving song index from playlist view
     * and play the song
     */
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            currentSongIndex = data.getExtras().getInt("songIndex");
            // play selected song
            playSong(currentSongIndex);
        }

    }

    /**
     * Function to play a song
     *
     * @param songIndex - index of song
     */
    public void playSong(int songIndex) {
        // Play song
        try {
            mp.reset();
            //mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setDataSource(songsList.get(songIndex).getParseFile("Source").getUrl());
            mp.prepare();
            mp.start();


            // Displaying Song title
            String songTitle = songsList.get(songIndex).getString("StoryName");
            String songAuthor = songsList.get(songIndex).getString("Author");
            songTitleLabel.setText(songTitle + " - " + songAuthor);

            // Displaying Image Song

           /* ParseFile image = songsList.get(songIndex).getParseFile("Image");
            LoadImageAudioParse loadImageAudioParse = new LoadImageAudioParse();
            loadImageAudioParse.loadImages(image, storyImage);*/

            // Displaying Song Description

          /*  String description = songsList.get(songIndex).getString("Description");
            songDescription.setText(description);*/

            // Changing Button Image to pause image
            btnPlay.setImageResource(R.drawable.btn_pause);

            // set Progress bar values
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);

            // Updating progress bar
            updateProgressBar();


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playSong(String storyName, ParseFile imageFile, String storyAuthor, String storyDescription, String sourceFile) {
        // Play song
        try {
            btnNext.setClickable(false);
            btnPrevious.setClickable(false);
            btnShuffle.setClickable(false);
            btnRepeat.setClickable(false);
            isRepeat = true;
            mp.reset();
            //mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setDataSource(sourceFile);
            mp.prepare();
            mp.start();


            // Displaying Song title
            songTitleLabel.setText(storyName + " - " + storyAuthor);

            // Displaying Image Song

            /*LoadImageAudioParse loadImageAudioParse = new LoadImageAudioParse();
            loadImageAudioParse.loadImages(imageFile, storyImage);*/

            // Displaying Song Description
            // songDescription.setText(storyDescription);

            // Changing Button Image to pause image
            btnPlay.setImageResource(R.drawable.btn_pause);

            // set Progress bar values
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);

            // Updating progress bar
            updateProgressBar();


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update timer on seekbar
     */
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if (mp != null) {

                //SET MAX VALUE
                int mDuration = mp.getDuration();
                songProgressBar.setMax(mDuration);

                //UPDATE TOTAL TIME TEXT VIEW
                TextView totalTime = (TextView) findViewById(R.id.songTotalDurationLabel);
                totalTime.setText(utils.milliSecondsToTimer(mDuration));

                //SET PROGRESS TO CURRENT POSITION
                int mCurrentPosition = mp.getCurrentPosition();
                songProgressBar.setProgress(mCurrentPosition);

                //UPDATE CURRENT TIME TEXT VIEW
                TextView currentTime = (TextView) findViewById(R.id.songCurrentDurationLabel);
                currentTime.setText(utils.milliSecondsToTimer(mCurrentPosition));


            }


            //repeat above code every second
            mHandler.postDelayed(this, 10);
        }
    };

    /**
     *
     * */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        if (mp != null && fromTouch) {
            mp.seekTo(progress);
        }
    }

    /**
     * When user starts moving the progress handler
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // remove message Handler from updating progress bar
        //mHandler.removeCallbacks(mUpdateTimeTask);
    }

    /**
     * When user stops moving the progress hanlder
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        /*mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mp.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        mp.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();*/
    }

    /**
     * On Song Playing completed
     * if repeat is ON play same song again
     * if shuffle is ON play random song
     */
    @Override
    public void onCompletion(MediaPlayer arg0) {

        // check for repeat is ON or OFF
        if (isRepeat) {
            // repeat is on play same song again
            playSong(currentSongIndex);
        } else if (isShuffle) {
            // shuffle is on - play a random song
            Random rand = new Random();
            currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
            playSong(currentSongIndex);
        } else {
            // no repeat or shuffle ON - play next song
            if (currentSongIndex < (songsList.size() - 1)) {
                playSong(currentSongIndex + 1);
                currentSongIndex = currentSongIndex + 1;
            } else {
                // play first song
                playSong(0);
                currentSongIndex = 0;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.release();
    }


    //alert dialog for downloadDialog
    public AlertDialog showDialog(final PlayingPage activity, CharSequence title, CharSequence[] time) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(activity);
        downloadDialog.setTitle(title);

        downloadDialog.setItems(time, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Toast.makeText(PlayingPage.this, "0x", Toast.LENGTH_SHORT).show();
                        // get current song position
                        int currentPosition = mp.getCurrentPosition();

                        btnSpeed.setText("0x");


                        // check if seekBackward time is greater than 0 sec
                        /*if(currentPosition - seekBackwardTime >= 0){
                            // forward song
                            mp.seekTo(currentPosition - seekBackwardTime);
                        }else{
                            // backward to starting position
                            mp.seekTo(0);
                        }*/
                        break;
                    case 1:
                        Toast.makeText(PlayingPage.this, "1x", Toast.LENGTH_SHORT).show();

                        btnSpeed.setText("1x");


                        break;
                    case 2:
                        Toast.makeText(PlayingPage.this, "2x", Toast.LENGTH_SHORT).show();

                        btnSpeed.setText("2x");


                        break;
                    case 3:
                        Toast.makeText(PlayingPage.this, "4x", Toast.LENGTH_SHORT).show();

                        btnSpeed.setText("4x");


                        break;
                    default:
                        Toast.makeText(PlayingPage.this, "0x", Toast.LENGTH_SHORT).show();
                        break;
                }
                dialog.dismiss();
            }
        });

        return downloadDialog.show();
    }

    private int currentTime = 30;
    public AlertDialog sleepTimesDialog(final PlayingPage activity, CharSequence title, CharSequence nativeBtn, CharSequence negativeBtn) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(activity);
        LayoutInflater inflater = PlayingPage.this.getLayoutInflater();

        View view = inflater.inflate(R.layout.custom_dialog_sleep_time, null);
        downloadDialog
                .setView(view);
        //setView Dialog
        //downloadDialog.setView(R.layout.custom_dialog_sleep_time);
        //call Id in custom dialog layout

        btnDg5 = (Button) view.findViewById(R.id.btnDg5Min);
        btnDg1 = (Button) view.findViewById(R.id.btnDg1Min);
        btnAdd5 = (Button) view.findViewById(R.id.btnAg5Min);
        btnAdd1 = (Button) view.findViewById(R.id.btnAg1Min);
        timeView = (TextView) view.findViewById(R.id.txtViewTime);

        timeView.setText(""+currentTime);

        btnDg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sleepT = timeView.getText().toString();
                int sleepTime = Integer.parseInt(sleepT);

                currentTime = sleepTime - 5;

                if(currentTime <= 0){
                    timeView.setText("" + 0);
                }
                else{
                    timeView.setText("" + currentTime);
                }
            }
        });
        btnDg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sleepT = timeView.getText().toString();
                int sleepTime = Integer.parseInt(sleepT);


                currentTime = sleepTime - 1;


                if(currentTime <= 0){
                    timeView.setText("" + 0);
                }
                else{
                    timeView.setText("" + currentTime);
                }
            }
        });
        btnAdd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sleepT = timeView.getText().toString();
                int sleepTime = Integer.parseInt(sleepT);

                currentTime = sleepTime + 5;

                timeView.setText("" + currentTime);
            }
        });
        btnAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sleepT = timeView.getText().toString();
                int sleepTime = Integer.parseInt(sleepT);


                currentTime = sleepTime + 1;

                timeView.setText("" + currentTime);
            }
        });
        downloadDialog.setTitle(title);
        downloadDialog.setPositiveButton(nativeBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int timeset = Integer.valueOf(timeView.getText().toString()) * 1000 *60;
                long time =Long.valueOf(timeset);
                new CountDownTimer(time , 1000) {

                    public void onTick(long millisUntilFinished) {
                        //timeView.setText("" + millisUntilFinished/60000);
                        Log.e("Time: ", "" + millisUntilFinished/60000);
                    }

                    public void onFinish() {
                        timeView.setText("" + 0);
                        if (mp.isPlaying()) {
                            if (mp != null) {
                                mp.pause();
                                // Changing button image to play button
                                btnPlay.setImageResource(R.drawable.btn_play);
                            }
                        } else {
                            // Resume song
                            if (mp != null) {
                                mp.start();
                                // Changing button image to pause button
                                btnPlay.setImageResource(R.drawable.btn_pause);
                            }
                        }
                    }
                }.start();

            }
        });
        downloadDialog.setNegativeButton(negativeBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        return downloadDialog.show();
    }


}
