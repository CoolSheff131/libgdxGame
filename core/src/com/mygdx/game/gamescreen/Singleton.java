package com.mygdx.game.gamescreen;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.gamescreen.cards.CardActor;

import java.util.ArrayList;

import static com.mygdx.game.gamescreen.GameScreen.CARD_WIDTH;
import static com.mygdx.game.gamescreen.GameScreen.PADDING;
import static com.mygdx.game.gamescreen.GameScreen.WIDTH_BUTTON;

public class Singleton {


    private static DragAndDrop dragAndDrop = new DragAndDrop();
    private static ArrayList<CardActor> buildingCards = new ArrayList<>(), craftingCards = new ArrayList<>(), cardsInCraftingSlots = new ArrayList<>();
    private static Stage stage = new Stage();
    private static Group workshop = new Group();
    private static int MAX_BUILD_CARDS = 9,MAX_CRAFT_CARDS = 9;
    static {
        dragAndDrop.setDragActorPosition(CARD_WIDTH / 2, -CARD_WIDTH/2 );
    }

    public static Group getWorkshop() {
        if(workshop == null){
            workshop = new Group();
        }
        return workshop;
    }
    public static void addcraftingcard(CardActor cardActor){
        if(craftingCards.size() <= MAX_CRAFT_CARDS ){
            dragAndDrop.addSource(cardActor.getSource());
        workshop.addActor(cardActor);
        craftingCards.add(cardActor);
        cardActor.setBounds(CARD_WIDTH * (Singleton.getCraftingCards().size() - 1),CARD_WIDTH+PADDING,CARD_WIDTH,CARD_WIDTH);
        }
    }
    public static void removecraftingcard(CardActor cardActor){
        //DADWorkshop.removeSource(cardActor.getSource());
        craftingCards.remove(cardActor);
        workshop.removeActor(cardActor);
        cardActor.remove();
    }

    public static void clearHandBuilding(){

        for(int i = 0; i< buildingCards.size();i++){
            System.out.println(i + " building removed");
            removeBuildingCard(buildingCards.get(i));
            i--;
        }
    }
    public static void clearHandCrafting(){
        for(int i = 0;i < craftingCards.size();i++){
            System.out.println(i + " crafting removed");
            removecraftingcard(craftingCards.get(i));
            i--;
        }

    }

    public static void addBuildingCard(CardActor buildingCardActor){
        if (buildingCards.size() <= MAX_BUILD_CARDS) {
            buildingCards.add(buildingCardActor);
            dragAndDrop.addSource(buildingCardActor.getSource());
            buildingCardActor.setBounds(WIDTH_BUTTON + PADDING + CARD_WIDTH * (Singleton.getBuildingCards().size() - 1), 0, CARD_WIDTH, CARD_WIDTH);
            stage.addActor(buildingCardActor);
        }
    }
    public static void removeBuildingCard(CardActor buildingCardActor){
        buildingCards.remove(buildingCardActor);
        //DADToField.removeSource(buildingCardActor.getSource());
        buildingCardActor.remove();
    }

    ///ГЕТЕРЫ
    public static Stage getStage() {
        if(stage == null){
            stage = new Stage();
        }
        return stage;
    }

    public static DragAndDrop getDragAndDrop() {
        if(dragAndDrop == null){
            dragAndDrop = new DragAndDrop();
        }
        return dragAndDrop;
    }


    public static ArrayList<CardActor> getBuildingCards() {
        if (buildingCards == null){
            buildingCards = new ArrayList<>();
        }
        return buildingCards;
    }

    public static ArrayList<CardActor> getCraftingCards() {
        if(craftingCards == null ){
            craftingCards = new ArrayList<>();
        }
        return craftingCards;
    }

    public static ArrayList<CardActor> getCardsInCraftingSlots() {
        if(cardsInCraftingSlots == null){
            cardsInCraftingSlots = new ArrayList<>();
        }
        return cardsInCraftingSlots;
    }
}
