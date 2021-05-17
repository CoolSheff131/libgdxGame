package com.mygdx.game.gamescreen.cells;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.gamescreen.Singleton;
import com.mygdx.game.gamescreen.cards.Factory;

public class CraftingCellActor extends CellActor{
    public CraftingCellActor(Skin skin, String drawableName,int size) {
        super(skin, drawableName,size);
        target = Factory.createTarget(Factory.WORKSHOPTARGET,this);
        source = Factory.createSource(this);

    }

    @Override
    public void clearCell() {
        Singleton.getCardsInCraftingSlots().remove(buildingCardActor);
        buildingCardActor = null;
        placedCraftingCard.setDrawable(getBackgroundDrawable());
        Singleton.getDADToField().addTarget(getTarget());

        //todo убирать из крафта
    }

    @Override
    public DragAndDrop.Target getTarget() {
        if(target == null){
            target = Factory.createTarget(Factory.WORKSHOPTARGET,this);
        }
        return target;
    }
    @Override
    public DragAndDrop.Source getSource() {

        return source;
    }
}
