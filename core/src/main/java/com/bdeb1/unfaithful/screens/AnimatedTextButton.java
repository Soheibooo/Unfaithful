package com.bdeb1.unfaithful.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class AnimatedTextButton extends TextButton {

    private TextureAtlas atlas;
    private String[] regions;
    private Sprite defaultSprite;
    private float animationTimer;
    private float animationSpeed;
    private int animationIndex;
    private boolean activated = false;

    public AnimatedTextButton(String text, TextButtonStyle style,
            Sprite defaultSprite) {
        super(text, style);
        this.defaultSprite = defaultSprite;
        getStyle().up = new SpriteDrawable(defaultSprite);

        addAction(new Action() {
            @Override
            public boolean act(float delta) {
                AnimatedTextButton.this.update(delta);
                return false;
            }
        });

        addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y,
                    int pointer, Actor fromActor) {
                activated = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y,
                    int pointer, Actor toActor) {
                activated = false;
                getStyle().up = new SpriteDrawable(defaultSprite);
            }
        });
    }

    private void update(float delta) {
        if (!activated) {
            return;
        }
        this.animationTimer += delta;
        if (animationTimer > animationSpeed) {
            animationTimer -= animationSpeed;
            animationIndex++;
            if (animationIndex >= regions.length) {
                animationIndex = 0;
            }
            TextureAtlas.AtlasRegion region = this.atlas.findRegion(regions[animationIndex]);
            Sprite sprite = new Sprite(region);
            getStyle().up = new SpriteDrawable(sprite);
        }

    }

    public void setAnimation(TextureAtlas atlas, String[] regions,
            float speed) {
        this.atlas = atlas;
        this.regions = regions;
        this.animationSpeed = speed;
    }
}
