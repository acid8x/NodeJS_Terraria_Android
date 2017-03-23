package com.example.acid8xtreme.socket_io_example;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

public class GetServerActivity extends Activity implements View.OnClickListener {

    public int[] id = {R.id.np1, R.id.np2, R.id.np3};
    public NumberPicker[] np = new NumberPicker[3];
    public static int activityInfo,n1,n2,n3;
    public static boolean landscape;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        int orientation = getResources().getConfiguration().orientation;
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        if (orientation == 2) {
            landscape = true;
            if (rotation < 2) activityInfo = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            if (rotation > 1) activityInfo = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
        } else if (orientation == 1) {
            landscape = false;
            if (rotation < 2) activityInfo = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            if (rotation > 1) activityInfo = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getserver);
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String ipint = Formatter.formatIpAddress(ip);
        char[] array = ipint.toCharArray();
        String ipAddress = "";
        int count = 0;
        for (char c : array) {
            if (c > 47 && c < 58) ipAddress += c;
            else if (c == '.') {
                ipAddress += '.';
                count++;
            }
            if (count == 3) break;
        }
        TextView tvip = (TextView) findViewById(R.id.tvip);
        tvip.setText(ipAddress);
        for (int i = 0; i < 3; i++) {
            np[i] = (NumberPicker) findViewById(id[i]);
            np[i].setMinValue(0);
            np[i].setMaxValue(9);
            np[i].setValue(i);
        }
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                int ip = 0;
                for (int i = 0; i < 3; i++) ip = (ip*10) + np[i].getValue();
                ConnectTo(ip);
                break;
        }
    }

    public void ConnectTo(int s) {
        Intent intent = new Intent(GetServerActivity.this, MainActivity.class);
        intent.putExtra("SOCKET", "http://192.168.1." + s + ":2222");
        startActivity(intent);
    }
}
