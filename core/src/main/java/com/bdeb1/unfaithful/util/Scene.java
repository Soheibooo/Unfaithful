package com.bdeb1.unfaithful.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.bdeb1.unfaithful.Assets;

public class Scene {

	public  OrthographicCamera camera;
	private Rectangle          bounds;
	private Direction          directionCamera;
	private Texture            background;
	private Vector3            cameraOrigin;
	private Vector3            anchorPoint;

	public Scene (Dimension visible, Dimension dimension)
		{
		background = Assets.getInstance().manager.get(Assets.IMAGE_BACKGROUND);
		directionCamera = Direction.Center;

		camera = new OrthographicCamera (visible.width, visible.height);
		cameraOrigin = new Vector3 (visible.width / 2, visible.height / 2, 0);
		bounds = new Rectangle (0, 0, dimension.width, dimension.height);

		anchorPoint = new Vector3 (
			  cameraOrigin.x + bounds.width * Constants.World.SCENE_PARTITION,
			  cameraOrigin.y + 0,
			  0);

		camera.translate (anchorPoint.x, anchorPoint.y);
		directionCamera = Direction.Center;
	}


	public void update () {

		
		float dx = Math.min (1 + Math.abs (anchorPoint.x - camera.position.x) *
		                         Constants.World.CAMERA_PAN_EASE,
		                     camera.position.x);
		if (Gdx.input.isKeyPressed (Input.Keys.RIGHT)) {
			camera.translate (dx, 0);
		} else if (Gdx.input.isKeyPressed (Input.Keys.LEFT)) {
			camera.translate (- dx, 0);
		} else if (directionCamera == Direction.Right) {
			camera.translate (- dx, 0);
		} else if (directionCamera == Direction.Left) {
			camera.translate (dx, 0);
		}

		// bound checking
		if (camera.position.x < cameraOrigin.x) {
			camera.position.x = cameraOrigin.x;
		} else if (camera.position.x >
		           cameraOrigin.x + bounds.width - camera.viewportWidth)
		{
			camera.position.x = cameraOrigin.x + bounds.width -
			                    camera.viewportWidth;
		}

		if (camera.position.x < anchorPoint.x - 0.5f) {
			directionCamera = Direction.Left;
		} else if (camera.position.x > anchorPoint.x + 0.5f) {
			directionCamera = Direction.Right;
		} else {
			directionCamera = Direction.Center;
		}
		camera.update ();
	}

	public void draw (Batch batch) {
		batch.draw (background, bounds.x, bounds.y, bounds.width,
		            bounds.height);
	}

	public void dispose () {
		background.dispose ();
	}
}
