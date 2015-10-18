package com.SweetDream.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.SweetDream.Extends.LoadImageAudioParse;
import com.SweetDream.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class StoryDetails extends AppCompatActivity {
    String test = "";
    Button btnPlay,btnDownload;

    ImageView storyImage;
    TextView tvStoryName, tvAuthor;
    LoadImageAudioParse load = new LoadImageAudioParse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_page);

        storyImage = (ImageView) findViewById(R.id.tvStoryImage);
        tvStoryName = (TextView) findViewById(R.id.tvStoryName);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);

        SharedPreferences myprefs= getSharedPreferences("user", MODE_WORLD_READABLE);
        String session_id= myprefs.getString("session_id", null);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Story");
        query.whereEqualTo("objectId", session_id);
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



        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(StoryDetails.this, PlayingPage.class);
                startActivity(playIntent);
            }
        });
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
}
