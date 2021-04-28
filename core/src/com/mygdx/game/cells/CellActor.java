package com.mygdx.game.cells;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.cards.CardActor;
import com.mygdx.game.cards.Factory;
import com.mygdx.game.cards.buildings.Building;

public abstract class CellActor extends Image {
    protected CardActor buildingCardActor;
    protected DragAndDrop.Target target;
    protected DragAndDrop.Source source;
    protected Drawable drawable;
    public abstract DragAndDrop.Target getTarget();
    public abstract DragAndDrop.Source getSource();
    public CellActor(Skin skin, String drawableName){
        super(skin, drawableName);
        drawable = skin.getDrawable(drawableName);
    }

    @Override
    public Drawable getDrawable() {
        return drawable;
    }

    public void setBuildingCardActor(CardActor buildingCardActor) {
        this.buildingCardActor = buildingCardActor;
    }

    public void removeCartActor(){
        buildingCardActor = null;
    }
    public CardActor getBuildingCardActor() {
        return buildingCardActor;
    }
}
