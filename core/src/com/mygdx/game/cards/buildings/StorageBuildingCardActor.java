package com.mygdx.game.cards.buildings;

import com.mygdx.game.cards.CardActor;

public class StorageBuildingCardActor extends CardActor {
    public StorageBuildingCardActor() {
        super("stor");
    }

    @Override
    public String getDrawableName() {
        return "stor";
    }
}
