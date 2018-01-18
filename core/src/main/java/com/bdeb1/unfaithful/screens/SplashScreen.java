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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.bdeb1.unfaithful.Assets;
import com.bdeb1.unfaithful.Unfaithful;
import java.util.ArrayList;

/**
 *
 * @author Soheib El-Harrache
 */
public class SplashScreen implements Screen {

    private Unfaithful game;
    private int transitionLevel, step = 0;
    private ArrayList<Texture> splashes;

    public SplashScreen(Unfaithful game, int transitionLevel) {
        this.game = game;
        this.transitionLevel = transitionLevel;

        splashes = new ArrayList<>();
        switch (transitionLevel) {
            case 1:
                //Ajoute les splash du premier niveau
                splashes.add(Assets.getInstance().manager.get(Assets.SPLASH_LV1_1));
                splashes.add(Assets.getInstance().manager.get(Assets.SPLASH_LV1_2));
                break;
            case 2:
                break;
            default:
                break;
        }

        //If 0 splashes, skipping to next level
        if (splashes.isEmpty()) {
            game.setScreen(new GameScreen(game, transitionLevel));
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update(delta);
        
        //Precaution taken
        if (splashes.size() != step) {
            game.sb.begin();
            game.sb.draw(splashes.get(step), 0, 0,
                    Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            //TODO Render le bouton aussi
            game.sb.end();
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

    private void update(float delta) {
        updateInput();
    }

    private void updateInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)
                || Gdx.input.isTouched()) {
            nextStep();
        }
    }

    private void nextStep() {
        step++;
        if (splashes.size() == step) {
            game.setScreen(new GameScreen(game, transitionLevel));
        }
    }

}
