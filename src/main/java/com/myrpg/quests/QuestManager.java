package com.myrpg.quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestManager {
    private Map<String, Quest> activeQuests;
    private Map<String, Quest> completedQuests;

    public QuestManager() {
        activeQuests = new HashMap<>();
        completedQuests = new HashMap<>();
        initializeQuests();
    }

    private void initializeQuests() {
        // Начальный квест
        Quest firstQuest = new Quest(
                "first_quest",
                "Goblin Problem",
                "The village is threatened by goblins. Defeat 3 goblins to help.",
                Quest.QuestType.KILL_ENEMIES,
                3,
                50,
                100
        );

        activeQuests.put(firstQuest.getTitle(), firstQuest);

        // Квест на сбор
        Quest collectQuest = new Quest(
                "collect_herbs",
                "Medicinal Herbs",
                "Collect 5 medicinal herbs from the forest.",
                Quest.QuestType.COLLECT_ITEMS,
                5,
                30,
                50
        );

        activeQuests.put(collectQuest.getTitle(), collectQuest);
    }

    public void addQuest(Quest quest) {
        activeQuests.put(quest.getTitle(), quest);
    }

    public void updateQuest(String title, int amount) {
        Quest quest = activeQuests.get(title);
        if (quest != null && !quest.isCompleted()) {
            quest.updateProgress(amount);

            if (quest.isCompleted()) {
                activeQuests.remove(title);
                completedQuests.put(title, quest);
                System.out.println("Quest completed: " + title);
            }
        }
    }

    public List<Quest> getActiveQuests() {
        return new ArrayList<>(activeQuests.values());
    }

    public List<Quest> getCompletedQuests() {
        return new ArrayList<>(completedQuests.values());
    }

    public boolean hasActiveQuests() {
        return !activeQuests.isEmpty();
    }
}
