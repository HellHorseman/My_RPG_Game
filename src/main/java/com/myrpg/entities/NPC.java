package com.myrpg.entities;

public class NPC {
    private String name;
    private String[] dialogues;
    private int currentDialogue = 0;

    public NPC(String name, String[] dialogues) {
        this.name = name;
        this.dialogues = dialogues;
    }

    public String getName() { return name; }

    public String getCurrentDialogue() {
        if (currentDialogue < dialogues.length) {
            return dialogues[currentDialogue];
        }
        return "I have nothing more to say.";
    }

    public boolean nextDialogue() {
        currentDialogue++;
        return currentDialogue < dialogues.length;
    }

    public void resetDialogues() {
        currentDialogue = 0;
    }
}

