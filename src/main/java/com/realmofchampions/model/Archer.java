package com.realmofchampions.model;

import com.realmofchampions.interfaces.Attackable;
import com.realmofchampions.interfaces.SpecialAbility;

public class Archer extends Character implements SpecialAbility {
    private int cooldown = 0;

    public Archer(String name) {
        super(name, 100, 20);
    }

    @Override
    public int attack() {
        if (cooldown > 0) cooldown--;
        System.out.println(this.name + " shoots a precise arrow!");
        return this.baseAttackPower;
    }

    @Override
    public void useSpecialAbility(Attackable target) {
        if (!canUseSpecialAbility()) return;
        System.out.println(this.name + " uses PIERCING SHOT!");
        target.takeDamage(this.baseAttackPower + 15);
        cooldown = 2;
    }

    @Override
    public boolean canUseSpecialAbility() {
        return cooldown == 0;
    }
}
