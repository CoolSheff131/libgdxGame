package com.mygdx.game.gamescreen.cards.buildings;

import com.mygdx.game.gamescreen.Items;
import com.mygdx.game.gamescreen.Singleton;
import com.mygdx.game.gamescreen.cards.Factory;

public class WorkerBuilding extends Building {
    public WorkerBuilding() {
        super("BuildW","BuildWSch");
    }

    @Override
    public String getDrawableName() {
        if(!isBuilded) return "BuildWSch";
        return "BuildW";
    }

    @Override
    public Items getItemType() {
        return Items.WORKER_BUILDING;
    }

    @Override
    public void doThing() {
        Singleton.addcraftingcard(Factory.createCard(Items.WORKER_CARD));

    }
}