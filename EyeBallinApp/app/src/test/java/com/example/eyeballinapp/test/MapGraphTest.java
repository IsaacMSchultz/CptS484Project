package com.example.eyeballinapp.test;

import com.example.eyeballinapp.MapStuff.MapGraph;
import com.example.eyeballinapp.MapStuff.MapNode;

import org.junit.Test;

import static org.junit.Assert.*;

public class MapGraphTest {

    @Test
    public void getSize() {
        MapGraph map = new MapGraph();
    }

    @Test
    public void addNode() {
        MapGraph map = new MapGraph();

        assertEquals(0, map.getSize());
        MapNode testNode = new MapNode("test1", 1);
        map.addNode(testNode);
        assertEquals(1, map.getSize());
    }

    @Test
    public void addNode1() {
    }

    @Test
    public void addEdge() {
    }

    @Test
    public void nearestNodeName() {
    }

    @Test
    public void nearestNode() {
    }

    @Test
    public void navigateFrom() {
    }
}