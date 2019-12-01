package com.example.eyeballinapp.MapStuff;

import java.util.Comparator;

public class AdjacencyPairComparator implements Comparator<AdjacencyPair> {
        @Override
        public int compare(AdjacencyPair p1, AdjacencyPair p2) {
            //sort using distance values
            int key1 = p1.getDistance();
            int key2 = p2.getDistance();
            return key1 - key2;
        }
}
