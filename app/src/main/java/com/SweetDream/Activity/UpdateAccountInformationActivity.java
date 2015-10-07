package com.SweetDream.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.SweetDream.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class UpdateAccountInformationActivity extends AppCompatActivity {
    TextView txtChangePass;
    String txtUser, txtPhone, txtOldPass, txtNewPass, txtConfirmPass;
    Button btnUpdateNamePhone, btnUpdateAll, btnCancel;
    EditText edtUserName, edtPhone, edtOldPass, edtNewPass, edtConfirmPass;
    ParseUser user = ParseUser.getCurrentUser();

    boolean checkOldPass = true;
    List<ParseException> exceptionList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account_information);

        btnUpdateNamePhone = (Button) findViewById(R.id.btnUpdateNamePhone);
        btnUpdateAll = (Button) findViewById(R.id.btnUpdateAll);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        edtUserName = (EditText) findViewById(R.id.edtUsernameUAI);
        edtPhone = (EditText) findViewById(R.id.edtPhoneUAI);
        edtOldPass = (EditText) findViewById(R.id.edtOldPasswordUAI);
        edtNewPass = (EditText) findViewById(R.id.edtPasswordUAI);
        edtConfirmPass = (EditText) findViewById(R.id.edtConfirmPasswordUAI);
        txtChangePass = (TextView) findViewById(R.id.txtChangePassword);

        edtUserName.setText(user.getUsername());
        edtPhone.setText(user.getString("phone"));


        //Update UserName when txtChangePass.-----------------------------------------------

        btnUpdateNamePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Value in EditText

                txtUser = edtUserName.getText().toString();
                txtPhone = edtPhone.getText().toString();
                // txtOldPass = edtOldPass.getText().toString();
                // txtNewPass = edtNewPass.getText().toString();
                // txtConfirmPass = edtConfirmPass.getText().toString();
                //Check Error EditText

                String alert = "";
                if (txtUser.equalsIgnoreCase("")) {
                    alert = "Please input Username";
                } else if (txtUser.length() < 4) {
                    alert = "Please input Username at least 4 character";
                } else if (txtUser.length() > 20) {
                    alert = "Please input Username no more 20 character";
                } else {
                    UpdateAccount();
                }
                //Process UpdateAccount()
                if (!alert.equalsIgnoreCase("")) {
                    //Call error
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateAccountInformationActivity.this);
                    builder.setMessage(alert)
                            .setTitle("Update State")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }



            }
        });
        //Update Alll.--------------------------------------------------------------------
        btnUpdateAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get Value in EditText
                txtUser = edtUserName.getText().toString();
                txtPhone = edtPhone.getText().toString();
                txtOldPass = edtOldPass.getText().toString();
                txtNewPass = edtNewPass.getText().toString();
                txtConfirmPass = edtConfirmPass.getText().toString();

                //Toast.makeText(UpdateAccountInformationActivity.this, error[0], Toast.LENGTH_LONG).show();
                //Check Validation
                String alert = "";
                if (txtUser.equalsIgnoreCase("")) {
                    alert = "Please input Username";
                } else if (txtUser.length() <= 4) {
                    alert = "Please input Username at least 4 character";
                } else if (txtUser.length() > 20) {
                    alert = "Please input Username no more 20 character";
                } else if (txtOldPass.equalsIgnoreCase("")) {
                    alert = "Please input Old Password";
                } else if (checkOldPassword(txtOldPass) == false) {
                    alert = "Wrong Old Password";
                    //Toast.makeText(UpdateAccountInformationActivity.this, "Wrong Old Password", Toast.LENGTH_LONG).show();

                } else if (txtNewPass.equalsIgnoreCase("")) {
                    alert = "Please input New Password";
                } else if (txtConfirmPass.equalsIgnoreCase("")) {
                    alert = "Please input Confirm Password";
                } else if (!txtNewPass.equals(txtConfirmPass)) {
                    alert = "Please input Password equal Confirm Password";
                } else {

                    UpdateAccount1();
                }
                //Process UpdateAccount()
                if (!alert.equalsIgnoreCase("")) {
                    //Call error
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateAccountInformationActivity.this);
                    builder.setMessage(alert)
                            .setTitle("Update State")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

        });
        txtChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpdateNamePhone.setVisibility(View.GONE);
                btnUpdateAll.setVisibility(View.VISIBLE);
                txtChangePass.setVisibility(View.GONE);
                edtOldPass.setVisibility(View.VISIBLE);
                edtNewPass.setVisibility(View.VISIBLE);
                edtConfirmPass.setVisibility(View.VISIBLE);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpdateNamePhone.setVisibility(View.VISIBLE);
                btnUpdateAll.setVisibility(View.GONE);
                edtOldPass.setVisibility(View.GONE);
                edtNewPass.setVisibility(View.GONE);
                edtConfirmPass.setVisibility(View.GONE);
                txtChangePass.setVisibility(View.VISIBLE);
                //edtUserName.setText("");
                //edtPhone.setText("");
                edtOldPass.setText("");
                edtNewPass.setText("");
                edtConfirmPass.setText("");
            }
        });
    }


    // check Login old password
    boolean checkOldPassword(String oldPass) {

        ParseUser.logInInBackground(ParseUser.getCurrentUser().getUsername(), oldPass, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                // Hooray! The password is correct
// The password was incorrect
                checkOldPass = user != null;
                Toast.makeText(UpdateAccountInformationActivity.this, "" + checkOldPass, Toast.LENGTH_SHORT).show();//true

            }
        });

        return checkOldPass;
    }

    //Update username and phone
    void UpdateAccount() {
        //updater username
        user.setUsername(txtUser);
        //updater phonenumber
        user.put("phone", txtPhone);
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    Toast.makeText(UpdateAccountInformationActivity.this, "Update Account Success!", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(UpdateAccountInformationActivity.this, MyProfileActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
                    startActivity(myIntent);
                    finish();
                } else {
                    //Call error
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateAccountInformationActivity.this);
                    builder.setMessage(e.getMessage())
                            .setTitle("Update State")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    //Update Password User
    void UpdateAccount1() {

        user.setPassword(txtNewPass);
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    Toast.makeText(UpdateAccountInformationActivity.this, "Update Account Success!", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(UpdateAccountInformationActivity.this, MyProfileActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
                    startActivity(myIntent);
                    finish();
                } else {
                    //Call error
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateAccountInformationActivity.this);
                    builder.setMessage(e.getMessage())
                            .setTitle("Update State")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_account_information, menu);
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
