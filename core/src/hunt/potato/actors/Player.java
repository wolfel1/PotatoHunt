package hunt.potato.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import hunt.potato.enumeration.Direction;

public class Player extends Actor {
    private static final int FRAME_COLS = 12, FRAME_ROWS = 1;
    private Array<Animation<TextureRegion>> walkAnimations;
    private Array<TextureRegion> idleFrames;
    private Texture spriteSheet;
    private final static int STARTING_X = 1900;
    private final static int STARTING_Y = 2560;
    private TextureRegion currentFrame;
    private float stateTime;
    private Direction direction = Direction.SOUTH;
    private static final float FRAME_DURATION = 0.25f;
    private float speed = 500;
    private Texture playground;

    public Player(Texture field){
        createFrames();
        setPosition(STARTING_X, STARTING_Y);
        setWidth(spriteSheet.getWidth() / FRAME_COLS);
        setHeight(spriteSheet.getHeight());
        scaleBy(-0.2f);
        playground = field;
    }

    private void createFrames() {
        spriteSheet = new Texture(Gdx.files.internal("player/spritesheet.png"));
        TextureRegion[][] frameArray = TextureRegion.split(spriteSheet,
                spriteSheet.getWidth() / FRAME_COLS,
                spriteSheet.getHeight() / FRAME_ROWS);
        idleFrames = new Array<>(4 );
        for (int j = 0; j < 4; j++) {
            idleFrames.add(frameArray[0][j]);
        }
        currentFrame = idleFrames.get(0);

        createWalkAnimations(frameArray);
    }

    private void createWalkAnimations(TextureRegion[][] frameArray) {
        walkAnimations = new Array<>(8);
        for (int j = 4; j < FRAME_COLS; j++ ) {
            Animation<TextureRegion> animation = new Animation<>(FRAME_DURATION, frameArray[0][j], frameArray[0][++j]);
            walkAnimations.add(animation);
        }
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        switch (direction) {
            case NORTH:
                currentFrame = idleFrames.get(1);
                break;
            case SOUTH:
                currentFrame = idleFrames.get(0);
                break;
            case WEST:
                currentFrame = idleFrames.get(2);
                break;
            case EAST:
                currentFrame = idleFrames.get(3);
                break;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            setY(getY() + speed * delta);
            currentFrame = walkAnimations.get(2).getKeyFrame(stateTime,true);
            direction = Direction.NORTH;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            setY(getY() - speed * delta);
            currentFrame = walkAnimations.get(0).getKeyFrame(stateTime,true);
            direction =Direction.SOUTH;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            setX(getX() - speed * delta);
            currentFrame = walkAnimations.get(1).getKeyFrame(stateTime,true);
            direction =Direction.WEST;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            setX(getX() + speed * delta);
            currentFrame = walkAnimations.get(3).getKeyFrame(stateTime,true);
            direction =Direction.EAST;
        }

        if(getY() < 0) setY(0);
        if(getY() > playground.getHeight() * 2 - getHeight()) setY(playground.getHeight() * 2 - getHeight());
        if(getX() < 0) setX(0);
        if(getX() > playground.getWidth() * 2 - getWidth()) setX(playground.getWidth() * 2 - getWidth());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //System.out.println(getX()+","+ getY());
        batch.draw(currentFrame,getX(),getY(),getWidth()/2,getHeight()/2,getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation());
    }
}