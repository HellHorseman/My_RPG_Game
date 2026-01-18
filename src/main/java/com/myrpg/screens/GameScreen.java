package com.myrpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.myrpg.Main;
import com.myrpg.entities.Enemy;
import com.myrpg.entities.NPC;
import com.myrpg.entities.Player;
import com.myrpg.world.Camera;
import com.myrpg.world.GameMap;

public class GameScreen implements Screen {
    private final Main game;
    private Player player;
    private GameMap map;
    private Camera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    private Texture grassTexture;
    private Texture wallTexture;
    private Texture waterTexture;
    private Texture forestTexture;
    private Texture playerTexture;

    public GameScreen(Main game, Player player) {
        this.game = game;
        this.player = player;

        map = new GameMap(40, 30);
        player.setCurrentMap(map);
        player.setPosition(100, 100);

        camera = new Camera(player, map);

        System.out.println("Game Screen started with map");
        map.printMap();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2);
        shapeRenderer = new ShapeRenderer();

        createTextures();

        System.out.println("Game Screen started");
        System.out.println("Player: " + player.getName());
        player.getInventory().printInventory();
    }

    private void createTextures() {
        grassTexture = createColorTexture(0x00FF00FF);
        wallTexture = createColorTexture(0x888888FF);
        waterTexture = createColorTexture(0x0000FFFF);
        forestTexture = createColorTexture(0x008800FF);
        playerTexture = createColorTexture(0xFF0000FF);
    }

    private Texture createColorTexture(int color) {
        Texture texture = new Texture(32, 32, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
        texture.getTextureData().prepare();

        var pixmap = texture.getTextureData().consumePixmap();
        pixmap.setColor(color);
        pixmap.fill();

        texture.getTextureData().disposePixmap();
        return texture;
    }

    @Override
    public void render(float delta) {
        handleInput();
        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawMap();
        drawPlayer();
        batch.end();

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

        batch.setProjectionMatrix(batch.getProjectionMatrix().setToOrtho2D(0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        batch.begin();
        drawUI();
        batch.end();
    }

    private void drawMap() {
        int tileSize = map.getTileSize();
        int[][] tiles = map.getTiles();

        int startX = Math.max(0, (int)(camera.position.x - camera.viewportWidth / 2) / tileSize - 1);
        int endX = Math.min(map.getWidth(), (int)(camera.position.x + camera.viewportWidth / 2) / tileSize + 1);
        int startY = Math.max(0, (int)(camera.position.y - camera.viewportHeight / 2) / tileSize - 1);
        int endY = Math.min(map.getHeight(), (int)(camera.position.y + camera.viewportHeight / 2) / tileSize + 1);

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                Texture texture = null;
                switch(tiles[x][y]) {
                    case 1: texture = wallTexture; break;
                    case 2: texture = grassTexture; break;
                    case 3: texture = waterTexture; break;
                    case 4: texture = forestTexture; break;
                }

                if (texture != null) {
                    batch.draw(texture, x * tileSize, y * tileSize, tileSize, tileSize);
                }
            }
        }
    }

    private void drawPlayer() {
        batch.draw(playerTexture, player.getX(), player.getY(), 32, 32);
    }

    private void drawUI() {
        font.setColor(1, 1, 1, 1);
        font.draw(batch, player.getStatus(), 10, Gdx.graphics.getHeight() - 10);
        font.draw(batch, "Position: (" + (int)player.getX() + ", " + (int)player.getY() + ")",
                10, Gdx.graphics.getHeight() - 40);
        font.draw(batch, "Gold: " + player.getInventory().getGold(),
                10, Gdx.graphics.getHeight() - 70);

        font.getData().setScale(1.0f);
        font.draw(batch, "WASD: Move | I: Inventory | B: Battle | T: Talk | ESC: Menu",
                10, 30);
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

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.I)) {
            game.setScreen(new InventoryScreen(game, player));
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.B)) {
            Enemy testEnemy = new Enemy("Goblin", 50, 8, 25, 10);
            game.setScreen(new CombatScreen(game, player, testEnemy));
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.T)) {
            String[] dialogues = {
                    "Hello, traveler!",
                    "Welcome to our village.",
                    "We need help with the goblins in the forest.",
                    "Will you help us?"
            };
            NPC testNPC = new NPC("Old Man", dialogues);
            game.setScreen(new DialogueScreen(game, player, testNPC));
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            // TODO: Добавить меню паузы
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        shapeRenderer.dispose();
        grassTexture.dispose();
        wallTexture.dispose();
        waterTexture.dispose();
        forestTexture.dispose();
        playerTexture.dispose();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 20 * map.getTileSize();
        camera.viewportHeight = 15 * map.getTileSize();
        camera.update();
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}