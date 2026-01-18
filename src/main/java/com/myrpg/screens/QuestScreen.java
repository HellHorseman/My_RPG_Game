package com.myrpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myrpg.Main;
import com.myrpg.entities.Player;
import com.myrpg.quests.Quest;

public class QuestScreen implements Screen {
    private final Main game;
    private final Player player;
    private SpriteBatch batch;
    private BitmapFont font;
    private int selectedQuest = 0;

    public QuestScreen(Main game, Player player) {
        this.game = game;
        this.player = player;
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

        Gdx.gl.glClearColor(0.1f, 0.05f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        font.setColor(1, 0.8f, 0, 1);
        font.draw(batch, "QUESTS", 350, 700);

        var quests = player.getQuestManager().getActiveQuests();

        if (quests.isEmpty()) {
            font.setColor(1, 1, 1, 1);
            font.draw(batch, "No active quests", 100, 500);
            font.draw(batch, "Talk to NPCs to get quests", 100, 450);
        } else {
            for (int i = 0; i < quests.size(); i++) {
                Quest quest = quests.get(i);

                if (i == selectedQuest) {
                    font.setColor(0, 1, 0, 1);
                } else {
                    font.setColor(0.7f, 0.7f, 0.7f, 1);
                }

                font.draw(batch, quest.getTitle(), 100, 600 - i * 100);

                font.getData().setScale(1.5f);
                font.setColor(1, 1, 1, 1);
                font.draw(batch, quest.getDescription(), 120, 560 - i * 100);
                font.draw(batch, "Progress: " + quest.getProgressText(),
                        120, 530 - i * 100);
                font.draw(batch, "Reward: " + quest.getRewardGold() + " gold, " +
                        quest.getRewardExp() + " EXP", 120, 500 - i * 100);
                font.getData().setScale(2);
            }
        }

        font.setColor(0.8f, 0.8f, 1, 1);
        font.getData().setScale(1.5f);
        font.draw(batch, "Up/Down: select | ESC: back", 100, 100);

        batch.end();
    }

    private void handleInput() {
        var quests = player.getQuestManager().getActiveQuests();

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.UP)) {
            selectedQuest--;
            if (selectedQuest < 0) selectedQuest = quests.size() - 1;
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
            selectedQuest++;
            if (selectedQuest >= quests.size()) selectedQuest = 0;
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE) ||
                Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.Q)) {
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