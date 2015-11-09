package com.SweetDream.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Parcelable;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class StoryDetails extends SuperActivity {
    String test = "";
    Button btnPlay, btnDownload, btnGetStory;
    ImageButton imgBtnFavorites, imgBtnShare;
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

//Get ID Widget in layout
        btnGetStory = (Button) findViewById(R.id.btnGetStory);
        btnDownload = (Button) findViewById(R.id.btnDownload);
        imgBtnFavorites = (ImageButton) findViewById(R.id.imgBtnFavorites);
        imgBtnShare = (ImageButton) findViewById(R.id.imgBtnShare);
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
        if(result.equals("free")){
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

                            btnGetStory.setVisibility(View.GONE);
                            btnPlay.setVisibility(View.VISIBLE);
                            btnDownload.setVisibility(View.VISIBLE);
                            tvPrice.setVisibility(View.GONE);


                    } else {
                        Toast.makeText(StoryDetails.this, "Data load fail", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        if(result.equals("paid")){
            query.whereEqualTo("objectId", objectId);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    if (parseObject != null) {
                        //Toast.makeText(StoryDetails.this, parseObject.getString("StoryName"),Toast.LENGTH_LONG).show();
                        ParseFile file = parseObject.getParseFile("Image");
                        tvStoryName.setText(parseObject.getString("StoryName"));
                        tvAuthor.setText(parseObject.getString("Author"));
                        tvPrice.setText("Price: "+parseObject.getNumber("Price").toString());
                        tvSummariesContent.setText(parseObject.getString("Description"));
                        load.loadImages(file, storyImage);
                        test = parseObject.getString("Author");

                        btnGetStory.setVisibility(View.VISIBLE);
                        btnPlay.setVisibility(View.GONE);
                        btnDownload.setVisibility(View.GONE);
                        tvPrice.setVisibility(View.VISIBLE);


                    } else {
                        Toast.makeText(StoryDetails.this, "Data load fail", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


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

        imgBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareContent();
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
        checkIsBuy();
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

    private void addRelationShipIsBuy() {
        // Create the story that user love
        ParseObject story = ParseObject.createWithoutData("Story", objectId);

        // Get current User
        ParseUser user = ParseUser.getCurrentUser();

        // Create relationship collumn UserLove
        ParseRelation relation = user.getRelation("StoryPaid");

        // Add story to Relation
        relation.add(story);

        // user save relation
        user.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {
                    //Toast.makeText(StoryDetails.this, "User Buy this story, go to Your Book to see!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error saving: " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }

        });

    }
    private void checkIsBuy() {
        // Get current User
        ParseUser user = ParseUser.getCurrentUser();
        if (user != null) {
            // create a relation based on the authors key
            ParseRelation relation = user.getRelation("StoryPaid");

            // generate a query based on that relation
            ParseQuery query = relation.getQuery();
            query.whereEqualTo("objectId", objectId);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    if (parseObject != null) {
                        btnGetStory.setVisibility(View.GONE);
                        btnPlay.setVisibility(View.VISIBLE);
                        btnDownload.setVisibility(View.VISIBLE);
                        tvPrice.setText("You Got this Story go to , your book to see !!");
                    } /*else {

                        btnGetStory.setVisibility(View.VISIBLE);
                        btnPlay.setVisibility(View.GONE);
                        btnDownload.setVisibility(View.GONE);

                    }*/
                }

            });
        }

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
        downloadDialog.setView(view);
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
                    currentUser.put("Coin", "" + Coin);
                    addRelationShipIsBuy();
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                Toast.makeText(StoryDetails.this, "Paid Sucessfully", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(StoryDetails.this, "" + e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    btnGetStory.setVisibility(View.GONE);
                    btnPlay.setVisibility(View.VISIBLE);
                    btnDownload.setVisibility(View.VISIBLE);
                    tvPrice.setText("You Got this Story go to , your book to see !!");
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

    String[] nameOfAppsToShareWith = new String[]{"facebook", "twitter", "gmail"};
    String[] blacklist = new String[]{"com.any.package", "net.other.package"};
    private void shareContent() {
        // your share intent
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Your feeling about this application");
        intent.putExtra(Intent.EXTRA_SUBJECT, "quangminhlk1994@gmail.com");
        // ... anything else you want to add invoke custom chooser
        startActivity(generateCustomChooserIntent(intent, blacklist));
    }

    private Intent generateCustomChooserIntent(Intent prototype,
                                               String[] forbiddenChoices) {
        List<Intent> targetedShareIntents = new ArrayList<>();
        List<HashMap<String, String>> intentMetaInfo = new ArrayList<HashMap<String, String>>();
        Intent chooserIntent;

        Intent dummy = new Intent(prototype.getAction());
        dummy.setType(prototype.getType());
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(dummy, 0);

        if (!resInfo.isEmpty()) {
            for (ResolveInfo resolveInfo : resInfo) {
                if (resolveInfo.activityInfo == null
                        || Arrays.asList(forbiddenChoices).contains(
                        resolveInfo.activityInfo.packageName))
                    continue;
                //Get all the posible sharers
                HashMap<String, String> info = new HashMap<String, String>();
                info.put("packageName", resolveInfo.activityInfo.packageName);
                info.put("className", resolveInfo.activityInfo.name);
                String appName = String.valueOf(resolveInfo.activityInfo
                        .loadLabel(getPackageManager()));
                info.put("simpleName", appName);
                //Add only what we want
                if (Arrays.asList(nameOfAppsToShareWith).contains(
                        appName.toLowerCase())) {
                    intentMetaInfo.add(info);
                }
            }

            if (!intentMetaInfo.isEmpty()) {
                // sorting for nice readability
                Collections.sort(intentMetaInfo,
                        new Comparator<HashMap<String, String>>() {
                            @Override
                            public int compare(
                                    HashMap<String, String> map,
                                    HashMap<String, String> map2) {
                                return map.get("simpleName").compareTo(
                                        map2.get("simpleName"));
                            }
                        });

                // create the custom intent list
                for (HashMap<String, String> metaInfo : intentMetaInfo) {
                    Intent targetedShareIntent = (Intent) prototype.clone();
                    targetedShareIntent.setPackage(metaInfo.get("packageName"));
                    targetedShareIntent.setClassName(
                            metaInfo.get("packageName"),
                            metaInfo.get("className"));
                    targetedShareIntents.add(targetedShareIntent);
                }
                String shareVia = getString(R.string.offer_share_via);
                String shareTitle = shareVia.substring(0, 1).toUpperCase()
                        + shareVia.substring(1);
                chooserIntent = Intent.createChooser(targetedShareIntents
                        .remove(targetedShareIntents.size() - 1), shareTitle);
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                        targetedShareIntents.toArray(new Parcelable[]{}));
                return chooserIntent;
            }
        }

        return Intent.createChooser(prototype,
                getString(R.string.offer_share_via));
    }
}
