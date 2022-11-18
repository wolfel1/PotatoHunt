package hunt.potato.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import hunt.potato.enumeration.Direction;

public class Plant extends Actor {
    private static final int SPRITESHEET_COLS = 3, SPRITESHEET_ROWS = 1;
    private Texture spriteSheet;
    private final static int STARTING_X = 1900;
    private final static int STARTING_Y = 2560;
    private TextureRegion plantFrame;

    public Plant(){
        createFrame();
        int x = MathUtils.random(1320, 2675-spriteSheet.getWidth() / 3);
        int y = MathUtils.random(785, 2360-spriteSheet.getHeight());
        setPosition(x, y);
        setWidth(spriteSheet.getWidth() / SPRITESHEET_COLS);
        setHeight(spriteSheet.getHeight());
        scaleBy(0.2f);
    }

    private void createFrame() {
        spriteSheet = new Texture(Gdx.files.internal("plant/spritesheet.png"));
        TextureRegion[][] frameArray = TextureRegion.split(spriteSheet,
                spriteSheet.getWidth() / SPRITESHEET_COLS,
                spriteSheet.getHeight() / SPRITESHEET_ROWS);
        plantFrame = frameArray[0][MathUtils.random(0, 2)];
    }

    @Override
    public void act(float delta) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(plantFrame,getX(),getY(),getWidth()/2,getHeight()/2,getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation());
    }

    public void dispose() {
        spriteSheet.dispose();
    }
}
