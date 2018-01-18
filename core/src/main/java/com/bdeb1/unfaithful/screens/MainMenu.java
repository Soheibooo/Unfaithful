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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.bdeb1.unfaithful.Assets;
import com.bdeb1.unfaithful.Unfaithful;

/**
 *
 * @author Soheib El-Harrache
 */
public class MainMenu implements Screen {

    private Unfaithful game;
    
    public MainMenu(Unfaithful game) {
        this.game = game;
    }
    
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Assets.getInstance().manager.update()) {
            //Level 1
            //game.setScreen(new GameScreen(game, 1));
            game.setScreen(new SplashScreen(game, 1));
        } else {
            //Show % loading
            //(int) (Assets.getInstance().manager.getProgress() * 100);
        }
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
