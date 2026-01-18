package com.myrpg.world;

public class GameMap {
    private int[][] tiles;
    private int width;
    private int height;
    private int tileSize = 32;

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new int[width][height];
        generateTestMap();
    }

    private void generateTestMap() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    tiles[x][y] = 1;
                } else if (x > 5 && x < 15 && y > 5 && y < 15) {
                    tiles[x][y] = 3;
                } else if (x > 20 && x < 30 && y > 20 && y < 30) {
                    tiles[x][y] = 4;
                } else {
                    tiles[x][y] = 2;
                }
            }
        }

        tiles[5][10] = 2;
        tiles[10][5] = 2;
        tiles[20][25] = 2;
        tiles[25][20] = 2;
    }

    public boolean isWalkable(int tileX, int tileY) {
        if (tileX < 0 || tileY < 0 || tileX >= width || tileY >= height) {
            return false;
        }
        int tile = tiles[tileX][tileY];
        return tile != 1 && tile != 3;
    }

    public boolean isWalkable(float pixelX, float pixelY) {
        int tileX = (int)(pixelX / tileSize);
        int tileY = (int)(pixelY / tileSize);
        return isWalkable(tileX, tileY);
    }

    public int getTile(float pixelX, float pixelY) {
        int tileX = (int)(pixelX / tileSize);
        int tileY = (int)(pixelY / tileSize);

        if (tileX < 0 || tileY < 0 || tileX >= width || tileY >= height) {
            return 1;
        }

        return tiles[tileX][tileY];
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getTileSize() { return tileSize; }
    public int[][] getTiles() { return tiles; }

    public void printMap() {
        System.out.println("Map " + width + "x" + height + ":");
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                System.out.print(tiles[x][y] + " ");
            }
            System.out.println();
        }
    }
}
