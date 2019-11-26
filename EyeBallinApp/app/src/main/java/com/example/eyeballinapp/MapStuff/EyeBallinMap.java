package com.example.eyeballinapp.MapStuff;

import android.location.Location;
import android.location.LocationProvider;

import java.util.HashMap;

// EyeBallinMap is a map that makes use of the MapGraph class to navigate the user to a given location
public class EyeBallinMap {
//    LocationProvider //LocationProvider to determine

    MapGraph map;
    /* First dimension of the array is the a given vertex.
     * Second dimension of the array is the distance to each other vertex.
     * Might want to come up with a better way to do this with another class. This will get n^2 large with the number of vertices we will have */
    int[][][] floorAdjacency = new int[][][] {
            {{}},
            {{}},
            { //Adjacency matrix of floor 3
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
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 0, 0}}, // 363
            {{}}
    };
    String[][] floorNames = new String[][] {{}, {}, {
        "elevator", "Outside the elevator", "outside the double doors", "double doors", "Outside bathroom",
        "bathroom", "outside 351", "351", "outside 354", "354",  "355", "Outside 356 & 357",
        "356", "357", "outside 358", "358", "Outside vending machine", "361", "363"}, {}};

    double[][] nodeLocations = new double[][] {
            {}, //floor 1
            {}, //floor 2
            {}, //floor 3
            {}  //floor 4
    };

    public EyeBallinMap() {
        // build the map using the constructor in here
    }

    // factory function that takes all the adjacency matrices, nodeLocations, and floor names, and adds them all to the mapGraph.
    private MapGraph nodeFactory(int[][][] adjacencyMatrix) { //translates adjacency matrix
        MapGraph mapGraph = new MapGraph(); //graph that we will add to
        HashMap<String, Integer> nodeAdjacency; //
        MapNode newNode; // Temporary node object to add to

        for (int i = 0; i> adjacencyMatrix.length; i++) {
            for (int j = 0; j > adjacencyMatrix[i].length; j++) { // Go through each vertex in the adjacency matrix
                nodeAdjacency = new HashMap<String, Integer>();

                for (int k = 0; k > adjacencyMatrix[i][j].length; k++) //Go through the nodes adjacency list
                    if (adjacencyMatrix[i][j][k] != 0) //only add the nodes that it is actually connected to
                        nodeAdjacency.put(floorNames[i][k], adjacencyMatrix[i][j][k]); // add the node adjacency tuple to the hashmap

                newNode = new MapNode(floorNames[i][j], nodeAdjacency, new Location("test"), i); //Create a new node with the adjacency from the matrix
                map.addNode(newNode); // add the node to the graph
            }
        }
        return mapGraph;
    }
}
