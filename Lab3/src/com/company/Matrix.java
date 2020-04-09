package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Matrix {
    private List<ArrayList<Double>> matrix;
    private int deg;

    public Matrix(int deg, String filename) throws FileNotFoundException {
        this.deg = deg;
        matrix = new ArrayList<>();
        Scanner scanner = new Scanner(new FileReader(new File(filename)));

        for (int i = 0; i < deg; i++) {
            ArrayList<Double> line = new ArrayList<>();

            for (int j = 0; j < deg; j++) {
                line.add(scanner.nextDouble());
            }

            matrix.add(line);
        }
    }

    public Matrix(Matrix other) {
        this.deg = other.deg;
        matrix = new ArrayList<>();

        for (int i = 0; i < deg; i++) {
            ArrayList<Double> line = new ArrayList<>();

            for (int j = 0; j < deg; j++) {
                line.add(other.getElem(i, j));
            }

            matrix.add(line);
        }
    }

    public Matrix(int deg) {
        this.deg = deg;
        matrix = new ArrayList<>();

        for (int i = 0; i < deg; i++) {
            ArrayList<Double> line = new ArrayList<>();

            matrix.add(line);
        }
    }

    public void printMatrix() {
        for(int i = 0; i < deg; i++) {

            for(int j = 0; j < deg; j++) {
                System.out.printf("%10.3f",  matrix.get(i).get(j));
            }

            System.out.println(' ');
        }

        System.out.println(' ');
    }

    public Matrix multiply(Matrix other) throws Exception {
        if (this.deg != other.deg) {
            throw new Exception("Multiplication is impossible!");
        }

        Matrix result = new Matrix(this.deg);

        for (int i = 0; i < this.deg; i++)
        {
            ArrayList<Double> line = new ArrayList<>();

            for (int j = 0; j < other.deg; j++)
            {
                double num = 0;

                for (int k = 0; k < this.deg; k++)
                {
                    num += this.matrix.get(i).get(k) * other.matrix.get(k).get(j);
                }

                line.add(num);
            }

            result.matrix.set(i, line);
        }

        return result;
    }

    public Matrix multiply(int scalar) {
        for (int i = 0; i < deg; i++)
        {
            for (int j = 0; j < deg; j++)
            {
                this.setElem(i, j, this.getElem(i, j) * scalar);
            }
        }

        return this;
    }

    public Vector multiply(Vector vector) throws Exception {
        if (this.deg != vector.getLength()) {
            throw new Exception("Multiplication is impossible!");
        }

        Vector result = new Vector(this.deg);

        for (int i = 0; i < this.deg; i++)
        {
            double num = 0;

            for (int j = 0; j < this.deg; j++)
            {

                num += this.matrix.get(i).get(j) * vector.getElem(j);
            }

            result.addElem(num);
        }

        return result;
    }

    public Matrix sum(Matrix other) throws Exception {
        if (this.deg != other.deg) {
            throw new Exception("Sum is impossible!");
        }

        Matrix result = new Matrix(this.deg);

        for (int i = 0; i < this.deg; i++)
        {
            ArrayList<Double> line = new ArrayList<>();

            for (int j = 0; j < other.deg; j++)
            {
                line.add(this.getElem(i, j) + other.getElem(i , j));
            }

            result.matrix.set(i, line);
        }

        return result;
    }

    public void setElem(int numLine, int numColumn, double elem) {
        matrix.get(numLine).set(numColumn, elem);
    }

    public double getElem(int numLine, int numColumn) {
        return matrix.get(numLine).get(numColumn);
    }

    public Matrix transpose() {
        Matrix result = new Matrix(this.deg);

        for (int i = 0; i < deg; i++) {
            for (int j = 0; j < deg; j++) {
                result.matrix.get(i).add(this.getElem(j, i));
            }
        }

        return result;
    }

    public int indexOfMaxElemInLine(int numLine) {
        int result = 0;
        double max = 0;

        for (int i = 0; i < deg; i++) {
            if (max < Math.abs(this.getElem(numLine, i)) && i != numLine) {
                max = Math.abs(this.getElem(numLine, i));
                result = i;
            }
        }

        return result;
    }

    public static Matrix getRotationMatrix(int numLine, int numColumn, double cos, double sin, int deg) {
        Matrix result = new Matrix(deg);

        for (int i = 0; i < deg; i++) {
            for (int j = 0; j < deg; j++) {
                if (i == j) {
                    result.matrix.get(i).add(1.);
                }
                else {
                    result.matrix.get(i).add(0.);
                }
            }
        }

        result.setElem(numLine, numLine, cos);
        result.setElem(numLine, numColumn, -sin);
        result.setElem(numColumn, numLine, sin);
        result.setElem(numColumn, numColumn, cos);

        return result;
    }

    public static Matrix getUnit(int dim) {
        Matrix unit = new Matrix(dim);

        for (int i = 0; i < dim; i++) {
            ArrayList<Double> line = new ArrayList<>(Collections.nCopies(dim, 0.));

            line.set(i, 1.);

            unit.matrix.set(i, line);
        }

        return unit;
    }
}
