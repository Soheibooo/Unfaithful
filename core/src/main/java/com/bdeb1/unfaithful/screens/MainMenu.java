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
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.bdeb1.unfaithful.Assets;
import com.bdeb1.unfaithful.Unfaithful;

/**
 *
 * @author Soheib El-Harrache
 */
public class MainMenu implements Screen {

    private Unfaithful game;
    private TextureAtlas atlas;
    private Animation animation;
    private float elapsedTime = 0;
    private boolean mainMenuActive = true;
    private Stage stage;

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
            if (atlas == null) {
                this.atlas = Assets.getInstance().manager.get(Assets.ATLAS_HOMESCREEN);
                this.animation = new Animation(1/12f, atlas.getRegions());
                this.stage = new Stage();
                Gdx.input.setInputProcessor(stage);

                addMenuButtons();
            }

            game.sb.begin();
            elapsedTime += delta;
            game.sb.draw((TextureRegion) animation.getKeyFrame(elapsedTime, true),0, 0,
                    Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            game.sb.end();
            stage.act(delta);
            stage.draw();

            if (!mainMenuActive) {
                game.setScreen(new SplashScreen(game, 2));
            }
        }
    }

    private void addMenuButtons() {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        BitmapFont font = new BitmapFont();
        font.getData().setScale(3);
        textButtonStyle.font = font;

        TextureAtlas.AtlasRegion defaultRegion = Assets.getInstance().manager.get(Assets.ATLAS_MENU).findRegion("menu_bar0000");
        Sprite defaultSprite = new Sprite(defaultRegion);
        String[] regions = new String[]{"menu_bar_select0000", "menu_bar_select0001", "menu_bar_select0002", "menu_bar_select0003"};

        AnimatedTextButton button = new AnimatedTextButton("Play", textButtonStyle, defaultSprite);
        button.setAnimation(Assets.getInstance().manager.get(Assets.ATLAS_MENU), regions, 0.25f);
        button.setPosition(Gdx.graphics.getWidth() / 2 - 250, 450);
        button.setWidth(500);
        button.setHeight(100);
        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mainMenuActive = false;
                return true;
            }
        });
        stage.addActor(button);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        defaultSprite = new Sprite(defaultRegion);

        button = new AnimatedTextButton("Help", textButtonStyle, defaultSprite);
        button.setAnimation(Assets.getInstance().manager.get(Assets.ATLAS_MENU), regions, 0.25f);
        button.setPosition(Gdx.graphics.getWidth() / 2 - 200, 300);
        button.setWidth(400);
        button.setHeight(80);
        button.addListener(new InputListener() {



            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new HelpScreen(game));
                return true;
            }
        });
        stage.addActor(button);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        defaultSprite = new Sprite(defaultRegion);

        button = new AnimatedTextButton("Quit", textButtonStyle, defaultSprite);
        button.setAnimation(Assets.getInstance().manager.get(Assets.ATLAS_MENU), regions, 0.25f);
        button.setPosition(Gdx.graphics.getWidth() / 2 - 200, 200);
        button.setWidth(400);
        button.setHeight(80);
        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
                return true;
            }
        });
        stage.addActor(button);
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
