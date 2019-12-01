package com.example.eyeballinapp.MapStuff.MapStuff;

public class AdjacencyPair {
    private String vertex;
    private int distance;

    public AdjacencyPair(String vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    public String getVertex() {
        return vertex;
    }

    public int getDistance() {
        return distance;
    }
}
