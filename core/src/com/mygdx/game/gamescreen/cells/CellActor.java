package com.mygdx.game.gamescreen.cells;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.TextureLoader;
import com.mygdx.game.gamescreen.IDragAndDrop;
import com.mygdx.game.gamescreen.Singleton;
import com.mygdx.game.gamescreen.cards.CardActor;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;


public abstract class CellActor extends Group implements IDragAndDrop {
    @Override
    public DragAndDrop.Payload dragStart() {
        DragAndDrop.Payload payload = new DragAndDrop.Payload();
        payload.setObject(this);
        payload.setDragActor(buildingCardActor);  // Ставим перетаскиваемой рисунок на ячейке
        clearCell();//убираем постройку с КЛЕТКИ (актера) поля
        Singleton.getDragAndDrop().removeSource(getSource());
        Singleton.getDragAndDrop().addTarget(getTarget());
        return payload;
    }

    @Override
    public void noTargetAction(DragAndDrop.Payload payload) {
        switch (((CardActor) payload.getDragActor()).getFamilyType()) {
            case BUILDING:
            case UPGRAGE:
                Singleton.addBuildingCard((CardActor) payload.getDragActor());
                break;
            case CRAFTING:
                Singleton.addcraftingcard((CardActor) payload.getDragActor());//отправляем карту назад в руку
                break;
        }

        CardActor card = ((CellActor) payload.getObject()).getBuildingCardActor();
        if (card != null) {
            card.addAction(moveTo( card.getWidth() * (Singleton.getBuildingCards().size() - 1), 0, 0.1f));
        }
    }

    protected CardActor buildingCardActor;
    protected DragAndDrop.Target target;
    protected DragAndDrop.Source source;
    protected Image background,placedCraftingCard;

    public DragAndDrop.Target getTarget() {
        return target;
    }

    public DragAndDrop.Source getSource() {
        return source;
    }

    public Image getBackground() {
        return background;
    }
    public CellActor(String backgroundName, int size){
        super();
        background = new Image(TextureLoader.getSkin(),backgroundName);
        placedCraftingCard = new Image(TextureLoader.getSkin(),backgroundName);
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
    public void setPlacedObjImg(Drawable drawable){
        this.placedCraftingCard.setDrawable(drawable);
    }
    public CardActor getBuildingCardActor() {
        return buildingCardActor;
    }
}
