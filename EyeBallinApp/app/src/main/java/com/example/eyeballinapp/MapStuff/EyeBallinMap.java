package com.example.eyeballinapp.MapStuff;

import com.example.eyeballinapp.MapStuff.Graph.MapGraph;
import com.example.eyeballinapp.MapStuff.Graph.MapNode;

// EyeBallinMap is a map that makes use of the MapGraph class to navigate the user to a given location
public class EyeBallinMap {
    MapGraph map;
    String destination;
    Route route;

    public EyeBallinMap() {
    // build the map using the constructor in here
    XmlParser parser = new XmlParser(map);
    map = parser.tempParse();
}

    // add / remove the user node in the graph, connect to the closest node.
    public void updateUser(CustomLocation loc) {
        map.removeNode("USER"); //remove the old node (also removes edges connected to it)
        Step closestNode = map.nearestNode(loc); // get the closest node before adding the new one.
        map.addNode("USER", loc); // add the new node

        map.addEdge("USER", closestNode.getNode().getName(), closestNode.getDistance()); // add the edge between the user and the closest node.
    }

    // set the current destination that you want to navigate to.
    // return true if the node is in the graph.
    public boolean setDestination(String destination) {
        if (map.containsNode(destination)) {
            this.destination = destination;
            return true;
        }
        return false;
    }

    //run dijsktras and return a route to take
    public Route calculateRoute() {
        route = map.navigateFrom("USER", destination);
        return route;
    }

    public Step nextStep() {
        return route.TakeStep();
    }
}
