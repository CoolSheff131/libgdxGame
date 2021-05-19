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
    public Building(String name,String buildedName) {
        super(buildedName,name);
        this.buildedName = buildedName;
        isBuilded = false;
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
        isBuilded = true;//ДОБАВИТЬ КОЛИЧЕСТВО ХОДОВ ДЛЯ ПОСТРОЙКИ
        if (occupiedCell != null){
            occupiedCell.setPlacedObjImg(skin,buildedName);
            Singleton.getDragAndDrop().removeSource( occupiedCell.getSource());//Убираем возможность перетаскивать построенной
        }
    }
}
