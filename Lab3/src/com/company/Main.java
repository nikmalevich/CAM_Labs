package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    private final static int DEG = 6;
    private final static int K = 11;
    private final static double E = 0.000001;

    public static void main(String[] args) throws Exception {
        Matrix B = new Matrix(DEG, "inputB.txt");
        Matrix C = new Matrix(DEG, "inputC.txt");
        Matrix A = B.sum(C.multiply(K));
        Vector y = Vector.getUnitVector(DEG);

        System.out.println("Task1:");
        powerMethod(A, y);
        System.out.println();

        System.out.println("Task2:");
        methodJacobi(A);
    }

    public static void powerMethod(Matrix A, Vector y) throws Exception {
        System.out.println("Vector y:");
        y.printVector();

        Vector newY = A.multiply(y);
        double eigenvalue = newY.scalarProduct(y) / y.scalarProduct(y);
        int numIterations = 1;

        y = newY;
        newY = A.multiply(y);
        double newEigenvalue = newY.scalarProduct(y) / y.scalarProduct(y);

        double difference = Math.abs(newEigenvalue - eigenvalue);

        while (difference >= E) {
            eigenvalue = newEigenvalue;
            y = newY;

            if (numIterations % 10 == 0) {
                y = y.multiplyByScalar(1 / y.getVectorRate(Vector.getNullVector(y.getLength())));
            }

            newY = A.multiply(y);
            newEigenvalue = newY.scalarProduct(y) / y.scalarProduct(y);
            difference = Math.abs(newEigenvalue - eigenvalue);

            numIterations++;
        }

        newY = newY.multiplyByScalar(1 / newY.getVectorRate(Vector.getNullVector(newY.getLength())));

        System.out.println("Eigenvalue: " + newEigenvalue);
        System.out.println("Eigenvector:");
        newY.printVector();
        System.out.println("Iterations: " + numIterations);
    }

    public static void methodJacobi(Matrix A) throws Exception {
        Matrix Q = Matrix.getUnit(DEG);
        Matrix newA = new Matrix(A);
        int numIterations = 1;
        List<Double> sumSqrts = new ArrayList<>();
        double sumSqrt;

        for (int i = 0; i < DEG; i++) {
            sumSqrt = 0;

            for (int j = 0; j < DEG; j++) {
                if (i != j) {
                    sumSqrt += Math.pow(newA.getElem(i, j), 2);
                }
            }

            sumSqrts.add(sumSqrt);
        }

        int indexMax1 = sumSqrts.indexOf(Collections.max(sumSqrts));
        int indexMax2 = newA.indexOfMaxElemInLine(indexMax1);

        while (Math.abs(newA.getElem(indexMax1, indexMax2)) >= E) {
            double cos;
            double sin;

            if (newA.getElem(indexMax1, indexMax1) == newA.getElem(indexMax2, indexMax2)) {
                cos = Math.sqrt(2) / 2;
                sin = newA.getElem(indexMax1, indexMax2) / Math.abs(newA.getElem(indexMax1, indexMax2)) * Math.sqrt(2) / 2;
            }
            else {
                double tg = 2 * newA.getElem(indexMax1, indexMax2) / (newA.getElem(indexMax1, indexMax1) - newA.getElem(indexMax2, indexMax2));
                double intermediate = 1 / Math.sqrt(1 + Math.pow(tg, 2));
                cos = Math.sqrt(0.5 * (1 + intermediate));
                sin = tg / Math.abs(tg) * Math.sqrt(0.5 * (1 - intermediate));
            }

            Matrix rotationMatrix = Matrix.getRotationMatrix(indexMax1, indexMax2, cos, sin, DEG);

            Q = Q.multiply(rotationMatrix);
            newA = rotationMatrix.transpose().multiply(newA).multiply(rotationMatrix);

            sumSqrt = 0;
            for (int i = 0; i < DEG; i++) {
                if (i != indexMax1) {
                    sumSqrt += Math.pow(newA.getElem(indexMax1, i), 2);
                }
            }
            sumSqrts.set(indexMax1, sumSqrt);

            sumSqrt = 0;
            for (int i = 0; i < DEG; i++) {
                if (i != indexMax2) {
                    sumSqrt += Math.pow(newA.getElem(indexMax2, i), 2);
                }
            }
            sumSqrts.set(indexMax2, sumSqrt);

            indexMax1 = sumSqrts.indexOf(Collections.max(sumSqrts));
            indexMax2 = newA.indexOfMaxElemInLine(indexMax1);
            numIterations++;
        }

        List<Vector> eigenvectors = new ArrayList<>();
        System.out.println("Eigenvalues and Eigenvectors:");
        for (int i = 0; i < DEG; i++) {
            System.out.print(newA.getElem(i, i) + ": ");

            Vector eigenvector = new Vector(DEG);
            for (int j = 0; j < DEG; j++) {
                System.out.print(Math.round(Q.getElem(j, i) * 100.0) / 100.0 + " ");

                eigenvector.addElem(Q.getElem(j, i));
            }
            System.out.println();

            eigenvectors.add(eigenvector);
        }

        System.out.print("Rate (Ax - лx): ");
        for (int i = 0; i < DEG; i++) {
            System.out.print(A.multiply(eigenvectors.get(i)).getVectorRate(eigenvectors.get(i).multiplyByScalar(newA.getElem(i, i))) + " ");
        }

        System.out.println();

        System.out.println("Iterations: " + numIterations);
    }
}
