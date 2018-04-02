package com.eslamwael74.inq.samplechatapp.Model;

public class Chat {

    int typeOfSend = 1;

    String message;

    String createdTime;

    int type;

    public Chat() {
    }

    public Chat(String message, String createdTime, int type, int typeOfSend) {
        this.message = message;
        this.createdTime = createdTime;
        this.type = type;
        this.typeOfSend = typeOfSend;
    }

    public Chat(String message, String createdTime, int type) {
        this.message = message;
        this.createdTime = createdTime;
        this.type = type;
    }

    public int getTypeOfSend() {
        return typeOfSend;
    }

    public void setTypeOfSend(int typeOfSend) {
        this.typeOfSend = typeOfSend;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
