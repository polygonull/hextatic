package polygonull.noskulls;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.HashMap;

import polygonull.noskulls.components.Counter;
import polygonull.noskulls.components.Heart;
import polygonull.noskulls.components.IntegerPair;
import polygonull.noskulls.components.Panel;
import polygonull.noskulls.components.SkullAndHeartPanel;
import polygonull.noskulls.helpers.AlphanumericHelper;
import polygonull.noskulls.helpers.State;

import static com.badlogic.gdx.math.MathUtils.random;

public class NoSkulls extends ApplicationAdapter {

	Preferences save;

	private static String alphanumerics = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!:| ";

	// Text
	private static String NO = "NO";
	private static String SKULLS = "SKULLS";
	private static String PLAY = "PLAY";
	private static String RESUME = "RESUME";
	private static String BEST = "BEST";
	private static String LEVEL = "LEVEL";
	private static String LIVES = "LIVES:";
	private static String TRIES = "TRIES:";
	private static String GAME_OVER = "GAME OVER";
	private static String NICE = "NICE!";
	private static String ALMOST = "ALMOST";
	private static String CLOSE = "CLOSE";
	private static String ENOUGH = "ENOUGH";

	// Texture dimensions
	public static float BACKGROUND_WIDTH = 480;
	public static float BACKGROUND_HEIGHT = 864;
	public static float PLAY_WIDTH = 180;
	public static float PLAY_HEIGHT = 180;
	public static float INSTAGRAM_WIDTH = 72;
	public static float INSTAGRAM_HEIGHT = 72;
	public static float SOUND_WIDTH = 72;
	public static float SOUND_HEIGHT = 72;
	public static float BEST_WIDTH = 180;
	public static float BEST_HEIGHT = 72;
	public static float PAUSE_WIDTH = 72;
	public static float PAUSE_HEIGHT = 72;
	public static float SCORE_WIDTH = 376;
	public static float SCORE_HEIGHT = 72;
	public static float CUP_WIDTH = 32;
	public static float CUP_HEIGHT = 32;
	public static float SYMBOL_WIDTH = 26;
	public static float SYMBOL_HEIGHT = 28;
	public static float LIFE_WIDTH = 32;
	public static float LIFE_HEIGHT = 29;
	public static float TRIES_WIDTH = 122;
	public static float TRIES_HEIGHT = 48;
	public static float PANEL_WIDTH = 72;
	public static float PANEL_HEIGHT = 72;
	public static float SETTINGS_WIDTH = 72;
	public static float SETTINGS_HEIGHT = 72;
	public static float MUSIC_WIDTH = 72;
	public static float MUSIC_HEIGHT = 72;
	public static float INFO_WIDTH = 72;
	public static float INFO_HEIGHT = 72;
	public static float WHITE_PIXEL_WIDTH = 1;
	public static float WHITE_PIXEL_HEIGHT = 1;
	public static float BIG_SYMBOL_WIDTH = 40;
	public static float BIG_SYMBOL_HEIGHT = 44;
	public static float GAME_OVER_LEVEL_WIDTH = 180;
	public static float GAME_OVER_LEVEL_HEIGHT = 72;
	public static float PANEL_LIFE_WIDTH = 32;
	public static float PANEL_LIFE_HEIGHT = 30;

	// Game 'magic' numbers
	public static float CAMERA_WIDTH = 480;
	public static float CAMERA_HEIGHT = 854;
	public static int BOARD_WIDTH_COUNT = 6;
	public static int BOARD_HEIGHT_COUNT = 6;
	public static float BOARD_WIDTH_SPACE = 4;
	public static float BOARD_HEIGHT_SPACE = 4;
	public static float UI_WIDTH_SPACE_1 = BOARD_WIDTH_SPACE;
	public static float UI_HEIGHT_SPACE_1 = BOARD_HEIGHT_SPACE;
	public static float UI_WIDTH_SPACE_2 = 13;
	public static float UI_HEIGHT_SPACE_2 = 13;
	public static float UI_WIDTH_SPACE_3 = 20;
	public static float UI_HEIGHT_SPACE_3 = 20;
	public static float UI_WIDTH_SPACE_4 = 50;
	public static float UI_HEIGHT_SPACE_4 = 50;
	public static float MAIN_MENU_WIDTH = PLAY_WIDTH;
	public static float MAIN_MENU_HEIGHT = PLAY_HEIGHT + UI_HEIGHT_SPACE_1 + BEST_HEIGHT;
	public static float GAME_1_WIDTH = BOARD_WIDTH_COUNT * PANEL_WIDTH + (BOARD_WIDTH_COUNT - 1) * BOARD_WIDTH_SPACE;
	public static float GAME_1_HEIGHT = BOARD_HEIGHT_COUNT * PANEL_HEIGHT + (BOARD_HEIGHT_COUNT - 1) * BOARD_HEIGHT_SPACE;
	public static float GAME_2_WIDTH = GAME_1_WIDTH;
	public static float GAME_2_HEIGHT = GAME_1_HEIGHT + UI_HEIGHT_SPACE_2 + TRIES_HEIGHT;
	public static float GAME_4_WIDTH = GAME_1_WIDTH;
	public static float GAME_4_HEIGHT = CAMERA_HEIGHT - (CAMERA_WIDTH - GAME_1_WIDTH);
	public static float GAME_3_WIDTH = GAME_1_WIDTH;
	public static float GAME_3_HEIGHT = GAME_4_HEIGHT - PAUSE_HEIGHT;

	// Texture coordinates
	public static float BACKGROUND_1_X = 0;
	public static float BACKGROUND_1_Y = 0;
	public static float BACKGROUND_2_X = -CAMERA_WIDTH;
	public static float BACKGROUND_2_Y = 0;
	public static float BACKGROUND_3_X = 0;
	public static float BACKGROUND_3_Y = BACKGROUND_HEIGHT;
	public static float BACKGROUND_4_X = -CAMERA_WIDTH;
	public static float BACKGROUND_4_Y = BACKGROUND_HEIGHT;
	public static float MAIN_MENU_X = (CAMERA_WIDTH - MAIN_MENU_WIDTH) / 2;
	public static float MAIN_MENU_Y = (CAMERA_HEIGHT - MAIN_MENU_HEIGHT) / 2 - (PLAY_HEIGHT - UI_HEIGHT_SPACE_1 - BEST_HEIGHT) / 2;
	public static float PLAY_X = MAIN_MENU_X;
	public static float PLAY_Y = MAIN_MENU_Y + (MAIN_MENU_HEIGHT - PLAY_HEIGHT);
	public static float BEST_X = MAIN_MENU_X;
	public static float BEST_Y = MAIN_MENU_Y;
	public static float GAME_4_X = (CAMERA_WIDTH - GAME_4_WIDTH) / 2;
	public static float GAME_4_Y = (CAMERA_HEIGHT - GAME_4_HEIGHT) / 2;
	public static float GAME_3_X = (CAMERA_WIDTH - GAME_3_WIDTH) / 2;
	public static float GAME_3_Y = GAME_4_Y;
	public static float GAME_2_X = (CAMERA_WIDTH - GAME_2_WIDTH) / 2;
	public static float GAME_2_Y = GAME_3_Y + (GAME_3_HEIGHT - GAME_2_HEIGHT) / 2;
	public static float GAME_1_X = (CAMERA_WIDTH - GAME_2_WIDTH) / 2;
	public static float GAME_1_Y = GAME_2_Y;
	public static float PAUSE_X = GAME_4_X;
	public static float PAUSE_Y = CAMERA_HEIGHT - GAME_4_Y - PAUSE_HEIGHT;
	public static float SCORE_X = GAME_4_X + PAUSE_WIDTH + UI_WIDTH_SPACE_1;
	public static float SCORE_Y = CAMERA_HEIGHT - GAME_4_Y  - SCORE_HEIGHT;
	public static float GAME_CUP_X = SCORE_X + (SCORE_WIDTH - CUP_WIDTH) / 2;
	public static float GAME_CUP_Y = SCORE_Y + (SCORE_HEIGHT - CUP_HEIGHT) / 2;
	public static float GAME_BEST_LABEL_X = GAME_CUP_X - ((SCORE_WIDTH - CUP_WIDTH) / 2 + BIG_SYMBOL_WIDTH * 0.4f * BEST.length()) / 2;
	public static float GAME_BEST_LABEL_Y = SCORE_Y - UI_HEIGHT_SPACE_2 - BIG_SYMBOL_HEIGHT * 0.4f;
	public static float GAME_LEVEL_LABEL_X = GAME_CUP_X + CUP_WIDTH  + ((SCORE_WIDTH - CUP_WIDTH) / 2 - BIG_SYMBOL_WIDTH * 0.4f * LEVEL.length()) / 2;
	public static float GAME_LEVEL_LABEL_Y = SCORE_Y - UI_HEIGHT_SPACE_2 - BIG_SYMBOL_HEIGHT * 0.4f;
	public static float LIFE_1_X = GAME_2_X + UI_WIDTH_SPACE_2;
	public static float LIFE_1_Y = GAME_2_Y + GAME_1_HEIGHT + UI_HEIGHT_SPACE_2 + (TRIES_HEIGHT - LIFE_HEIGHT) / 2;
	public static float LIFE_2_X = GAME_2_X + UI_WIDTH_SPACE_2 * 2 + LIFE_WIDTH;
	public static float LIFE_2_Y = LIFE_1_Y;
	public static float LIFE_3_X = GAME_2_X + UI_WIDTH_SPACE_2 * 3 + LIFE_WIDTH * 2;
	public static float LIFE_3_Y = LIFE_1_Y;
	public static float TRIES_X = CAMERA_WIDTH - GAME_1_X - TRIES_WIDTH - UI_WIDTH_SPACE_2;
	public static float TRIES_Y = GAME_2_Y + GAME_1_HEIGHT + UI_HEIGHT_SPACE_2;
	public static float PANEL_1_X = GAME_1_X;
	public static float PANEL_1_Y = GAME_1_Y + GAME_1_HEIGHT - PANEL_HEIGHT;
	public static float SETTINGS_X = GAME_4_X;
	public static float SETTINGS_Y = CAMERA_HEIGHT - GAME_4_Y - PAUSE_HEIGHT;
	public static float MUSIC_X = GAME_4_X;
	public static float MUSIC_Y = SETTINGS_Y - SETTINGS_HEIGHT - UI_HEIGHT_SPACE_1;
	public static float SOUND_X = GAME_4_X;
	public static float SOUND_Y = MUSIC_Y - MUSIC_HEIGHT - UI_HEIGHT_SPACE_1;
	public static float INFO_X = GAME_4_X;
	public static float INFO_Y = SOUND_Y - SOUND_HEIGHT - UI_HEIGHT_SPACE_1;
	public static float INSTAGRAM_X = GAME_4_X;
	public static float INSTAGRAM_Y = INFO_Y - INFO_HEIGHT - UI_HEIGHT_SPACE_1;
	public static float GAME_OVER_PLAY_X = MAIN_MENU_X;
	public static float GAME_OVER_PLAY_Y = PLAY_Y - BEST_HEIGHT;
	public static float GAME_OVER_BEST_X = MAIN_MENU_X;
	public static float GAME_OVER_BEST_Y = BEST_Y - BEST_HEIGHT;
	public static float GAME_OVER_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * GAME_OVER.length()) / 2;
	public static float GAME_OVER_LABEL_Y = SETTINGS_Y - BIG_SYMBOL_HEIGHT - UI_HEIGHT_SPACE_4;
	public static float GAME_OVER_LEVEL_X = MAIN_MENU_X;
	public static float GAME_OVER_LEVEL_Y = GAME_OVER_LABEL_Y - BEST_HEIGHT - UI_HEIGHT_SPACE_4;
	public static float NICE_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * NICE.length()) / 2;
	public static float NICE_LABEL_Y = 	(CAMERA_HEIGHT - BIG_SYMBOL_HEIGHT) / 2;
	public static float ALMOST_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * ALMOST.length()) / 2;
	public static float ALMOST_LABEL_Y = (CAMERA_HEIGHT - BIG_SYMBOL_HEIGHT) / 2;
	public static float CLOSE_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * CLOSE.length()) / 2;
	public static float CLOSE_LABEL_Y = (CAMERA_HEIGHT - BIG_SYMBOL_HEIGHT) / 2 + BIG_SYMBOL_HEIGHT / 2 + UI_HEIGHT_SPACE_2;
	public static float ENOUGH_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * ENOUGH.length()) / 2;
	public static float ENOUGH_LABEL_Y = (CAMERA_HEIGHT - BIG_SYMBOL_HEIGHT) / 2 - BIG_SYMBOL_HEIGHT / 2 - UI_HEIGHT_SPACE_2;
	public static float PLAY_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * PLAY.length()) / 2;
	public static float PLAY_LABEL_Y = MAIN_MENU_Y + MAIN_MENU_HEIGHT + UI_WIDTH_SPACE_4;
	public static float RESUME_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * RESUME.length()) / 2;
	public static float RESUME_LABEL_Y = PLAY_LABEL_Y;
	public static float NO_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * NO.length()) / 2;
	public static float NO_LABEL_Y = MAIN_MENU_Y - UI_HEIGHT_SPACE_4 - BIG_SYMBOL_HEIGHT;
	public static float SKULLS_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * SKULLS.length()) / 2;
	public static float SKULLS_LABEL_Y = NO_LABEL_Y - UI_HEIGHT_SPACE_2- BIG_SYMBOL_HEIGHT;
	public static float LEVEL_REACHED_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * 0.4f * LEVEL.length()) / 2;
	public static float LEVEL_REACHED_LABEL_Y = GAME_OVER_LEVEL_Y + BEST_HEIGHT + UI_HEIGHT_SPACE_2;

	// Game state
	private State state = State.MAIN_MENU;
	private int level = 1;
	private int best = 1;
	private int round = 1;
	private int prevTries = 1;
	private int tries = 1;
	private int lives = 3;
	private int skulls = 0;

	private AlphanumericHelper alphanumericHelper;

	// Batch
	private SpriteBatch batch;

	// Textures
	private Texture backgroundSprite;
	private Texture playSprite;
	private Texture bestSprite;
	private Texture pauseSprite;
	private Texture scoreSprite;
	private Texture cupSprite;
	private Texture symbolsSprite;
	private Texture lifeSprite;
	private Texture triesSprite;
	private Texture panelsSprite;
	private Texture settingsSprite;
	private Texture musicSprite;
	private Texture soundSprite;
	private Texture infoSprite;
	private Texture instagramSprite;
	private Texture whitePixel;
	private Texture bigSymbolsSprite;
	private Texture panelLifeSprite;

	// Touch areas
	private Rectangle settingsTouchArea;
	private Rectangle musicTouchArea;
	private Rectangle soundTouchArea;
	private Rectangle infoTouchArea;
	private Rectangle instagramTouchArea;
	private Rectangle playTouchArea;
	private Rectangle pauseTouchArea;
	private Rectangle[][] panelsTouchArea;
	private Rectangle gameOverPlayArea;

	private OrthographicCamera camera;

	private SkullAndHeartPanel[][] panelModels;
	private SkullAndHeartPanel[][] prevPanelModels;
	private Heart[][] heartModels;
	private Panel settingsModel;
	private Panel musicModel;
	private Panel soundModel;

	private IntegerPair[][] heartCoordinates;

	private boolean flipping = false;
	private boolean reset = false;

	private HashMap<String, Counter> counterMap;

	// Sounds
	Sound flipSound;
	Sound resetSound;
    Sound playSound;
	Sound pauseSound;
	Sound gameOverSound;
	Sound successSound;
	Sound failSound;

	private void drawText(Texture fontTexture, float x, float y, float charWidth, float charHeight, float scale, String text) {

		ArrayList<Integer> textOffsets = alphanumericHelper.stringToOffsets(text);
		for(int i = 0; i < textOffsets.size(); i++) {
			batch.draw(fontTexture,  x + i * charWidth * scale, y, 0, 0, charWidth, charHeight, scale, scale, 0, (int)(textOffsets.get(i) * charWidth), 0, (int)charWidth, (int)charHeight, false, false);
		}

	}

	private void loadGame() {

		save = Gdx.app.getPreferences("no_skulls_save");

		level = save.getInteger("level", 1);
		best = save.getInteger("best", 1);
		round = save.getInteger("round", 1);
		prevTries = save.getInteger("prevTries", 1);
		tries = save.getInteger("tries", 1);
		lives = save.getInteger("lives", 3);
		state = State.valueOf(save.getString("state", State.MAIN_MENU.name()));
		String panelModelsJson = save.getString("panels", "");
		String prevPanelModelsJson = save.getString("prevPanels", "");
		if(panelModelsJson.isEmpty() || prevPanelModelsJson.isEmpty()) {
			createBoard(tries);
		} else {
			recreateBoard(panelModelsJson, prevPanelModelsJson);
		}

		switch (state) {
			case MAIN_MENU:
				break;
			case GAME:

				boolean won = counterMap.get("fail_panels").getCount() == counterMap.get("fail_panels").getBottomThreshold();

				if(won) {
					level++;
					round++;
					if(level > best) {
						best = level;
					}
					if(round > prevTries * prevTries) {
						tries = prevTries + 1;
						round = 1;
					} else {
						tries = prevTries;
					}
					prevTries = tries;
					lives = 3;
					clearBoard();
					createBoard(tries);
				}

				if(!won && tries == 0) {
					lives--;
					tries = prevTries;
					resetBoardNoFlip();
				}

				state = State.PAUSE;

				if(!won && lives == 0) {
					counterMap.get("fail_panels").reset();
					level = 1;
					round = 1;
					tries = 1;
					prevTries = 1;
					lives = 3;
					clearBoard();
					createBoard(tries);
					state = State.MAIN_MENU;
				}

				break;
			case PAUSE:
				break;
			case FAIL:
				lives--;
				tries = prevTries;
				resetBoardNoFlip();
				state = State.PAUSE;
				break;
			case SUCCESS:
				level++;
				round++;
				if(level > best) {
					best = level;
				}
				if(round > prevTries * prevTries) {
					tries = prevTries + 1;
					round = 1;
				} else {
					tries = prevTries;
				}
				prevTries = tries;
				lives = 3;
				clearBoard();
				createBoard(tries);
				state = State.PAUSE;
				break;
			case GAME_OVER:
				counterMap.get("fail_panels").reset();
				level = 1;
				round = 1;
				tries = 1;
				prevTries = 1;
				lives = 3;
				clearBoard();
				createBoard(tries);
				state = State.MAIN_MENU;
				break;
		}

	}

	private String boardToString(SkullAndHeartPanel[][] panelsAsModels) {

		String boardToString = "";

		for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
			for(int j = 0; j < BOARD_WIDTH_COUNT; j++) {
				boardToString = boardToString + (panelsAsModels[i][j].isActive() ? 1 : 0);
			}
		}

		return boardToString;

	}

	private void recreateBoard(String panelsAsString, String prevPanelsAsString) {

		for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
			for(int j = 0; j < BOARD_WIDTH_COUNT; j++) {
				if(panelsAsString.charAt(i * 6 + j) == '0') {
					panelModels[i][j].setActive(false);
					panelModels[i][j].setState(0);
					panelModels[i][j].setPrevState(0);
				} else {
					panelModels[i][j].setActive(true);
					panelModels[i][j].setState(16);
					panelModels[i][j].setPrevState(16);
				}
				if(!panelModels[i][j].isActive()) {
					counterMap.get("fail_panels").step();
				}
				if(prevPanelsAsString.charAt(i * 6 + j) == '0') {
					prevPanelModels[i][j].setActive(false);
					prevPanelModels[i][j].setState(0);
					prevPanelModels[i][j].setPrevState(0);
				} else {
					prevPanelModels[i][j].setActive(true);
					prevPanelModels[i][j].setState(16);
					prevPanelModels[i][j].setPrevState(16);
				}
			}
		}

	}

	private void clearBoard() {

		for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
			for(int j = 0; j < BOARD_WIDTH_COUNT; j++) {
				panelModels[i][j].setActive(true);
				panelModels[i][j].setState(16);
				panelModels[i][j].setPrevState(16);
				prevPanelModels[i][j].setActive(true);
				prevPanelModels[i][j].setState(16);
				prevPanelModels[i][j].setPrevState(16);
			}
		}

	}

	private void createBoard(int tries) {

		while(tries != 0) {
			int i = random.nextInt(BOARD_HEIGHT_COUNT);
			int j = random.nextInt(BOARD_WIDTH_COUNT);
			panelModels[i][j].flip();
			float count = panelModels[i][j].isActive() ? counterMap.get("fail_panels").stepDown() : counterMap.get("fail_panels").step();
			for (SkullAndHeartPanel panel : panelModels[i][j].getNeighbours()) {
				panel.flip();
				count = panel.isActive() ? counterMap.get("fail_panels").stepDown() : counterMap.get("fail_panels").step();
			}
			prevPanelModels[i][j].flip();
			for (SkullAndHeartPanel panel : prevPanelModels[i][j].getNeighbours()) {
				panel.flip();
			}
			tries--;
		}

	}

	public void resetBoard() {

		for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
			for (int j = 0; j < BOARD_WIDTH_COUNT; j++) {
				if (panelModels[i][j].isActive() != prevPanelModels[i][j].isActive()) {
					panelModels[i][j].setJustFlipped(true);
					panelModels[i][j].setActive(!panelModels[i][j].isActive());
					flipping = true;
					float count = panelModels[i][j].isActive() ? counterMap.get("fail_panels").stepDown() : counterMap.get("fail_panels").step();
				}
			}
		}

	}

	public void resetBoardNoFlip() {

		for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
			for (int j = 0; j < BOARD_WIDTH_COUNT; j++) {
				if (panelModels[i][j].isActive() != prevPanelModels[i][j].isActive()) {
					panelModels[i][j].flip();
					float count = panelModels[i][j].isActive() ? counterMap.get("fail_panels").stepDown() : counterMap.get("fail_panels").step();
				}
			}
		}

	}

	public void checkFlipping(Panel panel) {
		if(panel.isJustFlipped()) {
			panel.setPrevState(panel.getState());
			panel.changeState();
			panel.setJustFlipped(false);
		} else if(panel.getPrevState() < panel.getState()) {
			panel.changeState();
			if(panel.getState() == panel.getPrevState() + 16) {
				flipping = false;
				if(panel.getState() == 32) {
					panel.setState(0);
				}
				panel.setPrevState(panel.getState());
			}
		}
	}

	@Override
	public void create() {

		flipSound = Gdx.audio.newSound(Gdx.files.internal("flip.mp3"));
		resetSound = Gdx.audio.newSound(Gdx.files.internal("reset.mp3"));
        playSound = Gdx.audio.newSound(Gdx.files.internal("play.ogg"));
		pauseSound = Gdx.audio.newSound(Gdx.files.internal("pause.ogg"));
		gameOverSound = Gdx.audio.newSound(Gdx.files.internal("game_over.ogg"));
		successSound = Gdx.audio.newSound(Gdx.files.internal("success.ogg"));
		failSound = Gdx.audio.newSound(Gdx.files.internal("fail.ogg"));

		camera = new OrthographicCamera();
		camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
		batch = new SpriteBatch();
		panelsTouchArea = new Rectangle[BOARD_WIDTH_COUNT][BOARD_HEIGHT_COUNT];
		panelModels = new SkullAndHeartPanel[BOARD_WIDTH_COUNT][BOARD_HEIGHT_COUNT];
		prevPanelModels = new SkullAndHeartPanel[BOARD_WIDTH_COUNT][BOARD_HEIGHT_COUNT];
		settingsModel = new Panel();
		musicModel = new Panel();
		soundModel = new Panel();
		alphanumericHelper = new AlphanumericHelper(alphanumerics);

		heartModels = new Heart[BOARD_WIDTH_COUNT][BOARD_HEIGHT_COUNT];

		// Load textures
		backgroundSprite = new Texture("background.png");
		playSprite = new Texture("play.png");
		bestSprite = new Texture("best.png");
		pauseSprite = new Texture("pause.png");
		scoreSprite = new Texture("score.png");
		cupSprite = new Texture("cup.png");
		symbolsSprite = new Texture("symbols.png");
		lifeSprite = new Texture("life.png");
		triesSprite = new Texture("tries.png");
		panelsSprite = new Texture("panels.png");
		settingsSprite = new Texture("settings.png");
		musicSprite = new Texture("music.png");
		soundSprite = new Texture("sound.png");
		infoSprite = new Texture("info.png");
		instagramSprite = new Texture("instagram.png");
		whitePixel = new Texture("white_pixel.png");
		bigSymbolsSprite = new Texture("symbols_grey.png");
		panelLifeSprite = new Texture("life2.png");

		// Initialize touch areas
		playTouchArea = new Rectangle(PLAY_X, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
		pauseTouchArea = new Rectangle(PAUSE_X, PAUSE_Y, PAUSE_WIDTH, PAUSE_HEIGHT);
		settingsTouchArea = new Rectangle(SETTINGS_X, SETTINGS_Y, SETTINGS_WIDTH, SETTINGS_HEIGHT);
		musicTouchArea = new Rectangle(MUSIC_X, MUSIC_Y, MUSIC_WIDTH, MUSIC_HEIGHT);
		soundTouchArea = new Rectangle(SOUND_X, SOUND_Y, SOUND_WIDTH, SOUND_HEIGHT);

		// Initialize panel touch areas, panel models and heart models
		float start_x = PANEL_1_X;
		float start_y = PANEL_1_Y;
		for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
			for(int j = 0; j < BOARD_WIDTH_COUNT; j++) {
				panelsTouchArea[i][j] = new Rectangle(start_x, start_y, PANEL_WIDTH, PANEL_HEIGHT);
				panelModels[i][j] = new SkullAndHeartPanel(i, j);
				prevPanelModels[i][j] = new SkullAndHeartPanel(i, j);
				heartModels[i][j] = new Heart((int)start_x + 20, (int)start_y + 22);
				start_x += PANEL_WIDTH + BOARD_WIDTH_SPACE;
			}
			start_x = PANEL_1_X;
			start_y -= PANEL_HEIGHT + BOARD_HEIGHT_SPACE;
		}

		// Initialize panel neighbours
		for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
			for (int j = 0; j < BOARD_WIDTH_COUNT; j++) {
				panelModels[i][j].populateNeighbours(panelModels);
				prevPanelModels[i][j].populateNeighbours(prevPanelModels);
			}
		}

		counterMap = new HashMap<>();
		counterMap.put("background", new Counter(0, 0.9f, true, CAMERA_WIDTH, -1));
		counterMap.put("settings", new Counter(0, 1, true, 16, 0));
		counterMap.put("fail", new Counter(0, 1, true, 105, 0));
		counterMap.put("fail_panels", new Counter(0, 1, true, 36, 0));
		counterMap.put("fail_panel_pulse", new Counter(1, 0.008f, true, 1.04f, 0.96f));
		counterMap.put("fail_panel_pulse_count", new Counter(0, 1, true, 2, 0));
		counterMap.put("success", new Counter(0, 1, true, 105, 0));
		counterMap.put("success_panel_heart_pulse", new Counter(1, 0.004f, true, 1.12f, 1));
		counterMap.put("success_heart_pulse", new Counter(1, 0.11f, 1.05f, true, -1, -1));

		loadGame();

	}

	public void startRender() {

		Gdx.gl.glClearColor(255, 255, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();

	}

	public void endRender() {

		batch.end();

	}

	public void renderSettings() {

		checkFlipping(settingsModel);

		batch.draw(settingsSprite, SETTINGS_X, SETTINGS_Y, (int)(settingsModel.getState() * SETTINGS_WIDTH), 0, (int)SETTINGS_WIDTH, (int)SETTINGS_HEIGHT);

		if(!settingsModel.isActive()) {
			if(counterMap.get("settings").getCount() < counterMap.get("settings").getTopThreshold()) {
				counterMap.get("settings").step();
			}
		} else {
			if(counterMap.get("settings").getCount() > counterMap.get("settings").getBottomThreshold()) {
				counterMap.get("settings").step();
			}
		}

		if(counterMap.get("settings").getCount() > counterMap.get("settings").getTopThreshold() -
				(counterMap.get("settings").getTopThreshold() / 4) * 4 + counterMap.get("settings").getStep()) {
			checkFlipping(musicModel);
			batch.draw(musicSprite, MUSIC_X, MUSIC_Y, (int) (musicModel.getState() * MUSIC_WIDTH), 0, (int) MUSIC_WIDTH, (int) MUSIC_HEIGHT);
		}
		if(counterMap.get("settings").getCount() > counterMap.get("settings").getTopThreshold() -
				(counterMap.get("settings").getTopThreshold() / 4) * 3 + counterMap.get("settings").getStep()) {
			checkFlipping(soundModel);
			batch.draw(soundSprite, SOUND_X, SOUND_Y, (int) (soundModel.getState() * SOUND_WIDTH), 0, (int) SOUND_WIDTH, (int) SOUND_HEIGHT);
		}
		if(counterMap.get("settings").getCount() > counterMap.get("settings").getTopThreshold() -
				(counterMap.get("settings").getTopThreshold() / 4) * 2 + counterMap.get("settings").getStep()) {
			batch.draw(infoSprite, INFO_X, INFO_Y);
		}
		if(counterMap.get("settings").getCount() > counterMap.get("settings").getTopThreshold() -
				(counterMap.get("settings").getTopThreshold() / 4) * 1 + counterMap.get("settings").getStep()) {
			batch.draw(instagramSprite, INSTAGRAM_X, INSTAGRAM_Y);
		}

	}

	public void renderMainMenu() {

		batch.draw(backgroundSprite, BACKGROUND_1_X + counterMap.get("background").getCount(), BACKGROUND_1_Y - counterMap.get("background").getCount());
		batch.draw(backgroundSprite, BACKGROUND_2_X + counterMap.get("background").getCount(), BACKGROUND_2_Y - counterMap.get("background").getCount());
		batch.draw(backgroundSprite, BACKGROUND_3_X + counterMap.get("background").getCount(), BACKGROUND_3_Y - counterMap.get("background").getCount());
		batch.draw(backgroundSprite, BACKGROUND_4_X + counterMap.get("background").getCount(), BACKGROUND_4_Y - counterMap.get("background").getCount());

		batch.draw(playSprite, PLAY_X, PLAY_Y, 0, 0, PLAY_WIDTH, PLAY_HEIGHT, 1f, 1f, 0, 0, 0, (int)PLAY_WIDTH, (int)PLAY_HEIGHT, false, false);
		batch.draw(bestSprite, BEST_X, BEST_Y);
		batch.draw(cupSprite, (BEST_X + (BEST_WIDTH + CUP_WIDTH + UI_WIDTH_SPACE_3 - SYMBOL_WIDTH * Integer.toString(best).length()) / 2) - CUP_WIDTH - UI_WIDTH_SPACE_3, BEST_Y + (BEST_HEIGHT - CUP_HEIGHT) / 2);
		drawText(symbolsSprite, BEST_X + (BEST_WIDTH + CUP_WIDTH + UI_WIDTH_SPACE_3 - SYMBOL_WIDTH * Integer.toString(best).length()) / 2, BEST_Y + (BEST_HEIGHT - SYMBOL_HEIGHT) / 2, SYMBOL_WIDTH, SYMBOL_HEIGHT, 1, Integer.toString(best));

		if(state == State.MAIN_MENU) {
			batch.setColor(255, 255, 255, 0.9f);
			drawText(bigSymbolsSprite, PLAY_LABEL_X, PLAY_LABEL_Y, BIG_SYMBOL_WIDTH, BIG_SYMBOL_HEIGHT, 1, PLAY);
			batch.setColor(Color.WHITE);
		}

		renderSettings();

		counterMap.get("background").step();

	}

	public void renderPause() {

		batch.setColor(255, 255, 255, 0.9f);
		drawText(bigSymbolsSprite, RESUME_LABEL_X, RESUME_LABEL_Y, BIG_SYMBOL_WIDTH, BIG_SYMBOL_HEIGHT, 1, RESUME);
		batch.setColor(Color.WHITE);

	}

	public void renderFail() {

		if(counterMap.get("fail_panel_pulse").getCount() <= counterMap.get("fail_panel_pulse").getBottomThreshold()
			&& counterMap.get("fail_panel_pulse_count").getCount() != counterMap.get("fail_panel_pulse_count").getTopThreshold()) {
			counterMap.get("fail_panel_pulse_count").step();
		}

		if(counterMap.get("fail_panel_pulse_count").getCount() < counterMap.get("fail_panel_pulse_count").getTopThreshold()) {
			float pulse = counterMap.get("fail_panel_pulse").step();
			for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
				for (int j = 0; j < BOARD_WIDTH_COUNT; j++) {
					if(!panelModels[i][j].isActive()) {
						batch.draw(panelsSprite, panelsTouchArea[i][j].x + (PANEL_WIDTH - PANEL_WIDTH * pulse) / 2, panelsTouchArea[i][j].y + (PANEL_HEIGHT - PANEL_HEIGHT * pulse) / 2, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, pulse, pulse, 0, (int) PANEL_WIDTH * panelModels[i][j].getState(), 0, (int)PANEL_WIDTH, (int)PANEL_HEIGHT, false,false);
					}
				}
			}
		} else {
			if(lives - 1 != 0 && Math.abs(counterMap.get("fail_panel_pulse").getCount() - counterMap.get("fail_panel_pulse").getInitialCount()) < 0.0001) {
				if(!reset) {
					resetSound.play();
					resetBoard();
					reset = true;
				}
				for (int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
					for (int j = 0; j < BOARD_WIDTH_COUNT; j++) {
						batch.draw(panelsSprite, panelsTouchArea[i][j].x, panelsTouchArea[i][j].y, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, 1, 1, 0, (int) PANEL_WIDTH * panelModels[i][j].getState(), 0, (int) PANEL_WIDTH, (int) PANEL_HEIGHT, false, false);
						checkFlipping(panelModels[i][j]);
					}
				}
			} else {
				float pulse = 1;
				if(Math.abs(counterMap.get("fail_panel_pulse").getCount() - counterMap.get("fail_panel_pulse").getInitialCount()) > 0.0001) {
					pulse = counterMap.get("fail_panel_pulse").step();
				}
				for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
					for (int j = 0; j < BOARD_WIDTH_COUNT; j++) {
						if(!panelModels[i][j].isActive()) {
							batch.draw(panelsSprite, panelsTouchArea[i][j].x + (PANEL_WIDTH - PANEL_WIDTH * pulse) / 2, panelsTouchArea[i][j].y + (PANEL_HEIGHT - PANEL_HEIGHT * pulse) / 2, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, pulse, pulse, 0, (int) PANEL_WIDTH * panelModels[i][j].getState(), 0, (int)PANEL_WIDTH, (int)PANEL_HEIGHT, false,false);
						}
					}
				}
			}
		}

		batch.draw(whitePixel, 0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		if(skulls <= counterMap.get("fail_panels").getTopThreshold() / 3) {
			drawText(bigSymbolsSprite, ALMOST_LABEL_X, ALMOST_LABEL_Y, BIG_SYMBOL_WIDTH, BIG_SYMBOL_HEIGHT, 1, ALMOST);
		} else {
			drawText(bigSymbolsSprite, CLOSE_LABEL_X, CLOSE_LABEL_Y, BIG_SYMBOL_WIDTH, BIG_SYMBOL_HEIGHT, 1, CLOSE);
			drawText(bigSymbolsSprite, ENOUGH_LABEL_X, ENOUGH_LABEL_Y, BIG_SYMBOL_WIDTH, BIG_SYMBOL_HEIGHT, 1, ENOUGH);
		}

	}

	public void renderSuccess() {

		float pulse = 1;

		if(counterMap.get("success").getCount() == counterMap.get("success").getBottomThreshold() ||
				counterMap.get("success_panel_heart_pulse").getCount() >= counterMap.get("success_panel_heart_pulse").getBottomThreshold()) {
			pulse = counterMap.get("success_panel_heart_pulse").step();
		}

		if(counterMap.get("success_panel_heart_pulse").getCount() >= counterMap.get("success_panel_heart_pulse").getBottomThreshold()) {

			for (int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
				for (int j = 0; j < BOARD_WIDTH_COUNT; j++) {
					batch.draw(panelLifeSprite,
							heartModels[i][j].getX() + (PANEL_LIFE_WIDTH - PANEL_LIFE_WIDTH * pulse) / 2,
							heartModels[i][j].getY() + (PANEL_LIFE_HEIGHT - PANEL_LIFE_HEIGHT * pulse) / 2,
							0, 0, PANEL_LIFE_WIDTH, PANEL_LIFE_HEIGHT, pulse, pulse, 0,
							0, 0, (int) PANEL_LIFE_WIDTH, (int) PANEL_LIFE_HEIGHT, false, false);
				}
			}

		} else {

			pulse = counterMap.get("success_heart_pulse").step();
			batch.draw(lifeSprite,  (CAMERA_WIDTH - LIFE_WIDTH * pulse) / 2, (CAMERA_HEIGHT - LIFE_HEIGHT * pulse) / 2, 0, 0, LIFE_WIDTH, LIFE_HEIGHT, pulse, pulse, 0, 0, 0, (int) LIFE_WIDTH, (int) LIFE_HEIGHT, false, false);

		}

		batch.draw(whitePixel, 0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		drawText(bigSymbolsSprite, NICE_LABEL_X, NICE_LABEL_Y, BIG_SYMBOL_WIDTH, BIG_SYMBOL_HEIGHT, 1, NICE);

	}

	public void renderGameOver() {

		batch.draw(backgroundSprite, BACKGROUND_1_X, BACKGROUND_1_Y);
		batch.draw(backgroundSprite, BACKGROUND_2_X, BACKGROUND_2_Y);
		batch.draw(backgroundSprite, BACKGROUND_3_X, BACKGROUND_3_Y);
		batch.draw(backgroundSprite, BACKGROUND_4_X, BACKGROUND_4_Y);

		batch.setColor(255, 255, 255, 0.8f);
		drawText(bigSymbolsSprite, GAME_OVER_LABEL_X, GAME_OVER_LABEL_Y, BIG_SYMBOL_WIDTH, BIG_SYMBOL_HEIGHT, 1, GAME_OVER);
		drawText(bigSymbolsSprite, LEVEL_REACHED_LABEL_X, LEVEL_REACHED_LABEL_Y, BIG_SYMBOL_WIDTH, BIG_SYMBOL_HEIGHT, 0.4f, LEVEL);
		batch.setColor(Color.WHITE);

		batch.draw(bestSprite, GAME_OVER_LEVEL_X, GAME_OVER_LEVEL_Y);
		drawText(symbolsSprite, GAME_OVER_LEVEL_X + (BEST_WIDTH - SYMBOL_WIDTH * Integer.toString(level).length()) / 2, GAME_OVER_LEVEL_Y + (BEST_HEIGHT - SYMBOL_HEIGHT) / 2, SYMBOL_WIDTH, SYMBOL_HEIGHT, 1, Integer.toString(level));
		batch.draw(bestSprite, BEST_X, BEST_Y);
		batch.draw(cupSprite, BEST_X + (BEST_WIDTH + CUP_WIDTH + UI_WIDTH_SPACE_3 - SYMBOL_WIDTH * Integer.toString(best).length()) / 2 - CUP_WIDTH - UI_WIDTH_SPACE_3, BEST_Y + (BEST_HEIGHT - CUP_HEIGHT) / 2);
		drawText(symbolsSprite, BEST_X + (BEST_WIDTH + CUP_WIDTH + UI_WIDTH_SPACE_3 - SYMBOL_WIDTH * Integer.toString(best).length()) / 2, BEST_Y + (BEST_HEIGHT - SYMBOL_HEIGHT) / 2, SYMBOL_WIDTH, SYMBOL_HEIGHT, 1, Integer.toString(best));
		batch.draw(playSprite, PLAY_X, PLAY_Y, 0, 0, PLAY_WIDTH, PLAY_HEIGHT, 1f, 1f, 0, 0, 0, (int)PLAY_WIDTH, (int)PLAY_HEIGHT, false, false);

		renderSettings();

	}

	public void renderGame() {

		batch.draw(backgroundSprite, BACKGROUND_1_X + counterMap.get("background").getCount(), BACKGROUND_1_Y - counterMap.get("background").getCount());
		batch.draw(backgroundSprite, BACKGROUND_2_X + counterMap.get("background").getCount(), BACKGROUND_2_Y - counterMap.get("background").getCount());
		batch.draw(backgroundSprite, BACKGROUND_3_X + counterMap.get("background").getCount(), BACKGROUND_3_Y - counterMap.get("background").getCount());
		batch.draw(backgroundSprite, BACKGROUND_4_X + counterMap.get("background").getCount(), BACKGROUND_4_Y - counterMap.get("background").getCount());

		batch.draw(pauseSprite, PAUSE_X, PAUSE_Y);
		batch.draw(scoreSprite, SCORE_X, SCORE_Y);
		batch.draw(cupSprite, GAME_CUP_X, GAME_CUP_Y);
		drawText(symbolsSprite, GAME_CUP_X - ((SCORE_WIDTH - CUP_WIDTH) / 2 + SYMBOL_WIDTH * Integer.toString(best).length()) / 2, SCORE_Y + (SCORE_HEIGHT - SYMBOL_HEIGHT) / 2, SYMBOL_WIDTH, SYMBOL_HEIGHT, 1, Integer.toString(best));
		drawText(symbolsSprite, GAME_CUP_X + CUP_WIDTH  + ((SCORE_WIDTH - CUP_WIDTH) / 2 - SYMBOL_WIDTH * Integer.toString(level).length()) / 2, SCORE_Y + (SCORE_HEIGHT - SYMBOL_HEIGHT) / 2, SYMBOL_WIDTH, SYMBOL_HEIGHT, 1, Integer.toString(level));
		batch.setColor(255, 255, 255, 0.9f);
		drawText(bigSymbolsSprite, GAME_BEST_LABEL_X, GAME_BEST_LABEL_Y, BIG_SYMBOL_WIDTH, BIG_SYMBOL_HEIGHT, 0.4f, BEST);
		drawText(bigSymbolsSprite, GAME_LEVEL_LABEL_X, GAME_LEVEL_LABEL_Y, BIG_SYMBOL_WIDTH, BIG_SYMBOL_HEIGHT, 0.4f, LEVEL);
		batch.setColor(Color.WHITE);

		batch.draw(triesSprite, TRIES_X, TRIES_Y);
		drawText(symbolsSprite, TRIES_X + (TRIES_WIDTH - SYMBOL_WIDTH * Integer.toString(tries).length()) / 2, TRIES_Y + (TRIES_HEIGHT - SYMBOL_HEIGHT) / 2, SYMBOL_WIDTH, SYMBOL_HEIGHT, 1, Integer.toString(tries));
		if(lives > 0) {
			batch.draw(lifeSprite, LIFE_1_X, LIFE_1_Y);
		}
		if(lives > 1) {
			batch.draw(lifeSprite, LIFE_2_X, LIFE_2_Y);
		}
		if(lives > 2) {
			batch.draw(lifeSprite, LIFE_3_X, LIFE_3_Y);
		}

		for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
			for(int j = 0; j < BOARD_WIDTH_COUNT; j++) {
				if(state == State.FAIL) {
					if(!panelModels[i][j].isActive()) {
						continue;
					}
				}
				batch.draw(panelsSprite, panelsTouchArea[i][j].x, panelsTouchArea[i][j].y, (int)PANEL_WIDTH * panelModels[i][j].getState(), 0, (int)PANEL_WIDTH, (int)PANEL_HEIGHT);
				checkFlipping(panelModels[i][j]);
			}
		}

		counterMap.get("background").step();

	}

	public void checkWinCondition() {

		if(!flipping) {

			boolean won = counterMap.get("fail_panels").getCount() == counterMap.get("fail_panels").getBottomThreshold();

			if(won) {
				state = State.SUCCESS;
			}

			if(!won && tries == 0) {
				state = State.FAIL;
			}

			if(!won && lives == 0) {
				state = State.GAME_OVER;
			}

		}

	}

	public void checkTouch() {

		if(Gdx.input.justTouched()) {

			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);

			if(state == State.MAIN_MENU || state == State.PAUSE) {
				if (playTouchArea.contains(touchPos.x, touchPos.y)) {
				    playSound.play();
					if (!settingsModel.isActive()) {
						settingsModel.flip();
						counterMap.get("settings").reset();
					}
					counterMap.get("background").unfreeze();
					state = State.GAME;

					save.putInteger("level", level);
					save.putInteger("best", best);
					save.putInteger("round", round);
					save.putInteger("prevTries", prevTries);
					save.putInteger("tries", tries);
					save.putInteger("lives", lives);
					save.putString("panels", boardToString(panelModels));
					save.putString("prevPanels", boardToString(prevPanelModels));
					save.putString("state", state.name());
					save.flush();
				} else if(settingsTouchArea.contains(touchPos.x, touchPos.y)) {
					if(!settingsModel.isFlipping()) {
                        flipSound.play();
						settingsModel.setJustFlipped(true);
						settingsModel.setActive(!settingsModel.isActive());
					}
				} else if(!settingsModel.isActive()) {
					if(musicTouchArea.contains(touchPos.x, touchPos.y)) {
						if(!musicModel.isFlipping()) {
							musicModel.setJustFlipped(true);
							musicModel.setActive(!musicModel.isActive());

							save.putBoolean("music", musicModel.isActive());
						}
					} else if(soundTouchArea.contains(touchPos.x, touchPos.y)) {
						if(!soundModel.isFlipping()) {
							soundModel.setJustFlipped(true);
							soundModel.setActive(!soundModel.isActive());

							save.putBoolean("sound", soundModel.isActive());
						}
					}
				}
			} else if(state == State.GAME) {
				if(pauseTouchArea.contains(touchPos.x, touchPos.y)) {
				    pauseSound.play();
					counterMap.get("background").freeze();
					state = State.PAUSE;

					save.putInteger("level", level);
					save.putInteger("best", best);
					save.putInteger("round", round);
					save.putInteger("prevTries", prevTries);
					save.putInteger("tries", tries);
					save.putInteger("lives", lives);
					save.putString("state", state.name());
					save.flush();
				} else {
					for (int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
						for (int j = 0; j < BOARD_WIDTH_COUNT; j++) {
							if (panelsTouchArea[i][j].contains(touchPos.x, touchPos.y)) {
								if (!flipping) {
									flipSound.play();
									tries--;
									flipping = true;
									panelModels[i][j].setJustFlipped(true);
									panelModels[i][j].setActive(!panelModels[i][j].isActive());
									float count = panelModels[i][j].isActive() ? counterMap.get("fail_panels").stepDown() : counterMap.get("fail_panels").step();
									for (Panel panel : panelModels[i][j].getNeighbours()) {
										panel.setJustFlipped(true);
										panel.setActive(!panel.isActive());
										count = panel.isActive() ? counterMap.get("fail_panels").stepDown() : counterMap.get("fail_panels").step();
									}

									save.putInteger("tries", tries);
									save.putString("panels", boardToString(panelModels));
									save.putString("prevPanels", boardToString(prevPanelModels));
									save.flush();
								}
							}
						}
					}
				}
			} else if(state == State.GAME_OVER) {
				if(playTouchArea.contains(touchPos.x, touchPos.y)) {
					playSound.play();

					level = 1;
					round = 1;
					tries = 1;
					prevTries = 1;
					lives = 3;
					clearBoard();
					createBoard(tries);
					state = State.GAME;

					save.putInteger("level", 1);
					save.putInteger("round", 1);
					save.putInteger("tries", 1);
					save.putInteger("prevTries", 1);
					save.putInteger("lives", 3);
					save.putString("panels", boardToString(panelModels));
					save.putString("prevPanels", boardToString(prevPanelModels));
					save.putString("state", state.name());
					save.flush();
				} else if(settingsTouchArea.contains(touchPos.x, touchPos.y)) {
					if(!settingsModel.isFlipping()) {
						flipSound.play();
						settingsModel.setJustFlipped(true);
						settingsModel.setActive(!settingsModel.isActive());
					}
				} else if(!settingsModel.isActive()) {
					if(musicTouchArea.contains(touchPos.x, touchPos.y)) {
						if(!musicModel.isFlipping()) {
							flipSound.play();
							musicModel.setJustFlipped(true);
							musicModel.setActive(!musicModel.isActive());

							save.putBoolean("music", musicModel.isActive());
						}
					} else if(soundTouchArea.contains(touchPos.x, touchPos.y)) {
						if(!soundModel.isFlipping()) {
							flipSound.play();
							soundModel.setJustFlipped(true);
							soundModel.setActive(!soundModel.isActive());

							save.putBoolean("sound", soundModel.isActive());
						}
					}
				}
			}

		}

	}

	@Override
	public void render() {

		startRender();

		if (state == State.MAIN_MENU) {
			renderMainMenu();
		} else if (state == State.GAME) {
			renderGame();
			checkWinCondition();
		} else if(state == State.PAUSE) {
			renderMainMenu();
			renderPause();
		} else if(state == State.SUCCESS) {
			if(counterMap.get("success").getCount() == counterMap.get("success").getBottomThreshold()) {
				successSound.play();
			}
			renderGame();
			renderSuccess();
			if(counterMap.get("success").step() == counterMap.get("success").getTopThreshold()) {
				counterMap.get("success").reset();
				counterMap.get("success_panel_heart_pulse").reset();
				counterMap.get("success_heart_pulse").reset();
				counterMap.get("fail_panels").reset();
				level++;
				round++;
				if(level > best) {
					best = level;
				}
				if(round > prevTries * prevTries) {
					tries = prevTries + 1;
					round = 1;
				} else {
					tries = prevTries;
				}
				prevTries = tries;
				lives = 3;
				clearBoard();
				createBoard(tries);
				state = State.GAME;

				save.putInteger("level", level);
				save.putInteger("best", best);
				save.putInteger("round", round);
				save.putInteger("tries", tries);
				save.putInteger("prevTries", prevTries);
				save.putInteger("lives", lives);
				save.putString("panels", boardToString(panelModels));
				save.putString("prevPanels", boardToString(prevPanelModels));
				save.putString("state", state.name());
				save.flush();
			}
		} else if(state == State.FAIL) {
			if(counterMap.get("fail").getCount() == counterMap.get("fail").getBottomThreshold()) {
				failSound.play();
				skulls = (int) counterMap.get("fail_panels").getCount();
			}
			renderGame();
			renderFail();
			if(counterMap.get("fail").step() == counterMap.get("fail").getTopThreshold()) {
				counterMap.get("fail").reset();
				counterMap.get("fail_panel_pulse").reset();
				counterMap.get("fail_panel_pulse_count").reset();
				lives--;
				tries = prevTries;
				reset = false;
				state = State.GAME;

				save.putInteger("tries", tries);
				save.putInteger("lives", lives);
				save.putString("state", state.name());
				save.flush();
			}
		} else if(state == State.GAME_OVER) {
			if(counterMap.get("fail_panels").getCount() != counterMap.get("fail_panels").getBottomThreshold()) {
				gameOverSound.play();
				counterMap.get("fail_panels").reset();
			}
			renderGameOver();
		}

		checkTouch();

		endRender();

	}

	@Override
	public void dispose() {
		batch.dispose();
		backgroundSprite.dispose();
		playSprite.dispose();
		instagramSprite.dispose();
		soundSprite.dispose();
		bestSprite.dispose();
		pauseSprite.dispose();
		scoreSprite.dispose();
		symbolsSprite.dispose();
		lifeSprite.dispose();
		triesSprite.dispose();
		panelsSprite.dispose();
	}

}
