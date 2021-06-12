package com.mygdx.game.gamescreen;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.TextureLoader;
import com.mygdx.game.gamescreen.cards.CardActor;

import java.util.ArrayList;

public class Hand extends Group {
    private ArrayList<CardActor> cards;
    private int size;
    private Image img;
    public Hand(int size){
        this.size = size;
        cards = new ArrayList<>();
        img = new Image(TextureLoader.getSkin(),"BuildR");

        addActor(img);
    }

    public ArrayList<CardActor> getCards() {
        return cards;
    }

    public void addCard(CardActor card ){
        if(cards.size() <= size){
            Singleton.getDragAndDrop().addSource(card.getSource());
            addActor(card);
            cards.add(card);
            card.setPosition(card.getWidth() * (cards.size() - 1),0);
        }
    }
    public void clearHand(){
        for(int i = 0; i< cards.size();i++){
            System.out.println(i + " building removed");
            removeCard(cards.get(i));
            i--;
        }
    }
    public void removeCard(CardActor buildingCardActor){
        cards.remove(buildingCardActor);
        buildingCardActor.remove();
    }
}
