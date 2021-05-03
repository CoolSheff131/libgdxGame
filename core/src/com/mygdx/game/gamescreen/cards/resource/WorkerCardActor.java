package com.mygdx.game.gamescreen.cards.resource;

import com.mygdx.game.gamescreen.Items;
import com.mygdx.game.gamescreen.cards.CardActor;

public class WorkerCardActor extends CardActor {
    public WorkerCardActor() {
        super("worker","worker");
    }

    @Override
    public String getDrawableName() {
        return "worker";
    }

    @Override
    public Items getItemType() {
        return Items.WORKER_CARD;
    }
}
