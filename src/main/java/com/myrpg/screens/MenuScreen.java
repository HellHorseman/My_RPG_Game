package com.myrpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myrpg.Main;

public class MenuScreen implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private BitmapFont font;

    public MenuScreen(final Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(3);
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        font.setColor(1, 1, 0, 1);
        font.draw(batch, "MY RPG GAME", 250, 550);

        font.setColor(0.8f, 0.8f, 1, 1);
        font.draw(batch, "НОВАЯ ИГРА", 300, 400);
        font.draw(batch, "ВЫХОД", 300, 300);

        font.setColor(1, 1, 1, 1);
        font.getData().setScale(1.5f);
        font.draw(batch, "Нажмите ENTER для начала", 250, 100);

        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            game.setScreen(new CharacterCreationScreen(game));
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
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
