package com.SweetDream.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.SweetDream.R;

import com.facebook.Profile;

import com.parse.LogInCallback;

import com.parse.ParseException;
import com.parse.ParseFacebookUtils;

import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {
    //Variable on layout---------------------------
    Button btnRegister, btnReset, btnLogin, btnFacebook, btnTwitter;
    EditText mEdtUserNameParse, mEdtPasswordParse;
    TextView forgotPass;
    CircleImageView imgProfile;
    String strUserNameParse, strPasswordParse;
    //Variable for facebook-------------------------------


    String[] nameOfAppsToShareWith = new String[]{"facebook", "twitter", "gmail"};
    String[] blacklist = new String[]{"com.any.package", "net.other.package"};
    List<String> mPermissions = Arrays.asList("public_profile", "user_about_me", "user_relationships", "user_birthday", "user_location", "email");

    private Dialog processingDialog;

    //Variable on drawer layout---------------------------
    TextView txtUserNameFB, txtEmailFB;


    // private ProgressView loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ParseTwitterUtils.initialize("7hWHzX5vIJ9EmipMBnBgIhyK1", "XUE03ZJVWsdgaGSvlVcEV5AYEgGBtqauzJX7zyKxkZyP5XEWZl");


// Check if there is a currently logged in user
        // and it's linked to a Facebook account.
        ParseUser currentUser = ParseUser.getCurrentUser();
        if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
            // Go to the user info activity
            showUserDetailsActivity();
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        ScrollView scrV = (ScrollView) findViewById(R.id.scrViewLogin);
        scrV.setVerticalScrollBarEnabled(false);
        //Call id in Layout
        mEdtUserNameParse = (EditText) findViewById(R.id.edtUsername);
        mEdtPasswordParse = (EditText) findViewById(R.id.edtPassword);
        btnReset = (Button) findViewById(R.id.btnResetLgLayout);
        forgotPass = (Button) findViewById(R.id.btnForgotPass);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnFacebook = (Button) findViewById(R.id.btnFacebookLogin);
        btnTwitter = (Button) findViewById(R.id.btnTwitterLogin);
        imgProfile = (CircleImageView) findViewById(R.id.profile_image);
        txtUserNameFB = (TextView) findViewById(R.id.txtUserNameFacebook);
        txtEmailFB = (TextView) findViewById(R.id.txtUserEmailFacebook);

        //Login Facebook----------------------------------------------------------------------

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processingDialog = ProgressDialog.show(LoginActivity.this, "", "Logging in...", true);
                // NOTE: for extended permissions, like "user_about_me", your app must be reviewed by the Facebook team
                // (https://developers.facebook.com/docs/facebook-login/permissions/)
                ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity.this, mPermissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        processingDialog.dismiss();
                        if (user == null) {
                            Toast.makeText(LoginActivity.this, "Uh oh. The user cancelled the Facebook login", Toast.LENGTH_LONG).show();
                        } else if (user.isNew()) {
                            Toast.makeText(LoginActivity.this, "User signed up and logged in through Facebook!", Toast.LENGTH_LONG).show();

                            showUserDetailsActivity();
                        } else {
                            Toast.makeText(LoginActivity.this, "User logged in through Facebook!", Toast.LENGTH_LONG).show();
                            showUserDetailsActivity();
                        }
                    }
                });
            }
        });

        btnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseTwitterUtils.logIn(LoginActivity.this, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Toast.makeText(LoginActivity.this, "Uh oh. The user cancelled the Twitter login.", Toast.LENGTH_LONG).show();
                        } else if (user.isNew()) {
                            String screen_name = ParseTwitterUtils.getTwitter().getScreenName();
                            //editor.putString("screen_name", screen_name);
                            //btnViewUserProfile.setText(parseUser.getUsername() +"\n"+ parseUser.getEmail());
                            Toast.makeText(LoginActivity.this, screen_name + " has signed in", Toast.LENGTH_LONG).show();
                            Log.d("MyApp", screen_name + " has signed in");
                            // Refresh
                            Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(myIntent);
                        } else {

                            Toast.makeText(LoginActivity.this, "User logged in through Facebook!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        //Intent Activity Register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
//Set All Value Null
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdtUserNameParse.setText("");
                mEdtPasswordParse.setText("");
            }
        });
//Processing Forgot Pass
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "", "Loading...", true);
                final EditText input = new EditText(LoginActivity.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                // Fail
                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage("Type your email to reset")
                        .setTitle("Get Password")
                        .setView(input);
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String yourEmail = input.getText().toString();
                        forgotPass(yourEmail);
                    }
                });
                AlertDialog dialog = builder.create();
                progressDialog.dismiss();
                dialog.show();


            }
        });

        //Process Login Activity
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUserNameParse = mEdtUserNameParse.getText().toString();
                strPasswordParse = mEdtPasswordParse.getText().toString();

                ParseUser.logInInBackground(strUserNameParse, strPasswordParse, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, com.parse.ParseException e) {

                        if (user !=null) {



                            Toast.makeText(LoginActivity.this,
                                    "Successfully Logged in",
                                    Toast.LENGTH_LONG).show();
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

    private void showUserDetailsActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    //Processing ForgotPassss.
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
        //intent.putExtra(Intent.EXTRA_TEXT, "some text");
        //intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "a subject");
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
                String shareVia = getString(R.string.open_email_via);
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
                getString(R.string.open_email_via));
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
