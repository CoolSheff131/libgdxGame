package com.mygdx.game.gamescreen.cards.buildings;

import com.mygdx.game.gamescreen.Items;
import com.mygdx.game.gamescreen.Singleton;
import com.mygdx.game.gamescreen.cards.Factory;

public class SchemeBuilding extends Building {
    public SchemeBuilding() {
        super("BuildS","BuildSSch");
    }

    @Override
    public String getDrawableName() {
        if(!isBuilded) return "BuildSSch";
        return "BuildS";
    }

    @Override
    public Items getItemType() {
        return Items.SCHEME_BUILDING;
    }

    @Override
    public void doThing() {
        Singleton.addcraftingcard(Factory.createCard(Items.SCHEME_CARD));

    }
}