package com.duje.wifibook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<NetworkModel> networkModels = new ArrayList<>();
    RecyclerView rvWifiList;

    ImageButton ibScanner;
    Network_RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvWifiList = findViewById(R.id.rvWifiList);
        ibScanner = findViewById(R.id.ibQr);




         adapter = new Network_RecyclerViewAdapter(this,
                networkModels);




        rvWifiList.setAdapter(adapter);
        rvWifiList.setLayoutManager(new LinearLayoutManager(this));

        ibScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScanner();
            }
        });
    }

    private void setNetworkModels(Network_RecyclerViewAdapter adapter,String SSID, String Type, String Password){
        networkModels.add(new NetworkModel("Network Name: " + SSID, "Security Type: " + Type,"Password: " + Password));
        Log.d("logg", "Change");
        adapter.notifyDataSetChanged();

    }

    public void openScanner() {
        Intent intent = new Intent(this, QRScanner.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Retrieve data from the intent
        Intent intent = getIntent();
        String ssid = intent.getStringExtra("key1");
        String Type = intent.getStringExtra("key2");
        String Password = intent.getStringExtra("key3");

        setNetworkModels(adapter, ssid, Type, Password);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Retrieve data from the intent
        Intent intent = getIntent();
        String ssid = intent.getStringExtra("key1");
        String Type = intent.getStringExtra("key2");
        String Password = intent.getStringExtra("key3");

        setNetworkModels(adapter, ssid, Type, Password);
    }
}