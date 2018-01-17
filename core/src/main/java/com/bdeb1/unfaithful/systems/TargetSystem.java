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
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.bdeb1.unfaithful.components.ActionComponent;
import com.bdeb1.unfaithful.components.RandomComponent;
import com.bdeb1.unfaithful.components.StateComponent;
import com.bdeb1.unfaithful.components.TargetComponent;
import com.bdeb1.unfaithful.components.TransformComponent;

/**
 *
 * @author Soheib El-Harrache
 */
public class TargetSystem extends IntervalIteratingSystem {

    private static final float TIME_STEP = 1 / 45f;
    
    
    
    private ComponentMapper<ActionComponent> actionM;
    private ComponentMapper<StateComponent> stateM;
    private ComponentMapper<TargetComponent> targetM;
    private ComponentMapper<RandomComponent> randomM;
    private ComponentMapper<TransformComponent> transformM;

    public TargetSystem() {
        super(Family.all(
                ActionComponent.class,
                StateComponent.class,
                TargetComponent.class,
                RandomComponent.class,
                TransformComponent.class
        ).get(), TIME_STEP);
        
        
        
        actionM = ComponentMapper.getFor(ActionComponent.class);
        stateM = ComponentMapper.getFor(StateComponent.class);
        targetM = ComponentMapper.getFor(TargetComponent.class);
        randomM = ComponentMapper.getFor(RandomComponent.class);
        transformM = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity) {
        ActionComponent actionC = actionM.get(entity);
        StateComponent stateC = stateM.get(entity);
        TargetComponent targetC = targetM.get(entity);
        TransformComponent transformC = transformM.get(entity);

        //Time between changing action
        RandomComponent randomC = randomM.get(entity);
        
        //Out of screen
        //if (transformC.position.x < )
                
        if (actionC.time > randomC.value
                && (actionC.action == TargetComponent.ACTION_LEFT_SCREEN
                || actionC.action == TargetComponent.ACTION_RIGHT_SCREEN)) {

            //TO ADJUST DIFFICULTY
            randomC.value = RandomComponent.rand.nextInt(
                    (int)(35 + targetC.difficultyAddition) - 
                    Math.max(15, (int) targetC.suspicion_gauge / 5 + 2)
            );
        } else {

        }

        if (targetC.suspicion_gauge
                >= TargetComponent.TRIGGER_POINT_DONE) {
            stateC.state = TargetComponent.STATE_DONE;
        } else if (targetC.suspicion_gauge
                >= TargetComponent.TRIGGER_POINT_FRENZY) {
            stateC.state = TargetComponent.STATE_FRENZY;
        }
    }
}
