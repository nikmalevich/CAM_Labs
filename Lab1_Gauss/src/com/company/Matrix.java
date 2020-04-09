package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Matrix {
    private List<ArrayList<Double>> matrix = new ArrayList<>();
    private int numLines;
    private int numColumns;
    private int endIndex;
    private double det;

    public Matrix(int lines, int columns) {
        numLines = lines;
        numColumns = columns;
        endIndex = numLines - 1;
        det = 1;

        for (int i = 0; i < numLines; i++) {
            ArrayList<Double> line = new ArrayList<>();

            for(int j = 0; j < numColumns; j++) {
                double num = Math.random() * 20 - 10;

                line.add(Math.round(num * 100.0) / 100.0);
            }

            matrix.add(line);
        }
    }

    public Matrix(Matrix other) {
        this.numLines = other.numLines;
        this.numColumns = other.numColumns;
        this.endIndex = other.endIndex;
        this.det = other.det;

        for (int i = 0; i < this.numLines; i++) {
            ArrayList<Double> line = new ArrayList<>();

            for(int j = 0; j < this.numColumns; j++) {
                line.add(other.matrix.get(i).get(j));
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

    public Matrix addColumn(Matrix column) throws Exception {
        if (column.numColumns != 1) {
            throw new Exception("No column passed");
        }

        if (column.numLines != this.numLines) {
            throw new Exception("Adding is not possible");
        }

        Matrix result = new Matrix(this);

        result.numColumns++;

        for (int i = 0; i < this.numLines; i++) {
            result.matrix.get(i).add(column.matrix.get(i).get(0));
        }

        return result;
    }

    public void forwardGaussMethod(Matrix unit) throws Exception {
        if (this.numLines + 1 != this.numColumns) {
            throw new Exception("Gauss method impossible!");
        }

        for (int i = 0; i < this.numColumns - 1; i++) {
            List<Double> column = new ArrayList<>();

            int indexOfMax = i;

            for (int j = i; j < this.numLines; j++) {
                column.add(this.matrix.get(j).get(i));

                if (Math.abs(column.get(j - i)) > Math.abs(column.get(indexOfMax - i))) {
                    indexOfMax = j;
                }
            }

            Collections.swap(this.matrix, i, indexOfMax);
            Collections.swap(unit.matrix, i, indexOfMax);

            if (indexOfMax != i) {
                this.det *= -1;
            }

            this.det *= this.matrix.get(i).get(i);

            if (Math.abs(this.matrix.get(i).get(i)) <= Math.pow(10, -15)) {
                for (int j = i; j < this.numLines; j++) {
                    if (this.matrix.get(j).get(this.numColumns - 1) != 0.00) {
                        throw new Exception("The system has no solutions!");
                    }
                }

                this.endIndex = i - 1;

                unit.det = 0;

                return;
            }

            for (int j = i + 1; j < this.numColumns; j++) {
                this.matrix.get(i).set(j, this.matrix.get(i).get(j) / this.matrix.get(i).get(i));
            }

            for (int j = 0; j < unit.numColumns; j++) {
                unit.matrix.get(i).set(j, unit.matrix.get(i).get(j) / this.matrix.get(i).get(i));
            }

            this.matrix.get(i).set(i, 1.00);

            for (int j = i + 1; j < this.numLines; j++) {
                double num = this.matrix.get(j).get(i);

                for (int k = i + 1; k < this.numColumns; k++) {
                    this.matrix.get(j).set(k, this.matrix.get(j).get(k) - this.matrix.get(i).get(k) * num);
                }

                for (int k = 0; k < unit.numColumns; k++) {
                    unit.matrix.get(j).set(k, unit.matrix.get(j).get(k) - unit.matrix.get(i).get(k) * num);
                }

                this.matrix.get(j).set(i, 0.00);
            }
        }
    }

    public Matrix backwardGaussMethod(Matrix inverse, Matrix unit) throws Exception {
        if (this.numLines + 1 != this.numColumns) {
            throw new Exception("Gauss method impossible!");
        }

        Matrix x = new Matrix(this.numLines, 1);

        if (this.endIndex != numLines - 1) {
            for (int i = this.numLines - 1; i > this.endIndex; i--) {
                x.matrix.set(i, new ArrayList<Double>(Collections.singletonList((Math.random() * 20 - 10))));
            }
        }

        for (int i = this.endIndex; i >= 0; i--) {
            double num = this.matrix.get(i).get(this.numColumns - 1);

            for (int j = this.numColumns - 2; j > i; j--) {
                num -= this.matrix.get(i).get(j) * x.matrix.get(j).get(0);
            }

            x.matrix.set(i, new ArrayList<Double>(Collections.singletonList(num)));
        }

        if (unit != null) {
            for (int i = 0; i < inverse.numLines; i++) {
                inverse.matrix.set(i, new ArrayList<>(Collections.nCopies(inverse.numColumns, 0.)));
            }

            for (int i = 0; i < unit.numColumns; i++) {
                for (int j = this.endIndex; j >= 0; j--) {
                    double num = unit.matrix.get(j).get(i);

                    for (int k = this.numColumns - 2; k > j; k--) {
                        num -= this.matrix.get(j).get(k) * inverse.matrix.get(k).get(i);
                    }

                    inverse.matrix.get(j).set(i, num);
                }
            }
        }

        return x;
    }

    public double getElem(int lineNumber, int columnNumber) {
        return matrix.get(lineNumber).get(columnNumber);
    }

    public double getDet() {
        return det;
    }

    public static Matrix getUnit(int dim) {
        Matrix unit = new Matrix(dim, dim);

        for (int i = 0; i < dim; i++) {
            ArrayList<Double> line = new ArrayList<>(Collections.nCopies(dim, 0.));

            line.set(i, 1.);

            unit.matrix.set(i, line);
        }

        return unit;
    }

}
