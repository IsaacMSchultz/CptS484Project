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

    public void dijkstra_GetMinDistances(String sourceVertex) {

        if (!nodes.containsKey(sourceVertex))
            return;

        boolean[] SPT = new boolean[getSize()];

        //distance used to store the distance of vertex from a source
        int[] distance = new int[getSize()];

        //Initialize all the distance to infinity
        for (int i = 0; i < getSize(); i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        //Initialize priority queue
        //override the comparator to do the sorting based keys
        PriorityQueue<AdjacencyPair> pq = new PriorityQueue<>(getSize(), new Comparator<AdjacencyPair>() {
            @Override
            public int compare(AdjacencyPair p1, AdjacencyPair p2) {
                //sort using distance values
                int key1 = p1.getDistance();
                int key2 = p2.getDistance();
                return key1 - key2;
            }
        });

        //create the pair for for the first index, 0 distance, (source vertex) index
        distance[0] = 0; //set starting distance to 0
        AdjacencyPair p0 = new AdjacencyPair( sourceVertex, distance[0]); // Assuming that all graphs are connected. This will pick a random starting point.

        pq.offer(p0); //add starting point to the priority queue

        //while priority queue is not empty
        while (!pq.isEmpty()) {
            //extract the min
            AdjacencyPair extractedPair = pq.poll();

            //extracted vertex
            int extractedVertex = extractedPair.getDistance();
            if (SPT[extractedVertex] == false) {
                SPT[extractedVertex] = true;

                //iterate through all the adjacent vertices and update the keys
                HashMap<String, Integer> adjacencyList = nodes.get();
                for (String destString : adjacencyList.keySet()) {
                    int destination = adjacencyList.get(destString);
                    //only if edge destination is not present in mst
                    if (SPT[destination] == false) {
                        ///check if distance needs an update or not
                        //means check total weight from source to vertex_V is less than
                        //the current distance value, if yes then update the distance
                        int newKey = distance[extractedVertex] + edge.weight;
                        int currentKey = distance[destination];
                        if (currentKey > newKey) {
                            Pair<Integer, Integer> p = new Pair<>(newKey, destination);
                            pq.offer(p);
                            distance[destination] = newKey;
                        }
                    }
                }
            }
        }
        //print Shortest Path Tree
        printDijkstra(distance, sourceVertex);
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