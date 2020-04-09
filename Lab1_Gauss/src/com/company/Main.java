package com.company;

public class Main {

    public static void main(String[] args) throws Exception {
        final int dim = 10;

        Matrix A = new Matrix(dim, dim);
        Matrix exactX = new Matrix(dim, 1);

        System.out.println("Matrix A:");
        A.printMatrix();

        System.out.println("Exact solution:");
        exactX.printMatrix();

        try {
            Matrix exactF = A.multiply(exactX);

            System.out.println("Vector f:");
            exactF.printMatrix();

            Matrix extendedA = A.addColumn(exactF);

            Matrix unit = Matrix.getUnit(dim);

            extendedA.forwardGaussMethod(unit);

            Matrix inverseA = new Matrix(dim, dim);

            Matrix x = extendedA.backwardGaussMethod(inverseA, unit);

            System.out.println("Decision received:");
            x.printMatrix();

            Matrix f = A.multiply(x);

            double discrepancy = 0;
            double roughness = 0;

            for (int i = 0; i < dim; i++) {
                if (Math.abs(exactX.getElem(i, 0) - x.getElem(i, 0)) > roughness) {
                    roughness = Math.abs(exactX.getElem(i, 0) - x.getElem(i, 0));
                }

                if (Math.abs(exactF.getElem(i, 0) - f.getElem(i, 0)) > discrepancy) {
                    discrepancy = Math.abs(exactF.getElem(i, 0) - f.getElem(i, 0));
                }
            }

            System.out.println("The maximum norm of the discrepancy: " + Double.toString(discrepancy));
            System.out.println("The maximum norm of the roughness: " + Double.toString(roughness));

            System.out.println("Matrix determinant: " + Double.toString(Math.round(extendedA.getDet() * 100.0) / 100.0));

            if (unit.getDet() == 0) {
                System.out.println("The inverse matrix does not exist");
            }
            else {
                System.out.println("Inverse matrix:");
                inverseA.printMatrix();

                System.out.println("Obtained unit matrix:");
                A.multiply(inverseA).printMatrix();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
