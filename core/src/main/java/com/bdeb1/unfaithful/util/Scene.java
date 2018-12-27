package com.bdeb1.unfaithful.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Scene {

    public final Vector3 CAMERA_ORIGIN;
    public OrthographicCamera camera;
    private Rectangle bounds;
    //private      Direction          directionCamera;
    private Array<Layer> layers;

    private Animation<TextureRegion> animation;
    private TextureRegion textureRegion;
    private float deltaTime = 0;

    public Scene(Dimension visible, Animation<TextureRegion> animation) {
        this.animation = animation;
        textureRegion = animation.getKeyFrame(deltaTime);

        camera = new OrthographicCamera(Constants.World.VIEW_DIMENSION.width,
                Constants.World.VIEW_DIMENSION.height);
        CAMERA_ORIGIN = new Vector3(visible.width / 2, visible.height / 2, 0);
        camera.translate(Constants.World.SCENE_ANCHOR.x,
                Constants.World.SCENE_ANCHOR.y);
        bounds = new Rectangle(0, 0, Constants.World.SCENE_DIMENSION.width,
                Constants.World.SCENE_DIMENSION.height);

        layers = new Array<>();

        Constants.World.CAMERA_DIRECTION = Direction.Center;
    }

    public void update(float dtime) {
        deltaTime += dtime;
        // deplacement of the camera
        float dc = 1 + Math.abs(
                Constants.World.SCENE_ANCHOR.x - camera.position.x)
                * Constants.World.CAMERA_PAN_EASE;

        // move with keys
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || touchedRight()) {
            camera.translate(dc, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || touchedLeft()) {
            camera.translate(-dc, 0);
            // move back automagically
        } else if (Constants.World.CAMERA_DIRECTION == Direction.Right) {
            camera.translate(-dc, 0);
        } else if (Constants.World.CAMERA_DIRECTION == Direction.Left) {
            camera.translate(dc, 0);
        }

        // bound checking
        if (camera.position.x < CAMERA_ORIGIN.x) {
            camera.position.x = CAMERA_ORIGIN.x;
        } else if (camera.position.x
                > CAMERA_ORIGIN.x + bounds.width - camera.viewportWidth) {
            camera.position.x = CAMERA_ORIGIN.x + bounds.width
                    - camera.viewportWidth;
        }

        // update camera direction (state)
        if (camera.position.x < Constants.World.SCENE_ANCHOR.x - 0.5f) {
            Constants.World.CAMERA_DIRECTION = Direction.Left;
        } else if (camera.position.x > Constants.World.SCENE_ANCHOR.x + 0.5f) {
            Constants.World.CAMERA_DIRECTION = Direction.Right;
        } else {
            Constants.World.CAMERA_DIRECTION = Direction.Center;
        }
        camera.update();
    }

    private boolean touchedRight() {
        Vector3 clickedPos = new Vector3(0, 0, 0);
        if (Gdx.input.isTouched()) {
            clickedPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        } else {
            return false;
        }

        if (Constants.World.TOUCHABLE_RIGHT.contains(clickedPos.x, 10)) {
            return true;
        }
        return false;
    }

    private boolean touchedLeft() {
        Vector3 clickedPos = new Vector3(0, 0, 0);
        if (Gdx.input.isTouched()) {
            clickedPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        } else {
            return false;
        }

        if (Constants.World.TOUCHABLE_LEFT.contains(clickedPos.x, 10)) {
            return true;
        }

        return false;
    }

    public void draw(Batch batch) {
        if (!Constants.World.PAUSE) {
            textureRegion = animation.getKeyFrame(deltaTime, true);
        }
        batch.draw(textureRegion, bounds.x, bounds.y, bounds.width,
                bounds.height);

        for (Layer layer : layers) {
            layer.draw(batch);
        }
    }

    public int addLayer(Layer layer) {
        layers.add(layer);
        return layers.size - 1;
    }

    public void dispose() {
        for (Layer layer : layers) {
            layer.dispose();
        }
    }
}
