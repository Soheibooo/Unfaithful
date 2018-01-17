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

    public Button addButton(int x, int y, Texture texture, Texture textureHover) {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight()));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(texture, -2, -2, texture.getWidth(), texture.getHeight()));
        textButtonStyle.over = new TextureRegionDrawable(new TextureRegion(textureHover, 0, 0, textureHover.getWidth(), textureHover.getHeight()));

        TextButton button = new TextButton("", textButtonStyle);
        button.setPosition(x, y);
        return button;
    }

    public ProgressBar addProgressBar(int x, int y, Texture textureBackground, Texture textureFill) {
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = new TextureRegionDrawable(new TextureRegion(textureBackground));
        progressBarStyle.knobBefore = new TextureRegionDrawable(new TextureRegion(textureFill));

        ProgressBar progressBar = new ProgressBar(0, 100, 1, false, progressBarStyle);
        progressBar.setBounds(x, y, textureBackground.getWidth(), textureBackground.getHeight());
        progressBar.setValue(25);
        return progressBar;
    }
}
