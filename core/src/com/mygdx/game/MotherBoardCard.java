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
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.cards.CardActor;
import com.mygdx.game.cards.Factory;
import com.mygdx.game.cards.buildings.Building;
import com.mygdx.game.cells.CellActor;
import com.mygdx.game.cells.CraftingCellActor;
import com.mygdx.game.cells.FieldCellActor;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MotherBoardCard extends ApplicationAdapter {
 	public static Skin skin;
	private Stage stage;
	private ImageButton toWorkshopBtn, endTurnBtn,craftBtn, debugBtn;
	private Label turnLabel;
	private Group fieldGroup, workshop, info;
	private OrthographicCamera camera;
	public static int  WIDTH_SCREEN, HEIGHT_SCREEN, HEIGHT_HAND,HEIGH_FIELD,HEIGHT_INFO,PADDING=20,WIDTH_BUTTON,WIDTH_HAND, CARD_WIDTH,CELL_WIDTH = 100;
	private final int FIELD_SIZE = 5,CRAFTING_SIZE = 3;
	private boolean inFields;
	private ShapeRenderer shapeRenderer;
	private Table field;
	private Container<Image>  craftingRes;
	private CellActor[] craftingCells = new CellActor[CRAFTING_SIZE];
	private FieldCellActor[][] fieldCells;
	public static int turn = 0;
	@Override
	public void create () {
		init();
		setBtnListeners();
		setActorsBounds();
		stage.addActor(info);
		stage.addActor(fieldGroup);
		stage.addActor(toWorkshopBtn);
		stage.addActor(debugBtn);
		stage.addActor(workshop);
		fieldGroup.addActor(field);
		fieldGroup.addActor(endTurnBtn);

	}
	private void setBtnListeners(){
		debugBtn.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				field.setDebug(!field.getDebug());
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		endTurnBtn.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				turn++;
				for (FieldCellActor[] fieldRow: fieldCells) {
					for(FieldCellActor fieldCell: fieldRow){
						fieldCell.doEndTurnThing();
					}
				}
				turnLabel.setText("Turn: "+turn);
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		craftBtn.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				CardActor res = CraftingSystem.GetRecipeOutput(Singleton.getCardsInCraftingSlots());
				if(res !=null){
					System.out.println("CRAFT!");
					clearCraftingCells();
					Singleton.addBuildingCard(res);
				}else {
					System.out.println("WRONG");//todo показать игроку что неправильно собран крафт
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		toWorkshopBtn.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
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
		skin.add("fieldCell", new Texture("sprites/FieldCellImg.png"));
		skin.add("badlogic", new Texture("heart.png"));
		skin.add("coem", new Texture("sprites/coem.png"));
		skin.add("res", new Texture("sprites/resourceImg.png"));
		skin.add("scheme", new Texture("sprites/schemeImg.png"));
		skin.add("worker", new Texture("sprites/workerImg.png"));
		skin.add("BuildR", new Texture("sprites/BuildingRes.png"));
		skin.add("BuildS", new Texture("sprites/BuildingScheme.png"));
		skin.add("BuildW", new Texture("sprites/BuildingWork.png"));
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
		stage = Singleton.getStage();
		Gdx.input.setInputProcessor(stage);//Добавляем обработку нажатии stage

		TextureRegion textureRegion = new TextureRegion(new Texture("heart.png"));
		TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion);
		toWorkshopBtn = new ImageButton(textureRegionDrawable);
		endTurnBtn = new ImageButton(textureRegionDrawable);
		craftBtn = new ImageButton(textureRegionDrawable);
		debugBtn = new ImageButton(textureRegionDrawable);
		shapeRenderer = new ShapeRenderer();
		fieldGroup = new Group();

		stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
		stage.getViewport().apply();
		field = makeField();
		makeHand();
		info = makeInfo();
		workshop = Singleton.getWorkshop();
		makeWorkshop();
	}

	private void makeWorkshop() {
		Table craftingTable = new Table();//Создание поле крафта
		for (int i = 0; i < craftingCells.length ; i++) {
			craftingCells[i] = new CraftingCellActor(skin, "badlogic");
			craftingTable.add(craftingCells[i]);
			Singleton.getDADWorkshop().addTarget(Factory.createTarget(Factory.WORKSHOPTARGET,craftingCells[i]));
		}
		for (int i =0; i < 4;i++)	Singleton.addcraftingcard(Factory.createCard(Items.RESOURSE_CARD)); //Добавление карт в руку
		Singleton.addcraftingcard(Factory.createCard(Items.SCHEME_CARD));
		Singleton.addcraftingcard(Factory.createCard(Items.WORKER_CARD));

		Singleton.getDADWorkshop().setDragActorPosition(Singleton.getCraftingCards().get(0).getWidth()/2, -Singleton.getCraftingCards().get(0).getHeight()*3/2);
		craftingTable.setDebug(true);
		craftingRes = new Container<>();
		workshop.addActor(craftingTable);
		workshop.addActor(craftBtn);
		workshop.addActor(craftingRes);
		craftBtn.setBounds(0,HEIGH_FIELD/3,WIDTH_SCREEN/4,HEIGH_FIELD/3*2);
		craftingTable.setBounds(WIDTH_SCREEN/4,HEIGH_FIELD/3,WIDTH_SCREEN/4*2,HEIGH_FIELD/3*2);
		craftingRes.setBounds(WIDTH_SCREEN/4*3,HEIGH_FIELD/3,WIDTH_SCREEN/4,HEIGH_FIELD/3*2);
	}
	private Group makeInfo() {
		Group group = new Group();
		turnLabel = new Label("Turn: "+ turn,new Label.LabelStyle(new BitmapFont(),Color.BROWN));
		turnLabel.setBounds(0,0,100,40);
		group.addActor(turnLabel);
		return group;
	}

	private void clearCraftingCells(){
		for (CellActor cell: craftingCells ) {
			cell.setDrawable(skin,"badlogic");
			cell.removeCartActor();
			Singleton.getDADWorkshop().addTarget(Factory.createTarget(Factory.WORKSHOPTARGET,cell));
		}
		Singleton.getCardsInCraftingSlots().clear();
	}

	private void setActorsBounds(){
		field.setBounds(0,0, WIDTH_SCREEN/3*2,HEIGH_FIELD);
		toWorkshopBtn.setBounds(0,0,WIDTH_BUTTON,HEIGHT_HAND);
		fieldGroup.setBounds(0,HEIGHT_HAND+PADDING, WIDTH_SCREEN,HEIGH_FIELD);
		info.setBounds(0,HEIGHT_HAND+PADDING+HEIGH_FIELD+PADDING, WIDTH_SCREEN,HEIGHT_INFO);
		workshop.setBounds(-WIDTH_SCREEN,HEIGHT_HAND+PADDING, WIDTH_SCREEN,HEIGH_FIELD);
		debugBtn.setBounds(-WIDTH_SCREEN,0,WIDTH_BUTTON,HEIGHT_HAND);
		endTurnBtn.setBounds(WIDTH_SCREEN/3*2,0,WIDTH_SCREEN/3,HEIGH_FIELD);
	}

	private void handleInput() {
		if(Gdx.input.isKeyPressed(Input.Keys.A)) camera.zoom += 0.02;
		if(Gdx.input.isKeyPressed(Input.Keys.Q)) camera.zoom -= 0.02;
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.translate(-3, 0, 0);
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.translate(3, 0, 0);
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.translate(0, -3, 0);
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) camera.translate(0, 3, 0);
		if(Gdx.input.isKeyPressed(Input.Keys.W)) camera.rotate(-1, 0, 0, 1);
		if(Gdx.input.isKeyPressed(Input.Keys.E))camera.rotate(1, 0, 0, 1);
	}
	private void makeHand() {
		for (int i = 0; i < 3; i++) Singleton.addBuildingCard(Factory.createCard(Items.ENERGY_BUILDING));
		Singleton.addBuildingCard(Factory.createCard(Items.STORAGE_BUILDING));
	}

	private Table makeField() {
		Table table = new Table();
		fieldCells = new FieldCellActor[FIELD_SIZE][FIELD_SIZE];
		for (int i = 0; i < fieldCells.length ; i++) {
			for (int j = 0; j < fieldCells[i].length ; j++) {
				fieldCells[i][j] = new FieldCellActor(skin, "fieldCell");
				fieldCells[i][j].setSize(CELL_WIDTH,CELL_WIDTH);
				table.add(fieldCells[i][j]).size(HEIGH_FIELD/ FIELD_SIZE,HEIGH_FIELD/ FIELD_SIZE);
				Singleton.getDADToField().addTarget(Factory.createTarget(Factory.FIELDTARGET,fieldCells[i][j]));
			}
			table.row();
		}
		return table;
	}

	@Override
	public void render () {
		handleInput();
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);	//макет игрового экрана
		shapeRenderer.rect(0,0,WIDTH_BUTTON,HEIGHT_HAND);
		shapeRenderer.rect(WIDTH_BUTTON+PADDING,0,WIDTH_HAND,HEIGHT_HAND);
		shapeRenderer.rect(0,HEIGHT_HAND+PADDING, WIDTH_SCREEN,HEIGH_FIELD);
		shapeRenderer.rect(0,HEIGHT_HAND+PADDING+HEIGH_FIELD+PADDING, WIDTH_SCREEN,HEIGHT_INFO);
		shapeRenderer.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	@Override
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	@Override
	public void dispose () {
		stage.dispose();
	}
}
