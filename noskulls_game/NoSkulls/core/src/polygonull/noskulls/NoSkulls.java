package polygonull.noskulls;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Random;

import polygonull.noskulls.components.Panel;
import polygonull.noskulls.components.SkullAndHeartPanel;
import polygonull.noskulls.helpers.AlphanumericHelper;
import polygonull.noskulls.helpers.State;

public class NoSkulls extends ApplicationAdapter {

	private class Pair {

		public int i;
		public int j;

		public Pair(int i, int j) {
			this.i = i;
			this.j = j;
		}

	}

	private static String alphanumerics = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!";

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
	public static float SYMBOL_BIG_WIDTH = 40;
	public static float SYMBOL_BIG_HEIGHT = 44;

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
	public static float MAIN_MENU_Y = (CAMERA_HEIGHT - MAIN_MENU_HEIGHT) / 2;
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
	public static float SCORE_Y = CAMERA_HEIGHT - GAME_4_Y - SCORE_HEIGHT;
	public static float GAME_CUP_X = SCORE_X + (SCORE_WIDTH - CUP_WIDTH) / 2;
	public static float GAME_CUP_Y = SCORE_Y + (SCORE_HEIGHT - CUP_HEIGHT) / 2;
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

	// Game state
	private State state = State.MAIN_MENU;
	private int level = 1;
	private int best = 1;
	private int prevTries = 3;
	private int tries = 3;
	private int lives = 3;
	private ArrayList<Pair> prevMoves;
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
	private Texture symbolsBigSprite;

	// Touch areas
	private Rectangle settingsTouchArea;
	private Rectangle musicTouchArea;
	private Rectangle soundTouchArea;
	private Rectangle infoTouchArea;
	private Rectangle instagramTouchArea;
	private Rectangle playTouchArea;
	private Rectangle pauseTouchArea;
	private Rectangle[][] panelsTouchArea;

	private OrthographicCamera camera;

	private SkullAndHeartPanel[][] panelModels;
	private Panel settingsModel;
	private Panel musicModel;
	private Panel soundModel;

	private float timer;
	private boolean timerdown;
	private float mover;
	private boolean flipping = false;

	private void clearBoard() {

		for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
			for(int j = 0; j < BOARD_WIDTH_COUNT; j++) {
				panelModels[i][j].setActive(true);
				panelModels[i][j].setState(16);
				panelModels[i][j].setPrevState(16);
			}
		}

	}

	private ArrayList<Pair> createBoard(int tries) {

		ArrayList<Pair> moves = new ArrayList<Pair>();

		while(tries != 0) {
			Random rand = new Random();
			int i = rand.nextInt(BOARD_HEIGHT_COUNT);
			int j = rand.nextInt(BOARD_WIDTH_COUNT);
			panelModels[i][j].setActive(!panelModels[i][j].isActive());
			panelModels[i][j].setState(panelModels[i][j].isActive() ? 16 : 0);
			panelModels[i][j].setPrevState(panelModels[i][j].isActive() ? 16 : 0);
			for (Panel panel : panelModels[i][j].getNeighbours()) {
				panel.setActive(!panel.isActive());
				panel.setState(panel.isActive() ? 16 : 0);
				panel.setPrevState(panel.isActive() ? 16 : 0);
			}
			tries--;
			moves.add(new Pair(i, j));
		}

		return moves;

	}

	private void recreateBoard() {

		for(int k = 0; k < prevMoves.size(); k++) {
			int i = prevMoves.get(k).i;
			int j = prevMoves.get(k).j;
			panelModels[i][j].setActive(!panelModels[i][j].isActive());
			panelModels[i][j].setState(panelModels[i][j].isActive() ? 16 : 0);
			panelModels[i][j].setPrevState(panelModels[i][j].isActive() ? 16 : 0);
			for (Panel panel : panelModels[i][j].getNeighbours()) {
				panel.setActive(!panel.isActive());
				panel.setState(panel.isActive() ? 16 : 0);
				panel.setPrevState(panel.isActive() ? 16 : 0);
			}
		}

	}

	@Override
	public void create() {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
		batch = new SpriteBatch();
		panelsTouchArea = new Rectangle[BOARD_WIDTH_COUNT][BOARD_HEIGHT_COUNT];
		panelModels = new SkullAndHeartPanel[BOARD_WIDTH_COUNT][BOARD_HEIGHT_COUNT];
		settingsModel = new Panel();
		musicModel = new Panel();
		soundModel = new Panel();
		alphanumericHelper = new AlphanumericHelper(alphanumerics);

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
		symbolsBigSprite = new Texture("symbols_grey.png");

		// Initialize touch areas
		playTouchArea = new Rectangle(PLAY_X, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
		pauseTouchArea = new Rectangle(PAUSE_X, PAUSE_Y, PAUSE_WIDTH, PAUSE_HEIGHT);
		settingsTouchArea = new Rectangle(SETTINGS_X, SETTINGS_Y, SETTINGS_WIDTH, SETTINGS_HEIGHT);
		musicTouchArea = new Rectangle(MUSIC_X, MUSIC_Y, MUSIC_WIDTH, MUSIC_HEIGHT);
		soundTouchArea = new Rectangle(SOUND_X, SOUND_Y, SOUND_WIDTH, SOUND_HEIGHT);
		float start_x = PANEL_1_X;
		float start_y = PANEL_1_Y;
		for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
			for(int j = 0; j < BOARD_WIDTH_COUNT; j++) {
				panelModels[i][j] = new SkullAndHeartPanel(i, j);
				panelsTouchArea[i][j] = new Rectangle(start_x, start_y, PANEL_WIDTH, PANEL_HEIGHT);
				start_x += PANEL_WIDTH + BOARD_WIDTH_SPACE;
			}
			start_x = PANEL_1_X;
			start_y -= PANEL_HEIGHT + BOARD_HEIGHT_SPACE;
		}

		for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
			for (int j = 0; j < BOARD_WIDTH_COUNT; j++) {
				panelModels[i][j].populateNeighbours(panelModels);
			}
		}

		prevMoves = createBoard(tries);

		timer = 1;
		timerdown = false;
		mover = 0;

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

	public void checkFlipping(Panel panel) {
		if(panel.isJustFlipped()) {
			panel.setPrevState(panel.getState());
			panel.changeState();
			panel.setJustFlipped(false);
		} else if(panel.getPrevState() < panel.getState()) {
			panel.changeState();
			if(panel.getState() == panel.getPrevState() + 16) {
				if(panel.getState() == 32) {
					panel.setState(0);
				}
				panel.setPrevState(panel.getState());
			}
		}
	}

	public void renderMainMenu() {

		batch.draw(backgroundSprite, BACKGROUND_1_X + mover, BACKGROUND_1_Y - mover);
		batch.draw(backgroundSprite, BACKGROUND_2_X + mover, BACKGROUND_2_Y - mover);
		batch.draw(backgroundSprite, BACKGROUND_3_X + mover, BACKGROUND_3_Y - mover);
		batch.draw(backgroundSprite, BACKGROUND_4_X + mover, BACKGROUND_4_Y - mover);

		batch.setColor(255, 255, 255, 0.7f);
		ArrayList<Integer> noOffsets = alphanumericHelper.stringToOffsets("PAUSED");
		int noOffset = (int)((CAMERA_WIDTH - SYMBOL_BIG_WIDTH * noOffsets.size()) / 2);
		for(int i = 0; i < noOffsets.size(); i++) {
			batch.draw(symbolsBigSprite,  noOffset + i * SYMBOL_BIG_WIDTH, (CAMERA_HEIGHT - SYMBOL_BIG_HEIGHT) / 2 + 200, 0, 0, SYMBOL_BIG_WIDTH, SYMBOL_HEIGHT, 1, 1, 0, (int)(noOffsets.get(i) * SYMBOL_BIG_WIDTH), 0, (int)SYMBOL_BIG_WIDTH, (int)SYMBOL_BIG_HEIGHT, false, false);
		}
		ArrayList<Integer> skullsOffsets = alphanumericHelper.stringToOffsets("SKULLS");
		int skullsOffset = (int)((CAMERA_WIDTH - SYMBOL_BIG_WIDTH * skullsOffsets.size()) / 2);
		for(int i = 0; i < skullsOffsets.size(); i++) {
			batch.draw(symbolsBigSprite,  skullsOffset + i * SYMBOL_BIG_WIDTH, (CAMERA_HEIGHT - SYMBOL_BIG_HEIGHT) / 2 - 200 - SYMBOL_BIG_HEIGHT, 0, 0, SYMBOL_BIG_WIDTH, SYMBOL_HEIGHT, 1, 1, 0, (int)(skullsOffsets.get(i) * SYMBOL_BIG_WIDTH), 0, (int)SYMBOL_BIG_WIDTH, (int)SYMBOL_BIG_HEIGHT, false, false);
		}
		batch.setColor(255,255,255, 1);

		batch.draw(playSprite, PLAY_X, PLAY_Y, 0, 0, PLAY_WIDTH, PLAY_HEIGHT, 1f, 1f, 0, 0, 0, (int)PLAY_WIDTH, (int)PLAY_HEIGHT, false, false);
		batch.draw(bestSprite, BEST_X, BEST_Y);
		ArrayList<Integer> bestOffsets = alphanumericHelper.stringToOffsets(Integer.toString(best));
		int bestOffset = (int)(BEST_X + (BEST_WIDTH + CUP_WIDTH + UI_WIDTH_SPACE_3 - SYMBOL_WIDTH * bestOffsets.size()) / 2);
		batch.draw(cupSprite, bestOffset - CUP_WIDTH - UI_WIDTH_SPACE_3, BEST_Y + (BEST_HEIGHT - CUP_HEIGHT) / 2);
		for(int i = 0; i < bestOffsets.size(); i++) {
			batch.draw(symbolsSprite,  bestOffset + i * SYMBOL_WIDTH, BEST_Y + (BEST_HEIGHT - SYMBOL_HEIGHT) / 2, 0, 0, SYMBOL_WIDTH, SYMBOL_HEIGHT, 1f, 1f, 0, (int)(bestOffsets.get(i) * SYMBOL_WIDTH), 0, (int)SYMBOL_WIDTH, (int)SYMBOL_HEIGHT, false, false);
		}

		if(!settingsModel.isActive()) {
			checkFlipping(settingsModel);
			batch.draw(settingsSprite, SETTINGS_X, SETTINGS_Y, (int)(settingsModel.getState() * SETTINGS_WIDTH), 0, (int)SETTINGS_WIDTH, (int)SETTINGS_HEIGHT);
			batch.setColor(255, 255, 255, 0.5f);
			ArrayList<Integer> closeOffsets = alphanumericHelper.stringToOffsets("CLOSE");
			int closeOffset = (int)(int)(SETTINGS_WIDTH + UI_WIDTH_SPACE_2 * 2);
			for(int i = 0; i < closeOffsets.size(); i++) {
				batch.draw(symbolsBigSprite,  closeOffset + i * SYMBOL_BIG_WIDTH * 0.4f, SETTINGS_Y + (SETTINGS_HEIGHT - SYMBOL_BIG_HEIGHT * 0.4f) / 2, 0, 0, SYMBOL_BIG_WIDTH, SYMBOL_HEIGHT, 0.4f, 0.4f, 0, (int)(closeOffsets.get(i) * SYMBOL_BIG_WIDTH), 0, (int)SYMBOL_BIG_WIDTH, (int)SYMBOL_BIG_HEIGHT, false, false);
			}
			batch.setColor(255, 255, 255, 1f);
			if (timer < 16) {
				timer++;
			}
			if(timer > 1) {
				checkFlipping(musicModel);
				batch.draw(musicSprite, MUSIC_X, MUSIC_Y, (int) (musicModel.getState() * MUSIC_WIDTH), 0, (int) MUSIC_WIDTH, (int) MUSIC_HEIGHT);
				batch.setColor(255, 255, 255, 0.7f);
				ArrayList<Integer> musicOffsets = alphanumericHelper.stringToOffsets("MUSIC");
				int musicOffset = (int)(MUSIC_WIDTH + UI_WIDTH_SPACE_2 * 2);
				for(int i = 0; i < musicOffsets.size(); i++) {
					batch.draw(symbolsBigSprite,  musicOffset + i * SYMBOL_BIG_WIDTH * 0.4f, MUSIC_Y + (MUSIC_HEIGHT - SYMBOL_BIG_HEIGHT * 0.4f) / 2, 0, 0, SYMBOL_BIG_WIDTH, SYMBOL_HEIGHT, 0.4f, 0.4f, 0, (int)(musicOffsets.get(i) * SYMBOL_BIG_WIDTH), 0, (int)SYMBOL_BIG_WIDTH, (int)SYMBOL_BIG_HEIGHT, false, false);
				}
				batch.setColor(255, 255, 255, 1f);
			}
			if(timer > 5) {
				checkFlipping(soundModel);
				batch.draw(soundSprite, SOUND_X, SOUND_Y, (int) (soundModel.getState() * SOUND_WIDTH), 0, (int) SOUND_WIDTH, (int) SOUND_HEIGHT);
				batch.setColor(255, 255, 255, 0.7f);
				ArrayList<Integer> soundOffsets = alphanumericHelper.stringToOffsets("SFX");
				int soundOffset = (int)(SOUND_WIDTH + UI_WIDTH_SPACE_2 * 2);
				for(int i = 0; i < soundOffsets.size(); i++) {
					batch.draw(symbolsBigSprite,  soundOffset + i * SYMBOL_BIG_WIDTH * 0.4f, SOUND_Y + (SOUND_HEIGHT - SYMBOL_BIG_HEIGHT * 0.4f) / 2, 0, 0, SYMBOL_BIG_WIDTH, SYMBOL_HEIGHT, 0.4f, 0.4f, 0, (int)(soundOffsets.get(i) * SYMBOL_BIG_WIDTH), 0, (int)SYMBOL_BIG_WIDTH, (int)SYMBOL_BIG_HEIGHT, false, false);
				}
				batch.setColor(255, 255, 255, 1f);
			}
			if(timer > 9) {
				batch.draw(infoSprite, INFO_X, INFO_Y);
				batch.setColor(255, 255, 255, 0.7f);
				ArrayList<Integer> infoOffsets = alphanumericHelper.stringToOffsets("PRIVACY POLICY");
				int infoOffset = (int)(INFO_WIDTH + UI_WIDTH_SPACE_2 * 2);
				for(int i = 0; i < infoOffsets.size(); i++) {
					batch.draw(symbolsBigSprite,  infoOffset + i * SYMBOL_BIG_WIDTH * 0.4f, INFO_Y + (INFO_HEIGHT - SYMBOL_BIG_HEIGHT * 0.4f) / 2, 0, 0, SYMBOL_BIG_WIDTH, SYMBOL_HEIGHT, 0.4f, 0.4f, 0, (int)(infoOffsets.get(i) * SYMBOL_BIG_WIDTH), 0, (int)SYMBOL_BIG_WIDTH, (int)SYMBOL_BIG_HEIGHT, false, false);
				}
				batch.setColor(255, 255, 255, 1f);
			}
			if(timer > 13) {
				batch.draw(instagramSprite, INSTAGRAM_X, INSTAGRAM_Y);
				batch.setColor(255, 255, 255, 0.7f);
				ArrayList<Integer> instagramOffsets = alphanumericHelper.stringToOffsets("INSTAGRAM");
				int instagramOffset = (int)(INSTAGRAM_WIDTH + UI_WIDTH_SPACE_2 * 2);
				for(int i = 0; i < instagramOffsets.size(); i++) {
					batch.draw(symbolsBigSprite,  instagramOffset + i * SYMBOL_BIG_WIDTH * 0.4f, INSTAGRAM_Y + (INSTAGRAM_HEIGHT - SYMBOL_BIG_HEIGHT * 0.4f) / 2, 0, 0, SYMBOL_BIG_WIDTH, SYMBOL_HEIGHT, 0.4f, 0.4f, 0, (int)(instagramOffsets.get(i) * SYMBOL_BIG_WIDTH), 0, (int)SYMBOL_BIG_WIDTH, (int)SYMBOL_BIG_HEIGHT, false, false);
				}
				batch.setColor(255, 255, 255, 1f);
			}
		} else {
			checkFlipping(settingsModel);
			batch.draw(settingsSprite, SETTINGS_X, SETTINGS_Y, (int)(settingsModel.getState() * SETTINGS_WIDTH), 0, (int)SETTINGS_WIDTH, (int)SETTINGS_HEIGHT);
			batch.setColor(255, 255, 255, 0.5f);
			ArrayList<Integer> settingsOffsets = alphanumericHelper.stringToOffsets("MENU");
			int settingsOffset = (int)(int)(SETTINGS_WIDTH + UI_WIDTH_SPACE_2 * 2);
			for(int i = 0; i < settingsOffsets.size(); i++) {
				batch.draw(symbolsBigSprite,  settingsOffset + i * SYMBOL_BIG_WIDTH * 0.4f, SETTINGS_Y + (SETTINGS_HEIGHT - SYMBOL_BIG_HEIGHT * 0.4f) / 2, 0, 0, SYMBOL_BIG_WIDTH, SYMBOL_HEIGHT, 0.4f, 0.4f, 0, (int)(settingsOffsets.get(i) * SYMBOL_BIG_WIDTH), 0, (int)SYMBOL_BIG_WIDTH, (int)SYMBOL_BIG_HEIGHT, false, false);
			}
			batch.setColor(255, 255, 255, 1f);
			if (timer > 0) {
				timer--;
			}
			if(timer > 3) {
				checkFlipping(musicModel);
				batch.draw(musicSprite, MUSIC_X, MUSIC_Y, (int) (musicModel.getState() * MUSIC_WIDTH), 0, (int) MUSIC_WIDTH, (int) MUSIC_HEIGHT);
			}
			if(timer > 7) {
				checkFlipping(soundModel);
				batch.draw(soundSprite, SOUND_X, SOUND_Y, (int) (soundModel.getState() * SOUND_WIDTH), 0, (int) SOUND_WIDTH, (int) SOUND_HEIGHT);
			}
			if(timer > 11) {
				batch.draw(infoSprite, INFO_X, INFO_Y);
			}
			if(timer > 15) {
				batch.draw(instagramSprite, INSTAGRAM_X, INSTAGRAM_Y);
			}
		}

		mover = mover + 0.9f;
		if(mover > CAMERA_WIDTH) {
			mover = 0;
		}

	}

	public void renderFail() {

		batch.draw(whitePixel, 0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		ArrayList<Integer> failOffsets = alphanumericHelper.stringToOffsets("ALMOST"); // CLOSE ENOUGH
		int failOffset = (int)((CAMERA_WIDTH - SYMBOL_WIDTH * 1.5f * failOffsets.size()) / 2);

		for(int i = 0; i < failOffsets.size(); i++) {
			batch.draw(symbolsBigSprite,  failOffset + i * SYMBOL_BIG_WIDTH * 1.5f, (CAMERA_HEIGHT - SYMBOL_BIG_HEIGHT * 1.5f) / 2, 0, 0, SYMBOL_BIG_WIDTH, SYMBOL_HEIGHT, 1.5f, 1.5f, 0, (int)(failOffsets.get(i) * SYMBOL_BIG_WIDTH), 0, (int)SYMBOL_BIG_WIDTH, (int)SYMBOL_BIG_HEIGHT, false, false);
		}

	}

	public void renderSuccess() {

		batch.draw(whitePixel, 0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		ArrayList<Integer> failOffsets = alphanumericHelper.stringToOffsets("NICE");
		int failOffset = (int)((CAMERA_WIDTH - SYMBOL_BIG_WIDTH * failOffsets.size()) / 2);

		for(int i = 0; i < failOffsets.size(); i++) {
			batch.draw(symbolsBigSprite,  failOffset + i * SYMBOL_BIG_WIDTH, (CAMERA_HEIGHT - SYMBOL_BIG_HEIGHT) / 2, 0, 0, SYMBOL_BIG_WIDTH, SYMBOL_HEIGHT, 1, 1, 0, (int)(failOffsets.get(i) * SYMBOL_BIG_WIDTH), 0, (int)SYMBOL_BIG_WIDTH, (int)SYMBOL_BIG_HEIGHT, false, false);
		}

	}

	public void renderGameOver() {

		batch.draw(whitePixel, 0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		ArrayList<Integer> failOffsets = alphanumericHelper.stringToOffsets("GAME OVER");
		int failOffset = (int)((CAMERA_WIDTH - SYMBOL_BIG_WIDTH * 1.5f * failOffsets.size()) / 2);

		for(int i = 0; i < failOffsets.size(); i++) {
			batch.draw(symbolsBigSprite,  failOffset + i * SYMBOL_WIDTH * 1.5f, (CAMERA_HEIGHT - SYMBOL_HEIGHT * 1.5f) / 2, 0, 0, SYMBOL_WIDTH, SYMBOL_HEIGHT, 1.5f, 1.5f, 0, (int)(failOffsets.get(i) * SYMBOL_WIDTH), 0, (int)SYMBOL_WIDTH, (int)SYMBOL_HEIGHT, false, false);
		}

	}

	public void renderGame() {

		batch.draw(backgroundSprite, BACKGROUND_1_X + mover, BACKGROUND_1_Y - mover);
		batch.draw(backgroundSprite, BACKGROUND_2_X + mover, BACKGROUND_2_Y - mover);
		batch.draw(backgroundSprite, BACKGROUND_3_X + mover, BACKGROUND_3_Y - mover);
		batch.draw(backgroundSprite, BACKGROUND_4_X + mover, BACKGROUND_4_Y - mover);

		batch.draw(pauseSprite, PAUSE_X, PAUSE_Y);
		batch.setColor(255, 255, 255, 0.7f);
		ArrayList<Integer> settingsOffsets = alphanumericHelper.stringToOffsets("PAUSE");
		int settingsOffset = (int)(5);
		for(int i = 0; i < settingsOffsets.size(); i++) {
			batch.draw(symbolsBigSprite,  settingsOffset + i * SYMBOL_BIG_WIDTH * 0.4f, SETTINGS_Y - 25, 0, 0, SYMBOL_BIG_WIDTH, SYMBOL_HEIGHT, 0.4f, 0.4f, 0, (int)(settingsOffsets.get(i) * SYMBOL_BIG_WIDTH), 0, (int)SYMBOL_BIG_WIDTH, (int)SYMBOL_BIG_HEIGHT, false, false);
		}
		batch.setColor(255, 255, 255, 1f);

		batch.draw(scoreSprite, SCORE_X, SCORE_Y);
		batch.draw(cupSprite, GAME_CUP_X, GAME_CUP_Y);
		ArrayList<Integer> levelOffsets = alphanumericHelper.stringToOffsets(Integer.toString(level));
		int levelOffset = (int)(GAME_CUP_X + CUP_WIDTH  + ((SCORE_WIDTH - CUP_WIDTH) / 2 - SYMBOL_WIDTH * levelOffsets.size()) / 2);
		for(int i = 0; i < levelOffsets.size(); i++) {
			batch.draw(symbolsSprite,  levelOffset + i * SYMBOL_WIDTH, SCORE_Y + (SCORE_HEIGHT - SYMBOL_HEIGHT) / 2, 0, 0, SYMBOL_WIDTH, SYMBOL_HEIGHT, 1, 1, 0, (int)(levelOffsets.get(i) * SYMBOL_WIDTH), 0, (int)SYMBOL_WIDTH, (int)SYMBOL_HEIGHT, false, false);
		}
		ArrayList<Integer> bestOffsets = alphanumericHelper.stringToOffsets(Integer.toString(best));
		int bestOffset = (int)(GAME_CUP_X - ((SCORE_WIDTH - CUP_WIDTH) / 2 + SYMBOL_WIDTH * bestOffsets.size()) / 2);
		for(int i = 0; i < bestOffsets.size(); i++) {
			batch.draw(symbolsSprite,  bestOffset + i * SYMBOL_WIDTH, SCORE_Y + (SCORE_HEIGHT - SYMBOL_HEIGHT) / 2, 0, 0, SYMBOL_WIDTH, SYMBOL_HEIGHT, 1, 1, 0, (int)(bestOffsets.get(i) * SYMBOL_WIDTH), 0, (int)SYMBOL_WIDTH, (int)SYMBOL_HEIGHT, false, false);
		}

		batch.draw(triesSprite, TRIES_X, TRIES_Y);
		ArrayList<Integer> triesOffsets = alphanumericHelper.stringToOffsets(Integer.toString(tries));
		int triesOffset = (int)(TRIES_X + (TRIES_WIDTH - SYMBOL_WIDTH * 1f * triesOffsets.size()) / 2);
		for(int i = 0; i < triesOffsets.size(); i++) {
			batch.draw(symbolsSprite,  triesOffset + i * SYMBOL_WIDTH * 1f, TRIES_Y + (TRIES_HEIGHT - SYMBOL_HEIGHT) / 2, 0, 0, SYMBOL_WIDTH, SYMBOL_HEIGHT, 1, 1, 0, (int)(triesOffsets.get(i) * SYMBOL_WIDTH), 0, (int)SYMBOL_WIDTH, (int)SYMBOL_HEIGHT, false, false);
		}
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
				batch.draw(panelsSprite, panelsTouchArea[i][j].x, panelsTouchArea[i][j].y, (int)PANEL_WIDTH * panelModels[i][j].getState(), 0, (int)PANEL_WIDTH, (int)PANEL_HEIGHT);
				if(panelModels[i][j].isJustFlipped()) {
					panelModels[i][j].setPrevState(panelModels[i][j].getState());
					panelModels[i][j].changeState();
					panelModels[i][j].setJustFlipped(false);
				} else if(panelModels[i][j].getPrevState() < panelModels[i][j].getState()) {
					panelModels[i][j].changeState();
					if(panelModels[i][j].getState() == panelModels[i][j].getPrevState() + 16) {
						flipping = false;
						if(panelModels[i][j].getState() == 32) {
							panelModels[i][j].setState(0);
						}
						panelModels[i][j].setPrevState(panelModels[i][j].getState());
					}
				}
			}
		}

		mover = mover + 0.9f;
		if(mover > CAMERA_WIDTH) {
			mover = 0;
		}

	}

	public void checkWinCondition() {

		boolean won = true;
		if(!flipping) {

			for(int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
				for (int j = 0; j < BOARD_WIDTH_COUNT; j++) {
					if(!panelModels[i][j].isActive()) {
						won = false;
						break;
					}
				}
				if(!won) {
					break;
				}
			}

			if(won) {
				tries = ++prevTries;
				level++;
				if(level > best) {
					best = level;
				}
				clearBoard();
				prevMoves = createBoard(tries);
			}

			if(!won && tries == 0) {
				lives--;
				tries = prevTries;
				clearBoard();
				recreateBoard();
			}

			if(!won && lives == 0) {
				level = 1;
				tries = 2;
				prevTries = 2;
				lives = 3;
				clearBoard();
				prevMoves = createBoard(tries);
			}

		}

	}

	public void checkTouch() {

		if(Gdx.input.justTouched()) {

			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);

			if(state == State.MAIN_MENU) {
				if (playTouchArea.contains(touchPos.x, touchPos.y)) {
					state = State.GAME;
				} else if(settingsTouchArea.contains(touchPos.x, touchPos.y)) {
					if(!settingsModel.isFlipping()) {
						settingsModel.setJustFlipped(true);
						settingsModel.setActive(!settingsModel.isActive());
					}
				} else if(!settingsModel.isActive()) {
					if(musicTouchArea.contains(touchPos.x, touchPos.y)) {
						if(!musicModel.isFlipping()) {
							musicModel.setJustFlipped(true);
							musicModel.setActive(!musicModel.isActive());
						}
					} else if(soundTouchArea.contains(touchPos.x, touchPos.y)) {
						if(!soundModel.isFlipping()) {
							soundModel.setJustFlipped(true);
							soundModel.setActive(!soundModel.isActive());
						}
					}
				}
			} else if(state == State.GAME) {
				if(pauseTouchArea.contains(touchPos.x, touchPos.y)) {
					state = State.MAIN_MENU;
				} else {
					for (int i = 0; i < BOARD_HEIGHT_COUNT; i++) {
						for (int j = 0; j < BOARD_WIDTH_COUNT; j++) {
							if (panelsTouchArea[i][j].contains(touchPos.x, touchPos.y)) {
								if (!flipping) {
									tries--;
									flipping = true;
									panelModels[i][j].setJustFlipped(true);
									panelModels[i][j].setActive(!panelModels[i][j].isActive());
									for (Panel panel : panelModels[i][j].getNeighbours()) {
										panel.setJustFlipped(true);
										panel.setActive(!panel.isActive());
									}
								}
							}
						}
					}
				}
			}

		}

	}

	@Override
	public void render () {

		startRender();

		if(state == State.MAIN_MENU) {
			renderMainMenu();
			//renderSuccess();
		} else if(state == State.GAME) {
			renderGame();
			//renderFail();
		}

		checkTouch();

		checkWinCondition();

		endRender();

	}

	@Override
	public void dispose () {
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
