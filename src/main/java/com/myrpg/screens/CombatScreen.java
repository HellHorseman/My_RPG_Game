package com.myrpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myrpg.Main;
import com.myrpg.entities.Enemy;
import com.myrpg.entities.Player;

public class CombatScreen implements Screen {
    private final Main game;
    private final Player player;
    private final Enemy enemy;
    private SpriteBatch batch;
    private BitmapFont font;

    private String combatLog = "";
    private boolean playerTurn = true;
    private boolean combatEnded = false;

    public CombatScreen(Main game, Player player, Enemy enemy) {
        this.game = game;
        this.player = player;
        this.enemy = enemy;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2);

        combatLog = "A wild " + enemy.getName() + " appears!";
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0.3f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        font.setColor(1, 0.5f, 0.5f, 1);
        font.draw(batch, "COMBAT", 350, 700);

        font.setColor(0, 1, 0, 1);
        font.draw(batch, player.getName(), 100, 600);
        font.draw(batch, "HP: " + player.getHealth() + "/" + player.getMaxHealth(),
                100, 560);
        font.draw(batch, "MP: " + player.getMana() + "/" + player.getMaxMana(),
                100, 520);

        font.setColor(1, 0, 0, 1);
        font.draw(batch, enemy.getName(), 500, 600);
        font.draw(batch, "HP: " + enemy.getHealth() + "/" + enemy.getMaxHealth(),
                500, 560);

        font.setColor(1, 1, 1, 1);
        font.getData().setScale(1.5f);
        font.draw(batch, "Combat Log:", 100, 400);
        font.draw(batch, combatLog, 100, 350);

        if (!combatEnded) {
            if (playerTurn) {
                font.setColor(0, 1, 1, 1);
                font.draw(batch, "YOUR TURN", 100, 250);
                font.setColor(1, 1, 1, 1);
                font.draw(batch, "1. Attack", 150, 200);
                font.draw(batch, "2. Use item", 150, 160);
                font.draw(batch, "3. Run", 150, 120);
            } else {
                font.setColor(1, 0, 0, 1);
                font.draw(batch, enemy.getName() + "'s TURN", 100, 250);
            }
        } else {
            font.setColor(1, 1, 0, 1);
            font.draw(batch, "COMBAT ENDED", 100, 250);
            font.draw(batch, "Press SPACE to continue", 100, 200);
        }

        batch.end();
    }

    private void handleInput() {
        if (combatEnded) {
            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
                game.setScreen(new GameScreen(game, player));
            }
            return;
        }

        if (playerTurn) {
            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.NUM_1)) {
                playerAttack();
            } else if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.NUM_3)) {
                tryRun();
            }
        } else {
            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ANY_KEY)) {
                enemyAttack();
            }
        }
    }

    private void playerAttack() {
        int damage = 10 + (int)(Math.random() * 10);
        enemy.takeDamage(damage);
        combatLog = player.getName() + " attacks for " + damage + " damage!";

        if (!enemy.isAlive()) {
            endCombat(true);
        } else {
            playerTurn = false;
        }
    }

    private void enemyAttack() {
        int damage = enemy.getDamage();
        player.takeDamage(damage);
        combatLog = enemy.getName() + " attacks for " + damage + " damage!";

        if (!player.isAlive()) {
            endCombat(false);
        } else {
            playerTurn = true;
        }
    }

    private void tryRun() {
        if (Math.random() > 0.5) {
            combatLog = "Successfully escaped!";
            combatEnded = true;
        } else {
            combatLog = "Failed to escape!";
            playerTurn = false;
        }
    }

    private void endCombat(boolean playerWon) {
        combatEnded = true;

        if (playerWon) {
            player.getQuestManager().updateQuest("Goblin Problem", 1);
        }

        if (playerWon) {
            int gold = enemy.getGoldReward();
            int exp = enemy.getExperienceReward();

            player.getInventory().addGold(gold);
            // TODO: Add experience system

            combatLog = "Victory! Gained " + gold + " gold and " + exp + " EXP!";
        } else {
            combatLog = "You were defeated...";
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
