package com.realmofchampions;

import com.realmofchampions.controller.BattleController;
import com.realmofchampions.model.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== WELCOME TO REALM OF CHAMPIONS ===");
        System.out.println("Select your hero class:");
        System.out.println("1. Warrior (High Health)");
        System.out.println("2. Archer (Balanced)");
        System.out.println("3. Mage (High Damage)");
        System.out.print("Choice: ");

        int choice = scanner.hasNextInt() ? scanner.nextInt() : 1;

        Character hero = switch (choice) {
            case 1 -> new Warrior("Arthur");
            case 2 -> new Archer("Robin");
            case 3 -> new Mage("Merlin");
            default -> new Warrior("Arthur");
        };

        Character enemy = new Goblin("Sneaky Goblin");
        BattleController controller = new BattleController(hero, enemy);

        System.out.println("\nA wild " + enemy.getName() + " appears!\n");

        while (controller.getCurrentState() == BattleState.HERO_TURN || controller.getCurrentState() == BattleState.ENEMY_TURN) {
            if (controller.getCurrentState() == BattleState.HERO_TURN) {
                System.out.println("--- YOUR TURN ---");
                System.out.println(hero.getName() + " HP: " + hero.getHealth() + "/" + hero.getMaxHealth());
                System.out.println(enemy.getName() + " HP: " + enemy.getHealth() + "/" + enemy.getMaxHealth());
                System.out.println("1. Basic Attack");
                System.out.println("2. Special Ability");
                System.out.print("Action: ");
                int action = scanner.hasNextInt() ? scanner.nextInt() : 1;

                if (action == 2) {
                    controller.executeHeroSpecial();
                } else {
                    controller.executeHeroAttack();
                }
            } else if (controller.getCurrentState() == BattleState.ENEMY_TURN) {
                System.out.println("\n--- ENEMY TURN ---");
                controller.executeEnemyTurn();
            }
        }

        if (controller.getCurrentState() == BattleState.VICTORY) {
            System.out.println("\n*** VICTORY! You defeated " + enemy.getName() + "! ***");
        } else {
            System.out.println("\n*** DEFEAT! You were slain... ***");
        }

        scanner.close();
    }
}
