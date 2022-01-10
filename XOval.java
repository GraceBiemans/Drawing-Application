// Grace Biemans
// geb965 11279874

package com.example.assignment3;

import javafx.scene.paint.Color;

public class XOval extends XShape{
    double radX, radY, centerX, centerY, topBoxY, bottomBoxY, leftBoxX, rightBoxX;

    public XOval(Color c, double newRadX, double newRadY, double x, double y) {
        super(c);
        radX = newRadX;
        radY = newRadY;
        centerX = x;
        centerY = y;

        topBoxY = centerY - radY;
        bottomBoxY = centerY + radY;
        leftBoxX = centerX - radX;
        rightBoxX = centerX + radX;
    }

    /**
     * Check if hit the bounding box
     */
    public boolean checkHit(double x, double y) {
        return x >= leftBoxX && x <= rightBoxX && y >= topBoxY && y <= bottomBoxY;
    }

    /**
     * Check if hit the shape within the bounding box
     */
    public boolean checkHitShape(double x, double y) {
        if (x >= leftBoxX && x <= rightBoxX && y >= topBoxY && y <= bottomBoxY) {
            double ovalRegion = ((Math.pow((x - centerX), 2)) / Math.pow(radX, 2) + (Math.pow((y - centerY), 2)) / Math.pow(radY, 2));
            return ovalRegion <= 1 && ovalRegion >= -1;
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
            radX = (x - leftBoxX) / 2;

            radY = radX / 2;
            bottomBoxY = topBoxY + radY;

            centerX = leftBoxX + radX;
            centerY = topBoxY + radY;
        }
        else {
            bottomBoxY = y;
            radY = (y - topBoxY) / 2;

            radX = radY * 2;
            rightBoxX = leftBoxX + radX * 2;

            centerX = leftBoxX + radX;
            centerY = topBoxY + radY;
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
