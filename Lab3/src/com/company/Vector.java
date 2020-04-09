package com.company;

import java.util.ArrayList;
import java.util.List;

public class Vector {
    private List<Double> vector;
    private int length;

    public Vector(int length) {
        this.length = length;
        this.vector = new ArrayList<>();
    }

    public Vector(Vector other) {
        this.length = other.length;
        this.vector = new ArrayList<>();

        for (int i = 0; i < this.length; i++) {
            vector.add(other.vector.get(i));
        }
    }

    public static Vector getUnitVector(int length) {
        Vector result = new Vector(length);

        for (int i = 0; i < result.length; i++) {
            result.vector.add(1.);
        }

        return result;
    }

    public static Vector getNullVector(int length) {
        Vector result = new Vector(length);

        for (int i = 0; i < result.length; i++) {
            result.vector.add(0.);
        }

        return result;
    }

    public static Vector subtract(Vector a, Vector b) throws Exception {
        if (a.length != b.length) {
            throw new Exception("Impossible to calculate");
        }

        Vector result = new Vector(a.length);

        for (int i = 0; i < result.length; i++) {
            result.setElem(i, a.getElem(i) - b.getElem(i));
        }

        return result;
    }

    public void setElem(int index, double elem) {
        vector.set(index, elem);
    }

    public void printVector() {
        for(int i = 0; i < length; i++) {
            System.out.printf("%8.2f",  Math.round(vector.get(i) * 100.0) / 100.0);

            System.out.println(' ');
        }

        System.out.println(' ');
    }

    public double getVectorRate(Vector other) throws Exception {
        if (this.length != other.length) {
            throw new Exception("Impossible to calculate");
        }

        double vectorRate = 0;

        for (int i = 0; i < this.length; i++) {
            if (Math.abs(this.vector.get(i) - other.vector.get(i)) > vectorRate) {
                vectorRate = Math.abs(this.vector.get(i) - other.vector.get(i));
            }
        }

        return vectorRate;
    }

    public Vector div(Vector other) throws Exception {
        if (this.length != other.length) {
            throw new Exception("Impossible to calculate");
        }

        Vector result  = new Vector(this.length);

        for (int i = 0; i < this.length; i++) {
            result.addElem(this.getElem(i) / other.getElem(i));
        }

        return result;
    }

    public double getElem(int index) {
        return vector.get(index);
    }

    public void addElem(double elem) {
        vector.add(elem);
    }

    public double scalarProduct(Vector other) throws Exception {
        if (this.length != other.length) {
            throw new Exception("Impossible to calculate");
        }

        double result = 0;

        for (int i = 0; i < this.length; i++) {
            result += this.vector.get(i) * other.vector.get(i);
        }

        return result;
    }

    public Vector multiplyByScalar(double scalar) {
        Vector result = new Vector(this);

        for (int i = 0; i < result.length; i++) {
            result.setElem(i, result.getElem(i) * scalar);
        }

        return result;
    }

    public int getLength() {
        return length;
    }
}
