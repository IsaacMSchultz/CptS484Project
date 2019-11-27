package com.example.eyeballinapp.MapStuff;

import android.location.Location;

import java.util.HashMap;

public class MapNode {
    private String name;
    private Location location; // GPS location of the node
    private int floor;
    private HashMap<String, Integer> adjacency; //one slice of the adjacency matrix.

    public MapNode(String nodeName, HashMap<String, Integer> adjacentNodes, Location loc, int floor) {
        name = nodeName;
        adjacency = adjacentNodes;
        location = loc;
    }

    public MapNode(String nodeName, int floor) {
        name = nodeName;
        adjacency = new HashMap<String, Integer>();
        location = new Location("nothing");
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public HashMap<String, Integer> getAdjacency() {
        return adjacency;
    }

    public int isAdjacent(String nodeName) {
        if (adjacency.containsKey(nodeName)) {
            return adjacency.get(nodeName); //return the distance if the node is adjacent to this one.
        }
        return -1; //return -1 if the node is not adjacent.
    }

    //add an edge to this node. boolean return informs if the operation was successful
    public boolean addEdge(String destination, int distance) {
        if (!adjacency.containsKey(destination)) {
            adjacency.put(destination, distance);
            return true;
        } else {
            return false;
        }
    }

    //remove an edge from this node. boolean return informs if the operation was successful
    public boolean removeEdge(String destination) {
        if (adjacency.containsKey(destination)) {
            adjacency.remove(destination);
            return true;
        } else {
            return false;
        }
    }
}

