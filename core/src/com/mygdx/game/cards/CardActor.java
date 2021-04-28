package com.mygdx.game.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.Items;

import static com.mygdx.game.MotherBoardCard.CARD_WIDTH;
import static com.mygdx.game.MotherBoardCard.skin;

public abstract class CardActor extends Group {

    private DragAndDrop.Source source;
    private Label lblName,lblDescription;
    private Image coem, img;

    public CardActor(String drawableName,String name) {
        super();//MotherBoardCard.skin, drawableName
        img = new Image(skin,drawableName);



        lblName =  new Label(name,new Label.LabelStyle(new BitmapFont(), Color.BROWN));
        lblDescription =  new Label("Description",new Label.LabelStyle(new BitmapFont(), Color.BROWN));
        coem = new Image(skin,"coem");
        img.setBounds(0,CARD_WIDTH/2,CARD_WIDTH,CARD_WIDTH/2);
        lblName.setBounds(0,CARD_WIDTH/2,CARD_WIDTH,CARD_WIDTH/5);

        coem.setBounds(0,0,CARD_WIDTH,CARD_WIDTH);

        addActor(img);
        addActor(lblName);
        addActor(lblDescription);
        addActor(coem);
    }

    public void setSource(DragAndDrop.Source source) {
        this.source = source;
    }

    public DragAndDrop.Source getSource() {
        return source;
    }

    public abstract String getDrawableName();

    public abstract Items getItemType();
}
