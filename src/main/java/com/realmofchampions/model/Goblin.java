package com.realmofchampions.model;

public class Goblin extends Character {
    
    public Goblin(String name) {
        super(name, 50, 10); // 50 Max HP, 10 Base Attack
    }

    @Override
    public int attack() {
        System.out.println(this.name + " stabs with a rusty dagger!");
        return this.baseAttackPower;
    }
}
