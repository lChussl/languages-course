public class MyTriangle {
    private MyPoint v1;
    private MyPoint v2;
    private MyPoint v3;

    private static int count = 0;

    public MyTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        this.v1 = new MyPoint(x1, y1);
        this.v2 = new MyPoint(x2, y2);
        this.v3 = new MyPoint(x3, y3);
        count++;
    }

    public MyTriangle(MyPoint v1, MyPoint v2, MyPoint v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        count++;
    }

    public MyPoint getV1() {
        return v1;
    }

    public MyPoint getV2() {
        return v2;
    }

    public MyPoint getV3() {
        return v3;
    }

    public static int getCount() {
        return count;
    }

    public String toString() {
        return "Triangle  (" + v1.getX() + "," + v1.getY() + "), (" + v2.getX() + "," + v2.getY() + "), (" + v3.getX() + "," + v3.getY() + ")";
    }

    public double getPerimeter() {
        return v1.distance(v2) + v2.distance(v3) + v3.distance(v1);
    }

    public String getType() {
        double d1 = v1.distance(v2);
        double d2 = v2.distance(v3);
        double d3 = v3.distance(v1);
        if (MathUtils.calculateHypotenuse(d1, d2) == d3 || MathUtils.calculateHypotenuse(d2, d3) == d1 || MathUtils.calculateHypotenuse(d3, d1) == d2) {
            return "right";
        } else if (d1 == d2 && d2 == d3) {
            return "equilateral";
        } else if (d1 == d2 || d2 == d3 || d3 == d1) {
            return "isosceles";
        } else {
            return "scalene";
        }
    }
}


