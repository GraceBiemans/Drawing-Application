// Grace Biemans
// geb965 11279874

package com.example.assignment3;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MiniDrawingView extends DrawingView implements DrawingModelSubscriber{
    GraphicsContext gc;
    Canvas canvas;
    double width, height;
    DrawingModel model;
    private InteractionModel iModel;
    double shapeLeft, shapeTop, shapeWidth, shapeHeight;

    public MiniDrawingView(Canvas myCanvas, double w, double h) {
        super();
        width = w;
        height = h;
        canvas = myCanvas;
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
    }

    public void setModel(DrawingModel newModel) {
        model = newModel;
    }

    public void setInteractionModel(InteractionModel newiModel) {
        iModel = newiModel;
        drawViewBox();
    }

    public void setController(DrawingController controller) {
        canvas.setOnMousePressed(e -> controller.handlePressed(e.getX()/width, e.getY()/height, e));
        canvas.setOnMouseDragged(e -> controller.handleDragged(e.getX()/width, e.getY()/height, e, width, height));
        canvas.setOnMouseReleased(e -> controller.handleReleased(e.getX()/width, e.getY()/height, e));
    }

    /**
     * Draw the viewfinder
     */
    public void drawViewBox() {
        gc.setStroke(Color.YELLOW);
        gc.strokeRect(iModel.viewLeft, iModel.viewTop, 25, 25);
        gc.setStroke(Color.BLACK);
    }

    public void draw() {
        gc.clearRect(0, 0, width, height);
        gc.setStroke(Color.BLACK);

        for (XShape shape : model.shapes) {
            if (shape instanceof XSquare) {
                shapeLeft = (((XSquare) shape).left * 100) / 4;
                shapeTop = (((XSquare) shape).top * 100) / 4;
                shapeWidth = ((XSquare) shape).width * 100;
                shapeHeight = ((XSquare) shape).height * 100;
                gc.setFill(shape.color);
                gc.fillRect(shapeLeft, shapeTop, shapeWidth, shapeHeight);
                gc.strokeRect(shapeLeft, shapeTop, shapeWidth, shapeHeight);
            }
            else if (shape instanceof XRectangle) {
                shapeLeft = (((XRectangle) shape).left * 100) / 4;
                shapeTop = (((XRectangle) shape).top * 100) / 4;
                shapeWidth = ((XRectangle) shape).width * 100;
                shapeHeight = ((XRectangle) shape).height * 100;
                gc.setFill(shape.color);
                gc.fillRect(shapeLeft, shapeTop, shapeWidth, shapeHeight);
                gc.strokeRect(shapeLeft, shapeTop, shapeWidth, shapeHeight);
            }
            else if (shape instanceof XCircle) {
                shapeLeft = (((XCircle) shape).leftBoxX * 100) / 4;
                shapeTop = (((XCircle) shape).topBoxY * 100) / 4;
                shapeWidth = ((XCircle) shape).radius * 100 / 2;
                shapeHeight = ((XCircle) shape).radius * 100 / 2;
                gc.setFill(shape.color);
                gc.fillOval(shapeLeft, shapeTop, shapeWidth, shapeHeight);
                gc.strokeOval(shapeLeft, shapeTop, shapeWidth, shapeHeight);
            }
            else if (shape instanceof XOval) {
                shapeLeft = (((XOval) shape).leftBoxX * 100) / 4;
                shapeTop = (((XOval) shape).topBoxY * 100) / 4;
                shapeWidth = ((XOval) shape).radX * 100 / 2;
                shapeHeight = ((XOval) shape).radY * 100 / 2;
                gc.setFill(shape.color);
                gc.fillOval(shapeLeft, shapeTop, shapeWidth, shapeHeight);
                gc.strokeOval(shapeLeft, shapeTop, shapeWidth, shapeHeight);
            }
            else {
                shapeLeft = (((XLine) shape).x1 * 100) / 4;
                shapeTop = (((XLine) shape).y1 * 100) / 4;
                shapeWidth = (((XLine) shape).x2 * 100) / 4;
                shapeHeight = (((XLine) shape).y2 * 100) / 4;
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
        drawViewBox();
    }

    /**
     * Draw the bounding box for the selected shape
     */
    public void boundingBox() {
        gc.setStroke(Color.RED);
        gc.strokeRect(shapeLeft, shapeTop, shapeWidth, shapeHeight);

        gc.setStroke(Color.BLACK);
        gc.setLineDashes(0);
    }

    /**
     * Draw the bounding box for the selected line
     */
    public void boundingLine() {
        gc.setStroke(Color.RED);
        gc.strokeLine(shapeLeft, shapeTop, shapeWidth, shapeHeight);

        gc.setStroke(Color.BLACK);
        gc.setLineDashes(0);
    }

    public void modelChanged() {
        draw();
    }
}
