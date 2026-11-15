package com.realmofchampions.ui;

import com.realmofchampions.model.*;
import com.realmofchampions.model.Character;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.function.BiConsumer;

public class SelectionView extends VBox {
    private final ComboBox<String> heroBox;
    private final ComboBox<String> enemyBox;

    public SelectionView(BiConsumer<Character, Character> onStartBattle) {
        setSpacing(15);
        setPadding(new Insets(30));
        setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Realm of Champions");
        titleLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        Label heroLabel = new Label("Choose Your Hero:");
        heroBox = new ComboBox<>();
        heroBox.getItems().addAll(
            "Warrior - Arthur (120 HP, 15 ATK)",
            "Archer - Robin (100 HP, 20 ATK)",
            "Mage - Merlin (70 HP, 30 ATK)"
        );
        heroBox.getSelectionModel().selectFirst();

        Label enemyLabel = new Label("Choose Your Opponent:");
        enemyBox = new ComboBox<>();
        enemyBox.getItems().addAll(
            "Sneaky Goblin (50 HP, 10 ATK)",
            "Brutal Orc (150 HP, 25 ATK)"
        );
        enemyBox.getSelectionModel().selectFirst();

        Button startButton = new Button("Start Battle");
        startButton.setStyle("-fx-font-size: 15px; -fx-padding: 8 20;");
        startButton.setOnAction(e -> {
            Character hero = createSelectedHero();
            hero.addItem(new HealthPotion());
            hero.addItem(new HealthPotion());

            Character enemy = createSelectedEnemy();
            onStartBattle.accept(hero, enemy);
        });

        getChildren().addAll(titleLabel, heroLabel, heroBox, enemyLabel, enemyBox, startButton);
    }

    private Character createSelectedHero() {
        int index = heroBox.getSelectionModel().getSelectedIndex();
        return switch (index) {
            case 1 -> new Archer("Robin");
            case 2 -> new Mage("Merlin");
            default -> new Warrior("Arthur");
        };
    }

    private Character createSelectedEnemy() {
        int index = enemyBox.getSelectionModel().getSelectedIndex();
        return (index == 1) ? new Orc("Grom the Orc") : new Goblin("Sneaky Goblin");
    }
}
