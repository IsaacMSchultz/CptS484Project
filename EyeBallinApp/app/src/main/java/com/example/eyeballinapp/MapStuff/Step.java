package com.example.eyeballinapp.MapStuff;

import android.location.Location;

import com.example.eyeballinapp.MapStuff.Graph.MapNode;

public class Step {
    private MapNode node;
    private EBVector vector;

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
}
