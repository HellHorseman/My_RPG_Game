package com.myrpg;

import com.badlogic.gdx.Game;
import com.myrpg.screens.MenuScreen;

public class Main extends Game {

    @Override
    public void create() {
        System.out.println("=== RPG Game Started ===");
        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        System.out.println("=== Game Closed ===");
    }
}
