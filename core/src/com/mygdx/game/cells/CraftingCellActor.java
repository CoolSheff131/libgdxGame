package com.mygdx.game.cells;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.cards.Factory;

public class CraftingCellActor extends CellActor{
    public CraftingCellActor(Skin skin, String drawableName) {
        super(skin, drawableName);
        target = Factory.createTarget(Factory.WORKSHOPTARGET,this);
        source = Factory.createSource(Factory.CRAFTCELLCARDSOURCE,this);
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
        if(source == null){
            source = Factory.createSource(Factory.CRAFTCELLCARDSOURCE,this);
        }
        return source;
    }
}
