package com.example.acid8xtreme.socket_io_example;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

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
        ChatApplication app = (ChatApplication) getActivity().getApplication();
        mSocket = app.getSocket();
        mSocket.on("completeItem", onCompleteItem);
        mSocket.on("stackOnly", onStackOnly);
        mSocket.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off("completeItem", onCompleteItem);
        mSocket.off("stackOnly", onStackOnly);
    }

    public void attemptSend(String message) {
        if (mSocket != null && mSocket.connected() && !message.equals("")) mSocket.emit("message", message);
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
}