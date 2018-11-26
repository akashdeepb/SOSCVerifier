package com.example.adb7473.soscverifier;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    Button btnScan, btnSubmit;
    EditText code;
    TextView lastVerified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Activity activity = this;

        //Hooking Elements
        btnScan = (Button) findViewById(R.id.scan_btn);
        btnSubmit = (Button) findViewById(R.id.submit_btn);
        code = (EditText) findViewById(R.id.code_text);
        lastVerified = (TextView) findViewById(R.id.lastVerifiedText);

        //QRCode Scanner
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan  Code");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        final Request request = new Request();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, R.string.scan_cancel_text, Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
                code.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
