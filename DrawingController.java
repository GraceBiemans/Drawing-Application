// Grace Biemans
// geb965 11279874
package com.example.assignment3;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.Objects;


public class DrawingController {
    InteractionModel imodel;
    DrawingModel model;

    double prevX, prevY;

    enum State { READY, DRAGGING, PREPARE_CREATE, RESIZING, PANNING}
    State currentState = State.READY;

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
                boolean hit = model.checkHit(normX, normY);
                // hit a shape
                if (hit && event.getButton().equals(MouseButton.PRIMARY)) {
                    // unselect the currently selected shape
                    imodel.unselect();

                    // select the shape that was hit
                    XShape toSelect = model.whichShape(normX, normY);
                    toSelect.setZ(model.zCounter);
                    model.zCounter++;
                    imodel.setSelectedShape(toSelect);
                    model.moveToTop(toSelect);

                    // if oval or circle, checkHit only checks if in bounding box
                    if (imodel.selectedShape instanceof XCircle || imodel.selectedShape instanceof XOval) {
                        if (imodel.selectedShape instanceof XCircle) {
                            // if hit the shape, dragging mode
                            if (((XCircle) imodel.selectedShape).checkHitShape(normX, normY)) {
                                currentState = State.DRAGGING;
                            }
                            // if hit the handle, resizing mode
                            else if (imodel.selectedShape.checkOnHandle(normX, normY)) {
                                currentState = State.RESIZING;
                            }
                            // the shape was not hit, but within bounding box, so clicked on background
                            else {
                                // if a shape is selected, unselect
                                if (imodel.selectedShape != null) {
                                    imodel.unselect();
                                }
                                // if a shape is not selected when pressed on background, create new shape
                                else {
                                    currentState = State.PREPARE_CREATE;
                                }
                            }
                        }
                        // same as above with circle, different shape
                        else {
                            if (((XOval) imodel.selectedShape).checkHitShape(normX, normY)) {
                                currentState = State.DRAGGING;
                            }
                            else if (imodel.selectedShape.checkOnHandle(normX, normY)) {
                                currentState = State.RESIZING;
                            }
                            else {
                                // if a shape is selected, unselect
                                if (imodel.selectedShape != null) {
                                    imodel.unselect();
                                }
                                // if a shape is not selected when pressed on background, create new shape
                                else {
                                    currentState = State.PREPARE_CREATE;
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

                            // if we are on the resizing handle, resize
                            if (imodel.selectedShape.checkOnHandle(normX, normY)) {
                                currentState = State.RESIZING;
                            } else {
                                currentState = State.DRAGGING;
                            }
                        }
                        // it is a circle or oval
                        else {
                            if (toSelect instanceof  XCircle) {
                                if (((XCircle) toSelect).checkHitShape(normX, normY)) {
                                    toSelect.setZ(model.zCounter);
                                    model.zCounter++;
                                    imodel.setSelectedShape(toSelect);
                                    model.moveToTop(toSelect);

                                    // if we are on the resizing handle, resize
                                    if (imodel.selectedShape.checkOnHandle(normX, normY)) {
                                        currentState = State.RESIZING;
                                    } else {
                                        currentState = State.DRAGGING;
                                    }
                                }
                                // on background
                                else {
                                    // if a shape is selected, unselect
                                    if (imodel.selectedShape != null) {
                                        imodel.unselect();
                                    }
                                    // if a shape is not selected when pressed on background, create new shape
                                    else {
                                        currentState = State.PREPARE_CREATE;
                                    }
                                }
                            }
                            else {
                                if (((XOval) toSelect).checkHitShape(normX, normY)) {
                                    toSelect.setZ(model.zCounter);
                                    model.zCounter++;
                                    imodel.setSelectedShape(toSelect);
                                    model.moveToTop(toSelect);

                                    // if we are on the resizing handle, resize
                                    if (imodel.selectedShape.checkOnHandle(normX, normY)) {
                                        currentState = State.RESIZING;
                                    } else {
                                        currentState = State.DRAGGING;
                                    }
                                }
                                // on background
                                else {
                                    // if a shape is selected, unselect
                                    if (imodel.selectedShape != null) {
                                        imodel.unselect();
                                    }
                                    // if a shape is not selected when pressed on background, create new shape
                                    else {
                                        currentState = State.PREPARE_CREATE;
                                    }
                                }
                            }
                        }
                    }
                }
                // If right-clicked, go to drag
                else if (event.getButton().equals(MouseButton.SECONDARY)) {
                   currentState = State.PANNING;
                }
                // context: on background
                else {
                    // if a shape is selected, unselect
                    if (imodel.selectedShape != null) {
                       imodel.unselect();
                    }
                    // if a shape is not selected when pressed on background, create new shape
                    else {
                        currentState = State.PREPARE_CREATE;
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
            case PANNING -> {
                imodel.moveView(dX, dY);
                model.notifySubs();
            }
        }
    }

    public void handleReleased(double normX, double normY, MouseEvent e) {
        normX = (normX * 2000 - imodel.viewLeft) / 500;
        normY = (normY * 2000 - imodel.viewTop) / 500;

        switch(currentState) {
            case DRAGGING, RESIZING, PANNING -> {
                // context: none
                currentState = State.READY;
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
                currentState = State.READY;
            }
        }
    }

    /**
     * Handles when a button in the shape toolbar is pressed
     * @param selectedButton: the button that was selected
     * @postcond: selected shape tool button held by imodel is changed
     */
    public void handleShapeChanged(Button selectedButton) {
        // get the shape within the button
        Button b = imodel.getSelectedShapeToolButton();
        VBox n = (VBox) b.getGraphic();
        Shape s = (Shape) n.getChildren().get(0);

        // change the color of the previously selected shape back to black
        if (Objects.equals(imodel.getSelectedShapeToolString(), "Line")) {
            s.setStroke(Color.BLACK);
        }
        else {
            s.setFill(Color.BLACK);
        }

        // select the new shape button
        imodel.setSelectedShapeTool(selectedButton);

        // color the selected shape button
        Button b2 = imodel.getSelectedShapeToolButton();
        VBox n2 = (VBox) b2.getGraphic();
        Shape s2 = (Shape) n2.getChildren().get(0);
        s2.setFill(imodel.selectedColor);
    }

    /**
     * Handles when a button in the color toolbar is pressed
     * @param selectedButton: the button that was selected
     * @postcond: the selected color tool button held by imodel is changed
     */
    public void handleColorChanged(Button selectedButton) {
        // remove outline from the button to be unselected
        imodel.selectedColorButton.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(1.0), BorderStroke.THIN)));

        // select the new button
        imodel.setSelectedColor((Color) selectedButton.getBackground().getFills().get(0).getFill());
        imodel.setSelectedColorButton(selectedButton);
        selectedButton.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(1.0), BorderStroke.THIN)));

        // update the shape toolbar color
        imodel.setShapeToolColor((Color) selectedButton.getBackground().getFills().get(0).getFill());
    }

    /**
     * Handles when a key is released
     * @param e: the key event
     */
    public void handleKeyReleased(KeyEvent e) {
        switch (currentState) {
            case READY -> {
                // if it was the correct key and a shape is selected, delete the shape from the model
                if ((e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.DELETE) && imodel.selectedShape != null) {
                    model.removeShape(imodel.selectedShape);
                    imodel.selectedShape = null;
                }
            }
        }
    }
}
