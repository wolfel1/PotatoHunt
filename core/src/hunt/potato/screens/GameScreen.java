package hunt.potato.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import hunt.potato.actors.Plant;
import hunt.potato.actors.Player;
import hunt.potato.PotatoHunt;
import hunt.potato.utils.Constants;

import java.util.Iterator;

public class GameScreen implements Screen {
    final PotatoHunt game;
    private HUDScreen hudScreen;

    private Texture field;
    private Sound dropSound;
    private Music rainMusic;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Player player;
    private Array<Plant> plants;

    private int potatoCount = 0;

    private boolean showCollect = false;

    private long startTime;

    public GameScreen(PotatoHunt game) {
        this.game = game;
        game.font.setColor(1,1,1,1);
        game.font.getData().scale(1);

        field = new Texture(Gdx.files.internal("field.jpg"));

        // load the drop sound effect and the rain background "music"
        //dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        //rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        // start the playback of the background music immediately
		/*rainMusic.setLooping(true);
        rainMusic.play();*/

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        batch = new SpriteBatch();

        hudScreen = new HUDScreen(game);

        player = new Player(field);

        plants = new Array<>();
        spawnPlants();

        startTime = TimeUtils.millis();
    }

    private void spawnPlants() {
        for (int i = 0; i < 10; i++) {
            Plant plant = new Plant();
            plants.add(plant);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
        if(camera.position.y < camera.viewportHeight / 2)
            camera.position.y = camera.viewportHeight / 2;
        if(camera.position.y > field.getHeight() * 2 - camera.viewportHeight / 2)
            camera.position.y = field.getHeight() * 2 - camera.viewportHeight / 2;
        if(camera.position.x < camera.viewportWidth / 2)
            camera.position.x = camera.viewportWidth / 2;
        if(camera.position.x > field.getWidth() * 2 - camera.viewportWidth / 2)
            camera.position.x = field.getWidth() * 2 - camera.viewportWidth / 2;
        camera.update();

        hudScreen.updateTimeLabel(TimeUtils.timeSinceMillis(startTime));
        hudScreen.updateCountLabel(potatoCount);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(field, 0, 0, field.getWidth() * 2, field.getHeight() * 2);
        for(Plant plant : plants) {
            plant.draw(batch, 1);
        }
        player.draw(batch, 1);
        batch.end();
        hudScreen.render(delta);

        player.act(delta);

        for (Iterator<Plant> iter = plants.iterator(); iter.hasNext(); ) {
            Plant plant = iter.next();
            Rectangle r = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
            Rectangle s = new Rectangle(plant.getX(), plant.getY(), plant.getWidth(), plant.getHeight());
            if (r.overlaps(s)) {
                potatoCount += 5;
                iter.remove();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        hudScreen.resize(width, height);
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
    public void dispose() {
        field.dispose();
        /*dropSound.dispose();
        rainMusic.dispose();*/
        batch.dispose();
        hudScreen.dispose();
    }
}
