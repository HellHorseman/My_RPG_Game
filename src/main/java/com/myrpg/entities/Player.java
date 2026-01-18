package com.myrpg.entities;

import com.myrpg.inventory.Inventory;
import com.myrpg.inventory.Item;

public class Player {
    private String name;
    private String characterClass;
    private int level;
    private int health;
    private int maxHealth;
    private Inventory inventory;
    private float x, y;

    public Player(String name, String characterClass) {
        this.name = name;
        this.characterClass = characterClass;
        this.level = 1;
        this.maxHealth = 100;
        this.health = maxHealth;
        this.inventory = new Inventory(20);

        inventory.addItem(new Item("Меч новичка", "Обычный меч для начинающих", 5));
        inventory.addItem(new Item("Малое зелье здоровья", "Восстанавливает 20 HP", 2));
    }

    public String getName() { return name; }
    public String getCharacterClass() { return characterClass; }
    public int getLevel() { return level; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public Inventory getInventory() { return inventory; }
    public float getX() { return x; }
    public float getY() { return y; }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) health = maxHealth;
    }

    public boolean isAlive() {
        return health > 0;
    }
}
