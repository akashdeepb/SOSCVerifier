package com.example.adb7473.soscverifier;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;

public class MainActivity extends AppCompatActivity{
    Button btnSubmit;
    ImageButton btnClear;
    EditText code;
    TextView lastVerified;
    final Request request = new Request();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Activity activity = this;

        Slidr.attach(this);

        //Hooking Elements
        code = (EditText) findViewById(R.id.code_text);
        lastVerified = (TextView) findViewById(R.id.lastVerifiedText);
        btnSubmit=(Button)findViewById(R.id.submit_btn);
        btnClear = (ImageButton)findViewById(R.id.btn_clear);

        request.setListener(new Request.Listener() {
            @Override
            public void changeField(String responseText) {
                lastVerified.setText(responseText);
                Toast.makeText(MainActivity.this, responseText, Toast.LENGTH_LONG).show();
                if (!responseText.contains("Unregistered") && !responseText.contains("Error")) {
                    code.setText("");
                }
            }
        });

        //Clear Button OnClick
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code.setText("");
            }
        });

        //Submit Button OnClick
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check if Network Connected
                boolean connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                } else
                    connected = false;

                //Send Request only when Network Connected
                if (connected) {
                    //Check if Input is not empty field
                    if (!code.getText().toString().isEmpty()) {
                        request.verifyRequest(MainActivity.this, code.getText().toString());
                    } else
                        Toast.makeText(MainActivity.this, R.string.enter_code_text, Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(MainActivity.this, R.string.internet_check_text, Toast.LENGTH_LONG).show();
            }
        });

    }

}
