package com.mygdx.game.cards.buildings;

import com.mygdx.game.Items;
import com.mygdx.game.cards.CardActor;

public class EnergyBuildingCardActor extends CardActor  {
    public EnergyBuildingCardActor() {
        super("en");
    }

    @Override
    public String getDrawableName() {
        return "en";
    }

    @Override
    public Items getItemType() {
        return Items.ENERGY_BUILDING;
    }
}
