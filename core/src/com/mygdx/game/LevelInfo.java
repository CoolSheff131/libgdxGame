package com.mygdx.game;

public class LevelInfo {
    private int maxTurn,  needResources,index;

    private boolean isOpen;

    public int getMaxTurn() {
        return maxTurn;
    }

    public int getNeedResources() {
        return needResources;
    }

    public void openLevel() {
        isOpen = true;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public int getIndex() {
        return index;
    }

    public LevelInfo(int maxTurn, int needResources) {
        this.maxTurn = maxTurn;
        this.needResources = needResources;
        index = LevelManager.getLevelInfos().size();
        isOpen = false;
    }
}
