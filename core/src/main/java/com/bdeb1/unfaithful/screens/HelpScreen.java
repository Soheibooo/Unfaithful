package com.bdeb1.unfaithful.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bdeb1.unfaithful.Assets;
import com.bdeb1.unfaithful.Unfaithful;

public class HelpScreen implements Screen {

    private TextureAtlas atlas;
    private Animation animation;
    private float elapsedTime = 0;
    private Unfaithful game;

    public HelpScreen(Unfaithful game) {
        this.game = game;
        this.atlas = Assets.getInstance().manager.get(Assets.ATLAS_TUTO);
        this.animation = new Animation(1, atlas.getRegions());
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.sb.begin();
        elapsedTime += delta;
        game.sb.draw((TextureRegion) animation.getKeyFrame(elapsedTime, true), 0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.sb.end();

        if (elapsedTime > 0.5f) {
            update();
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

    private void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)
                || Gdx.input.justTouched()) {
            game.setScreen(new MainMenu(game));
        }
    }
}
