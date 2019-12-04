package com.example.eyeballinapp.MapStuff.Graph;

import java.util.ArrayList;

public class Route {
    private ArrayList<Step> steps;
    private int currentStep;

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
        return steps.get(stepNumber);
    }

    public Step takeStep() {
        currentStep++;
        return steps.get(currentStep);
    }
}