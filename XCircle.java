// Grace Biemans
// geb965 11279874

package com.example.assignment3;

import javafx.scene.paint.Color;

public class XCircle extends XShape{
    double radius, centerX, centerY, topBoxY, bottomBoxY, leftBoxX, rightBoxX;

    public XCircle(Color c, double newRadius, double newCenterX, double newCenterY) {
        super(c);
        radius = newRadius;
        centerX = newCenterX;
        centerY = newCenterY;

        topBoxY = centerY - radius;
        bottomBoxY = centerY + radius;
        leftBoxX = centerX - radius;
        rightBoxX = centerX + radius;
    }

    /**
     * Check if hit the bounding box
     */
    public boolean checkHit(double x, double y) {
        return x >= leftBoxX && x <= rightBoxX&& y >= topBoxY && y <= bottomBoxY;
    }

    /**
     * Check if hit the shape within the bounding box
     */
    public boolean checkHitShape(double x, double y) {
        if (x >= leftBoxX && x <= rightBoxX && y >= topBoxY && y <= bottomBoxY) {
            // it is in bounding box, check if in circle
            double distance2 = Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2);
            double rad2 = Math.pow(radius, 2);
            return distance2 <= rad2;
        }
        else {  // not in the bounding box
            return false;
        }
    }

    /**
     * Check if hit the resizing handle
     */
    public boolean checkOnHandle(double x, double y) {
        return x >= (rightBoxX - 0.02) && x <= (rightBoxX + 0.02) && y >= (bottomBoxY - 0.02) && y <= (bottomBoxY + 0.02);
    }

    /**
     * Move the shape
     */
    public void move(double dX, double dY) {
        centerX += dX;
        centerY += dY;

        topBoxY += dY;
        bottomBoxY += dY;
        leftBoxX += dX;
        rightBoxX += dX;
    }

    /**
     * Resize the shape
     */
    public void resize(double x, double y) {
        x = x * 2000 / 500;
        y = y * 2000 / 500;
        if (x >= y) {
            rightBoxX = x;
            radius = (x - leftBoxX) / 2;
            bottomBoxY = topBoxY + radius * 2;
            centerX = leftBoxX + radius;
            centerY = topBoxY + radius;
        }
        else {
            bottomBoxY = y;
            radius = (y - topBoxY) / 2;
            rightBoxX = leftBoxX + radius * 2;
            centerX = leftBoxX + radius;
            centerY = topBoxY + radius;
        }
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
