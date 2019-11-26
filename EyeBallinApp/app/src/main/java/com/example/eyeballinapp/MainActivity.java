package com.example.eyeballinapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_LISTENER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        startListener(getString(R.string.init_greeting));
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
        switch(message) {
            case "navigate": SpeakActivity say = new SpeakActivity(getApplicationContext(), "Starting navigation...");
                Intent myIntent = new Intent(MainActivity.this, NavigationActivity.class);
                startActivity(myIntent);
                break;
            default: Toast.makeText(this ,"Dont say navigate or this will be an infinite loop", Toast.LENGTH_SHORT).show();
                startListener(message);
        }
    }

}
