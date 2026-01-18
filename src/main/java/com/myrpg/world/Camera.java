package com.myrpg.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.myrpg.entities.Player;

public class Camera extends OrthographicCamera {
    private Player player;
    private GameMap map;

    public Camera(Player player, GameMap map) {
        super();
        this.player = player;
        this.map = map;

        float viewportWidth = 20;
        float viewportHeight = 15;
        viewportWidth *= map.getTileSize();
        viewportHeight *= map.getTileSize();

        setToOrtho(false, viewportWidth, viewportHeight);
        update();
    }

    public void update() {
        if (player == null || map == null) return;

        position.set(
                player.getX() + 16,
                player.getY() + 16,
                0
        );

        float halfViewportWidth = viewportWidth / 2;
        float halfViewportHeight = viewportHeight / 2;

        float minX = halfViewportWidth;
        float maxX = map.getWidth() * map.getTileSize() - halfViewportWidth;
        float minY = halfViewportHeight;
        float maxY = map.getHeight() * map.getTileSize() - halfViewportHeight;

        if (position.x < minX) position.x = minX;
        if (position.x > maxX) position.x = maxX;
        if (position.y < minY) position.y = minY;
        if (position.y > maxY) position.y = maxY;

        super.update();
    }
}
