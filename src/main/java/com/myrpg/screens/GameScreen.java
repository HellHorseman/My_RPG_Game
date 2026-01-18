package com.myrpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myrpg.Main;
import com.myrpg.entities.Player;

public class GameScreen implements Screen {
    private final Main game;
    private Player player;
    private SpriteBatch batch;
    private BitmapFont font;

    public GameScreen(Main game, Player player) {
        this.game = game;
        this.player = player;
        player.setPosition(400, 300);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2);

        System.out.println("Game Screen started");
        System.out.println("Player: " + player.getName());
        player.getInventory().printInventory();
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0.1f, 0.3f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        font.setColor(1, 1, 1, 1);
        font.draw(batch, player.getStatus(), 50, 700);

        font.draw(batch, "Gold: " + player.getInventory().getGold(), 50, 650);
        font.draw(batch, "Inventory: " + player.getInventory().getUsedSlots() + "/20", 50, 600);

        font.draw(batch, "Position: (" + (int)player.getX() + ", " + (int)player.getY() + ")", 50, 550);

        font.setColor(1, 0, 0, 1);
        font.draw(batch, "@", player.getX(), player.getY());

        font.setColor(0.8f, 0.8f, 1, 1);
        font.getData().setScale(1.5f);
        font.draw(batch, "WASD: movement | I: inventory | ESC: menu", 50, 100);

        batch.end();
    }

    private void handleInput() {
        float speed = 3;
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) {
            player.setPosition(player.getX(), player.getY() + speed);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) {
            player.setPosition(player.getX(), player.getY() - speed);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)) {
            player.setPosition(player.getX() - speed, player.getY());
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) {
            player.setPosition(player.getX() + speed, player.getY());
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.I)) {
            player.getInventory().printInventory();
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.U)) {
            player.takeDamage(10);
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.H)) {
            player.heal(20);
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.G)) {
            player.getInventory().addGold(50);
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            System.out.println("Return to menu (ball)");
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