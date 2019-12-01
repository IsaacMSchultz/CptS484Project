package com.example.eyeballinapp.MapStuff.MapStuff;

import android.location.Location;
import java.util.List;

public interface Graph {

    int getSize(); //return the number of nodes in the graph.

    // Add a node into the graph that has already been built
    public void addNode(MapNode node); // Add a node to the graph.

    // Adding a new node to a graph with an entire adjacency list
    public void addNode(String source, String[] destination, int[] weight, int floor);

    // Add a single edge to the graph.
    public boolean addEdge(String source, String destination, int weight); // return false if source or destination dont exist

    // Find the closes node given a location.
    public String nearestNodeName(Location loc);

    // Find the node that is closest to the location passed.
    public MapNode nearestNode(Location loc);

    // Navigate from a source node, to a destination node. This will not be called
    public List<MapNode> navigateFrom(String source, String destination);
        // This function should run Dijkstra's on the map to get an ordered list of nodes that the user should follow to get to their location.

}
