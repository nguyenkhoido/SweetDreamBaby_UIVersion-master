package com.SweetDream.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.SweetDream.R;

/**
 * Created by nguye_000 on 07/10/2015.
 */
public class PlayingPage extends AppCompatActivity {
    ImageButton btnPlaying;
    Button btnTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playing_page);


        btnTimes = (Button) findViewById(R.id.imgButtonTimes);
        btnPlaying = (ImageButton) findViewById(R.id.btnPlaying);

        final ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.times));

        btnTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(PlayingPage.this);
                builder.setTitle("Choice Time")
                        .setPositiveButton(android.R.string.ok, null)
                        .setAdapter(Adapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               // Adapter.getItem(which);
                                Toast.makeText(getApplication(), Adapter.getItem(which), Toast.LENGTH_LONG).show();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "Hello Minh Color", Toast.LENGTH_LONG).show();
            }
        });


    }
}
