package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.gamescreen.GameScreen;
import com.mygdx.game.gamescreen.craft.CraftingBook;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;

public class MainMenuScreen implements Screen {
   private MotherBoardCard game;
    private Stage stage;
    private OrthographicCamera camera;
    private Group mainMenu, levels, options;
    private ArrayList<Button> mainButtons,choosLvlBtn;
    private ArrayList<OptionItem> optionItems;
    private Label menuLabel,levelLabel, optionLabel,gameLabel;
    private Skin skin;
    protected static TextButton.TextButtonStyle checkBtnStyle, textButtonStyle;
    private int WIDTH_SCREEN, HEIGHT_SCREEN;
    private TextButton backOption,backLevel;
    protected static int BUTTON_WIDTH, BUTTON_HEIGHT;
    private Texture background;
    public MainMenuScreen(final MotherBoardCard game){
        WIDTH_SCREEN = MotherBoardCard.getWidthScreen();
        HEIGHT_SCREEN = MotherBoardCard.getHeightScreen();
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


        skin = new Skin();
        skin.add("up", new Texture("sprites/Button.png"));
        skin.add("down", new Texture("sprites/ButtonPressed.png"));
        skin.add("unCheck", new Texture("sprites/CheckBtn.png"));
        skin.add("check", new Texture("sprites/CheckBtnChecked.png"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/k.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        gameLabel = new Label("MotherBoardCard",new Label.LabelStyle(FontManager.getFont(100), Color.WHITE));
        gameLabel.setPosition(WIDTH_SCREEN/2-gameLabel.getWidth()/2,HEIGHT_SCREEN/8*7);

        textButtonStyle = new TextButton.TextButtonStyle();
        generator.dispose();
        textButtonStyle.font = FontManager.getFont(20);
        textButtonStyle.fontColor = Color.BLACK;

        textButtonStyle.up = skin.getDrawable("up");
        textButtonStyle.down = skin.getDrawable("down");


        checkBtnStyle = new TextButton.TextButtonStyle();
        checkBtnStyle.up =  skin.getDrawable("check");

        int WIDTH_MENU = WIDTH_SCREEN/3*2,HEIGHT_MENU = HEIGHT_SCREEN;
        background = new Texture("sprites/Wood.png");
        //========main menu===============
        mainMenu = new Group();
        Image menuPanel = new Image( new Texture("sprites/menuPanel.png"));
        menuPanel.setBounds(WIDTH_SCREEN/6,0,WIDTH_MENU,HEIGHT_MENU);
        mainMenu.addActor(menuPanel);
        mainMenu.setBounds(0,0,WIDTH_SCREEN,HEIGHT_SCREEN);
        mainButtons = new ArrayList<>();

        mainButtons.add(new TextButton("Play",textButtonStyle));
        mainButtons.add(new TextButton("Options",textButtonStyle));
        int i =0;

        BUTTON_WIDTH = WIDTH_SCREEN /3;
        BUTTON_HEIGHT = HEIGHT_SCREEN / 6;
        for (Button button: mainButtons) {
            button.setBounds(WIDTH_SCREEN/2 - BUTTON_WIDTH/2,HEIGHT_SCREEN/4*2 - i * BUTTON_HEIGHT,BUTTON_WIDTH,BUTTON_HEIGHT);
            i++;
            mainMenu.addActor(button);
        }
        menuLabel = new Label("MAIN MENU ",new Label.LabelStyle(FontManager.getFont(40), Color.WHITE));
        mainMenu.addActor(menuLabel);
        menuLabel.setPosition(WIDTH_SCREEN/2-menuLabel.getWidth()/2,HEIGHT_SCREEN/8*6);

        //=========LEVELS========
        levels = new Group();
        Image levelPanel = new Image( new Texture("sprites/menuPanel.png"));
        levelPanel.setBounds(WIDTH_SCREEN/6,0,WIDTH_MENU,HEIGHT_MENU);
        levels.addActor(levelPanel);
        levels.setBounds(WIDTH_SCREEN,0,WIDTH_SCREEN,HEIGHT_SCREEN);

        backLevel = new TextButton("Back",textButtonStyle);
        backLevel.setBounds(WIDTH_SCREEN/2-BUTTON_WIDTH/2,BUTTON_HEIGHT,BUTTON_WIDTH,BUTTON_HEIGHT);
        levels.addActor(backLevel);
        levelLabel = new Label("LEVELS",new Label.LabelStyle(FontManager.getFont(40), Color.WHITE));
        levels.addActor(levelLabel);
        levelLabel.setPosition(WIDTH_SCREEN/2-levelLabel.getWidth()/2,HEIGHT_SCREEN/8*7);
        choosLvlBtn = new ArrayList<>();
        Table table = new Table();
        i = 0;
        int TABLE_WIDTH = WIDTH_SCREEN/3*2,TABLE_HEIGHT = HEIGHT_SCREEN;
        int maxInRow = 3;
        for (final LevelInfo lvlInfo: LevelManager.getLevelInfos()) { //Добавляем кнопки выбора уровня
            choosLvlBtn.add(new TextButton((i+1)+"",textButtonStyle));
            if(!lvlInfo.isOpen()){
                choosLvlBtn.get(i).setTouchable(Touchable.disabled);
                choosLvlBtn.get(i).setColor(Color.GRAY);
            }
            choosLvlBtn.get(i).addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    MediaPlayer.playBtn();
                    game.setScreen(new GameScreen(game,lvlInfo));

                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            table.add(choosLvlBtn.get(i)).size(TABLE_WIDTH/maxInRow,BUTTON_HEIGHT);
            i++;
            if(i% maxInRow == 0)
                table.row();
        }
        table.setPosition(WIDTH_SCREEN/2,0);
        //table.setDebug(true);
        table.setBounds(WIDTH_SCREEN/2-TABLE_WIDTH/2,0,TABLE_WIDTH,TABLE_HEIGHT);
        levels.addActor(table);

        //=================OPTIONS====================
        options = new Group();
        Image optionPanel = new Image( new Texture("sprites/menuPanel.png"));
        optionPanel.setBounds(WIDTH_SCREEN/6,0,WIDTH_MENU,HEIGHT_MENU);
        options.addActor(optionPanel);

        options.setBounds(-WIDTH_SCREEN,0,WIDTH_SCREEN,HEIGHT_SCREEN);

        backOption = new TextButton("Back",textButtonStyle);
        optionItems = new ArrayList<>();
        optionItems.add(new OptionItem("Button Sounds"));
        i = 0;
        for (OptionItem button:optionItems){
            button.setBounds(WIDTH_SCREEN/2-BUTTON_WIDTH/2,HEIGHT_SCREEN/4*2- i*BUTTON_HEIGHT,BUTTON_WIDTH,BUTTON_HEIGHT);
            i++;
            options.addActor(button);
        }
        optionLabel = new Label("OPTIONS",new Label.LabelStyle(FontManager.getFont(40), Color.WHITE));
        backOption.setBounds(WIDTH_SCREEN/2-BUTTON_WIDTH/2,BUTTON_HEIGHT,BUTTON_WIDTH,BUTTON_HEIGHT);
        options.addActor(backOption);
        options.addActor(optionLabel);
        optionLabel.setPosition(WIDTH_SCREEN/2-optionLabel.getWidth()/2,HEIGHT_SCREEN/4*3);


        setBtnListerners();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);//Добавляем обработку нажатии stage
        mainMenu.addActor(gameLabel);
        stage.addActor(mainMenu);
        stage.addActor(levels);
        stage.addActor(options);

    }

    private void setBtnListerners() {
        mainButtons.get(0).addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mainMenu.addAction(moveBy(-WIDTH_SCREEN,0,0.15f));
                levels.addAction(moveBy(-WIDTH_SCREEN,0,0.15f));
                MediaPlayer.playBtn();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        mainButtons.get(1).addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mainMenu.addAction(moveBy(WIDTH_SCREEN,0,0.15f));
                options.addAction(moveBy(WIDTH_SCREEN,0,0.15f));
                MediaPlayer.playBtn();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        backLevel.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mainMenu.addAction(moveBy(WIDTH_SCREEN,0,0.15f));
                levels.addAction(moveBy(WIDTH_SCREEN,0,0.15f));
                MediaPlayer.playBtn();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        backOption.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mainMenu.addAction(moveBy(-WIDTH_SCREEN,0,0.15f));
                options.addAction(moveBy(-WIDTH_SCREEN,0,0.15f));
                MediaPlayer.playBtn();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        optionItems.get(0).getButton().addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MediaPlayer.playBtn();
                Options.setSoundBtn(!Options.isSoundBtn());
                if(Options.isSoundBtn()){
                    optionItems.get(0).getButton().getStyle().up = skin.getDrawable("check");
                }
                else{
                    optionItems.get(0).getButton().getStyle().up = skin.getDrawable("unCheck");
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void show() {    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background,0,0,WIDTH_SCREEN,HEIGHT_SCREEN);
        game.batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {    }

    @Override
    public void pause() {    }

    @Override
    public void resume() {    }

    @Override
    public void hide() {    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
