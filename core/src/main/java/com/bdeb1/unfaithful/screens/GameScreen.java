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
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.bdeb1.unfaithful.Assets;
import com.bdeb1.unfaithful.GameWorld;
import com.bdeb1.unfaithful.Unfaithful;
import com.bdeb1.unfaithful.systems.*;
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
    private Stage stage;

    private Scene background;
    private SpriteBatch batch;

    public GameScreen(Unfaithful game) {
        super();
        this.game = game;
        this.stage = new Stage();
        this.batch = new SpriteBatch();
        Dimension visibleDimension = new Dimension (Gdx.graphics.getWidth (), Gdx.graphics.getHeight ());
        this.background = new Scene (visibleDimension, Constants.World.SCENE_DIMENSION, Constants.Path.BACKGROUND);

        this.engine = new PooledEngine();
        this.engine.addSystem(new RenderingSystem(game.sb));
        this.engine.addSystem(new AnimationSystem());
        this.engine.addSystem(new StateSystem());
        this.engine.addSystem(new ActionSystem());
        this.engine.addSystem(new MovementSystem());
        this.engine.addSystem(new TargetSystem());
        this.engine.addSystem(new HackerSystem());
        this.gWorld = new GameWorld(engine);

        Gdx.input.setInputProcessor(stage);
        GUI gui = GUI.getInstance();
        Button btnPause = gui.addButton(10, 10, 64, 32, "Pause", Assets.getInstance().manager.get(Assets.SPRITE_NAME));
        ProgressBar progressBarHack = gui.addProgressBar(100, 40, 50, 16, 2, Assets.getInstance().manager.get(Assets.SPRITE_NAME), Assets.getInstance().manager.get(Assets.SPRITE_NAME), Assets.getInstance().manager.get(Assets.SPRITE_NAME));

        stage.addActor(btnPause);
        stage.addActor(progressBarHack);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    private void update(float delta) {
        engine.update(delta);

        if(gWorld.isHacked(gWorld.getIDLevel())) {
            if(gWorld.getIDLevel() < 3) {
                gWorld.generateLevel(gWorld.getIDLevel()+1);
            }
            else {
                //Insert Code for End screen
                //Later
            }
        }

        background.update ();
    }

    private void draw() {
        //UI
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix (background.camera.combined);

        batch.begin ();
        background.draw(batch);
        batch.end ();

        stage.draw();
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
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        background.dispose ();
    }
}
