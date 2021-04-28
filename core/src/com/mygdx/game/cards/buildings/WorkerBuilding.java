package com.mygdx.game.cards.buildings;

import com.mygdx.game.Items;
import com.mygdx.game.Singleton;
import com.mygdx.game.cards.Factory;

public class WorkerBuilding extends Building {
    public WorkerBuilding() {
        super("BuildW","BuildW");
    }

    @Override
    public String getDrawableName() {
        return "BuildW";
    }

    @Override
    public Items getItemType() {
        return Items.ENERGY_BUILDING;
    }

    @Override
    public void doThing() {
        Singleton.addcraftingcard(Factory.createCard(Items.WORKER_CARD));

    }
}