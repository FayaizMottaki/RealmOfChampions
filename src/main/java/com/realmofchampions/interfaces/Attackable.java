package com.realmofchampions.interfaces;

public interface Attackable {
    void takeDamage(int damage);
    int attack();
    boolean isAlive();
}
