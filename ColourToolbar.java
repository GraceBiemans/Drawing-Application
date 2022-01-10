// Grace Biemans
// geb965 11279874

package com.example.assignment3;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ColourToolbar extends VBox {

    Button aqua, violet, green, gold, orange, coral, fuchsia, peru;


    public ColourToolbar() {
        aqua = new Button("Aqua");
        aqua.setPrefWidth(70);
        aqua.setPrefHeight(60);
        aqua.setStyle("-fx-background-color: #7FFFD4; ");

        violet = new Button("Violet");
        violet.setPrefWidth(70);
        violet.setPrefHeight(60);
        violet.setStyle("-fx-background-color: #8A2BE2; ");

        green = new Button("Green");
        green.setPrefWidth(70);
        green.setPrefHeight(60);
        green.setStyle("-fx-background-color: #556B2F; ");

        gold = new Button("Gold");
        gold.setPrefWidth(70);
        gold.setPrefHeight(60);
        gold.setStyle("-fx-background-color: #FFD700; ");

        orange = new Button("Orange");
        orange.setPrefWidth(70);
        orange.setPrefHeight(60);
        orange.setStyle("-fx-background-color: #FFA500; ");

        coral = new Button("Coral");
        coral.setPrefWidth(70);
        coral.setPrefHeight(60);
        coral.setStyle("-fx-background-color: #FF7F50; ");

        fuchsia = new Button("Fuchsia");
        fuchsia.setPrefWidth(70);
        fuchsia.setPrefHeight(60);
        fuchsia.setStyle("-fx-background-color: #FF00FF; ");

        peru = new Button("Peru");
        peru.setPrefWidth(70);
        peru.setPrefHeight(60);
        peru.setStyle("-fx-background-color: #CD853F; ");

        this.getChildren().addAll(aqua, violet, green, gold, orange, coral, fuchsia, peru);
        this.setPadding(new Insets(0, 5, 0, 5));
        this.setSpacing(3);
    }



    public Button getAqua() {
        return aqua;
    }

    public Button getViolet() {
        return violet;
    }

    public Button getGreen() {
        return green;
    }

    public Button getGold() {
        return gold;
    }

    public Button getOrange() {
        return orange;
    }

    public Button getCoral() {
        return coral;
    }

    public Button getFuchsia() {
        return fuchsia;
    }

    public Button getPeru() {
        return peru;
    }
}
