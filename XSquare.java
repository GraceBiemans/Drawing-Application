// Grace Biemans
// geb965 11279874

package com.example.assignment3;

import javafx.scene.paint.Color;

public class XSquare extends XShape{

    double left, top, width, height;


    public XSquare(Color c, double newLeft, double newTop, double newWidth, double newHeight) {
        super(c);
        left = newLeft;
        top = newTop;
        width = newWidth;
        height = newHeight;
    }

    /**
     * Check if hit the shape
     */
    public boolean checkHit(double x, double y) {
        x = x * 500 / 2000;
        y = y * 500 / 2000;
        return x >= (left * 500 / 2000) && x <= (left * 500 / 2000) + width && y >= (top * 500 / 2000) && y <= (top * 500 / 2000) + height;
    }

    /**
     * Check if hit the resizing handle
     */
    public boolean checkOnHandle(double x, double y) {
        x = x * 500 / 2000;
        y = y * 500 / 2000;
        return x >= ((left * 500 / 2000) + width - 0.005) && x <= ((left * 500 / 2000) + width + 0.005) && y >= ((top * 500 / 2000) + height - 0.005) && y <= ((top * 500 / 2000) + height + 0.005);
    }

    /**
     * Move the shape
     */
    public void move(double dX, double dY) {
        left += dX;
        top += dY;
    }

    /**
     * Resize the shape
     */
    public void resize(double x, double y) {
        width = x - (left * 500 / 2000);
        height = y - (top * 500 / 2000);
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
