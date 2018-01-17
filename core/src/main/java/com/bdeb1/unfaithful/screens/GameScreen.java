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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.bdeb1.unfaithful.GameWorld;
import com.bdeb1.unfaithful.Unfaithful;
import com.bdeb1.unfaithful.systems.ActionSystem;
import com.bdeb1.unfaithful.systems.AnimationSystem;
import com.bdeb1.unfaithful.systems.MovementSystem;
import com.bdeb1.unfaithful.systems.RenderingSystem;
import com.bdeb1.unfaithful.systems.StateSystem;
import com.bdeb1.unfaithful.systems.TargetSystem;

/**
 *
 * @author Soheib El-Harrache
 */
public class GameScreen implements Screen {

    private World world;
    private GameWorld gWorld;
    private PooledEngine engine;
    private Game game;

    public GameScreen(Unfaithful game) {
        super();
        this.game = game;
        this.world = new World(new Vector2(0f, -9.8f), true);

        this.engine = new PooledEngine();
        this.engine.addSystem(new RenderingSystem(game.sb));
        this.engine.addSystem(new AnimationSystem());
        this.engine.addSystem(new StateSystem());
        this.engine.addSystem(new ActionSystem());
        this.engine.addSystem(new MovementSystem());
        this.engine.addSystem(new TargetSystem());
        
        this.gWorld = new GameWorld(engine);
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
    }

    private void draw() {
        //UI
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
