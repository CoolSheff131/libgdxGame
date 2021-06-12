package com.mygdx.game.gamescreen.cells;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.TextureLoader;
import com.mygdx.game.gamescreen.FieldTarget;
import com.mygdx.game.gamescreen.Singleton;
import com.mygdx.game.gamescreen.cards.Factory;
import com.mygdx.game.gamescreen.cards.buildings.Building;

public class FieldCellActor extends CellActor{
    private Building building;
    private int row, column;
    public FieldCellActor(FieldCellActor[][] field, int size, int row, int column) {
        super("fieldCell",size);
        target = new FieldTarget(this, field);
        source = Factory.createSource(this);
        this.row = row;
        this.column = column;
    }
    public boolean isClear(){
        return building == null;
    }
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Building getBuilding() {
        return building;
    }

    @Override
    public void clearCell() {
        building = null;
        buildingCardActor = null;
        placedCraftingCard.setDrawable(getBackgroundDrawable());
        Singleton.getDragAndDrop().addTarget(getTarget());
    }

    public void doEndTurnThing() {
        if(building != null)
            building.doThing();
    }


    public void setBuilding(Building building) {
        this.building = building;
    }
}
