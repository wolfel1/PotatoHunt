package hunt.potato;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final PotatoHunt game;

    private Texture potato;
    private Texture plant;
    private Texture field;
    private Sound dropSound;
    private Music rainMusic;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Player player;
    private Array<Rectangle> raindrops;
    private long lastDropTime;


    public GameScreen(PotatoHunt game) {
        this.game = game;

        potato = new Texture(Gdx.files.internal("potato.png"));
        plant = new Texture(Gdx.files.internal("plant/plant1.png"));
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

        player = new Player(field);

        raindrops = new Array<>();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.position.set(player.getX(), player.getY(), 0);
        if(camera.position.y < camera.viewportHeight / 2)
            camera.position.y = camera.viewportHeight / 2;
        if(camera.position.y > field.getHeight() * 2 - camera.viewportHeight / 2)
            camera.position.y = field.getHeight() * 2 - camera.viewportHeight / 2;
        if(camera.position.x < camera.viewportWidth / 2)
            camera.position.x = camera.viewportWidth / 2;
        if(camera.position.x > field.getWidth() * 2 - camera.viewportWidth / 2)
            camera.position.x = field.getWidth() * 2 - camera.viewportWidth / 2;
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(field, 0, 0, field.getWidth() * 2, field.getHeight() * 2);
        player.draw(batch, 1);
        batch.end();

        player.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
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
        potato.dispose();
        plant.dispose();
        /*dropSound.dispose();
        rainMusic.dispose();*/
        batch.dispose();

    }
}
