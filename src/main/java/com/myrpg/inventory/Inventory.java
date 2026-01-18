package com.myrpg.inventory;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;
    private int capacity;
    private int gold;

    public Inventory(int capacity) {
        this.capacity = capacity;
        this.items = new ArrayList<>();
        this.gold = 100;
    }

    public boolean addItem(Item item) {
        if (items.size() < capacity) {
            items.add(item);
            return true;
        }
        return false;
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public int getGold() { return gold; }
    public void addGold(int amount) { gold += amount; }
    public boolean spendGold(int amount) {
        if (gold >= amount) {
            gold -= amount;
            return true;
        }
        return false;
    }

    public int getCapacity() { return capacity; }
    public int getUsedSlots() { return items.size(); }
}
