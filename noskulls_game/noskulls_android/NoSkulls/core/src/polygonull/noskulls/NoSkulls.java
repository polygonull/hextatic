package polygonull.noskulls;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import polygonull.noskulls.components.Counter;
import polygonull.noskulls.components.IntegerPair;
import polygonull.noskulls.components.Panel;
import polygonull.noskulls.components.SkullAndHeartPanel;
import polygonull.noskulls.components.AlphanumericHelper;
import polygonull.noskulls.components.State;
import polygonull.noskulls.components.Sound;

import static com.badlogic.gdx.math.MathUtils.random;

public class NoSkulls extends ApplicationAdapter {

	Preferences save;

	private static String alphanumerics = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!:| ";

	// Text
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
	public static float INFO_WIDTH = 72;
	public static float INFO_HEIGHT = 72;
	public static float HELP_WIDTH = 72;
	public static float HELP_HEIGHT = 72;
	public static float WHITE_PIXEL_WIDTH = 1;
	public static float WHITE_PIXEL_HEIGHT = 1;
	public static float BIG_SYMBOL_WIDTH = 40;
	public static float BIG_SYMBOL_HEIGHT = 40;
	public static float GAME_OVER_LEVEL_WIDTH = 180;
	public static float GAME_OVER_LEVEL_HEIGHT = 72;
	public static float PANEL_LIFE_WIDTH = 32;
	public static float PANEL_LIFE_HEIGHT = 30;
	public static float CLOSE_WIDTH = 72;
	public static float CLOSE_HEIGHT = 72;

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
	public static float UI_WIDTH_SPACE_4 = 28;
	public static float UI_HEIGHT_SPACE_4 = 28;
	public static float UI_WIDTH_SPACE_5 = 50;
	public static float UI_HEIGHT_SPACE_5 = 50;
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
	public static float BACKGROUND_X = 0;
	public static float BACKGROUND_Y = 0;
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
	public static float SOUND_X = GAME_4_X;
	public static float SOUND_Y = SETTINGS_Y - SETTINGS_HEIGHT - UI_HEIGHT_SPACE_1;
	public static float INFO_X = GAME_4_X;
	public static float INFO_Y = SOUND_Y - SOUND_HEIGHT - UI_HEIGHT_SPACE_1;
	public static float HELP_X = GAME_4_X;
	public static float HELP_Y = INFO_Y - INFO_HEIGHT - UI_HEIGHT_SPACE_1;
	public static float GAME_OVER_PLAY_X = MAIN_MENU_X;
	public static float GAME_OVER_PLAY_Y = PLAY_Y - BEST_HEIGHT;
	public static float GAME_OVER_BEST_X = MAIN_MENU_X;
	public static float GAME_OVER_BEST_Y = BEST_Y - BEST_HEIGHT;
	public static float GAME_OVER_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * GAME_OVER.length()) / 2;
	public static float GAME_OVER_LABEL_Y = SETTINGS_Y - UI_HEIGHT_SPACE_5;
	public static float GAME_OVER_LEVEL_X = MAIN_MENU_X;
	public static float GAME_OVER_LEVEL_Y = GAME_OVER_LABEL_Y - BEST_HEIGHT - UI_HEIGHT_SPACE_5;
	public static float NICE_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * NICE.length()) / 2;
	public static float NICE_LABEL_Y = 	(CAMERA_HEIGHT - BIG_SYMBOL_HEIGHT) / 2;
	public static float ALMOST_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * ALMOST.length()) / 2;
	public static float ALMOST_LABEL_Y = (CAMERA_HEIGHT - BIG_SYMBOL_HEIGHT) / 2;
	public static float CLOSE_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * CLOSE.length()) / 2;
	public static float CLOSE_LABEL_Y = (CAMERA_HEIGHT - BIG_SYMBOL_HEIGHT) / 2 + BIG_SYMBOL_HEIGHT / 2 + UI_HEIGHT_SPACE_2;
	public static float ENOUGH_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * ENOUGH.length()) / 2;
	public static float ENOUGH_LABEL_Y = (CAMERA_HEIGHT - BIG_SYMBOL_HEIGHT) / 2 - UI_HEIGHT_SPACE_2;
	public static float PLAY_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * PLAY.length()) / 2;
	public static float PLAY_LABEL_Y = MAIN_MENU_Y + MAIN_MENU_HEIGHT + UI_WIDTH_SPACE_5;
	public static float RESUME_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * RESUME.length()) / 2;
	public static float RESUME_LABEL_Y = PLAY_LABEL_Y;
	public static float LEVEL_REACHED_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * 0.4f * LEVEL.length()) / 2;
	public static float LEVEL_REACHED_LABEL_Y = GAME_OVER_LEVEL_Y + BEST_HEIGHT + UI_HEIGHT_SPACE_5;
	public static float CLOSE_X = SETTINGS_X;
	public static float CLOSE_Y = SETTINGS_Y;

	// Game state
	private State state = State.MAIN_MENU;
	private int level = 0;
	private int best = 0;
	private int round = 1;
	private int prevTries = 1;
	private int tries = 1;
	private int lives = 3;

	private int skulls = 0;
	private boolean flipping = false;
	private boolean reset = false;
	private boolean resetFlip = false;
	private int tutorialI = 3;
	private int tutorialJ = 3;

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
	private Texture soundSprite;
	private Texture infoSprite;
	private Texture whitePixel;
	private Texture panelLifeSprite;
	private Texture bluePixelSprite;
	private Texture helpSprite;
	private Texture closeSprite;

	// Touch areas
	private Rectangle settingsTouchArea;
	private Rectangle soundTouchArea;
	private Rectangle infoTouchArea;
	private Rectangle helpTouchArea;
	private Rectangle playTouchArea;
	private Rectangle pauseTouchArea;
	private Rectangle[][] panelsTouchArea;

	private OrthographicCamera camera;
	private Viewport viewport;

	private SkullAndHeartPanel[][] panelModels;
	private SkullAndHeartPanel[][] prevPanelModels;
	private IntegerPair[][] heartModels;
	private Panel settingsModel;
	private Panel soundModel;

	private HashMap<String, Counter> counterMap;

	BitmapFont font50;
	BitmapFont font20;

	// Sounds
	Sound flipSound;
	Sound resetSound;
    Sound playSound;
	Sound pauseSound;
	Sound gameOverSound;
	Sound successSound;
	Sound failSound;

	public void recalculateCoordinates() {

		// Game 'magic' numbers
		CAMERA_WIDTH = camera.viewportWidth;
		CAMERA_HEIGHT = camera.viewportHeight;

		BOARD_WIDTH_COUNT = 6;
		BOARD_HEIGHT_COUNT = 6;
		BOARD_WIDTH_SPACE = 4;
		BOARD_HEIGHT_SPACE = 4;
		UI_WIDTH_SPACE_1 = BOARD_WIDTH_SPACE;
		UI_HEIGHT_SPACE_1 = BOARD_HEIGHT_SPACE;
		UI_WIDTH_SPACE_2 = 13;
		UI_HEIGHT_SPACE_2 = 13;
		UI_WIDTH_SPACE_3 = 20;
		UI_HEIGHT_SPACE_3 = 20;
		UI_WIDTH_SPACE_5 = 50;
		UI_HEIGHT_SPACE_5 = 50;
		MAIN_MENU_WIDTH = PLAY_WIDTH;
		MAIN_MENU_HEIGHT = PLAY_HEIGHT + UI_HEIGHT_SPACE_1 + BEST_HEIGHT;
		GAME_1_WIDTH = BOARD_WIDTH_COUNT * PANEL_WIDTH + (BOARD_WIDTH_COUNT - 1) * BOARD_WIDTH_SPACE;
		GAME_1_HEIGHT = BOARD_HEIGHT_COUNT * PANEL_HEIGHT + (BOARD_HEIGHT_COUNT - 1) * BOARD_HEIGHT_SPACE;
		GAME_2_WIDTH = GAME_1_WIDTH;
		GAME_2_HEIGHT = GAME_1_HEIGHT + UI_HEIGHT_SPACE_2 + TRIES_HEIGHT;
		GAME_4_WIDTH = GAME_1_WIDTH;
		GAME_4_HEIGHT = CAMERA_HEIGHT - UI_HEIGHT_SPACE_4;
		GAME_3_WIDTH = GAME_1_WIDTH;
		GAME_3_HEIGHT = GAME_4_HEIGHT - PAUSE_HEIGHT;

		// Texture coordinates
		BACKGROUND_X = 0;
		BACKGROUND_Y = 0;
		MAIN_MENU_X = (CAMERA_WIDTH - MAIN_MENU_WIDTH) / 2;
		MAIN_MENU_Y = (CAMERA_HEIGHT - MAIN_MENU_HEIGHT) / 2 - (PLAY_HEIGHT - UI_HEIGHT_SPACE_1 - BEST_HEIGHT) / 2;
		PLAY_X = MAIN_MENU_X;
		PLAY_Y = MAIN_MENU_Y + (MAIN_MENU_HEIGHT - PLAY_HEIGHT);
		BEST_X = MAIN_MENU_X;
		BEST_Y = MAIN_MENU_Y;
		GAME_4_X = UI_WIDTH_SPACE_4 / 2;
		GAME_4_Y = (CAMERA_HEIGHT - GAME_4_HEIGHT) / 2;
		GAME_3_X = (CAMERA_WIDTH - GAME_3_WIDTH) / 2;
		GAME_3_Y = GAME_4_Y;
		GAME_2_X = (CAMERA_WIDTH - GAME_2_WIDTH) / 2;
		GAME_2_Y = GAME_3_Y + (GAME_3_HEIGHT - GAME_2_HEIGHT) / 2;
		GAME_1_X = (CAMERA_WIDTH - GAME_2_WIDTH) / 2;
		GAME_1_Y = GAME_2_Y;
		PAUSE_X = GAME_4_X;
		PAUSE_Y = CAMERA_HEIGHT - GAME_4_Y - PAUSE_HEIGHT;
		if(CAMERA_WIDTH > 480 + PAUSE_WIDTH + UI_WIDTH_SPACE_1) {
			SCORE_X = (CAMERA_WIDTH - SCORE_WIDTH) / 2;
		} else {
			SCORE_X = GAME_4_X + PAUSE_WIDTH + UI_WIDTH_SPACE_1;
		}
		SCORE_Y = CAMERA_HEIGHT - GAME_4_Y  - SCORE_HEIGHT;
		GAME_CUP_X = SCORE_X + (SCORE_WIDTH - CUP_WIDTH) / 2;
		GAME_CUP_Y = SCORE_Y + (SCORE_HEIGHT - CUP_HEIGHT) / 2;
		GAME_BEST_LABEL_X = GAME_CUP_X - ((SCORE_WIDTH - CUP_WIDTH) / 2 + BIG_SYMBOL_WIDTH * 0.4f * BEST.length()) / 2;
		GAME_BEST_LABEL_Y = SCORE_Y - UI_HEIGHT_SPACE_2 - BIG_SYMBOL_HEIGHT * 0.4f;
		GAME_LEVEL_LABEL_X = GAME_CUP_X + CUP_WIDTH  + ((SCORE_WIDTH - CUP_WIDTH) / 2 - BIG_SYMBOL_WIDTH * 0.4f * LEVEL.length()) / 2;
		GAME_LEVEL_LABEL_Y = SCORE_Y - UI_HEIGHT_SPACE_2 - BIG_SYMBOL_HEIGHT * 0.4f;
		LIFE_1_X = GAME_2_X + UI_WIDTH_SPACE_2;
		LIFE_1_Y = GAME_2_Y + GAME_1_HEIGHT + UI_HEIGHT_SPACE_2 + (TRIES_HEIGHT - LIFE_HEIGHT) / 2;
		LIFE_2_X = GAME_2_X + UI_WIDTH_SPACE_2 * 2 + LIFE_WIDTH;
		LIFE_2_Y = LIFE_1_Y;
		LIFE_3_X = GAME_2_X + UI_WIDTH_SPACE_2 * 3 + LIFE_WIDTH * 2;
		LIFE_3_Y = LIFE_1_Y;
		TRIES_X = CAMERA_WIDTH - GAME_1_X - TRIES_WIDTH - UI_WIDTH_SPACE_2;
		TRIES_Y = GAME_2_Y + GAME_1_HEIGHT + UI_HEIGHT_SPACE_2;
		PANEL_1_X = GAME_1_X;
		PANEL_1_Y = GAME_1_Y + GAME_1_HEIGHT - PANEL_HEIGHT;
		SETTINGS_X = GAME_4_X;
		SETTINGS_Y = CAMERA_HEIGHT - GAME_4_Y - PAUSE_HEIGHT;
		SOUND_X = GAME_4_X;
		SOUND_Y = SETTINGS_Y - SETTINGS_HEIGHT - UI_HEIGHT_SPACE_1;
		INFO_X = GAME_4_X;
		INFO_Y = SOUND_Y - SOUND_HEIGHT - UI_HEIGHT_SPACE_1;
		HELP_X = GAME_4_X;
		HELP_Y = INFO_Y - INFO_HEIGHT - UI_HEIGHT_SPACE_1;
		GAME_OVER_PLAY_X = MAIN_MENU_X;
		GAME_OVER_PLAY_Y = PLAY_Y - BEST_HEIGHT;
		GAME_OVER_BEST_X = MAIN_MENU_X;
		GAME_OVER_BEST_Y = BEST_Y - BEST_HEIGHT;
		GAME_OVER_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * GAME_OVER.length()) / 2;
		GAME_OVER_LABEL_Y = SETTINGS_Y - UI_HEIGHT_SPACE_5;
		GAME_OVER_LEVEL_X = MAIN_MENU_X;
		GAME_OVER_LEVEL_Y = GAME_OVER_LABEL_Y - BEST_HEIGHT - UI_HEIGHT_SPACE_5;
		NICE_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * NICE.length()) / 2;
		NICE_LABEL_Y = 	(CAMERA_HEIGHT - BIG_SYMBOL_HEIGHT) / 2;
		ALMOST_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * ALMOST.length()) / 2;
		ALMOST_LABEL_Y = (CAMERA_HEIGHT - BIG_SYMBOL_HEIGHT) / 2;
		CLOSE_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * CLOSE.length()) / 2;
		CLOSE_LABEL_Y = (CAMERA_HEIGHT - BIG_SYMBOL_HEIGHT) / 2 + BIG_SYMBOL_HEIGHT / 2 + UI_HEIGHT_SPACE_2;
		ENOUGH_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * ENOUGH.length()) / 2;
		ENOUGH_LABEL_Y = (CAMERA_HEIGHT - BIG_SYMBOL_HEIGHT) / 2 - UI_HEIGHT_SPACE_2;
		PLAY_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * PLAY.length()) / 2;
		PLAY_LABEL_Y = MAIN_MENU_Y + MAIN_MENU_HEIGHT + UI_WIDTH_SPACE_5;
		RESUME_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * RESUME.length()) / 2;
		RESUME_LABEL_Y = PLAY_LABEL_Y;
		LEVEL_REACHED_LABEL_X = (CAMERA_WIDTH - BIG_SYMBOL_WIDTH * 0.4f * LEVEL.length()) / 2;
		LEVEL_REACHED_LABEL_Y = GAME_OVER_LEVEL_Y + BEST_HEIGHT + UI_HEIGHT_SPACE_5;
		CLOSE_X = SETTINGS_X;
		CLOSE_Y = SETTINGS_Y;

		// Initialize touch areas
		playTouchArea = new Rectangle(PLAY_X, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
		pauseTouchArea = new Rectangle(PAUSE_X, PAUSE_Y, PAUSE_WIDTH, PAUSE_HEIGHT);
		settingsTouchArea = new Rectangle(SETTINGS_X, SETTINGS_Y, SETTINGS_WIDTH, SETTINGS_HEIGHT);
		soundTouchArea = new Rectangle(SOUND_X, SOUND_Y, SOUND_WIDTH, SOUND_HEIGHT);
		infoTouchArea = new Rectangle(INFO_X, INFO_Y, INFO_WIDTH, INFO_HEIGHT);
		helpTouchArea = new Rectangle(HELP_X, HELP_Y, HELP_WIDTH, HELP_HEIGHT);

		// Initialize panel touch areas, panel models and heart models
		float start_x = PANEL_1_X;
		float start_y = PANEL_1_Y;
		for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
			for(int j = 0; j < BOARD_WIDTH_COUNT; j++) {
				panelsTouchArea[i][j] = new Rectangle(start_x, start_y, PANEL_WIDTH, PANEL_HEIGHT);
				heartModels[i][j] = new IntegerPair((int)start_x + 20, (int)start_y + 22);
				start_x += PANEL_WIDTH + BOARD_WIDTH_SPACE;
			}
			start_x = PANEL_1_X;
			start_y -= PANEL_HEIGHT + BOARD_HEIGHT_SPACE;
		}

	}

	private void drawText(Texture fontTexture, float x, float y, float charWidth, float charHeight, float scale, String text) {

		ArrayList<Integer> textOffsets = alphanumericHelper.stringToOffsets(text);
		for(int i = 0; i < textOffsets.size(); i++) {
			batch.draw(fontTexture,  x + i * charWidth * scale, y, 0, 0, charWidth, charHeight, scale, scale, 0, (int)(textOffsets.get(i) * charWidth), 0, (int)charWidth, (int)charHeight, false, false);
		}

	}

	private void loadGame() {

		save = Gdx.app.getPreferences("no_skulls_save");

		level = save.getInteger("level", 0);
		best = save.getInteger("best", 0);
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

		if(!save.getBoolean("sound", true)) {
			soundModel.flip();
		}

		switch (state) {
			case MAIN_MENU:
				break;
			case PAUSE:
				counterMap.get("background").freeze();
				break;
			case GAME:

				boolean won = counterMap.get("fail_panels").getRealCount() == counterMap.get("fail_panels").getFloor();

				if(won) {
					level++;
					round++;
					if(level > best) {
						best = level;
					}
					if(round > (prevTries * prevTries) + (save.getBoolean("tutorial", true) ? 1 : 0)) {
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
				counterMap.get("background").freeze();

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
					counterMap.get("background").unfreeze();
				}

				break;
		}

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

	}

	private String boardToString(SkullAndHeartPanel[][] panelsAsModels) {

		StringBuilder boardToString = new StringBuilder();

		for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
			for(int j = 0; j < BOARD_WIDTH_COUNT; j++) {
				boardToString.append(panelsAsModels[i][j].isActive() ? 1 : 0);
			}
		}

		return boardToString.toString();

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

		int initTries = tries;
		while(counterMap.get("fail_panels").getCount() == 0) {

			ArrayList<IntegerPair> moves = new ArrayList<IntegerPair>(Arrays.asList(
					new IntegerPair(0, 0), new IntegerPair(0, 1), new IntegerPair(0, 2), new IntegerPair(0, 3), new IntegerPair(0, 4), new IntegerPair(0, 5),
					new IntegerPair(1, 0), new IntegerPair(1, 1), new IntegerPair(1, 2), new IntegerPair(1, 3), new IntegerPair(1, 4), new IntegerPair(1, 5),
					new IntegerPair(2, 0), new IntegerPair(2, 1), new IntegerPair(2, 2), new IntegerPair(2, 3), new IntegerPair(2, 4), new IntegerPair(2, 5),
					new IntegerPair(3, 0), new IntegerPair(3, 1), new IntegerPair(3, 2), new IntegerPair(3, 3), new IntegerPair(3, 4), new IntegerPair(3, 5),
					new IntegerPair(4, 0), new IntegerPair(4, 1), new IntegerPair(4, 2), new IntegerPair(4, 3), new IntegerPair(4, 4), new IntegerPair(4, 5),
					new IntegerPair(5, 0), new IntegerPair(5, 1), new IntegerPair(5, 2), new IntegerPair(5, 3), new IntegerPair(5, 4), new IntegerPair(5, 5)
			));

			while (tries != 0) {
				int moveIndex = random.nextInt(moves.size() - 1);
				IntegerPair move = moves.get(moveIndex);
				int i = move.getI();
				int j = move.getJ();
				if(save.getBoolean("tutorial", true)) {
					i = tutorialI;
					j = tutorialJ;
				}
				panelModels[i][j].flip();
				double count = panelModels[i][j].isActive() ? counterMap.get("fail_panels").stepDown() : counterMap.get("fail_panels").step();
				for (SkullAndHeartPanel panel : panelModels[i][j].getNeighbours()) {
					panel.flip();
					count = panel.isActive() ? counterMap.get("fail_panels").stepDown() : counterMap.get("fail_panels").step();
				}
				prevPanelModels[i][j].flip();
				for (SkullAndHeartPanel panel : prevPanelModels[i][j].getNeighbours()) {
					panel.flip();
				}
				tries--;
				moves.remove(moveIndex);
				if (moves.size() == 0) {
					moves = new ArrayList<>(Arrays.asList(
							new IntegerPair(0, 0), new IntegerPair(0, 1), new IntegerPair(0, 2), new IntegerPair(0, 3), new IntegerPair(0, 4), new IntegerPair(0, 5),
							new IntegerPair(1, 0), new IntegerPair(1, 1), new IntegerPair(1, 2), new IntegerPair(1, 3), new IntegerPair(1, 4), new IntegerPair(1, 5),
							new IntegerPair(2, 0), new IntegerPair(2, 1), new IntegerPair(2, 2), new IntegerPair(2, 3), new IntegerPair(2, 4), new IntegerPair(2, 5),
							new IntegerPair(3, 0), new IntegerPair(3, 1), new IntegerPair(3, 2), new IntegerPair(3, 3), new IntegerPair(3, 4), new IntegerPair(3, 5),
							new IntegerPair(4, 0), new IntegerPair(4, 1), new IntegerPair(4, 2), new IntegerPair(4, 3), new IntegerPair(4, 4), new IntegerPair(4, 5),
							new IntegerPair(5, 0), new IntegerPair(5, 1), new IntegerPair(5, 2), new IntegerPair(5, 3), new IntegerPair(5, 4), new IntegerPair(5, 5)
					));
				}
			}

			tries = initTries;

		}

	}

	public void resetBoard() {

		for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
			for (int j = 0; j < BOARD_WIDTH_COUNT; j++) {
				if (panelModels[i][j].isActive() != prevPanelModels[i][j].isActive()) {
					panelModels[i][j].setJustFlipped(true);
					panelModels[i][j].setActive(!panelModels[i][j].isActive());
					flipping = true;
					double count = panelModels[i][j].isActive() ? counterMap.get("fail_panels").stepDown() : counterMap.get("fail_panels").step();
					resetFlip = true;
				}
			}
		}

	}

	public void resetBoardNoFlip() {

		for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
			for (int j = 0; j < BOARD_WIDTH_COUNT; j++) {
				if (panelModels[i][j].isActive() != prevPanelModels[i][j].isActive()) {
					panelModels[i][j].flip();
					double count = panelModels[i][j].isActive() ? counterMap.get("fail_panels").stepDown() : counterMap.get("fail_panels").step();
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

	public void renderTutorial() {

		batch.draw(bluePixelSprite, 0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		batch.draw(whitePixel, 0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		batch.draw(closeSprite, CLOSE_X, CLOSE_Y);

		GlyphLayout layout = new GlyphLayout(font50, "How to play");
		font50.draw(batch, layout, (CAMERA_WIDTH - layout.width) / 2, CAMERA_HEIGHT / 2 + (layout.height + UI_HEIGHT_SPACE_3) * 4);

		font50.getData().setScale(0.6f);
		layout = new GlyphLayout(font50, "Touch a panel to flip it");
		font50.draw(batch, layout, (CAMERA_WIDTH - layout.width) / 2, CAMERA_HEIGHT / 2 + (layout.height + UI_HEIGHT_SPACE_3) * 2);

		font50.getData().setScale(0.5f);
		layout = new GlyphLayout(font50, "(and it's neighboring panels)");
		font50.draw(batch, layout, (CAMERA_WIDTH - layout.width) / 2, CAMERA_HEIGHT / 2 + (layout.height + UI_HEIGHT_SPACE_3));
		font50.getData().setScale(0.6f);

		layout = new GlyphLayout(font50, "so that the board");
		font50.draw(batch, layout, (CAMERA_WIDTH - layout.width) / 2, CAMERA_HEIGHT / 2);

		layout = new GlyphLayout(font50, "contains no skulls.");
		font50.draw(batch, layout, (CAMERA_WIDTH - layout.width) / 2, CAMERA_HEIGHT / 2 - (layout.height + UI_HEIGHT_SPACE_3));

		layout = new GlyphLayout(font50, "Have fun!");
		font50.draw(batch, layout, (CAMERA_WIDTH - layout.width) / 2, CAMERA_HEIGHT / 2 - (layout.height + UI_HEIGHT_SPACE_3) * 2);
		font50.getData().setScale(1);

	}

	public void renderTutorialGame() {

		batch.draw(whitePixel, 0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		batch.draw(pauseSprite, PAUSE_X, PAUSE_Y);

		batch.draw(panelsSprite, panelsTouchArea[tutorialI][tutorialJ].x, panelsTouchArea[tutorialI][tutorialJ].y, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, 1f, 1f, 0, (int) PANEL_WIDTH * panelModels[tutorialI][tutorialJ].getState(), 0, (int)PANEL_WIDTH, (int)PANEL_HEIGHT, false,false);

		GlyphLayout layout = new GlyphLayout(font50, "Touch the skull");
		font50.draw(batch, layout, (CAMERA_WIDTH - layout.width) / 2, GAME_1_Y - UI_HEIGHT_SPACE_5);

	}

	public void renderSettings() {

		checkFlipping(settingsModel);

		batch.draw(settingsSprite, SETTINGS_X, SETTINGS_Y, (int)(settingsModel.getState() * SETTINGS_WIDTH), 0, (int)SETTINGS_WIDTH, (int)SETTINGS_HEIGHT);

		if(!settingsModel.isActive()) {
			if(counterMap.get("settings").getCount() < counterMap.get("settings").getCeiling()) {
				counterMap.get("settings").step();
			}
		} else {
			if(counterMap.get("settings").getCount() > counterMap.get("settings").getFloor()) {
				counterMap.get("settings").step();
			}
		}

		if(counterMap.get("settings").getCount() > counterMap.get("settings").getCeiling() -
				(counterMap.get("settings").getCeiling() / 3f) * 3 + counterMap.get("settings").getStep()) {
			checkFlipping(soundModel);
			batch.draw(soundSprite, SOUND_X, SOUND_Y, (int) (soundModel.getState() * SOUND_WIDTH), 0, (int) SOUND_WIDTH, (int) SOUND_HEIGHT);
		}
		if(counterMap.get("settings").getCount() > counterMap.get("settings").getCeiling() -
				(counterMap.get("settings").getCeiling() / 3f) * 2 + counterMap.get("settings").getStep()) {
			batch.draw(infoSprite, INFO_X, INFO_Y);
		}
		if(counterMap.get("settings").getCount() > counterMap.get("settings").getCeiling() -
				(counterMap.get("settings").getCeiling() / 3f) * 1 + counterMap.get("settings").getStep()) {
			batch.draw(helpSprite, HELP_X, HELP_Y);
		}

	}

	public void renderMainMenu() {

		batch.draw(backgroundSprite, BACKGROUND_X, BACKGROUND_Y, 0, 0, CAMERA_WIDTH * 2, CAMERA_HEIGHT * 2, 0.75f, 0.75f, 0, (int) -counterMap.get("background").getCount(), (int) -counterMap.get("background").getCount(), (int) CAMERA_WIDTH * 2, (int) CAMERA_HEIGHT * 2, false, false);

		batch.draw(playSprite, PLAY_X, PLAY_Y, 0, 0, PLAY_WIDTH, PLAY_HEIGHT, 1f, 1f, 0, 0, 0, (int)PLAY_WIDTH, (int)PLAY_HEIGHT, false, false);
		batch.draw(bestSprite, BEST_X, BEST_Y);
		batch.draw(cupSprite, (BEST_X + (BEST_WIDTH + CUP_WIDTH + UI_WIDTH_SPACE_3 - SYMBOL_WIDTH * Integer.toString(best).length()) / 2) - CUP_WIDTH - UI_WIDTH_SPACE_3, BEST_Y + (BEST_HEIGHT - CUP_HEIGHT) / 2);
		drawText(symbolsSprite, BEST_X + (BEST_WIDTH + CUP_WIDTH + UI_WIDTH_SPACE_3 - SYMBOL_WIDTH * Integer.toString(best).length()) / 2, BEST_Y + (BEST_HEIGHT - SYMBOL_HEIGHT) / 2, SYMBOL_WIDTH, SYMBOL_HEIGHT, 1, Integer.toString(best));

		counterMap.get("text_pulse").step();
		font50.getData().setScale(counterMap.get("text_pulse").getCount());
		if(state == State.MAIN_MENU) {
			GlyphLayout layout = new GlyphLayout(font50, PLAY);
			font50.draw(batch, layout, (CAMERA_WIDTH - layout.width) / 2, MAIN_MENU_Y + MAIN_MENU_HEIGHT + layout.height + UI_HEIGHT_SPACE_4 + UI_HEIGHT_SPACE_2);
		} else {
			GlyphLayout layout = new GlyphLayout(font50, RESUME);
			font50.draw(batch, layout, (CAMERA_WIDTH - layout.width) / 2, MAIN_MENU_Y + MAIN_MENU_HEIGHT + layout.height + UI_HEIGHT_SPACE_4 + UI_HEIGHT_SPACE_2);
		}
		font50.getData().setScale(1);

		renderSettings();

		counterMap.get("background").step();

	}

	public void renderFail() {

		if(counterMap.get("fail_panel_pulse").getRepeats() < counterMap.get("fail_panel_pulse").getMaxRepeats()) {
			counterMap.get("fail_panel_pulse").step();
			float pulse = counterMap.get("fail_panel_pulse").getCount();
			for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
				for (int j = 0; j < BOARD_WIDTH_COUNT; j++) {
					if(!panelModels[i][j].isActive()) {
						batch.draw(panelsSprite, panelsTouchArea[i][j].x + (PANEL_WIDTH - PANEL_WIDTH * pulse) / 2, panelsTouchArea[i][j].y + (PANEL_HEIGHT - PANEL_HEIGHT * pulse) / 2, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, pulse, pulse, 0, (int) PANEL_WIDTH * panelModels[i][j].getState(), 0, (int)PANEL_WIDTH, (int)PANEL_HEIGHT, false,false);
					}
				}
			}
		} else {
			if(lives != 1) {
				if (!reset) {
					resetBoard();
					if (resetFlip) {
						resetSound.play(soundModel.isActive());
					}
					resetFlip = false;
				}
				for (int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
					for (int j = 0; j < BOARD_WIDTH_COUNT; j++) {
						batch.draw(panelsSprite, panelsTouchArea[i][j].x, panelsTouchArea[i][j].y, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, 1, 1, 0, (int) PANEL_WIDTH * panelModels[i][j].getState(), 0, (int) PANEL_WIDTH, (int) PANEL_HEIGHT, false, false);
						checkFlipping(panelModels[i][j]);
					}
				}
			} else {
				for (int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
					for (int j = 0; j < BOARD_WIDTH_COUNT; j++) {
						batch.draw(panelsSprite, panelsTouchArea[i][j].x, panelsTouchArea[i][j].y, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, 1, 1, 0, (int) PANEL_WIDTH * panelModels[i][j].getState(), 0, (int) PANEL_WIDTH, (int) PANEL_HEIGHT, false, false);
					}
				}
			}
			if(counterMap.get("fail_delay").step() == counterMap.get("fail_delay").getCeiling()) {
				reset = true;
			}
		}

		batch.draw(whitePixel, 0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		if(skulls <= counterMap.get("fail_panels").getCeiling() / 3) {
			GlyphLayout layout = new GlyphLayout(font50, ALMOST);
			font50.draw(batch, layout, (CAMERA_WIDTH - layout.width) / 2, (CAMERA_HEIGHT - layout.height) / 2);
		} else {
			GlyphLayout layoutClose = new GlyphLayout(font50, CLOSE);
			font50.draw(batch, layoutClose, (CAMERA_WIDTH - layoutClose.width) / 2, (CAMERA_HEIGHT - layoutClose.height) / 2 + UI_HEIGHT_SPACE_3 + UI_HEIGHT_SPACE_1);
			GlyphLayout layoutEnough = new GlyphLayout(font50, ENOUGH);
			font50.draw(batch, layoutEnough, (CAMERA_WIDTH - layoutEnough.width) / 2, (CAMERA_HEIGHT - layoutEnough.height) / 2 - UI_HEIGHT_SPACE_3 - UI_HEIGHT_SPACE_1);
		}

	}

	public void renderSuccess() {

		if(counterMap.get("success_panel_heart_pulse").getRepeats() < counterMap.get("success_panel_heart_pulse").getMaxRepeats()) {

			counterMap.get("success_panel_heart_pulse").step();
			float pulse = counterMap.get("success_panel_heart_pulse").getCount();

			for (int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
				for (int j = 0; j < BOARD_WIDTH_COUNT; j++) {
					batch.draw(panelLifeSprite,
							heartModels[i][j].getI() + (PANEL_LIFE_WIDTH - PANEL_LIFE_WIDTH * pulse) / 2,
							heartModels[i][j].getJ() + (PANEL_LIFE_HEIGHT - PANEL_LIFE_HEIGHT * pulse) / 2,
							0, 0, PANEL_LIFE_WIDTH, PANEL_LIFE_HEIGHT, pulse, pulse, 0,
							0, 0, (int) PANEL_LIFE_WIDTH, (int) PANEL_LIFE_HEIGHT, false, false);
				}
			}

		} else {

			counterMap.get("success_heart_pulse").setCount((int) Math.pow(counterMap.get("success_heart_pulse").step(), 1.017f));
			float scale = counterMap.get("success_heart_pulse").getCount();
			batch.draw(lifeSprite,  (CAMERA_WIDTH - LIFE_WIDTH * scale) / 2, (CAMERA_HEIGHT - LIFE_HEIGHT * scale) / 2, 0, 0, LIFE_WIDTH, LIFE_HEIGHT, scale, scale, 0, 0, 0, (int) LIFE_WIDTH, (int) LIFE_HEIGHT, false, false);

		}

		batch.draw(whitePixel, 0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		GlyphLayout layout = new GlyphLayout(font50, NICE);
		font50.draw(batch, layout, (CAMERA_WIDTH - layout.width) / 2, (CAMERA_HEIGHT - layout.height) / 2);

	}

	public void renderGameOver() {

		batch.draw(backgroundSprite, BACKGROUND_X, BACKGROUND_Y, 0, 0, CAMERA_WIDTH * 2, CAMERA_HEIGHT * 2, 0.75f, 0.75f, 0, (int) -counterMap.get("background").getCount(), (int) -counterMap.get("background").getCount(), (int) CAMERA_WIDTH * 2, (int) CAMERA_HEIGHT * 2, false, false);

		GlyphLayout layoutGameOver = new GlyphLayout(font50, GAME_OVER);
		font50.draw(batch, layoutGameOver, (CAMERA_WIDTH - layoutGameOver.width) / 2, SETTINGS_Y - UI_HEIGHT_SPACE_5);
		font50.getData().setScale(0.7f);
		GlyphLayout layoutLevel = new GlyphLayout(font50, LEVEL);
		font50.draw(batch, layoutLevel, (CAMERA_WIDTH - layoutLevel.width) / 2, SETTINGS_Y - UI_HEIGHT_SPACE_5 - layoutGameOver.height - UI_HEIGHT_SPACE_3);
		font50.getData().setScale(1);

		batch.draw(bestSprite, GAME_OVER_LEVEL_X, SETTINGS_Y - UI_HEIGHT_SPACE_5 - layoutGameOver.height - UI_HEIGHT_SPACE_3 - layoutLevel.height - UI_HEIGHT_SPACE_2 - BEST_HEIGHT);
		drawText(symbolsSprite, GAME_OVER_LEVEL_X + (BEST_WIDTH - SYMBOL_WIDTH * Integer.toString(level).length()) / 2, SETTINGS_Y - UI_HEIGHT_SPACE_5 - layoutGameOver.height - UI_HEIGHT_SPACE_3 - layoutLevel.height - UI_HEIGHT_SPACE_2 - BEST_HEIGHT + (BEST_HEIGHT - SYMBOL_HEIGHT) / 2, SYMBOL_WIDTH, SYMBOL_HEIGHT, 1, Integer.toString(level));
		batch.draw(bestSprite, BEST_X, BEST_Y);
		batch.draw(cupSprite, BEST_X + (BEST_WIDTH + CUP_WIDTH + UI_WIDTH_SPACE_3 - SYMBOL_WIDTH * Integer.toString(best).length()) / 2 - CUP_WIDTH - UI_WIDTH_SPACE_3, BEST_Y + (BEST_HEIGHT - CUP_HEIGHT) / 2);
		drawText(symbolsSprite, BEST_X + (BEST_WIDTH + CUP_WIDTH + UI_WIDTH_SPACE_3 - SYMBOL_WIDTH * Integer.toString(best).length()) / 2, BEST_Y + (BEST_HEIGHT - SYMBOL_HEIGHT) / 2, SYMBOL_WIDTH, SYMBOL_HEIGHT, 1, Integer.toString(best));
		batch.draw(playSprite, PLAY_X, PLAY_Y, 0, 0, PLAY_WIDTH, PLAY_HEIGHT, 1f, 1f, 0, 0, 0, (int)PLAY_WIDTH, (int)PLAY_HEIGHT, false, false);

		renderSettings();

	}

	public void renderGame() {

		batch.draw(backgroundSprite, BACKGROUND_X, BACKGROUND_Y, 0, 0, CAMERA_WIDTH * 2, CAMERA_HEIGHT * 2, 0.75f, 0.75f, 0, (int) -counterMap.get("background").getCount(), (int) -counterMap.get("background").getCount(), (int) CAMERA_WIDTH * 2, (int) CAMERA_HEIGHT * 2, false, false);

		batch.draw(pauseSprite, PAUSE_X, PAUSE_Y);
		batch.draw(scoreSprite, SCORE_X, SCORE_Y);
		batch.draw(cupSprite, GAME_CUP_X, GAME_CUP_Y);
		drawText(symbolsSprite, GAME_CUP_X - ((SCORE_WIDTH - CUP_WIDTH) / 2 + SYMBOL_WIDTH * Integer.toString(best).length()) / 2, SCORE_Y + (SCORE_HEIGHT - SYMBOL_HEIGHT) / 2, SYMBOL_WIDTH, SYMBOL_HEIGHT, 1, Integer.toString(best));
		drawText(symbolsSprite, GAME_CUP_X + CUP_WIDTH  + ((SCORE_WIDTH - CUP_WIDTH) / 2 - SYMBOL_WIDTH * Integer.toString(level).length()) / 2, SCORE_Y + (SCORE_HEIGHT - SYMBOL_HEIGHT) / 2, SYMBOL_WIDTH, SYMBOL_HEIGHT, 1, Integer.toString(level));
		GlyphLayout layoutBest = new GlyphLayout(font20, BEST);
		font20.draw(batch, layoutBest, GAME_CUP_X - ((SCORE_WIDTH - CUP_WIDTH) / 2 + layoutBest.width) / 2, SCORE_Y - UI_HEIGHT_SPACE_2);
		GlyphLayout layoutLevel = new GlyphLayout(font20, LEVEL);
		font20.draw(batch, layoutLevel, GAME_CUP_X + CUP_WIDTH  + ((SCORE_WIDTH - CUP_WIDTH) / 2 - layoutLevel.width) / 2, SCORE_Y - UI_HEIGHT_SPACE_2);

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

			boolean won = counterMap.get("fail_panels").getRealCount() == counterMap.get("fail_panels").getFloor();

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
				    playSound.play(soundModel.isActive());
					if (!settingsModel.isActive()) {
						settingsModel.flip();
						counterMap.get("settings").reset();
					}
					counterMap.get("background").unfreeze();
					state = State.GAME;

					save.putString("state", state.name());
					save.flush();
				} else if(settingsTouchArea.contains(touchPos.x, touchPos.y)) {
					if(!settingsModel.isFlipping()) {
                        flipSound.play(soundModel.isActive());
						settingsModel.setJustFlipped(true);
						settingsModel.setActive(!settingsModel.isActive());
					}
				} else if(!settingsModel.isActive()) {
					if(soundTouchArea.contains(touchPos.x, touchPos.y)) {
						if(!soundModel.isFlipping()) {
							soundModel.setJustFlipped(true);
							soundModel.setActive(!soundModel.isActive());
							flipSound.play(soundModel.isActive());

							save.putBoolean("sound", soundModel.isActive());
							save.flush();
						}
					} else if(infoTouchArea.contains(touchPos.x, touchPos.y)) {
						Gdx.net.openURI("https://noskulls.polygonull.com/privacy");
					} else if(helpTouchArea.contains(touchPos.x, touchPos.y)) {
						playSound.play(soundModel.isActive());
						settingsModel.flip();
						counterMap.get("settings").reset();
						state = State.TUTORIAL;
					}
				}
			} else if(state == State.GAME) {
				if(pauseTouchArea.contains(touchPos.x, touchPos.y)) {
				    pauseSound.play(soundModel.isActive());
					counterMap.get("background").freeze();
					state = State.PAUSE;

					save.putString("state", state.name());
					save.flush();
				} else {
					if(save.getBoolean("tutorial", true)) {
						if(!panelsTouchArea[tutorialI][tutorialJ].contains(touchPos.x, touchPos.y)) {
							return;
						}
					}
					for (int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
						for (int j = 0; j < BOARD_WIDTH_COUNT; j++) {
							if (panelsTouchArea[i][j].contains(touchPos.x, touchPos.y)) {
								if (!flipping) {
									flipSound.play(soundModel.isActive());
									tries--;
									flipping = true;
									panelModels[i][j].setJustFlipped(true);
									panelModels[i][j].setActive(!panelModels[i][j].isActive());
									double count = panelModels[i][j].isActive() ? counterMap.get("fail_panels").stepDown() : counterMap.get("fail_panels").step();
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
					playSound.play(soundModel.isActive());
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
						flipSound.play(soundModel.isActive());
						settingsModel.setJustFlipped(true);
						settingsModel.setActive(!settingsModel.isActive());
					}
				} else if(!settingsModel.isActive()) {
					if(soundTouchArea.contains(touchPos.x, touchPos.y)) {
						if(!soundModel.isFlipping()) {
							flipSound.play(soundModel.isActive());
							soundModel.setJustFlipped(true);
							soundModel.setActive(!soundModel.isActive());
							flipSound.play(soundModel.isActive());

							save.putBoolean("sound", soundModel.isActive());
							save.flush();
						}
					} else if(infoTouchArea.contains(touchPos.x, touchPos.y)) {
						Gdx.net.openURI("https://noskulls.polygonull.com/privacy");
					} else if(helpTouchArea.contains(touchPos.x, touchPos.y)) {
						playSound.play(soundModel.isActive());
						settingsModel.flip();
						counterMap.get("settings").reset();
						state = State.TUTORIAL;
					}
				}
			} else if(state == State.TUTORIAL) {
				if(settingsTouchArea.contains(touchPos.x, touchPos.y)) {
					pauseSound.play(soundModel.isActive());
					state = State.valueOf(save.getString("state", State.MAIN_MENU.name()));
					if (counterMap.get("fail_panels").getRealCount() == counterMap.get("fail_panels").getFloor() && lives == 0) {
						state = State.GAME_OVER;
					}
				}
			}

		}

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

	@Override
	public void render() {

		startRender();

		if (state == State.MAIN_MENU) {
			renderMainMenu();
		} else if (state == State.GAME) {
			renderGame();
			if(save.getBoolean("tutorial", true)) {
				renderTutorialGame();
			}
		} else if(state == State.PAUSE) {
			renderMainMenu();
		} else if(state == State.SUCCESS) {
			if(counterMap.get("success_panel_heart_pulse").getRealCount() == counterMap.get("success_panel_heart_pulse").getInitialCount()
					&& counterMap.get("success_panel_heart_pulse").getRepeats() == 0) {
				successSound.play(soundModel.isActive());
			}
			renderGame();
			renderSuccess();
			if(counterMap.get("success_heart_pulse").getRealCount() >= counterMap.get("success_heart_pulse").getCeiling()) {
				counterMap.get("success_panel_heart_pulse").reset();
				counterMap.get("success_heart_pulse").reset();

				boolean tutorial = save.getBoolean("tutorial", true);

				level++;
				round++;
				if(level > best) {
					best = level;
				}
				if(round > (prevTries * prevTries) + (tutorial ? 1 : 0)) {
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

				if(tutorial) {
					save.putBoolean("tutorial", false);
				}
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

				if(tutorial) {
					state = State.TUTORIAL;
				}
			}
		} else if(state == State.FAIL) {
			if(counterMap.get("fail_panel_pulse").getRealCount() == counterMap.get("fail_panel_pulse").getInitialCount()
					&& counterMap.get("fail_panel_pulse").getRepeats() == 0) {
				failSound.play(soundModel.isActive());
				skulls = counterMap.get("fail_panels").getRealCount();
			}
			renderGame();
			renderFail();
			if(counterMap.get("fail_panel_pulse").getRepeats() == counterMap.get("fail_panel_pulse").getMaxRepeats() && !flipping && reset) {
				counterMap.get("fail_panel_pulse").reset();
				counterMap.get("fail_delay").reset();
				lives--;
				tries = prevTries;
				reset = false;
				state = State.GAME;

				save.putInteger("lives", lives);
				save.putInteger("tries", tries);
				save.putString("panels", boardToString(panelModels));
				save.putString("state", state.name());
				save.flush();
			}
		} else if(state == State.GAME_OVER) {
			if(counterMap.get("fail_panels").getRealCount() != counterMap.get("fail_panels").getFloor()) {
				gameOverSound.play(soundModel.isActive());
				counterMap.get("fail_panels").reset();
			}
			renderGameOver();
		} else if(state == State.TUTORIAL) {
			renderTutorial();
		}

		if(state == State.GAME) {
			checkWinCondition();
		}

		checkTouch();

		endRender();

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		recalculateCoordinates();
	}

	@Override
	public void create() {

		flipSound = new Sound("flip.mp3");
		resetSound = new Sound("reset.mp3");
		playSound = new Sound("play.ogg");
		pauseSound = new Sound("pause.ogg");
		gameOverSound = new Sound("game_over.ogg");
		successSound = new Sound("success.ogg");
		failSound = new Sound("fail.ogg");

		camera = new OrthographicCamera();
		camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
		viewport = new ExtendViewport(CAMERA_WIDTH, CAMERA_HEIGHT, camera);
		batch = new SpriteBatch();
		panelsTouchArea = new Rectangle[BOARD_WIDTH_COUNT][BOARD_HEIGHT_COUNT];
		panelModels = new SkullAndHeartPanel[BOARD_WIDTH_COUNT][BOARD_HEIGHT_COUNT];
		prevPanelModels = new SkullAndHeartPanel[BOARD_WIDTH_COUNT][BOARD_HEIGHT_COUNT];
		settingsModel = new Panel();
		soundModel = new Panel();
		alphanumericHelper = new AlphanumericHelper(alphanumerics);

		heartModels = new IntegerPair[BOARD_WIDTH_COUNT][BOARD_HEIGHT_COUNT];

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
		soundSprite = new Texture("sound.png");
		infoSprite = new Texture("info.png");
		whitePixel = new Texture("alpha_pixel.png");
		panelLifeSprite = new Texture("panel_life.png");
		bluePixelSprite =  new Texture("blue_pixel.png");
		helpSprite = new Texture("help.png");
		closeSprite = new Texture("close.png");

		// Initialize touch areas
		playTouchArea = new Rectangle(PLAY_X, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
		pauseTouchArea = new Rectangle(PAUSE_X, PAUSE_Y, PAUSE_WIDTH, PAUSE_HEIGHT);
		settingsTouchArea = new Rectangle(SETTINGS_X, SETTINGS_Y, SETTINGS_WIDTH, SETTINGS_HEIGHT);
		soundTouchArea = new Rectangle(SOUND_X, SOUND_Y, SOUND_WIDTH, SOUND_HEIGHT);
		infoTouchArea = new Rectangle(INFO_X, INFO_Y, INFO_WIDTH, INFO_HEIGHT);
		helpTouchArea = new Rectangle(HELP_X, HELP_Y, HELP_WIDTH, HELP_HEIGHT);

		// Initialize panel touch areas, panel models and heart models
		float start_x = PANEL_1_X;
		float start_y = PANEL_1_Y;
		for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
			for(int j = 0; j < BOARD_WIDTH_COUNT; j++) {
				panelsTouchArea[i][j] = new Rectangle(start_x, start_y, PANEL_WIDTH, PANEL_HEIGHT);
				panelModels[i][j] = new SkullAndHeartPanel(i, j);
				prevPanelModels[i][j] = new SkullAndHeartPanel(i, j);
				heartModels[i][j] = new IntegerPair((int)start_x + 20, (int)start_y + 22);
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

		counterMap.put("background", new Counter(0, 9, true, (int) CAMERA_WIDTH * 10, 0, false, false, 10));
		counterMap.put("settings", new Counter(0, 1, true, true, false, 0, 9, 0, false, false, 1));
		counterMap.put("fail_panels", new Counter(0, 1, true, 36, 0, false, true, 1));
		counterMap.put("fail_panel_pulse", new Counter(1000, 8, true, true, true, 2, 1040, 960, false, false, 1000));
		counterMap.put("success_panel_heart_pulse", new Counter(1000, 4, true, true, false, 1, 1120, 1000, false, false, 1000));
		counterMap.put("success_heart_pulse", new Counter(100, 11, true, 50000, 100, false, false, 100));
		counterMap.put("text_pulse", new Counter(1000, 4, true, true, true, 0, 1040, 960, false, false, 1000));
		counterMap.put("fail_delay", new Counter(0, 1, true, 32, 0, false, true, 1));

		font50 = new BitmapFont(Gdx.files.internal("font50.fnt"),Gdx.files.internal("font50.png"),false);
		font20 = new BitmapFont(Gdx.files.internal("font20.fnt"),Gdx.files.internal("font20.png"),false);

		loadGame();

		backgroundSprite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		playSprite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		bestSprite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		pauseSprite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		scoreSprite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		cupSprite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		symbolsSprite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		lifeSprite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		triesSprite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		panelsSprite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		settingsSprite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		soundSprite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		infoSprite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		whitePixel.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		panelLifeSprite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		bluePixelSprite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		helpSprite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		closeSprite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		backgroundSprite.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

	}

	@Override
	public void dispose() {
		batch.dispose();
		backgroundSprite.dispose();
		playSprite.dispose();
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
