package com.SweetDream.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.SweetDream.Pay.SuperActivity;
import com.SweetDream.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.viettel.android.gsm.ViettelClient;
import com.viettel.android.gsm.VtResponseCode;
import com.viettel.android.gsm.charging.ChargingGateWayApi;
import com.viettel.android.gsm.charging.TopupInfo;
import com.viettel.android.gsm.listener.ViettelOnResponse;

public class MyProfileActivity extends SuperActivity {
    Button btnGetCoin, btnUpdateAccount;
    EditText edtUserName, edtPhone, edtEmail, edtCoin;
    ParseUser currentUser = ParseUser.getCurrentUser();
    private  int Coin = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);*/



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

        /*if(phone.equals("")){
            edtPhone.setText("Update Later!");
        }
        else{
            edtPhone.setText(phone);
        }*/
        edtCoin.setText(currentUser.getString("Coin"));

        btnGetCoin = (Button) findViewById(R.id.btnGetCoin);
        btnUpdateAccount = (Button) findViewById(R.id.btnUpdateAccInfor);
        btnGetCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ViettelClient viettelClient = getViettelClient();
                if (viettelClient == null) {
                    Toast.makeText(MyProfileActivity.this, viettelClient.toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                ChargingGateWayApi.topupCardApp(viettelClient, new ViettelOnResponse<TopupInfo>() {
                    @Override
                    public void onResult(final TopupInfo topupInfo, final int vtCode) {
                        if (vtCode == VtResponseCode.VT_RESULT_OK && topupInfo != null) {
                            if (vtCode == 1) {
                                String napthanhcong = "Nạp Thành công";

                                if (topupInfo.getAmount().toString().equals("10000")) {

                                    String CoinUser = currentUser.getString("Coin");

                                    Coin = Integer.parseInt(CoinUser) + 10;
                                    currentUser.put("Coin", ""+Coin);
                                    currentUser.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if(e == null){

                                                Toast.makeText(MyProfileActivity.this, "Luu Thanh Cong", Toast.LENGTH_LONG).show();
                                            }
                                            else {
                                                Toast.makeText(MyProfileActivity.this, "Loi: "+e, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                                    edtCoin.setText(currentUser.getString("Coin"));
                                    Toast.makeText(MyProfileActivity.this, napthanhcong, Toast.LENGTH_LONG).show();

                                } else if (topupInfo.getAmount().toString().equals("20000")) {
                                   /* Coin = Coin + 20;
                                    edtCoin.setText(String.valueOf(Coin));*/
                                    Toast.makeText(MyProfileActivity.this, napthanhcong, Toast.LENGTH_LONG).show();
                                } else if (topupInfo.getAmount().toString().equals("50000")) {
                                    /*Coin = Coin + 50;
                                    edtCoin.setText(String.valueOf(Coin));
                                    Toast.makeText(MyProfileActivity.this, napthanhcong, Toast.LENGTH_LONG).show();*/
                                } else if (topupInfo.getAmount().toString().equals("100000")) {
                                   /* Coin = Coin + 100;
                                    edtCoin.setText(String.valueOf(Coin));
                                    Toast.makeText(MyProfileActivity.this, napthanhcong, Toast.LENGTH_LONG).show();*/
                                } else if (topupInfo.getAmount().toString().equals("200000")) {
                                   /* Coin = Coin + 200;
                                    edtCoin.setText(String.valueOf(Coin));
                                    Toast.makeText(MyProfileActivity.this, napthanhcong, Toast.LENGTH_LONG).show();*/
                                } else if (topupInfo.getAmount().toString().equals("500000")) {
                                   /* Coin = Coin + 500;
                                    edtCoin.setText(String.valueOf(Coin));
                                    Toast.makeText(MyProfileActivity.this, napthanhcong, Toast.LENGTH_LONG).show();*/
                                }
                            }
                        } else if (vtCode == 106) {
                            Toast.makeText(MyProfileActivity.this, "Đã thoát giao dịch", Toast.LENGTH_LONG).show();
                        } else if (vtCode == 102) {
                            Toast.makeText(MyProfileActivity.this, "Mã thẻ và số seri không được để trống hoặc không đủ kí tự.", Toast.LENGTH_LONG).show();
                        } else if (vtCode == 101) {
                            Toast.makeText(MyProfileActivity.this, "Mã thẻ và số seri không đúng . Vui lòng nhập lại.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MyProfileActivity.this, "Nạp thất bại" + vtCode, Toast.LENGTH_LONG).show();
                        }
                    }
                });
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
