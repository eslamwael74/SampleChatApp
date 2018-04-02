package com.eslamwael74.inq.samplechatapp.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eslamwael74.inq.samplechatapp.ApplicationClass;
import com.eslamwael74.inq.samplechatapp.ChatAdapter;
import com.eslamwael74.inq.samplechatapp.CircleImageView;
import com.eslamwael74.inq.samplechatapp.Constants;
import com.eslamwael74.inq.samplechatapp.Model.Chat;
import com.eslamwael74.inq.samplechatapp.R;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatActivity extends AppCompatActivity {


    private static final String TAG = "ChatActivity";
    private BroadcastReceiver broadcastReceiver;


    RecyclerView mRecyclerView;
    View mBackBtn;
    CircleImageView mProfileImage;
    TextView tvUserName;
    EditText editTextSendMessage;
    FloatingActionButton fabSendMessage;
    CardView mCardMessage;


    int orderId, userType, userId;
    String userName, token, image;
    String message;
    private boolean mTyping = false;
    Socket socket;
    private Boolean isConnected = true;


    Calendar currentTime = Calendar.getInstance();

    String messageEscape;
    ChatAdapter chatAdapter;
    ArrayList<Chat> chats = new ArrayList<>();
//    ChatData chatData;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initWidgets();

        init();


    }

    public void initWidgets() {
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mBackBtn = findViewById(R.id.back_btn);
        tvUserName = findViewById(R.id.name);
        editTextSendMessage = findViewById(R.id.edit_text_send_message);
        fabSendMessage = findViewById(R.id.send_btn);
        mCardMessage = findViewById(R.id.card_message);

        editTextSendMessage.setOnTouchListener((v, event) -> {
            new Handler().postDelayed(() -> {
                if (chats != null)
                    mRecyclerView.scrollToPosition(chats.size() - 1);
            }, 300);
            return false;
        });

        editTextSendMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    fabSendMessage.animate().rotation(-360f).setDuration(100);
                    fabSendMessage.setClickable(true);
                    fabSendMessage.setBackgroundTintList(getColorStateList(R.color.colorAccent));
                } else {
                    fabSendMessage.animate().rotation(360f).setDuration(250);
                    fabSendMessage.setClickable(false);
                    fabSendMessage.setBackgroundTintList(getColorStateList(R.color.gray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBackBtn.setOnClickListener(v -> onBackPressed());

        fabSendMessage.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
    }


    public void init() {

        ApplicationClass applicationClass = (ApplicationClass) getApplication();
        socket = applicationClass.getSocket();
        socket.on(Socket.EVENT_CONNECT, onConnect);
        socket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        socket.on(Socket.EVENT_CONNECT_ERROR,onConnectError);
        socket.on(Socket.EVENT_CONNECT_TIMEOUT,onConnectError);

        socket.on(Constants.EVENT_NEW_MESSAGE,onNewMessage);
        socket.on(Constants.EVENT_TYPING,onTyping);
        socket.on(Constants.EVENT_STOP_TYPING,onStopTyping);

        //then connect your app to server
        socket.connect();

        tvUserName.setText(userName);
        //and here you should set Cooker data if you logged in as a user

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(() -> {
                if (!isConnected) {
                    if (null != userName)
                        socket.emit("add user", userName);
                    isConnected = true;
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = args -> runOnUiThread(() -> {
        Log.i(TAG, "diconnected");
        isConnected = false;

    });

    private Emitter.Listener onConnectError = args -> runOnUiThread(() -> Log.e(TAG, "Socket Error connecting"));

    private Emitter.Listener onNewMessage = args -> {
        runOnUiThread(() -> {
            JSONObject object = (JSONObject) args[0];
            try {
                userName = object.getString("username");
                message = object.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    };

    private Emitter.Listener onTyping = args -> {
        runOnUiThread(() -> {
            JSONObject object = (JSONObject) args[0];
            try {
                userName = object.getString("username");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    };

    private Emitter.Listener onStopTyping = args -> {
        runOnUiThread(() -> {
            JSONObject object = (JSONObject) args[0];
            try {
                userName = object.getString("username");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    };

    private Runnable onTypingTimeout = () -> {
        if (!mTyping)
            return;

        mTyping = false;

    };

}
