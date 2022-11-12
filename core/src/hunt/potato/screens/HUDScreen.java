package hunt.potato.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import hunt.potato.PotatoHunt;
import hunt.potato.utils.Constants;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.Date;

public class HUDScreen implements Screen {
    private Table table;
    private Stage stage;
    private Skin skin;
    private OrthographicCamera hudCamera;
    private SpriteBatch batch;
    private Texture potatoTexture;
    private Label timeLabel;
    private Label countLabel;
    private Image potatoImage;

    public HUDScreen(PotatoHunt game) {
        potatoTexture = new Texture(Gdx.files.internal("potato.png"));
        batch = new SpriteBatch();
        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        stage = new Stage();

        skin = new Skin();

        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        // Store the default libGDX font under the name "default".
        skin.add("default", game.font);
        skin.add("white", new Texture(pixmap));// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        skin.add("default", labelStyle);

        table = new Table();
        table.setFillParent(true);
        table.setSkin(skin);
        stage.addActor(table);
        table.left().top();

        timeLabel = new Label("Time", skin);
        timeLabel.setAlignment(Align.left);
        countLabel = new Label("Count", skin);
        countLabel.setAlignment(Align.right);
        potatoImage = new Image(potatoTexture);

        table.add(timeLabel).width(330).padLeft(15).padTop(10).top().left();
        table.add(countLabel).width(20).padTop(10).padLeft(20).top().right();
        table.add(potatoImage).padTop(5).padLeft(10);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        hudCamera.update();

        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        stage.draw();
        /*game.font.draw(game.batch, "" + TimeUtils.timeSinceMillis(startTime), potatoCount < 10 ? 170 : 135, hudCamera.viewportHeight - 35);
        game.font.draw(game.batch, "" + potatoCount, potatoCount < 10 ? 170 : 135, hudCamera.viewportHeight - 35);
        game.batch.draw(potato, 200, hudCamera.viewportHeight - 110);*/
        batch.end();

    }

    public void updateTimeLabel(long time) {
        int hours = (int) (time / (60 * 60 * 1000));
        int minutes = (int) (time / (60 * 1000)) % 60;
        int seconds = (int) (time / 1000) % 60;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        timeLabel.setText(formatter.format(LocalTime.of(hours, minutes, seconds)));
    }

    public void updateCountLabel(int count) {
        countLabel.setText(count);
    }

    @Override
    public void resize(int width, int height) {
        hudCamera.setToOrtho(false, width, height);
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
        batch.dispose();
        stage.dispose();
        potatoTexture.dispose();
    }
}
