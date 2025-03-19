package dk.sdu.cbse.data;

public class Time {
    private double now;
    private double deltaTime;


    public double getDeltaTime() {
        return deltaTime;
    }

    public void setDeltaTime(double deltaTime) {
        this.deltaTime = deltaTime;
    }

    public double getNow() {
        return now;
    }

    public void setNow(double now) {
        this.now = now;
    }
}
