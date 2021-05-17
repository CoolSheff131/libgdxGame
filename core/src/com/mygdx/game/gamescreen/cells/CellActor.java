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
    protected Image background,placedCraftingCard;
    public abstract DragAndDrop.Target getTarget();
    public abstract DragAndDrop.Source getSource();

    public Image getBackground() {
        return background;
    }

    public CellActor(Skin skin, String backgroundName, int size){
        super();
        background = new Image(skin,backgroundName);
        placedCraftingCard = new Image(skin,backgroundName);
        background.setSize(size,size);
        placedCraftingCard.setSize(size,size);
        addActor(background);
        addActor(placedCraftingCard);
    }


    public Image getPlacedCraftingCard() {
        return placedCraftingCard;
    }

    public Drawable getBackgroundDrawable() {
        return background.getDrawable();
    }

    public void setBuildingCardActor(CardActor buildingCardActor) {
        this.buildingCardActor = buildingCardActor;
    }

    public abstract void clearCell();

    public void setPlacedObjImg(Skin skin, String drawable){
        this.placedCraftingCard.setDrawable(skin, drawable);
    }

    public CardActor getBuildingCardActor() {
        return buildingCardActor;
    }
}
