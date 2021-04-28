package com.mygdx.game.cards.resource;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.Items;
import com.mygdx.game.cards.CardActor;

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
