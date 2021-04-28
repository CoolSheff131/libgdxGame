package com.mygdx.game.cards.resource;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.Items;
import com.mygdx.game.cards.CardActor;
import com.mygdx.game.cards.Factory;

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
