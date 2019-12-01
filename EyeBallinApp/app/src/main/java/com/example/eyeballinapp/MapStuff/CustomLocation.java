package com.example.eyeballinapp.MapStuff;

import android.location.Location;


public class CustomLocation extends Location {

    private double positionX;
    private double positionY;
    private int floorNum;

    public CustomLocation(double x, double y, int floor){
        // This is required
        super("PlaceHolder");
        this.positionX = x;
        this.positionY = y;
        this.floorNum = floor;
    }

    // TODO: Override the DistanceTo function
}
