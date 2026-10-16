package com.realmofchampions.model;

import com.realmofchampions.interfaces.Attackable;
import com.realmofchampions.interfaces.SpecialAbility;

public class Orc extends Character implements SpecialAbility {
    
    public Orc(String name) {
        super(name, 150, 25); // 150 Max HP, 25 Base Attack (High HP, Mid-High Damage)
    }

    @Override
    public int attack() {
        System.out.println(this.name + " swings a massive warhammer!");
        return this.baseAttackPower;
    }

    @Override
    public void useSpecialAbility(Attackable target) {
        System.out.println(this.name + " uses BRUTAL SMASH!");
        target.takeDamage(this.baseAttackPower + 20);
    }

    @Override
    public boolean canUseSpecialAbility() {
        return true;
    }
}
