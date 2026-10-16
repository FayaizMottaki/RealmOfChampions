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

    @Override
    public void use(Character target) {
        int currentHp = target.getHealth();
        int maxHp = target.getMaxHealth();
        int actualHeal = Math.min(healAmount, maxHp - currentHp);
        
        // To restore health, we apply negative damage or directly adjust health
        target.takeDamage(-actualHeal);
        System.out.println(target.getName() + " used " + name + " and restored " + actualHeal + " HP!");
    }
}
