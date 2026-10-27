package com.realmofchampions.model;

import com.realmofchampions.interfaces.Attackable;
import com.realmofchampions.interfaces.SpecialAbility;

public class Warrior extends Character implements SpecialAbility {
    private int cooldown = 0;

    public Warrior(String name) {
        super(name, 120, 15);
    }

    @Override
    public int attack() {
        if (cooldown > 0) cooldown--;
        System.out.println(this.name + " swings their sword!");
        return this.baseAttackPower;
    }

    @Override
    public void useSpecialAbility(Attackable target) {
        if (!canUseSpecialAbility()) return;
        System.out.println(this.name + " uses HEAVY STRIKE!");
        target.takeDamage(this.baseAttackPower * 2);
        cooldown = 2;
    }

    @Override
    public boolean canUseSpecialAbility() {
        return cooldown == 0;
    }
}
