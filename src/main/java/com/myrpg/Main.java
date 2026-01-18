package com.myrpg;

import com.badlogic.gdx.Game;
import com.myrpg.screens.MenuScreen;
import com.myrpg.utils.FontManager;

public class Main extends Game {

    @Override
    public void create() {
        System.out.println("=== RPG Game Started ===");

        FontManager.loadFonts();

        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        System.out.println("=== Game Closed ===");
    }
}
