package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.cards.buildings.BuildingCardActor;
import com.mygdx.game.cards.CardActor;
import com.mygdx.game.cards.buildings.BuildingCardTypes;
import com.mygdx.game.cards.buildings.EnergyBuildingCardActor;
import com.mygdx.game.cards.buildings.StorageBuildingCardActor;
import com.mygdx.game.cards.FactoryCard;
import com.mygdx.game.cards.resource.ResourceCardTypes;

import java.util.ArrayList;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MotherBoardCard extends ApplicationAdapter {
 	public static Skin skin;
	private Stage stage;
	private ImageButton toWorkshopBtn, endTurnBtn,craftBtn, toFields;
	private Label label;
	private DragAndDrop dragAndDropField,dragAndDropWorkshop;
	private Group fieldGroup, workshop;
	private OrthographicCamera camera;
	private int count=0, WIDTH_SCREEN, HEIGHT_SCREEN, HEIGHT_HAND,HEIGH_FIELD,HEIGHT_INFO,PADDING=20,WIDTH_BUTTON,WIDTH_HAND, CARD_WIDTH,CELL_WIDTH = 100;
	private final int FIELD_SIZE = 5,CRAFTING_SIZE = 3;
	private boolean inFields;
	private ShapeRenderer shapeRenderer;
	private Group info;
	private Table field;
	private ArrayList<BuildingCardActor> cards;
	private Container<Image>  craftingRes;
	private final ArrayList<CardActor> craftingCards = new ArrayList<>();
	@Override
	public void create () {
		init();

		setBtnListeners();

		//label = new Label("You pressed this button "+count+" times",new Label.LabelStyle(new BitmapFont(),Color.BROWN));

		setActorsBounds();

		stage.addActor(fieldGroup);

		stage.addActor(toWorkshopBtn);
		stage.addActor(toFields);
		stage.addActor(workshop);
		fieldGroup.addActor(field);
		fieldGroup.addActor(endTurnBtn);

	}
	private void setBtnListeners(){
		toFields.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("Нажато");
				count++;
				field.setDebug(!field.getDebug());
				//label.setText("You pressed this button "+count+" times");
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		endTurnBtn.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("END TURN");
				//TODO добавить завершение хода
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		craftBtn.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("CRAFT!");
				addBuildingCardToHand(FactoryCard.createCard(BuildingCardTypes.STORAGE));//TODO Добавить крафт
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		toWorkshopBtn.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("Нажато");
				count++;
				field.setDebug(!field.getDebug());
				//label.setText("You pressed this button "+count+" times");
				//camera.position.set(-WIDTH_SCREEN / 2, HEIGHT_SCREEN / 2, 0);
				if(inFields){
					fieldGroup.addAction(moveBy(WIDTH_SCREEN,0,1f));
					workshop.addAction(moveBy(WIDTH_SCREEN,0,1f));
					inFields = !inFields;
				}
				else{
					fieldGroup.addAction(moveBy(-WIDTH_SCREEN,0,1f));
					workshop.addAction(moveBy(-WIDTH_SCREEN,0,1f));
					inFields = !inFields;
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}
	private void init(){
		skin = new Skin();
		skin.add("badlogic", new Texture("heart.png"));
		skin.add("res", new Texture("resourceTest.png"));
		skin.add("scheme", new Texture("schemeTest.png"));
		skin.add("worker", new Texture("workerTest.png"));
		skin.add("en", new Texture("EnergyTest.png"));
		skin.add("stor", new Texture("StorageTest.png"));
		WIDTH_SCREEN = Gdx.graphics.getWidth();
		HEIGHT_SCREEN = Gdx.graphics.getHeight();
		HEIGHT_HAND = HEIGHT_SCREEN /6;
		HEIGH_FIELD = HEIGHT_SCREEN /3*2;
		HEIGHT_INFO = HEIGHT_SCREEN /10;
		PADDING = 20;
		WIDTH_BUTTON = WIDTH_SCREEN /9;
		WIDTH_HAND = WIDTH_SCREEN /9*8;
		CARD_WIDTH = WIDTH_SCREEN/10;
		inFields = true;
		camera = new OrthographicCamera();
		camera.position.set(WIDTH_SCREEN / 2, HEIGHT_SCREEN / 2, 0);
		ExtendViewport viewp = new ExtendViewport(WIDTH_SCREEN, HEIGHT_SCREEN,camera);
		stage = new Stage(viewp);
		Gdx.input.setInputProcessor(stage);//Добавляем обработку нажатии stage
		cards = new ArrayList<>();
		dragAndDropField = new DragAndDrop();
		dragAndDropWorkshop = new DragAndDrop();
		TextureRegion textureRegion = new TextureRegion(new Texture("heart.png"));
		TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion);
		toWorkshopBtn = new ImageButton(textureRegionDrawable);
		endTurnBtn = new ImageButton(textureRegionDrawable);
		craftBtn = new ImageButton(textureRegionDrawable);
		toFields = new ImageButton(textureRegionDrawable);
		shapeRenderer = new ShapeRenderer();
		fieldGroup = new Group();

		stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
		stage.getViewport().apply();
		field = makeField();
		makeHand();
		info = makeInfo();
		workshop = new Group();
		makeWorkshop();
	}
	private void addcraftingcard(CardActor cardActor){
		dragAndDropWorkshop.addSource(new CraftingCardSource(cardActor));
		workshop.addActor(cardActor);
		craftingCards.add(cardActor);
		cardActor.setBounds(CARD_WIDTH * (craftingCards.size() - 1),0,CARD_WIDTH,CARD_WIDTH);
	}
	private void makeWorkshop() {


		//Создание поле крафта
		Table craftingTable = new Table();
		Image[] craftingCells = new Image[CRAFTING_SIZE];
		for (int i = 0; i < craftingCells.length ; i++) {//todo создать отдельный класс?? где будет проверяться содержимое клеток и правильность собраной комбинации
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
					System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);//TODo магнитить к клетке
				}
			});
		}

		//Добавление карт в руку

		for (int i =0; i<4;i++) {
			addcraftingcard(FactoryCard.createCard(ResourceCardTypes.RESOURSE_CARD));
		}
		dragAndDropWorkshop.setDragActorPosition(craftingCards.get(0).getWidth()/2, -craftingCards.get(0).getHeight()*3/2);
		craftingTable.setDebug(true);

		craftingRes = new Container<>();
		workshop.addActor(craftingTable);
		workshop.addActor(craftBtn);
		workshop.addActor(craftingRes);
		craftBtn.setBounds(0,HEIGH_FIELD/3,WIDTH_SCREEN/4,HEIGH_FIELD/3*2);
		craftingTable.setBounds(WIDTH_SCREEN/4,HEIGH_FIELD/3,WIDTH_SCREEN/4*2,HEIGH_FIELD/3*2);
		craftingRes.setBounds(WIDTH_SCREEN/4*3,HEIGH_FIELD/3,WIDTH_SCREEN/4,HEIGH_FIELD/3*2);
	}
	private class CraftingCardSource extends DragAndDrop.Source{

		public CraftingCardSource(Actor actor) {
			super(actor);
		}

		@Override
		public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
			DragAndDrop.Payload payload = new DragAndDrop.Payload();
			payload.setObject(getActor());
			payload.setDragActor(getActor());
			craftingCards.remove(getActor());
			System.out.println(craftingCards.size()+" crafting cards");
			for (int c = 0;c<craftingCards.size();c++) {
				craftingCards.get(c).addAction(moveTo(CARD_WIDTH * c,0,0.1f));
			}
			return payload;
		}
		@Override
		public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
			if(target==null) {

				craftingCards.add(((CardActor) payload.getObject()));
				((CardActor) payload.getObject()).addAction(moveTo(CARD_WIDTH*(craftingCards.size()-1),0,0.1f));
				//cards.add(((Image) payload.getObject()));
				//group.addActor((Image) payload.getObject());
			}else{
				//((CardActor) payload.getObject()).addAction(moveTo(target.getActor().getX(),target.getActor().getOriginY(),0.1f));TODO привязать к клетке
			}
		}

	}
	private class FieldCardSource extends  DragAndDrop.Source{

		public FieldCardSource(Actor actor) {
			super(actor);
		}

		@Null
		public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
			DragAndDrop.Payload payload = new DragAndDrop.Payload();
			payload.setObject(getActor());

			payload.setDragActor(getActor());
			cards.remove(getActor());
			System.out.println(cards.size());
			for (int c = 0;c<cards.size();c++) {
				//cards.get(c).setPosition(CARD_WIDTH * c,0);
				cards.get(c).addAction(moveTo(WIDTH_BUTTON+PADDING+CARD_WIDTH * c,0,0.1f));
			}
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
				cards.add(((BuildingCardActor) payload.getObject()));
				//((BuildingCardActor) payload.getObject()).setPosition(CARD_WIDTH*(cards.size()-1),0);
				((BuildingCardActor) payload.getObject()).addAction(moveTo(WIDTH_BUTTON+PADDING+CARD_WIDTH*(cards.size()-1),0,0.1f));
				//cards.add(((Image) payload.getObject()));
				//group.addActor((Image) payload.getObject());
			}

		}
	}

	private Group makeInfo() {
		Group group = new Group();
		label = new Label("You pressed this button "+count+" times",new Label.LabelStyle(new BitmapFont(),Color.BROWN));
		group.addActor(label);
		return group;
	}

	private void setActorsBounds(){
		field.setBounds(0,0, WIDTH_SCREEN/3*2,HEIGH_FIELD);
		toWorkshopBtn.setBounds(0,0,WIDTH_BUTTON,HEIGHT_HAND);

		fieldGroup.setBounds(0,HEIGHT_HAND+PADDING, WIDTH_SCREEN,HEIGH_FIELD);
		info.setBounds(0,HEIGHT_HAND+PADDING+HEIGH_FIELD+PADDING, WIDTH_SCREEN,HEIGHT_INFO);
		workshop.setBounds(-WIDTH_SCREEN,HEIGHT_HAND+PADDING, WIDTH_SCREEN,HEIGH_FIELD);
		toFields.setBounds(-WIDTH_SCREEN,0,WIDTH_BUTTON,HEIGHT_HAND);
		endTurnBtn.setBounds(WIDTH_SCREEN/3*2,0,WIDTH_SCREEN/3,HEIGH_FIELD);
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

	private void addBuildingCardToHand(BuildingCardActor buildingCardActor){
		cards.add(buildingCardActor);
		dragAndDropField.addSource(new FieldCardSource(buildingCardActor));
		buildingCardActor.setBounds(WIDTH_BUTTON+PADDING+CARD_WIDTH * (cards.size() - 1),0,CARD_WIDTH,CARD_WIDTH);
		stage.addActor(buildingCardActor);
	}
	private void makeHand() {
		for (int i = 0; i < 3; i++) {
			addBuildingCardToHand(FactoryCard.createCard(BuildingCardTypes.ENERGY));
		}
		dragAndDropField.setDragActorPosition(CARD_WIDTH / 2, -CARD_WIDTH/2 );
	}

	private Table makeField() {

		Table table = new Table();

		final CellFieldActor[][] cells = new CellFieldActor[FIELD_SIZE][FIELD_SIZE];//TODO добавить новый класс клетка поля???
		for (int i = 0; i < cells.length ; i++) {
			for (int j = 0; j <cells[i].length ; j++) {
				cells[i][j] = new CellFieldActor(skin, "badlogic");//TODO добавить текстуру клетки поля
				cells[i][j].setSize(CELL_WIDTH,CELL_WIDTH);
				table.add(cells[i][j]).size(HEIGH_FIELD/ FIELD_SIZE,HEIGH_FIELD/ FIELD_SIZE);
				final int finalI = i;
				final int finalJ = j;
				dragAndDropField.addTarget(new DragAndDrop.Target(cells[finalI][finalJ]) {
					public boolean drag (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
						getActor().setColor(Color.GREEN);
						return true;
					}

					public void reset (DragAndDrop.Source source, DragAndDrop.Payload payload) {
						getActor().setColor(Color.WHITE);
					}

					public void drop (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
						cells[finalI][finalJ].setBuildingCardActor((BuildingCardActor) payload.getObject());//TODO убрать из клетки постройку
						System.out.println(cells[finalI][finalJ].getBuildingCardActor());
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
