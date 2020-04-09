package com.company;

import java.util.ArrayList;
import java.util.List;

public class Vector {
    private List<Double> vector;
    private int dim;

    public Vector() {
        this.dim = 0;
        this.vector = new ArrayList<>();
    }

    public Vector(int dim) {
        this.dim = dim;
        this.vector = new ArrayList<>();

        for (int i = 0; i < this.dim; i++) {
            double num = Functions.random(-10, 10);

            while (num == 0.) {
                num = Functions.random(-10, 10);
            }

            vector.add(Functions.round(num, 2));
        }
    }

    public Vector(double dim) {
        this.dim = (int) dim;
        this.vector = new ArrayList<>();

        for (int i = 0; i < this.dim; i++) {
            double num = Functions.random(-10, 10);

            vector.add(Functions.round(num, 2));
        }
    }

    public Vector(Vector a, Vector b) {
        this.dim = a.dim + 1;
        this.vector = new ArrayList<>();

        double num = Functions.random(-100, 100);

        while (Math.abs(num) <= Math.abs(b.getElem(0))) {
            num = Functions.random(-100, 100);
        }

        this.vector.add(Functions.round(num, 2));

        for (int i = 1; i < this.dim - 1; i++) {
            num = Functions.random(-100, 100);

            while (Math.abs(num) < (a.getElem(i) + b.getElem(i))) {
                num = Functions.random(-100, 100);
            }

            this.vector.add(Functions.round(num, 2));
        }

        num = Functions.random(-100, 100);

        while (Math.abs(num) < Math.abs(a.getElem(a.dim - 1))) {
            num = Functions.random(-100, 100);
        }

        this.vector.add(Functions.round(num, 2));
    }

    public Vector(Vector y, Vector a, Vector b, Vector c) {
        this.dim = y.dim;
        this.vector = new ArrayList<>();

        this.vector.add(Functions.round(c.getElem(0) * y.getElem(0) - b.getElem(0) * y.getElem(1), 2));

        for (int i = 1; i < this.dim - 1; i++) {
            this.vector.add(Functions.round(-a.getElem(i - 1) * y.getElem(i - 1) + c.getElem(i) * y.getElem(i)
                    - b.getElem(i) * y.getElem(i + 1), 2));
        }

        this.vector.add(Functions.round(-a.getElem(y.dim - 2) * y.getElem(y.dim - 2) + c.getElem(y.dim - 1) * y.getElem(y.dim - 1), 2));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append('(');

        for(int i = 0; i < dim - 1; i++) {
            result.append(vector.get(i)).append(", ");
        }

        result.append(vector.get(dim - 1)).append(')');

        return result.toString();
    }

    public double getElem(int index) {
        return vector.get(index);
    }

    public int getDim() {
        return dim;
    }

    public void addToEnd(double elem) {
        dim++;
        vector.add(elem);
    }

    public void addToStart(double elem) {
        dim++;
        vector.add(0, elem);
    }
}
