package com.mygdx.game.gamescreen.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.FontManager;
import com.mygdx.game.TextureLoader;
import com.mygdx.game.gamescreen.GameScreen;
import com.mygdx.game.gamescreen.IDragAndDrop;
import com.mygdx.game.gamescreen.Singleton;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

public abstract class CardActor extends Group implements  IDragAndDrop {
    private DragAndDrop.Source source;
    private Label lblName,lblDescription;
    private Image coem, img;

    public CardActor(String drawableName,String name,String description) {
        super();
        setWidth(GameScreen.WIDTH_SCREEN/20);
        setHeight(GameScreen.WIDTH_SCREEN/20);
        img = new Image(TextureLoader.getSkin(),drawableName);

        lblName =  new Label(name,new Label.LabelStyle(FontManager.getFontJoystix(5), Color.WHITE));
        lblDescription =  new Label(description,new Label.LabelStyle(FontManager.getFontJoystix(5), Color.WHITE));
        coem = new Image(TextureLoader.getSkin(),"coem");

        img.setBounds(0,getWidth()/2,getWidth()-getWidth()/10,getHeight()/2-getHeight()/10);
        lblName.setPosition(getWidth()/2-lblName.getWidth()/2,getHeight()/3);
        lblDescription.setPosition(getWidth()/2-lblDescription.getWidth()/2,getHeight()/4);
        coem.setBounds(0,0,getWidth(),getWidth());

        addActor(coem);
        addActor(img);
        addActor(lblName);
        addActor(lblDescription);
        addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                addAction(scaleTo(2,2,0.2f));
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                addAction(scaleTo(1,1,0.2f));
                super.exit(event, x, y, pointer, toActor);
            }
        });
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

    @Override
    public DragAndDrop.Payload dragStart() {
        Singleton.getDragAndDrop().setDragActorPosition(GameScreen.WIDTH_SCREEN/20,GameScreen.WIDTH_SCREEN/20);
        DragAndDrop.Payload payload = new DragAndDrop.Payload();
        payload.setObject(this);
        payload.setDragActor(this);
        switch (getFamilyType()){
            case BUILDING:
            case UPGRAGE:
                Singleton.getBuildingCards().remove(this);
                for (int c = 0; c < Singleton.getBuild().getCards().size(); c++) {
                    Singleton.getBuild().getCards().get(c).addAction(moveTo(getWidth() * c, 0, 0.1f));
                }
                break;
            case CRAFTING:
                Singleton.getCraftingCards().remove(this);
                for (int c = 0; c < Singleton.getCraft().getCards().size(); c++) {
                    Singleton.getCraft().getCards().get(c).addAction(moveTo(getWidth() * c,0, 0.1f));
                }
                break;
        }
        return payload;
    }

    @Override
    public void noTargetAction(DragAndDrop.Payload payload) {

        switch (((CardActor) payload.getDragActor()).getFamilyType()) {
            case BUILDING:
            case UPGRAGE:
                Singleton.getBuild().getCards().add(((CardActor) payload.getDragActor()));
                ((CardActor) payload.getDragActor()).addAction(moveTo(getWidth() * (Singleton.getBuild().getCards().size() - 1), 0, 0.1f));
                break;
            case CRAFTING:
                Singleton.getCraft().getCards().add(((CardActor) payload.getDragActor()));
                ((CardActor) payload.getDragActor()).addAction(moveTo(getWidth()* (Singleton.getCraft().getCards().size() - 1), 0, 0.1f));
                break;
        }
    }
}
