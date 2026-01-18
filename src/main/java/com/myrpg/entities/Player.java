package com.myrpg.entities;

import com.myrpg.inventory.Inventory;
import com.myrpg.inventory.Item;
import com.myrpg.world.GameMap;

public class Player {
    private String name;
    private String characterClass;
    private int level;
    private int health;
    private int maxHealth;
    private int mana;
    private int maxMana;
    private Inventory inventory;

    private float x, y;

    private GameMap currentMap;

    public Player(String name, String characterClass) {
        this.name = name;
        this.characterClass = characterClass;
        this.level = 1;
        this.inventory = new Inventory(20);

        initializeClassStats();

        giveStartingItems();
    }

    private void initializeClassStats() {
        switch(characterClass) {
            case "Warrior":
                maxHealth = 120;
                maxMana = 30;
                break;
            case "Archer":
                maxHealth = 90;
                maxMana = 50;
                break;
            case "Mage":
                maxHealth = 70;
                maxMana = 100;
                break;
            case "Thief":
                maxHealth = 100;
                maxMana = 40;
                break;
            default:
                maxHealth = 100;
                maxMana = 50;
        }

        health = maxHealth;
        mana = maxMana;

        System.out.println("Create " + characterClass + ": HP=" + health + ", MP=" + mana);
    }

    private void giveStartingItems() {
        switch(characterClass) {
            case "Warrior":
                inventory.addItem(new Item("Steel Sword", "Reliable weapon", 15));
                inventory.addItem(new Item("Wood Shield", "Basic armour", 10));
                break;
            case "Archer":
                inventory.addItem(new Item("Wood bow", "Simple bow", 12));
                inventory.addItem(new Item("Arrows", "Quiver 20 arrows", 5));
                break;
            case "Mage":
                inventory.addItem(new Item("Wood stuff", "Focusing a magic", 10));
                inventory.addItem(new Item("Scroll of fire", "Magic of fire", 8));
                break;
            case "Thief":
                inventory.addItem(new Item("Dual daggers", "Fast blades", 14));
                inventory.addItem(new Item("Pick lock", "To pick the locks", 3));
                break;
        }

        inventory.addItem(new Item("Lesser Health Potion", "Recovers 30 HP", 2));
        inventory.addItem(new Item("Lesser Mana Potion", "Recovers 20 MP", 2));
    }

    public String getName() { return name; }
    public String getCharacterClass() { return characterClass; }
    public int getLevel() { return level; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getMana() { return mana; }
    public int getMaxMana() { return maxMana; }
    public Inventory getInventory() { return inventory; }
    public float getX() { return x; }
    public float getY() { return y; }

    public void setCurrentMap(GameMap map) {
        this.currentMap = map;
    }

    public boolean move(float deltaX, float deltaY) {
        if (currentMap == null) {
            x += deltaX;
            y += deltaY;
            return true;
        }

        float newX = x + deltaX;
        float newY = y + deltaY;

        boolean canMove = true;

        if (!currentMap.isWalkable(newX, newY + 32)) canMove = false;
        if (!currentMap.isWalkable(newX + 32, newY + 32)) canMove = false;
        if (!currentMap.isWalkable(newX, newY)) canMove = false;
        if (!currentMap.isWalkable(newX + 32, newY)) canMove = false;

        if (canMove) {
            x = newX;
            y = newY;
            return true;
        }

        newX = x + deltaX;
        newY = y;
        canMove = true;

        if (!currentMap.isWalkable(newX, newY + 32)) canMove = false;
        if (!currentMap.isWalkable(newX + 32, newY + 32)) canMove = false;
        if (!currentMap.isWalkable(newX, newY)) canMove = false;
        if (!currentMap.isWalkable(newX + 32, newY)) canMove = false;

        if (canMove) {
            x = newX;
            return true;
        }

        newX = x;
        newY = y + deltaY;
        canMove = true;

        if (!currentMap.isWalkable(newX, newY + 32)) canMove = false;
        if (!currentMap.isWalkable(newX + 32, newY + 32)) canMove = false;
        if (!currentMap.isWalkable(newX, newY)) canMove = false;
        if (!currentMap.isWalkable(newX + 32, newY)) canMove = false;

        if (canMove) {
            y = newY;
            return true;
        }

        return false;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
        System.out.println(name + " take " + damage + " damage. Left HP: " + health);
    }

    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) health = maxHealth;
        System.out.println(name + " recover " + amount + " HP. Now HP: " + health);
    }

    public boolean useMana(int amount) {
        if (mana >= amount) {
            mana -= amount;
            return true;
        }
        return false;
    }

    public void restoreMana(int amount) {
        mana += amount;
        if (mana > maxMana) mana = maxMana;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getStatus() {
        return name + " (" + characterClass + ") Lvl " + level +
                " | HP: " + health + "/" + maxHealth +
                " | MP: " + mana + "/" + maxMana;
    }
}