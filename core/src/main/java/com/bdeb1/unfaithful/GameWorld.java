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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.bdeb1.unfaithful.components.*;

import com.bdeb1.unfaithful.util.Constants;
import java.util.HashMap;

/**
 *
 * @author Soheib El-Harrache
 */
public class GameWorld {
    private int idLevel = 1;
    
    private PooledEngine engine;
    private Entity hacker, target, suspiciousGauge, hackingGauge;

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
    }

    private Entity createSuspiciousGauge() {
        Entity entity = engine.createEntity();

        AnimationComponent animC
                = engine.createComponent(AnimationComponent.class);
        TransformComponent positionC
                = engine.createComponent(TransformComponent.class);
        StateComponent stateC
                = engine.createComponent(StateComponent.class);
        TextureComponent textureC
                = engine.createComponent(TextureComponent.class);
        ActionComponent actionC
                = engine.createComponent(ActionComponent.class);

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

        positionC.position.set(Constants.World.HACKER_INITIAL_POSITION);
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
