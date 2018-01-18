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
package com.bdeb1.unfaithful.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bdeb1.unfaithful.Assets;
import com.bdeb1.unfaithful.GameWorld;
import com.bdeb1.unfaithful.Unfaithful;
import com.bdeb1.unfaithful.systems.ActionSystem;
import com.bdeb1.unfaithful.systems.AnimationSystem;
import com.bdeb1.unfaithful.systems.HackerSystem;
import com.bdeb1.unfaithful.systems.LaptopSystem;
import com.bdeb1.unfaithful.systems.MovementSystem;
import com.bdeb1.unfaithful.systems.RenderingSystem;
import com.bdeb1.unfaithful.systems.StateSystem;
import com.bdeb1.unfaithful.systems.TargetSystem;
import com.bdeb1.unfaithful.util.Constants;
import com.bdeb1.unfaithful.util.Dimension;
import com.bdeb1.unfaithful.util.Scene;
import com.bdeb1.unfaithful.util.TextureLayer;

/**
 * @author Soheib El-Harrache
 */
public class GameScreen implements Screen {

	private GameWorld    gWorld;
	private PooledEngine engine;
	private Unfaithful   game;
	private boolean      isPaused;
	private Stage        stage;
	private int          level;

	private Scene        background;
	private SpriteBatch  batch;
	private TextureAtlas backgroundAtlas;
	//    Toast.ToastFactory toastFactory = new Toast.ToastFactory.Builder()
	//            .font(new BitmapFont())
	//            .build();
	//    private Toast toast;

	public GameScreen (Unfaithful game, int level) {
		super ();
		this.game = game;
		this.stage = new Stage ();
		this.level = level;
		Gdx.input.setInputProcessor (stage);

		stage.act ();

		this.batch = new SpriteBatch ();
		this.backgroundAtlas = Assets.getInstance ().manager
			  .get (Assets.ATLAS_BACKGROUND_LV1);

		Animation<TextureRegion> animation = new Animation<> (1f / 2f,
		                                                      backgroundAtlas
			                                                        .getRegions ());
		Dimension visibleDimension = new Dimension (Gdx.graphics.getWidth (),
		                                            Gdx.graphics.getHeight ());
		this.background = new Scene (visibleDimension, animation);

		
		this.engine = new PooledEngine ();
		this.engine.addSystem (new RenderingSystem (game.sb));
		this.engine.addSystem (new AnimationSystem ());
		this.engine.addSystem (new StateSystem ());
		this.engine.addSystem (new ActionSystem ());
		this.engine.addSystem (new MovementSystem ());
		this.engine.addSystem (new TargetSystem ());
		this.engine.addSystem (new HackerSystem ());
		this.engine.addSystem (new LaptopSystem ());
		this.gWorld = new GameWorld (engine, level);

		isPaused = false;

		Gdx.input.setInputProcessor (stage);
		GUI gui = GUI.getInstance ();

		Texture textureBtnPause = Assets.getInstance ().manager
			  .get (Assets.BTN_PAUSE);
		Texture textureBtnPauseHover = Assets.getInstance ().manager
			  .get (Assets.TEXTURE_NAME);

		int btnX = Gdx.graphics.getWidth () - textureBtnPause.getWidth () - 5;
		int btnY = Gdx.graphics.getHeight () - textureBtnPause.getHeight ()
		           - 5;
		Button btnPause = gui
			  .addButton (btnX, btnY, textureBtnPause, textureBtnPauseHover);
		btnPause.addListener (new ClickListener () {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				pauseAction ();
			}
		});

		Pixmap pixmapPBHB = Assets.getInstance ().manager
			  .get (Assets.SPRITE_NAME);
		Pixmap pixmapPBHF = Assets.getInstance ().manager
			  .get (Assets.SPRITE_NAME);

		//        Texture textureProgressBarHackBackground = Assets
		// .getInstance().manager.get(Assets.SPRITE_NAME);
		//        Texture textureProgressBarHackFill = Assets.getInstance()
		// .manager.get(Assets.SPRITE_NAME);
		Pixmap pbhb = new Pixmap (10, 10, pixmapPBHB.getFormat ());
		pbhb.drawPixmap (pixmapPBHB, 0, 0, pixmapPBHB.getWidth (),
		                 pixmapPBHB.getHeight (), 0, 0, pbhb.getWidth (),
		                 pbhb.getHeight ());

		Pixmap pbhf = new Pixmap (10, 10, pixmapPBHF.getFormat ());
		pbhf.drawPixmap (pixmapPBHF, 0, 0, pixmapPBHF.getWidth (),
		                 pixmapPBHF.getHeight (), 0, 0, pbhf.getWidth (),
		                 pbhf.getHeight ());

		Texture textureProgressBarHackBackground = new Texture (pbhb);
		Texture textureProgressBarHackFill       = new Texture (pbhf);

		int progressBarHackX = 5;
		int progressBarHackY = Gdx.graphics.getHeight () -
		                       textureProgressBarHackBackground.getHeight () -
		                       5;
		ProgressBar progressBarHack = gui
			  .addProgressBar (progressBarHackX, progressBarHackY,
			                   textureProgressBarHackBackground,
			                   textureProgressBarHackFill);

		//Texture textureProgressBarSuspiciousBackground = Assets.getInstance()
		// .manager.get(Assets.ATLAS_BAR_SUSPICION);
		//Texture textureProgressBarSuspiciousFill = Assets.getInstance()
		//.manager.get(Assets.);
		//int progressBarSuspicousX = Gdx.graphics.getWidth() / 2 -
		// textureProgressBarHackBackground.getWidth() / 2;
		//int progressBarSuspicousY = Gdx.graphics.getHeight() -
		// textureProgressBarSuspiciousBackground.getHeight() - 5;
		//ProgressBar progressBarSuspicious = gui.addProgressBar
		// (progressBarSuspicousX, progressBarSuspicousY,
		// textureProgressBarSuspiciousBackground,
		// textureProgressBarSuspiciousFill);
		stage.addActor (btnPause);
		//stage.addActor(progressBarHack);
		//stage.addActor(progressBarSuspicious);

	}

	private void pauseAction () {
		if (isPaused) {
			resume ();
		} else {
			pause ();
		}
	}

	@Override
	public void show () {
	}

	@Override
	public void render (float delta) {
		update (delta);
		draw (delta);
                engine.update (delta);
		//        toast.render(Gdx.graphics.getDeltaTime());
		
	}

	private void update (float delta) {
		background.update (delta);
                
		updateInput ();

		if (gWorld.isHacked ()) {
			game.setScreen (new SplashScreen (game, level + 1));
		}
	}

	private void updateInput () {
		if (Gdx.input
			  .isKeyJustPressed (Keys.ESCAPE))
		{ //TODO add buttonpause on screen
			pauseAction ();
		}
		if (Gdx.input.isKeyPressed (Keys.SPACE)) {
			engine.getSystem (HackerSystem.class).setIsHacking (true);
		} else {

			engine.getSystem (HackerSystem.class).setIsHacking (false);
		}
	}

	private void draw (float delta) {
		//UI
		Gdx.gl.glClearColor (0, 0, 0, 1);
		Gdx.gl.glClear (GL20.GL_COLOR_BUFFER_BIT);
		game.sb.setProjectionMatrix (background.camera.combined);

		game.sb.begin ();
		background.draw (game.sb);
		game.sb.end ();

		stage.draw ();
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void pause () {

		engine.getSystem (ActionSystem.class).setProcessing (false);
		engine.getSystem (AnimationSystem.class).setProcessing (false);
		engine.getSystem (HackerSystem.class).setProcessing (false);
		engine.getSystem (MovementSystem.class).setProcessing (false);
		//engine.getSystem (RenderingSystem.class).setProcessing (false);
		engine.getSystem (StateSystem.class).setProcessing (false);
		engine.getSystem (TargetSystem.class).setProcessing (false);
		isPaused = true;
	}

	@Override
	public void resume () {

		engine.getSystem (ActionSystem.class).setProcessing (true);
		engine.getSystem (AnimationSystem.class).setProcessing (true);
		engine.getSystem (HackerSystem.class).setProcessing (true);
		engine.getSystem (MovementSystem.class).setProcessing (true);
		engine.getSystem (RenderingSystem.class).setProcessing (true);
		engine.getSystem (StateSystem.class).setProcessing (true);
		engine.getSystem (TargetSystem.class).setProcessing (true);
		isPaused = false;
	}

	@Override
	public void hide () {
	}

	@Override
	public void dispose () {
		background.dispose ();
		backgroundAtlas.dispose ();
		batch.dispose ();
		engine.clearPools ();
	}
}