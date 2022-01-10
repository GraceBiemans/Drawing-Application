// Grace Biemans
// geb965 11279874

package com.example.assignment3;

import javafx.scene.paint.Color;

public class XLine extends XShape{
    double x1, y1, x2, y2, length;

    public XLine(Color c, double newp1Left, double newp1Top, double newp2Left, double newp2Top) {
        super(c);
        x1 = newp1Left;
        y1 = newp1Top;
        x2 = newp2Left;
        y2 = newp2Top;
        length = Math.sqrt(Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2));
    }

    /**
     * Check if hit the shape
     */
    public boolean checkHit(double x, double y) {
        double ratioA = (y1 - y2) / length;
        double ratioB = (x2 - x1) / length;
        double ratioC = -1 * ((y1 - y2) * x1 + (x2 - x1) * y1) / length;
        double distance = ratioA * x + ratioB * y + ratioC;
        return distance <= 0.02 && distance >= -0.02 ;
    }

    /**
     * Check if hit the resizing handle
     */
    public boolean checkOnHandle(double x, double y) {
        return x >= (x2 - 0.02) && x <= (x2 + 0.02) && y >= (y2 - 0.02) && y <= (y2 + 0.02);
    }

    /**
     * Move the shape
     */
    public void move(double dX, double dY) {
        x1 += dX;
        y1 += dY;
        x2 += dX;
        y2 += dY;
    }

    /**
     * Resize the shape
     */
    public void resize(double x, double y) {
        x = x * 2000 / 500;
        y = y * 2000 / 500;
        x2 = x;
        y2 = y;
        length = Math.sqrt(Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2));
    }

    /**
     * Set the z value of the shape
     */
    public void setZ(int newZ) {
        z = newZ;
    }

    /**
     * Get the z value of the shape
     */
    public int getZ() {
        return z;
    }
}
