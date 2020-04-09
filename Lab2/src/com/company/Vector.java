package com.company;

import java.util.ArrayList;
import java.util.List;

public class Vector {
    private List<Double> vector;
    private int length;

    public Vector(int length) {
        this.length = length;
        this.vector = new ArrayList<>();

        for (int i = 0; i < this.length; i++) {
            double num = Math.random() * 20 - 10;

            vector.add(Math.round(num * 100.0) / 100.0);
        }
    }

    public Vector(Vector other) {
        this.length = other.length;
        this.vector = new ArrayList<>();

        for (int i = 0; i < this.length; i++) {
            vector.add(other.vector.get(i));
        }
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

    public Matrix toMatrix() {
        Matrix matrix = new Matrix(length, 1);

        for (int i = 0; i < length; i++) {
            matrix.setElem(i, 0, vector.get(i));
        }

        return matrix;
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

    public double getElem(int index) {
        return vector.get(index);
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
}
