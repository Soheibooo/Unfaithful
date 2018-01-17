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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bdeb1.unfaithful.components.ActionComponent;
import com.bdeb1.unfaithful.components.AnimationComponent;
import com.bdeb1.unfaithful.components.HackerComponent;
import com.bdeb1.unfaithful.components.StateComponent;
import com.bdeb1.unfaithful.components.TargetComponent;
import com.bdeb1.unfaithful.components.TextureComponent;
import com.bdeb1.unfaithful.components.TransformComponent;
import java.util.HashMap;

/**
 *
 * @author Soheib El-Harrache
 */
public class GameWorld {
    private int idLevel = 1;
    
    private PooledEngine engine;
    private Entity hacker, target;
    
    
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

//        animC.animations.put(CharacterComponent.STATE_ALIVE, Assets.uneAnim);
//        animC.animations.put(CharacterComponent.STATE_DEAD, Assets.uneAnim);
//        animC.animations.put(CharacterComponent.STATE_FRENZY, Assets.uneAnim);

        positionC.position.set(5.0f, 1.0f, 0.0f);
        stateC.set(TargetComponent.STATE_UNSUSPICIOUS);
        targetC.difficultyAddition = -difficultyKey * 5;
        
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

        positionC.position.set(5f, 2.9f, 0.0f);
        //PAS DE STATE POUR LE HACKER
        stateC.set(0);
        actionC.set(HackerComponent.ACTION_NOT_HACKING);
        
        TextureAtlas texAtHacking = Assets.getInstance().manager.get(Assets.ATLAS_HACKING);
        TextureAtlas texAtNOTHacking = Assets.getInstance().manager.get(Assets.ATLAS_NOTHACKING);
        
        HashMap<Integer, Animation> animeList = new HashMap<Integer, Animation>();
        
        
        Animation<TextureRegion> animeHacking = new Animation<TextureRegion>(0.1f, texAtHacking.getRegions());
        Animation<TextureRegion> animeNotHacking = new Animation<TextureRegion>(0.2f, texAtNOTHacking.getRegions());
        
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
