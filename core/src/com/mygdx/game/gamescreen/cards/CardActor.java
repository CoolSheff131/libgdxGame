package com.mygdx.game.gamescreen.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.MainMenuScreen;

import static com.mygdx.game.gamescreen.GameScreen.CARD_WIDTH;
import static com.mygdx.game.gamescreen.GameScreen.skin;

public abstract class CardActor extends Group implements CellOrCard {
    @Override
    public int CellOrCard() {
        return 0;
    }

    private DragAndDrop.Source source;
    private Label lblName,lblDescription;
    private Image coem, img;

    public CardActor(String drawableName,String name) {
        super();//MotherBoardCard.skin, drawableName
        img = new Image(skin,drawableName);

        lblName =  new Label(name,new Label.LabelStyle(MainMenuScreen.smallFont, Color.WHITE));
        lblDescription =  new Label("Description",new Label.LabelStyle(MainMenuScreen.smallFont, Color.WHITE));
        coem = new Image(skin,"coem");

        img.setBounds(0,CARD_WIDTH/2,CARD_WIDTH-CARD_WIDTH/10,CARD_WIDTH/2-CARD_WIDTH/10);
        lblName.setPosition(CARD_WIDTH/2-lblName.getWidth()/2,CARD_WIDTH/2);
        lblDescription.setPosition(CARD_WIDTH/2-lblDescription.getWidth()/2,CARD_WIDTH/2-CARD_WIDTH/3);
        coem.setBounds(0,0,CARD_WIDTH,CARD_WIDTH);

        addActor(coem);
        addActor(img);
        addActor(lblName);
        addActor(lblDescription);

    }

    public void setSource(DragAndDrop.Source source) {
        this.source = source;
    }

    public DragAndDrop.Source getSource() {
        return source;
    }

    public abstract String getDrawableName();

    public abstract Items getItemType();

    public abstract Family getFamilyType();
}
