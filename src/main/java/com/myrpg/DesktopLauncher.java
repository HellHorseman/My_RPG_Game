package com.myrpg;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

        config.setTitle("My RPG Game");
        config.setWindowedMode(1024, 768);
        config.setResizable(true);
        config.setForegroundFPS(60);
        config.setIdleFPS(30);

        try {
            new Lwjgl3Application(new Main(), config);
        } catch (Exception e) {
            System.err.println("Error starting game: " + e.getMessage());
            e.printStackTrace();
        }
    }
}