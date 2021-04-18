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
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Null;

import java.util.ArrayList;


public class MotherBoardCard extends ApplicationAdapter {
	SpriteBatch batch;
//	Texture texture, texture2;
//	Image image,image2;
	Stage stage;
	ImageButton button;
	Label label;
	int count=0;
	Table table;
	DragAndDrop dragAndDrop;
	Skin skin;
	@Override
	public void create () {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);//Добавляем обработку нажатии stage


		skin = new Skin();

		skin.add("default", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		skin.add("badlogic", new Texture("heart.png"));


		dragAndDrop = new DragAndDrop();



		TextureRegion textureRegion = new TextureRegion(new Texture("heart.png"));
		TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion);
		button = new ImageButton(textureRegionDrawable);

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


		table = new Table();
		table.setFillParent(true);
		table.setDebug(true);

		table.row();
		makeField();
		table.row();
		makeHand();

		stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
		stage.getViewport().apply();
		stage.addActor(table);

	}

	private void makeHand() {
		ArrayList<Image> cards = new ArrayList<>();
		cards.add(new Image(skin, "badlogic"));
		cards.add(new Image(skin, "badlogic"));
		cards.add(new Image(skin, "badlogic"));
		Table hand = new Table();

		for (final Image card :cards) {
			dragAndDrop.addSource(new DragAndDrop.Source(card) {
				@Null
				public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
					DragAndDrop.Payload payload = new DragAndDrop.Payload();
					payload.setObject(card);

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
			dragAndDrop.setDragActorPosition(card.getWidth() / 2-50, -card.getHeight() / 2+50);//TODO поставить курсор в центр карты

			hand.add(card);
		}

		table.add(hand).colspan(2);//TODO добавить руку
	}

	private void makeField() {
		Table field = new Table();
		field.setDebug(true);
		Image[][] cells = new Image[5][5];//TODO добавить новый класс клетка???
		for (int i = 0; i < cells.length ; i++) {
			for (int j = 0; j <cells[i].length ; j++) {
				cells[i][j] = new Image(skin, "badlogic");
				field.add(cells[i][j]);
				dragAndDrop.addTarget(new DragAndDrop.Target(cells[i][j]) {
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
			}
			field.row();
		}
		table.add(field);
//		dragAndDrop.addTarget(new DragAndDrop.Target(invalidTargetImage) {
//			public boolean drag (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
//				getActor().setColor(Color.RED);
//				return false;
//			}
//
//			public void reset (DragAndDrop.Source source, DragAndDrop.Payload payload) {
//				getActor().setColor(Color.WHITE);
//			}
//
//			public void drop (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
//			}
//		});
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
