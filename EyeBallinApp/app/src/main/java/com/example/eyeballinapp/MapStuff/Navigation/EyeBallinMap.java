package com.example.eyeballinapp.MapStuff.Navigation;

import android.content.Context;

import com.example.eyeballinapp.MapStuff.Location.CustomLocation;
import com.example.eyeballinapp.MapStuff.Graph.EBVector;
import com.example.eyeballinapp.MapStuff.Graph.MapGraph;
import com.example.eyeballinapp.MapStuff.Graph.Route;
import com.example.eyeballinapp.MapStuff.Graph.Step;
import com.example.eyeballinapp.MapStuff.parsing.XmlParser;

// EyeBallinMap is a map that makes use of the MapGraph class to navigate the user to a given location
public class EyeBallinMap {
    MapGraph map;
    String destination;
    Route route;

    public EyeBallinMap(Context current) {
        // build the map using the constructor in here
        XmlParser parser = new XmlParser(current);
        map = parser.tempParse();
    }

    // add / remove the user node in the graph, connect to the closest node.
    public void updateUser(CustomLocation loc) {
        map.removeNode("USER"); //remove the old node (also removes edges connected to it)
        Step closestNode = map.nearestNode(loc); // get the closest node before adding the new one.
        map.addNode("USER", loc); // add the new node

        map.addEdge("USER", closestNode.getNode().getName(), closestNode.getDistance()); // add the edge between the user and the closest node.
    }

    // set the current destination that you want to navigate to.
    // return true if the node is in the graph.
    public boolean setDestination(String destination) {
        if (map.containsNode(destination)) {
            this.destination = destination;
            return true;
        }
        return false;
    }

    //run dijsktras and return a route to take
    public Route calculateRoute() {
        route = map.navigateFrom("USER", destination);

         /*
         since the mapgraph might have put the user in between two nodes, we dont want to have them walk backwards, and then forwards again.
         To avoid this, we can look at the vectors of the first two steps.
         If the magnitude of the steps added to each other is larger than both of the steps on their own, then overall they are going in the same direction.
         However, if the magnitude of the steps added to each other is less than one of the steps on their own, then they are walking backwards, and then back forwards again.
         In the real world, this might happen between more than just two steps, but because of the way we have our graph set up, it should be highly improbable.
          */

        if (route.numberOfSteps() > 1) {
            // first we get the vectors of the first two steps
            EBVector step1 = route.getStep(0).getVector();
            EBVector step2 = route.getStep(1).getVector();

            // then we add them together.
            double totalMagnitude = step1.add(step2).getMagnitude(); // (step1 + step2).getMagnitude();

            //then we check to see if the magnitude of the steps combined is less than of the steps on their own.
            if (totalMagnitude <= step1.getMagnitude() || totalMagnitude <= step2.getMagnitude()) {
//        if (totalMagnitude <= (step1.getMagnitude() + step2.getMagnitude())) {
                route.removeStep(0); // we need to remove the first step, since it is counterproductive.
            }
        }

        return route;
    }

    public Step nextStep() {
        return route.takeStep();
    }

    public void setDirection() {

    }


}
