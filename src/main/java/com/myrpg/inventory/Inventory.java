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
            System.out.println("Добавлен предмет: " + item.getName());
            return true;
        }
        System.out.println("Инвентарь полон!");
        return false;
    }

    public void removeItem(Item item) {
        items.remove(item);
        System.out.println("Удален предмет: " + item.getName());
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
        System.out.println("Получено золото: +" + amount + " (всего: " + gold + ")");
    }

    public boolean spendGold(int amount) {
        if (gold >= amount) {
            gold -= amount;
            System.out.println("Потрачено золото: -" + amount + " (осталось: " + gold + ")");
            return true;
        }
        System.out.println("Недостаточно золота! Нужно: " + amount + ", есть: " + gold);
        return false;
    }

    public int getCapacity() { return capacity; }
    public int getUsedSlots() { return items.size(); }
    public int getFreeSlots() { return capacity - items.size(); }

    public void printInventory() {
        System.out.println("=== ИНВЕНТАРЬ ===");
        System.out.println("Золото: " + gold);
        System.out.println("Слоты: " + getUsedSlots() + "/" + capacity);

        if (items.isEmpty()) {
            System.out.println("Инвентарь пуст");
        } else {
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + items.get(i));
            }
        }
        System.out.println("================");
    }
}
