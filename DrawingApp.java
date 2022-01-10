// Grace Biemans
// geb965 11279874
package com.example.assignment3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DrawingApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        HBox root = new HBox();

        MainUI view = new MainUI(2000, 2000);

        DrawingModel model = new DrawingModel();
        InteractionModel imodel = new InteractionModel();

        DrawingController controller = new DrawingController();

        view.setModel(model);
        view.setController(controller);
        view.setInteractionModel(imodel);

        controller.setInteractionModel(imodel);
        controller.setModel(model);

        model.addSubscriber(view);
        imodel.addSubscriber(view);

        root.getChildren().add(view);

        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}