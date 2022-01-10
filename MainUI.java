// Grace Biemans
// geb965 11279874

package com.example.assignment3;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MainUI extends HBox implements DrawingModelSubscriber{
    DrawingModel model;
    InteractionModel imodel;
    DrawingView drawingView;
    ShapeToolbar shapeToolbar;
    ColourToolbar colourToolbar;
    Canvas myCanvas;
    MiniDrawingController miniController;

    MiniDrawingView mini;
    Canvas miniCanvas;

    double width, height;

    public MainUI(double w, double h) {
        // the width and height of the two toolbars and the drawing view
        width = w;
        height = h;

        // create the 3 components of the main user interface
        shapeToolbar = new ShapeToolbar();
        shapeToolbar.setMinHeight(500);
        colourToolbar = new ColourToolbar();
        colourToolbar.setMinHeight(500);
        myCanvas = new Canvas(500, 500);
        drawingView = new DrawingView(myCanvas, w, h);

        // create the miniview
        miniCanvas = new Canvas(100, 100);
        mini = new MiniDrawingView(miniCanvas, 100, 100);
        mini.setStyle("-fx-background-color: #778899 ; ");
        Group miniGroup = new Group(mini);
        miniController = new MiniDrawingController();
        mini.setController(miniController);

        StackPane stack = new StackPane(drawingView, miniGroup);
        StackPane.setAlignment(miniGroup, Pos.TOP_LEFT);

        this.getChildren().addAll(shapeToolbar, stack, colourToolbar);
    }

    public void setModel(DrawingModel newModel) {
        model = newModel;
        drawingView.setModel(model);
        mini.setModel(model);
        model.addSubscriber(mini);
        miniController.setModel(model);
    }

    public void setController(DrawingController newController) {
        this.setOnKeyReleased(newController::handleKeyReleased);

        myCanvas.setOnMousePressed(e -> newController.handlePressed(e.getX()/width, e.getY()/height, e));
        myCanvas.setOnMouseDragged(e -> newController.handleDragged(e.getX()/width, e.getY()/height, e, width, height));
        myCanvas.setOnMouseReleased(e -> newController.handleReleased(e.getX()/width, e.getY()/height, e));

        shapeToolbar.getRectB().setOnAction(e -> newController.handleShapeChanged(shapeToolbar.getRectB()));
        shapeToolbar.getSquareB().setOnAction(e -> newController.handleShapeChanged(shapeToolbar.getSquareB()));
        shapeToolbar.getOvalB().setOnAction(e -> newController.handleShapeChanged(shapeToolbar.getOvalB()));
        shapeToolbar.getCircleB().setOnAction(e -> newController.handleShapeChanged(shapeToolbar.getCircleB()));
        shapeToolbar.getLineB().setOnAction(e -> newController.handleShapeChanged(shapeToolbar.getLineB()));

        colourToolbar.getAqua().setOnAction(e -> newController.handleColorChanged(colourToolbar.getAqua()));
        colourToolbar.getViolet().setOnAction(e -> newController.handleColorChanged(colourToolbar.getViolet()));
        colourToolbar.getGreen().setOnAction(e -> newController.handleColorChanged(colourToolbar.getGreen()));
        colourToolbar.getGold().setOnAction(e -> newController.handleColorChanged(colourToolbar.getGold()));
        colourToolbar.getOrange().setOnAction(e -> newController.handleColorChanged(colourToolbar.getOrange()));
        colourToolbar.getCoral().setOnAction(e -> newController.handleColorChanged(colourToolbar.getCoral()));
        colourToolbar.getFuchsia().setOnAction(e -> newController.handleColorChanged(colourToolbar.getFuchsia()));
        colourToolbar.getPeru().setOnAction(e -> newController.handleColorChanged(colourToolbar.getPeru()));
    }

    public void setInteractionModel(InteractionModel newimodel) {
        imodel = newimodel;
        drawingView.setInteractionModel(imodel);

        // set the default color and shape on startup
        imodel.setSelectedShapeTool(shapeToolbar.getRectB());
        imodel.setSelectedColorButton(colourToolbar.getAqua());
        colourToolbar.getAqua().setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(1.0), BorderStroke.THIN)));

        mini.setInteractionModel(imodel);
        imodel.addSubscriber(mini);
        miniController.setInteractionModel(imodel);
    }

    public void modelChanged() {
        drawingView.draw();
    }
}
