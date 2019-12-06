package com.example.eyeballinapp.MapStuff.Graph;

import android.location.Location;

import com.example.eyeballinapp.MapStuff.Graph.EBVector;
import com.example.eyeballinapp.MapStuff.Graph.MapNode;
import com.example.eyeballinapp.MapStuff.Location.CustomLocation;

public class Step {
    private MapNode node;
    private EBVector vector;
    private String direction;

    public Step(MapNode node, double distanceX, double distanceY) {
        this.node = node;
        this.vector = new EBVector(distanceX, distanceY);
    }

    public Step(MapNode node, Location loc) {
        CustomLocation nodeLocation = (CustomLocation) node.getLocation();
        CustomLocation l = (CustomLocation) loc;
        double y = nodeLocation.getPositionY() - l.getPositionY();
        double x = nodeLocation.getPositionX() - l.getPositionX();

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

        this.vector = new EBVector(x, y);
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
}
