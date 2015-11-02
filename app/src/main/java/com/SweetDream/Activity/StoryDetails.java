package com.SweetDream.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.SweetDream.Extends.LoadImageAudioParse;
import com.SweetDream.Pay.SuperActivity;
import com.SweetDream.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class StoryDetails extends SuperActivity {
    String test = "";
    Button btnPlay, btnDownload, btnGetStory;
    ImageButton imgBtnFavorites;
    ImageView storyImage;
    TextView tvStoryName, tvAuthor, tvSummariesContent, tvPrice, dialogTvTitle, dialogTvPrice, dialogTvUserCoin, dialogTvAfterPaid, dialogTvCheckCoin, txtQuestion;
    LoadImageAudioParse load = new LoadImageAudioParse();
    final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Story");
    int Coin;
    String objectId;
    int currentStory;
    String result;
    AlertDialog.Builder downloadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_page);

        btnGetStory = (Button) findViewById(R.id.btnGetStory);
        btnDownload = (Button) findViewById(R.id.btnDownload);
        imgBtnFavorites = (ImageButton) findViewById(R.id.imgBtnFavorites);
        storyImage = (ImageView) findViewById(R.id.tvStoryImage);
        tvStoryName = (TextView) findViewById(R.id.tvStoryName);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvSummariesContent = (TextView) findViewById(R.id.tvSummariesContent);
        tvPrice = (TextView) findViewById(R.id.tvPrice);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        objectId = bundle.getString("objectId");
        currentStory = bundle.getInt("currentStory");
        result = bundle.getString("result");
        //final String objectId = intent.getStringExtra("objectId");
        //Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_SHORT).show();
        query.whereEqualTo("objectId", objectId);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (parseObject != null) {
                    //Toast.makeText(StoryDetails.this, parseObject.getString("StoryName"),Toast.LENGTH_LONG).show();
                    ParseFile file = parseObject.getParseFile("Image");
                    tvStoryName.setText(parseObject.getString("StoryName"));
                    tvAuthor.setText(parseObject.getString("Author"));
                    tvPrice.setText(parseObject.getNumber("Price").toString());
                    tvSummariesContent.setText(parseObject.getString("Description"));
                    load.loadImages(file, storyImage);
                    test = parseObject.getString("Author");


                    int price = (int) parseObject.getNumber("Price");
                    if (price > 0) {
                        btnGetStory.setVisibility(View.VISIBLE);
                        btnPlay.setVisibility(View.GONE);
                        btnDownload.setVisibility(View.GONE);
                    } else {
                        btnGetStory.setVisibility(View.GONE);
                        btnPlay.setVisibility(View.VISIBLE);
                        btnDownload.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(StoryDetails.this, "Data load fail", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnGetStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser != null) {
                    query.whereEqualTo("objectId", objectId);
                    query.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject parseObject, ParseException e) {

                            String userCoin = currentUser.getString("Coin");
                            String storyCoin = "" + parseObject.getNumber("Price");

                            dialogTvTitle.setText("Story Title: " + parseObject.getString("StoryName"));
                            dialogTvPrice.setText("Story Price: " + storyCoin);
                            dialogTvUserCoin.setText("Your Coin: " + userCoin);
                            Coin = Integer.parseInt(userCoin) - Integer.parseInt(storyCoin);
                            dialogTvAfterPaid.setText("After Pain: " + "" + Coin);


                            if (Coin <= 0) {
                                dialogTvCheckCoin.setVisibility(View.VISIBLE);
                                txtQuestion.setVisibility(View.GONE);

                            }
                        }
                    });
                    payStoryDialog(StoryDetails.this, "Pay Story", "Get Story", "Cancel");

                } else {
                    loadLoginView();
                }

                //Toast.makeText(StoryDetails.this, "You must get this story before play!", Toast.LENGTH_SHORT).show();
            }
        });

//Get StoryObjectId and UserObjectId
        imgBtnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser != null) {
                    imgBtnFavorites.setImageResource(R.drawable.ic_love_active_orange);
                    imgBtnFavorites.setClickable(false);
                    addRelationShip();
                    //getActivity().setResult(Activity.RESULT_OK);
                    //getActivity().finish();
                } else {
                    loadLoginView();
                }
            }
        });


        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(StoryDetails.this, PlayingPage.class);
                Bundle bundle = new Bundle();
                bundle.putString("objectId", objectId);
                bundle.putInt("currentStory", currentStory);
                bundle.putString("result", result);
                playIntent.putExtras(bundle);

                startActivity(playIntent);
            }
        });

        checkLove();
    }

    private void addRelationShip() {
        // Create the story that user love
        ParseObject story = ParseObject.createWithoutData("Story", objectId);

        // Get current User
        ParseUser user = ParseUser.getCurrentUser();

        // Create relationship collumn UserLove
        ParseRelation relation = user.getRelation("StoryLove");

        // Add story to Relation
        relation.add(story);

        // user save relation
        user.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(StoryDetails.this, "User like this story, go to YourFavorite to see!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error saving: " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }

        });


        ParseObject post = new ParseObject("UserStory");
        // Create an LoveStory relationship with the current user

        ParseRelation<ParseUser> relation1 = post.getRelation("UserLove");
        ParseRelation<ParseObject> relation2 = post.getRelation("StoryLove");
        relation1.add(user);
        relation2.add(story);
        // Save the post and return
        post.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(StoryDetails.this, "You like this story, go to YourFavorite to see!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error saving: " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }

        });

    }


    private void checkLove() {
        // Get current User
        ParseUser user = ParseUser.getCurrentUser();
        if (user != null) {
            // create a relation based on the authors key
            ParseRelation relation = user.getRelation("StoryLove");

            // generate a query based on that relation
            ParseQuery query = relation.getQuery();
            query.whereEqualTo("objectId", objectId);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    if (parseObject != null) {
                        imgBtnFavorites.setImageResource(R.drawable.ic_love_active_orange);
                        imgBtnFavorites.setClickable(false);
                        Toast.makeText(StoryDetails.this, "Like", Toast.LENGTH_LONG).show();
                    } else {
                        imgBtnFavorites.setImageResource(R.drawable.ic_love_disable_orange);
                        //Toast.makeText(StoryDetails.this, "Dislike", Toast.LENGTH_LONG).show();
                    }
                }

            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_story_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadLoginView() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        //finish();
    }

    public AlertDialog payStoryDialog(final StoryDetails activity, CharSequence title, CharSequence nativeBtn, CharSequence negativeBtn) {
        downloadDialog = new AlertDialog.Builder(activity);
        LayoutInflater inflater = StoryDetails.this.getLayoutInflater();

        View view = inflater.inflate(R.layout.custom_pay_dialog_layout, null);
        dialogTvTitle = (TextView) view.findViewById(R.id.storyTitle);
        dialogTvPrice = (TextView) view.findViewById(R.id.storyPrice);
        dialogTvUserCoin = (TextView) view.findViewById(R.id.userCoin);
        dialogTvAfterPaid = (TextView) view.findViewById(R.id.afterPaid);
        dialogTvCheckCoin = (TextView) view.findViewById(R.id.txtCheckCoin);
        txtQuestion = (TextView) view.findViewById(R.id.txtQuestion);
        downloadDialog
                .setView(view);
        //setView Dialog
        //downloadDialog.setView(R.layout.custom_dialog_sleep_time);
        //call Id in custom dialog layout

        downloadDialog.setTitle(title);

        downloadDialog.setPositiveButton(nativeBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Coin <= 0) {

                    Intent intent = new Intent(StoryDetails.this, MyProfileActivity.class);
                    startActivity(intent);
                } else {
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    currentUser.put("Coin",""+Coin);
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e != null){
                                Toast.makeText(StoryDetails.this,"Paid Sucessfully",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(StoryDetails.this,""+e,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    btnGetStory.setVisibility(View.GONE);
                    btnPlay.setVisibility(View.VISIBLE);
                    btnDownload.setVisibility(View.VISIBLE);
                }
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
