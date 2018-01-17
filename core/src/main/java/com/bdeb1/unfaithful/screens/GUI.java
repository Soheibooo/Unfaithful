package com.bdeb1.unfaithful.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bdeb1.unfaithful.Assets;

import static com.bdeb1.unfaithful.Assets.SPRITE_NAME;

public class GUI {

    private Stage stage;
    private BitmapFont font;

    private TextButton.TextButtonStyle textButtonPauseStyle;

    public GUI (Stage stage) {
        this.stage = stage;
        this.font = new BitmapFont();

        textButtonPauseStyle = new TextButton.TextButtonStyle();
        textButtonPauseStyle.font = font;
        textButtonPauseStyle.up = new TextureRegionDrawable(new TextureRegion(Assets.getInstance().manager.get(SPRITE_NAME), 0, 0, 64, 32));
        textButtonPauseStyle.down = new TextureRegionDrawable(new TextureRegion(Assets.getInstance().manager.get(SPRITE_NAME), 0, 32, 64, 32));

        Pixmap pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = drawable;

        pixmap = new Pixmap(0, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knob = drawable;

        pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knobBefore = drawable;

        ProgressBar healthBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, progressBarStyle);
        healthBar.setValue(1.0f);
        healthBar.setAnimateDuration(0.25f);
        healthBar.setBounds(10, 10, 100, 20);
        healthBar.setValue(0.5f);



        float x = Gdx.graphics.getWidth() - 70;
        float y = Gdx.graphics.getHeight() - 36;

        TextButton button = new TextButton("Pause", textButtonPauseStyle);
        button.setPosition(x, y);
        button.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Button Pressed");
            }
        });

        stage.addActor(healthBar);
        stage.addActor(button);
    }
}
