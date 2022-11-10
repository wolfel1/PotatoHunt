package hunt.potato;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import hunt.potato.screens.GameScreen;

public class PotatoHunt extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
	private Screen mainMenuScreen;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		mainMenuScreen = new GameScreen(this);
		this.setScreen(mainMenuScreen);
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
		mainMenuScreen.dispose();
	}
}
