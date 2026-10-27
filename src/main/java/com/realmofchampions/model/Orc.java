package com.realmofchampions.model;

import com.realmofchampions.interfaces.Attackable;
import com.realmofchampions.interfaces.SpecialAbility;

public class Orc extends Character implements SpecialAbility {
    private int cooldown = 0;

    public Orc(String name) {
        super(name, 150, 25);
    }

    @Override
    public int attack() {
        if (cooldown > 0) cooldown--;
        System.out.println(this.name + " swings a massive warhammer!");
        return this.baseAttackPower;
    }

    @Override
    public void useSpecialAbility(Attackable target) {
        if (!canUseSpecialAbility()) return;
        System.out.println(this.name + " uses BRUTAL SMASH!");
        target.takeDamage(this.baseAttackPower + 20);
        cooldown = 3;
    }

    @Override
    public boolean canUseSpecialAbility() {
        return cooldown == 0;
    }
}
