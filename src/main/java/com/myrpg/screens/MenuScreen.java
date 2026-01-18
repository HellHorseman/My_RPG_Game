package com.myrpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myrpg.Main;

public class MenuScreen implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture background;

    private float newGameButtonY = 400;
    private float exitButtonY = 300;

    public MenuScreen(final Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(3);

        background = new Texture(Gdx.files.internal("assets/ui/menu_bg.png"));

        if (!Gdx.files.internal("assets/ui/menu_bg.png").exists()) {
        }
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        if (background != null) {
            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        font.setColor(1, 1, 0, 1);
        font.draw(batch, "MY RPG GAME", 250, 550);

        font.setColor(0.8f, 0.8f, 1, 1);
        font.draw(batch, "НОВАЯ ИГРА", 300, newGameButtonY);
        font.draw(batch, "ВЫХОД", 300, exitButtonY);

        font.setColor(1, 1, 1, 1);
        font.getData().setScale(1.5f);
        font.draw(batch, "Используйте мышку или стрелки + Enter", 200, 100);

        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseY > newGameButtonY - 30 && mouseY < newGameButtonY + 30) {
                game.setScreen(new CharacterCreationScreen(game));
            } else if (mouseY > exitButtonY - 30 && mouseY < exitButtonY + 30) {
                Gdx.app.exit();
            }
        }

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
        if (background != null) {
            background.dispose();
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
