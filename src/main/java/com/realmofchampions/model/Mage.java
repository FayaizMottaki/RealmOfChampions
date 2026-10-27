package com.realmofchampions.model;

import com.realmofchampions.interfaces.Attackable;
import com.realmofchampions.interfaces.SpecialAbility;

public class Mage extends Character implements SpecialAbility {
    private int cooldown = 0;

    public Mage(String name) {
        super(name, 70, 30);
    }

    @Override
    public int attack() {
        if (cooldown > 0) cooldown--;
        System.out.println(this.name + " casts a magic missile!");
        return this.baseAttackPower;
    }

    @Override
    public void useSpecialAbility(Attackable target) {
        if (!canUseSpecialAbility()) return;
        System.out.println(this.name + " casts FIREBALL!");
        target.takeDamage(this.baseAttackPower * 2);
        cooldown = 2;
    }

    @Override
    public boolean canUseSpecialAbility() {
        return cooldown == 0;
    }
}
