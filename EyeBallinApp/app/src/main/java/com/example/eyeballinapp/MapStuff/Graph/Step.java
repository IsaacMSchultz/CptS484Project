package com.example.eyeballinapp.MapStuff.Graph;

import android.location.Location;

import com.example.eyeballinapp.MapStuff.Graph.EBVector;
import com.example.eyeballinapp.MapStuff.Graph.MapNode;
import com.example.eyeballinapp.MapStuff.Location.CustomLocation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Step {
    private MapNode node;
    private EBVector vector;
    private String direction;
    private boolean hasZComponent;

    public Step(MapNode node, double distanceX, double distanceY) {
        this.node = node;
        this.vector = new EBVector(distanceX, distanceY);
    }

    public Step(MapNode node, Location loc) {
        CustomLocation nodeLocation = (CustomLocation) node.getLocation();
        CustomLocation l = (CustomLocation) loc;
        double y = nodeLocation.getPositionY() - l.getPositionY();
        double x = nodeLocation.getPositionX() - l.getPositionX();
        int z = nodeLocation.getFloorNum() - l.getFloorNum();

        if (z == 0)
            hasZComponent = false;
        else
            hasZComponent = true;

        this.node = node;
        this.vector = new EBVector(x, y);
    }

    public void updateVector(EBVector v) {
        this.vector = v;
    }

    public void updateVector(Location loc) {
        CustomLocation nodeLocation = (CustomLocation) node.getLocation();
        CustomLocation l = (CustomLocation) loc;
        double y = nodeLocation.getPositionY() - l.getPositionY();
        double x = nodeLocation.getPositionX() - l.getPositionX();
        int z = nodeLocation.getFloorNum() - l.getFloorNum();

        if (z == 0)
            hasZComponent = false;
        else
            hasZComponent = true;

        this.vector = new EBVector(x, y);
    }

    // determine if vector is 0 without calculation.
    public boolean hasZComponent() {
        return hasZComponent;
    }

    // determine if there is a direction with a 0 component. (for finding near-right turns)
    public boolean has0Component() {
        if (vector.getX() == 0 || vector.getY() == 0)
            return true;
        else
            return false;
    }

    // get the magnitude of the distance to the next node.
    public double getDistance() {
        return vector.getMagnitude();
    }

    public EBVector getVector() {
        return vector;
    }

    public MapNode getNode() {
        return node;
    }

    public void setDirection(String dir) {
        direction = dir;
    }

    public String getDirection() {
        return direction;
    }

    private boolean ghettoRequiredNodesList(String name) {
        Pattern p = Pattern.compile("(Outside Hallway|Water Fountain|Outside.*61)"); // anything that contains outside hallway, water fountain, or outside the seminar rooms is required.
        Matcher m = p.matcher(name);
        if (m.matches())
            return true;
        else
            return false;
    }
}
