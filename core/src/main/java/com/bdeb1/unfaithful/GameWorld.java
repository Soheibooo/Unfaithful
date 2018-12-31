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
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.bdeb1.unfaithful.components.ActionComponent;
import com.bdeb1.unfaithful.components.AnimationComponent;
import com.bdeb1.unfaithful.components.ComptoirComponent;
import com.bdeb1.unfaithful.components.HackerComponent;
import com.bdeb1.unfaithful.components.LaptopComponent;
import com.bdeb1.unfaithful.components.MovementComponent;
import com.bdeb1.unfaithful.components.StateComponent;
import com.bdeb1.unfaithful.components.TargetComponent;
import com.bdeb1.unfaithful.components.TextureComponent;
import com.bdeb1.unfaithful.components.TransformComponent;
import com.bdeb1.unfaithful.util.Constants;
import static com.bdeb1.unfaithful.util.Constants.World.D_HACKER_SCREEN;
import static com.bdeb1.unfaithful.util.Constants.World.D_HACKER_SCREEN_2;
import static com.bdeb1.unfaithful.util.Constants.World.D_HACKER_SCREEN_3;
import java.util.HashMap;

/**
 * @author Soheib El-Harrache
 */
public class GameWorld {

    private PooledEngine engine;
    private Entity hacker, laptop, comptoir, target;
    private int level;

    public GameWorld(PooledEngine engine, int level) {
        this.engine = engine;
        this.level = level;
        generateLevel();
    }

    public void generateLevel() {
        engine.clearPools();
        engine.removeAllEntities();

        target = createTarget();
        comptoir = createDumbYouRE();
        laptop = createLaptopScreen();
        hacker = createHacker();
    }

    private Entity createLaptopScreen() {
        Entity entity = engine.createEntity();

        TransformComponent positionC = engine
                .createComponent(TransformComponent.class);
        AnimationComponent animC = engine
                .createComponent(AnimationComponent.class);
        TextureComponent textureC = engine
                .createComponent(TextureComponent.class);
        ActionComponent actionC = engine
                .createComponent(ActionComponent.class);

        StateComponent stateC = engine.createComponent(StateComponent.class);
        LaptopComponent laptopC = engine
                .createComponent(LaptopComponent.class);

        Vector3 v3 = new Vector3();
        switch (level) {
            case 1:
                v3 = D_HACKER_SCREEN;
                break;
            case 2:
                v3 = D_HACKER_SCREEN_2;
                break;
            case 3:
                v3 = D_HACKER_SCREEN_3;
                break;
            default:
                v3 = D_HACKER_SCREEN;
                break;
        }
        positionC.position.set(Constants.World.HACKER_INITIAL_POSITION.x
                + v3.x
                * Constants.World.SCALE,
                Constants.World.HACKER_INITIAL_POSITION.y
                + v3.y
                * Constants.World.SCALE, 0);

        stateC.set(0);
        actionC.set(LaptopComponent.ACTION_NOT_HACKING);

        TextureAtlas atTextureLaptopHack;
        TextureAtlas atTextureLaptop;

        atTextureLaptopHack = Assets.getInstance().manager
                .get(Assets.ATLAS_HACKING_LAPSCREEN_LV1);
        atTextureLaptop = Assets.getInstance().manager
                .get(Assets.ATLAS_NOTHACKING_LAPSCREEN_LV1);

        switch (level) {
            case 1:
                atTextureLaptopHack = Assets.getInstance().manager
                        .get(Assets.ATLAS_HACKING_LAPSCREEN_LV1);
                atTextureLaptop = Assets.getInstance().manager
                        .get(Assets.ATLAS_NOTHACKING_LAPSCREEN_LV1);
                break;
            case 2:
                atTextureLaptopHack = Assets.getInstance().manager
                        .get(Assets.ATLAS_HACKING_LAPSCREEN_LV2);
                atTextureLaptop = Assets.getInstance().manager
                        .get(Assets.ATLAS_NOTHACKING_LAPSCREEN_LV2);
                break;
            case 3:
                atTextureLaptopHack = Assets.getInstance().manager
                        .get(Assets.ATLAS_HACKING_LAPSCREEN_LV3);
                atTextureLaptop = Assets.getInstance().manager
                        .get(Assets.ATLAS_NOTHACKING_LAPSCREEN_LV3);
                break;
            default:
                break;
        }

        Animation<TextureRegion> laptopHack = new Animation<>(
                1 / 12f, atTextureLaptopHack.getRegions(), PlayMode.LOOP);
        Animation<TextureRegion> laptopNotHack = new Animation<>(
                1 / 12f, atTextureLaptop.getRegions(), PlayMode.LOOP);

        HashMap<Integer, Animation> anime = new HashMap<>();
        anime.put(1, laptopHack);
        anime.put(2, laptopNotHack);

        animC.animations.put(0, anime);

        entity.add(textureC);
        entity.add(animC);
        entity.add(positionC);
        entity.add(actionC);
        entity.add(laptopC);
        entity.add(stateC);

        engine.addEntity(entity);
        return entity;
    }

    private Entity createDumbYouRE() {
        Entity entity = engine.createEntity();

        TransformComponent positionC = engine
                .createComponent(TransformComponent.class);
        TextureComponent textureC = engine
                .createComponent(TextureComponent.class);
        ComptoirComponent compC = engine
                .createComponent(ComptoirComponent.class);

        switch (level) {
            case 1:
                textureC.region = new TextureRegion(
                        Assets.getInstance().manager.get(Assets.FOREGROUND_LV1));
                break;
            case 2:
                textureC.region = new TextureRegion(
                        Assets.getInstance().manager.get(Assets.FOREGROUND_LV2));
                break;
            case 3:
                textureC.region = new TextureRegion(
                        Assets.getInstance().manager.get(Assets.FOREGROUND_LV3));
                break;
            default:
                textureC.region = new TextureRegion(
                        Assets.getInstance().manager.get(Assets.FOREGROUND_LV1));
                break;
        }

        positionC.position.set(0, 0, 0);

        entity.add(positionC);
        entity.add(textureC);
        entity.add(compC);

        engine.addEntity(entity);
        return entity;
    }

    private Entity createTarget() {
        Entity entity = engine.createEntity();

        AnimationComponent animC = engine
                .createComponent(AnimationComponent.class);
        TransformComponent positionC = engine
                .createComponent(TransformComponent.class);
        StateComponent stateC = engine.createComponent(StateComponent.class);
        TextureComponent textureC = engine
                .createComponent(TextureComponent.class);
        TargetComponent targetC = engine
                .createComponent(TargetComponent.class);
        ActionComponent actionC = engine
                .createComponent(ActionComponent.class);
        MovementComponent movementC = engine
                .createComponent(MovementComponent.class);

        positionC.position.set(-50.0f, 170.0f, 0.0f);
        stateC.set(TargetComponent.STATE_UNSUSPICIOUS);
        targetC.difficultyAddition = -level * 5;

        TextureAtlas atTextureMarche = Assets.getInstance().manager
                .get(Assets.WOMAN_WALKING);
        TextureAtlas atTextureMarcheSus = Assets.getInstance().manager
                .get(Assets.WOMAN_WALKING_SUSPICIOUSLY);
        TextureAtlas atTextureRotate = Assets.getInstance().manager
                .get(Assets.WOMAN_ROTATING);
        TextureAtlas atTextureRotateSus = Assets.getInstance().manager
                .get(Assets.WOMAN_ROTATING_SUSPICIOUSLY);

        switch (level) {
            case 1:
                atTextureMarche = Assets.getInstance().manager
                        .get(Assets.WOMAN_WALKING);
                atTextureMarcheSus = Assets.getInstance().manager
                        .get(Assets.WOMAN_WALKING_SUSPICIOUSLY);
                atTextureRotate = Assets.getInstance().manager
                        .get(Assets.WOMAN_ROTATING);
                atTextureRotateSus = Assets.getInstance().manager
                        .get(Assets.WOMAN_ROTATING_SUSPICIOUSLY);
                break;
            case 2:
                atTextureMarche = Assets.getInstance().manager
                        .get(Assets.WOMAN_WALKING);
                atTextureMarcheSus = Assets.getInstance().manager
                        .get(Assets.WOMAN_WALKING_SUSPICIOUSLY);
                break;
            case 3:

                break;
            default:
                break;
        }

        Animation<TextureRegion> womanWalk = new Animation<>(
                1 / 12f, atTextureMarche.getRegions(), PlayMode.LOOP);
        Animation<TextureRegion> womanWalkSus = new Animation<>(
                1 / 12f, atTextureMarcheSus.getRegions(), PlayMode.LOOP);
        Animation<TextureRegion> womanRotate = new Animation<>(
                1 / 12f, atTextureRotate.getRegions(), PlayMode.LOOP);
        Animation<TextureRegion> womanRotateSus = new Animation<>(
                1 / 12f, atTextureRotateSus.getRegions(), PlayMode.LOOP);

        HashMap<Integer, Animation> animeActionWalk = new HashMap<>();
        HashMap<Integer, Animation> animeActionRotate = new HashMap<>();

        animeActionWalk.put(TargetComponent.ACTION_WALK_LEFT, womanWalk);
        animeActionWalk.put(TargetComponent.ACTION_WALK_RIGHT, womanWalk);
        animeActionWalk.put(1, womanWalkSus);
        animeActionWalk.put(1, womanWalkSus);

        animeActionRotate.put(0, womanRotate);
        animeActionRotate.put(1, womanRotateSus);

        animC.animations.put(1, animeActionWalk);

        stateC.set(TargetComponent.STATE_UNSUSPICIOUS);
        actionC.set(TargetComponent.ACTION_WALK_RIGHT);

        entity.add(textureC);
        entity.add(animC);
        entity.add(targetC);
        entity.add(positionC);
        entity.add(stateC);
        entity.add(actionC);
        entity.add(movementC);

        engine.addEntity(entity);

        return entity;
    }

    private Entity createHacker() {
        Entity entity = engine.createEntity();

        AnimationComponent animC = engine
                .createComponent(AnimationComponent.class);
        TransformComponent positionC = engine
                .createComponent(TransformComponent.class);
        StateComponent stateC = engine.createComponent(StateComponent.class);
        TextureComponent textureC = engine
                .createComponent(TextureComponent.class);
        HackerComponent hackerC = engine
                .createComponent(HackerComponent.class);
        ActionComponent actionC = engine
                .createComponent(ActionComponent.class);

        positionC.position.set(Constants.World.HACKER_INITIAL_POSITION);

        //PAS DE STATE POUR LE HACKER
        stateC.set(0);
        actionC.set(HackerComponent.ACTION_NOT_HACKING);

        TextureAtlas texAtHacking = Assets.getInstance().manager
                .get(Assets.ATLAS_HACKING_LV1);
        TextureAtlas texAtNOTHacking = Assets.getInstance().manager
                .get(Assets.ATLAS_NOTHACKING_LV1);

        switch (level) {
            case 1:
                texAtHacking = Assets.getInstance().manager
                        .get(Assets.ATLAS_HACKING_LV1);
                texAtNOTHacking = Assets.getInstance().manager
                        .get(Assets.ATLAS_NOTHACKING_LV1);
                break;
            case 2:
                texAtHacking = Assets.getInstance().manager
                        .get(Assets.ATLAS_HACKING_LV2);
                texAtNOTHacking = Assets.getInstance().manager
                        .get(Assets.ATLAS_NOTHACKING_LV2);
                break;
            case 3:
                texAtHacking = Assets.getInstance().manager
                        .get(Assets.ATLAS_HACKING_LV3);
                texAtNOTHacking = Assets.getInstance().manager
                        .get(Assets.ATLAS_NOTHACKING_LV3);
                break;
            default:
                break;
        }

        HashMap<Integer, Animation> animeList
                = new HashMap<>();

        Animation<TextureRegion> animeHacking = new Animation<>(
                1 / 12f, texAtHacking.getRegions(), PlayMode.LOOP);
        Animation<TextureRegion> animeNotHacking
                = new Animation<>(1 / 12f,
                        texAtNOTHacking.getRegions(),
                        PlayMode.LOOP);

        animeList.put(1, animeHacking);
        animeList.put(2, animeNotHacking);

        //DEFAULT
        animC.animations.put(0, animeList);

        entity.add(textureC);
        entity.add(animC);

        entity.add(hackerC);
        entity.add(positionC);
        entity.add(stateC);
        entity.add(actionC);

        engine.addEntity(entity);

        return entity;
    }

    public boolean isHacked() {
        HackerComponent hackerC = hacker.getComponent(HackerComponent.class);
        return hackerC.hacking_gauge
                >= (50 + level * level * 10); //1=60; 2=90; 3=140;
    }

    public float getHackBarProgress() {
        return hacker.getComponent(HackerComponent.class).hacking_gauge;
    }
}
