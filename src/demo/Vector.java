package demo;

public class Vector {
    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(double angle) {
        this.x = Math.cos(angle);
        this.y = Math.sin(angle);
    }

    public double getAngle(double def) {
        if (Math.sqrt(this.y * this.y + this.x * this.x) == 0) {
            return def;
        }
        double result = Math.atan(this.y/this.x);
        return (this.x >= 0 ? result : result + Math.PI);
    }

    public double addAndUpdate(double x, double y, double def) {
        double newX = this.x + x;
        double newY = this.y + y;
        this.x = newX;
        this.y = newY;
        return this.getAngle(def);
    }
}

