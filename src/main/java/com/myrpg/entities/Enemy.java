package com.myrpg.entities;

public class Enemy {
    private String name;
    private int health;
    private int maxHealth;
    private int damage;
    private int experienceReward;
    private int goldReward;

    private float x, y;

    public Enemy(String name, float x, float y, int maxHealth, int damage, int expReward, int goldReward) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.damage = damage;
        this.experienceReward = expReward;
        this.goldReward = goldReward;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getDamage() { return damage; }
    public int getExperienceReward() { return experienceReward; }
    public int getGoldReward() { return goldReward; }
    public float getX() {return x;}
    public float getY() {return y;}

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getStatus() {
        return name + " HP: " + health + "/" + maxHealth;
    }
}
