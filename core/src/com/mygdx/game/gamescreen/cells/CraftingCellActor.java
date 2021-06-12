package com.mygdx.game.gamescreen.cells;


import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.gamescreen.FieldTarget;
import com.mygdx.game.gamescreen.Singleton;
import com.mygdx.game.gamescreen.WorkshopTarget;
import com.mygdx.game.gamescreen.cards.Factory;

public class CraftingCellActor extends CellActor{
    public CraftingCellActor(int size) {
        super("craftCell",size);
        target = new WorkshopTarget(this);
        source = Factory.createSource(this);
        Singleton.getDragAndDrop().addTarget(target);
    }

    @Override
    public void clearCell() {
        Singleton.getCardsInCraftingSlots().remove(buildingCardActor);
        buildingCardActor = null;
        placedCraftingCard.setDrawable(getBackgroundDrawable());

        Singleton.getDragAndDrop().addTarget(target);
        System.out.println("added");
    }
}
