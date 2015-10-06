package com.SweetDream.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.SweetDream.R;

public class UpdateAccountInformationActivity extends AppCompatActivity {
    TextView txtChangePass;

    Button btnUpdate, btnCancel;
    EditText edtUserName, edtPhone, edtOldPass, edtNewPass, edtConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account_information);

        btnUpdate = (Button) findViewById(R.id.btnUpdateAccInfor);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        edtUserName = (EditText) findViewById(R.id.edtUsernameUAI);
        edtPhone = (EditText) findViewById(R.id.edtPhoneUAI);
        edtOldPass = (EditText) findViewById(R.id.edtOldPasswordUAI);
        edtNewPass = (EditText) findViewById(R.id.edtPasswordUAI);
        edtConfirmPass = (EditText) findViewById(R.id.edtConfirmPasswordUAI);


        txtChangePass = (TextView) findViewById(R.id.txtChangePassword);
        txtChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtChangePass.setVisibility(View.GONE);
                edtOldPass.setVisibility(View.VISIBLE);
                edtNewPass.setVisibility(View.VISIBLE);
                edtConfirmPass.setVisibility(View.VISIBLE);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtOldPass.setVisibility(View.GONE);
                edtNewPass.setVisibility(View.GONE);
                edtConfirmPass.setVisibility(View.GONE);
                txtChangePass.setVisibility(View.VISIBLE);
                edtUserName.setText("");
                edtPhone.setText("");
                edtOldPass.setText("");
                edtNewPass.setText("");
                edtConfirmPass.setText("");
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
