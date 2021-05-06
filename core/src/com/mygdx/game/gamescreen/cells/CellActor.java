package com.mygdx.game.gamescreen.cells;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.gamescreen.cards.CardActor;
import com.mygdx.game.gamescreen.cards.CellOrCard;

public abstract class CellActor extends Group implements CellOrCard {
    @Override
    public int CellOrCard() {
        return 1;
    }

    protected CardActor buildingCardActor;
    protected DragAndDrop.Target target;
    protected DragAndDrop.Source source;
    protected Image drawable,placedCraftingCard;
    public abstract DragAndDrop.Target getTarget();
    public abstract DragAndDrop.Source getSource();
    public CellActor(Skin skin, String drawableName,int size){
        super();
        drawable = new Image(skin,drawableName);
        placedCraftingCard = new Image(skin,drawableName);
        drawable.setSize(size,size);
        placedCraftingCard.setSize(size,size);
        addActor(drawable);
        addActor(placedCraftingCard);
    }


    public Drawable getDrawableClear() {
        return drawable.getDrawable();
    }

    public void setBuildingCardActor(CardActor buildingCardActor) {

        this.buildingCardActor = buildingCardActor;
    }

    public abstract void clearCell();


    public void setPlacedObjImg(Skin skin, String drawable){
        this.placedCraftingCard.setDrawable(skin, drawable);
    }
    public void setPlacedObjImg(Drawable drawable){
        this.placedCraftingCard.setDrawable(drawable);
    }

    public CardActor getBuildingCardActor() {
        return buildingCardActor;
    }
}
