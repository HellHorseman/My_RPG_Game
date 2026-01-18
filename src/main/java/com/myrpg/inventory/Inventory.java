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
            System.out.println("Got item: " + item.getName());
            return true;
        }
        System.out.println("Inventory is full!");
        return false;
    }

    public void removeItem(Item item) {
        items.remove(item);
        System.out.println("Item deleted: " + item.getName());
    }

    public Item getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public int getGold() { return gold; }

    public void addGold(int amount) {
        gold += amount;
        System.out.println("Got gold: +" + amount + " (total: " + gold + ")");
    }

    public boolean spendGold(int amount) {
        if (gold >= amount) {
            gold -= amount;
            System.out.println("Spend gold: -" + amount + " (left: " + gold + ")");
            return true;
        }
        System.out.println("Not enough gold! Need: " + amount + ", have: " + gold);
        return false;
    }

    public int getCapacity() { return capacity; }
    public int getUsedSlots() { return items.size(); }
    public int getFreeSlots() { return capacity - items.size(); }

    public void printInventory() {
        System.out.println("=== INVENTORY ===");
        System.out.println("Gold: " + gold);
        System.out.println("Slots: " + getUsedSlots() + "/" + capacity);

        if (items.isEmpty()) {
            System.out.println("Inventory is empty");
        } else {
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + items.get(i));
            }
        }
        System.out.println("================");
    }
}
