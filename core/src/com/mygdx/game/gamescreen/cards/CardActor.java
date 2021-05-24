package com.mygdx.game.gamescreen.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.MainMenuScreen;
import com.mygdx.game.gamescreen.IDragAndDrop;
import com.mygdx.game.gamescreen.Singleton;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.mygdx.game.gamescreen.GameScreen.CARD_WIDTH;
import static com.mygdx.game.gamescreen.GameScreen.HEIGHT_CRAFTING_HAND;
import static com.mygdx.game.gamescreen.GameScreen.PADDING;
import static com.mygdx.game.gamescreen.GameScreen.WIDTH_BUTTON;
import static com.mygdx.game.gamescreen.GameScreen.skin;

public abstract class CardActor extends Group implements  IDragAndDrop {
    @Override
    public DragAndDrop.Payload dragStart() {
        DragAndDrop.Payload payload = new DragAndDrop.Payload();
        payload.setObject(this);
        payload.setDragActor(this);
        switch (getFamilyType()){
            case BUILDING:
            case UPGRAGE:
                Singleton.getBuildingCards().remove(this);
                for (int c = 0; c < Singleton.getBuildingCards().size(); c++) {
                    Singleton.getBuildingCards().get(c).addAction(moveTo(WIDTH_BUTTON + PADDING + CARD_WIDTH * c, 0, 0.1f));
                }
                break;
            case CRAFTING:
                Singleton.getCraftingCards().remove(this);
                for (int c = 0; c < Singleton.getCraftingCards().size(); c++) {
                    Singleton.getCraftingCards().get(c).addAction(moveTo(CARD_WIDTH * c, HEIGHT_CRAFTING_HAND, 0.1f));
                }
                break;
        }
        return payload;
    }

    @Override
    public void dragStop(DragAndDrop.Payload payload) {

        switch (((CardActor) payload.getDragActor()).getFamilyType()) {
            case BUILDING:
            case UPGRAGE:
                Singleton.getBuildingCards().add(((CardActor) payload.getDragActor()));
                ((CardActor) payload.getDragActor()).addAction(moveTo(WIDTH_BUTTON + PADDING + CARD_WIDTH * (Singleton.getBuildingCards().size() - 1), 0, 0.1f));
                break;
            case CRAFTING:
                Singleton.getCraftingCards().add(((CardActor) payload.getDragActor()));
                ((CardActor) payload.getDragActor()).addAction(moveTo(CARD_WIDTH * (Singleton.getCraftingCards().size() - 1), HEIGHT_CRAFTING_HAND, 0.1f));
                break;
        }
    }

    @Override
    public boolean canDrop() {
        return true;
    }

    @Override
    public void drop() {

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

    public abstract CardFamily getFamilyType();


}
