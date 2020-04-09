package com.company;

public class Main {

    public static void main(String[] args) {
        final int dim = 10;

        Vector a = new Vector(dim - 1);
        System.out.println("a: " + a);

        Vector b = new Vector(dim - 1);
        System.out.println("b: " + b);

        Vector c = new Vector(a, b);
        System.out.println("c: " + c);

        Vector exactY = new Vector(dim);
        System.out.println("y: " + exactY);

        Vector exactF = new Vector(exactY, a, b, c);
        System.out.println("f: " + exactF);

        Vector y = Functions.thomasAlgorithm(a, b, c, exactF);
        System.out.println("~y: " + y);

        Vector f = new Vector(y, a, b, c);

        double discrepancy = 0;
        double roughness = 0;

        for (int i = 0; i < dim; i++) {
            if (Math.abs(exactY.getElem(i) - y.getElem(i)) > roughness) {
                roughness = Math.abs(exactY.getElem(i) - y.getElem(i));
            }

            if (Math.abs(exactF.getElem(i) - f.getElem(i)) > discrepancy) {
                discrepancy = Math.abs(exactF.getElem(i) - f.getElem(i));
            }
        }

        System.out.println("The maximum norm of the discrepancy: " + Double.toString(discrepancy));
        System.out.println("The maximum norm of the roughness: " + Double.toString(roughness));
    }
}
