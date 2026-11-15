package com.realmofchampions.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("Realm of Champions");
        titleLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        Label subtitleLabel = new Label("Turn-Based OOP RPG Battle Arena");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555555;");

        VBox root = new VBox(15, titleLabel, subtitleLabel);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 700, 500);
        primaryStage.setTitle("Realm of Champions");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
