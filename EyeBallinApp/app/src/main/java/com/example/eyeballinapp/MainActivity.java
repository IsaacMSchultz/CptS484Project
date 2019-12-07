package com.example.eyeballinapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.eyeballinapp.MapStuff.Location.CustomLocation;
import com.example.eyeballinapp.MapStuff.Graph.Route;
import com.example.eyeballinapp.MapStuff.Navigation.EyeBallinMap;
import com.example.eyeballinapp.SpeechStuff.ListenActivity;
import com.example.eyeballinapp.SpeechStuff.NavigationActivity;
import com.example.eyeballinapp.SpeechStuff.SpeakActivity;
import com.example.eyeballinapp.SpeechStuff.SpeechParser;
import com.example.eyeballinapp.SpeechStuff.SpeechResult;


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


//        EyeBallinMap map = new EyeBallinMap(this);
//        map.setDestination("458");
//
//        map.updateUser(new CustomLocation(0,-10,1)); //10 feet outside the south doors
//        Route steps = map.calculateRoute(); //expecting 14 steps, starting at "South Doors"
//
//        map.updateUser(new CustomLocation(2,2,1));
//        steps = map.calculateRoute(); //expecting 13 steps, starting at "Outside Hallway 1"

        /* Im not sure why, im probably really stupid. I think it has something to do with
         * the route method in MapGraph. I cant follow all the other method calls.
         */
//        map.updateUser(new CustomLocation(2,2,1));
//        steps = map.calculateRoute(); //expecting 13 steps, starting at "Outside Hallway 1"
//
//        // Just used for debugging
//        System.out.print("stopping point");

        //EyeBallinMap map = new EyeBallinMap(this);
        //if(map.setDestination("458")) {
        //    Intent myIntent = new Intent(MainActivity.this, NavigationActivity.class);
       //     myIntent.putExtra("DESTINATION", "458");
        //    startActivity(myIntent);
        //}
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
                if(testMessage == null)
                    testMessage = "repeat";
                if(testMessage == "navigate") {
                    EyeBallinMap map = new EyeBallinMap(this);
                    if(!map.setDestination(parser.getDestination()))
                        testMessage = "room number";
                }
                startProgramLoop(testMessage);
                break;
            }
        }
    }

    private void startProgramLoop(String message) {
        //parse the message
        switch(parser.getSpeechCommand(message)) {
            // TODO: 12/4/2019 Check if destination exists before proceeding
            case "navigate": SpeakActivity say = new SpeakActivity(getApplicationContext(), getString(R.string.navigate) + " " + parser.getDestination());
                //parser.clear();
                Log.d("TEST", parser.getDestination());
                Toast.makeText(this, parser.getDestination(), Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(MainActivity.this, NavigationActivity.class);
                myIntent.putExtra("DESTINATION", parser.getDestination());
                startActivity(myIntent);
                parser.clear();
                break;
            case "repeat": startListener(getString(R.string.navigate) + " " + parser.getDestination() + " " +getString(R.string.ask_again));
                break;
            case "no": startListener(getString(R.string.init_greeting));
                break;
            case "room number": startListener(getString(R.string.room_number));
                //Toast.makeText(getApplicationContext(), String.valueOf(parser.getDestination() == null), Toast.LENGTH_SHORT).show();
                break;
            case "not exist":startListener(getString(R.string.not_exist));
                break;
            default: startListener(getString(R.string.please_repeat));
        }
    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }


}
