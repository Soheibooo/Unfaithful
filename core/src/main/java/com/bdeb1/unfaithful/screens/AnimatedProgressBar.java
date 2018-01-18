package com.bdeb1.unfaithful.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class AnimatedProgressBar extends ProgressBar {
    private TextureAtlas atlasBackground;
    private String[] regionsBackground;
    private float animationBackgroundTimer;
    private float animationBackgroundSpeed;
    private int animationBackgroundIndex;

    private TextureAtlas atlasForeground;
    private String[] regionsForeground;
    private float animationForegroundTimer;
    private float animationForegroundSpeed;
    private int animationForegroundIndex;

    public AnimatedProgressBar(float min, float max, float stepSize, boolean vertical, ProgressBarStyle progressBarStyle) {
         super(min, max, stepSize, vertical, progressBarStyle);

        addAction(new Action() {
            @Override
            public boolean act(float delta) {
                AnimatedProgressBar.this.update(delta);
                return false;
            }
        });
    }

    private void update(float delta) {
        this.animationBackgroundTimer += delta;
        if (animationBackgroundTimer > animationBackgroundSpeed) {
            animationBackgroundTimer -= animationBackgroundSpeed;
            animationBackgroundIndex++;
            if (animationBackgroundIndex >= regionsBackground.length) {
                animationBackgroundIndex = 0;
            }
            TextureAtlas.AtlasRegion textureBackground = this.atlasBackground.findRegion(regionsBackground[animationBackgroundIndex]);
            Sprite spriteBackground = new Sprite(textureBackground);
            spriteBackground.setSize(getWidth(), getHeight());
            getStyle().background = new SpriteDrawable(spriteBackground);
        }

        this.animationForegroundTimer += delta;
        if (animationForegroundTimer > animationForegroundSpeed) {
            animationForegroundTimer -= animationForegroundSpeed;
            animationForegroundIndex++;
            if (animationForegroundIndex >= regionsForeground.length) {
                animationForegroundIndex = 0;
            }
            TextureAtlas.AtlasRegion textureForeground = this.atlasForeground.findRegion(regionsForeground[animationForegroundIndex]);
            Sprite spriteForeground = new Sprite(textureForeground);
            spriteForeground.setSize(getWidth(), getHeight() / 2);
            getStyle().knobBefore = new SpriteDrawable(spriteForeground);
        }

    }

    public void setAnimationBackground(TextureAtlas atlasBackground, String[] regionsBackground, float speedBackground) {
        this.atlasBackground = atlasBackground;
        this.regionsBackground = regionsBackground;
        this.animationBackgroundSpeed = speedBackground;
    }

    public void setAnimationForeground(TextureAtlas atlasForeground, String[] regionsForeground, float speedForeground) {
        this.atlasForeground = atlasForeground;
        this.regionsForeground = regionsForeground;
        this.animationForegroundSpeed = speedForeground;
    }

    public void size(int x, int y) {
        this.setSize(x, y);
    }
}
