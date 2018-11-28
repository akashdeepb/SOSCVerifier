package com.example.adb7473.soscverifier;

import android.content.Intent;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.r0adkll.slidr.Slidr;

public class QRScanner extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {
    ImageButton back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.LightTheme);
        setContentView(R.layout.activity_qrscanner);
        back_btn = (ImageButton)findViewById(R.id.button);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QRScanner.this,MainActivity.class);
                startActivity(i);
            }
        });

        //QR Code View Declaration
        QRCodeReaderView qrCodeReaderView;

        qrCodeReaderView = (QRCodeReaderView)findViewById(R.id.qr_view);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setBackCamera();
        qrCodeReaderView.setAutofocusInterval(2000L);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        //code.setText(text);
        //request.verifyRequest(MainActivity.this, code.getText().toString());
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);
    }
}