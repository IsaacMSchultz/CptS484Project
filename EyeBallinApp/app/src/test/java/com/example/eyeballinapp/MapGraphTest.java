package com.example.eyeballinapp;

import com.example.eyeballinapp.MapStuff.MapStuff.MapGraph;
import com.example.eyeballinapp.MapStuff.MapStuff.MapNode;

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

        testNode = new MapNode("test2", 1);
        map.addNode(testNode);
        testNode = new MapNode("test3", 1);
        map.addNode(testNode);
        testNode = new MapNode("test3", 1);
        map.addNode(testNode);
        assertEquals(3, map.getSize());
    }

    @Test
    public void addNode1() {
    }

    @Test
    public void addEdge() {
        MapGraph map = new MapGraph();
        MapNode testNode = new MapNode("test1", 1);
        map.addNode(testNode);
        testNode = new MapNode("test2", 1);
        map.addNode(testNode);
        map.addEdge("test1", "test2", 9);

        assertTrue(map.getNode("test1").getAdjacency().containsKey("test2"));
        assertTrue(map.getNode("test2").getAdjacency().containsKey("test1"));
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