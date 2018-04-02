package com.eslamwael74.inq.samplechatapp;

import android.app.Application;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by EslamWael74 on 4/1/2018.
 */
public class ApplicationClass extends Application {

    private Socket socket;

    {
        try {
            socket = IO.socket(Constants.SERVER_URL);
            socket.connect();
        } catch (URISyntaxException e) {
           throw new RuntimeException();
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
