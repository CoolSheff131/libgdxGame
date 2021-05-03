package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class MediaPlayer {
    private static Sound btnSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Btn1.mp3"));

    public static void playBtn(){
        if(Options.isSoundBtn())
        btnSound.play();
    }
}
