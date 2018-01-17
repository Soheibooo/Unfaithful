package com.bdeb1.unfaithful.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GUI {
    private static GUI instance;
    private BitmapFont font;

    public static GUI getInstance() {
        if (instance == null) {
            instance = new GUI();
        }
        return instance;
    }

    private GUI() {
        this.font = new BitmapFont();
    }

    public Button addButton(int x, int y, int width, int height, String text, Texture texture) {
        TextButton.TextButtonStyle textButtonPauseStyle = new TextButton.TextButtonStyle();
        textButtonPauseStyle.font = font;
        textButtonPauseStyle.up = new TextureRegionDrawable(new TextureRegion(texture, 0, 0, width, height));
        textButtonPauseStyle.down = new TextureRegionDrawable(new TextureRegion(texture, -2, -2, width, height));

        TextButton button = new TextButton(text, textButtonPauseStyle);
        button.setPosition(x, y);
        return button;
    }

    public ProgressBar addProgressBar(int x, int y, int width, int height, float value, Texture textureBackground, Texture textureKnob, Texture textureBefore) {
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = new TextureRegionDrawable(new TextureRegion(textureBackground));
        progressBarStyle.knob = new TextureRegionDrawable(new TextureRegion(textureKnob));
        progressBarStyle.knobBefore = new TextureRegionDrawable(new TextureRegion(textureBefore));


        ProgressBar progressBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, progressBarStyle);
        progressBar.setBounds(x, y, width, height);
        progressBar.setValue(value);
        progressBar.setAnimateDuration(0.25f);
        progressBar.setValue(0.5f);
        return progressBar;
    }
}
