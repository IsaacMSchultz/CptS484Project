package com.example.eyeballinapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.eyeballinapp.MapStuff.MapGraph;
import com.example.eyeballinapp.MapStuff.MapNode;
import com.example.eyeballinapp.MapStuff.XmlParser;
import com.example.eyeballinapp.SpeechStuff.ListenActivity;
import com.example.eyeballinapp.SpeechStuff.NavigationActivity;
import com.example.eyeballinapp.SpeechStuff.SpeakActivity;
import com.example.eyeballinapp.SpeechStuff.SpeechParser;
import com.example.eyeballinapp.SpeechStuff.SpeechResult;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_LISTENER = 0;
    private SpeechParser parser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        startListener(getString(R.string.init_greeting));
        parser = new SpeechParser();

        if (!checkPermission(Manifest.permission.SEND_SMS)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        // Populate matrix from XML data
        XmlParser test = new XmlParser(this);
        MapGraph graph = new MapGraph();
//        try {
//            graph = test.tempParse();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
        graph = test.tempParse();

        List<MapNode> steps = graph.navigateFrom("East Doors", "458");

        // Just used for debugging
        System.out.print("stopping point");
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

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }


}
