package com.mygdx.game.gamescreen.cells;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.gamescreen.Singleton;
import com.mygdx.game.gamescreen.cards.Factory;
import com.mygdx.game.gamescreen.cards.buildings.Building;

public class FieldCellActor extends CellActor{
    private Building building;
    public FieldCellActor(Skin skin, String drawableName,int size) {
        super(skin, drawableName,size);
        target = Factory.createTarget(Factory.FIELDTARGET,this);
        source = Factory.createSource(Factory.FieldCellCardSource,this);
    }

    public void doEndTurnThing() {
        if(building != null)
            building.doThing();
    }

    @Override
    public DragAndDrop.Target getTarget() {
        if(target == null){
            target = Factory.createTarget(Factory.FIELDTARGET,this);
        }
        return target;
    }

    @Override
    public DragAndDrop.Source getSource() {
        if(source == null){
            source = Factory.createSource(Factory.FieldCellCardSource,this);
        }
        return source;
    }
    public void setBuilding(Building building) {
        this.building = building;
    }
    public void removeBuilding(){
        building = null;
    }
    public void clearBuilding(){
        removeCartActor();
        removeBuilding();//убираем постройку с КЛЕТКИ (актера) поля
        placedCraftingCard.setDrawable(getDrawableClear());
        Singleton.getDADToField().addTarget(getTarget());
    }
}
