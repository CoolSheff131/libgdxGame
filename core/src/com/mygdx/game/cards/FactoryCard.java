package com.mygdx.game.cards;

import com.mygdx.game.cards.CardActor;
import com.mygdx.game.cards.buildings.BuildingCardActor;
import com.mygdx.game.cards.buildings.BuildingCardTypes;
import com.mygdx.game.cards.buildings.EnergyBuildingCardActor;
import com.mygdx.game.cards.buildings.StorageBuildingCardActor;
import com.mygdx.game.cards.resource.ResourceCardActor;
import com.mygdx.game.cards.resource.ResourceCardTypes;
import com.mygdx.game.cards.resource.SchemeCardActor;
import com.mygdx.game.cards.resource.WorkerCardActor;

public class FactoryCard {
    public static CardActor createCard(ResourceCardTypes type){
        CardActor cardActor=null;
        switch (type){
            case RESOURSE_CARD: cardActor = new ResourceCardActor(); break;
            case WORKER_CARD:cardActor = new WorkerCardActor();break;
            case SCHEME_CARD:cardActor = new SchemeCardActor();break;

        }
        return cardActor;
    }
    public static BuildingCardActor createCard(BuildingCardTypes type){
        BuildingCardActor buildingCardActor = null;
        switch (type){
            case ENERGY: buildingCardActor = new EnergyBuildingCardActor(); break;
            case STORAGE:buildingCardActor = new StorageBuildingCardActor(); break;
        }
        return buildingCardActor;
    }
}
