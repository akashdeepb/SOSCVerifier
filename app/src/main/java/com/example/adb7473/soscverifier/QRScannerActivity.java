package com.example.adb7473.soscverifier;

import android.content.Intent;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.r0adkll.slidr.Slidr;

import android.view.View;
import android.widget.ImageButton;

public class QRScannerActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

    ImageButton focusButton;

    public static QRScanListener qrScanListener;


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
        qrScanListener.onScanned(text);
        finish();
    }
}