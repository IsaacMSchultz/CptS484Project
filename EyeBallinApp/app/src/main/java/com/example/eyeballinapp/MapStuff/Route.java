package com.example.eyeballinapp.MapStuff;

import java.util.ArrayList;

public class Route {
    private ArrayList<Step> steps;
    private int currentStep;

    public Route(ArrayList<Step> steps) {
        this.steps = steps;
        currentStep = 0;
    }

    public Step TakeStep() {
        currentStep++;
        return steps.get(currentStep);
    }
}
