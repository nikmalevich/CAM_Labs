package com.company;

public class Functions {
    public static double round(double num, int numSigns) {
        return Math.round(num * Math.pow(10.0, numSigns)) / Math.pow(10.0, numSigns);
    }

    public static double random(double down, double up) {
        return ((Math.random() * (up - down)) + down);
    }

    public static Vector thomasAlgorithm(Vector a, Vector b, Vector c, Vector f) {
        Vector y = new Vector();

        Vector r = new Vector();
        Vector h = new Vector();

        r.addToEnd(b.getElem(0) / c.getElem(0));
        h.addToEnd(f.getElem(0) / c.getElem(0));

        for (int i = 1; i < f.getDim() - 1; i++) {
            r.addToEnd(b.getElem(i) / (c.getElem(i) - r.getElem(i - 1) * a.getElem(i - 1)));
            h.addToEnd((f.getElem(i) + h.getElem(i - 1) * a.getElem(i - 1))
                    / (c.getElem(i) - r.getElem(i - 1) * a.getElem(i - 1)));
        }

        h.addToEnd((f.getElem(f.getDim() - 1) + h.getElem(f.getDim() - 2) * a.getElem(f.getDim() - 2))
                / (c.getElem(f.getDim() - 1) - r.getElem(f.getDim() - 2) * a.getElem(f.getDim() - 2)));

        y.addToStart(Functions.round(h.getElem(f.getDim() - 1), 2));

        for (int i = f.getDim() - 2; i >= 0; i--) {
            y.addToStart(Functions.round(r.getElem(i) * y.getElem(0) + h.getElem(i), 2));
        }

        return y;
    }
}
