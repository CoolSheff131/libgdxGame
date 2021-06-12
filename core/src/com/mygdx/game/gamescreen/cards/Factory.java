package com.mygdx.game.gamescreen.cards;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Null;
import com.mygdx.game.gamescreen.IDragAndDrop;
import com.mygdx.game.gamescreen.Singleton;
import com.mygdx.game.gamescreen.cards.buildings.EnergyBuilding;
import com.mygdx.game.gamescreen.cards.buildings.WorkerBuilding;
import com.mygdx.game.gamescreen.cards.upgrades.ActivateBuild;
import com.mygdx.game.gamescreen.cards.upgrades.DemolishBuild;
import com.mygdx.game.gamescreen.cards.upgrades.QuickBuild;
import com.mygdx.game.gamescreen.cards.buildings.SchemeBuilding;
import com.mygdx.game.gamescreen.cards.buildings.ResourseBuilding;
import com.mygdx.game.gamescreen.cards.resource.ResourceCardActor;
import com.mygdx.game.gamescreen.cards.resource.SchemeCardActor;
import com.mygdx.game.gamescreen.cards.resource.WorkerCardActor;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

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

//                Singleton.getDragAndDrop().setDragActorPosition(0,0);
                return ((IDragAndDrop)getActor()).dragStart();
            }
            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                if (target == null) {
                    ((IDragAndDrop) payload.getObject()).noTargetAction(payload);
                }
            }
        };
    }


}
