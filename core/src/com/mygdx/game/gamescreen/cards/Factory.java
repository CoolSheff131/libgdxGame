package com.mygdx.game.gamescreen.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Null;
import com.mygdx.game.gamescreen.cards.buildings.EnergyBuilding;
import com.mygdx.game.gamescreen.cells.CellActor;
import com.mygdx.game.gamescreen.cells.FieldCellActor;
import com.mygdx.game.gamescreen.Singleton;
import com.mygdx.game.gamescreen.cards.buildings.Building;
import com.mygdx.game.gamescreen.cards.buildings.SchemeBuilding;
import com.mygdx.game.gamescreen.cards.buildings.ResourseBuilding;
import com.mygdx.game.gamescreen.cards.resource.ResourceCardActor;
import com.mygdx.game.gamescreen.Items;
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
            case RESOURSE_CARD: cardActor = new ResourceCardActor();
                cardActor.setSource(createSource(CraftingCardSource,cardActor));
                break;
            case WORKER_CARD:cardActor = new WorkerCardActor();
                cardActor.setSource(createSource(CraftingCardSource,cardActor));
                break;
            case SCHEME_CARD:cardActor = new SchemeCardActor();
                cardActor.setSource(createSource(CraftingCardSource,cardActor));
                break;

            case RESOURSE_BUILDING:cardActor = new ResourseBuilding();
                cardActor.setSource(createSource(BuildingCardSource,cardActor));
                break;
            case WORKER_BUILDING:cardActor = new WorkerCardActor();
                cardActor.setSource(createSource(BuildingCardSource,cardActor));
                break;
            case SCHEME_BUILDING: cardActor = new SchemeBuilding();
                cardActor.setSource(createSource(BuildingCardSource,cardActor));
                break;
            case ENERGY_BUILDING: cardActor = new EnergyBuilding();
            cardActor.setSource(createSource(BuildingCardSource,cardActor));
                break;

        }
        return cardActor;
    }
    public static final int CRAFTCELLCARDSOURCE = 1;
    public static final int FieldCellCardSource = 2;
    public static final int BuildingCardSource = 3;
    public static final int CraftingCardSource = 4;

    public static DragAndDrop.Source createSource(int type, Actor actor){
        DragAndDrop.Source source = null;
        switch (type){
            case CraftingCardSource:
                source = new DragAndDrop.Source(actor) {
                    @Override
                    public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                        DragAndDrop.Payload payload = new DragAndDrop.Payload();
                        payload.setObject(getActor());
                        payload.setDragActor(getActor());
                        Singleton.getCraftingCards().remove(getActor());
                        System.out.println(Singleton.getCraftingCards().size()+" crafting cards");
                        for (int c = 0;c<Singleton.getCraftingCards().size();c++) {
                            Singleton.getCraftingCards().get(c).addAction(moveTo(CARD_WIDTH * c,HEIGHT_CRAFTING_HAND,0.1f));
                        }
                        return payload;
                    }
                    @Override
                    public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                        if(target==null) {
                            Singleton.getCraftingCards().add(((CardActor) payload.getObject()));
                            ((CardActor) payload.getObject()).addAction(moveTo(CARD_WIDTH*(Singleton.getCraftingCards().size()-1),HEIGHT_CRAFTING_HAND,0.1f));
                        }
                    }
                }; break;
            case BuildingCardSource:
                source = new DragAndDrop.Source(actor) {
                    @Null
                    public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
                        DragAndDrop.Payload payload = new DragAndDrop.Payload();
                        payload.setObject(getActor());
                        payload.setDragActor(getActor());

                        Singleton.getBuildingCards().remove(getActor());


                        System.out.println(Singleton.getBuildingCards().size());
                        for (int c = 0; c < Singleton.getBuildingCards().size(); c++) {
                            Singleton.getBuildingCards().get(c).addAction(moveTo(WIDTH_BUTTON+PADDING+CARD_WIDTH * c,0,0.1f));
                        }
                        return payload;
                    }

                    @Override
                    public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                        if(target==null) {
                            Singleton.getBuildingCards().add(((CardActor) payload.getObject()));
                            ((CardActor) payload.getObject()).addAction(moveTo(WIDTH_BUTTON+PADDING+CARD_WIDTH*(Singleton.getBuildingCards().size()-1),0,0.1f));
                        }

                    }
                }; break;
            case FieldCellCardSource:
                source = new DragAndDrop.Source(actor) {
                    @Override
                    public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                        DragAndDrop.Payload payload = new DragAndDrop.Payload();
                        payload.setObject(getActor());
                        payload.setDragActor(((CellActor)getActor()).getBuildingCardActor());
                        ((CellActor)getActor()).removeCartActor();
                        ((FieldCellActor)getActor()).removeBuilding();//убираем постройку с КЛЕТКИ (актера) поля
                        ((CellActor)getActor()).setDrawable(((CellActor)getActor()).getDrawable());
                        return payload;
                    }
                    @Override
                    public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                        if(target==null) {
                            Singleton.addBuildingCard((CardActor) payload.getDragActor());//отправляем карту назад в руку
                            Singleton.getDADToHand().removeSource(this);
                            Singleton.getDADToField().addTarget(((CellActor)getActor()).getTarget());
                        }
                    }
                }; break;
            case CRAFTCELLCARDSOURCE:
                source = new DragAndDrop.Source(actor) {
                    @Override
                    public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                        DragAndDrop.Payload payload = new DragAndDrop.Payload();
                        payload.setObject(getActor());
                        payload.setDragActor(((CellActor)getActor()).getBuildingCardActor());
                        ((CellActor)getActor()).removeCartActor();
                        Singleton.getCardsInCraftingSlots().remove((CardActor) payload.getDragActor());// убираем карту с крафта
                        ((CellActor)getActor()).setDrawable(((CellActor)getActor()).getDrawable());
                        return payload;
                    }
                    @Override
                    public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                        if(target==null) {
                            Singleton.addcraftingcard((CardActor) payload.getDragActor());//отправляем карту назад в руку
                            Singleton.getDADToWorkshopHand().removeSource(this);				//убираем возможность перетаскивать карту
                            Singleton.getDADWorkshop().addTarget(((CellActor)getActor()).getTarget());// возвращаем возможност ставить карты на клетку
                        }
                    }
                }; break;
        }
        return source;
    }
    public static final int FIELDTARGET = 10;
    public static final int WORKSHOPTARGET = 11;
    public static DragAndDrop.Target createTarget(int type, Actor actor){
        DragAndDrop.Target target = null;
        switch (type){
            case FIELDTARGET:
                target = new DragAndDrop.Target(actor) {
                    public boolean drag (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                        getActor().setColor(Color.GREEN);
                        return true;
                    }

                    public void reset (DragAndDrop.Source source, DragAndDrop.Payload payload) {
                        getActor().setColor(Color.WHITE);
                    }

                    public void drop (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                        ((CellActor)getActor()).setDrawable(skin,((CardActor) payload.getObject()).getDrawableName());//устанавливаем картинку поставленной картинки
                        ((Building) payload.getObject()).setOccupiedCell((FieldCellActor) getActor());
                        ((CardActor) payload.getObject()).remove();										//убираем актера со сцены
                        ((CellActor)getActor()).setBuildingCardActor((CardActor) payload.getObject());	//устанавливаем карту
                        ((FieldCellActor)getActor()).setBuilding((Building) payload.getObject());		//устанавливаем постройку
                        Singleton.getDADToHand().addSource(((CellActor)getActor()).getSource());	//добавляем возможность передвигать
                        Singleton.getDADToField().removeTarget(this);								//убираем возможность ставить на клетку карту
                        System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);	// debag
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
                        ((CellActor)getActor()).setDrawable(skin,((CardActor) payload.getObject()).getDrawableName());//устанавливаем картинку поставленной картинки
                        ((CardActor)payload.getObject()).remove();// убираем актера со сцены
                        ((CellActor)getActor()).setBuildingCardActor((CardActor) payload.getObject());//устанавливаем карту
                        Singleton.getCardsInCraftingSlots().add((CardActor) payload.getObject());//добавляем карту в крафт
                        Singleton.getDADToWorkshopHand().addSource(((CellActor)getActor()).getSource());//добавляем возможность передвигать
                        Singleton.getDADWorkshop().removeTarget(this);//убираем возможность ставить на клетку карту
                        System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);// debag
                    }
                }; break;
        }
        return target;
    }
}
