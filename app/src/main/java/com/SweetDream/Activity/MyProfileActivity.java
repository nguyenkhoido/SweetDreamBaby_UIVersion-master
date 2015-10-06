package com.SweetDream.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.SweetDream.R;
import com.parse.ParseUser;

public class MyProfileActivity extends AppCompatActivity {
    Button btnGetCoin, btnUpdateAccount;
    EditText edtUserName, edtPhone, edtEmail, edtCoin;
    ParseUser currentUser = ParseUser.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);



        edtUserName = (EditText)findViewById(R.id.edtUserNameProfile);
        edtEmail = (EditText)findViewById(R.id.edtEmailProfile);
        edtPhone = (EditText)findViewById(R.id.edtPhoneProfile);
        edtCoin = (EditText)findViewById(R.id.edtCoinProfile);


        /*ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("phone","sweet dream");
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                } else {
                    // Something went wrong.
                }
            }
        });*/
        edtUserName.setText(currentUser.getUsername());
        edtEmail.setText(currentUser.getEmail());

        String phone = currentUser.getString("phone");

        if(phone.equals("")){
            edtPhone.setText("Update Later!");
        }
        else{
            edtPhone.setText(phone);
        }
        edtCoin.setText(String.valueOf(currentUser.getNumber("coin")));

        btnGetCoin = (Button) findViewById(R.id.btnGetCoin);
        btnUpdateAccount = (Button) findViewById(R.id.btnUpdateAccInfor);
        btnGetCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getCoin = new Intent(MyProfileActivity.this, GetCoinActivity.class);
                startActivity(getCoin);
            }
        });

        btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateAccount = new Intent(MyProfileActivity.this, UpdateAccountInformationActivity.class);
                startActivity(updateAccount);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_profile, menu);
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
