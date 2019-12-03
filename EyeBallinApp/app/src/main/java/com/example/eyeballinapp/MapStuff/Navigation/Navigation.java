// This class will interact with the voice classes to gather user input and create a navigation plan.
// It will also generate the text that will be spoken to navigate the user to their location
// This class will also create a location listener that will be used to navigate the user
// For testing

package com.example.eyeballinapp.MapStuff.Navigation;

import android.content.Context;
import android.widget.Toast;

import com.example.eyeballinapp.MapStuff.Graph.Route;
import com.example.eyeballinapp.MapStuff.Location.CustomLocation;



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
        // FIXME: 12/3/2019 Hardcoded the destination.
        map.setDestination("458");
        Toast.makeText(context, "Dest: " + destination, Toast.LENGTH_SHORT).show();
    }


    public void navigate(String direction) {
       switch(direction) {
           case "up": userLocation.setLocation(userLocation.getPositionX(), userLocation.getPositionY() + stepLength, userLocation.getFloorNum());
               //update user location in map
               map.updateUser(userLocation);
               //recalculate route with new location
               // FIXME: 12/3/2019 When calculating route the 2nd or 3rd time it returns a empty route.
               steps = map.calculateRoute();
               //print out the distance between current step and next step.
               Toast.makeText(mContext, "Distance to next step: " + steps.getStep(0).getDistance() + "\nSize: " + steps.getStepList().size(), Toast.LENGTH_SHORT).show();
               break;
           case "down":
               break;
           case "left":
               break;
           case "right":
               break;
               default:
       }
    }


}
