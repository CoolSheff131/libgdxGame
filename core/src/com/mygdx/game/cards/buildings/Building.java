package com.mygdx.game.cards.buildings;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.cards.CardActor;

public abstract class Building extends CardActor {
    public Building(String drawableName) {
        super(drawableName);
    }

    public abstract void doThing();
}
