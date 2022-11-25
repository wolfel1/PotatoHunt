package hunt.potato.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import hunt.potato.PotatoHunt;
import hunt.potato.actors.Plant;
import hunt.potato.actors.Player;
import hunt.potato.utils.Constants;

import java.util.Iterator;

public class GameScreen implements Screen {
    final PotatoHunt game;
    private HUDScreen hudScreen;
    private MenuScreen menuScreen;
    private WinScreen winScreen;

    private Texture field;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Player player;
    private Array<Plant> plants;
    private int plantCount = 10;
    private int pointsPerPlant = 5;
    private int maxCount;

    private int potatoCount = 0;

    private static boolean inMenu = true;

    private long startTime;

    public GameScreen(PotatoHunt game) {
            this.game = game;

            field = new Texture(Gdx.files.internal("field.jpg"));

            camera = new OrthographicCamera();
            camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
            batch = new SpriteBatch();

            menuScreen = new MenuScreen(game);
            hudScreen = new HUDScreen(game);

            player = new Player(field);

            plants = new Array<>();
    }

    private void spawnPlants() {
        for (var plant : plants) {
            plant.dispose();
        }
        plants.clear();
        for (int i = 0; i < plantCount; i++) {
            Plant plant = new Plant();
            plants.add(plant);
        }
        maxCount = plantCount * pointsPerPlant;
    }

    public void back() {
        menuScreen.setInput();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
        if (camera.position.y < camera.viewportHeight / 2)
            camera.position.y = camera.viewportHeight / 2;
        if (camera.position.y > field.getHeight() * 2 - camera.viewportHeight / 2)
            camera.position.y = field.getHeight() * 2 - camera.viewportHeight / 2;
        if (camera.position.x < camera.viewportWidth / 2)
            camera.position.x = camera.viewportWidth / 2;
        if (camera.position.x > field.getWidth() * 2 - camera.viewportWidth / 2)
            camera.position.x = field.getWidth() * 2 - camera.viewportWidth / 2;
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(field, 0, 0, field.getWidth() * 2, field.getHeight() * 2);
        for (Plant plant : plants) {
            plant.draw(batch, 1);
        }
        player.draw(batch, 1);
        batch.end();
        if (inMenu) {
            menuScreen.render(delta);
        } else if (potatoCount == maxCount) {
            if (winScreen == null) {
                winScreen = new WinScreen(game, TimeUtils.timeSinceMillis(startTime));
            }
            winScreen.render(delta);
        } else {

            player.act(delta);
            hudScreen.updateTimeLabel(TimeUtils.timeSinceMillis(startTime));
            hudScreen.updateCountLabel(potatoCount);
            hudScreen.render(delta);

            for (Iterator<Plant> iter = plants.iterator(); iter.hasNext(); ) {
                Plant plant = iter.next();
                Rectangle r = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
                Rectangle s = new Rectangle(plant.getX(), plant.getY(), plant.getWidth(), plant.getHeight());
                if (r.overlaps(s)) {
                    potatoCount += pointsPerPlant;
                    iter.remove();
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            if (winScreen != null) {
                winScreen.dispose();
                winScreen = null;
            }
            inMenu = true;
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        hudScreen.resize(width, height);
        menuScreen.resize(width, height);
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

    public void setInMenu(boolean inMenu) {
        if (!inMenu) {
            startTime = TimeUtils.millis();
            potatoCount = 0;
            spawnPlants();
        }

        GameScreen.inMenu = inMenu;
    }

    @Override
    public void dispose() {
        field.dispose();
        player.dispose();
        for (var plant : plants) {
            plant.dispose();
        }
        batch.dispose();
        hudScreen.dispose();
        menuScreen.dispose();
        if (winScreen != null) {
            winScreen.dispose();
        }
    }
}
