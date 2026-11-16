package com.realmofchampions.ui;

import com.realmofchampions.model.*;
import com.realmofchampions.model.Character;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class ArenaCanvas extends Canvas {
    private final Character hero;
    private final Character enemy;

    private double heroOffsetX = 0;
    private double enemyOffsetX = 0;
    private boolean heroHitFlash = false;
    private boolean enemyHitFlash = false;
    private boolean showDefeatedHeroDuringAnimation = false;
    private boolean showDefeatedEnemyDuringAnimation = false;

    private double projectileX = -1;
    private Color projectileColor = Color.ORANGE;

    private String floatingText = "";
    private double floatingX = 0;
    private double floatingY = 0;
    private Color floatingColor = Color.WHITE;

    public ArenaCanvas(Character hero, Character enemy) {
        super(640, 210);
        this.hero = hero;
        this.enemy = enemy;
        draw();
    }

    public void playHeroAttack(int damage, boolean isSpecial, Runnable onFinished) {
        showDefeatedEnemyDuringAnimation = !enemy.isAlive();
        Timeline timeline = new Timeline();

        if (isSpecial) {
            projectileColor = (hero instanceof Mage) ? Color.ORANGERED : Color.DEEPSKYBLUE;
            timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(0), e -> { projectileX = 160; draw(); }),
                new KeyFrame(Duration.millis(80), e -> { projectileX = 250; draw(); }),
                new KeyFrame(Duration.millis(160), e -> { projectileX = 340; draw(); }),
                new KeyFrame(Duration.millis(240), e -> {
                    projectileX = -1;
                    enemyHitFlash = true;
                    showFloating("-" + damage + " CRIT!", 470, 65, Color.GOLD);
                    draw();
                }),
                new KeyFrame(Duration.millis(480), e -> {
                    enemyHitFlash = false;
                    showDefeatedEnemyDuringAnimation = false;
                    floatingText = "";
                    draw();
                    if (onFinished != null) onFinished.run();
                })
            );
        } else {
            timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(0), e -> { heroOffsetX = 40; draw(); }),
                new KeyFrame(Duration.millis(100), e -> { heroOffsetX = 180; draw(); }),
                new KeyFrame(Duration.millis(180), e -> {
                    enemyHitFlash = true;
                    showFloating("-" + damage, 480, 65, Color.CRIMSON);
                    draw();
                }),
                new KeyFrame(Duration.millis(300), e -> { heroOffsetX = 0; draw(); }),
                new KeyFrame(Duration.millis(480), e -> {
                    enemyHitFlash = false;
                    showDefeatedEnemyDuringAnimation = false;
                    floatingText = "";
                    draw();
                    if (onFinished != null) onFinished.run();
                })
            );
        }
        timeline.play();
    }

    public void playEnemyAttack(int damage, Runnable onFinished) {
        showDefeatedHeroDuringAnimation = !hero.isAlive();
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(0), e -> { enemyOffsetX = -40; draw(); }),
            new KeyFrame(Duration.millis(100), e -> { enemyOffsetX = -180; draw(); }),
            new KeyFrame(Duration.millis(180), e -> {
                heroHitFlash = true;
                showFloating("-" + damage, 130, 65, Color.CRIMSON);
                draw();
            }),
            new KeyFrame(Duration.millis(300), e -> { enemyOffsetX = 0; draw(); }),
            new KeyFrame(Duration.millis(480), e -> {
                heroHitFlash = false;
                showDefeatedHeroDuringAnimation = false;
                floatingText = "";
                draw();
                if (onFinished != null) onFinished.run();
            })
        );
        timeline.play();
    }

    public void playHealEffect(int amount, Runnable onFinished) {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(0), e -> {
                showFloating("+" + amount + " HP", 125, 65, Color.LIMEGREEN);
                draw();
            }),
            new KeyFrame(Duration.millis(450), e -> {
                floatingText = "";
                draw();
                if (onFinished != null) onFinished.run();
            })
        );
        timeline.play();
    }

    private void showFloating(String text, double x, double y, Color color) {
        this.floatingText = text;
        this.floatingX = x;
        this.floatingY = y;
        this.floatingColor = color;
    }

    public void draw() {
        GraphicsContext gc = getGraphicsContext2D();

        // Sky & Arena Ground
        gc.setFill(Color.web("#1e293b"));
        gc.fillRect(0, 0, getWidth(), 155);
        gc.setFill(Color.web("#334155"));
        gc.fillRect(0, 155, getWidth(), 55);
        gc.setStroke(Color.web("#475569"));
        gc.setLineWidth(2);
        gc.strokeLine(0, 155, getWidth(), 155);

        // Draw Hero (Left) & Enemy (Right)
        if (hero.isAlive() || showDefeatedHeroDuringAnimation) {
            drawHero(gc, 120 + heroOffsetX, 90);
        }
        if (enemy.isAlive() || showDefeatedEnemyDuringAnimation) {
            drawEnemy(gc, 470 + enemyOffsetX, 90);
        }

        // Draw Special Projectile if active
        if (projectileX > 0) {
            gc.setFill(projectileColor);
            gc.fillOval(projectileX, 105, 24, 24);
            gc.setFill(Color.WHITE);
            gc.fillOval(projectileX + 6, 111, 10, 10);
        }

        // Draw Floating Damage / Heal Text
        if (!floatingText.isEmpty()) {
            gc.setFont(Font.font("System", FontWeight.BOLD, 20));
            gc.setFill(floatingColor);
            gc.fillText(floatingText, floatingX, floatingY);
        }
    }

    private void drawHero(GraphicsContext gc, double x, double y) {
        Color bodyColor = heroHitFlash ? Color.RED : Color.DODGERBLUE;
        if (!heroHitFlash && hero instanceof Archer) bodyColor = Color.SEAGREEN;
        if (!heroHitFlash && hero instanceof Mage) bodyColor = Color.MEDIUMPURPLE;

        // Shadow
        gc.setFill(Color.rgb(0, 0, 0, 0.35));
        gc.fillOval(x - 5, y + 60, 50, 12);

        // Body & Head
        gc.setFill(bodyColor);
        gc.fillRoundRect(x, y + 20, 40, 45, 10, 10);
        gc.setFill(heroHitFlash ? Color.RED : Color.PEACHPUFF);
        gc.fillOval(x + 7, y - 5, 26, 26);

        // Class Weapon
        if (hero instanceof Warrior) {
            gc.setFill(Color.SILVER);
            gc.fillRect(x + 42, y + 5, 6, 42); // Sword
            gc.setFill(Color.GOLD);
            gc.fillRect(x + 36, y + 32, 18, 5); // Hilt
        } else if (hero instanceof Archer) {
            gc.setStroke(Color.SANDYBROWN);
            gc.setLineWidth(3);
            gc.strokeArc(x + 32, y + 5, 20, 45, -90, 180, javafx.scene.shape.ArcType.OPEN);
        } else if (hero instanceof Mage) {
            gc.setFill(Color.SADDLEBROWN);
            gc.fillRect(x + 42, y, 5, 55); // Staff
            gc.setFill(Color.CYAN);
            gc.fillOval(x + 38, y - 6, 13, 13); // Orb
        }
    }

    private void drawEnemy(GraphicsContext gc, double x, double y) {
        boolean isOrc = enemy instanceof Orc;
        double sizeOffset = isOrc ? 12 : 0;
        Color bodyColor = enemyHitFlash ? Color.RED : (isOrc ? Color.DARKOLIVEGREEN : Color.LIMEGREEN);

        // Shadow
        gc.setFill(Color.rgb(0, 0, 0, 0.35));
        gc.fillOval(x - 5, y + 60, 50 + sizeOffset, 12);

        // Body & Head
        gc.setFill(bodyColor);
        gc.fillRoundRect(x, y + 20 - sizeOffset, 40 + sizeOffset, 45 + sizeOffset, 10, 10);
        gc.fillOval(x + 7, y - 5 - sizeOffset, 26 + sizeOffset / 2, 26 + sizeOffset / 2);

        // Angry Eyes
        gc.setFill(Color.YELLOW);
        gc.fillOval(x + 10, y + 4 - sizeOffset, 5, 5);
        gc.fillOval(x + 20, y + 4 - sizeOffset, 5, 5);

        // Weapon
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(x - 12, y + 10, 6, 38);
        if (isOrc) {
            gc.fillRect(x - 20, y + 5, 22, 14); // Warhammer head
        }
    }
}
