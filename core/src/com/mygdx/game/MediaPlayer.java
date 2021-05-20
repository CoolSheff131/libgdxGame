package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class MediaPlayer {
    private static Sound btnSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Btn1.mp3"));
    private static Sound craftSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/craft.wav")),
            wrongCraftSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/wrongCraft.wav")),
            winSnd  = Gdx.audio.newSound(Gdx.files.internal("sounds/win.wav")),
            loseSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/lose.wav"));

    public static void playBtn() {
        if (Options.isSoundBtn())
            btnSound.play();
    }

    public static void playCraftSnd() {
        if (Options.isSoundBtn())
            craftSnd.play();
    }
    public static void playWrongCraftSnd() {
        if (Options.isSoundBtn())
            wrongCraftSnd.play();
    }
    public static void playWinSnd() {
        if (Options.isSoundBtn())
            winSnd.play();
    }
    public static void playLoseSnd() {
        if (Options.isSoundBtn())
            loseSnd.play();
    }
}