package com.company;

public class Main {
    private final static int DEG = 10;

    public static void main(String[] args) throws Exception {
        System.out.println("Task 1:");

        Matrix A = new Matrix(DEG, DEG);

        A = A.multiply(A.transpose());

        System.out.println("Matrix A:");
        A.printMatrix();

        Vector exactX = new Vector(DEG);

        Vector f = A.multiply(exactX.toMatrix()).toVector();

        System.out.println("Vector f:");
        f.printVector();

        System.out.println("Exact vector x:");
        exactX.printVector();

        Vector x = new Vector(DEG);

        System.out.println("Table:");
        int iteration = 0;

        iteration = Main.relaxMethod(A, f, x, 0.2);
        System.out.println(0.2 + " " + iteration + " " + exactX.getVectorRate(x));
        iteration = Main.relaxMethod(A, f, x, 0.5);
        System.out.println(0.5 + " " + iteration + " " + exactX.getVectorRate(x));
        iteration = Main.relaxMethod(A, f, x, 0.8);
        System.out.println(0.8 + " " + iteration + " " + exactX.getVectorRate(x));
        iteration = Main.relaxMethod(A, f, x, 1);
        System.out.println(1.0 + " " + iteration + " " + exactX.getVectorRate(x));
        iteration = Main.relaxMethod(A, f, x, 1.3);
        System.out.println(1.3 + " " + iteration + " " + exactX.getVectorRate(x));
        iteration = Main.relaxMethod(A, f, x, 1.5);
        System.out.println(1.5 + " " + iteration + " " + exactX.getVectorRate(x));
        iteration = Main.relaxMethod(A, f, x, 1.8);
        System.out.println(1.8 + " " + iteration + " " + exactX.getVectorRate(x));

        System.out.println();

        System.out.println("Received vector x:");
        x.printVector();

        System.out.println("Accuracy:");
        System.out.println(x.getVectorRate(exactX));

        System.out.println("Task 2:");

        iteration = gradientDescentMethod(A, f, x);

        System.out.println("Iteration: " + iteration);

        System.out.println("Received vector x:");
        x.printVector();

        System.out.println("Accuracy:");
        System.out.println(x.getVectorRate(exactX));
    }

    public static int relaxMethod(Matrix A, Vector f, Vector x, double w) throws Exception {
        int iteration = 0;

        for (int i = 0; i < DEG; i++) {
            x.setElem(i, f.getElem(i) / A.getElem(i, i));
        }

        Vector prevX;

        do {
            prevX = new Vector(x);

            for (int i = 0; i < DEG; i++) {
                double sum1 = 0;

                for (int j = 0; j < i; j++) {
                    sum1 += A.getElem(i, j) / A.getElem(i, i) * x.getElem(j);
                }

                double sum2 = 0;

                for (int j = i + 1; j < DEG; j++) {
                    sum2 += A.getElem(i, j) / A.getElem(i, i) * prevX.getElem(j);
                }

                double num = (1 - w) * prevX.getElem(i) - w * (sum1 + sum2 - f.getElem(i) / A.getElem(i, i));

                x.setElem(i, num);
                iteration++;
            }
        } while (x.getVectorRate(prevX) >= Math.pow(10, -5));

        return iteration;
    }

    public static int gradientDescentMethod(Matrix A, Vector f, Vector x) throws Exception {
        int iteration = 0;

        for (int i = 0; i < DEG; i++) {
            x.setElem(i, f.getElem(i) / A.getElem(i, i));
        }

        Vector prevX;

        do {
            prevX = new Vector(x);

            Vector r = Vector.subtract(A.multiply(prevX.toMatrix()).toVector(), f);

            double scalar = r.scalarProduct(r) / A.multiply(r.toMatrix()).toVector().scalarProduct(r);

            Vector temp = Vector.subtract(prevX, r.multiplyByScalar(scalar));

            for (int i = 0; i < DEG; i++) {
                x.setElem(i, temp.getElem(i));
            }

            iteration++;

        } while (x.getVectorRate(prevX) >= Math.pow(10, -5));

        return iteration;
    }
}
