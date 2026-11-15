package com.realmofchampions.model;

import com.realmofchampions.interfaces.Attackable;
import java.util.ArrayList;
import java.util.List;

public abstract class Character implements Attackable {
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int baseAttackPower;
    protected List<Item> inventory;

    public Character(String name, int maxHealth, int baseAttackPower) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.baseAttackPower = baseAttackPower;
        this.inventory = new ArrayList<>();
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getBaseAttackPower() { return baseAttackPower; }

    public List<Item> getInventory() {
        return new ArrayList<>(inventory);
    }

    public void addItem(Item item) {
        if (item != null) {
            this.inventory.add(item);
        }
    }

    public boolean removeItem(Item item) {
        return this.inventory.remove(item);
    }

    public boolean hasItems() {
        return !this.inventory.isEmpty();
    }

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
