package com.mygdx.game.gamescreen.cards.upgrades;

import com.mygdx.game.gamescreen.cards.Items;
import com.mygdx.game.gamescreen.cards.buildings.Building;

public class QuickBuild extends Upgrade{

    public QuickBuild() {
        super("quickBuild", "quickBuild");
    }


    @Override
    public Items getItemType() {
        return Items.QUICKBUILD_UPGRADE;
    }

    @Override
    public void dropDo(Building building) {
        building.build();
    }

    @Override
    public boolean canDrop(Building building) {
        return !building.isBuilded();
    }
}
