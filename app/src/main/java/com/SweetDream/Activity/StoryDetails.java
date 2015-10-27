package com.SweetDream.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.SweetDream.Extends.LoadImageAudioParse;
import com.SweetDream.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class StoryDetails extends AppCompatActivity {
    String test = "";
    Button btnPlay, btnDownload;
    ImageButton imgBtnFavorites;
    ImageView storyImage;
    TextView tvStoryName, tvAuthor;
    LoadImageAudioParse load = new LoadImageAudioParse();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_page);
        imgBtnFavorites = (ImageButton) findViewById(R.id.imgBtnFavorites);
        storyImage = (ImageView) findViewById(R.id.tvStoryImage);
        tvStoryName = (TextView) findViewById(R.id.tvStoryName);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);

        Intent intent = getIntent();
        final String objectId = intent.getStringExtra("objectId");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Story");
        query.whereEqualTo("objectId", objectId);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (parseObject != null) {
                    //Toast.makeText(StoryDetails.this, parseObject.getString("StoryName"),Toast.LENGTH_LONG).show();
                    ParseFile file = parseObject.getParseFile("Image");
                    tvStoryName.setText(parseObject.getString("StoryName"));
                    tvAuthor.setText(parseObject.getString("Author"));
                    load.loadImages(file, storyImage);
                    test = parseObject.getString("Author");
                } else {
                    Toast.makeText(StoryDetails.this, "Data load fail", Toast.LENGTH_LONG).show();
                }
            }
        });
//Get StoryObjectId and UserObjectId
        imgBtnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser != null) {
                    // Create the Post object
                    ParseObject post = new ParseObject("Users");
                    post.put("textContent", tvStoryName.getText().toString());

                    // Create an author relationship with the current user
                    post.put("author", ParseUser.getCurrentUser());

                    // Save the post and return
                    post.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Error saving: " + e.getMessage(),
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }

                    });

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
                playIntent.putExtra("objectId", objectId);
                startActivity(playIntent);
            }
        });


    }

    /*private void updatePostList() {
        // Create query for objects of type "Post"
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");

        // Restrict to cases where the author is the current user.
        query.whereEqualTo("author", ParseUser.getCurrentUser());

        // Run the query
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> postList,
                             ParseException e) {
                if (e == null) {
                    // If there are results, update the list of posts
                    // and notify the adapter
                    posts.clear();
                    for (ParseObject post : postList) {
                        posts.add(post.getString("textContent"));
                    }

                    ((ArrayAdapter<String>) getListAdapter()).notifyDataSetChanged();
                } else {
                    Log.d("Post retrieval", "Error: " + e.getMessage());
                }
            }

        });

    }*/

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
}
