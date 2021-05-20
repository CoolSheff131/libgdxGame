package com.mygdx.game;

import java.util.ArrayList;

public class LevelManager {
    private static ArrayList<LevelInfo> levelInfos;
    static{
        levelInfos = new ArrayList<>();
        levelInfos.add(new LevelInfo(10,10));
        levelInfos.get(0).openLevel();
        levelInfos.add(new LevelInfo(15,30));
        levelInfos.add(new LevelInfo(20,50));
    }
    public static void addLevel(LevelInfo levelInfo){
        if(levelInfo != null)
        levelInfos.add(levelInfo);
    }
    public static LevelInfo getLevel(int indexLevel){
        if (indexLevel >= levelInfos.size())  // Если уровень последний
            return null;
        return levelInfos.get(indexLevel);
    }

    public static ArrayList<LevelInfo> getLevelInfos() {
        return levelInfos;
    }
}
