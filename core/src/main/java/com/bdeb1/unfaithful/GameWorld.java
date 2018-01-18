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
package com.bdeb1.unfaithful;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.bdeb1.unfaithful.components.*;
import com.bdeb1.unfaithful.screens.GUI;

import java.util.HashMap;

/**
 *
 * @author Soheib El-Harrache
 */
public class GameWorld {
    private int idLevel = 1;
    
    private PooledEngine engine;
    private Entity hacker, target, suspiciousGauge, hackingGauge, menu;

    private final int DISTANCE_BETWEEB_BARS = 10;
    
    public GameWorld(PooledEngine engine) {
        this.engine = engine;
        
        generateLevel(idLevel);
        
    }
    
    public int getIDLevel() {
        return idLevel;
    }
    
    public void generateLevel(int difficulty) {
        engine.clearPools();
        engine.removeAllEntities();
        
        idLevel = difficulty;
        target = createTarget(difficulty);
        hacker = createHacker(difficulty);
        suspiciousGauge = createSuspiciousGauge();
        menu = registerGUI();
    }

    public Entity registerGUI() {
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
        TextureComponent textureC =
                engine.createComponent(TextureComponent.class);

        transformC.position.set(10, 10, 0);
        actionC.action = 0;

        stateC.set(MenuComponent.STATE_INACTIVE);
        TextureAtlas texAtlas =  Assets.getInstance().manager.get(Assets.ATLAS_MENU);

        TextureAtlas.AtlasRegion menuImage = texAtlas.findRegion("menu_bar0000");

        Array<TextureAtlas.AtlasRegion> menuRegion = new Array<TextureAtlas.AtlasRegion>();
        menuRegion.add(menuImage);

        Animation<TextureRegion> menuStill = new Animation<TextureRegion>(1/12f, menuRegion);
        Animation<TextureRegion> MenuAnim = new Animation<TextureRegion>(1/12f, texAtlas.findRegions("menu_bar_select"), Animation.PlayMode.LOOP);


        HashMap<Integer, Animation> animeList = new HashMap<Integer, Animation>();
        HashMap<Integer, Animation> animeList2 = new HashMap<Integer, Animation>();

        animeList.put(0, menuStill);
        animeList2.put(0, MenuAnim);

        animC.animations.put(MenuComponent.STATE_INACTIVE, animeList2);
        animC.animations.put(MenuComponent.STATE_ACTIVE, animeList);

        entity.add(textureC);
        entity.add(menuC);
        entity.add(stateC);
        entity.add(transformC);
        entity.add(actionC);
        entity.add(animC);

        engine.addEntity(entity);

        return entity;
        laptopScreen(hacker);
		//suspiciousGauge = createSuspiciousGauge();
    }


    private Entity laptopScreen(Entity hacker) {
        Entity entity = engine.createEntity();

        TransformComponent positionC
                = engine.createComponent(TransformComponent.class);
        AnimationComponent animC
                = engine.createComponent(AnimationComponent.class);
				TextureComponent textureC
                = engine.createComponent(TextureComponent.class);
        ActionComponent actionC
                = engine.createComponent(ActionComponent.class);

        StateComponent stateC
                = engine.createComponent(StateComponent.class);
        LaptopComponent laptopC
                = engine.createComponent(LaptopComponent.class);


        stateC.set(0);
        actionC.set(LaptopComponent.ACTION_NOT_HACKING);

        TextureAtlas atTextureLaptopHack = Assets.getInstance().manager.get(Assets.ATLAS_HACKING_LAPSCREEN);
        TextureAtlas atTextureLaptop = Assets.getInstance().manager.get(Assets.ATLAS_NOTHACKING_LAPSCREEN);

        Animation<TextureRegion> laptopHack = new Animation<TextureRegion>(1/12f, atTextureLaptopHack.getRegions(), PlayMode.LOOP);
        Animation<TextureRegion> laptopNotHack = new Animation<TextureRegion>(1/12f, atTextureLaptop.getRegions(), PlayMode.LOOP);

        HashMap<Integer, Animation> anime = new HashMap<Integer, Animation>();
        anime.put(1, laptopHack);
        anime.put(2, laptopNotHack);

        animC.animations.put(0, anime);

        TransformComponent positionRel = hacker.getComponent(TransformComponent.class);


        positionC.position.set(positionRel.position.x + 5, positionRel.position.y + 5, 0);

        entity.add(textureC);
        entity.add(animC);
        entity.add(positionC);
        entity.add(actionC);
        entity.add(laptopC);

        engine.addEntity(entity);
        return entity;

    }

    /*private Entity createSuspiciousGauge() {
        Entity entity = engine.createEntity();

        AnimationComponent animC
                = engine.createComponent(AnimationComponent.class);
        TransformComponent positionC
                = engine.createComponent(TransformComponent.class);
        StateComponent stateC
                = engine.createComponent(StateComponent.class);
		        positionC.position.set(5.0f, Gdx.graphics.getHeight() - DISTANCE_BETWEEB_BARS, 0.0f);

        entity.add(textureC);
        entity.add(animC);
        entity.add(positionC);
        entity.add(stateC);
        //Not used for now (to talk with Samuel, I have a plan to make it conditional on the system) -Soso
        entity.add(actionC);

        stateC.set(GaugeStateComponent.STATE_NORMAL);
        TextureAtlas suspiciousGauge = Assets.getInstance().manager.get(Assets.ATLAS_BAR_SUSPICION);

        TextureAtlas.AtlasRegion suspiciousGaugeImage = suspiciousGauge.findRegion("suspicion_bar0000");

       Array<TextureAtlas.AtlasRegion> simpleSuspiciousGaugeRegion = new Array<TextureAtlas.AtlasRegion>();
       simpleSuspiciousGaugeRegion.add(suspiciousGaugeImage);

        Animation<TextureRegion> suspiciousGaugeAnim = new Animation<TextureRegion>(1/12f, simpleSuspiciousGaugeRegion);
        Animation<TextureRegion> suspiciousGaugeBlinkingAnim = new Animation<TextureRegion>(1/12f, suspiciousGauge.getRegions(), PlayMode.LOOP);


        HashMap<Integer, Animation> animeList = new HashMap<Integer, Animation>();
        HashMap<Integer, Animation> animeList2 = new HashMap<Integer, Animation>();

        animeList.put(0, suspiciousGaugeAnim);
        animeList2.put(0, suspiciousGaugeBlinkingAnim);

        //DEFAULT
        animC.animations.put(0 ,animeList);
        animC.animations.put(1 ,animeList2);

        engine.addEntity(entity);

        return entity;
    }


    private Entity createTarget(float difficultyKey) {
        Entity entity = engine.createEntity();

        AnimationComponent animC
                = engine.createComponent(AnimationComponent.class);
        TransformComponent positionC
                = engine.createComponent(TransformComponent.class);
        StateComponent stateC
                = engine.createComponent(StateComponent.class);
        TextureComponent textureC
                = engine.createComponent(TextureComponent.class);
        TargetComponent targetC
                = engine.createComponent(TargetComponent.class);
        ActionComponent actionC
                = engine.createComponent(ActionComponent.class);

//        animC.animations.put(CharacterComponent.STATE_ALIVE, Assets.uneAnim);
//        animC.animations.put(CharacterComponent.STATE_DEAD, Assets.uneAnim);
//        animC.animations.put(CharacterComponent.STATE_FRENZY, Assets.uneAnim);

        positionC.position.set(5.0f, 1.0f, 0.0f);
        stateC.set(TargetComponent.STATE_UNSUSPICIOUS);
        targetC.difficultyAddition = -difficultyKey * 5;

        stateC.set(0);
        actionC.set(TargetComponent.ACTION_TALKING);

        entity.add(textureC);
        entity.add(animC);
        entity.add(targetC);
        entity.add(positionC);
        entity.add(stateC);

        

        engine.addEntity(entity);

        return entity;
}

    private Entity createHacker(float difficultyKey) {
                Entity entity = engine.createEntity();

        AnimationComponent animC
                = engine.createComponent(AnimationComponent.class);
        TransformComponent positionC
                = engine.createComponent(TransformComponent.class);
        StateComponent stateC
                = engine.createComponent(StateComponent.class);
        TextureComponent textureC
                = engine.createComponent(TextureComponent.class);
        HackerComponent hackerC
                = engine.createComponent(HackerComponent.class);
        ActionComponent actionC
                = engine.createComponent(ActionComponent.class);

//        animC.animations.put(CharacterComponent.STATE_ALIVE, Assets.uneAnim);
//        animC.animations.put(CharacterComponent.STATE_DEAD, Assets.uneAnim);
//        animC.animations.put(CharacterComponent.STATE_FRENZY, Assets.uneAnim);

        positionC.position.set(5f, 3.5f, 0.0f);
        //PAS DE STATE POUR LE HACKER
        stateC.set(0);
        actionC.set(HackerComponent.ACTION_NOT_HACKING);
        
        TextureAtlas texAtHacking = Assets.getInstance().manager.get(Assets.ATLAS_HACKING);
        TextureAtlas texAtNOTHacking = Assets.getInstance().manager.get(Assets.ATLAS_NOTHACKING);
        
        HashMap<Integer, Animation> animeList = new HashMap<Integer, Animation>();
        
        
        Animation<TextureRegion> animeHacking = new Animation<TextureRegion>(1/12f, texAtHacking.getRegions(), PlayMode.LOOP);
        Animation<TextureRegion> animeNotHacking = new Animation<TextureRegion>(1/12f, texAtNOTHacking.getRegions(), PlayMode.LOOP);
        
        
        animeList.put(1, animeHacking);
        animeList.put(2, animeNotHacking);
        
        //DEFAULT
        animC.animations.put(0 ,animeList);
        
        
        entity.add(textureC);
        entity.add(animC);
        
        entity.add(hackerC);
        entity.add(positionC);
        entity.add(stateC);
        entity.add(actionC);

        engine.addEntity(entity);
        
        return entity;
    }
    
    public boolean isHacked(int difficultyID) {
        HackerComponent hackerC = hacker.getComponent(HackerComponent.class);
        return hackerC.hacking_gauge >= (50 + difficultyID*difficultyID*10) ? true : false; //1=60; 2=90; 3=140;
    }
    
    
}
