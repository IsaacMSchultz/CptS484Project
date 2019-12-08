package com.example.eyeballinapp.MapStuff.Graph;

import java.util.ArrayList;

public class Route {
    private ArrayList<Step> steps;
    private int currentStep;
    private boolean isArrived;

    public Route(ArrayList<Step> steps) {
        this.steps = steps;
        currentStep = 0;
    }

    public ArrayList<Step> getStepList() {
        return steps;
    }

    public int numberOfSteps() {
        return steps.size();
    }

    //remove a step and update the vector of the step after it to be accurate after the removal.
    public boolean removeStep(int step) {
        if (step <= 0 && step > steps.size())
            return false;
        EBVector oldVector = steps.remove(step).getVector(); //get the vector of the step we removed.
        EBVector nextVector = steps.get(step).getVector(); //get the vector of the step after the one we removed
        EBVector newVector = oldVector.add(nextVector); //add the two vectors;
        steps.get(step).updateVector(newVector); //update the vector of the step

        // https://imgur.com/a/6pPPkBZ - amazing drawing of what we are doing here.

        return true;
    }

    public Step getStep(int stepNumber) {
        if (stepNumber >= 0 && stepNumber < steps.size())
            return steps.get(stepNumber);
        else
            throw new ArrayIndexOutOfBoundsException("index out of bounds: " + stepNumber);
    }

    public Step takeStep() {
        Step s = steps.get(currentStep);
        if (currentStep < steps.size() - 1) //cannot take a step when you are at the last step.
            currentStep++;
        return s;
    }

    // pass in the direction the user is facing, and get the direction they need to turn as a string.
    public String getDirections(String userIsFacing) {
        Step s = steps.get(currentStep);
        String direction = s.setUserDirection(userIsFacing); //pass the direction the user is facing to the step to see which direction we need to turn.

        // either need to get the direction and build a voice string from it here, or change the return value of direction in the other class to return the voice string.
        // It probably makes more sense to use a case statement here just in case we need direction in another locaation.
        // Since we are using easy to define strings, a switch statement is likely the easiest approach.

        // We want this to return the direction the user needs to turn, and then tell them how far to walk in that direction.

        // Should say things like "continue straight for 10 feet"



        return "";
    }

}
