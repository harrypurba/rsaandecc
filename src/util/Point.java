package util;

import java.math.BigInteger;

public class Point {
    public BigInteger x;
    public BigInteger y;

    public Point() {
    }

    public Point(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (!this.x.equals(point.x)) return false;
        return this.y.equals(point.y);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        int hashx = x.hashCode();
        int hashy = y.hashCode();
        temp = Double.doubleToLongBits(hashx);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(hashy);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
