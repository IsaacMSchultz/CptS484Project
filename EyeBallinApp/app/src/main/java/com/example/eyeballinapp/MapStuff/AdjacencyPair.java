package com.example.eyeballinapp.MapStuff;

public class AdjacencyPair {
    String destination;
    int distance;

    public AdjacencyPair(String destination, int distance) {
        this.destination = destination;
        this.distance = distance;
    }

    public String getDestination() {
        return destination;
    }

    public int getDistance() {
        return distance;
    }
}
