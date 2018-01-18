package com.bdeb1.unfaithful.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public final class Constants {

	public static final class World {

		public static final Dimension BG_REAL_SIZE            = new Dimension (
			  640, 180);
		public static final Dimension VIEW_DIMENSION          = new Dimension (
			  Gdx.graphics.getWidth (), Gdx.graphics.getHeight ());
		public static final Dimension SCENE_DIMENSION         = new Dimension (
			  VIEW_DIMENSION.width * 2, VIEW_DIMENSION.height);
		public static final int       SCALE                   =
			  SCENE_DIMENSION.height / BG_REAL_SIZE.height;
		public static final Vector3   SCENE_ORIGIN            = new Vector3 (
			  VIEW_DIMENSION.width / 2, VIEW_DIMENSION.height / 2, 0);
		public static final Vector3   SCENE_ANCHOR            = new Vector3 (
			  SCENE_ORIGIN.x + SCENE_DIMENSION.width / 4, SCENE_ORIGIN.y, 0);
		public static final float     CAMERA_PAN_EASE         = 0.05f;
		public static final Vector3   HACKER_INITIAL_POSITION = new Vector3 (
			  SCENE_ANCHOR.x - SCENE_ORIGIN.x, 0f, 0f);
		public static final Vector3   D_HACKER_SCREEN         = new Vector3 (
			  82, 55, 0);
                public static final Vector3   D_HACKER_SCREEN_2         = new Vector3 (
			  120, 50, 0);
                public static final Vector3   D_HACKER_SCREEN_3         = new Vector3 (
			  95, 45, 0);
		public static final Rectangle TOUCHABLE_LEFT          = new Rectangle (
			  0, 0, VIEW_DIMENSION.width / 5, VIEW_DIMENSION.height);
		public static final Rectangle TOUCHABLE_RIGHT         = new Rectangle (
			  TOUCHABLE_LEFT.width * 4, 0, TOUCHABLE_LEFT.width,
			  TOUCHABLE_LEFT.height);
		public static Direction CAMERA_DIRECTION = Direction.Center;

		public static boolean PAUSE = false;
	}
}
