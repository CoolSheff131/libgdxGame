package com.mygdx.game.gamescreen.craft;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.MediaPlayer;
import com.mygdx.game.TextureLoader;
import com.mygdx.game.gamescreen.cards.Items;

import java.util.ArrayList;

public class CraftingBook extends Group {
    private Table craft,cards;
    private Button next,prev,back;
    private Button[] cardsBtns;

    private int page, pageSize;
    private ArrayList<Recipe> recipesPage;
    private Image[]  craftingImage;

    public CraftingBook(int width,int height){
        page = 0;
        craft = new Table();
        cards = new Table();
        next = new Button();
        prev = new Button();
        back = new Button();
        Image menuPanel = new Image( new Texture("sprites/menuPanel.png"));
        recipesPage = new ArrayList<>();
        craftingImage = new Image[3];
        cardsBtns = new Button[4];

        menuPanel.setBounds(0,0,width,height);
        addActor(menuPanel);
        prev.setStyle(new Button.ButtonStyle());
        prev.getStyle().up = TextureLoader.getDrawable("left");
        prev.getStyle().down = TextureLoader.getDrawable("leftPressed");


        back.setStyle(new Button.ButtonStyle());
        back.getStyle().up = TextureLoader.getDrawable("exit");
        back.getStyle().down = TextureLoader.getDrawable("exitPressed");

        next.setStyle(new Button.ButtonStyle());
        next.getStyle().up = TextureLoader.getDrawable("right");
        next.getStyle().down = TextureLoader.getDrawable("rightPressed");


        prev.setBounds(0,0,width/3,height/5);
        back.setBounds(width/3,0,width/3,height/5);
        next.setBounds(width/3*2,0,width/3,height/5);

        cards.setBounds(0,height/5,width,height/5*2);
        craft.setBounds(0,height/5*3,width,height/5*2);

        for (int i = 0; i < cardsBtns.length ; i++) {
            cardsBtns[i] = new Button();
            cardsBtns[i].setStyle(new Button.ButtonStyle());
            cardsBtns[i].getStyle().up = TextureLoader.getDrawable("craftCell");
            final int finalI = i;
            cardsBtns[i].addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    MediaPlayer.playBtn();
                    showCraft(finalI);
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            if(i%2==0)
                cards.row();
            cards.add(cardsBtns[i]).grow();
        }
        for (int i = 0; i < craftingImage.length ; i++) {
            craftingImage[i] = new Image();
            craftingImage[i].setDrawable(TextureLoader.getDrawable("craftCell"));
            craft.add(craftingImage[i]).grow();
        }
        pageSize = 4;
        next.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MediaPlayer.playBtn();
                clearCraft();
                loadCrafts(true);
                checkBtns();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        prev.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MediaPlayer.playBtn();
                clearCraft();
                loadCrafts(false);
                checkBtns();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        back.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MediaPlayer.playBtn();
                setVisible(false);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        addActor(next);
        addActor(prev);
        addActor(back);
        addActor(craft);
        addActor(cards);
        checkBtns();
        page--;
        loadCrafts(true);
        setVisible(false);
    }
    public void show(){
        setVisible(true);
    }
    private void showCraft(int i){
        if(pageSize*page + i < CraftingSystem.getRecipes().size()) {//проверка на выход за границу массива рецептов
            clearCraft();
            Recipe recipe = recipesPage.get(i);
            Items[] craft = recipe.getRecipe();

            cardsBtns[i].setColor(Color.GRAY);//помечаем выбранную карту
            for (int j = 0; j < craft.length; j++) {//Загружаем рецепт в ячейки
                craftingImage[j].setDrawable(TextureLoader.getDrawable(craft[j]));
            }
        }
    }
    private void clearCraft(){
        for (Button card : cardsBtns) {//очищаем все карты
            card.setColor(Color.WHITE);
        }
        for (int j = 0; j < craftingImage.length; j++) {//Загружаем рецепт в ячейки
            craftingImage[j].setDrawable(TextureLoader.getDrawable("craftCell"));
        }
    }
    private void checkBtns(){
        if(page<=0)
            prev.setTouchable(Touchable.disabled);
        else
            prev.setTouchable(Touchable.enabled);
        if(page >= CraftingSystem.getRecipes().size()/4)
            next.setTouchable(Touchable.disabled);
        else
            next.setTouchable(Touchable.enabled);
    }
    private void loadCrafts(boolean next){
        recipesPage.clear();
        for (Button cell: cardsBtns) {
            cell.getStyle().up = TextureLoader.getDrawable("craftCell");;
        }
        if(next && page < CraftingSystem.getRecipes().size() /4){
            page++;
        }else if(!next && page>0){
            page--;
        }
        for (int i = 0; i < pageSize && pageSize*page + i < CraftingSystem.getRecipes().size() ; i++) {
            recipesPage.add(CraftingSystem.getRecipes().get(pageSize*page + i));
        }
        for (int i = 0;i<recipesPage.size();i++ ) {
            Recipe recipe= recipesPage.get(i);
            Items result =  recipe.getOut();
            Drawable drawableResult = TextureLoader.getDrawable(result);
            cardsBtns[i].getStyle().up = (drawableResult);
        }
    }
}
