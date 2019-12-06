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
    public Route calculateRoute1() { //rename to use this one,
        route = map.navigateFrom("USER", destination);

         /*
         since the mapgraph might have put the user in between two nodes, we dont want to have them walk backwards, and then forwards again.
         To avoid this, we can look at the vectors of the first two steps.
         If the magnitude of the steps added to each other is larger than both of the steps on their own, then overall they are going in the same direction.
         However, if the magnitude of the steps added to each other is less than one of the steps on their own, then they are walking backwards, and then back forwards again.
         In the real world, this might happen between more than just two steps, but because of the way we have our graph set up, it should be highly improbable.
          */

        // if there are steps to take, and the vectors are not 0;
        if (route.numberOfSteps() > 1) {
            // first we get the vectors of the first two steps
            Step step1 = route.getStep(0);
            Step step2 = route.getStep(1);

            EBVector v1 = step1.getVector();
            EBVector v2 = step2.getVector();

            // then we add them together.
            double totalMagnitude = v1.add(v2).getMagnitude(); // (step1 + step2).getMagnitude();
            double dot = v1.getUnitVector().dot(v2.getUnitVector()); //we can get the dot pruduct (of unit vectors) for some information about the direction the vectors are facing
            // since these are unit vectors, the output we get is the angle in radians

            if (route.getStep(0).hasZComponent()) {
                route.removeStep(0); // we need to remove the first step, since it is something in an elevator.
            } else if (!route.getStep(1).hasZComponent()) { // we do not want to remove a step if the second one has a vertical component.

                //then we check to see if the magnitude of the steps combined is less than of the steps on their own.
                if (totalMagnitude <= v1.getMagnitude() || totalMagnitude <= v2.getMagnitude()) {
                    // if (totalMagnitude <= (step1.getMagnitude() + step2.getMagnitude())) {
                    if (v1.getMagnitude() < 3) // are within 3 feet of the target node
                        route.removeStep(0); // we need to remove the first step, since it is counterproductive.
                    else if (dot > 0) // vectors are in the same general direction, but not orthogonal. (includes 1, which is in the same direction)
                        route.removeStep(0);
                    else if (dot == -1) // vectors are opposite to each other
                        route.removeStep(0);
                }
            }
        }

        return route;
    }

    //run dijsktras and return a route to
    // remo
    public Route calculateRoute() {
        boolean ghettoFlag = false;

        route = map.navigateFrom("USER", destination);

        // handle removal of useless steps related to the user's location
        if (route.numberOfSteps() > 1) { // if there at least 2 steps.
            // first we get the vectors of the first two steps
            Step step1 = route.getStep(0);
            Step step2 = route.getStep(1);

            double step1Magnitude = step1.getVector().getMagnitude();

            EBVector unitVector1 = step1.getVector().getUnitVector();
            EBVector unitVector2 = step2.getVector().getUnitVector();

            double angleBetween = unitVector1.dot(unitVector2);  // We can get the dot product (of unit vectors) for some information about the direction the vectors are facing.
            // since these are unit vectors, the output we get is the angle in radians (ranged from -1 to 1 on the unit circle)

            if (step1.hasZComponent()) {
                route.removeStep(0); // we need to remove the first step, since it is something in an elevator.
            } else if (!step2.hasZComponent()) { // we do not want to remove a step if the second one has a vertical component.
                // now we can check for removal cases based on user location criteria.
                if (step1Magnitude < 3) { // are within 3 feet of the target node
                    route.removeStep(0);
                } else if (angleBetween > EBVector.ThirtyDegrees || angleBetween < EBVector.NegThirtyDegrees) { // vectors are within 30 degrees of the same direction. ( does not appear in our unless the user is close to a node but not orthogonal to it)
                    route.removeStep(0);
                } else if (angleBetween == -1) { // vectors are opposite to each other. This will never be a path created by dijkstra with our map. So this is because the user is closest to the previous node.
                    route.removeStep(0);
                }
            }
        }

        // remove steps so its just the start and end of long stretches of straight walking.
        for (int currentNode = 0; currentNode + 1 < route.numberOfSteps(); currentNode++) { // go through all the steps. We need to look 2 nodes ahead to see if there is a stretch of straight line
            int removalNode = currentNode + 1; // node that we will remove if there is a straight line

            // get unit vectors of the next two steps
            EBVector unitVector1 = route.getStep(currentNode).getVector().getUnitVector();
            EBVector unitVector2 = route.getStep(currentNode + 1).getVector().getUnitVector();

            // We can get the dot product (of unit vectors) for some information about the direction the vectors are facing.
            // since these are unit vectors, the output we get is the angle in radians (ranged from -1 to 1 on the unit circle)
            double angleBetween12 = unitVector1.dot(unitVector2);


            // remove the step if it is in a straight line
            while (angleBetween12 == 1) {
                // (but NOT if it has any vertical components. This wont happen since they always have unit vectors of 0
                route.removeStep(currentNode); //remove the middle step

                if (currentNode > route.numberOfSteps())
                    break; //end the while loop if we reach the end of the route.

                // update vector2 and the angle between the old and the new
                unitVector2 = route.getStep(currentNode).getVector().getUnitVector();
                angleBetween12 = unitVector1.dot(unitVector2);
            }
        }

        return route;
    }

    public Step nextStep() {
        return route.takeStep();
    }

    public void setDirection() {

    }

    // get the unit vector (angle in radians) of the difference between the next to step vectors
    private double getNextStepDirection(int startingIndex) {
        if (startingIndex + 1 < route.numberOfSteps()) {
            // first we get the vectors of the first two steps
            Step step1 = route.getStep(startingIndex);
            Step step2 = route.getStep(startingIndex + 1);

            EBVector v1 = step1.getVector();
            EBVector v2 = step2.getVector();

            return v1.getUnitVector().dot(v2.getUnitVector()); //we can get the dot product (of unit vectors) for some information about the direction the vectors are facing
            // since these are unit vectors, the output we get is the angle in radians
        } else {
            return 0.0;
        }
    }

}
