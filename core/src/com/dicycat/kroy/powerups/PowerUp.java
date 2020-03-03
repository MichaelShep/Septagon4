package com.dicycat.kroy.powerups;

public class PowerUp {
    //FIELDS
    private double x;
    private double y;
    private int r;

    private int type;

    // 1 -- immunity in 5s
    //
    //

    //CONSTRUCTOR
    public PowerUp(double x, double y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    //FUNCTIONS

    public double getx() { return x; }
    public double gety() { return y; }
    public double getr() { return r; }

    public int getType() { return type; }
}
