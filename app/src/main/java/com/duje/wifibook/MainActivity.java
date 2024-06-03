package com.duje.wifibook;

import static com.duje.wifibook.Util.networkModels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SharedPreferencesHelper sharedPreferencesHelper;
    private AppDatabase db;
    WifiData wifiData;
    String[] SSIDArray, TypeArray, PasswordArray;
    RecyclerView rvWifiList;

    ImageButton ibScanner;
   public Network_RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvWifiList = findViewById(R.id.rvWifiList);
        ibScanner = findViewById(R.id.ibQr);

        db = AppDatabase.getDatabase(this);
        sharedPreferencesHelper = new SharedPreferencesHelper(this);


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

    private static void setNetworkModels(Network_RecyclerViewAdapter adapter, String SSID, String Type, String Password) {
        networkModels.add(new NetworkModel("Network Name: " + SSID, "Security Type: " + Type, "Password: " + Password));
        Log.d("logg", "Change");
        adapter.notifyDataSetChanged();

    }

    public void openScanner() {
        Intent intent = new Intent(this, QRScanner.class);
        startActivity(intent);
    }

    protected void onResume() {
        super.onResume();
        // Retrieve data from the intent
        Intent intent = getIntent();
        String ssid = intent.getStringExtra("key1");
        String Type = intent.getStringExtra("key2");
        String Password = intent.getStringExtra("key3");

        networkModels.clear();

       new GetAllWifiDataAsyncTask(db.wifiDataDao(), adapter).execute();


        if(ssid != null){
            setNetworkModels(adapter, ssid, Type, Password);
            wifiData = new WifiData(ssid, Type, Password);
            new InsertWifiDataAsyncTask(db.wifiDataDao()).execute(wifiData);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private static class GetAllWifiDataAsyncTask extends AsyncTask<Void, Void, List<WifiData>> {
        private WifiDataDAO wifiDataDao;
        Network_RecyclerViewAdapter adapter;
        GetAllWifiDataAsyncTask(WifiDataDAO wifiDataDao, Network_RecyclerViewAdapter adapter) {
            this.wifiDataDao = wifiDataDao;
            this.adapter = adapter;
        }

        @Override
        protected List<WifiData> doInBackground(Void... voids) {
            return wifiDataDao.getAllWifiData();
        }

        @Override
        protected void onPostExecute(List<WifiData> wifiDataList) {
            super.onPostExecute(wifiDataList);
            for (WifiData wifiData : wifiDataList) {
                Log.d("MainActivity", "SSID: " + wifiData.ssid + ", Security Type: " + wifiData.securityType + ", Password: " + wifiData.password);
                setNetworkModels(adapter, wifiData.ssid, wifiData.securityType, wifiData.password);

            }
        }
    }

    private static class InsertWifiDataAsyncTask extends AsyncTask<WifiData, Void, Void> {
        private WifiDataDAO wifiDataDao;

        InsertWifiDataAsyncTask(WifiDataDAO wifiDataDao) {
            this.wifiDataDao = wifiDataDao;
        }

        @Override
        protected Void doInBackground(WifiData... wifiData) {
            wifiDataDao.insert(wifiData[0]);
            return null;
        }
    }

    public class SharedPreferencesHelper {
        private static final String PREFERENCES_FILE = "com.duje.wifibook.networksdata";
        private static final String ARRAY1_KEY = "array1";
        private static final String ARRAY2_KEY = "array2";
        private static final String ARRAY3_KEY = "array3";
        private SharedPreferences sharedPreferences;
        private Gson gson;

        public SharedPreferencesHelper(Context context) {
            sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
            gson = new Gson();
        }

        // Save string arrays to SharedPreferences
        public void saveStringArrays(String[] array1, String[] array2, String[] array3) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ARRAY1_KEY, gson.toJson(array1));
            editor.putString(ARRAY2_KEY, gson.toJson(array2));
            editor.putString(ARRAY3_KEY, gson.toJson(array3));
            editor.apply();
        }

        // Retrieve string arrays from SharedPreferences
        public String[] getStringArray(String key) {
            String json = sharedPreferences.getString(key, null);
            return gson.fromJson(json, String[].class);
        }

        public String[] getArray1() {
            return getStringArray(ARRAY1_KEY);
        }

        public String[] getArray2() {
            return getStringArray(ARRAY2_KEY);
        }

        public String[] getArray3() {
            return getStringArray(ARRAY3_KEY);
        }
    }
}