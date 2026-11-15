package com.realmofchampions.ui;

import com.realmofchampions.controller.BattleController;
import com.realmofchampions.model.Character;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApp extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Realm of Champions");
        showSelectionScreen();
        this.primaryStage.show();
    }

    private void showSelectionScreen() {
        SelectionView selectionView = new SelectionView(this::startBattle);
        Scene scene = new Scene(selectionView, 720, 560);
        primaryStage.setScene(scene);
    }

    private void startBattle(Character hero, Character enemy) {
        BattleController controller = new BattleController(hero, enemy);
        BattleView battleView = new BattleView(controller, this::showSelectionScreen);
        Scene scene = new Scene(battleView, 720, 560);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
