package com.mygdx.game.gamescreen;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.TextureLoader;
import com.mygdx.game.gamescreen.cards.CardActor;
import com.mygdx.game.gamescreen.cells.CellActor;


public class WorkshopTarget extends DragAndDrop.Target {

    public WorkshopTarget(Actor actor) {
        super(actor);
    }

    @Override
    public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
        getActor().setColor(Color.GREEN);
        return true;
    }

    @Override
    public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
        ((CellActor)getActor()).setPlacedObjImg(TextureLoader.getSkin(),((CardActor) payload.getDragActor()).getDrawableName());//устанавливаем картинку поставленной картинки
        ((CardActor)payload.getDragActor()).remove();// убираем актера со сцены
        ((CellActor)getActor()).setBuildingCardActor((CardActor) payload.getDragActor());//устанавливаем карту
        Singleton.getCardsInCraftingSlots().add((CardActor) payload.getDragActor());//добавляем карту в крафт
        Singleton.getDragAndDrop().addSource(((CellActor)getActor()).getSource());//добавляем возможность передвигать
        Singleton.getDragAndDrop().removeTarget(this);//убираем возможность ставить на клетку карту
    }

    public void reset (DragAndDrop.Source source, DragAndDrop.Payload payload) {
        getActor().setColor(Color.WHITE);
    }



}
