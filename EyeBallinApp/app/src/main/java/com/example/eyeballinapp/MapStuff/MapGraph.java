package com.example.eyeballinapp.MapStuff;
// A Java program for Dijkstra's single source shortest path algorithm.
// The program is for adjacency matrix representation of the graph

import android.location.Location;
import android.util.Pair;

import java.util.*;
import java.lang.*;
import java.util.HashMap;
import java.io.*;

public class MapGraph implements Graph {
    HashMap<String, MapNode> nodes; //HashMap where the name of the node is the key

    public int getSize() {
        return nodes.size();
    }

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

    // Navigate from a source node, to a destination node. This will not be called
    public List<MapNode> navigateFrom(String source, String destination) {
        // This function should run Dijkstra's on the map to get an ordered list of nodes that the user should follow to get to their location.
        return new ArrayList<MapNode>();
    }

    // https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Pseudocode
    // This function actually traverses the tree to find the shortest path between the source, and destination vertices.
    // Assumes that the graph is connected!!!
    public List<MapNode> dijkstra(String sourceVertex, String destinationVertex) {
        if (!nodes.containsKey(sourceVertex) || !nodes.containsKey(destinationVertex)) //Cannot find the distances if the source or destination is not in the graph.
            return new ArrayList<MapNode>();

        HashMap<String, String> prev = new HashMap<String, String>(); // keeps track of the previous node for each node
        HashMap<String, Integer> distance = new HashMap<String, Integer>(); //distance used to store the distance of vertex from a source
        ArrayList<MapNode> shortestPath = new ArrayList<>(); // Create the arrayList that we will use to store the shortest path.

        // Creating a priority queue for ordering which node to select next.
        // Size of the number of nodes, compares based on distance
        // AdjacencyPair contains a String for the destination, and an integer for the distance to it.
        PriorityQueue<AdjacencyPair> priorityQueue = new PriorityQueue<AdjacencyPair>(nodes.size(), new AdjacencyPairComparator());

        distance.put(sourceVertex, 0); // Set starting vertex to have a distance of 0.

        //Initialize the distance of all other nodes to infinity
        for (String nodeName : nodes.keySet()) {
            if (nodeName != sourceVertex)
                distance.put(nodeName, Integer.MAX_VALUE); // Set the distance to be infinity for every node
            prev.put(nodeName, "N/A"); // Set the previous value to be N/A for every node

            priorityQueue.offer(new AdjacencyPair(nodeName, distance.get(nodeName))); // Add the node to the priority queue. SourceVertex will be at the top since it has the smallest.
        }

        // While there are nodes that are still reachable.
        while (priorityQueue.isEmpty() == false) {

            // Find the node with the shortest distance
            String closest = priorityQueue.poll().getVertex(); // Take the top node off the queue.

            if (closest != destinationVertex) { // If we have not reached the destination vertex, continue traversing neighbors of this node to find the shortest path.
                HashMap<String, Integer> neighbors = nodes.get(closest).getAdjacency(); // Get the neighbors of the closest node.

                for (String neighbor : neighbors.keySet()) {
                    distance.get(neighbor);
                    int pathLength = distance.get(closest) + neighbors.get(neighbor); // Store the path length, which is the current distance, plus the distance of to the neighbor.
                    if (pathLength < distance.get(neighbor)) {// If the pathLength we just found is shorter than the one we already have stored in the distance HashMap
                        distance.put(neighbor, pathLength); // Update the length of the new shortest path to that node.
                        prev.put(neighbor, closest); // Set the node previous to the neighbor for the shortest distance path, to the closest node.
                    }
                }
            } else { // We have found the shortest path from the source to the destination!
                // Now we need to build the steps that need to be taken to navigate from the source to the destination.
                String currentNode = closest; //renaming the variable to make this section more clear

                // walk backwards from the destination to the source, and add each node along the way.
                if (prev.get(currentNode).equals("N/A") || currentNode == sourceVertex) { // Can only run this if prev is defined, or this is the source (I think thats the case if the destination was not found)
                    while (prev.get(currentNode).equals("N/A")){ // While there is a node behind the currentNode
                        shortestPath.add(0, nodes.get(currentNode)); // Add that node to the top of the list
                        currentNode = prev.get(currentNode); // set the currentNode to the one previous to it along the path.
                    }
                }
            }
        }
        return shortestPath;
    }
}
// This code is contributed by Aakash Hasija