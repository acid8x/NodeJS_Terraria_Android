package com.example.acid8xtreme.socket_io_example;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends FragmentActivity {

    public TextView[] slots;
    private MainFragment mainFragment = null;
    private boolean landscape;
    private int activityInfo;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_SOCKET_IO:
                    String socketMessage = msg.getData().getString(Constants.SOCKET_IO);
                    if (socketMessage != null) {
                        String[] separated = socketMessage.split(",");
                        for (int i = 0; i < separated.length; i++) {
                            byte[] decodedString = Base64.decode(separated[i], Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            BitmapDrawable ob = new BitmapDrawable(getResources(), decodedByte);
                            slots[i].setBackground(ob);
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        setContentView(R.layout.activity_main);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        slots = new TextView[50];
        loadTextViewArray();
        byte[] decodedString = Base64.decode(
                "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NiYZ/3JROP9yUTj/NiYZ/zYmGf8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADYmGf82Jhn/clE4/3JROP82Jhn/NiYZ/wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANiYZ/zYmGf82Jhn/NiYZ/wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA2Jhn/NiYZ/zYmGf82Jhn/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=="
                , Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        BitmapDrawable ob = new BitmapDrawable(getResources(), decodedByte);
        slots[12].setBackground(ob);

        if (savedInstanceState == null) {
            mainFragment = MainFragment.newInstance(mHandler);
            getSupportFragmentManager().beginTransaction().add(mainFragment, "worker").commit();
            mainFragment.setRetainInstance(true);
        }
    }

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
        System.exit(0);
    }

    private void loadTextViewArray() {
        slots[0] = (TextView) findViewById(R.id.tv0);
        slots[1] = (TextView) findViewById(R.id.tv1);
        slots[2] = (TextView) findViewById(R.id.tv2);
        slots[3] = (TextView) findViewById(R.id.tv3);
        slots[4] = (TextView) findViewById(R.id.tv4);
        slots[5] = (TextView) findViewById(R.id.tv5);
        slots[6] = (TextView) findViewById(R.id.tv6);
        slots[7] = (TextView) findViewById(R.id.tv7);
        slots[8] = (TextView) findViewById(R.id.tv8);
        slots[9] = (TextView) findViewById(R.id.tv9);
        slots[10] = (TextView) findViewById(R.id.tv10);
        slots[11] = (TextView) findViewById(R.id.tv11);
        slots[12] = (TextView) findViewById(R.id.tv12);
        slots[13] = (TextView) findViewById(R.id.tv13);
        slots[14] = (TextView) findViewById(R.id.tv14);
        slots[15] = (TextView) findViewById(R.id.tv15);
        slots[16] = (TextView) findViewById(R.id.tv16);
        slots[17] = (TextView) findViewById(R.id.tv17);
        slots[18] = (TextView) findViewById(R.id.tv18);
        slots[19] = (TextView) findViewById(R.id.tv19);
        slots[20] = (TextView) findViewById(R.id.tv20);
        slots[21] = (TextView) findViewById(R.id.tv21);
        slots[22] = (TextView) findViewById(R.id.tv22);
        slots[23] = (TextView) findViewById(R.id.tv23);
        slots[24] = (TextView) findViewById(R.id.tv24);
        slots[25] = (TextView) findViewById(R.id.tv25);
        slots[26] = (TextView) findViewById(R.id.tv26);
        slots[27] = (TextView) findViewById(R.id.tv27);
        slots[28] = (TextView) findViewById(R.id.tv28);
        slots[29] = (TextView) findViewById(R.id.tv29);
        slots[30] = (TextView) findViewById(R.id.tv30);
        slots[31] = (TextView) findViewById(R.id.tv31);
        slots[32] = (TextView) findViewById(R.id.tv32);
        slots[33] = (TextView) findViewById(R.id.tv33);
        slots[34] = (TextView) findViewById(R.id.tv34);
        slots[35] = (TextView) findViewById(R.id.tv35);
        slots[36] = (TextView) findViewById(R.id.tv36);
        slots[37] = (TextView) findViewById(R.id.tv37);
        slots[38] = (TextView) findViewById(R.id.tv38);
        slots[39] = (TextView) findViewById(R.id.tv39);
        slots[40] = (TextView) findViewById(R.id.tv40);
        slots[41] = (TextView) findViewById(R.id.tv41);
        slots[42] = (TextView) findViewById(R.id.tv42);
        slots[43] = (TextView) findViewById(R.id.tv43);
        slots[44] = (TextView) findViewById(R.id.tv44);
        slots[45] = (TextView) findViewById(R.id.tv45);
        slots[46] = (TextView) findViewById(R.id.tv46);
        slots[47] = (TextView) findViewById(R.id.tv47);
        slots[48] = (TextView) findViewById(R.id.tv48);
        slots[49] = (TextView) findViewById(R.id.tv49);
    }
}
