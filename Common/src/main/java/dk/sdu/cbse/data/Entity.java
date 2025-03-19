package dk.sdu.cbse.data;

import java.util.UUID;

public abstract class Entity {
    private UUID id = UUID.randomUUID();

    private double[] polygon;
    private Vector position = new Vector(0,0);
    private double rotation;
    private double radius;
    private double[] color;
    private Layer layer = Layer.Default;
    private boolean dead;

    public UUID getId() {
        return id;
    }

    public double[] getPolygonCoordinates() {
        return polygon;
    }

    public void setPolygonCoordinates(double... polygonCoordinates) {
        this.polygon = polygonCoordinates;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector newPos) {
        position = newPos;
    }

    public double[] getColor() {
        return color;
    }

    public void setColor(double r, double g, double b) {
        this.color = new double[]{r,g,b};
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
