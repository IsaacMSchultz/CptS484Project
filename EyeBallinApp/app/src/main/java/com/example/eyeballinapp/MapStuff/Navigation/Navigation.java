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

    private SpeakActivity say;

    private int stepLength = 1;
    Route steps;

    public Navigation(Context context, String destination) {
        mContext = context;
        map = new EyeBallinMap(mContext);
        //starting point
        userLocation = new CustomLocation(0, 0, 1);
        map.setDestination(destination);
        map.updateUser(userLocation);
        steps = map.calculateRoute();
        //setDirection("highest");
        say = new SpeakActivity(mContext, "");
        //Toast.makeText(context, "Dest: " + destination, Toast.LENGTH_SHORT).show();
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
                move(userLocation.getPositionX() + stepLength, userLocation.getPositionY(), userLocation.getFloorNum(),"right");
                lastLocation = "right";
                break;
            case "up":
                if (userLocation.getFloorNum() != 4) {
                    move(userLocation.getPositionX(), userLocation.getPositionY(), userLocation.getFloorNum() + 1, "up");
                    //say.speak("Going up, you are at floor" + userLocation.getFloorNum());
                    //lastLocation = "up";
                }
                break;
            case "down":
                if (userLocation.getFloorNum() != 1) {
                    move(userLocation.getPositionX(), userLocation.getPositionY(), userLocation.getFloorNum() - 1, "down");
                    say.speak( "Going down, you are at floor" + userLocation.getFloorNum());
                    //lastLocation = "down";
                }
                break;
            default:
        }
        EventBus.getDefault().post(new OnButtonClickedMessage("UPDATE", steps.getGeneralWalkingDirection(lastLocation)));
    }

    public List<Step> getStepList() {
        return steps.getStepList();
    }

    private void move(double x, double y, int floor, String direction) {
        userLocation.setLocation(x, y, floor);
        map.updateUser(userLocation);
        int stepLength = steps.getStepList().size();
        steps = map.calculateRoute();

        //step size decreased so going on the right path.
        if(stepLength != steps.getStepList().size() && !direction.equals("up")) {
            say.speak( steps.getVoiceDirections(direction));
            EventBus.getDefault().post(new OnButtonClickedMessage("CHANGED", steps.getGeneralWalkingDirection(direction)));
        }

        if(stepLength != steps.getStepList().size() && direction.equals("up")) {
            say.speak( steps.getVoiceDirections("left"));
        }

        if(steps.getStepList().size() == 1 && steps.getStep(0).getDistance() == 0) {
            say.speak( "Yay! You have reached your destination");
            EventBus.getDefault().post(new OnButtonClickedMessage("FINISHED"));
        }
    }
}
