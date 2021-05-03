package com.mygdx.game.gamescreen.cards.buildings;

import com.mygdx.game.MotherBoardCard;
import com.mygdx.game.gamescreen.GameScreen;
import com.mygdx.game.gamescreen.Items;

public class EnergyBuilding extends Building{
    public EnergyBuilding() {
        super("BuildEn","BuildEn");
    }

    @Override
    public void doThing() {
        if(!isBuilded) build();
        else GameScreen.addEn(1);
    }

    @Override
    public String getDrawableName() {
        if(!isBuilded) return "BuildEnSch";
        return "BuildEn";
    }

    @Override
    public Items getItemType() {
        return Items.ENERGY_BUILDING;
    }
}
