package com.example.eyeballinapp.MapStuff.Graph;

public class EBVector {
    private double y;
    private double x;
    private double magnitude;

    public EBVector(double xDistance, double yDistance) {
        x = xDistance;
        y = yDistance;

        magnitude = Math.sqrt(x*x + y*y); // square both distances and take the square root of their sum to get the total magnitude.
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public double getMagnitude() {
        return magnitude;
    }

    // Behaves like + operator, does not update internal values, and instead returns a new EBVector that has the addition.
    public EBVector add(EBVector v) {
        double newX = x + v.getX();
        double newY = y + v.getY();

        return new EBVector(newX, newY);
    }

    // Behaves like - operator, does not update internal values, and instead returns a new EBVector.
    public EBVector subtract(EBVector v) {
        double newX = x - v.getX();
        double newY = y - v.getY();

        return new EBVector(newX, newY);
    }

    // other vector addition functions and shit
}
