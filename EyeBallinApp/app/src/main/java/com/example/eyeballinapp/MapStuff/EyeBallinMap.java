package com.example.eyeballinapp.MapStuff;

import com.example.eyeballinapp.MapStuff.Graph.MapGraph;

// EyeBallinMap is a map that makes use of the MapGraph class to navigate the user to a given location
public class EyeBallinMap {
//    LocationProvider //LocationProvider to determine

    MapGraph map;

    public EyeBallinMap() {
        // build the map using the constructor in here
        XmlParser parser = new XmlParser(map);
        map = parser.tempParse();
    }

    // add / remove the user node in the graph, connect to the closest node.
    public void updateUser(CustomLocation loc) {

    }

    // return true if the node is int he graph.
    public boolean setDestination(String destination) {
        return false;
    }

    //run dijsktras and return a route to take
    public Route calculateRoute(String destination) {
        return null;
    }

    public Step nextStep() {
        return null;
    }
}
