package com.mygdx.game.cards.resource;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.Items;
import com.mygdx.game.cards.CardActor;

public class SchemeCardActor extends CardActor {
    public SchemeCardActor() {
        super("scheme");
    }

    @Override
    public String getDrawableName() {
        return "scheme";
    }

    @Override
    public Items getItemType() {
        return Items.SCHEME_CARD;
    }
}