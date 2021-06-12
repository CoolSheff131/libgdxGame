package com.mygdx.game.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.FontManager;
import com.mygdx.game.LevelInfo;
import com.mygdx.game.LevelManager;
import com.mygdx.game.MainMenuScreen;
import com.mygdx.game.MediaPlayer;
import com.mygdx.game.MotherBoardCard;
import com.mygdx.game.TextureLoader;
import com.mygdx.game.gamescreen.cards.Factory;
import com.mygdx.game.gamescreen.cards.Items;
import com.mygdx.game.gamescreen.craft.CraftingBook;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class GameScreen implements Screen {
	private MotherBoardCard game;
	private OrthographicCamera camera;
 	public static Skin skin = TextureLoader.getSkin();//todo Изаваться от этого
	private Stage stage;
	private Button pauseBtn, exitBtn, restartBtn,toWorkshopBtn, endTurnBtn,craftBtn,nextLvlBtn, craftingBookBtn;
	private Label turnLabel, enLabel, endLabel;
	private Group info, endGroup;
	public static int WIDTH_SCREEN,HEIGHT_SCREEN;
	private boolean inFields,onPause;
	private LevelInfo levelInfo;
	private static int energy,turn = 0;
	private Group fieldGroup,workshopGroup;
	private static final FieldCells FIELD_CELLS =  new FieldCells(600,500,5,5);
	private WorkshopCells workshopCells;
	private Image craftMachineImg, woodenTable;
	private CraftingBook craftingBook;
	private Hand buildHand;
	public static void addEn(int en){
		energy+=en;
	}
	public GameScreen (MotherBoardCard game,LevelInfo levelInfo) {
		this.levelInfo = levelInfo;
		this.game = game;
		init();
		setBtnListeners();
		stage.addActor(fieldGroup);
		stage.addActor(workshopGroup);
		stage.addActor(toWorkshopBtn);
		stage.addActor(info);
		stage.addActor(buildHand);
		restart();
	}
	private void setBtnListeners(){
		pauseBtn.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				MediaPlayer.playBtn();
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
				MediaPlayer.playBtn();
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
				MediaPlayer.playBtn();
				restart();
				return super.touchDown(event, x, y, pointer, button);
				
			}
		});

		exitBtn.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				MediaPlayer.playBtn();
				game.setScreen(new MainMenuScreen(game));
				dispose();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		endTurnBtn.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				MediaPlayer.playBtn();
				turn++;
				FIELD_CELLS.makePower();
				turnLabel.setText("Turn: "+turn + "/"+levelInfo.getMaxTurn());
				enLabel.setText("en: "+ energy+"/"+levelInfo.getNeedResources());
				if(energy >= levelInfo.getNeedResources()){
					pauseBtn.setTouchable(Touchable.disabled);
					MediaPlayer.playWinSnd();
					endLabel.setText("YOU WIN!");
					stage.addActor(endGroup);
					if(LevelManager.getLevel(levelInfo.getIndex() + 1) != null)
						LevelManager.getLevel(levelInfo.getIndex() + 1).openLevel();
					nextLvlBtn.setVisible(true);

				}else if(turn >= levelInfo.getMaxTurn()){
					pauseBtn.setTouchable(Touchable.disabled);
					MediaPlayer.playLoseSnd();
					endLabel.setText("YOU LOSE");
					stage.addActor(endGroup);
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		toWorkshopBtn.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				MediaPlayer.playBtn();
				if(inFields){
					fieldGroup.addAction(moveBy(WIDTH_SCREEN,0,0.2f));
					workshopGroup.addAction(moveBy(WIDTH_SCREEN,0,0.2f));
					inFields = !inFields;
					toWorkshopBtn.getStyle().up = skin.getDrawable("right");
					toWorkshopBtn.getStyle().down = skin.getDrawable("rightPressed");
				}
				else{
					fieldGroup.addAction(moveBy(-WIDTH_SCREEN,0,0.2f));
					workshopGroup.addAction(moveBy(-WIDTH_SCREEN,0,0.2f));
					inFields = !inFields;
					toWorkshopBtn.getStyle().up = skin.getDrawable("left");
					toWorkshopBtn.getStyle().down = skin.getDrawable("leftPressed");
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		craftBtn.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(workshopCells.craft()){
					MediaPlayer.playCraftSnd();
				}else{
					MediaPlayer.playWrongCraftSnd();
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		craftingBookBtn.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				MediaPlayer.playBtn();
				craftingBook.show();
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}

	private boolean isNextLvlAvailable() {
		return levelInfo != null && LevelManager.getLevel(levelInfo.getIndex() + 1) != null && LevelManager.getLevel(levelInfo.getIndex() + 1).isOpen();
	}

	private void init(){
		WIDTH_SCREEN = MotherBoardCard.getWidthScreen();
		HEIGHT_SCREEN = MotherBoardCard.getHeightScreen();
		Image backgroundField = new Image(skin, "bgn");
		energy = 0;
		backgroundField.setSize(WIDTH_SCREEN,HEIGHT_SCREEN);
		int HEIGHT_HAND,HEIGH_FIELD,HEIGHT_INFO,PADDING,WIDTH_BUTTON;
		HEIGHT_HAND = HEIGHT_SCREEN /6;
		HEIGH_FIELD = HEIGHT_SCREEN /3*2;
		HEIGHT_INFO = HEIGHT_SCREEN /12;
		PADDING     = HEIGHT_SCREEN/12;
		WIDTH_BUTTON= WIDTH_SCREEN /9;

		inFields = true;
		camera = new OrthographicCamera();
		camera.position.set(WIDTH_SCREEN / 2, HEIGHT_SCREEN / 2, 0);
		buildHand = Singleton.getBuild();
		stage = Singleton.getStage();
		Gdx.input.setInputProcessor(stage);//Добавляем обработку нажатии stage
		craftingBook = new CraftingBook(WIDTH_SCREEN/3,HEIGHT_SCREEN/3);
		pauseBtn = new Button();
		pauseBtn.setStyle(new Button.ButtonStyle());
		pauseBtn.getStyle().up = TextureLoader.getSkin().getDrawable("pause");
		pauseBtn.getStyle().down = TextureLoader.getSkin().getDrawable("pausePressed");

		exitBtn = new Button();
		exitBtn.setStyle(new Button.ButtonStyle());
		exitBtn.getStyle().up = TextureLoader.getSkin().getDrawable("exit");
		exitBtn.getStyle().down = TextureLoader.getSkin().getDrawable("exitPressed");

		restartBtn = new Button();
		restartBtn.setStyle(new Button.ButtonStyle());
		restartBtn.getStyle().up = TextureLoader.getSkin().getDrawable("restart");
		restartBtn.getStyle().down = TextureLoader.getSkin().getDrawable("restartPressed");

		nextLvlBtn = new Button();
		nextLvlBtn.setStyle(new Button.ButtonStyle());
		nextLvlBtn.getStyle().up = TextureLoader.getSkin().getDrawable("next");
		nextLvlBtn.getStyle().down = TextureLoader.getSkin().getDrawable("nextPressed");

		toWorkshopBtn = new Button();
		toWorkshopBtn.setStyle(new Button.ButtonStyle());
		toWorkshopBtn.getStyle().up = TextureLoader.getSkin().getDrawable("left");
		toWorkshopBtn.getStyle().down = TextureLoader.getSkin().getDrawable("leftPressed");
		endTurnBtn = new Button();
		endTurnBtn.setStyle(new Button.ButtonStyle());
        endTurnBtn.getStyle().up = TextureLoader.getSkin().getDrawable("power");
        endTurnBtn.getStyle().down = TextureLoader.getSkin().getDrawable("powerPressed");

		craftBtn = new Button();
		craftBtn.setStyle(new Button.ButtonStyle());
        craftBtn.getStyle().up = TextureLoader.getSkin().getDrawable("craft");
        craftBtn.getStyle().down = TextureLoader.getSkin().getDrawable("craftPressed");

        craftingBookBtn = new Button();
        craftingBookBtn.setStyle(new Button.ButtonStyle());
        craftingBookBtn.getStyle().up = TextureLoader.getSkin().getDrawable("bookBtn");
        craftingBookBtn.getStyle().down = TextureLoader.getSkin().getDrawable("bookBtnPressed");

		if(!isNextLvlAvailable())
			nextLvlBtn.setVisible(false);
		fieldGroup = new Group();
		workshopGroup = Singleton.getWorkshop();
		workshopCells = new WorkshopCells(WIDTH_SCREEN/2,HEIGHT_SCREEN/4,3);
		woodenTable = new Image(TextureLoader.getSkin(),"wood");
		craftMachineImg = new Image(TextureLoader.getSkin(),"craftM");
		workshopCells.setPosition(WIDTH_SCREEN/4,HEIGHT_SCREEN/3*2);
		craftMachineImg.setBounds(WIDTH_SCREEN/6,HEIGHT_SCREEN/2,WIDTH_SCREEN/6*4,HEIGHT_SCREEN/2);
		craftBtn.setBounds(0,HEIGHT_SCREEN/2,WIDTH_SCREEN/9,WIDTH_SCREEN/9);
		craftingBookBtn.setBounds(WIDTH_SCREEN/6*5,HEIGHT_SCREEN/2,WIDTH_SCREEN/9,WIDTH_SCREEN/9);
		craftingBook.setBounds(WIDTH_SCREEN/6*4,HEIGHT_SCREEN/3,WIDTH_SCREEN/9,WIDTH_SCREEN/9);


		workshopGroup.addActor(woodenTable);
		workshopGroup.addActor(craftMachineImg);
		workshopGroup.addActor(craftBtn);
		workshopGroup.addActor(craftingBookBtn);
		workshopGroup.addActor(workshopCells);
		workshopGroup.addActor(craftingBook);
		workshopGroup.addActor(Singleton.getCraft());
		Singleton.getCraft().setPosition(0,HEIGHT_SCREEN/3);

		fieldGroup.addActor(backgroundField);
		fieldGroup.addActor(FIELD_CELLS);
		fieldGroup.addActor(endTurnBtn);

		endGroup = new Group();;

		Image menuPanel = new Image( new Texture("sprites/menuPanel.png"));
		int WIDTH_MENU = WIDTH_SCREEN/3*2,HEIGHT_MENU = HEIGHT_HAND+PADDING+HEIGH_FIELD+PADDING;
		int btnMenu_size = WIDTH_MENU/5;
		int paddingMenu = WIDTH_MENU/10;
		menuPanel.setBounds(WIDTH_SCREEN/6,0,WIDTH_MENU,HEIGHT_MENU);
		endLabel = new Label("ERROR",new Label.LabelStyle(FontManager.getFont(100),Color.BLACK));
		endGroup.addActor(menuPanel);
		endGroup.addActor(exitBtn);
		endGroup.addActor(restartBtn);
		endGroup.addActor(nextLvlBtn);
		endGroup.addActor(endLabel);
		endLabel.setBounds(WIDTH_SCREEN/2-endLabel.getWidth()/2,HEIGHT_SCREEN/3*2,WIDTH_SCREEN/5,HEIGHT_SCREEN/5);
		exitBtn.setBounds(WIDTH_SCREEN/6+paddingMenu,btnMenu_size,btnMenu_size,btnMenu_size);
		restartBtn.setBounds(WIDTH_SCREEN/6+2*paddingMenu+btnMenu_size,btnMenu_size,btnMenu_size,btnMenu_size);
		nextLvlBtn.setBounds(WIDTH_SCREEN/6+3*paddingMenu+2*btnMenu_size,btnMenu_size,btnMenu_size,btnMenu_size);

		stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
		stage.getViewport().apply();
		info =  new Group();

		pauseBtn.setBounds(0,0,HEIGHT_INFO,HEIGHT_INFO);
		turnLabel = new Label("Turn: "+ turn+"/"+levelInfo.getMaxTurn(),new Label.LabelStyle(FontManager.getFont(40),Color.BLACK));
		enLabel = new Label("en: "+ energy+"/"+levelInfo.getNeedResources(),new Label.LabelStyle(FontManager.getFont(40),Color.BLACK));
		turnLabel.setBounds(HEIGHT_INFO + PADDING,0,WIDTH_SCREEN/5,HEIGHT_INFO);
		enLabel.setBounds(HEIGHT_INFO + PADDING+WIDTH_SCREEN/5+PADDING,0,WIDTH_SCREEN/5,HEIGHT_INFO);
		Image bgn = new Image(skin,"bgnInfo");
		bgn.setSize(WIDTH_SCREEN,HEIGHT_INFO);
		info.addActor(bgn);
		info.addActor(turnLabel);
		info.addActor(pauseBtn);
		info.addActor(enLabel);


		FIELD_CELLS.setBounds(0,HEIGHT_SCREEN/2-HEIGH_FIELD/2, WIDTH_SCREEN/3*2,HEIGH_FIELD);
		toWorkshopBtn.setBounds(0,0,WIDTH_BUTTON,HEIGHT_HAND);
		buildHand.setPosition(WIDTH_BUTTON+PADDING,0);
		fieldGroup.setBounds(0,0, WIDTH_SCREEN,HEIGH_FIELD);
		workshopGroup.setBounds(-WIDTH_SCREEN,0,WIDTH_SCREEN,HEIGHT_SCREEN);
		woodenTable.setBounds(0,0,WIDTH_SCREEN,HEIGHT_SCREEN);
		info.setBounds(0,HEIGHT_HAND+PADDING+HEIGH_FIELD, WIDTH_SCREEN,HEIGHT_INFO);
		endTurnBtn.setBounds(WIDTH_SCREEN/4*3,HEIGH_FIELD/3,HEIGH_FIELD/6,HEIGH_FIELD/6);
		endGroup.setBounds(0,0,WIDTH_SCREEN,HEIGHT_HAND+PADDING+HEIGH_FIELD);
	}

	private void restart(){
		pauseBtn.setTouchable(Touchable.enabled);
//		Singleton.getDragAndDrop().clear();todo исправить убираются таргеты с крафтовых но не возвращаются
		pauseBtn.getStyle().up = skin.getDrawable("pause");
		pauseBtn.getStyle().down = skin.getDrawable("pausePressed");
		onPause = false;

		energy = 0;
		turn = 0;
		turnLabel.setText("Turn: "+turn + "/"+levelInfo.getMaxTurn());
		enLabel.setText("en: "+ energy+"/"+levelInfo.getNeedResources());

		FIELD_CELLS.clearField();
		workshopCells.clearCraftingCells();

		Singleton.clearHandCrafting();
		Singleton.clearHandBuilding();
		fillStartHandBuild();
		endGroup.remove();
	}



	private void fillStartHandBuild() {//todo брать информацию о стартовой руке от посылки
		Singleton.addcraftingcard(Factory.createCard(Items.WORKER_CARD));
		Singleton.addcraftingcard(Factory.createCard(Items.WORKER_CARD));
		Singleton.addcraftingcard(Factory.createCard(Items.RESOURSE_CARD));
		Singleton.addcraftingcard(Factory.createCard(Items.RESOURSE_CARD));
		Singleton.addcraftingcard(Factory.createCard(Items.SCHEME_CARD));
		Singleton.addcraftingcard(Factory.createCard(Items.SCHEME_CARD));

		Singleton.addBuildingCard(Factory.createCard(Items.ENERGY_BUILDING));
		Singleton.addBuildingCard(Factory.createCard(Items.ENERGY_BUILDING));
		Singleton.addBuildingCard(Factory.createCard(Items.ENERGY_BUILDING));
		Singleton.addBuildingCard(Factory.createCard(Items.RESOURSE_BUILDING));
		Singleton.addBuildingCard(Factory.createCard(Items.WORKER_BUILDING));
		Singleton.addBuildingCard(Factory.createCard(Items.SCHEME_BUILDING));
		Singleton.addBuildingCard(Factory.createCard(Items.QUICKBUILD_UPGRADE));
		Singleton.addBuildingCard(Factory.createCard(Items.ACTIVATEBUILD_UPGRADE));
		Singleton.addBuildingCard(Factory.createCard(Items.ACTIVATEBUILD_UPGRADE));
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0.80f, 0.80f, 0.80f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void show() {	}



	@Override
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose () {
		//stage.dispose();
	}
}
