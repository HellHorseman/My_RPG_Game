package com.myrpg.quests;

public class Quest {
    private String id;
    private String title;
    private String description;
    private QuestType type;
    private int targetCount;
    private int currentCount;
    private boolean completed;
    private int rewardGold;
    private int rewardExp;

    public enum QuestType {
        KILL_ENEMIES,
        COLLECT_ITEMS,
        TALK_TO_NPC,
        EXPLORE_AREA
    }

    public Quest(String id, String title, String description, QuestType type,
                 int targetCount, int rewardGold, int rewardExp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.targetCount = targetCount;
        this.currentCount = 0;
        this.completed = false;
        this.rewardGold = rewardGold;
        this.rewardExp = rewardExp;
    }

    public void updateProgress(int amount) {
        if (completed) return;

        currentCount += amount;
        if (currentCount >= targetCount) {
            completed = true;
        }
    }

    public boolean isCompleted() { return completed; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getProgress() { return currentCount; }
    public int getTarget() { return targetCount; }
    public int getRewardGold() { return rewardGold; }
    public int getRewardExp() { return rewardExp; }
    public QuestType getType() { return type; }

    public String getProgressText() {
        return currentCount + "/" + targetCount + " (" +
                (currentCount * 100 / targetCount) + "%)";
    }
}
