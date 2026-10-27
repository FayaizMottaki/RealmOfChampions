package com.realmofchampions.controller;

import com.realmofchampions.interfaces.SpecialAbility;
import com.realmofchampions.model.BattleState;
import com.realmofchampions.model.Character;
import com.realmofchampions.model.Item;
import com.realmofchampions.util.BattleLog;

public class BattleController {
    private final Character hero;
    private final Character enemy;
    private BattleState currentState;
    private final BattleLog battleLog;

    public BattleController(Character hero, Character enemy) {
        this.hero = hero;
        this.enemy = enemy;
        this.currentState = BattleState.HERO_TURN;
        this.battleLog = new BattleLog();
        this.battleLog.log("Battle started: " + hero.getName() + " vs " + enemy.getName() + "!");
    }

    public BattleState getCurrentState() { return currentState; }
    public Character getHero() { return hero; }
    public Character getEnemy() { return enemy; }
    public BattleLog getBattleLog() { return battleLog; }

    public void executeHeroAttack() {
        if (currentState != BattleState.HERO_TURN) return;

        int damage = hero.attack();
        enemy.takeDamage(damage);
        battleLog.log(hero.getName() + " attacked " + enemy.getName() + " for " + damage + " damage.");

        checkBattleStatus();
        if (currentState == BattleState.HERO_TURN) {
            currentState = BattleState.ENEMY_TURN;
        }
    }

    public void executeHeroSpecial() {
        if (currentState != BattleState.HERO_TURN) return;

        if (hero instanceof SpecialAbility specialHero) {
            if (specialHero.canUseSpecialAbility()) {
                int enemyHpBefore = enemy.getHealth();
                specialHero.useSpecialAbility(enemy);
                int damageDealt = enemyHpBefore - enemy.getHealth();
                battleLog.log(hero.getName() + " used a Special Ability on " + enemy.getName() + " for " + damageDealt + " damage!");

                checkBattleStatus();
                if (currentState == BattleState.HERO_TURN) {
                    currentState = BattleState.ENEMY_TURN;
                }
            } else {
                battleLog.log(hero.getName() + " cannot use Special Ability right now (on cooldown)!");
            }
        }
    }

    public void useItem(Item item) {
        if (currentState != BattleState.HERO_TURN || item == null) return;

        item.use(hero);
        battleLog.log(hero.getName() + " used " + item.getName() + ".");

        checkBattleStatus();
        if (currentState == BattleState.HERO_TURN) {
            currentState = BattleState.ENEMY_TURN;
        }
    }

    public void executeEnemyTurn() {
        if (currentState != BattleState.ENEMY_TURN) return;

        if (enemy instanceof SpecialAbility specialEnemy && specialEnemy.canUseSpecialAbility()) {
            int heroHpBefore = hero.getHealth();
            specialEnemy.useSpecialAbility(hero);
            int damageDealt = heroHpBefore - hero.getHealth();
            battleLog.log(enemy.getName() + " used a Special Ability on " + hero.getName() + " for " + damageDealt + " damage!");
        } else {
            int damage = enemy.attack();
            hero.takeDamage(damage);
            battleLog.log(enemy.getName() + " attacked " + hero.getName() + " for " + damage + " damage.");
        }

        checkBattleStatus();
        if (currentState == BattleState.ENEMY_TURN) {
            currentState = BattleState.HERO_TURN;
        }
    }

    private void checkBattleStatus() {
        if (!enemy.isAlive()) {
            currentState = BattleState.VICTORY;
            battleLog.log(enemy.getName() + " was defeated! " + hero.getName() + " is victorious!");
        } else if (!hero.isAlive()) {
            currentState = BattleState.DEFEAT;
            battleLog.log(hero.getName() + " has fallen in battle...");
        }
    }
}
