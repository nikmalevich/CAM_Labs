package com.company;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    private List<ArrayList<Double>> matrix = new ArrayList<>();
    private int numLines;
    private int numColumns;

    public Matrix(int lines, int columns) {
        numLines = lines;
        numColumns = columns;

        for (int i = 0; i < numLines; i++) {
            ArrayList<Double> line = new ArrayList<>();

            for(int j = 0; j < numColumns; j++) {
                double num = Math.random() * 20 - 10;

                line.add(Math.round(num * 100.0) / 100.0);
            }

            matrix.add(line);
        }
    }

    public void printMatrix() {
        for(int i = 0; i < numLines; i++) {

            for(int j = 0; j < numColumns; j++) {
                System.out.printf("%10.2f",  Math.round(matrix.get(i).get(j) * 100.0) / 100.0);
            }

            System.out.println(' ');
        }

        System.out.println(' ');
    }

    public Matrix multiply(Matrix other) throws Exception {
        if (this.numColumns != other.numLines) {
            throw new Exception("Multiplication is impossible!");
        }

        Matrix result = new Matrix(this.numLines, other.numColumns);

        for (int i = 0; i < this.numLines; i++)
        {
            ArrayList<Double> line = new ArrayList<>();

            for (int j = 0; j < other.numColumns; j++)
            {
                double num = 0;

                for (int k = 0; k < this.numColumns; k++)
                {
                    num += this.matrix.get(i).get(k) * other.matrix.get(k).get(j);
                }

                line.add(num);
            }

            result.matrix.set(i, line);
        }

        return result;
    }

    public void setElem(int numLine, int numColumn, double elem) {
        matrix.get(numLine).set(numColumn, elem);
    }

    public Vector toVector() throws Exception {
        if (numColumns != 1) {
            throw new Exception("Unable to convert");
        }

        Vector vector = new Vector(numLines);

        for (int i = 0; i < numLines; i++) {
            vector.setElem(i, matrix.get(i).get(0));
        }

        return vector;
    }

    public double getElem(int numLine, int numColumn) {
        return matrix.get(numLine).get(numColumn);
    }

    public Matrix transpose() {
        Matrix matrix = new Matrix(this.numLines, this.numColumns);

        for (int i = 0; i < numLines; i++) {
            for (int j = 0; j < numColumns; j++) {
                matrix.setElem(i, j, this.getElem(j, i));
            }
        }

        return matrix;
    }
}
