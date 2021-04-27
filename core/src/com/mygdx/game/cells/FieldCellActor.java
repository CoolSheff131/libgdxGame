package com.mygdx.game.cells;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.cards.Factory;
import com.mygdx.game.cards.buildings.Building;

public class FieldCellActor extends CellActor{
    private Building building;
    public FieldCellActor(Skin skin, String drawableName) {
        super(skin, drawableName);
        target = Factory.createTarget(Factory.FIELDTARGET,this);
        source = Factory.createSource(Factory.FieldCellCardSource,this);
        System.out.println("created");
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
}