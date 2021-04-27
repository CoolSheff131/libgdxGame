package com.mygdx.game.cards.buildings;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.Items;
import com.mygdx.game.MotherBoardCard;

public class EnergyBuildingCardActor extends Building {
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

    @Override
    public void doThing() {

        //todo добавить действие на завершение хода
    }
}
