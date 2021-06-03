package com.mygdx.game.gamescreen.cards.upgrades;

import com.mygdx.game.gamescreen.cards.Items;
import com.mygdx.game.gamescreen.cards.buildings.Building;

public class DemolishBuild extends Upgrade{

    public DemolishBuild() {
            super("demolishBuild", "demolishBuild");
        }


        @Override
        public Items getItemType() {
            return Items.DEMOLISHBUILD_UPGRADE;
        }

        @Override
        public void dropDo(Building building) {
            building.demolish();
        }

        @Override
        public boolean canDrop(Building building) {
            return building.isBuilded();
        }
}
