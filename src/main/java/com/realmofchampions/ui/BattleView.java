package com.realmofchampions.ui;

import com.realmofchampions.controller.BattleController;
import com.realmofchampions.interfaces.SpecialAbility;
import com.realmofchampions.model.BattleState;
import com.realmofchampions.model.Character;
import com.realmofchampions.model.Item;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BattleView extends VBox {
    private final BattleController controller;
    private final Runnable onPlayAgain;
    private final ArenaCanvas arenaCanvas;

    private final Label heroHpLabel;
    private final ProgressBar heroHpBar;
    private final Label enemyHpLabel;
    private final ProgressBar enemyHpBar;
    private final Label statusLabel;

    private final Button attackBtn;
    private final Button specialBtn;
    private final Button potionBtn;
    private final Button restartBtn;
    private final TextArea logArea;

    private boolean isAnimating = false;

    public BattleView(BattleController controller, Runnable onPlayAgain) {
        this.controller = controller;
        this.onPlayAgain = onPlayAgain;

        setSpacing(12);
        setPadding(new Insets(16));
        setAlignment(Pos.CENTER);

        Character hero = controller.getHero();
        Character enemy = controller.getEnemy();

        Label heroNameLabel = new Label("Hero: " + hero.getName());
        heroNameLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        heroHpLabel = new Label();
        heroHpBar = new ProgressBar(1.0);
        heroHpBar.setPrefWidth(220);
        VBox heroBox = new VBox(4, heroNameLabel, heroHpLabel, heroHpBar);
        heroBox.setAlignment(Pos.CENTER);

        Label enemyNameLabel = new Label("Enemy: " + enemy.getName());
        enemyNameLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        enemyHpLabel = new Label();
        enemyHpBar = new ProgressBar(1.0);
        enemyHpBar.setPrefWidth(220);
        VBox enemyBox = new VBox(4, enemyNameLabel, enemyHpLabel, enemyHpBar);
        enemyBox.setAlignment(Pos.CENTER);

        HBox fightersBox = new HBox(40, heroBox, enemyBox);
        fightersBox.setAlignment(Pos.CENTER);

        arenaCanvas = new ArenaCanvas(hero, enemy);

        statusLabel = new Label("Your Turn!");
        statusLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        attackBtn = new Button("Basic Attack");
        specialBtn = new Button("Special Ability");
        potionBtn = new Button("Use Health Potion");
        restartBtn = new Button("Play Again");
        restartBtn.setVisible(false);

        attackBtn.setOnAction(e -> performAttack(false));
        specialBtn.setOnAction(e -> performAttack(true));
        potionBtn.setOnAction(e -> performHeal());
        restartBtn.setOnAction(e -> this.onPlayAgain.run());

        HBox actionBox = new HBox(10, attackBtn, specialBtn, potionBtn, restartBtn);
        actionBox.setAlignment(Pos.CENTER);

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setWrapText(true);
        logArea.setPrefHeight(120);

        getChildren().addAll(fightersBox, arenaCanvas, statusLabel, actionBox, logArea);
        refreshUI();
    }

    private void performAttack(boolean isSpecial) {
        if (isAnimating || controller.getCurrentState() != BattleState.HERO_TURN) return;
        isAnimating = true;
        disableCombatButtons(true);

        int enemyHpBefore = controller.getEnemy().getHealth();
        if (isSpecial) {
            controller.executeHeroSpecial();
        } else {
            controller.executeHeroAttack();
        }
        int damageDealt = enemyHpBefore - controller.getEnemy().getHealth();

        arenaCanvas.playHeroAttack(damageDealt, isSpecial, this::triggerEnemyTurn);
    }

    private void performHeal() {
        if (isAnimating || controller.getCurrentState() != BattleState.HERO_TURN) return;
        Character hero = controller.getHero();
        if (!hero.hasItems()) return;

        isAnimating = true;
        disableCombatButtons(true);

        int heroHpBefore = hero.getHealth();
        Item potion = hero.getInventory().get(0);
        controller.useItem(potion);
        int healed = hero.getHealth() - heroHpBefore;

        arenaCanvas.playHealEffect(healed, this::triggerEnemyTurn);
    }

    private void triggerEnemyTurn() {
        refreshUI();
        if (controller.getCurrentState() == BattleState.ENEMY_TURN) {
            int heroHpBefore = controller.getHero().getHealth();
            controller.executeEnemyTurn();
            int damageTaken = heroHpBefore - controller.getHero().getHealth();

            arenaCanvas.playEnemyAttack(damageTaken, () -> {
                isAnimating = false;
                refreshUI();
            });
        } else {
            isAnimating = false;
            refreshUI();
        }
    }

    private void refreshUI() {
        Character hero = controller.getHero();
        Character enemy = controller.getEnemy();

        heroHpLabel.setText("HP: " + hero.getHealth() + " / " + hero.getMaxHealth());
        heroHpBar.setProgress((double) hero.getHealth() / hero.getMaxHealth());

        enemyHpLabel.setText("HP: " + enemy.getHealth() + " / " + enemy.getMaxHealth());
        enemyHpBar.setProgress((double) enemy.getHealth() / enemy.getMaxHealth());

        arenaCanvas.draw();

        int potionCount = hero.getInventory().size();
        potionBtn.setText("Use Potion (" + potionCount + ")");

        logArea.clear();
        for (String entry : controller.getBattleLog().getLogs()) {
            logArea.appendText(entry + "\n");
        }
        logArea.setScrollTop(Double.MAX_VALUE);

        BattleState state = controller.getCurrentState();
        if (state == BattleState.VICTORY) {
            statusLabel.setText("VICTORY! You defeated " + enemy.getName() + "!");
            disableCombatButtons(true);
            restartBtn.setVisible(true);
        } else if (state == BattleState.DEFEAT) {
            statusLabel.setText("DEFEAT! You were slain by " + enemy.getName() + "...");
            disableCombatButtons(true);
            restartBtn.setVisible(true);
        } else if (!isAnimating) {
            statusLabel.setText("Your Turn! Choose an action.");
            attackBtn.setDisable(false);
            potionBtn.setDisable(!hero.hasItems());

            if (hero instanceof SpecialAbility specialHero) {
                specialBtn.setDisable(!specialHero.canUseSpecialAbility());
            } else {
                specialBtn.setDisable(true);
            }
        }
    }

    private void disableCombatButtons(boolean disabled) {
        attackBtn.setDisable(disabled);
        specialBtn.setDisable(disabled);
        potionBtn.setDisable(disabled);
    }
}
