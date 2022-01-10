// Grace Biemans
// geb965 11279874

package com.example.assignment3;

import javafx.scene.paint.Color;

import java.util.*;

public class DrawingModel {
    public ArrayList<XShape> shapes;
    ArrayList<DrawingModelSubscriber> subscribers;
    ArrayList<DrawingModelSubscriber> colorSubscribers;

    int zCounter;

    public DrawingModel() {
        shapes = new ArrayList<>();
        subscribers = new ArrayList<>();
        colorSubscribers = new ArrayList<>();
        zCounter = 1;
    }


    public void resizeOval(XOval shape, double newX, double newY) {
        shape.resize(newX, newY);
        notifySubs();
    }

    public XOval createOval(Color c, double x, double y, double w, double h) {
        XOval newShape = new XOval(c, w, h, x, y);
        newShape.setZ(zCounter);
        zCounter ++;
        addShape(newShape);
        return newShape;
    }

    public void resizeRectangle(XRectangle shape, double newX, double newY) {
        shape.resize(newX, newY);
        notifySubs();
    }
    public XRectangle createRectangle(Color c, double x, double y, double w, double h) {
        XRectangle newShape = new XRectangle(c, x, y, w, h);
        newShape.setZ(zCounter);
        zCounter ++;
        addShape(newShape);
        return newShape;
    }

    public void resizeSquare(XSquare shape, double newX, double newY) {
        shape.resize(newX, newY);
        notifySubs();
    }

    public XSquare createSquare(Color c, double x, double y, double w, double h) {
        XSquare newShape = new XSquare(c, x, y, w, h);
        newShape.setZ(zCounter);
        zCounter ++;
        addShape(newShape);
        return newShape;
    }

    public void resizeCircle(XCircle shape, double newX, double newY) {
        shape.resize(newX, newY);
        notifySubs();
    }

    public XCircle createCircle(Color c, double x, double y, double r) {
        XCircle newShape = new XCircle(c, r, x, y);
        newShape.setZ(zCounter);
        zCounter ++;
        addShape(newShape);
        return newShape;
    }

    public void resizeLine(XLine shape, double newX, double newY) {
        shape.resize(newX, newY);
        notifySubs();
    }

    public XLine createLine(Color c, double x, double y, double xlen, double ylen) {
        XLine newShape = new XLine(c, x, y, x + xlen, y + ylen);
        newShape.setZ(zCounter);
        zCounter ++;
        addShape(newShape);
        return newShape;
    }

    public void addShape(XShape newShape) {
        shapes.add(newShape);
        notifySubs();
    }

    public void removeShape(XShape shape) {
        shapes.remove(shape);
        notifySubs();
    }

    public boolean checkHit(double x, double y) {
        return shapes.stream().anyMatch(shape -> shape.checkHit(x, y));
    }

    public XShape whichShape(double x, double y) {
        ArrayList<XShape> validShapes = new ArrayList<>();
        for (XShape shape : shapes) {
            if (shape.checkHit(x, y)) {
                validShapes.add(shape);
            }
        }
        validShapes.sort(Comparator.comparingInt(XShape::getZ));
        return validShapes.get(validShapes.size() - 1);
    }

    public void moveShape(XShape shape, double dX, double dY) {
        shape.move(dX, dY);
        notifySubs();
    }

    public void addSubscriber(DrawingModelSubscriber sub) {
        subscribers.add(sub);
    }

    public void notifySubs() {
        subscribers.forEach(sub -> sub.modelChanged());
    }

    public void moveToTop(XShape toSelect) {
        shapes.remove(toSelect);
        shapes.add(toSelect);
        notifySubs();
    }
}
