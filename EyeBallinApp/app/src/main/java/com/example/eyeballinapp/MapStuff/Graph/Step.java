package com.example.eyeballinapp.MapStuff.Graph;

import android.location.Location;

import com.example.eyeballinapp.MapStuff.Graph.EBVector;
import com.example.eyeballinapp.MapStuff.Graph.MapNode;
import com.example.eyeballinapp.MapStuff.Location.CustomLocation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Step {
    private MapNode node;
    private EBVector vector;
    private String direction; // the direction the user needs to go for their next step
    private boolean hasZComponent;

    public Step(MapNode node, double distanceX, double distanceY) {
        this.node = node;
        this.vector = new EBVector(distanceX, distanceY);
    }

    public Step(MapNode node, Location loc) {
        CustomLocation nodeLocation = (CustomLocation) node.getLocation();
        CustomLocation l = (CustomLocation) loc;
        double y = nodeLocation.getPositionY() - l.getPositionY();
        double x = nodeLocation.getPositionX() - l.getPositionX();
        int z = nodeLocation.getFloorNum() - l.getFloorNum();

        if (z == 0)
            hasZComponent = false;
        else
            hasZComponent = true;

        this.node = node;
        this.vector = new EBVector(x, y);
    }

    public void updateVector(EBVector v) {
        this.vector = v;
    }

    public void updateVector(Location loc) {
        CustomLocation nodeLocation = (CustomLocation) node.getLocation();
        CustomLocation l = (CustomLocation) loc;
        double y = nodeLocation.getPositionY() - l.getPositionY();
        double x = nodeLocation.getPositionX() - l.getPositionX();
        int z = nodeLocation.getFloorNum() - l.getFloorNum();

        if (z == 0)
            hasZComponent = false;
        else
            hasZComponent = true;

        this.vector = new EBVector(x, y);
    }

    // determine if vector is 0 without calculation.
    public boolean hasZComponent() {
        return hasZComponent;
    }

    // determine if there is a direction with a 0 component. (for finding near-right turns)
    public boolean has0Component() {
        if (vector.getX() == 0 || vector.getY() == 0)
            return true;
        else
            return false;
    }

    // get the magnitude of the distance to the next node.
    public double getDistance() {
        return vector.getMagnitude();
    }

    public EBVector getVector() {
        return vector;
    }

    public MapNode getNode() {
        return node;
    }

    /**
     * https://i.imgur.com/QHf5w6n.jpg drawing explanation of the breakup of directions.
     * @param dir a string representing the direction in the form: (positive|negative)(X|Y)
     */
    public String setUserDirection(String dir) {
        EBVector directionVector;
        double vx = vector.getX();
        double vy = vector.getY();

        // we need to rotate our direction vector based on the direction the user is facing so that our orientation is the same.
        // To do this, we use a rotation matrix for 90, 180, and 270 degrees based on which direction they are facing.
        // rotations for exact 90 degree angles are simply transformations of the signs of the components.
        if (dir.equals("positiveY")) {
            directionVector = new EBVector(vy, -vx); // transformation: <y, -x>
        } else if (dir.equals("negativeX")) {
            directionVector = new EBVector(-vx, -vy); // transformation: <-x, -y>
        } else if (dir.equals("negativeY")) {
            directionVector = new EBVector(-vy, vx); // transformation: <-y, x>
        } else { // we do not need to rotate if they are facing in the positive X direction since their vectors already line up with our representation of directions.
            directionVector = vector;
        }

        // calculate the unit vector so we can determine which whay to tell the user to go.
        // adding the two vectors will give us one pointing
        EBVector turningDirection = directionVector.getUnitVector();

        // pull out all the values we care about
        double x = turningDirection.getX();
        double y = turningDirection.getY();

        //if we split the unit circle based on the y component, we can make a more clear if-else chain
        if (y >= EBVector.twentyThreePiOver12y) { // vector is pointing between F and B.
            if (x < EBVector.piOver12x) {
                direction = "forward";
            } else if (x < EBVector.fivePiOver12x) {
                direction = "slight_right";
            } else if (x < EBVector.sevenPiOver12x) {
                direction = "right";
            } else if (x < EBVector.elevenPiOver12x) {
                direction = "120degrees_right";
            } else {
                direction = "backwards";
            }
        } else { // Vector is pointing between 120L and SL
            if (x > EBVector.nineteenPiOver12x) {
                direction = "slight_left";
            } else if (x > EBVector.seventeenPiOver12x) {
                direction = "left";
            } else {
                direction = "120degrees_left";
            }
        }

        return direction;
    }

    public String getDirection() {
        return direction;
    }

    private boolean ghettoRequiredNodesList(String name) {
        Pattern p = Pattern.compile("(Outside Hallway|Water Fountain|Outside.*61)"); // anything that contains outside hallway, water fountain, or outside the seminar rooms is required.
        Matcher m = p.matcher(name);
        if (m.matches())
            return true;
        else
            return false;
    }
}
