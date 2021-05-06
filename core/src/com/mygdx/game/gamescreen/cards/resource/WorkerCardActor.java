package com.mygdx.game.gamescreen.cards.resource;

import com.mygdx.game.gamescreen.cards.Items;

public class WorkerCardActor extends Crafting{
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
