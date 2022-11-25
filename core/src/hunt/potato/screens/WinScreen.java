package hunt.potato.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import hunt.potato.PotatoHunt;
import hunt.potato.utils.Constants;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class WinScreen implements Screen {
    final PotatoHunt game;
    private SpriteBatch batch;

    private OrthographicCamera camera;

    private Stage stage;
    private Skin skin;
    private Table table;
    private Label timeLabel;


    public WinScreen(PotatoHunt game, long time) {
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        stage = new Stage();

        skin = new Skin();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("default", game.font);
        skin.add("white", new Texture(pixmap));
        var labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        skin.add("default", labelStyle);

        table = new Table();
        table.setFillParent(true);
        table.setSkin(skin);
        stage.addActor(table);
        pixmap.setColor(0.1f,0.1f,0.1f,0.8f);
        pixmap.fill();
        table.setBackground(new SpriteDrawable(new Sprite(new Texture(pixmap))));

        var winLabel = new Label("You win!", skin);
        timeLabel = new Label("", skin);
        setTimeLabel(time);

        table.add(winLabel);
        table.row();
        table.add(timeLabel);
    }

    public void setTimeLabel(long time) {
        int hours = (int) (time / (60 * 60 * 1000));
        int minutes = (int) (time / (60 * 1000)) % 60;
        int seconds = (int) (time / 1000) % 60;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        timeLabel.setText("Your time: " +formatter.format(LocalTime.of(hours, minutes, seconds)));
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        stage.getViewport().update(width, height);
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
        stage.dispose();
        batch.dispose();
    }
}
