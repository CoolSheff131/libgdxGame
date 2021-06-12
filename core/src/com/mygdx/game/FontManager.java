package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontManager {
    private static FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/k.ttf"));
    private static FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    public static BitmapFont getFont(int size){
        parameter.size = size;
        return generator.generateFont(parameter);
    }

}
