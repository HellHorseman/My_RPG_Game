package com.myrpg;

import com.badlogic.gdx.Game;

public class Main extends Game {

    @Override
    public void create() {
        System.out.println("=== RPG Game Started ===");
        setScreen(new GameScreen(this));
    }
}
