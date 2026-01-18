package com.myrpg.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontManager {
    private static BitmapFont defaultFont;
    private static BitmapFont largeFont;
    private static BitmapFont smallFont;

    public static void loadFonts() {
        try {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                    Gdx.files.internal("assets/fonts/arial.ttf")
            );

            FreeTypeFontParameter parameter = new FreeTypeFontParameter();

            parameter.size = 20;
            defaultFont = generator.generateFont(parameter);

            parameter.size = 32;
            largeFont = generator.generateFont(parameter);

            parameter.size = 14;
            smallFont = generator.generateFont(parameter);

            generator.dispose();
            System.out.println("Fonts loaded successfully");

        } catch (Exception e) {
            System.out.println("Using default font (Arial not found)");
            defaultFont = new BitmapFont();
            largeFont = new BitmapFont();
            smallFont = new BitmapFont();
        }
    }

    public static BitmapFont getDefaultFont() {
        return defaultFont;
    }

    public static BitmapFont getLargeFont() {
        return largeFont;
    }

    public static BitmapFont getSmallFont() {
        return smallFont;
    }

    public static void dispose() {
        if (defaultFont != null) defaultFont.dispose();
        if (largeFont != null) largeFont.dispose();
        if (smallFont != null) smallFont.dispose();
    }
}
