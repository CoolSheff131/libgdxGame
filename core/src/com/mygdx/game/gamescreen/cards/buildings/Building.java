package com.mygdx.game.gamescreen.cards.buildings;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.gamescreen.Singleton;
import com.mygdx.game.gamescreen.cards.CardActor;
import com.mygdx.game.gamescreen.cards.CardFamily;
import com.mygdx.game.gamescreen.cells.FieldCellActor;


import static com.mygdx.game.gamescreen.GameScreen.skin;

public abstract class Building extends CardActor {
    protected boolean isBuilded;
    private FieldCellActor occupiedCell;
    private String buildedName;
    private int buildTurns;
    public Building(String name,String buildedName) {
        super(buildedName,name);
        this.buildedName = buildedName;
        isBuilded = false;
        buildTurns = 1;
    }
    public Building(String name,String buildedName, int buildTurns) {
        super(buildedName,name);
        this.buildedName = buildedName;
        isBuilded = false;
        this.buildTurns = buildTurns;
    }
    public void setOccupiedCell(FieldCellActor occupiedCell) {
        this.occupiedCell = occupiedCell;
    }

    public abstract void doThing();

    @Override
    public DragAndDrop.Source getSource() {
        if (isBuilded) return null;
        return super.getSource();
    }

    @Override
    public CardFamily getFamilyType() {
        return CardFamily.BUILDING;
    }

    public boolean isBuilded() {
        return isBuilded;
    }

    public void build(){
        buildTurns--;
        if(buildTurns ==0) {
            isBuilded = true;
            if (occupiedCell != null) {
                occupiedCell.setPlacedObjImg(skin, buildedName);
                Singleton.getDragAndDrop().removeSource(occupiedCell.getSource());//Убираем возможность перетаскивать построенной
            }
        }
    }
    public void demolish(){
        if(occupiedCell !=null){
            occupiedCell.clearCell();

        }

    }
}
