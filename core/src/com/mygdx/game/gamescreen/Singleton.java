package com.mygdx.game.gamescreen;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.gamescreen.cards.CardActor;
import java.util.ArrayList;
import static com.mygdx.game.gamescreen.GameScreen.WIDTH_SCREEN;

public class Singleton {

    private static DragAndDrop dragAndDrop = new DragAndDrop();
    private static ArrayList<CardActor> cardsInCraftingSlots = new ArrayList<>();
    private static Stage stage = new Stage();
    private static Group workshop = new Group();
    private static Hand craft = new Hand(9),build = new Hand(9);
    private static int CARD_WIDTH = WIDTH_SCREEN/20;


    public static Hand getCraft() {
        return craft;
    }
    public static Hand getBuild() {
        return build;
    }
    public static Group getWorkshop(){
        return workshop;
    }

    public static void clearHandBuilding(){
        build.clearHand();
    }
    public static void clearHandCrafting(){
        craft.clearHand();
    }
    public static void addcraftingcard(CardActor craftingCardActor) {
        craft.addCard(craftingCardActor);
    }
    public static void addBuildingCard(CardActor buildingCardActor){
        build.addCard(buildingCardActor);
    }


    ///ГЕТЕРЫ
    public static Stage getStage() {
        if(stage == null){
            stage = new Stage();
        }
        return stage;
    }

    public static DragAndDrop getDragAndDrop() {
        return dragAndDrop;
    }


    public static ArrayList<CardActor> getBuildingCards() {
        return build.getCards();
    }

    public static ArrayList<CardActor> getCraftingCards() {
        return craft.getCards();

    }

    public static ArrayList<CardActor> getCardsInCraftingSlots() {
        if(cardsInCraftingSlots == null){
            cardsInCraftingSlots = new ArrayList<>();
        }
        return cardsInCraftingSlots;
    }
}
