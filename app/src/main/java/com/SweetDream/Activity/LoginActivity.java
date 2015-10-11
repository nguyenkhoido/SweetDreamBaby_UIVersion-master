package com.SweetDream.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
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
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SaveCallback;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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
    String strUserNameParse,strPasswordParse;
//Variable for facebook-------------------------------
    Profile mFbProfile;
    String name=null, email=null;
    String[] nameOfAppsToShareWith = new String[]{"facebook", "twitter", "gmail"};
    String[] blacklist = new String[]{"com.any.package", "net.other.package"};
    List<String> mPermissions = Arrays.asList("public_profile", "email");

    private Dialog processingDialog;
    ParseUser parseUser;
    TextView txtUserNameFB;


   // private ProgressView loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        FacebookSdk.sdkInitialize(getApplicationContext());
        ParseFacebookUtils.initialize(this.getApplicationContext());

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

        //Login Facebook----------------------------------------------------------------------
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processingDialog = ProgressDialog.show(LoginActivity.this, "", "Login in...", true);

                ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity.this, mPermissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        processingDialog.dismiss();
                        //loader.stop();
                        if (user == null) {
                            Toast.makeText(LoginActivity.this, "Uh oh. The user cancelled the Facebook login", Toast.LENGTH_LONG).show();

                        } else if (user.isNew()) {
                            Toast.makeText(LoginActivity.this, "User signed up and logged in through Facebook!", Toast.LENGTH_LONG).show();


                            getUserDetailsFromFB();

                        } else {
                            Toast.makeText(LoginActivity.this, "User logged in through Facebook!", Toast.LENGTH_LONG).show();


                            getUserDetailsFromParse();
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
                final EditText input = new EditText(LoginActivity.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                // Fail
                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage("Type your email to reset")
                        .setTitle("Login Error")
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
                        if (user != null) {

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
    //Save New USer.........................................................................
    private void saveNewUser() {
        parseUser = ParseUser.getCurrentUser();
        parseUser.setUsername(name);
        parseUser.setEmail(email);
//        Saving profile photo as a ParseFile
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] data = stream.toByteArray();
        String thumbName = parseUser.getUsername().replaceAll("\\s+", "");
        final ParseFile parseFile = new ParseFile(thumbName + "_thumb.jpg", data);
        parseFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                parseUser.put("profileThumb", parseFile);
                //Finally save all the user details
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(LoginActivity.this, "New user:" + name + " Signed up", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //Get User Details Email Name---------------------------------------------------------
    private void getUserDetailsFromFB() {

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                null,
                HttpMethod.GET,

                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
           /* handle the result */
                        try {

                            name = response.getJSONObject().getString("name");

                            email = response.getJSONObject().getString("email");

                           //setText user profile at facebook
                            saveNewUser();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
        ProfilePhotoAsync profilePhotoAsync = new ProfilePhotoAsync(mFbProfile);
        profilePhotoAsync.execute();
    }

    //Get Details User In Parse
    private void getUserDetailsFromParse() {
        parseUser = ParseUser.getCurrentUser();
//Fetch profile photo
        try {
            ParseFile parseFile = parseUser.getParseFile("profileThumb");
            byte[] data = parseFile.getData();
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            imgProfile.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

       // btnViewUserProfile.setText(parseUser.getUsername() +"\n"+ parseUser.getEmail());
        Toast.makeText(LoginActivity.this, "Welcome back " + txtUserNameFB.getText().toString(), Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    //Method Processing Picture Profile
    class ProfilePhotoAsync extends AsyncTask<String, String, String> {
        Profile profile;
        public Bitmap bitmap;

        public ProfilePhotoAsync(Profile profile) {
            this.profile = profile;
        }

        @Override
        protected String doInBackground(String... params) {
            // Fetching data from URI and storing in bitmap
            bitmap = DownloadImageBitmap(profile.getProfilePictureUri(200, 200).toString());
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            imgProfile.setImageBitmap(bitmap);
        }
    }

    //Draw image profile
    public static Bitmap DownloadImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("IMAGE", "Error getting bitmap", e);
        }
        return bm;
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
