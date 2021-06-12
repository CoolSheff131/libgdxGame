package com.mygdx.game.gamescreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.gamescreen.cards.CardActor;
import com.mygdx.game.gamescreen.cards.CardFamily;
import com.mygdx.game.gamescreen.cards.buildings.Building;
import com.mygdx.game.gamescreen.cards.upgrades.Upgrade;
import com.mygdx.game.gamescreen.cells.CellActor;
import com.mygdx.game.gamescreen.cells.FieldCellActor;

import static com.mygdx.game.gamescreen.GameScreen.skin;

public class FieldTarget extends DragAndDrop.Target {
    private CellActor[][] fieldCells;

    public FieldTarget(Actor actor, CellActor[][] fieldCells) {
        super(actor);
        this.fieldCells = fieldCells;
    }

    @Override
    public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
        CardActor cardActor = (CardActor) payload.getDragActor();
        if ((cardActor.getFamilyType() == CardFamily.UPGRAGE && !((FieldCellActor) getActor()).isClear() && ((Upgrade) cardActor).canDrop(((FieldCellActor) getActor()).getBuilding())) || (cardActor.getFamilyType() == CardFamily.BUILDING && ((FieldCellActor) getActor()).isClear())) {
            FieldCellActor fieldCellTarget = (FieldCellActor) getActor();
            fieldCellTarget.setColor(Color.GREEN);
            if(fieldCells != null) {
                for (int i = 0; i < fieldCells.length; i++) {
                    fieldCells[i][fieldCellTarget.getColumn()].getBackground().setColor(Color.GREEN);
                    fieldCells[i][fieldCellTarget.getColumn()].getPlacedCraftingCard().setColor(Color.GREEN);
                }
                for (int i = 0; i < fieldCells.length; i++) {
                    fieldCells[fieldCellTarget.getRow()][i].getBackground().setColor(Color.GREEN);
                    fieldCells[fieldCellTarget.getRow()][i].getPlacedCraftingCard().setColor(Color.GREEN);
                }
            }
            return true;
        } else
            return false;
    }

    @Override
    public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float v, float v1, int i) {
        CardActor cardActor = (CardActor) payload.getDragActor();
        if (cardActor.getFamilyType() == CardFamily.BUILDING) {
            ((CellActor) getActor()).setPlacedObjImg(skin, ((CardActor) payload.getDragActor()).getDrawableName());//устанавливаем картинку поставленной картинки
            ((Building) (payload.getDragActor())).setOccupiedCell((FieldCellActor) getActor());
            ((CellActor) getActor()).setBuildingCardActor(((CardActor) payload.getDragActor()));    //устанавливаем карту
            ((FieldCellActor) getActor()).setBuilding((Building) payload.getDragActor());        //устанавливаем постройку
            Singleton.getDragAndDrop().addSource(((CellActor) getActor()).getSource());    //добавляем возможность передвигать
        } else if (cardActor.getFamilyType() == CardFamily.UPGRAGE) {
            ((Upgrade) cardActor).dropDo(((FieldCellActor) getActor()).getBuilding());
        }
        payload.getDragActor().remove(); //убираем актера со сцены
    }

    public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {
        FieldCellActor fieldCellTarget = (FieldCellActor) getActor();
        fieldCellTarget.setColor(Color.WHITE);

        for (int i = 0; i < fieldCells.length; i++) {
            fieldCells[i][fieldCellTarget.getColumn()].getBackground().setColor(Color.WHITE);
            fieldCells[i][fieldCellTarget.getColumn()].getPlacedCraftingCard().setColor(Color.WHITE);
        }
        for (int i = 0; i < fieldCells.length; i++) {
            fieldCells[fieldCellTarget.getRow()][i].getBackground().setColor(Color.WHITE);
            fieldCells[fieldCellTarget.getRow()][i].getPlacedCraftingCard().setColor(Color.WHITE);
        }

    }
}
