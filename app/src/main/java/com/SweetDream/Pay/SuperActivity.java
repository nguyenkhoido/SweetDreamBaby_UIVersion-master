package com.SweetDream.Pay;

import android.widget.Toast;

import com.SweetDream.Activity.MyProfileActivity;
import com.viettel.android.gsm.ViettelClient;
import com.viettel.android.gsm.ViettelError;
import com.viettel.android.gsm.services.ViettelClientSimpleActivity;

/**
 * Created by Phạm Đức Thịnh on 27/10/2015.
 */
public class SuperActivity extends ViettelClientSimpleActivity {
    @Override
    public Class<?> classExitApp() {
        return MyProfileActivity.class;

    }

    @Override
    public InitializeParams initParams() {
        InitializeParams pramsAuto = new InitializeParams();

        pramsAuto.setViettelId("123197","12667");
        pramsAuto.setTestDevice(false);
        pramsAuto.setReadTimeout(300000);
        return pramsAuto;
    }

    @Override
    public boolean autoConnectServer() {
        return true;
    }

    @Override
    public ViettelClient.OnConnectionCallbacks onConnectionCallbacks() {
      return new ViettelClient.OnConnectionCallbacks() {
          @Override
          public void onConnected() {
              //Toast.makeText(SuperActivity.this,"Connected" , Toast.LENGTH_LONG).show();
          }

          @Override
          public void onConnectFail(ViettelError viettelError) {
            String str = viettelError !=  null ? viettelError.toString() : "Connect failure!!!";
              Toast.makeText(SuperActivity.this,str , Toast.LENGTH_LONG).show();
          }
      };
    }
}