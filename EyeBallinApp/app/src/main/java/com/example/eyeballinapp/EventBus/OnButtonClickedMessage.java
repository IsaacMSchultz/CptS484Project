package com.example.eyeballinapp.EventBus;

public class OnButtonClickedMessage {

    private String message;

    public OnButtonClickedMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
