package dk.sdu.cbse.data;

public class Vector {
    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double magnitude() {
        return Math.sqrt(x*x + y*y);
    }

    public Vector add(Vector vector) {
        return new Vector(x + vector.getX(), y + vector.getY());
    }

    public Vector scale(double scale) {
        return new Vector(x * scale, y * scale);
    }

    public Vector normalize() {
        double mag = magnitude();
        if (mag == 0)
            return new Vector(0, 0);
        return new Vector(x/mag, y/mag);
    }

    public Vector clamp(double length) {
        Vector normal = normalize();
        double mag = magnitude();
        double f = Math.min(length, mag);
        return new Vector(f * normal.getX(), f * normal.getY());
    }
}
