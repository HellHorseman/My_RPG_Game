package com.myrpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myrpg.Main;
import com.myrpg.entities.Player;
import com.myrpg.inventory.Item;

public class InventoryScreen implements Screen {
    private final Main game;
    private final Player player;
    private SpriteBatch batch;
    private BitmapFont font;

    private int selectedItem = 0;

    public InventoryScreen(Main game, Player player) {
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

        Gdx.gl.glClearColor(0.1f, 0.05f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        font.setColor(1, 0.8f, 0, 1);
        font.draw(batch, "INVENTORY", 350, 700);

        font.setColor(1, 1, 0, 1);
        font.draw(batch, "Gold: " + player.getInventory().getGold(), 50, 650);

        font.setColor(0.8f, 0.8f, 1, 1);
        font.draw(batch, "Slots: " + player.getInventory().getUsedSlots() + "/" +
                player.getInventory().getCapacity(), 50, 600);

        font.setColor(1, 1, 1, 1);
        font.draw(batch, "ITEMS:", 100, 500);

        var items = player.getInventory().getItems();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);

            if (i == selectedItem) {
                font.setColor(0, 1, 0, 1);
                font.draw(batch, "> " + item.getName() + " - " + item.getValue() + "g",
                        150, 450 - i * 40);

                font.setColor(0.7f, 0.7f, 1, 1);
                font.getData().setScale(1.5f);
                font.draw(batch, "Description: " + item.getDescription(),
                        100, 200);
                font.getData().setScale(2);
            } else {
                font.setColor(0.7f, 0.7f, 0.7f, 1);
                font.draw(batch, "  " + item.getName() + " - " + item.getValue() + "g",
                        150, 450 - i * 40);
            }
        }

        font.setColor(0.8f, 0.8f, 1, 1);
        font.getData().setScale(1.5f);
        font.draw(batch, "Up/Down: select | Enter: use | ESC: back", 100, 100);

        batch.end();
    }

    private void handleInput() {
        var items = player.getInventory().getItems();

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.UP)) {
            selectedItem--;
            if (selectedItem < 0) selectedItem = items.size() - 1;
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
            selectedItem++;
            if (selectedItem >= items.size()) selectedItem = 0;
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            if (!items.isEmpty() && selectedItem < items.size()) {
                Item item = items.get(selectedItem);
                item.use();

                if (item.getName().contains("Health Potion")) {
                    player.heal(30);
                    player.getInventory().removeItem(item);
                    if (selectedItem >= items.size()) {
                        selectedItem = Math.max(0, items.size() - 1);
                    }
                }
            }
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE) ||
                Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.I)) {
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
