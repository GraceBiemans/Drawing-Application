// Grace Biemans
// geb965 11279874

package com.example.assignment3;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


public class ShapeToolbar extends VBox {

    Button rectB, squareB, circleB, ovalB, lineB;


    public ShapeToolbar() {
        // create each of the shape toolbar buttons
        rectB = new Button();
        rectB.setPrefWidth(70);
        rectB.setPrefHeight(100);
        VBox rectbox = new VBox(new Rectangle(30, 20, Color.BLACK), new Label("Rect"));
        rectbox.setSpacing(7);
        rectbox.setAlignment(Pos.CENTER);
        rectB.setGraphic(rectbox);

        squareB = new Button();
        squareB.setPrefWidth(70);
        squareB.setPrefHeight(100);
        VBox sqrbox = new VBox(new Rectangle(30, 30, Color.BLACK), new Label("Square"));
        sqrbox.setSpacing(7);
        sqrbox.setAlignment(Pos.CENTER);
        squareB.setGraphic(sqrbox);

        circleB = new Button();
        circleB.setPrefWidth(70);
        circleB.setPrefHeight(100);
        VBox circlebox = new VBox(new Circle(15, Color.BLACK), new Label("Circle"));
        circlebox.setSpacing(7);
        circlebox.setAlignment(Pos.CENTER);
        circleB.setGraphic(circlebox);

        ovalB = new Button();
        ovalB.setPrefWidth(70);
        ovalB.setPrefHeight(100);
        Ellipse oval = new Ellipse(15, 10);
        oval.setFill(Color.BLACK);
        VBox ovalbox = new VBox(oval, new Label("Oval"));
        ovalbox.setSpacing(7);
        ovalbox.setAlignment(Pos.CENTER);
        ovalB.setGraphic(ovalbox);

        lineB = new Button();
        lineB.setPrefWidth(70);
        lineB.setPrefHeight(100);
        Line line = new Line(0, 0, 15, 15);
        line.setStrokeWidth(2);
        VBox linebox = new VBox(line, new Label("Line"));
        linebox.setSpacing(7);
        linebox.setAlignment(Pos.CENTER);
        lineB.setGraphic(linebox);

        this.getChildren().addAll(rectB, squareB, circleB, ovalB, lineB);
    }

    public Button getRectB() {
        return rectB;
    }

    public Button getSquareB() {
        return squareB;
    }

    public Button getCircleB() {
        return circleB;
    }

    public Button getOvalB() {
        return ovalB;
    }

    public Button getLineB() {
        return lineB;
    }
}
