package com.realmofchampions.controller;

import com.realmofchampions.interfaces.SpecialAbility;
import com.realmofchampions.model.BattleState;
import com.realmofchampions.model.Character;

public class BattleController {
    private final Character hero;
    private final Character enemy;
    private BattleState currentState;

    public BattleController(Character hero, Character enemy) {
        this.hero = hero;
        this.enemy = enemy;
        this.currentState = BattleState.HERO_TURN;
    }

    public BattleState getCurrentState() { return currentState; }
    public Character getHero() { return hero; }
    public Character getEnemy() { return enemy; }

    public void executeHeroAttack() {
        if (currentState != BattleState.HERO_TURN) return;

        int damage = hero.attack();
        enemy.takeDamage(damage);

        checkBattleStatus();
        if (currentState == BattleState.HERO_TURN) {
            currentState = BattleState.ENEMY_TURN;
        }
    }

    public void executeHeroSpecial() {
        if (currentState != BattleState.HERO_TURN) return;

        if (hero instanceof SpecialAbility specialHero) {
            if (specialHero.canUseSpecialAbility()) {
                specialHero.useSpecialAbility(enemy);
                checkBattleStatus();
                if (currentState == BattleState.HERO_TURN) {
                    currentState = BattleState.ENEMY_TURN;
                }
            }
        }
    }

    public void executeEnemyTurn() {
        if (currentState != BattleState.ENEMY_TURN) return;

        int damage = enemy.attack();
        hero.takeDamage(damage);

        checkBattleStatus();
        if (currentState == BattleState.ENEMY_TURN) {
            currentState = BattleState.HERO_TURN;
        }
    }

    private void checkBattleStatus() {
        if (!enemy.isAlive()) {
            currentState = BattleState.VICTORY;
        } else if (!hero.isAlive()) {
            currentState = BattleState.DEFEAT;
        }
    }
}
