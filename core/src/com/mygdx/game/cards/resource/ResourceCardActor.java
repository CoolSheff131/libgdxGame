package com.mygdx.game.cards.resource;

import com.mygdx.game.Items;
import com.mygdx.game.cards.CardActor;

public class ResourceCardActor extends CardActor{
    private Items buildingCardTypes;
    public ResourceCardActor(){
        super("res");

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
