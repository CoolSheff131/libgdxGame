package com.mygdx.game.gamescreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.FontManager;
import com.mygdx.game.TextureLoader;

import java.util.ArrayList;

public class Dialog extends Group {
    private Image background,speakerImage;
    private ArrayList<String> text;
    private Label textlabel,speakerName;
    private Button next,prev;
    private int currentSay;
    public Dialog(Characters speaker, final ArrayList<String> text,final int width,final int height){

        this.text = text;
        if(text.size() == 0){
            setVisible(false);
            return;
        }

        currentSay = 0;
        background = new Image(TextureLoader.getSkin(),"bgnDialog");
        speakerName = new Label(speaker.toString(),new Label.LabelStyle(FontManager.getFontJoystix(40), Color.WHITE));
        textlabel = new Label(text.get(0),new Label.LabelStyle(FontManager.getFontJoystix(20), Color.WHITE));
        speakerImage = new Image(TextureLoader.getDrawable(speaker));

        next = new Button();
        prev = new Button();
        prev.setStyle(new Button.ButtonStyle());
        prev.getStyle().up = TextureLoader.getDrawable("left");
        prev.getStyle().down = TextureLoader.getDrawable("leftPressed");
        next.setStyle(new Button.ButtonStyle());
        next.getStyle().up = TextureLoader.getDrawable("right");
        next.getStyle().down = TextureLoader.getDrawable("rightPressed");
        prev.setTouchable(Touchable.disabled);
        prev.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(currentSay>0){
                    currentSay--;
                    textlabel.setText(text.get(currentSay));
                    next.setTouchable(Touchable.enabled);

                    next.getStyle().up = TextureLoader.getDrawable("right");
                    if(currentSay==0)
                        prev.setTouchable(Touchable.disabled);
                }else{
                    prev.setTouchable(Touchable.disabled);
                }


                return super.touchDown(event, x, y, pointer, button);
            }
        });
        next.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(currentSay == text.size() - 1)
                    setVisible(false);
                if(currentSay< text.size() - 1){

                    currentSay++;
                    textlabel.setText(text.get(currentSay));
                    if(currentSay == text.size() - 1)
                    next.getStyle().up = TextureLoader.getDrawable("exit");
                    prev.setTouchable(Touchable.enabled);
                }else{
                    next.setTouchable(Touchable.disabled);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        background.setBounds(0,0,width,height/5);
        prev.setBounds(0,0,height/5,height/5);
        next.setBounds(width-height/5,0,height/5,height/5);
        textlabel.setPosition(width/2-textlabel.getWidth()/2,height/20);
        speakerImage.setBounds(width/3,height/5,width/3,height/2);
        speakerName.setPosition(width/2-speakerName.getWidth()/2,height/7);
        addActor(background);
        addActor(textlabel);
        addActor(speakerImage);
        addActor(speakerName);
        addActor(next);
        addActor(prev);
    }
}
