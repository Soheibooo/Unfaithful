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
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.bdeb1.unfaithful.Assets;
import com.bdeb1.unfaithful.GameWorld;
import com.bdeb1.unfaithful.Unfaithful;
import com.bdeb1.unfaithful.systems.ActionSystem;
import com.bdeb1.unfaithful.systems.AnimationSystem;
import com.bdeb1.unfaithful.systems.HackerSystem;
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

    private Scene        background;
    private SpriteBatch  batch;
    private TextureAtlas backgroundAtlas;

    public GameScreen(Unfaithful game) {
        super();
        this.game = game;
        this.stage = new Stage();
<<<<<<< HEAD
        Gdx.input.setInputProcessor(stage);


        stage.act();

        batch = new SpriteBatch ();

        backgroundAtlas = Assets.getInstance().manager.get(Assets.ATLAS_BACKGROUND_1);
        
=======
        this.batch = new SpriteBatch ();
        this.backgroundAtlas = Assets.getInstance().manager.get(Assets.ATLAS_BACKGROUND);
>>>>>>> de89d1cbee3a17a81e4c0ab69b204b7ec9925b11
        Animation<TextureRegion> animation = new Animation<TextureRegion>
              (1f/2f, backgroundAtlas.getRegions ());
        Dimension visibleDimension = new Dimension (Gdx.graphics.getWidth (),
                                                    Gdx.graphics.getHeight ());
        this.background = new Scene (visibleDimension, animation);

        this.engine = new PooledEngine();
        this.engine.addSystem(new RenderingSystem(game.sb));
        this.engine.addSystem(new AnimationSystem());
        this.engine.addSystem(new StateSystem());
        this.engine.addSystem(new ActionSystem());
        this.engine.addSystem(new MovementSystem());
        this.engine.addSystem(new TargetSystem());
        this.engine.addSystem(new HackerSystem());
        this.gWorld = new GameWorld(engine);

        isPaused = false;

        Gdx.input.setInputProcessor(stage);
        GUI gui = GUI.getInstance();

        Texture textureBtnPause = Assets.getInstance().manager.get(Assets.BTN_PAUSE);
        Texture textureBtnPauseHover = Assets.getInstance().manager.get(Assets.TEXTURE_NAME);

        int btnX = Gdx.graphics.getWidth() - textureBtnPause.getWidth() - 5;
        int btnY = Gdx.graphics.getHeight() - textureBtnPause.getHeight() - 5;
        Button btnPause = gui.addButton(btnX, btnY, textureBtnPause, textureBtnPauseHover);

        Pixmap pixmapPBHB = Assets.getInstance ().manager.get(Assets
                                                                   .SPRITE_NAME);
        Pixmap pixmapPBHF = Assets.getInstance ().manager.get(Assets
                                                                    .SPRITE_NAME);

//        Texture textureProgressBarHackBackground = Assets.getInstance().manager.get(Assets.SPRITE_NAME);
//        Texture textureProgressBarHackFill = Assets.getInstance().manager.get(Assets.SPRITE_NAME);
        Pixmap pbhb = new Pixmap (10, 10, pixmapPBHB.getFormat ());
        pbhb.drawPixmap (pixmapPBHB,0, 0 ,pixmapPBHB.getWidth (), pixmapPBHB
              .getHeight (), 0, 0,  pbhb.getWidth (), pbhb.getHeight ());

        Pixmap pbhf = new Pixmap (10, 10, pixmapPBHF.getFormat ());
        pbhf.drawPixmap (pixmapPBHF, 0, 0, pixmapPBHF.getWidth (),
                         pixmapPBHF.getHeight (), 0, 0, pbhf.getWidth (),
                         pbhf.getHeight ());

        Texture textureProgressBarHackBackground = new Texture (pbhb);
        Texture textureProgressBarHackFill = new Texture (pbhf);

        int progressBarHackX = 5;
        int progressBarHackY = Gdx.graphics.getHeight() - textureProgressBarHackBackground.getHeight() - 5;
        ProgressBar progressBarHack = gui.addProgressBar(progressBarHackX, progressBarHackY, textureProgressBarHackBackground, textureProgressBarHackFill);

        Texture textureProgressBarSuspiciousBackground = Assets.getInstance()
              .manager.get(Assets.TEXTURE_NAME);
        Texture textureProgressBarSuspiciousFill = Assets.getInstance()
              .manager.get(Assets.TEXTURE_NAME);
        int progressBarSuspicousX = Gdx.graphics.getWidth() / 2 - textureProgressBarHackBackground.getWidth() / 2;
        int progressBarSuspicousY = Gdx.graphics.getHeight() - textureProgressBarSuspiciousBackground.getHeight() - 5;
        ProgressBar progressBarSuspicious = gui.addProgressBar(progressBarSuspicousX, progressBarSuspicousY, textureProgressBarSuspiciousBackground, textureProgressBarSuspiciousFill);

        stage.addActor(btnPause);
        stage.addActor(progressBarHack);
        stage.addActor(progressBarSuspicious);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw(delta);
        engine.update(delta);
    }

    private void update(float delta) {
        background.update (delta);
        


        updateInput();
		
		
        if(gWorld.isHacked(gWorld.getIDLevel())) {
            if(gWorld.getIDLevel() < 3) {
                //add transitions
                gWorld.generateLevel(gWorld.getIDLevel()+1);
            }
            else {
                //Insert Code for End screen
                //Later
            }
        }
    }
    private void updateInput() {
        if(Gdx.input.isKeyPressed(Keys.ESCAPE)) { //TODO add buttonpause on screen
            if(isPaused)
                resume();
            else
                pause();
        }
        if(Gdx.input.isKeyPressed(Keys.SPACE)) {
            System.out.println("Space");
            engine.getSystem(HackerSystem.class).setIsHacking(true);
        }
        else {
            System.out.println("No space");
            engine.getSystem(HackerSystem.class).setIsHacking(false);
        }
        
    }
    private void draw(float delta) {
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
        background.dispose ();
        backgroundAtlas.dispose ();
        batch.dispose ();
    }
}
