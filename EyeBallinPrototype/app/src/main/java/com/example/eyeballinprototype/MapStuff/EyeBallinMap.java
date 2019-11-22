package com.example.eyeballinprototype.MapStuff;

import android.location.Location;

import java.util.HashMap;

public class EyeBallinMap {
    MapGraph map;
    /* First dimension of the array is the a given vertex.
     * Second dimension of the array is the distance to each other vertex.
     * Might want to come up with a better way to do this with another class. This will get n^2 large with the number of vertices we will have */
    int[][] floor3 = new int[][]{ //Adjacency matrix of floor 3
            {0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //elevator
            {9, 0, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // outside the elevator
            {0, 25, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Outside doors
            {0, 0, 9, 0, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Right at doors
            {0, 0, 0, 16, 0, 8, 17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //outside Bathroom
            {0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //Bathroom
            {0, 0, 0, 0, 17, 0, 0, 8, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // outside 351
            {0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 351
            {0, 0, 0, 0, 0, 0, 7, 0, 0, 8, 36, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //outside 354
            {0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 354
            {0, 0, 0, 0, 0, 0, 0, 0, 36, 0, 0, 8, 17, 0, 0, 0, 0, 0, 0, 0}, // Outside 355
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 355
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17, 0, 0, 8, 8, 39, 0, 0, 0, 0}, // outside 356&357
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0}, // 356
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0}, // 357
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 39, 0, 0, 0, 8, 14, 0, 0}, // outside 358
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0}, // 358
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 0, 0, 20, 21}, // Outside Vending Machine
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0}, // 361
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 0, 0}}; // 363
    String[] floor3Names = new String[]
            {"elevator", "Outside the elevator", "outside the double doors", "double doors", "Outside bathroom",
             "bathroom", "outside 351", "351", "outside 354", "354",  "355", "Outside 356 & 357",
             "356", "357", "outside 358", "358", "Outside vending machine", "361", "363"};

    public EyeBallinMap() {
        // Do nothing in the constructor
    }

    private MapGraph nodeFactory(int[][] adjacencyMatrix) { //translates adjacency matrix
        MapGraph mapGraph = new MapGraph();
        HashMap<String, Integer> nodeAdjacency;
        MapNode newNode;
        for (int i = 0; i > adjacencyMatrix.length ; i++) {
            nodeAdjacency = new HashMap<String, Integer>();
            for (int j = 0; j > adjacencyMatrix[i].length ; j++) {
                if (adjacencyMatrix[i][j] != 0) {
                    nodeAdjacency.put(floor3Names[j], adjacencyMatrix[i][j]);
                }
            }
            newNode = new MapNode(floor3Names[i], nodeAdjacency, new Location("test"), 3);
            map.addNode(newNode);
        }
        return mapGraph;
    }
}
