# Realm of Champions

A 2D turn-based RPG battle game built in Java 27 and JavaFX 21 using Gradle and the Model-View-Controller (MVC) architectural pattern.

## Object-Oriented Design Principles

- **Abstraction:** Character and Item are abstract base classes defining common attributes and abstract behaviors.
- **Interface Segregation:** Attackable separates core combat actions (attack, takeDamage, isAlive) from SpecialAbility (useSpecialAbility, canUseSpecialAbility), so basic enemies like Goblin do not implement unused special-attack methods.
- **Inheritance:** Warrior, Archer, Mage, Goblin, and Orc extend Character; HealthPotion extends Item.
- **Polymorphism:** BattleController operates on Character and Item references, dynamically dispatching overridden attack(), useSpecialAbility(), and use() methods at runtime.
- **Encapsulation:** Character health, inventory (List<Item>), and ability cooldown states are protected and modified only through controlled methods (takeDamage, heal, addItem, removeItem).

## Project Structure

    src/main/java/com/realmofchampions/
    ├── Main.java                 # Console (CLI) entry point
    ├── controller/
    │   └── BattleController.java # Turn-based state machine & combat rules
    ├── interfaces/
    │   ├── Attackable.java       # Core combat contract
    │   └── SpecialAbility.java   # Special skill contract with cooldowns
    ├── model/
    │   ├── Archer.java
    │   ├── BattleState.java
    │   ├── Character.java
    │   ├── Goblin.java
    │   ├── HealthPotion.java
    │   ├── Item.java
    │   ├── Mage.java
    │   ├── Orc.java
    │   └── Warrior.java
    ├── ui/
    │   ├── BattleView.java       # JavaFX combat arena screen
    │   ├── GameApp.java          # JavaFX Application entry point
    │   └── SelectionView.java    # Hero & enemy selection screen
    └── util/
        └── BattleLog.java        # Combat event history logger

## How to Build & Run

### Run the JavaFX Desktop Application

    gradle run

### Compile Only

    gradle compileJava
