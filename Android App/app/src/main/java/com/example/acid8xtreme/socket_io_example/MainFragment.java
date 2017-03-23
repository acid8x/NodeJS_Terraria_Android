package com.example.acid8xtreme.socket_io_example;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;

public class MainFragment extends Fragment {

    public Socket mSocket;
    private static Handler mHandler;

    public static MainFragment newInstance(Handler handler) {
        mHandler = handler;
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mSocket = IO.socket(MainActivity.SOCKET_IO_SERVER);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        mSocket.on("completeItem", onCompleteItem);
        mSocket.on("stackOnly", onStackOnly);
        mSocket.on("playerInfo", onPlayerInfo);
        mSocket.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off("completeItem", onCompleteItem);
        mSocket.off("stackOnly", onStackOnly);
        mSocket.off("playerInfo", onPlayerInfo);
    }

    public void attemptSend(String type, String message) {
        if (mSocket != null && mSocket.connected()) mSocket.emit(type, message);
    }

    private Emitter.Listener onCompleteItem = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String message = (String) args[0];
                    Message SocketMsg = mHandler.obtainMessage(Constants.MESSAGE_COMPLETE_ITEM);
                    Bundle bundle = new Bundle();
                    bundle.putString("MESSAGE", message);
                    SocketMsg.setData(bundle);
                    mHandler.sendMessage(SocketMsg);
                }
            });
        }
    };

    private Emitter.Listener onStackOnly = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String message = (String) args[0];
                    Message SocketMsg = mHandler.obtainMessage(Constants.MESSAGE_STACK_ONLY);
                    Bundle bundle = new Bundle();
                    bundle.putString("MESSAGE", message);
                    SocketMsg.setData(bundle);
                    mHandler.sendMessage(SocketMsg);
                }
            });
        }
    };

    private Emitter.Listener onPlayerInfo = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String message = (String) args[0];
                    int index = 0;
                    int[] val = {0,0,0,0};
                    char[] array = message.toCharArray();
                    for (char c : array) {
                        if (c > 47 && c < 58) val[index] = (val[index] * 10) + (c-48);
                        else index++;
                    }
                    Message SocketMsg = mHandler.obtainMessage(Constants.MESSAGE_PLAYER_INFO);
                    Bundle bundle = new Bundle();
                    bundle.putIntArray("PLAYERINFO", val);
                    SocketMsg.setData(bundle);
                    mHandler.sendMessage(SocketMsg);
                }
            });
        }
    };
}