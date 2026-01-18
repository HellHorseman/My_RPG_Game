package com.myrpg.world;

import com.myrpg.entities.NPC;
import java.util.ArrayList;
import java.util.List;

public class NPCManager {
    private List<MapNPC> npcs;

    public NPCManager() {
        npcs = new ArrayList<>();
        initializeNPCs();
    }

    private void initializeNPCs() {
        String[] guideDialogues = {
                "Welcome to our village!",
                "Be careful in the forest - goblins live there.",
                "You can buy supplies at the shop to the east."
        };

        npcs.add(new MapNPC("Guide", guideDialogues, 150, 150));

        String[] merchantDialogues = {
                "Greetings, adventurer!",
                "I sell weapons and armor.",
                "Come back when you have more gold!"
        };

        npcs.add(new MapNPC("Merchant", merchantDialogues, 300, 200));
    }

    public List<MapNPC> getNPCs() {
        return npcs;
    }

    public MapNPC getNPCAt(float x, float y, float range) {
        for (MapNPC npc : npcs) {
            float dx = npc.getX() - x;
            float dy = npc.getY() - y;
            float distance = (float)Math.sqrt(dx * dx + dy * dy);

            if (distance < range) {
                return npc;
            }
        }
        return null;
    }

    public static class MapNPC {
        private NPC npc;
        private float x, y;

        public MapNPC(String name, String[] dialogues, float x, float y) {
            this.npc = new NPC(name, dialogues);
            this.x = x;
            this.y = y;
        }

        public NPC getNPC() { return npc; }
        public float getX() { return x; }
        public float getY() { return y; }
    }
}
