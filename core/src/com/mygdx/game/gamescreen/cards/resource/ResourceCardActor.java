package com.mygdx.game.gamescreen.cards.resource;

import com.mygdx.game.gamescreen.Items;
import com.mygdx.game.gamescreen.cards.CardActor;

public class ResourceCardActor extends CardActor{

    public ResourceCardActor(){
        super("res","res");

    }

    @Override
    public String getDrawableName() {
        return "res";
    }

    @Override
    public Items getItemType() {
        return Items.RESOURSE_CARD;
    }
}
