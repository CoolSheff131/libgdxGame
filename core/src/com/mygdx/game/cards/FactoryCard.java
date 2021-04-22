package com.mygdx.game.cards;

import com.mygdx.game.cards.buildings.EnergyBuildingCardActor;
import com.mygdx.game.cards.buildings.StorageBuildingCardActor;
import com.mygdx.game.cards.resource.ResourceCardActor;
import com.mygdx.game.Items;
import com.mygdx.game.cards.resource.SchemeCardActor;
import com.mygdx.game.cards.resource.WorkerCardActor;

public class FactoryCard {
    public static CardActor createCard(Items type){
        CardActor cardActor=null;
        switch (type){
            case RESOURSE_CARD: cardActor = new ResourceCardActor(); break;
            case WORKER_CARD:cardActor = new WorkerCardActor();break;
            case SCHEME_CARD:cardActor = new SchemeCardActor();break;
            case ENERGY_BUILDING: cardActor = new EnergyBuildingCardActor(); break;
            case STORAGE_BUILDING:cardActor = new StorageBuildingCardActor(); break;

        }
        return cardActor;
    }

}
