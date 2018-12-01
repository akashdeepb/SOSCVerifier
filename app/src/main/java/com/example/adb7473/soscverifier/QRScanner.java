package com.example.adb7473.soscverifier;

import android.content.Intent;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.r0adkll.slidr.Slidr;

import android.view.View;
import android.widget.ImageButton;

public class QRScanner extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

    ImageButton focusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_qrscanner);

        Slidr.attach(this);

        focusButton = (ImageButton)findViewById(R.id.btn_focus);

        //QR Code View Declaration
        final QRCodeReaderView qrCodeReaderView;

        qrCodeReaderView = (QRCodeReaderView)findViewById(R.id.qr_view);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setBackCamera();
        qrCodeReaderView.setAutofocusInterval(4000L);

        focusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrCodeReaderView.forceAutoFocus();
            }
        });
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        //code.setText(text);
        //request.verifyRequest(MainActivity.this, code.getText().toString());
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);
    }
}