package com.myrpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myrpg.Main;
import com.myrpg.entities.NPC;
import com.myrpg.entities.Player;

public class DialogueScreen implements Screen {
    private final Main game;
    private final Player player;
    private final NPC npc;
    private SpriteBatch batch;
    private BitmapFont font;

    public DialogueScreen(Main game, Player player, NPC npc) {
        this.game = game;
        this.player = player;
        this.npc = npc;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2);
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        font.setColor(1, 1, 0, 1);
        font.draw(batch, npc.getName(), 100, 600);

        font.setColor(1, 1, 1, 1);
        font.getData().setScale(1.8f);

        String dialogue = npc.getCurrentDialogue();
        int y = 500;
        int maxWidth = 600;
        int charPerLine = 50;

        for (int i = 0; i < dialogue.length(); i += charPerLine) {
            int end = Math.min(i + charPerLine, dialogue.length());
            String line = dialogue.substring(i, end);
            font.draw(batch, line, 100, y);
            y -= 40;
        }

        font.setColor(0.8f, 0.8f, 1, 1);
        font.getData().setScale(1.5f);
        font.draw(batch, "Press SPACE to continue", 100, 100);
        font.draw(batch, "Press ESC to exit", 100, 60);

        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            if (!npc.nextDialogue()) {
                game.setScreen(new GameScreen(game, player));
            }
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            game.setScreen(new GameScreen(game, player));
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
