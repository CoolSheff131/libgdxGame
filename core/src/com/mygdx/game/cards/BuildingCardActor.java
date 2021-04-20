package com.mygdx.game.cards;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class BuildingCardActor extends Image {
    private BuildingCardTypes buildingCardTypes;
    public BuildingCardActor(Skin skin, String drawableName, BuildingCardTypes buildingCardTypes){
        super(skin,drawableName);
        this.buildingCardTypes = buildingCardTypes;
    }


}
