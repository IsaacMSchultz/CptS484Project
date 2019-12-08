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

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class Navigation {
    EyeBallinMap map;
    Context mContext;
    CustomLocation userLocation;

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
        setDirection("highest");
        //Toast.makeText(context, "Dest: " + destination, Toast.LENGTH_SHORT).show();
    }

    public void navigate(String direction) {
        switch (direction) {
            //do a post here using eventbus
            case "forward":
                move(userLocation.getPositionX(), userLocation.getPositionY() + stepLength, userLocation.getFloorNum());
                break;
            case "back":
                move(userLocation.getPositionX(), userLocation.getPositionY() - stepLength, userLocation.getFloorNum());
                break;
            case "left":
                move(userLocation.getPositionX() - stepLength, userLocation.getPositionY(), userLocation.getFloorNum());
                break;
            case "right":
                move(userLocation.getPositionX() + stepLength, userLocation.getPositionY(), userLocation.getFloorNum());
                break;
            case "up":
                if (userLocation.getFloorNum() != 4)
                    move(userLocation.getPositionX(), userLocation.getPositionY(), userLocation.getFloorNum() + 1);
                break;
            case "down":
                if (userLocation.getFloorNum() != 1)
                    move(userLocation.getPositionX(), userLocation.getPositionY(), userLocation.getFloorNum() - 1);
                break;
            default:
        }
        EventBus.getDefault().post(new OnButtonClickedMessage("UPDATE"));
        Toast.makeText(mContext, steps.getStep(0).getDistance() + "\tSize: " + steps.numberOfSteps() + "\n" +
                "X: " + userLocation.getPositionX() + "\tY: " + userLocation.getPositionY() + "\tZ: " + userLocation.getFloorNum(), Toast.LENGTH_SHORT).show();
    }

    public List<Step> getStepList() {
        return steps.getStepList();
    }

    private void move(double x, double y, int floor) {
        userLocation.setLocation(x, y, floor);
        map.updateUser(userLocation);
        int stepLength = steps.getStepList().size();
        steps = map.calculateRoute();
        if(stepLength != steps.getStepList().size())
            EventBus.getDefault().post(new OnButtonClickedMessage("CHANGED"));
    }

    //level should be high or low, so either grab the lowest direction or the highest.
    //distance = 29 others 30, which means 29 is the closer node and whichever direction that is
    //thats what we set.
    /*
    *  sets the direction of the current step by calculating future steps and getting which one
    *  is closer.
    * */
    private void setDirection(String level) {
        CustomLocation tempLoc = userLocation;
        double[] direction = new double[4];
        int index = 0;
        move(userLocation.getPositionX(), userLocation.getPositionY() + stepLength, userLocation.getFloorNum());
        direction[0] = steps.getStep(0).getDistance();
        move(userLocation.getPositionX(), userLocation.getPositionY() - stepLength, userLocation.getFloorNum());
        direction[1] = steps.getStep(0).getDistance();
        move(userLocation.getPositionX() + stepLength, userLocation.getPositionY(), userLocation.getFloorNum());
        direction[2] = steps.getStep(0).getDistance();
        move(userLocation.getPositionX() - stepLength, userLocation.getPositionY(), userLocation.getFloorNum());
        direction[3] = steps.getStep(0).getDistance();
        if(level.equals("highest")) {
            index = getHighest(direction);
            String dir = directionString(index);
            steps.getStep(0).setUserDirection(dir);
        }
        else {

        }
        move(tempLoc.getPositionX(), tempLoc.getPositionY(), tempLoc.getFloorNum());
    }

    private int getHighest(double[] dir) {
        int index = 0;
        for(int i = 1; i < dir.length; i++) {
            if(dir[i] > dir[index]) {
                index = i;
            }
        }
        return index;
    }

    private void getLowest(double[] dir) {

    }

    private String directionString(int dirIndex) {
        switch(dirIndex) {
            case 0: return "forward";
            case 1: return "back";
            case 2: return "right";
            case 3: return "left";
        }
        return "forward";
    }


}
