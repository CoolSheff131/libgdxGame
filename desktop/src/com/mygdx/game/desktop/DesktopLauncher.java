package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MotherBoardCard;

public class DesktopLauncher {
	public static final int WIDTH=1280,HEIGHT=720;
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "MotherBoardCard";
		config.width = WIDTH;
		config.height = HEIGHT;
		new LwjglApplication(new MotherBoardCard(), config);
	}
}
