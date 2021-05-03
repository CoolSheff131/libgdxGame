package com.mygdx.game.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.LevelInfo;
import com.mygdx.game.LevelManager;
import com.mygdx.game.MainMenuScreen;
import com.mygdx.game.MotherBoardCard;
import com.mygdx.game.gamescreen.cards.CardActor;
import com.mygdx.game.gamescreen.cards.Factory;
import com.mygdx.game.gamescreen.cells.CellActor;
import com.mygdx.game.gamescreen.cells.CraftingCellActor;
import com.mygdx.game.gamescreen.cells.FieldCellActor;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class GameScreen implements Screen {
	private MotherBoardCard game;
	private OrthographicCamera camera;
 	public static Skin skin;
 	static{
		skin = new Skin();
		skin.add("fieldCell", new Texture("sprites/FieldCellImg.png"));
		skin.add("badlogic", new Texture("heart.png"));
		skin.add("coem", new Texture("sprites/coem.png"));
		skin.add("res", new Texture("sprites/resourceImg.png"));
		skin.add("scheme", new Texture("sprites/schemeImg.png"));
		skin.add("worker", new Texture("sprites/workerImg.png"));

		skin.add("BuildR", new Texture("sprites/BuildingRes.png"));
		skin.add("BuildRSch", new Texture("sprites/BuildingResScheme.png"));
		skin.add("BuildS", new Texture("sprites/BuildingScheme.png"));
		skin.add("BuildSSch", new Texture("sprites/BuildingSchemeScheme.png"));
		skin.add("BuildW", new Texture("sprites/BuildingWork.png"));
		skin.add("BuildWSch", new Texture("sprites/BuildingWorkScheme.png"));
		skin.add("BuildEn", new Texture("sprites/BuildingEn.png"));
		skin.add("BuildEnSch", new Texture("sprites/BuildingEnScheme.png"));

		skin.add("exit", new Texture("sprites/ExitBtn.png"));
		skin.add("exitPressed", new Texture("sprites/ExitBtnPressed.png"));
		skin.add("pause", new Texture("sprites/PauseBtn.png"));
		skin.add("pausePressed", new Texture("sprites/PauseBtnPressed.png"));
		skin.add("restart", new Texture("sprites/RestartBtn.png"));
		skin.add("restartPressed", new Texture("sprites/RestartBtnPressed.png"));
		skin.add("left", new Texture("sprites/LeftArrowBtn.png"));
		skin.add("leftPressed", new Texture("sprites/LeftArrowBtnPressed.png"));
		skin.add("right", new Texture("sprites/RightArrowBtn.png"));
		skin.add("rightPressed", new Texture("sprites/RightArrowBtnPressed.png"));
		skin.add("craft", new Texture("sprites/CraftBtn.png"));
		skin.add("craftPressed", new Texture("sprites/CraftBtnPressed.png"));
		skin.add("power", new Texture("sprites/PowerBtn.png"));
		skin.add("powerPressed", new Texture("sprites/PowerBtnPressed.png"));
		skin.add("resume", new Texture("sprites/ResumeBtn.png"));
		skin.add("resumePressed", new Texture("sprites/ResumeBtnPressed.png"));
	}

	private Stage stage;
	private ImageButton debugBtn,nextLvlBtn;
	private Button pauseBtn, exitBtn, restartBtn,toWorkshopBtn, endTurnBtn,craftBtn;
	private Label turnLabel, enLabel, endLabel;
	private Group fieldGroup, workshop, info, endGroup;
	public static int WIDTH_SCREEN,HEIGHT_SCREEN , HEIGHT_HAND,HEIGH_FIELD,HEIGHT_INFO,PADDING=20,WIDTH_BUTTON,WIDTH_HAND, CARD_WIDTH,CELL_WIDTH = 100;
	private final int FIELD_SIZE = 5,CRAFTING_SIZE = 3;
	private boolean inFields,onPause;
	private ShapeRenderer shapeRenderer;
	private Table field;
	private Container<Image>  craftingRes;
	private CellActor[] craftingCells = new CellActor[CRAFTING_SIZE];
	private FieldCellActor[][] fieldCells;
	public static int turn = 0;
	private LevelInfo levelInfo;
	private static int energy;

	private Sound btnSound, craftSnd,wrongCraftSnd,winSnd,loseSnd;
	public static void addEn(int en){
		energy+=en;
	}
	public GameScreen (MotherBoardCard game, LevelInfo levelInfo) {
		this.levelInfo = levelInfo;
		this.game = game;
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
		pauseBtn.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				btnSound.play();
				if (!onPause) {
					endLabel.setText("PAUSE");
					stage.addActor(endGroup);
					onPause =!onPause;
					pauseBtn.getStyle().up = skin.getDrawable("resume");
					pauseBtn.getStyle().down = skin.getDrawable("resumePressed");

				}else{
					endGroup.remove();
					pauseBtn.getStyle().up = skin.getDrawable("pause");
					pauseBtn.getStyle().down = skin.getDrawable("pausePressed");
					onPause =!onPause;
				}

				return super.touchDown(event, x, y, pointer, button);
			}
		});

		nextLvlBtn.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				btnSound.play();
				levelInfo = LevelManager.getLevel(levelInfo.getIndex() + 1);
				if(!isNextLvlAvailable())
					nextLvlBtn.setVisible(false);
				restart();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		restartBtn.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				btnSound.play();
				System.out.println("restart");
				restart();
				return super.touchDown(event, x, y, pointer, button);
				
			}
		});

		exitBtn.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				btnSound.play();
				System.out.println("exit");
				game.setScreen(new MainMenuScreen(game));
				dispose();
				return super.touchDown(event, x, y, pointer, button);
			}
		});
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
				btnSound.play();
				turn++;
				for (FieldCellActor[] fieldRow: fieldCells) {
					for(FieldCellActor fieldCell: fieldRow){
						fieldCell.doEndTurnThing();
					}
				}
				turnLabel.setText("Turn: "+turn + "/"+levelInfo.getMaxTurn());
				enLabel.setText("en: "+ energy+"/"+levelInfo.getNeedResources());
				if(energy >= levelInfo.getNeedResources()){
					pauseBtn.setTouchable(Touchable.disabled);
					winSnd.play();
					endLabel.setText("YOU WIN!");
					stage.addActor(endGroup);
					if(LevelManager.getLevel(levelInfo.getIndex() + 1) != null)
						LevelManager.getLevel(levelInfo.getIndex() + 1).openLevel();
					nextLvlBtn.setVisible(true);

				}else if(turn >= levelInfo.getMaxTurn()){
					pauseBtn.setTouchable(Touchable.disabled);
					loseSnd.play();
					endLabel.setText("YOU LOSE");
					stage.addActor(endGroup);
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		craftBtn.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				CardActor res = CraftingSystem.GetRecipeOutput(Singleton.getCardsInCraftingSlots());
				if(res !=null){
					craftSnd.play();
					clearCraftingCells();
					Singleton.addBuildingCard(res);
				}else {
					wrongCraftSnd.play();
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		toWorkshopBtn.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				btnSound.play();
				if(inFields){
					fieldGroup.addAction(moveBy(WIDTH_SCREEN,0,0.5f));
					workshop.addAction(moveBy(WIDTH_SCREEN,0,0.5f));
					inFields = !inFields;
					toWorkshopBtn.getStyle().up = skin.getDrawable("right");
					toWorkshopBtn.getStyle().down = skin.getDrawable("rightPressed");
				}
				else{
					fieldGroup.addAction(moveBy(-WIDTH_SCREEN,0,0.5f));
					workshop.addAction(moveBy(-WIDTH_SCREEN,0,0.5f));
					inFields = !inFields;
					toWorkshopBtn.getStyle().up = skin.getDrawable("left");
					toWorkshopBtn.getStyle().down = skin.getDrawable("leftPressed");
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}

	private boolean isNextLvlAvailable() {
		return LevelManager.getLevel(levelInfo.getIndex() + 1) != null && LevelManager.getLevel(levelInfo.getIndex() + 1).isOpen();
	}

	private void init(){
		btnSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Btn1.mp3"));
		craftSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/craft.wav"));
		wrongCraftSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/wrongCraft.wav"));
		winSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/win.wav"));
		loseSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/lose.wav"));
		energy = 0;
		WIDTH_SCREEN = MotherBoardCard.getWidthScreen();
		HEIGHT_SCREEN = MotherBoardCard.getHeightScreen();
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

		stage = Singleton.getStage();
		Gdx.input.setInputProcessor(stage);//Добавляем обработку нажатии stage

		TextureRegion textureRegion = new TextureRegion(new Texture("heart.png"));
		TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion);

		pauseBtn = new Button();
		pauseBtn.setStyle(new Button.ButtonStyle());
		pauseBtn.getStyle().up = skin.getDrawable("pause");
		pauseBtn.getStyle().down = skin.getDrawable("pausePressed");

		exitBtn = new Button();
		exitBtn.setStyle(new Button.ButtonStyle());
		exitBtn.getStyle().up = skin.getDrawable("exit");
		exitBtn.getStyle().down = skin.getDrawable("exitPressed");

		restartBtn = new Button();
		restartBtn.setStyle(new Button.ButtonStyle());
		restartBtn.getStyle().up = skin.getDrawable("restart");
		restartBtn.getStyle().down = skin.getDrawable("restartPressed");

		toWorkshopBtn = new Button();
		toWorkshopBtn.setStyle(new Button.ButtonStyle());
		toWorkshopBtn.getStyle().up = skin.getDrawable("left");
		toWorkshopBtn.getStyle().down = skin.getDrawable("leftPressed");
		endTurnBtn = new Button();
		endTurnBtn.setStyle(new Button.ButtonStyle());
        endTurnBtn.getStyle().up = skin.getDrawable("power");
        endTurnBtn.getStyle().down = skin.getDrawable("powerPressed");

		craftBtn = new Button();
		craftBtn.setStyle(new Button.ButtonStyle());
        craftBtn.getStyle().up = skin.getDrawable("craft");
        craftBtn.getStyle().down = skin.getDrawable("craftPressed");

		debugBtn = new ImageButton(textureRegionDrawable);

		nextLvlBtn = new ImageButton(textureRegionDrawable);
		if(!isNextLvlAvailable())
			nextLvlBtn.setVisible(false);
		shapeRenderer = new ShapeRenderer();
		fieldGroup = new Group();
		endGroup = makeEnd();
		stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
		stage.getViewport().apply();
		field = makeField();
		makeHand();
		info = makeInfo();
		workshop = Singleton.getWorkshop();
		makeWorkshop();
	}

	private Group makeEnd() {
		Group group =new Group();
		Image menuPanel = new Image( new Texture("sprites/menuPanel.png"));
		int WIDTH_MENU = WIDTH_SCREEN/3*2,HEIGHT_MENU = HEIGHT_HAND+PADDING+HEIGH_FIELD+PADDING;
		int btnMenu_size = WIDTH_MENU/5;
		int paddingMenu = WIDTH_MENU/10;
		menuPanel.setBounds(WIDTH_SCREEN/6,0,WIDTH_MENU,HEIGHT_MENU);
		endLabel = new Label("ERROR",new Label.LabelStyle(MainMenuScreen.bigFont,Color.BLACK));
		group.addActor(menuPanel);
		group.addActor(exitBtn);
		group.addActor(restartBtn);
		group.addActor(nextLvlBtn);
		group.addActor(endLabel);

		endLabel.setBounds(WIDTH_SCREEN/2-endLabel.getWidth()/2,HEIGHT_SCREEN/3*2,WIDTH_SCREEN/5,HEIGHT_SCREEN/5);

		exitBtn.setBounds(WIDTH_SCREEN/6+paddingMenu,0,btnMenu_size,btnMenu_size);
		restartBtn.setBounds(WIDTH_SCREEN/6+2*paddingMenu+btnMenu_size,0,btnMenu_size,btnMenu_size);
		nextLvlBtn.setBounds(WIDTH_SCREEN/6+3*paddingMenu+2*btnMenu_size,0,btnMenu_size,btnMenu_size);
		return group;
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
		pauseBtn.setBounds(0,0,HEIGHT_INFO,HEIGHT_INFO);
		turnLabel = new Label("Turn: "+ turn+"/"+levelInfo.getMaxTurn(),new Label.LabelStyle(MainMenuScreen.lblfont,Color.BLACK));
		enLabel = new Label("en: "+ energy+"/"+levelInfo.getNeedResources(),new Label.LabelStyle(MainMenuScreen.lblfont,Color.BLACK));
		turnLabel.setBounds(HEIGHT_INFO + PADDING,0,WIDTH_SCREEN/5,HEIGHT_INFO);
		enLabel.setBounds(HEIGHT_INFO + PADDING+WIDTH_SCREEN/5+PADDING,0,WIDTH_SCREEN/5,HEIGHT_INFO);
		group.addActor(turnLabel);
		group.addActor(pauseBtn);
		group.addActor(enLabel);
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

	private void restart(){
		pauseBtn.setTouchable(Touchable.enabled);
		Singleton.getDADToHand().clear();
		Singleton.getDADToField().clear();
		Singleton.getDADToWorkshopHand().clear();
		Singleton.getDADWorkshop().clear();


		pauseBtn.getStyle().up = skin.getDrawable("pause");
		pauseBtn.getStyle().down = skin.getDrawable("pausePressed");
		onPause = false;

		energy = 0;
		turn = 0;
		turnLabel.setText("Turn: "+turn + "/"+levelInfo.getMaxTurn());
		enLabel.setText("en: "+ energy+"/"+levelInfo.getNeedResources());
		clearField();
		clearCraftingCells();
		Singleton.clearHandCrafting();
		Singleton.clearHandBuilding();
		makeHand();
		for (int i =0; i < 4;i++)	Singleton.addcraftingcard(Factory.createCard(Items.RESOURSE_CARD)); //Добавление карт в руку
		Singleton.addcraftingcard(Factory.createCard(Items.SCHEME_CARD));
		endGroup.remove();
	}
	private void clearField(){
		for(FieldCellActor fieldRow[]: fieldCells)
			for(FieldCellActor fieldCell: fieldRow)
				fieldCell.clearBuilding();
	}


	private void setActorsBounds(){
		field.setBounds(0,0, WIDTH_SCREEN/3*2,HEIGH_FIELD);
		toWorkshopBtn.setBounds(0,0,WIDTH_BUTTON,HEIGHT_HAND);
		fieldGroup.setBounds(0,HEIGHT_HAND+PADDING, WIDTH_SCREEN,HEIGH_FIELD);
		info.setBounds(0,HEIGHT_HAND+PADDING+HEIGH_FIELD+PADDING, WIDTH_SCREEN,HEIGHT_INFO);
		workshop.setBounds(-WIDTH_SCREEN,HEIGHT_HAND+PADDING, WIDTH_SCREEN,HEIGH_FIELD);
		debugBtn.setBounds(-WIDTH_SCREEN,0,WIDTH_BUTTON,HEIGHT_HAND);
		endTurnBtn.setBounds(WIDTH_SCREEN/3*2,0,WIDTH_SCREEN/3,HEIGH_FIELD);
		endGroup.setBounds(0,0,WIDTH_SCREEN,HEIGHT_HAND+PADDING+HEIGH_FIELD+PADDING);
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
		Singleton.addBuildingCard(Factory.createCard(Items.ENERGY_BUILDING));
		Singleton.addBuildingCard(Factory.createCard(Items.ENERGY_BUILDING));
		Singleton.addBuildingCard(Factory.createCard(Items.ENERGY_BUILDING));
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
	public void render (float delta) {
		handleInput();
		Gdx.gl.glClearColor(0.80f, 0.80f, 0.80f, 1);
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
	public void show() {

	}



	@Override
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose () {

		stage.dispose();
	}
}
