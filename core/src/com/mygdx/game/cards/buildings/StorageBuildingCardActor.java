package com.mygdx.game.cards.buildings;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.Items;
import com.mygdx.game.MotherBoardCard;

public class StorageBuildingCardActor extends Building{
    public StorageBuildingCardActor() {
        super("stor");
    }

    @Override
    public String getDrawableName() {
        return "stor";
    }

    @Override
    public Items getItemType() {
        return Items.STORAGE_BUILDING;
    }

    @Override
    public void doThing() {

        //todo добавить действие на завершение хода
    }
}
