// Grace Biemans
// geb965 11279874

package com.example.assignment3;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Objects;


public class MiniDrawingController extends DrawingController {

    InteractionModel imodel;
    DrawingModel model;

    double prevX, prevY;

    public void setInteractionModel(InteractionModel newimodel) {
        imodel = newimodel;
    }

    public void setModel(DrawingModel newmodel) {
        model = newmodel;
    }

    public void handlePressed(double normX, double normY, MouseEvent event) {
        normX = (normX * 2000 - imodel.viewLeft) / 500;
        normY = (normY * 2000 - imodel.viewTop) / 500;

        prevX = normX;
        prevY = normY;

        switch(currentState) {
            case READY -> {
                boolean hitView = imodel.checkHit(normX, normY);
                boolean hit = model.checkHit(normX, normY);

                // context: on the viewfinder
                if (hitView && event.getButton().equals(MouseButton.PRIMARY)) {
                    currentState = State.PANNING;
                }
                // context: on a shape
                else if (hit && event.getButton().equals(MouseButton.PRIMARY)) {
                    imodel.unselect();

                    // select the shape
                    XShape toSelect = model.whichShape(normX, normY);
                    toSelect.setZ(model.zCounter);
                    model.zCounter++;
                    imodel.setSelectedShape(toSelect);
                    model.moveToTop(toSelect);

                    // if one of these, checkHit only checks if in bounding box
                    if (imodel.selectedShape instanceof XCircle || imodel.selectedShape instanceof XOval) {
                        if (imodel.selectedShape instanceof XCircle) {
                            if (((XCircle) imodel.selectedShape).checkHitShape(normX, normY)) {
                                currentState = DrawingController.State.DRAGGING;
                            }
                            else {
                                // if a shape is selected, unselect
                                if (imodel.selectedShape != null) {
                                    imodel.unselect();
                                }
                                // if a shape is not selected when pressed on background, create new shape
                                else {
                                    currentState = DrawingController.State.PREPARE_CREATE;
                                }
                            }
                        }
                        else {
                            if (((XOval) imodel.selectedShape).checkHitShape(normX, normY)) {
                                currentState = DrawingController.State.DRAGGING;
                            }
                            else {
                                // if a shape is selected, unselect
                                if (imodel.selectedShape != null) {
                                    imodel.unselect();
                                }
                                // if a shape is not selected when pressed on background, create new shape
                                else {
                                    currentState = DrawingController.State.PREPARE_CREATE;
                                }
                            }
                        }
                    }
                    else {
                        // unselect the previous
                        imodel.unselect();

                        // select the shape
                        toSelect = model.whichShape(normX, normY);
                        if (!(toSelect instanceof XCircle) && !(toSelect instanceof XOval)) {
                            toSelect.setZ(model.zCounter);
                            model.zCounter++;
                            imodel.setSelectedShape(toSelect);
                            model.moveToTop(toSelect);

                            currentState = DrawingController.State.DRAGGING;
                        }
                        // it is a circle or oval
                        else {
                            if (toSelect instanceof  XCircle) {
                                if (((XCircle) toSelect).checkHitShape(normX, normY)) {
                                    toSelect.setZ(model.zCounter);
                                    model.zCounter++;
                                    imodel.setSelectedShape(toSelect);
                                    model.moveToTop(toSelect);

                                    currentState = DrawingController.State.DRAGGING;
                                }
                                // on background
                                else {
                                    // if a shape is selected, unselect
                                    if (imodel.selectedShape != null) {
                                        imodel.unselect();
                                    }
                                    // if a shape is not selected when pressed on background, create new shape
                                    else {
                                        currentState = DrawingController.State.PREPARE_CREATE;
                                    }
                                }
                            }
                            else {
                                if (((XOval) toSelect).checkHitShape(normX, normY)) {
                                    toSelect.setZ(model.zCounter);
                                    model.zCounter++;
                                    imodel.setSelectedShape(toSelect);
                                    model.moveToTop(toSelect);

                                    currentState = DrawingController.State.DRAGGING;
                                }
                                // on background
                                else {
                                    // if a shape is selected, unselect
                                    if (imodel.selectedShape != null) {
                                        imodel.unselect();
                                    }
                                    // if a shape is not selected when pressed on background, create new shape
                                    else {
                                        currentState = DrawingController.State.PREPARE_CREATE;
                                    }
                                }
                            }
                        }
                    }
                }
                // context: on background
                else {
                    // if a shape is selected, unselect
                    if (imodel.selectedShape != null) {
                        imodel.unselect();
                    }
                    // if a shape is not selected when pressed on background, create new shape
                    else {
                        currentState = DrawingController.State.PREPARE_CREATE;
                    }
                }
            }
        }
    }

    public void handleDragged(double normX, double normY, MouseEvent e, double width, double height) {
        normX = (normX * 2000 - imodel.viewLeft) / 500;
        normY = (normY * 2000 - imodel.viewTop) / 500;

        double dX = normX - prevX;
        double dY = normY - prevY;
        prevX = normX;
        prevY = normY;

        switch(currentState) {
            case PANNING -> {
                dX = dX * 100 / 500;
                dY = dY * 100 / 500;
                imodel.moveView(dX * -1, dY * -1);
                model.notifySubs();
            }
            case DRAGGING -> {
                // drag the shape
                model.moveShape(imodel.selectedShape, dX, dY);
            }
            case PREPARE_CREATE -> {
                // context: left button pressed
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    if (Objects.equals(imodel.getSelectedShapeToolString(), "Oval")) {
                        imodel.unselect();
                        XShape toSelect = model.createOval(imodel.selectedColor, normX, normY, 0, 0);
                        toSelect.setZ(model.zCounter);
                        model.zCounter ++;
                        imodel.setSelectedShape(toSelect);
                        model.moveToTop(toSelect);
                    }
                    else if (Objects.equals(imodel.getSelectedShapeToolString(), "Rect")) {
                        imodel.unselect();
                        XShape toSelect = model.createRectangle(imodel.selectedColor, normX, normY, 0, 0);
                        toSelect.setZ(model.zCounter);
                        model.zCounter ++;
                        imodel.setSelectedShape(toSelect);
                        model.moveToTop(toSelect);
                    }
                    else if (Objects.equals(imodel.getSelectedShapeToolString(), "Square")) {
                        imodel.unselect();
                        XShape toSelect = model.createSquare(imodel.selectedColor, normX, normY, 0, 0);
                        toSelect.setZ(model.zCounter);
                        model.zCounter ++;
                        imodel.setSelectedShape(toSelect);
                        model.moveToTop(toSelect);
                    }
                    else if (Objects.equals(imodel.getSelectedShapeToolString(), "Circle")) {
                        imodel.unselect();
                        XShape toSelect = model.createCircle(imodel.selectedColor, normX, normY, 0);
                        toSelect.setZ(model.zCounter);
                        model.zCounter ++;
                        imodel.setSelectedShape(toSelect);
                        model.moveToTop(toSelect);
                    }
                    else if  (Objects.equals(imodel.getSelectedShapeToolString(), "Line")) {
                        imodel.unselect();
                        XShape toSelect = model.createLine(imodel.selectedColor, normX, normY, 0, 0);
                        toSelect.setZ(model.zCounter);
                        model.zCounter ++;
                        imodel.setSelectedShape(toSelect);
                        model.moveToTop(toSelect);
                    }

                    currentState = State.RESIZING;
                }
                // context: right button pressed
                else if (e.getButton().equals(MouseButton.SECONDARY)) {
                    currentState = State.READY;
                }
            }
            case RESIZING -> {
                if (imodel.selectedShape instanceof XOval) {
                    model.resizeOval((XOval) imodel.selectedShape, e.getX() / width, e.getY() / height);
                }
                else if (imodel.selectedShape instanceof XRectangle) {
                    model.resizeRectangle((XRectangle) imodel.selectedShape, e.getX() / width, e.getY() / width);
                }
                else if (imodel.selectedShape instanceof XSquare) {
                    model.resizeSquare((XSquare) imodel.selectedShape, e.getX() / width, e.getY() / height);
                }
                else if (imodel.selectedShape instanceof XCircle) {
                    model.resizeCircle((XCircle) imodel.selectedShape, e.getX() / width, e.getY() / height);
                }
                else if (imodel.selectedShape instanceof XLine){
                    model.resizeLine((XLine) imodel.selectedShape, e.getX() / width, e.getY() / height);
                }
            }
        }
    }

    public void handleReleased(double normX, double normY, MouseEvent e) {
        normX = (normX * 2000 - imodel.viewLeft) / 500;
        normY = (normY * 2000 - imodel.viewTop) / 500;

        switch(currentState) {
            case DRAGGING, RESIZING, PANNING -> {
                // context: none
                currentState = DrawingController.State.READY;
            }
            case PREPARE_CREATE -> {
                if (Objects.equals(imodel.getSelectedShapeToolString(), "Oval")) {
                    XShape toSelect = model.createOval(imodel.selectedColor, normX, normY, 0.2, 0.1);
                    toSelect.setZ(model.zCounter);
                    model.zCounter ++;
                    imodel.setSelectedShape(toSelect);
                    model.moveToTop(toSelect);
                }
                else if (Objects.equals(imodel.getSelectedShapeToolString(), "Rect")) {
                    XShape toSelect = model.createRectangle(imodel.selectedColor, normX, normY, 0.2, 0.1);
                    toSelect.setZ(model.zCounter);
                    model.zCounter ++;
                    imodel.setSelectedShape(toSelect);
                    model.moveToTop(toSelect);
                }
                else if (Objects.equals(imodel.getSelectedShapeToolString(), "Square")) {
                    XShape toSelect = model.createSquare(imodel.selectedColor, normX, normY, 0.1, 0.1);
                    toSelect.setZ(model.zCounter);
                    model.zCounter ++;
                    imodel.setSelectedShape(toSelect);
                    model.moveToTop(toSelect);
                }
                else if (Objects.equals(imodel.getSelectedShapeToolString(), "Circle")) {
                    XShape toSelect = model.createCircle(imodel.selectedColor, normX, normY, 0.2);
                    toSelect.setZ(model.zCounter);
                    model.zCounter ++;
                    imodel.setSelectedShape(toSelect);
                    model.moveToTop(toSelect);
                }
                else if (Objects.equals(imodel.getSelectedShapeToolString(), "Line")) {
                    XShape toSelect = model.createLine(imodel.selectedColor, normX, normY, 0.4, 0.4);
                    toSelect.setZ(model.zCounter);
                    model.zCounter ++;
                    imodel.setSelectedShape(toSelect);
                    model.moveToTop(toSelect);
                }
                currentState = DrawingController.State.READY;
            }
        }
    }

    public void handleKeyReleased(KeyEvent e) {
        switch (currentState) {
            case READY -> {
                if (e.getCode() == KeyCode.BACK_SPACE && imodel.selectedShape != null) {
                    model.removeShape(imodel.selectedShape);
                    imodel.selectedShape = null;
                }
            }
        }
    }
}
