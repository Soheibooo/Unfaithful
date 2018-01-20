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
        textButtonStyle.checkedOver = new TextureRegionDrawable(new TextureRegion(textureHover, 0, 0, textureHover.getWidth(), textureHover.getHeight()));

        TextButton button = new TextButton("", textButtonStyle);
        button.setPosition(x, y);
        return button;
    }

    /* public Entity registerGUI(PooledEngine engine) {
        Entity entity = engine.createEntity();

        AnimationComponent animC
                = engine.createComponent(AnimationComponent.class);
        MenuComponent menuC =
                engine.createComponent(MenuComponent.class);
        StateComponent stateC =
                engine.createComponent(StateComponent.class);
        ActionComponent actionC =
                engine.createComponent(ActionComponent.class);
        TransformComponent transformC =
                engine.createComponent(TransformComponent.class);

        transformC.position.set(5f, 10f, 0);
        actionC.action = 0;

        stateC.set(MenuComponent.STATE_INACTIVE);
        TextureAtlas texAtlas =  Assets.getInstance().manager.get(Assets.ATLAS_MENU);

        TextureAtlas.AtlasRegion menuImage = texAtlas.findRegion("menu_bar0000");

        Array<TextureAtlas.AtlasRegion> menuRegion = new Array<TextureAtlas.AtlasRegion>();
        menuRegion.add(menuImage);

        Animation<TextureRegion> menuStill = new Animation<TextureRegion>(1/12f, menuRegion);
        Animation<TextureRegion> MenuAnim = new Animation<TextureRegion>(1/12f, texAtlas.getRegions(), Animation.PlayMode.LOOP);


        HashMap<Integer, Animation> animeList = new HashMap<Integer, Animation>();
        HashMap<Integer, Animation> animeList2 = new HashMap<Integer, Animation>();

        animeList.put(0, menuStill);
        animeList2.put(0, MenuAnim);

        animC.animations.put(MenuComponent.STATE_INACTIVE, animeList);
        animC.animations.put(MenuComponent.STATE_ACTIVE, animeList2);

        entity.add(menuC);
        entity.add(stateC);
        entity.add(transformC);
        entity.add(actionC);
        entity.add(animC);

        engine.addEntity(entity);

        return entity;
    }*/
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
