package com.mygdx.game.gamescreen.cards.upgrades;

import com.mygdx.game.gamescreen.cards.Items;
import com.mygdx.game.gamescreen.cards.buildings.Building;

public class ActivateBuild extends Upgrade{

    public ActivateBuild() {
        super("activateBuild", "activatehBuild","Activate circuit");
    }


    @Override
    public Items getItemType() {
        return Items.DEMOLISHBUILD_UPGRADE;
    }

    @Override
    public void dropDo(Building building) {
        building.doThing();
    }

    @Override
    public boolean canDrop(Building building) {
        return building.isBuilded();
    }
}