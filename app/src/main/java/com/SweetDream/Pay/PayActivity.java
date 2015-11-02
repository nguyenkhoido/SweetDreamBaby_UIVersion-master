package com.SweetDream.Pay;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.SweetDream.R;
import com.viettel.android.gsm.ViettelClient;
import com.viettel.android.gsm.VtResponseCode;
import com.viettel.android.gsm.charging.ChargingGateWayApi;
import com.viettel.android.gsm.charging.TopupInfo;
import com.viettel.android.gsm.listener.ViettelOnResponse;

public class PayActivity extends SuperActivity{
//(Publisher ID : 123197
    //Mã ứng dụng (App ID):12667
private  int Coin = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        final TextView TextCoin = (TextView) findViewById(R.id.coin);
        Button btnnaptien = (Button) findViewById(R.id.btnnaptien);

        btnnaptien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ViettelClient viettelClient = getViettelClient();
                if (viettelClient == null) {
                    Toast.makeText(PayActivity.this,viettelClient.toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                    ChargingGateWayApi.topupCardApp(viettelClient, new ViettelOnResponse<TopupInfo>() {
                        @Override
                        public void onResult(final TopupInfo topupInfo, final int vtCode) {
                            if (vtCode == VtResponseCode.VT_RESULT_OK && topupInfo != null) {
                                if (vtCode == 1){
                                    String napthanhcong = "Nạp Thành công";

                                     if(topupInfo.getAmount().toString().equals("10000")){
                                         Coin = Coin + 10;
                                         TextCoin.setText(String.valueOf(Coin));
                                         Toast.makeText(PayActivity.this, napthanhcong, Toast.LENGTH_LONG).show();
                                     } else if(topupInfo.getAmount().toString().equals("20000")){
                                         Coin = Coin + 20;
                                         TextCoin.setText(String.valueOf(Coin));
                                         Toast.makeText(PayActivity.this,napthanhcong , Toast.LENGTH_LONG).show();
                                     }
                                     else if(topupInfo.getAmount().toString().equals("50000")){
                                         Coin = Coin + 50;
                                         TextCoin.setText(String.valueOf(Coin));
                                         Toast.makeText(PayActivity.this,napthanhcong, Toast.LENGTH_LONG).show();
                                     }
                                     else if(topupInfo.getAmount().toString().equals("100000")){
                                         Coin = Coin + 100;
                                         TextCoin.setText(String.valueOf(Coin));
                                         Toast.makeText(PayActivity.this,napthanhcong, Toast.LENGTH_LONG).show();
                                     }
                                     else if(topupInfo.getAmount().toString().equals("200000")){
                                         Coin = Coin + 200;
                                         TextCoin.setText(String.valueOf(Coin));
                                         Toast.makeText(PayActivity.this, napthanhcong, Toast.LENGTH_LONG).show();
                                     }
                                     else if(topupInfo.getAmount().toString().equals("500000")){
                                         Coin = Coin + 500;
                                         TextCoin.setText(String.valueOf(Coin));
                                         Toast.makeText(PayActivity.this, napthanhcong, Toast.LENGTH_LONG).show();
                                     }
                                }
                            }
                            else if(vtCode == 106){
                                Toast.makeText(PayActivity.this, "Đã thoát giao dịch", Toast.LENGTH_LONG).show();
                            }
                            else if(vtCode == 102){
                                Toast.makeText(PayActivity.this, "Mã thẻ và số seri không được để trống hoặc không đủ kí tự.", Toast.LENGTH_LONG).show();
                            }
                            else if(vtCode == 101){
                                Toast.makeText(PayActivity.this, "Mã thẻ và số seri không đúng . Vui lòng nhập lại.", Toast.LENGTH_LONG).show();
                            }

                            else {
                                Toast.makeText(PayActivity.this, "Nạp thất bại" + vtCode, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

        });


    }

}
