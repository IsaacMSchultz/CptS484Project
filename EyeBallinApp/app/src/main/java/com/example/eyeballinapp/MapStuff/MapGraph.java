package com.example.eyeballinapp.MapStuff;
// A Java program for Dijkstra's single source shortest path algorithm.
// The program is for adjacency matrix representation of the graph

import android.location.Location;
import android.util.Pair;

import java.util.*;
import java.lang.*;
import java.util.HashMap;
import java.io.*;

public class MapGraph {

    HashMap<String, MapNode> nodes; //HashMap where the name of the node is the key

    // Default constructor
    public MapGraph() {
        this.nodes = new HashMap<String, MapNode>(); //make a new adjacency list
    }

    // Constructor for if you already have a list of nodes that you want to put into the graph.
    public MapGraph(HashMap<String, MapNode> nodes) {
        this.nodes = nodes; // put the nodes in the graph
    }

    // Add a node into the graph that has already been built
    public void addNode(MapNode node) {
        nodes.put(node.getName(), node); // Add a node to the graph.
    }

    // Adding a new node to a graph with an entire adjacency list
    public void addNode(String source, String[] destination, int[] weight, int floor) {
        if (!nodes.containsKey(source)) {
            nodes.put(source, new MapNode(source, floor));
        }
        for (int i = 0; i < destination.length; i++) {
            nodes.get(source).addEdge(destination[i], weight[i]); // Add the edges one by one
        }
    }

    // Add a single edge to the graph.
    public void addEdge(String source, String destination, int weight, int floor) {
        if (!nodes.containsKey(source)) { // If this is a node that has not been added yet, add it
            nodes.put(source, new MapNode(source, floor));
        }
        nodes.get(source).addEdge(destination, weight);
    }

    // Navigate from a source node, to a destination node. This will not be called
    public List<MapNode> navigateFrom(String source, String destination) {
        // This function should run Dijkstra's on the map to get an ordered list of nodes that the user should follow to get to their location.
        return new ArrayList<MapNode>();
    }

    // Find the closes node given a location.
    public String nearestNodeName(Location loc) {
        return nearestNode(loc).getName();
    }

    // Find the node that is closest to the location passed.
    public MapNode nearestNode(Location loc) {
        double lowest = Double.MAX_VALUE;
        MapNode lowestNode = new MapNode("N/A", 0);

        for (String nodeName : nodes.keySet()) {
            double distance = nodes.get(nodeName).getLocation().distanceTo(loc);
            if (distance < lowest) {
                lowest = distance;
                lowestNode = nodes.get(nodeName); //set the lowest node to the actual lowest node
            }
        }
        return lowestNode;
    }

    public int getSize() {
        return nodes.size();
    }

    public void dijkstra_GetMinDistances(String sourceVertex) { // I need to completely re-do this part. I don't think I did any of this right.

        if (!nodes.containsKey(sourceVertex)) //Cannot find the distances if the source doesn't exist.
            return;

        HashMap<String, Boolean> SPT = new HashMap<String, Boolean>;

        //distance used to store the distance of vertex from a source
        HashMap<String, Integer> distance = new HashMap<String, Integer>;

        //Initialize all the distance to infinity
        for (String nodeName : nodes.keySet()) {
            distance.put(nodeName, Integer.MAX_VALUE);
        }

        // https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Pseudocode
    }

    public void printDijkstra(int[] distance, int sourceVertex) {
        System.out.println("Dijkstra Algorithm: (Adjacency List + Priority Queue)");
        for (int i = 0; i < getSize(); i++) {
            System.out.println("Source Vertex: " + sourceVertex + " to vertex " + +i +
                    " distance: " + distance[i]);
        }
    }

}
// This code is contributed by Aakash Hasija