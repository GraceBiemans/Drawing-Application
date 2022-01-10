// Grace Biemans
// geb965 11279874

package com.example.assignment3;

import javafx.scene.paint.Color;

public abstract class XShape {

    Color color;
    int z;

    public XShape(Color c) {
        color = c;
    }

    /**
     * Check if hit the shape
     */
    public abstract boolean checkHit(double x, double y);

    /**
     * Check if hit the resizing handle
     */
    public abstract boolean checkOnHandle(double x, double y);

    /**
     * Move the shape
     */
    public abstract void move(double dX, double dY);

    /**
     * Set the z value of the shape
     */
    public abstract int getZ();

    /**
     * Get the z value of the shape
     */
    public abstract void setZ(int newZ);
}
