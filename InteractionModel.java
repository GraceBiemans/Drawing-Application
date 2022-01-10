// Grace Biemans
// geb965 11279874

package com.example.assignment3;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Objects;

public class InteractionModel {
    XShape selectedShape;
    Color selectedColor;
    Button selectedColorButton;
    Button selectedShapeTool;
    ArrayList<DrawingModelSubscriber> subscribers;

    double viewLeft, viewTop;

    public InteractionModel() {
        subscribers = new ArrayList<>();
        selectedColor = Color.AQUAMARINE;
        selectedShape = null;
        viewLeft = 0;
        viewTop = 0;
    }

    /**
     * Unselect the currently selected shape
     */
    public void unselect() {
        selectedShape = null;
        notifySubs();
    }

    /**
     * Change the color chosen in the color toolbar
     * @param c: the new color
     */
    public void setSelectedColor(Color c) {
        selectedColor = c;
    }

    /**
     * Change the chosen button in the color toolbar
     * @param button: the newly selected button
     */
    public void setSelectedColorButton(Button button) {
        selectedColorButton = button;
    }

    /**
     * Change the chosen button in the shape toolbar
     * @param button: the newly selected button
     */
    public void setSelectedShapeTool(Button button) {
        selectedShapeTool = button;
        setShapeToolColor(selectedColor);
    }

    /**
     * Change the color of the shape in the selected shape toolbar button
     * @param color: color to be changed to
     */
    public void setShapeToolColor(Color color) {
        VBox box = (VBox) selectedShapeTool.getGraphic();
        Shape s = (Shape) box.getChildren().get(0);
        if (Objects.equals(getSelectedShapeToolString(), "Line")) {
            s.setStroke(color);
        }
        else {
            s.setFill(color);
        }
    }

    /**
     * Change the current shape
     * @param shape: the new selected shape
     */
    public void setSelectedShape(XShape shape) {
        selectedShape = shape;
        notifySubs();
    }

    /**
     * Add a view to the list of subscribers
     * @param sub: the view
     */
    public void addSubscriber(DrawingModelSubscriber sub) {
        subscribers.add(sub);
    }

    /**
     * Notify the subscribing views of changes
     */
    public void notifySubs() {
        subscribers.forEach(sub -> sub.modelChanged());
    }

    /**
     * Get the selected shape name
     * @return: the name of the selected shape
     */
    public String getSelectedShapeToolString() {
        VBox box = (VBox) selectedShapeTool.getGraphic();
        Label l = (Label) box.getChildren().get(1);
        return l.getText();
    }

    /**
     * Get the currently selected button in the shape toolbar
     */
    public Button getSelectedShapeToolButton() {
        return selectedShapeTool;
    }

    /**
     * Pan the view
     * @param x: change in x
     * @param y: change in y
     */
    public void moveView(double x, double y) {
        if ((viewLeft + (x * -1) * 100) <= 75 && (viewLeft + (x * -1) * 100 >= 0)) {
            viewLeft += (x * -1) * 100;
        }
        if ((viewTop + (y * -1) * 100) <= 75 && (viewTop + (y * -1) * 100) >= 0) {
            viewTop += (y * -1) * 100;
        }
    }

    /**
     * Check if the viewfinder in the miniview was hit
     * @param normX: click x coordinate
     * @param normY: click y coordinate
     * @return: whether the viewfinder was hit
     */
    public boolean checkHit(double normX, double normY) {
        normX = normX * 2000 / 75;
        normY = normY * 2000 / 75;
        return normX >= viewLeft && normX <= viewLeft + 25 && normY >= viewTop && normY <= viewTop + 25;
    }
}
