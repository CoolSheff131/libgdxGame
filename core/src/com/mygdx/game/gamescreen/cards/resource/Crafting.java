package com.mygdx.game.gamescreen.cards.resource;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.gamescreen.Singleton;
import com.mygdx.game.gamescreen.cards.CardActor;
import com.mygdx.game.gamescreen.cards.CardFamily;



public abstract class Crafting extends CardActor {


    public Crafting(String drawableName, String name) {
        super(drawableName, name);
    }

    @Override
    public CardFamily getFamilyType() {
        return CardFamily.CRAFTING;
    }
}
