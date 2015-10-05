package com.SweetDream.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.SweetDream.R;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity {

    EditText username, email, phone, pass, confirmPass;
    String mUsername, mEmail, mPhone, mPass, mConfirmPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Use parse to test recive data
        // thong ke lai nguoi dung dang ky
        ParseAnalytics.trackAppOpened(getIntent());

        Button signup = (Button) findViewById(R.id.btnRegister);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = (EditText) findViewById(R.id.edtUsername);
                email = (EditText) findViewById(R.id.edtEmail);
                phone = (EditText) findViewById(R.id.edtPhone);
                pass = (EditText) findViewById(R.id.edtPassword);
                confirmPass = (EditText) findViewById(R.id.edtConfirmPassword);


                mUsername = username.getText().toString();
                mEmail = email.getText().toString();
                mPhone = phone.getText().toString();
                mPass = pass.getText().toString();
                mConfirmPass = confirmPass.getText().toString();

                ParseUser user = new ParseUser();
                user.setUsername(mUsername);
                user.put("phone", mPhone);
                user.setPassword(mPass);
                user.setEmail(mEmail);

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            //
                            Toast.makeText(RegisterActivity.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            //Call error
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage(e.getMessage())
                                    .setTitle("Register Fail")
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
