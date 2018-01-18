/*
 * Copyright 2018 Soheib El-Harrache.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bdeb1.unfaithful;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bdeb1.unfaithful.components.ActionComponent;
import com.bdeb1.unfaithful.components.AnimationComponent;
import com.bdeb1.unfaithful.components.ComptoirComponent;
import com.bdeb1.unfaithful.components.HackerComponent;
import com.bdeb1.unfaithful.components.LaptopComponent;
import com.bdeb1.unfaithful.components.StateComponent;
import com.bdeb1.unfaithful.components.TargetComponent;
import com.bdeb1.unfaithful.components.TextureComponent;
import com.bdeb1.unfaithful.components.TransformComponent;
import com.bdeb1.unfaithful.util.Constants;
import java.util.HashMap;

/**
 * @author Soheib El-Harrache
 */
public class GameWorld {

	private PooledEngine engine;
	private Entity       hacker, laptop, comptoir, target;
	private int level;

	public GameWorld (PooledEngine engine, int level) {
		this.engine = engine;
		this.level = level;
		generateLevel ();
	}

	public void generateLevel () {
		engine.clearPools ();
		engine.removeAllEntities ();

		target = createTarget ();
		comptoir = createDumbYouRE ();
		laptop = createLaptopScreen ();
		hacker = createHacker ();
	}

	private Entity createLaptopScreen () {
		Entity entity = engine.createEntity ();

		TransformComponent positionC = engine
			  .createComponent (TransformComponent.class);
		AnimationComponent animC = engine
			  .createComponent (AnimationComponent.class);
		TextureComponent textureC = engine
			  .createComponent (TextureComponent.class);
		ActionComponent actionC = engine
			  .createComponent (ActionComponent.class);

		StateComponent stateC = engine.createComponent (StateComponent.class);
		LaptopComponent laptopC = engine
			  .createComponent (LaptopComponent.class);

		positionC.position.set (Constants.World.HACKER_INITIAL_POSITION.x +
		                        Constants.World.D_HACKER_SCREEN.x *
		                        Constants.World.SCALE,
		                        Constants.World.HACKER_INITIAL_POSITION.y +
		                        Constants.World.D_HACKER_SCREEN.y *
		                        Constants.World.SCALE, 0);

		stateC.set (0);
		actionC.set (LaptopComponent.ACTION_NOT_HACKING);

		TextureAtlas atTextureLaptopHack = Assets.getInstance ().manager
			  .get (Assets.ATLAS_HACKING_LAPSCREEN);
		TextureAtlas atTextureLaptop = Assets.getInstance ().manager
			  .get (Assets.ATLAS_NOTHACKING_LAPSCREEN);

		Animation<TextureRegion> laptopHack = new Animation<TextureRegion> (
			  1 / 12f, atTextureLaptopHack.getRegions (), PlayMode.LOOP);
		Animation<TextureRegion> laptopNotHack = new Animation<TextureRegion> (
			  1 / 12f, atTextureLaptop.getRegions (), PlayMode.LOOP);

		HashMap<Integer, Animation> anime = new HashMap<Integer, Animation> ();
		anime.put (1, laptopHack);
		anime.put (2, laptopNotHack);

		animC.animations.put (0, anime);

		entity.add (textureC);
		entity.add (animC);
		entity.add (positionC);
		entity.add (actionC);
		entity.add (laptopC);
		entity.add (stateC);

		engine.addEntity (entity);
		return entity;
	}

	private Entity createDumbYouRE () {
		Entity entity = engine.createEntity ();

		TransformComponent positionC = engine
			  .createComponent (TransformComponent.class);
		TextureComponent textureC = engine
			  .createComponent (TextureComponent.class);
		ComptoirComponent compC = engine
			  .createComponent (ComptoirComponent.class);

		textureC.region = new TextureRegion (
			  Assets.getInstance ().manager.get (Assets.COMPTOIR));


		positionC.position.set (0, 0, 0);

		entity.add (positionC);
		entity.add (textureC);
		entity.add (compC);

		engine.addEntity (entity);
		return entity;
	}

	private Entity createTarget () {
		Entity entity = engine.createEntity ();

		AnimationComponent animC = engine
			  .createComponent (AnimationComponent.class);
		TransformComponent positionC = engine
			  .createComponent (TransformComponent.class);
		StateComponent stateC = engine.createComponent (StateComponent.class);
		TextureComponent textureC = engine
			  .createComponent (TextureComponent.class);
		TargetComponent targetC = engine
			  .createComponent (TargetComponent.class);
		ActionComponent actionC = engine
			  .createComponent (ActionComponent.class);
		positionC.position.set (5.0f, 1.0f, 0.0f);
		stateC.set (TargetComponent.STATE_UNSUSPICIOUS);
		targetC.difficultyAddition = - level * 5;

		TextureAtlas atTextureMarche = Assets.getInstance ().manager
			  .get (Assets.WOMAN_WALKING);
		TextureAtlas atTextureMarcheSus = Assets.getInstance ().manager
			  .get (Assets.WOMAN_WALKING_SUSPICIOUSLY);
		TextureAtlas atTextureRotate = Assets.getInstance ().manager
			  .get (Assets.WOMAN_ROTATING);
		TextureAtlas atTextureRotateSus = Assets.getInstance ().manager
			  .get (Assets.WOMAN_ROTATING_SUSPICIOUSLY);

		Animation<TextureRegion> womanWalk = new Animation<TextureRegion> (
			  1 / 12f, atTextureMarche.getRegions (), PlayMode.LOOP);
		Animation<TextureRegion> womanWalkSus = new Animation<TextureRegion> (
			  1 / 12f, atTextureMarcheSus.getRegions (), PlayMode.LOOP);
		Animation<TextureRegion> womanRotate = new Animation<TextureRegion> (
			  1 / 12f, atTextureRotate.getRegions (), PlayMode.LOOP);
		Animation<TextureRegion> womanRotateSus = new
			  Animation<TextureRegion> (
			  1 / 12f, atTextureRotateSus.getRegions (), PlayMode.LOOP);

		HashMap<Integer, Animation> animeActionWalk   = new HashMap<> ();
		HashMap<Integer, Animation> animeActionRotate = new HashMap<> ();


		animeActionWalk.put (TargetComponent.ACTION_WALK_LEFT, womanWalk);
		animeActionWalk.put (TargetComponent.ACTION_WALK_RIGHT, womanWalk);
		animeActionWalk.put (1, womanWalkSus);
		animeActionWalk.put (1, womanWalkSus);

		animeActionRotate.put (0, womanRotate);
		animeActionRotate.put (1, womanRotateSus);

		animC.animations.put (1, animeActionWalk);

		stateC.set (TargetComponent.STATE_UNSUSPICIOUS);
		actionC.set (TargetComponent.ACTION_WALK_RIGHT);

		entity.add (textureC);
		entity.add (animC);
		entity.add (targetC);
		entity.add (positionC);
		entity.add (stateC);
		entity.add (actionC);

		engine.addEntity (entity);

		return entity;
	}

	private Entity createHacker () {
		Entity entity = engine.createEntity ();

		AnimationComponent animC = engine
			  .createComponent (AnimationComponent.class);
		TransformComponent positionC = engine
			  .createComponent (TransformComponent.class);
		StateComponent stateC = engine.createComponent (StateComponent.class);
		TextureComponent textureC = engine
			  .createComponent (TextureComponent.class);
		HackerComponent hackerC = engine
			  .createComponent (HackerComponent.class);
		ActionComponent actionC = engine
			  .createComponent (ActionComponent.class);

		positionC.position.set (Constants.World.HACKER_INITIAL_POSITION);

		//PAS DE STATE POUR LE HACKER
		stateC.set (0);
		actionC.set (HackerComponent.ACTION_NOT_HACKING);

		TextureAtlas texAtHacking = Assets.getInstance ().manager
			  .get (Assets.ATLAS_HACKING);
		TextureAtlas texAtNOTHacking = Assets.getInstance ().manager
			  .get (Assets.ATLAS_NOTHACKING);

		HashMap<Integer, Animation> animeList
			  = new HashMap<Integer, Animation> ();


		Animation<TextureRegion> animeHacking = new Animation<TextureRegion> (
			  1 / 12f, texAtHacking.getRegions (), PlayMode.LOOP);
		Animation<TextureRegion> animeNotHacking
			  = new Animation<TextureRegion> (1 / 12f,
			                                  texAtNOTHacking.getRegions (),
			                                  PlayMode.LOOP);


		animeList.put (1, animeHacking);
		animeList.put (2, animeNotHacking);

		//DEFAULT
		animC.animations.put (0, animeList);

		entity.add (textureC);
		entity.add (animC);

		entity.add (hackerC);
		entity.add (positionC);
		entity.add (stateC);
		entity.add (actionC);

		engine.addEntity (entity);

		return entity;
	}

	public boolean isHacked () {
		HackerComponent hackerC = hacker.getComponent (HackerComponent.class);
		return hackerC.hacking_gauge >=
		       (50 + level * level * 10); //1=60; 2=90; 3=140;
	}
}
