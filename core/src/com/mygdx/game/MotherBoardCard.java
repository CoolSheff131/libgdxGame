package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureArray;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Null;


public class MotherBoardCard extends ApplicationAdapter {
	SpriteBatch batch;
//	Texture texture, texture2;
//	Image image,image2;
	Stage stage;

	ImageButton button;
	Label label;
	int count=0;
	@Override
	public void create () {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		final Skin skin = new Skin();
		skin.add("default", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		skin.add("badlogic", new Texture("heart.png"));

		final Image sourceImage = new Image(skin, "badlogic");
		sourceImage.setBounds(300, 10, 100, 100);
		stage.addActor(sourceImage);

		Image validTargetImage = new Image(skin, "badlogic");
		validTargetImage.setBounds(100, 200, 100, 100);
		stage.addActor(validTargetImage);

		Image invalidTargetImage = new Image(skin, "badlogic");
		invalidTargetImage.setBounds(300, 200, 100, 100);
		stage.addActor(invalidTargetImage);

		DragAndDrop dragAndDrop = new DragAndDrop();
		dragAndDrop.addSource(new DragAndDrop.Source(sourceImage) {
			@Null
			public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
				DragAndDrop.Payload payload = new DragAndDrop.Payload();
				payload.setObject(sourceImage);

				payload.setDragActor(getActor());

				//Label validLabel = new Label("Some payload!", skin);
				//validLabel.setColor(0, 1, 0, 1);
				//payload.setValidDragActor(validLabel);

				//Label invalidLabel = new Label("Some payload!", skin);
				//invalidLabel.setColor(1, 0, 0, 1);
				//payload.setInvalidDragActor(invalidLabel);

				return payload;
			}

			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
				if(target==null)
					((Image)payload.getObject()).setPosition(300,10);

			}
		});
		dragAndDrop.addTarget(new DragAndDrop.Target(validTargetImage) {
			public boolean drag (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
				getActor().setColor(Color.GREEN);
				return true;
			}

			public void reset (DragAndDrop.Source source, DragAndDrop.Payload payload) {
				getActor().setColor(Color.WHITE);
			}

			public void drop (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
				System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);
			}
		});
		dragAndDrop.addTarget(new DragAndDrop.Target(invalidTargetImage) {
			public boolean drag (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
				getActor().setColor(Color.RED);
				return false;
			}

			public void reset (DragAndDrop.Source source, DragAndDrop.Payload payload) {
				getActor().setColor(Color.WHITE);
			}

			public void drop (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
			}
		});

		TextureRegion textureRegion = new TextureRegion(new Texture("heart.png"));
		TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion);
		button = new ImageButton(textureRegionDrawable);
		button.setBounds(300,300,100,100);

		stage.addActor(button);
		button.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("Нажато");
				count++;
				label.setText("You pressed this button "+count+" times");
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		label = new Label("You pressed this button "+count+" times",new Label.LabelStyle(new BitmapFont(),Color.BROWN));
		stage.addActor(label);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//		batch.begin();
//		batch.draw(texture,10,10);
//		batch.draw(texture2,50,10);
//		batch.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	@Override
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	@Override
	public void dispose () {
		batch.dispose();
		stage.dispose();
	}
}
