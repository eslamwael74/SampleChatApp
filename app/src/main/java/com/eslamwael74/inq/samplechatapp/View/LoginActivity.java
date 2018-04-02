package com.eslamwael74.inq.samplechatapp.View;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eslamwael74.inq.samplechatapp.ApplicationClass;
import com.eslamwael74.inq.samplechatapp.Constants;
import com.eslamwael74.inq.samplechatapp.Model.Chat;
import com.eslamwael74.inq.samplechatapp.R;

import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class LoginActivity extends Activity {

    private EditText mUsernameView;

    private String mUsername;

    private Socket mSocket;
    private Button signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    void init() {

        mUsernameView = findViewById(R.id.username_input);
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(v -> {
            attemptLogin();
        });

        ApplicationClass applicationClass = (ApplicationClass) getApplication();
        mSocket = applicationClass.getSocket();
        mSocket.on(Constants.EVENT_LOGIN,onLogin);
    }

    private void attemptLogin() {
        // Reset errors.
        mUsernameView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString().trim();

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            mUsernameView.setError(getString(R.string.error_field_required));
            mUsernameView.requestFocus();
            return;
        }

        mUsername = username;

        // perform the user login attempt.
        mSocket.emit("add user", username);
    }

    private Emitter.Listener onLogin = args -> {
        JSONObject object = (JSONObject) args[0];

        Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
        intent.putExtra("username", mUsername);
        startActivity(intent);
        finishAffinity();
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.off("login", onLogin);

    }
}
