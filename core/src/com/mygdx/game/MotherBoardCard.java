package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MotherBoardCard extends ApplicationAdapter {
	SpriteBatch batch;
//	Texture texture, texture2;
//	Image image,image2;
	Stage stage;
	ImageButton toWorkshop;
	Label label;
	int count=0;

	DragAndDrop dragAndDropField,dragAndDropWorkshop;
	Skin skin;
	OrthographicCamera camera;
	int WIDTH_SCREEN, HEIGHT_SCREEN;
	final int FIELD_SIZE = 5
			,CRAFTING_SIZE = 3;
	int HEIGHT_HAND,HEIGH_FIELD,HEIGHT_INFO,PADDING=20,WIDTH_BUTTON,WIDTH_HAND;
	boolean inFields;
	final int CARD_WIDTH = 200
			,CELL_WIDTH = 100;
	ShapeRenderer shapeRenderer;
	ImageButton toFields;
	Group hand,info;
	Table field;
	Group workshop;
	@Override
	public void create () {
		WIDTH_SCREEN =Gdx.graphics.getWidth();
		HEIGHT_SCREEN =Gdx.graphics.getHeight();
		HEIGHT_HAND= HEIGHT_SCREEN /6;
		HEIGH_FIELD= HEIGHT_SCREEN /3*2;
		HEIGHT_INFO= HEIGHT_SCREEN /10;
		PADDING=20;
		WIDTH_BUTTON = WIDTH_SCREEN /9;
		WIDTH_HAND= WIDTH_SCREEN /9*8;
		inFields=true;
		camera = new OrthographicCamera();
		camera.position.set(WIDTH_SCREEN / 2, HEIGHT_SCREEN / 2, 0);

		ExtendViewport viewp = new ExtendViewport(WIDTH_SCREEN, HEIGHT_SCREEN,camera);
		stage = new Stage(viewp);
		Gdx.input.setInputProcessor(stage);//Добавляем обработку нажатии stage



		skin = new Skin();
		skin.add("default", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		skin.add("badlogic", new Texture("heart.png"));
		dragAndDropField = new DragAndDrop();
		dragAndDropWorkshop = new DragAndDrop();
		TextureRegion textureRegion = new TextureRegion(new Texture("heart.png"));
		TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion);
		toWorkshop = new ImageButton(textureRegionDrawable);

		toWorkshop.setSize(WIDTH_BUTTON,WIDTH_BUTTON);

		toFields = new ImageButton(textureRegionDrawable);
		toFields.setSize(WIDTH_BUTTON,WIDTH_BUTTON);
		toFields.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("Нажато");
				count++;
				field.setDebug(!field.getDebug());
				//label.setText("You pressed this button "+count+" times");
				//camera.position.set(WIDTH_SCREEN / 2, HEIGHT_SCREEN / 2, 0);

				return super.touchDown(event, x, y, pointer, button);
			}
		});
		toWorkshop.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("Нажато");
				count++;
				field.setDebug(!field.getDebug());
				//label.setText("You pressed this button "+count+" times");
				//camera.position.set(-WIDTH_SCREEN / 2, HEIGHT_SCREEN / 2, 0);
				if(inFields){
					field.addAction(moveBy(WIDTH_SCREEN,0,1f));
					workshop.addAction(moveBy(WIDTH_SCREEN,0,1f));
					inFields = !inFields;
				}
				else{
					field.addAction(moveBy(-WIDTH_SCREEN,0,1f));
					workshop.addAction(moveBy(-WIDTH_SCREEN,0,1f));
					inFields = !inFields;
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		//label = new Label("You pressed this button "+count+" times",new Label.LabelStyle(new BitmapFont(),Color.BROWN));

		stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
		stage.getViewport().apply();

		field = makeField();
//		field.setPosition(Gdx.graphics.getWidth()/3-field.getWidth()/2,Gdx.graphics.getHeight()*2/3-field.getHeight()/2);
		hand = makeHand();
		info = makeInfo();
		workshop = makeWorkshop();
		placeObejcts();
		stage.addActor(field);
		stage.addActor(hand);
		stage.addActor(toWorkshop);
		stage.addActor(toFields);
		stage.addActor(workshop);
		shapeRenderer = new ShapeRenderer();
	}

	private Group makeWorkshop() {
		Group workshopGroup = new Group();
		Table craftingTable = new Table();
		Image[] craftingCells = new Image[CRAFTING_SIZE];//TODO добавить новый класс клетка крафта???
		for (int i = 0; i <craftingCells.length ; i++) {
			craftingCells[i] = new Image(skin, "badlogic");//TODO добавить текстуру клетки крафта
			craftingTable.add(craftingCells[i]);
			dragAndDropWorkshop.addTarget(new DragAndDrop.Target(craftingCells[i]) {
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
		final ArrayList<Image> craftingCards = new ArrayList<>();
		craftingCards.add(new Image(skin, "badlogic"));
		craftingCards.add(new Image(skin, "badlogic"));
		craftingCards.add(new Image(skin, "badlogic"));
		Group craftingHand = new Group();
		for (int i =0; i<craftingCards.size();i++) {
			final Image craftingCard = craftingCards.get(i);
			DragAndDrop.Source source = new DragAndDrop.Source(craftingCard) {
				@Null
				public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
					DragAndDrop.Payload payload = new DragAndDrop.Payload();
					payload.setObject(craftingCard);
					payload.setDragActor(getActor());
					craftingCards.remove(craftingCard);
					return payload;
				}

				@Override
				public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
					if(target==null) {
						((Image) payload.getObject()).setPosition(0,0);//TODO сделать возвращение в руку карты
						//cards.add(((Image) payload.getObject()));
						//group.addActor((Image) payload.getObject());
					}
				}
			};

			dragAndDropWorkshop.addSource(source);

			craftingCard.setBounds(CARD_WIDTH * i,0,CARD_WIDTH,CARD_WIDTH);
			craftingHand.addActor(craftingCard);

		}
		dragAndDropWorkshop.setDragActorPosition(100, -300);
		craftingTable.setDebug(true);

		workshopGroup.addActor(craftingTable);
		craftingTable.setBounds(0,HEIGH_FIELD/3,WIDTH_SCREEN,HEIGH_FIELD/3*2);
		craftingHand.setSize(WIDTH_SCREEN,HEIGH_FIELD/3);
		workshopGroup.addActor(craftingHand);
		return workshopGroup;
	}

	private Group makeInfo() {
		Group group = new Group();
		label = new Label("You pressed this button "+count+" times",new Label.LabelStyle(new BitmapFont(),Color.BROWN));
		group.addActor(label);
		return group;
	}

	private void placeObejcts(){
		toWorkshop.setBounds(0,0,WIDTH_BUTTON,HEIGHT_HAND);
		hand.setBounds(WIDTH_BUTTON+PADDING,0,WIDTH_HAND,HEIGHT_HAND);
		field.setBounds(0,HEIGHT_HAND+PADDING, WIDTH_SCREEN,HEIGH_FIELD);
		info.setBounds(0,HEIGHT_HAND+PADDING+HEIGH_FIELD+PADDING, WIDTH_SCREEN,HEIGHT_INFO);
		toFields.setBounds(-WIDTH_SCREEN,0,WIDTH_BUTTON,HEIGHT_HAND);
		workshop.setBounds(-WIDTH_SCREEN,HEIGHT_HAND+PADDING, WIDTH_SCREEN,HEIGH_FIELD);
	}

	private void handleInput() {
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			camera.zoom += 0.02;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
			camera.zoom -= 0.02;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//			if (camera.position.x > 0)
				camera.translate(-3, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//			if (camera.position.x < 1024)
				camera.translate(3, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//			if (camera.position.y > 0)
				camera.translate(0, -3, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
//			if (camera.position.y < 1024)
				camera.translate(0, 3, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			camera.rotate(-1, 0, 0, 1);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.E)) {
			camera.rotate(1, 0, 0, 1);
		}
	}

	private Group makeHand() {
		final ArrayList<Image> cards = new ArrayList<>();
		cards.add(new Image(skin, "badlogic"));
		cards.add(new Image(skin, "badlogic"));
		cards.add(new Image(skin, "badlogic"));
		final Group group = new Group();

		for (int i =0; i<cards.size();i++) {
			final Image card = cards.get(i);
			dragAndDropField.addSource(new DragAndDrop.Source(card) {
				@Null
				public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
					DragAndDrop.Payload payload = new DragAndDrop.Payload();
					payload.setObject(card);

					payload.setDragActor(getActor());
                    cards.remove(card);
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
					if(target==null) {
						((Image) payload.getObject()).setPosition(0,0);//TODO сделать возвращение в руку карты
					    //cards.add(((Image) payload.getObject()));
                        //group.addActor((Image) payload.getObject());
                    }
				}
			});

			card.setBounds(CARD_WIDTH * i,0,CARD_WIDTH,CARD_WIDTH);
			group.addActor(card);
			dragAndDropField.setDragActorPosition(card.getWidth() / 2-200, -card.getHeight() / 2+50);//TODO поставить курсор в центр карты

		}
        return group;

	}

	private Table makeField() {

		Table table = new Table();

		Image[][] cells = new Image[FIELD_SIZE][FIELD_SIZE];//TODO добавить новый класс клетка???
		for (int i = 0; i < cells.length ; i++) {
			for (int j = 0; j <cells[i].length ; j++) {
				cells[i][j] = new Image(skin, "badlogic");//TODO добавить текстуру клетки поля
				cells[i][j].setSize(CELL_WIDTH,CELL_WIDTH);
				table.add(cells[i][j]).size(HEIGH_FIELD/ FIELD_SIZE,HEIGH_FIELD/ FIELD_SIZE);
				dragAndDropField.addTarget(new DragAndDrop.Target(cells[i][j]) {
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
			table.row();
		}
		return table;

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
		handleInput();
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.rect(0,0,WIDTH_BUTTON,HEIGHT_HAND);
		shapeRenderer.rect(WIDTH_BUTTON+PADDING,0,WIDTH_HAND,HEIGHT_HAND);
		shapeRenderer.rect(0,HEIGHT_HAND+PADDING, WIDTH_SCREEN,HEIGH_FIELD);
		shapeRenderer.rect(0,HEIGHT_HAND+PADDING+HEIGH_FIELD+PADDING, WIDTH_SCREEN,HEIGHT_INFO);
		shapeRenderer.end();

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
//		batch.dispose();
		stage.dispose();
	}
}
