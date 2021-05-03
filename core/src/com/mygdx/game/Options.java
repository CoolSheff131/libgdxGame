package com.mygdx.game;

public class Options {
    private static boolean soundBtn = true;

    public static boolean isSoundBtn() {
        return soundBtn;
    }

    public static void setSoundBtn(boolean soundBtn) {
        Options.soundBtn = soundBtn;
    }
}
