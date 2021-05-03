package com.mygdx.game.gamescreen.cards.buildings;

import com.mygdx.game.gamescreen.Items;
import com.mygdx.game.gamescreen.Singleton;
import com.mygdx.game.gamescreen.cards.Factory;

public class ResourseBuilding extends Building{
    public ResourseBuilding() {
        super("BuildR","BuildRSch");
    }

    @Override
    public String getDrawableName() {
        if(!isBuilded) return "BuildRSch";
        return "BuildR";
    }

    @Override
    public Items getItemType() {
        return Items.RESOURSE_BUILDING;
    }

    @Override
    public void doThing() {
        Singleton.addcraftingcard(Factory.createCard(Items.RESOURSE_CARD));

    }
}
