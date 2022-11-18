package hunt.potato.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import hunt.potato.PotatoHunt;
import hunt.potato.utils.Constants;

public class MenuScreen implements Screen {
    final PotatoHunt game;
    private SpriteBatch batch;

    private OrthographicCamera camera;

    private Stage stage;
    private Skin skin;
    private Table table;

    public MenuScreen(PotatoHunt game) {
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("default", game.font);
        skin.add("white", new Texture(pixmap));
        var textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("default");
        textButtonStyle.overFontColor = Color.DARK_GRAY;
        textButtonStyle.focusedFontColor = Color.DARK_GRAY;
        skin.add("default", textButtonStyle);

        table = new Table();
        table.setFillParent(true);
        table.setSkin(skin);
        stage.addActor(table);
        pixmap.setColor(0.1f,0.1f,0.1f,0.8f);
        pixmap.fill();
        table.setBackground(new SpriteDrawable(new Sprite(new Texture(pixmap))));

        var startButton = new TextButton("Start", skin);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                GameScreen.setInMenu(false);
            }
        });
        var exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });

        table.add(startButton);
        table.row();
        table.add(exitButton);
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
        skin.dispose();
    }
}
