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
import com.bdeb1.unfaithful.components.MovementComponent;
import com.bdeb1.unfaithful.components.RandomComponent;
import com.bdeb1.unfaithful.components.StateComponent;
import com.bdeb1.unfaithful.components.TargetComponent;
import com.bdeb1.unfaithful.components.TransformComponent;
import javax.swing.text.Position;

/**
 *
 * @author Soheib El-Harrache
 */
public class TargetSystem extends IntervalIteratingSystem {

    private static final float TIME_STEP = 1 / 45f;
    
    private static final int WIDTH_SCREEN_TOT = 900;
    private static final int WIDTH_ZONE_SAFE = 300;
    private static final int WIDTH_ZONE_SUSPICION = 300;

    private int randPositionTar = 0;
    private int randTimeStay = 0;
    private int randDir = 0;
    
    private ComponentMapper<ActionComponent> actionM;
    private ComponentMapper<StateComponent> stateM;
    private ComponentMapper<TargetComponent> targetM;
    private ComponentMapper<RandomComponent> randomM;
    private ComponentMapper<TransformComponent> transformM;
    private ComponentMapper<MovementComponent> movementM;

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
        movementM = ComponentMapper.getFor(MovementComponent.class);
    }

    @Override
    protected void processEntity(Entity entity) {
        ActionComponent actionC = actionM.get(entity);
        StateComponent stateC = stateM.get(entity);
        TargetComponent targetC = targetM.get(entity);
        TransformComponent transformC = transformM.get(entity);
        MovementComponent movementC = movementM.get(entity);

        //Time between changing action
        RandomComponent randomC = randomM.get(entity);
        if (movementC.speed > 0) {

        } else if (movementC.speed < 0) {

        } else {

        }
        
        
        
        
        //ZONE SAFE
        if (transformC.position.x < WIDTH_ZONE_SAFE || transformC.position.x >= WIDTH_SCREEN_TOT - WIDTH_ZONE_SAFE) {
            if (targetC.suspicion_gauge >= 0) {
                targetC.suspicion_gauge -= 0.05f;
            }
        } else if (transformC.position.x > WIDTH_ZONE_SAFE && transformC.position.x < WIDTH_ZONE_SUSPICION) {
            if (movementC.speed > 0) {
                targetC.suspicion_gauge += 0.1f;
            } else {
                if (targetC.suspicion_gauge >= 0) {
                    targetC.suspicion_gauge -= 0.05f;
                }
            }
        } else if (transformC.position.x >= WIDTH_SCREEN_TOT - WIDTH_ZONE_SAFE - WIDTH_ZONE_SUSPICION && transformC.position.x < WIDTH_SCREEN_TOT - WIDTH_ZONE_SAFE) {
            if (movementC.speed > 0) {
                if (targetC.suspicion_gauge >= 0) {
                    targetC.suspicion_gauge -= 0.05f;
                }

            } else {

                targetC.suspicion_gauge += 0.1f;
            }
        } else { //Zone de Danger très radioactive
                targetC.suspicion_gauge += 100f;
        }
        
        
        
        if(targetC.suspicion_gauge < TargetComponent.TRIGGER_POINT_SUSPICIOUS) {
            stateC.state = TargetComponent.STATE_UNSUSPICIOUS;
            
        }else if(targetC.suspicion_gauge >= TargetComponent.TRIGGER_POINT_SUSPICIOUS && targetC.suspicion_gauge < TargetComponent.TRIGGER_POINT_FRENZY) {
            stateC.state = TargetComponent.STATE_SUSPICIOUS;
            
            
        } else if(targetC.suspicion_gauge >= TargetComponent.TRIGGER_POINT_FRENZY && targetC.suspicion_gauge < TargetComponent.TRIGGER_POINT_DONE) {
            stateC.state = TargetComponent.STATE_FRENZY;
            
        } else {
            stateC.state = TargetComponent.STATE_DONE;
        }
        
        
        
        

        if (actionC.time > randomC.value
                && (actionC.action == TargetComponent.ACTION_LEFT_SCREEN
                || actionC.action == TargetComponent.ACTION_RIGHT_SCREEN)) {

            //TO ADJUST DIFFICULTY
            randomC.value = RandomComponent.rand.nextInt(
                    (int) (35 + targetC.difficultyAddition)
                    - Math.max(15, (int) targetC.suspicion_gauge / 5 + 2)
            );
            
            randPositionTar = RandomComponent.rand.nextInt(WIDTH_SCREEN_TOT - 2*WIDTH_ZONE_SAFE - 2*WIDTH_ZONE_SUSPICION)+WIDTH_ZONE_SAFE+WIDTH_ZONE_SUSPICION;
            randTimeStay = RandomComponent.rand.nextInt(8 - (int)targetC.difficultyAddition);
            randDir = RandomComponent.rand.nextInt(2);
            
            if(actionC.action == TargetComponent.ACTION_LEFT_SCREEN){
                
                movementC.speed=5;
                
            }else if(actionC.action == TargetComponent.ACTION_RIGHT_SCREEN){
                movementC.speed=-5;
            }
           
            
            actionC.time = 0;
            
        } else {
            
            if((transformC.position.x >=randPositionTar && movementC.speed>0) || (transformC.position.x<=randPositionTar && movementC.speed<0)){
                movementC.speed=0;
                if(actionC.action != TargetComponent.ACTION_WATCHING)
                    actionC.action = TargetComponent.ACTION_WATCHING;
                
                if(actionC.time >= randTimeStay) {
                    if(randDir == 0)
                        movementC.speed = -5;
                    else
                        movementC.speed = 5;
                }
                
                
                if(transformC.position.x > WIDTH_SCREEN_TOT) {
                    actionC.action = TargetComponent.ACTION_RIGHT_SCREEN;
                    movementC.speed = 0;
                }
                else if (transformC.position.x < 0) {
                    actionC.action = TargetComponent.ACTION_LEFT_SCREEN;
                    movementC.speed = 0;
                }
            }
            
        }
    }
}
