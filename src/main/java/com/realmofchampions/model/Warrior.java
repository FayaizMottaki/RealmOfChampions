package com.realmofchampions.model;

import com.realmofchampions.interfaces.Attackable;
import com.realmofchampions.interfaces.SpecialAbility;

public class Warrior extends Character implements SpecialAbility {
    
    public Warrior(String name) {
        super(name, 120, 15); // 120 Max HP, 15 Base Attack
    }

    @Override
    public int attack() {
        System.out.println(this.name + " swings their sword!");
        return this.baseAttackPower;
    }

    @Override
    public void useSpecialAbility(Attackable target) {
        System.out.println(this.name + " uses HEAVY STRIKE!");
        target.takeDamage(this.baseAttackPower * 2);
    }

    @Override
    public boolean canUseSpecialAbility() {
        // For now, they can always use it. We will add cooldowns/mana later!
        return true;
    }
}
