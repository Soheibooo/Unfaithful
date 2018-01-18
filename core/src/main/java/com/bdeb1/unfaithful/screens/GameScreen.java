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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
import com.bdeb1.unfaithful.util.Dimension;
import com.bdeb1.unfaithful.util.Scene;

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
	private AnimatedProgressBar hackingBar;
	private AnimatedProgressBar suspicionBar;

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

		Texture textureBtnPause = Assets.getInstance ().manager.get (Assets.BTN_PAUSE);
		Texture textureBtnPauseHover = Assets.getInstance ().manager.get (Assets.TEXTURE_NAME);

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

		addProgressBar();
		stage.addActor (btnPause);
	}

    private void addProgressBar() {
        String[] suspiciousRegionsBackground = new String[]{"suspicion_bar0000",
                "suspicion_bar0001",
                "suspicion_bar0002",
                "suspicion_bar0003",
                "suspicion_bar0004",
                "suspicion_bar0005",
                "suspicion_bar0015",
                "suspicion_bar0016",
                "suspicion_bar0017",
                "suspicion_bar0018",
                "suspicion_bar0006",
                "suspicion_bar0007",
                "suspicion_bar0008",
                "suspicion_bar0009",
                "suspicion_bar0010",
                "suspicion_bar0011",
                "suspicion_bar0012",
                "suspicion_bar0013",
                "suspicion_bar0014"};

        String[] suspiciousRegionsForeground = new String[]{"suspicion_bar_progress0006",
                "suspicion_bar_progress0007",
                "suspicion_bar_progress0008",
                "suspicion_bar_progress0015",
                "suspicion_bar_progress0016",
                "suspicion_bar_progress0017",
                "suspicion_bar_progress0018",
                "suspicion_bar_progress0009",
                "suspicion_bar_progress0000",
                "suspicion_bar_progress0001",
                "suspicion_bar_progress0002",
                "suspicion_bar_progress0010",
                "suspicion_bar_progress0011",
                "suspicion_bar_progress0012",
                "suspicion_bar_progress0003",
                "suspicion_bar_progress0004",
                "suspicion_bar_progress0005",
                "suspicion_bar_progress0013",
                "suspicion_bar_progress0014"};

        String[] hackingRegionsBackground = new String[]{"hacking_bar0000",
                "hacking_bar0001",
                "hacking_bar0002",
                "hacking_bar0003",
                "hacking_bar0004",
                "hacking_bar0005",
                "hacking_bar0006",
                "hacking_bar0007",
                "hacking_bar0008",
                "hacking_bar0009"};

        String[] hackingRegionsForeground = new String[]{"hacking_bar_progress0000",
                "hacking_bar_progress0001",
                "hacking_bar_progress0002",
                "hacking_bar_progress0003",
                "hacking_bar_progress0004",
                "hacking_bar_progress0005",
                "hacking_bar_progress0006",
                "hacking_bar_progress0007",
                "hacking_bar_progress0008",
                "hacking_bar_progress0009"};


        Pixmap pixmap = new Pixmap(0, 0, Pixmap.Format.RGBA8888);
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = drawable;
        progressBarStyle.disabledKnob = drawable;
        progressBarStyle.knobBefore = drawable;
        progressBarStyle.knobAfter = drawable;
        progressBarStyle.knob = drawable;

        AnimatedProgressBar suspiciousBar = new AnimatedProgressBar(0, 100, 1, false, progressBarStyle);
        suspiciousBar.setAnimationBackground(Assets.getInstance().manager.get(Assets.ATLAS_BAR_SUSPICIOUS_HACKING), suspiciousRegionsBackground, 0.1f);
        suspiciousBar.setAnimationForeground(Assets.getInstance().manager.get(Assets.ATLAS_BAR_SUSPICIOUS_HACKING), suspiciousRegionsForeground, 0.1f);
        suspiciousBar.setPosition(220, Gdx.graphics.getHeight() - 35);
        suspiciousBar.size(200, 30);
        suspiciousBar.freeze();
        suspiciousBar.setValue(10);
        stage.addActor(suspiciousBar);

        Pixmap pixmap2 = new Pixmap(0, 0, Pixmap.Format.RGBA8888);
        TextureRegionDrawable drawable2 = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap2)));
        pixmap2.dispose();
        ProgressBar.ProgressBarStyle progressBarStyle2 = new ProgressBar.ProgressBarStyle();
        progressBarStyle2.background = drawable2;
        progressBarStyle2.disabledKnob = drawable2;
        progressBarStyle2.knobBefore = drawable2;
        progressBarStyle2.knobAfter = drawable2;
        progressBarStyle2.knob = drawable2;

        AnimatedProgressBar hackingBar = new AnimatedProgressBar(0, 100, 2, false, progressBarStyle2);
        hackingBar.setAnimationBackground(Assets.getInstance().manager.get(Assets.ATLAS_BAR_SUSPICIOUS_HACKING), hackingRegionsBackground, 0.1f);
        hackingBar.setAnimationForeground(Assets.getInstance().manager.get(Assets.ATLAS_BAR_SUSPICIOUS_HACKING), hackingRegionsForeground, 0.1f);
        hackingBar.setPosition(10, Gdx.graphics.getHeight() - 30);
        hackingBar.size(200, 20);
        hackingBar.setValue(5);
        stage.addActor(hackingBar);
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
			engine.getSystem (LaptopSystem.class).setIsHacking (true);
		} else if (Gdx.input.isKeyPressed (Keys.LEFT)) {
			engine.getSystem (HackerSystem.class).setIsHacking (false);
			engine.getSystem (LaptopSystem.class).setIsHacking (false);

		} else if (Gdx.input.isKeyPressed (Keys.RIGHT)) {
			engine.getSystem (HackerSystem.class).setIsHacking (false);
			engine.getSystem (LaptopSystem.class).setIsHacking (false);
		} else {
			engine.getSystem (HackerSystem.class).setIsHacking (false);
			engine.getSystem (LaptopSystem.class).setIsHacking (false);
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
