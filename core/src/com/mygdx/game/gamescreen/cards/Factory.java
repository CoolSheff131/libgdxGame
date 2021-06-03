package com.mygdx.game.gamescreen.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Null;
import com.mygdx.game.gamescreen.GameScreen;
import com.mygdx.game.gamescreen.IDragAndDrop;
import com.mygdx.game.gamescreen.cards.buildings.EnergyBuilding;
import com.mygdx.game.gamescreen.cards.buildings.WorkerBuilding;
import com.mygdx.game.gamescreen.cards.upgrades.ActivateBuild;
import com.mygdx.game.gamescreen.cards.upgrades.DemolishBuild;
import com.mygdx.game.gamescreen.cards.upgrades.QuickBuild;
import com.mygdx.game.gamescreen.cards.upgrades.Upgrade;
import com.mygdx.game.gamescreen.cells.CellActor;
import com.mygdx.game.gamescreen.cells.FieldCellActor;
import com.mygdx.game.gamescreen.Singleton;
import com.mygdx.game.gamescreen.cards.buildings.Building;
import com.mygdx.game.gamescreen.cards.buildings.SchemeBuilding;
import com.mygdx.game.gamescreen.cards.buildings.ResourseBuilding;
import com.mygdx.game.gamescreen.cards.resource.ResourceCardActor;
import com.mygdx.game.gamescreen.cards.resource.SchemeCardActor;
import com.mygdx.game.gamescreen.cards.resource.WorkerCardActor;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.mygdx.game.gamescreen.GameScreen.CARD_WIDTH;
import static com.mygdx.game.gamescreen.GameScreen.HEIGHT_CRAFTING_HAND;
import static com.mygdx.game.gamescreen.GameScreen.PADDING;
import static com.mygdx.game.gamescreen.GameScreen.WIDTH_BUTTON;
import static com.mygdx.game.gamescreen.GameScreen.skin;

public class Factory {
    public static CardActor createCard(Items type){
        CardActor cardActor = null;
        switch (type){
            case RESOURSE_CARD: cardActor = new ResourceCardActor(); break;
            case WORKER_CARD:cardActor = new WorkerCardActor(); break;
            case SCHEME_CARD:cardActor = new SchemeCardActor(); break;

            case RESOURSE_BUILDING:cardActor = new ResourseBuilding();break;
            case WORKER_BUILDING:cardActor = new WorkerBuilding();  break;
            case SCHEME_BUILDING: cardActor = new SchemeBuilding(); break;
            case ENERGY_BUILDING: cardActor = new EnergyBuilding(); break;

            case QUICKBUILD_UPGRADE: cardActor = new QuickBuild(); break;
            case DEMOLISHBUILD_UPGRADE: cardActor = new DemolishBuild(); break;
            case ACTIVATEBUILD_UPGRADE: cardActor = new ActivateBuild(); break;
        }
        cardActor.setSource(createSource(cardActor));
        return cardActor;
    }
    public static DragAndDrop.Source createSource(Actor actor) {
        return new DragAndDrop.Source(actor) {
            @Null
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                return ((IDragAndDrop)getActor()).dragStart();
            }
            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                if (target == null) {
                    ((IDragAndDrop) payload.getObject()).dragStop(payload);
                }
            }
        };
    }
    public static final int FIELDTARGET = 10;
    public static final int WORKSHOPTARGET = 11;
    public static DragAndDrop.Target createTarget(int type, Actor actor){
        DragAndDrop.Target target = null;
        switch (type){
            case FIELDTARGET:
                target = new DragAndDrop.Target(actor) {
                    public boolean drag (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                        CardActor cardActor = (CardActor) payload.getDragActor();
                        if((cardActor.getFamilyType() == CardFamily.UPGRAGE   &&  !((FieldCellActor) getActor()).isClear() && ((Upgrade)cardActor).canDrop(((FieldCellActor) getActor()).getBuilding()))|| (cardActor.getFamilyType() == CardFamily.BUILDING &&  ((FieldCellActor) getActor()).isClear())) {
                            FieldCellActor fieldCellTarget = (FieldCellActor) getActor();
                            fieldCellTarget.setColor(Color.GREEN);
                            FieldCellActor[][] fieldCells = GameScreen.getFieldCells();
                            for (int i = 0; i < fieldCells.length; i++) {
                                fieldCells[i][fieldCellTarget.getColumn()].getBackground().setColor(Color.GREEN);
                                fieldCells[i][fieldCellTarget.getColumn()].getPlacedCraftingCard().setColor(Color.GREEN);
                            }
                            for (int i = 0; i < fieldCells.length; i++) {
                                fieldCells[fieldCellTarget.getRow()][i].getBackground().setColor(Color.GREEN);
                                fieldCells[fieldCellTarget.getRow()][i].getPlacedCraftingCard().setColor(Color.GREEN);
                            }
                            return true;
                        }else
                            return false;
                    }
                    public void reset (DragAndDrop.Source source, DragAndDrop.Payload payload) {
                        FieldCellActor fieldCellTarget = (FieldCellActor) getActor();
                        fieldCellTarget.setColor(Color.WHITE);
                        FieldCellActor[][] fieldCells = GameScreen.getFieldCells();
                        for (int i = 0; i < fieldCells.length ; i++) {
                            fieldCells[i][fieldCellTarget.getColumn()].getBackground().setColor(Color.WHITE);
                            fieldCells[i][fieldCellTarget.getColumn()].getPlacedCraftingCard().setColor(Color.WHITE);
                        }
                        for (int i = 0; i < fieldCells.length ; i++) {
                            fieldCells[fieldCellTarget.getRow()][i].getBackground().setColor(Color.WHITE);
                            fieldCells[fieldCellTarget.getRow()][i].getPlacedCraftingCard().setColor(Color.WHITE);
                        }

                    }
                    public void drop (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                        CardActor cardActor = (CardActor) payload.getDragActor();
                        if(cardActor.getFamilyType() == CardFamily.BUILDING) {
                            ((CellActor) getActor()).setPlacedObjImg(skin, ((CardActor) payload.getDragActor()).getDrawableName());//устанавливаем картинку поставленной картинки
                            ((Building) (payload.getDragActor())).setOccupiedCell((FieldCellActor) getActor());
                            ((CellActor) getActor()).setBuildingCardActor(((CardActor) payload.getDragActor()));    //устанавливаем карту
                            ((FieldCellActor) getActor()).setBuilding((Building) payload.getDragActor());        //устанавливаем постройку
                            Singleton.getDragAndDrop().addSource(((CellActor) getActor()).getSource());    //добавляем возможность передвигать
                        }else if(cardActor.getFamilyType() == CardFamily.UPGRAGE){
                            ((Upgrade)cardActor).dropDo(((FieldCellActor) getActor()).getBuilding());
                        }
                        payload.getDragActor().remove(); //убираем актера со сцены
                    }
                };break;
            case WORKSHOPTARGET:
                target = new DragAndDrop.Target(actor) {
                    public boolean drag (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                        getActor().setColor(Color.GREEN);
                        return true;
                    }
                    public void reset (DragAndDrop.Source source, DragAndDrop.Payload payload) {
                        getActor().setColor(Color.WHITE);
                    }
                    public void drop (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                        ((CellActor)getActor()).setPlacedObjImg(skin,((CardActor) payload.getDragActor()).getDrawableName());//устанавливаем картинку поставленной картинки
                        ((CardActor)payload.getDragActor()).remove();// убираем актера со сцены
                        ((CellActor)getActor()).setBuildingCardActor((CardActor) payload.getDragActor());//устанавливаем карту
                        Singleton.getCardsInCraftingSlots().add((CardActor) payload.getDragActor());//добавляем карту в крафт
                        Singleton.getDragAndDrop().addSource(((CellActor)getActor()).getSource());//добавляем возможность передвигать
                        Singleton.getDragAndDrop().removeTarget(this);//убираем возможность ставить на клетку карту
                    }
                }; break;
        }
        return target;
    }
}
