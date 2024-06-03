package com.duje.wifibook;

import android.Manifest;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.ScanMode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QRScanner extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 101;
    private CodeScanner codeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_scanner);


        ImageView exitBtn = findViewById(R.id.exit);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);

        setupPermissions();
        scannerCode(scannerView);

        exitBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            ContextCompat.startActivity(this, intent, savedInstanceState);
        });
    }

    private void scannerCode(CodeScannerView scannerView) {
        codeScanner = new CodeScanner(this, scannerView);

        codeScanner.setCamera(CodeScanner.CAMERA_BACK);
        codeScanner.setFormats(CodeScanner.ALL_FORMATS);
        codeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        codeScanner.setScanMode(ScanMode.SINGLE);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFlashEnabled(false);



        //codeScanner.setDecodeCallback(result -> runOnUiThread(() -> postData(result.getText())));
        codeScanner.setDecodeCallback(result -> runOnUiThread(() -> {

            String wifiString = result.getText();
            Pattern pattern = Pattern.compile("WIFI:S:(.*?);T:(.*?);P:(.*?);");
            Pattern pattern2 = Pattern.compile("WIFI:T:(.*?);S:(.*?);P:(.*?);");
            Matcher matcher = pattern.matcher(wifiString);
            Matcher matcher2 = pattern2.matcher(wifiString);

            if (matcher.find()) {
                String ssid = matcher.group(1);
                String type = matcher.group(2);
                String password = matcher.group(3);
                Log.d("loggg", "SSID: " + ssid);
                Log.d("loggg", "Type: " + type);
                Log.d("loggg", "Password: " + password);
                postData(ssid,type,password);
            }else if(matcher2.find()){
                String ssid = matcher2.group(2);
                String type = matcher2.group(1);
                String password = matcher2.group(3);
                Log.d("loggg", "SSID: " + ssid);
                Log.d("loggg", "Type: " + type);
                Log.d("loggg", "Password: " + password);
                postData(ssid,type,password);
            }
            else {
                Toast.makeText(this, "Pattern not found", Toast.LENGTH_SHORT);
                Log.d("loggg", "" + result.getText());
            }
        }));
        codeScanner.setErrorCallback(error -> runOnUiThread(() -> {
            // Handle any errors
        }));

        scannerView.setOnClickListener(v -> codeScanner.startPreview());
    }

    // We get the info
    private void postData(String SSID, String SecurityType, String Password) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("key1", SSID);
        intent.putExtra("key2", SecurityType);
        intent.putExtra("key3", Password );
        startActivity(intent);
    }

    private void setupPermissions() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                // Handle permission denial
            } else {
                // Permission granted
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    public void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }

}


