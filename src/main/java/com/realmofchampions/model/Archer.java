package com.realmofchampions.model;

import com.realmofchampions.interfaces.Attackable;
import com.realmofchampions.interfaces.SpecialAbility;

public class Archer extends Character implements SpecialAbility {
    
    public Archer(String name) {
        super(name, 100, 20); // 100 Max HP, 20 Base Attack (Mid/Mid)
    }

    @Override
    public int attack() {
        System.out.println(this.name + " shoots a precise arrow!");
        return this.baseAttackPower;
    }

    @Override
    public void useSpecialAbility(Attackable target) {
        System.out.println(this.name + " uses PIERCING SHOT!");
        target.takeDamage(this.baseAttackPower + 15);
    }

    @Override
    public boolean canUseSpecialAbility() {
        return true;
    }
}
