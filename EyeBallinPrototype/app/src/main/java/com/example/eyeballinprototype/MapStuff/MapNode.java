package com.example.eyeballinprototype.MapStuff;

import android.location.Location;
import java.util.HashMap;

public class MapNode {
    String name;
    Location location; // GPS location of the node
    int floor;
    HashMap<String, Integer> adjacency; //one slice of the adjacency matrix.

    public MapNode(String nodeName, HashMap<String, Integer> adjacentNodes, Location loc, int floor) {
        name = nodeName;
        adjacency = adjacentNodes;
        location = loc;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Integer> getAdjacency() { return adjacency; }

    public int isAdjacent(String nodeName) {
        if (adjacency.containsKey(nodeName)) {
            return adjacency.get(nodeName); //return the distance if the node is adjacent to this one.
        }
        return -1; //return -1 if the node is not adjacent.
    }

    public Location getLocation() {
        return location;
    }
}
