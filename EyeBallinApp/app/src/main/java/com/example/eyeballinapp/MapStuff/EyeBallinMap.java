package com.example.eyeballinapp.MapStuff;

import android.location.Location;

import java.util.HashMap;

// EyeBallinMap is a map that makes use of the MapGraph class to navigate the user to a given location
public class EyeBallinMap {
//    LocationProvider //LocationProvider to determine

    MapGraph map;

    public EyeBallinMap() {
        // build the map using the constructor in here
        XmlParser parser = new XmlParser(map);
        map = parser.tempParse();
    }
}
