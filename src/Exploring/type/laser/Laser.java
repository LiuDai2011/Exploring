package Exploring.type.laser;

import Exploring.util.VelocityException;
import arc.math.geom.Point2;

public class Laser {
    public Point2 start = new Point2();
    public float amount = 0.0f;
    public float length = 0.0f;
    private Point2 velocity = new Point2(0, 1);


    public Laser() {
        this(new Point2(0, 0), new Point2(1, 0), 0.0f, 0.0f);
    }

    public Laser(Point2 pos, Point2 vel, float amount, float length) {
        start = pos;
        setVelocity(vel);
        this.amount = amount;
        this.length = length;
    }

    public Point2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2 velocity) throws VelocityException {
        if (velocity.x == 0 && velocity.y == 0)
            throw new VelocityException("velocity cannot be 0, 0!");
        this.velocity = velocity;
    }
}
