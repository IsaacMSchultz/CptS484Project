// This class will interact with the voice classes to gather user input and create a navigation plan.
// It will also generate the text that will be spoken to navigate the user to their location
// This class will also create a location listener that will be used to navigate the user
// For testing

package com.example.eyeballinapp.MapStuff.Navigation;

import android.content.Context;
import android.widget.Toast;

import com.example.eyeballinapp.EventBus.OnButtonClickedMessage;
import com.example.eyeballinapp.MapStuff.Graph.Route;
import com.example.eyeballinapp.MapStuff.Graph.Step;
import com.example.eyeballinapp.MapStuff.Location.CustomLocation;
import com.example.eyeballinapp.SpeechStuff.SpeakActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class Navigation {
    EyeBallinMap map;
    Context mContext;
    CustomLocation userLocation;

    private String lastLocation = "forward";
    private String currentLocation = "forward";
    private String lastDirection = "N/A";

    SpeakActivity speech;

    private int stepLength = 1;
    Route steps;

    public Navigation(Context context, String destination) {
        mContext = context;
        map = new EyeBallinMap(mContext);
        //starting point
        userLocation = new CustomLocation(0, 55, 1);
        map.setDestination(destination);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        speech = new SpeakActivity(mContext, "test");
    }

    public void navigate(String direction) {
        switch (direction) {
            //do a post here using eventbus
            case "forward":
                move(userLocation.getPositionX(), userLocation.getPositionY() + stepLength, userLocation.getFloorNum(), "forward");
                lastLocation = "forward";
                break;
            case "back":
                move(userLocation.getPositionX(), userLocation.getPositionY() - stepLength, userLocation.getFloorNum(), "back");
                lastLocation = "back";
                break;
            case "left":
                move(userLocation.getPositionX() - stepLength, userLocation.getPositionY(), userLocation.getFloorNum(), "left");
                lastLocation = "left";
                break;
            case "right":
                move(userLocation.getPositionX() + stepLength, userLocation.getPositionY(), userLocation.getFloorNum(), "right");
                lastLocation = "right";
                break;
            case "up":
                if (userLocation.getFloorNum() != 4)
                    move(userLocation.getPositionX(), userLocation.getPositionY(), userLocation.getFloorNum() + 1, "elevator");
                lastLocation = "up";
                break;
            case "down":
                if (userLocation.getFloorNum() != 1)
                    move(userLocation.getPositionX(), userLocation.getPositionY(), userLocation.getFloorNum() - 1, "elevator");
                lastLocation = "down";
                break;
            default:
        }
        EventBus.getDefault().post(new OnButtonClickedMessage("UPDATE"));
    }

    public List<Step> getStepList() {
        return steps.getStepList();
    }

    private void move(double x, double y, int floor, String direction) {
        userLocation.setLocation(x, y, floor); //create new location where the user is now
        map.updateUser(userLocation); //update the user's location in the map
        steps = map.calculateRoute(); // re-calculate the route
        String currentDirection = steps.getGeneralWalkingDirection(direction); //get the general walking direction that is needed to reach the next step

        // for mocking elevator stuff
        if (currentDirection.equals("elevator_up")) { //if we need to go up still in the elevator
            speech.speak("demo elevator simulation. go up");
            currentDirection = "elevator";
        } else if (currentDirection.equals("elevator_down")) { //if we need to go down still in the elevator
            speech.speak("demo elevator simulation. go down");
            currentDirection = "elevator";
        }

        if (lastDirection != currentDirection) { //if the direction we need to go to has changed, restate the directions.
            speech.speak(steps.getVoiceDirections(direction));
            lastDirection = currentDirection; //update the last direction we need to go to.
        }
    }
}
