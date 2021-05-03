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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.gamescreen.GameScreen;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;

public class MainMenuScreen implements Screen {
   private MotherBoardCard game;
    private Stage stage;
    private OrthographicCamera camera;
    private Group mainMenu, levels, options;
    private ArrayList<Button> mainButtons, levelsButton,choosLvlBtn;
    private ArrayList<OptionItem> optionItems;
    private Label menuLabel,levelLabel, optionLabel,gameLabel;
    private Skin skin;
    public static BitmapFont lblfont,bigFont;
    protected static TextButton.TextButtonStyle checkBtnStyle, textButtonStyle;
    private int WIDTH_SCREEN, HEIGHT_SCREEN;
    private TextButton backOption;
    protected static int BUTTON_WIDTH, BUTTON_HEIGHT;
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
        parameter.size = 130;
        bigFont = generator.generateFont(parameter);
        gameLabel = new Label("MotherBoardCard",new Label.LabelStyle(bigFont, Color.WHITE));
        gameLabel.setPosition(WIDTH_SCREEN/10,HEIGHT_SCREEN/8*6);
        parameter.size = 20;

        textButtonStyle = new TextButton.TextButtonStyle();
        BitmapFont font = generator.generateFont(parameter);
        parameter.size = 40;
        lblfont = generator.generateFont(parameter);
        generator.dispose();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.BLACK;

        textButtonStyle.up = skin.getDrawable("up");
        textButtonStyle.down = skin.getDrawable("down");


        checkBtnStyle = new TextButton.TextButtonStyle();
        checkBtnStyle.up =  skin.getDrawable("check");

        //========main menu===============
        mainMenu = new Group();
        mainMenu.setBounds(0,0,WIDTH_SCREEN,HEIGHT_SCREEN);
        mainButtons = new ArrayList<>();

        mainButtons.add(new TextButton("Play",textButtonStyle));
        mainButtons.add(new TextButton("Options",textButtonStyle));
        int i =0;


        BUTTON_WIDTH = WIDTH_SCREEN /3*2;
        BUTTON_HEIGHT = HEIGHT_SCREEN / 6;
        for (Button button: mainButtons) {
            button.setBounds(WIDTH_SCREEN/2 - BUTTON_WIDTH/2,HEIGHT_SCREEN/4*2 - i * BUTTON_HEIGHT,BUTTON_WIDTH,BUTTON_HEIGHT);
            i++;
            mainMenu.addActor(button);
        }
        menuLabel = new Label("MAIN MENU ",new Label.LabelStyle(lblfont, Color.WHITE));
        mainMenu.addActor(menuLabel);
        menuLabel.setPosition(WIDTH_SCREEN/3,HEIGHT_SCREEN/8*5);
        //=========LEVELS========
        levels = new Group();
        levels.setBounds(WIDTH_SCREEN,0,WIDTH_SCREEN,HEIGHT_SCREEN);
        levelsButton = new ArrayList<>();
        levelsButton.add(new TextButton("Back",textButtonStyle));
        i =0;
        for (Button button: levelsButton) {
            button.setBounds(0,HEIGHT_SCREEN/2,WIDTH_SCREEN/3,BUTTON_HEIGHT);
            i++;
            levels.addActor(button);
        }
        levelLabel = new Label("LEVELS",new Label.LabelStyle(lblfont, Color.WHITE));
        levels.addActor(levelLabel);
        levelLabel.setPosition(WIDTH_SCREEN/20,HEIGHT_SCREEN/3*2);
        choosLvlBtn = new ArrayList<>();
        Table table = new Table();
        i =0;
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
        table.setDebug(true);
        table.setBounds(WIDTH_SCREEN/3,0,TABLE_WIDTH,TABLE_HEIGHT);
        levels.addActor(table);

        //=================OPTIONS====================
        options = new Group();
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
        optionLabel = new Label("OPTIONS",new Label.LabelStyle(lblfont, Color.WHITE));
        backOption.setBounds(0,0,BUTTON_WIDTH,BUTTON_HEIGHT);
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
        levelsButton.get(0).addListener(new InputListener(){
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
