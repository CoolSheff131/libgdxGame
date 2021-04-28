package com.mygdx.game.cards.buildings;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.Items;
import com.mygdx.game.MotherBoardCard;
import com.mygdx.game.Singleton;
import com.mygdx.game.cards.Factory;

public class SchemeBuilding extends Building {
    public SchemeBuilding() {
        super("BuildS","BuildS");
    }

    @Override
    public String getDrawableName() {
        return "BuildS";
    }

    @Override
    public Items getItemType() {
        return Items.ENERGY_BUILDING;
    }

    @Override
    public void doThing() {
        Singleton.addcraftingcard(Factory.createCard(Items.SCHEME_CARD));

    }
}
