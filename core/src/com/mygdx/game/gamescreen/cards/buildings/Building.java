package com.mygdx.game.gamescreen.cards.buildings;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.FontManager;
import com.mygdx.game.TextureLoader;
import com.mygdx.game.gamescreen.Singleton;
import com.mygdx.game.gamescreen.cards.CardActor;
import com.mygdx.game.gamescreen.cards.CardFamily;
import com.mygdx.game.gamescreen.cells.FieldCellActor;



public abstract class Building extends CardActor {
    protected boolean isBuilded;
    private FieldCellActor occupiedCell;
    private String buildedName;
    private int buildTurns;
    private Label buildTurnsLabel;
    public Building(String name,String buildedName,String description) {
        this(name,buildedName,1,description);
    }
    public Building(String name,String buildedName, int buildTurns,String description) {
        super(buildedName,name,description);
        this.buildedName = buildedName;
        isBuilded = false;
        this.buildTurns = buildTurns;


        buildTurnsLabel = new Label(String.valueOf(buildTurns),new Label.LabelStyle(FontManager.getFontJoystix(10), Color.WHITE));
        buildTurnsLabel.setPosition(getWidth()/20,getHeight()/20);
        addActor(buildTurnsLabel);
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
                occupiedCell.setPlacedObjImg(TextureLoader.getSkin(), buildedName);
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
