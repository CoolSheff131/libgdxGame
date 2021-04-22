package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.cards.CardActor;

public class CellActor extends Image {
    private CardActor buildingCardActor;
    public CellActor(Skin skin, String drawableName){
        super(skin, drawableName);
    }

    public void setBuildingCardActor(CardActor buildingCardActor) {
        this.buildingCardActor = buildingCardActor;
    }
    public void removeCartActor(){
        buildingCardActor = null;
    }
    public CardActor getBuildingCardActor() {
        return buildingCardActor;
    }
}
