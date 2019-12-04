// This class will interact with the voice classes to gather user input and create a navigation plan.
// It will also generate the text that will be spoken to navigate the user to their location
// This class will also create a location listener that will be used to navigate the user
// For testing

package com.example.eyeballinapp.MapStuff.Navigation;

import android.content.Context;

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
        userLocation = new CustomLocation(0,0,1);
        map.setDestination(destination);
        map.updateUser(userLocation);
        steps = map.calculateRoute();
        //Toast.makeText(context, "Dest: " + destination, Toast.LENGTH_SHORT).show();
    }


    public void navigate(String direction) {
       switch(direction) {
           //do a post here using eventbus
           case "forward": move(userLocation.getPositionX(), userLocation.getPositionY() + stepLength, userLocation.getFloorNum());
               break;
           case "back": move(userLocation.getPositionX(), userLocation.getPositionY() - stepLength, userLocation.getFloorNum());
               break;
           case "left": move(userLocation.getPositionX() - stepLength, userLocation.getPositionY() , userLocation.getFloorNum());
               break;
           case "right": move(userLocation.getPositionX() + stepLength, userLocation.getPositionY(), userLocation.getFloorNum());
               break;
           case "up": if(userLocation.getFloorNum() != 4)
               move(userLocation.getPositionX(), userLocation.getPositionY(), userLocation.getFloorNum()+1);
               break;
           case "down": if(userLocation.getFloorNum() != 0)
               move(userLocation.getPositionX(), userLocation.getPositionY(), userLocation.getFloorNum()-1);
               break;
               default:
       }
       EventBus.getDefault().post(new OnButtonClickedMessage("UPDATE"));
    }

    public List<Step> getStepList() {
        return steps.getStepList();
    }

    private void move(double x, double y, int floor) {
        userLocation.setLocation(x,y,floor);
        map.updateUser(userLocation);
        steps = map.calculateRoute();
    }


}