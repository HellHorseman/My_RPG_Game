package com.myrpg.entities;

import com.myrpg.inventory.Inventory;
import com.myrpg.inventory.Item;

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
            case "Воин":
                maxHealth = 120;
                maxMana = 30;
                break;
            case "Лучник":
                maxHealth = 90;
                maxMana = 50;
                break;
            case "Маг":
                maxHealth = 70;
                maxMana = 100;
                break;
            case "Разбойник":
                maxHealth = 100;
                maxMana = 40;
                break;
            default:
                maxHealth = 100;
                maxMana = 50;
        }

        health = maxHealth;
        mana = maxMana;

        System.out.println("Создан " + characterClass + ": HP=" + health + ", MP=" + mana);
    }

    private void giveStartingItems() {
        switch(characterClass) {
            case "Воин":
                inventory.addItem(new Item("Стальной меч", "Надежное оружие", 15));
                inventory.addItem(new Item("Деревянный щит", "Базовая защита", 10));
                break;
            case "Лучник":
                inventory.addItem(new Item("Деревянный лук", "Простой лук", 12));
                inventory.addItem(new Item("Стрелы", "Пачка из 20 стрел", 5));
                break;
            case "Маг":
                inventory.addItem(new Item("Деревянный посох", "Фокусирует магию", 10));
                inventory.addItem(new Item("Свиток огня", "Магия огня", 8));
                break;
            case "Разбойник":
                inventory.addItem(new Item("Парные кинжалы", "Быстрые клинки", 14));
                inventory.addItem(new Item("Отмычка", "Для вскрытия замков", 3));
                break;
        }

        inventory.addItem(new Item("Малое зелье здоровья", "Восстанавливает 30 HP", 2));
        inventory.addItem(new Item("Малое зелье маны", "Восстанавливает 20 MP", 2));
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

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
        System.out.println(name + " получил " + damage + " урона. Осталось HP: " + health);
    }

    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) health = maxHealth;
        System.out.println(name + " восстановил " + amount + " HP. Теперь HP: " + health);
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