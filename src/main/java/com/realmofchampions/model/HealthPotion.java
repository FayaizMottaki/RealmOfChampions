package com.realmofchampions.model;

public class HealthPotion extends Item {
    private final int healAmount;

    public HealthPotion(String name, int healAmount) {
        super(name, "Restores " + healAmount + " HP.");
        this.healAmount = healAmount;
    }

    public HealthPotion() {
        this("Health Potion", 40);
    }

    public int getHealAmount() {
        return healAmount;
    }

    @Override
    public void use(Character target) {
        int beforeHp = target.getHealth();
        target.heal(healAmount);
        int actualHeal = target.getHealth() - beforeHp;
        System.out.println(target.getName() + " used " + name + " and restored " + actualHeal + " HP!");
    }
}
