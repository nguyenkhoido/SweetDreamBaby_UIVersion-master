package com.SweetDream.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.SweetDream.R;
import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import java.net.URI;

import com.parse.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button btnRegister;
    EditText username, pass;
    String mUsername, mPass;
    TextView forgotPass;
    String[] nameOfAppsToShareWith = new String[]{"facebook", "twitter", "gmail"};
    String[] blacklist = new String[]{"com.any.package", "net.other.package"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        forgotPass = (TextView) findViewById(R.id.txtForgotPass);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Forgot pass", Toast.LENGTH_LONG).show();

                final EditText input = new EditText(LoginActivity.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                // Fail
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage("Type your email to reset")
                        .setTitle("Login Error")
                        .setView(input);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String youremail = input.getText().toString();
                        forgotPass(youremail);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });

        Button login = (Button) findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = (EditText) findViewById(R.id.edtUsername);
                pass = (EditText) findViewById(R.id.edtPassword);

                mUsername = username.getText().toString();
                mPass = pass.getText().toString();

                ParseUser.logInInBackground(mUsername, mPass, new LogInCallback() {

                    @Override
                    public void done(ParseUser user, com.parse.ParseException e) {
                        if (user != null) {
                            //Success
                            /*Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);*/

                            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            /*startActivity(intent);*/

                            onBackPressed();
                        } else {
                            // Fail
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage(e.getMessage())
                                    .setTitle("Login Error")
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    }
                });

            }
        });

    }

    private void forgotPass(String youremail) {
        ParseUser.requestPasswordResetInBackground(youremail, new RequestPasswordResetCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(LoginActivity.this, "An email was successfully sent with reset", Toast.LENGTH_LONG).show();
                    // An email was successfully sent with reset instructions.
                    shareContent();
                } else {
                    Toast.makeText(LoginActivity.this, "Your email wrong", Toast.LENGTH_LONG).show();
                    // Something went wrong. Look at the ParseException to see what's up.
                }
            }
        });
    }

    private void shareContent() {
        // your share intent
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "some text");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "a subject");
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

    public void onBackPressed() {
        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
        startActivity(myIntent);
        finish();
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
