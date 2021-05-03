package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MotherBoardCard extends Game {
    BitmapFont font;
    SpriteBatch batch;
    private static int  WIDTH_SCREEN, HEIGHT_SCREEN;
    @Override
    public void create() {
        WIDTH_SCREEN = Gdx.graphics.getWidth();
        HEIGHT_SCREEN = Gdx.graphics.getHeight();
        font = new BitmapFont();
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));

    }

    public static int getWidthScreen() {
        return WIDTH_SCREEN;
    }

    public static int getHeightScreen() {
        return HEIGHT_SCREEN;
    }
}
