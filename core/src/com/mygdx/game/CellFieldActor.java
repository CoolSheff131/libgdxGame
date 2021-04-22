package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.cards.buildings.BuildingCardActor;

public class CellFieldActor extends Image {
    private BuildingCardActor buildingCardActor;
    public CellFieldActor(Skin skin, String drawableName){
        super(skin, drawableName);
    }

    public void setBuildingCardActor(BuildingCardActor buildingCardActor) {
        this.buildingCardActor = buildingCardActor;
    }
    public void removeCartActor(){
        buildingCardActor = null;
    }
    public BuildingCardActor getBuildingCardActor() {
        return buildingCardActor;
    }
}
