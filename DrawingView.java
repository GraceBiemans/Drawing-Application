// Grace Biemans
// geb965 11279874

package com.example.assignment3;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class DrawingView extends Pane {
    GraphicsContext gc;
    double width, height;
    DrawingModel model;
    private InteractionModel iModel;
    double shapeLeft, shapeTop, shapeWidth, shapeHeight;


    public DrawingView(Canvas myCanvas, double w, double h) {
        width = w;
        height = h;
        gc = myCanvas.getGraphicsContext2D();
        this.getChildren().add(myCanvas);
    }

    public DrawingView() {}

    public void setModel(DrawingModel newModel) {
        model = newModel;
    }

    public void setInteractionModel(InteractionModel newiModel) {
        iModel = newiModel;
    }

    public void draw() {
        gc.clearRect(0, 0, width, height);
        gc.setStroke(Color.BLACK);

        for (XShape shape : model.shapes) {
            if (shape instanceof XSquare) {
                shapeLeft = (((XSquare) shape).left * 500 - (iModel.viewLeft / 100 * 2000));
                shapeTop = (((XSquare) shape).top * 500 - (iModel.viewTop / 100 * 2000));
                shapeWidth = ((XSquare) shape).width * 2000;
                shapeHeight = ((XSquare) shape).height * 2000;
                gc.setFill(shape.color);
                gc.fillRect(shapeLeft, shapeTop, shapeWidth, shapeHeight);
                gc.strokeRect(shapeLeft, shapeTop, shapeWidth, shapeHeight);
            }
            else if (shape instanceof XRectangle) {
                shapeLeft = (((XRectangle) shape).left * 500 - iModel.viewLeft / 100 * 2000);
                shapeTop = (((XRectangle) shape).top * 500 - iModel.viewTop / 100 * 2000);
                shapeWidth = ((XRectangle) shape).width * 2000;
                shapeHeight = ((XRectangle) shape).height * 2000;
                gc.setFill(shape.color);
                gc.fillRect(shapeLeft, shapeTop, shapeWidth, shapeHeight);
                gc.strokeRect(shapeLeft, shapeTop, shapeWidth, shapeHeight);
            }
            else if (shape instanceof XCircle) {
                shapeLeft = (((XCircle) shape).leftBoxX * 500 - iModel.viewLeft / 100 * 2000);
                shapeTop = (((XCircle) shape).topBoxY * 500 - iModel.viewTop / 100 * 2000);
                shapeWidth = ((XCircle) shape).radius * 2000 / 2;
                shapeHeight = ((XCircle) shape).radius * 2000 / 2;
                gc.setFill(shape.color);
                gc.fillOval(shapeLeft, shapeTop, shapeWidth, shapeHeight);
                gc.strokeOval(shapeLeft, shapeTop, shapeWidth, shapeHeight);
            }
            else if (shape instanceof XOval) {
                shapeLeft = (((XOval) shape).leftBoxX * 500 - iModel.viewLeft / 100 * 2000);
                shapeTop = (((XOval) shape).topBoxY * 500 - iModel.viewTop / 100 * 2000);
                shapeWidth = ((XOval) shape).radX * 2000 / 2;
                shapeHeight = ((XOval) shape).radY * 2000 / 2;
                gc.setFill(shape.color);
                gc.fillOval(shapeLeft, shapeTop, shapeWidth, shapeHeight);
                gc.strokeOval(shapeLeft, shapeTop, shapeWidth, shapeHeight);
            }
            else {
                shapeLeft = (((XLine) shape).x1 * 500 - iModel.viewLeft / 100 * 2000);
                shapeTop = (((XLine) shape).y1 * 500 - iModel.viewTop / 100 * 2000);
                shapeWidth = (((XLine) shape).x2 * 500 - iModel.viewLeft / 100 * 2000);
                shapeHeight = (((XLine) shape).y2 * 500 - iModel.viewTop / 100 * 2000);
                gc.setStroke(shape.color);
                gc.strokeLine(shapeLeft, shapeTop, shapeWidth, shapeHeight);
                gc.setStroke(Color.BLACK);
            }

            // show the bounding box
            if (shape == iModel.selectedShape) {
                if (shape instanceof XLine) {
                    boundingLine();
                }
                else {
                    boundingBox();
                }
            }
        }
    }

    /**
     * Draws the bounding box for the selected shape
     */
    public void boundingBox() {
        gc.setStroke(Color.RED);
        gc.setLineDashes(8);
        gc.setLineWidth(2);
        gc.strokeRect(shapeLeft, shapeTop, shapeWidth, shapeHeight);
        gc.setStroke(Color.BLACK);
        gc.setLineDashes(0);
        gc.setLineWidth(0);

        gc.setFill(Color.YELLOW);
        gc.fillOval(shapeLeft + shapeWidth - 3, shapeTop + shapeHeight - 3, 6, 6);
        gc.strokeOval(shapeLeft + shapeWidth - 3, shapeTop + shapeHeight - 3, 6, 6);

        gc.setFill(Color.BLACK);
    }

    /**
     * draws the bounding box for the selected line
     */
    public void boundingLine() {
        gc.setStroke(Color.RED);
        gc.setLineDashes(8);
        gc.setLineWidth(2);
        gc.strokeLine(shapeLeft, shapeTop, shapeWidth, shapeHeight);
        gc.setStroke(Color.BLACK);
        gc.setLineDashes(0);
        gc.setLineWidth(0);

        gc.setStroke(Color.BLACK);
        gc.setFill(Color.YELLOW);
        gc.fillOval(shapeWidth - 3, shapeHeight - 3, 6, 6);
        gc.strokeOval(shapeWidth - 3, shapeHeight - 3, 6, 6);

        gc.setFill(Color.BLACK);
    }
}
