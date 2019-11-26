package com.example.eyeballinapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_LISTENER = 0;
    private SpeechParser parser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        startListener(getString(R.string.init_greeting));
        parser = new SpeechParser();
    }

    public void startListener(String message) {
        Intent myIntent = new Intent(MainActivity.this, ListenActivity.class);
        SpeechResult.get(getApplicationContext()).setSpeechText(message);
        startActivityForResult(myIntent, REQUEST_LISTENER);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_LISTENER: {
                String testMessage = SpeechResult.get(getApplicationContext()).getMessage();
                startProgramLoop(testMessage);
                break;
            }
        }
    }

    private void startProgramLoop(String message) {
        //parse the message
        switch(parser.getSpeechCommand(message)) {
            case "navigate": SpeakActivity say = new SpeakActivity(getApplicationContext(), getString(R.string.navigate) + " " + parser.getDestination());
                parser.clear();
                Intent myIntent = new Intent(MainActivity.this, NavigationActivity.class);
                startActivity(myIntent);
                break;
            case "repeat": startListener(getString(R.string.navigate) + " " + parser.getDestination() + " " +getString(R.string.ask_again));
                break;
            case "no": startListener(getString(R.string.init_greeting));
                break;
            case "room number": startListener(getString(R.string.room_number));
                //Toast.makeText(getApplicationContext(), String.valueOf(parser.getDestination() == null), Toast.LENGTH_SHORT).show();
                break;
            default: startListener(getString(R.string.please_repeat));
        }
    }

}
