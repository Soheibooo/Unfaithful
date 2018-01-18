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
package com.bdeb1.unfaithful.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Entries;
import com.bdeb1.unfaithful.components.ActionComponent;
import com.bdeb1.unfaithful.components.AnimationComponent;
import com.bdeb1.unfaithful.components.LaptopComponent;
import com.bdeb1.unfaithful.components.StateComponent;
import com.bdeb1.unfaithful.components.TextureComponent;

/**
 *
 * @author Soheib El-Harrache
 */
public class AnimationSystem extends IteratingSystem {

    private ComponentMapper<TextureComponent> textureM;
    private ComponentMapper<AnimationComponent> animationM;
    private ComponentMapper<StateComponent> stateM;
    private ComponentMapper<ActionComponent> actionM;

    public AnimationSystem() {
        super(Family.all(
                TextureComponent.class,
                AnimationComponent.class,
                StateComponent.class,
                LaptopComponent.class
        ).get());

        textureM = ComponentMapper.getFor(TextureComponent.class);
        animationM = ComponentMapper.getFor(AnimationComponent.class);
        stateM = ComponentMapper.getFor(StateComponent.class);
        actionM = ComponentMapper.getFor(ActionComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        StateComponent stateC = stateM.get(entity);
        ActionComponent actionC = actionM.get(entity);
        AnimationComponent animationC = animationM.get(entity);
        

        for (int i : animationC.animations.keySet()) {
            System.out.println("CLE: " + i);
            System.out.println("VALUE: " + animationC.animations.get(i).toString());
            System.out.println("POUR TANT LE STATE EST: " + stateC.get());
        }
        if (animationC.animations.containsKey(stateC.get())) {
            
            TextureComponent tex = textureM.get(entity);
            Animation animation = 
                    animationC.animations.get(stateC.get()).get(actionC.get());
            tex.region = (TextureRegion) animation.getKeyFrame(stateC.time);
        }
    }
}
