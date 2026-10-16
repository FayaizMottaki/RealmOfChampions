package com.realmofchampions.model;

import com.realmofchampions.interfaces.Attackable;
import com.realmofchampions.interfaces.SpecialAbility;

public class Mage extends Character implements SpecialAbility {
    
    public Mage(String name) {
        super(name, 70, 30); // 70 Max HP, 30 Base Attack (Low HP, High Damage)
    }

    @Override
    public int attack() {
        System.out.println(this.name + " casts a magic missile!");
        return this.baseAttackPower;
    }

    @Override
    public void useSpecialAbility(Attackable target) {
        System.out.println(this.name + " casts FIREBALL!");
        target.takeDamage(this.baseAttackPower * 2);
    }

    @Override
    public boolean canUseSpecialAbility() {
        return true;
    }
}
