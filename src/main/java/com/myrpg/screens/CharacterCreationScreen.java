package com.myrpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myrpg.Main;
import com.myrpg.entities.Player;

public class CharacterCreationScreen implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private BitmapFont font;

    private String characterName = "Hero";
    private String[] classes = {"Warrior", "Archer", "Mage", "Thief"};
    private int selectedClass = 0;

    private float titleY = 600;
    private float nameY = 500;
    private float classesY = 400;
    private float hintY = 200;
    private float createButtonY = 100;

    public CharacterCreationScreen(final Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2);

        System.out.println("Character Creation Screen started");
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        font.setColor(1, 1, 0, 1);
        font.draw(batch, "CHARACTER CREATION", 200, titleY);

        font.setColor(1, 1, 1, 1);
        font.draw(batch, "Name: " + characterName, 100, nameY);

        font.draw(batch, "Choose class:", 100, classesY);
        for (int i = 0; i < classes.length; i++) {
            if (i == selectedClass) {
                font.setColor(0, 1, 0, 1);
            } else {
                font.setColor(0.7f, 0.7f, 0.7f, 1);
            }
            font.draw(batch, classes[i], 300 + i * 150, classesY - 50);
        }

        font.setColor(0.8f, 0.8f, 1, 1);
        font.getData().setScale(1.5f);
        font.draw(batch, "Arrows: select class | Enter: confirm", 100, hintY);
        font.draw(batch, "Escape: back to menu", 100, hintY - 40);

        font.setColor(0, 1, 0, 1);
        font.draw(batch, "[ENTER] CREATE CHARACTER", 300, createButtonY);

        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.LEFT)) {
            selectedClass--;
            if (selectedClass < 0) selectedClass = classes.length - 1;
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.RIGHT)) {
            selectedClass++;
            if (selectedClass >= classes.length) selectedClass = 0;
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            createCharacter();
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {

            System.out.println("Back to menu");
        }
    }

    private void createCharacter() {
        System.out.println("Creating character: " + characterName + " (" + classes[selectedClass] + ")");

        Player player = new Player(characterName, classes[selectedClass]);

        game.setScreen(new GameScreen(game, player));

        System.out.println("Character is created! Health: " + player.getHealth() + "/" + player.getMaxHealth());
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
