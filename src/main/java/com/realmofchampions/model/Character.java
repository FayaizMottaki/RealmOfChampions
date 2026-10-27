package com.realmofchampions.model;

import com.realmofchampions.interfaces.Attackable;

public abstract class Character implements Attackable {
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int baseAttackPower;

    public Character(String name, int maxHealth, int baseAttackPower) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.baseAttackPower = baseAttackPower;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getBaseAttackPower() { return baseAttackPower; }

    @Override
    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
    }

    public void heal(int amount) {
        if (amount > 0) {
            this.health = Math.min(this.maxHealth, this.health + amount);
        }
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }
}
