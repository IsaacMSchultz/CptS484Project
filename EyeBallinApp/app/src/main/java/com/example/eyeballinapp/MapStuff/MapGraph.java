package com.example.eyeballinapp.MapStuff;
// A Java program for Dijkstra's single source shortest path algorithm.
// The program is for adjacency matrix representation of the graph
import java.util.*;
import java.lang.*;
import java.util.HashMap;
import java.io.*;

public class MapGraph {
    // A utility function to find the vertex with minimum distance value,
    // from the set of vertices not yet included in shortest path tree
    HashMap<String, MapNode> nodes; //HashMap where the name of the node is the key

    public MapGraph() {
        this.nodes = new HashMap<String, MapNode>();
    }

    public MapGraph(HashMap<String, MapNode> nodes) {
        this.nodes = nodes; // put the nodes in the graph
    }

    // Add a node into the graph.
    public void addNode(MapNode node) {
        nodes.put(node.getName(), node);
    }

    public List<MapNode> navigateFrom(MapNode beginningLocation) {
        // THis fucntion should run Dijkstras on the map to get an ordered list of nodes that the user should follow to get to their location.
        return new ArrayList<MapNode>();
    }

}
// This code is contributed by Aakash Hasija