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

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bdeb1.unfaithful.Assets;
import com.bdeb1.unfaithful.GameWorld;
import com.bdeb1.unfaithful.Toast;
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

/**
 *
 * @author Soheib El-Harrache
 */
public class GameScreen implements Screen {

    private GameWorld gWorld;
    private PooledEngine engine;
    private Game game;
    private boolean isPaused;
    private Stage stage;

    private Scene background;
    private SpriteBatch batch;
    private TextureAtlas backgroundAtlas;
//    Toast.ToastFactory toastFactory = new Toast.ToastFactory.Builder()
//            .font(new BitmapFont())
//            .build();
//    private Toast toast;

    public GameScreen(Unfaithful game) {
        super();
        this.game = game;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        this.batch = new SpriteBatch();
        this.backgroundAtlas = Assets.getInstance().manager.get(Assets.ATLAS_BACKGROUND);

        Animation<TextureRegion> animation = new Animation<TextureRegion>(1f / 2f, backgroundAtlas.getRegions());
        Dimension visibleDimension = new Dimension(Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        this.background = new Scene(visibleDimension, animation);

        this.engine = new PooledEngine();
        this.engine.addSystem(new RenderingSystem(game.sb));
        this.engine.addSystem(new AnimationSystem());
        this.engine.addSystem(new StateSystem());
        this.engine.addSystem(new ActionSystem());
        this.engine.addSystem(new MovementSystem());
        this.engine.addSystem(new TargetSystem());
        this.engine.addSystem(new HackerSystem());
        this.engine.addSystem(new LaptopSystem());
        this.gWorld = new GameWorld(engine);

        isPaused = false;

        Gdx.input.setInputProcessor(stage);
        GUI gui = GUI.getInstance();

        Texture textureBtnPause = Assets.getInstance().manager.get(Assets.BTN_PAUSE);
        Texture textureBtnPauseHover = Assets.getInstance().manager.get(Assets.TEXTURE_NAME);

        int btnX = Gdx.graphics.getWidth() - textureBtnPause.getWidth() - 5;
        int btnY = Gdx.graphics.getHeight() - textureBtnPause.getHeight() - 5;
        Button btnPause = gui.addButton(btnX, btnY, textureBtnPause, textureBtnPauseHover);
        btnPause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseAction();
            }
        });

        addMenuButtons();


        stage.addActor(btnPause);
    }

    private void addMenuButtons() {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        TextureAtlas.AtlasRegion defaultRegion = Assets.getInstance().manager.get(Assets.ATLAS_MENU).findRegion("menu_bar0000");
        Sprite defaultSprite = new Sprite(defaultRegion);
        String[] regions = new String[]{"menu_bar_select0000", "menu_bar_select0001", "menu_bar_select0002", "menu_bar_select0003"};

        AnimatedTextButton playButton = new AnimatedTextButton("Play", textButtonStyle, defaultSprite);
        playButton.setAnimation(Assets.getInstance().manager.get(Assets.ATLAS_MENU), regions, 0.25f);
        playButton.setPosition(20, 50);
        playButton.setWidth(160);
        playButton.setHeight(32);
        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button click");
                return true;
            }
        });
        stage.addActor(playButton);

        playButton = new AnimatedTextButton("Quit", textButtonStyle, defaultSprite);
        playButton.setAnimation(Assets.getInstance().manager.get(Assets.ATLAS_MENU), regions, 0.25f);
        playButton.setPosition(20, 5);
        playButton.setWidth(160);
        playButton.setHeight(32);
        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button click");
                return true;
            }
        });
        stage.addActor(playButton);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw(delta);
//        toast.render(Gdx.graphics.getDeltaTime());
        engine.update(delta);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void update(float delta) {
        background.update(delta);

        updateInput();

        if (gWorld.isHacked(gWorld.getIDLevel())) {
            if (gWorld.getIDLevel() < 3) {
                //add transitions
                gWorld.generateLevel(gWorld.getIDLevel() + 1);
            } else {
                //Insert Code for End screen
                //Later
            }
        }
    }

    private void updateInput() {
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) { //TODO add buttonpause on screen
            pauseAction();
        }
        if (Gdx.input.isKeyPressed(Keys.SPACE)) {

            engine.getSystem(HackerSystem.class).setIsHacking(true);
        } else {

            engine.getSystem(HackerSystem.class).setIsHacking(false);
        }

    }

    private void pauseAction() {
        if (isPaused) {
            resume();
        } else {
            pause();
        }
    }

    private void draw(float delta) {
        //UI
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(background.camera.combined);
//        toast = toastFactory.create("All started when i saw my girlfriend flirting with some guy at the gym. \n"
//                + "I got mad and as an apprentice hacker I decided to test my skill on her and finally \n"
//                + "get my revenge. I then decided to start with her facebook account.", Toast.Length.LONG);

        batch.begin();
        background.draw(batch);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

        engine.getSystem(ActionSystem.class).setProcessing(false);
        engine.getSystem(AnimationSystem.class).setProcessing(false);
        engine.getSystem(HackerSystem.class).setProcessing(false);
        engine.getSystem(MovementSystem.class).setProcessing(false);
        engine.getSystem(RenderingSystem.class).setProcessing(false);
        engine.getSystem(StateSystem.class).setProcessing(false);
        engine.getSystem(TargetSystem.class).setProcessing(false);
        isPaused = true;

    }

    @Override
    public void resume() {

        engine.getSystem(ActionSystem.class).setProcessing(true);
        engine.getSystem(AnimationSystem.class).setProcessing(true);
        engine.getSystem(HackerSystem.class).setProcessing(true);
        engine.getSystem(MovementSystem.class).setProcessing(true);
        engine.getSystem(RenderingSystem.class).setProcessing(true);
        engine.getSystem(StateSystem.class).setProcessing(true);
        engine.getSystem(TargetSystem.class).setProcessing(true);
        isPaused = false;
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        background.dispose();
        backgroundAtlas.dispose();
        batch.dispose();
    }
}
