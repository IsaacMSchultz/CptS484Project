package com.example.eyeballinapp.MapStuff;

import com.example.eyeballinapp.MapStuff.Graph.MapNode;

public class Step {
    private MapNode source;
    private EBVector vector;

    public Step(MapNode node, double distanceX, double distanceY) {

    }

    // get the magnitude of the distance to the next node.
    public double getDistance() {
        return 0.0;
        //return the magnitude of the vector
    }

    public EBVector getDirection() {
        return vector;
    }


}
