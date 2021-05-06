package com.mygdx.game.gamescreen.cards.resource;

import com.mygdx.game.gamescreen.cards.Items;

public class ResourceCardActor extends Crafting{

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
