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
import hunt.potato.PotatoHunt;
import hunt.potato.utils.Constants;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HUDScreen implements Screen {
    private Table table;
    private Stage stage;
    private Skin skin;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture potatoTexture;
    private Label timeLabel;
    private Label countLabel;
    private Image potatoImage;

    public HUDScreen(PotatoHunt game) {
        potatoTexture = new Texture(Gdx.files.internal("potato.png"));
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
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
        timeLabel.setFontScale(2);
        countLabel = new Label("Count", skin);
        countLabel.setAlignment(Align.right);
        countLabel.setFontScale(2);
        potatoImage = new Image(potatoTexture);

        table.add(timeLabel).width(330).padLeft(15).padTop(12).top().left();
        table.add(countLabel).width(25).padTop(12).padLeft(25).top().right();
        table.add(potatoImage).padTop(5).padLeft(10);
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
        batch.dispose();
        stage.dispose();
        potatoTexture.dispose();
        skin.dispose();
    }
}
