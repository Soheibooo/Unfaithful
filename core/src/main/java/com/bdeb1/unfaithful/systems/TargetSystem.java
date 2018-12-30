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
import com.badlogic.gdx.Gdx;
import com.bdeb1.unfaithful.components.ActionComponent;
import com.bdeb1.unfaithful.components.MovementComponent;
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

    private static final int WIDTH_SCREEN_TOT = 3200;
    private static final int WIDTH_ZONE_SAFE = 640;
    private static final int WIDTH_ZONE_SUSPICION = 700;

    private int randWaitingTime = RandomComponent.rand.nextInt(5);
    private int positionToGo = 0;
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
                TargetComponent.class
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

        
        //if hacker is hacking
        processSuspiciousGauge(targetC, transformC, movementC);
        
        
        if (targetC.suspicion_gauge < TargetComponent.TRIGGER_POINT_SUSPICIOUS) {
            stateC.state = TargetComponent.STATE_UNSUSPICIOUS;

        } else if (targetC.suspicion_gauge >= TargetComponent.TRIGGER_POINT_SUSPICIOUS && targetC.suspicion_gauge < TargetComponent.TRIGGER_POINT_FRENZY) {
            stateC.state = TargetComponent.STATE_SUSPICIOUS;

        } else if (targetC.suspicion_gauge >= TargetComponent.TRIGGER_POINT_FRENZY && targetC.suspicion_gauge < TargetComponent.TRIGGER_POINT_DONE) {
            stateC.state = TargetComponent.STATE_FRENZY;

        } else {
            stateC.state = TargetComponent.STATE_DONE;
        }
        
        processActionTarget(actionC, movementC, transformC);
        processMovementTarget(actionC, movementC, transformC);
        
    }
    
    private void processSuspiciousGauge(TargetComponent targetC, TransformComponent transformC, MovementComponent movementC) {
        // Three places: the safe zone, the suspicious zone, and the danger zone
        
        //SAFE
        if (transformC.position.x < WIDTH_ZONE_SAFE || transformC.position.x >= WIDTH_SCREEN_TOT - WIDTH_ZONE_SAFE - 50) {
            
            if (targetC.suspicion_gauge >= 0) {
                targetC.suspicion_gauge -= 0.013f;
            }
        //Suspicion left
        } else if (transformC.position.x > WIDTH_ZONE_SAFE && transformC.position.x < WIDTH_ZONE_SUSPICION + WIDTH_ZONE_SAFE) {
            
            if (movementC.speed > 0) {
                targetC.suspicion_gauge += 0.1f;
            } else {
                if (targetC.suspicion_gauge >= 0) {
                    targetC.suspicion_gauge -= 0.013f;
                }
            }
        //Suspicion Right
        } else if (transformC.position.x >= WIDTH_SCREEN_TOT - WIDTH_ZONE_SAFE - WIDTH_ZONE_SUSPICION && transformC.position.x < WIDTH_SCREEN_TOT - WIDTH_ZONE_SAFE) {
            if (movementC.speed > 0) {
                if (targetC.suspicion_gauge >= 0) {
                    targetC.suspicion_gauge -= 0.013f;
                }
            } else {
                targetC.suspicion_gauge += 0.1f;
            }
        //DANGER
        } else {
            targetC.suspicion_gauge = 100f;
        }
        
        
        
    }
    
    /**
     * process the current action that is being execute. (Walking, waiting, side of screen, etc)
     * 
     * @param actionC
     * @param movementC
     * @param transformC 
     */
    private void processActionTarget(ActionComponent actionC, MovementComponent movementC, TransformComponent transformC) {
        
        if(transformC.position.x <= -50) {
            actionC.set(TargetComponent.ACTION_LEFT_SCREEN);
        }
        if(transformC.position.x >= WIDTH_SCREEN_TOT) {
            actionC.set(TargetComponent.ACTION_RIGHT_SCREEN);
        }
        if(movementC.speed == 0.0f) {
            actionC.set(TargetComponent.ACTION_WAITING);
        }
        else {
            if(randDir == 0) {
                actionC.set(TargetComponent.ACTION_WALK_RIGHT);
            }
            else {
                actionC.set(TargetComponent.ACTION_WALK_LEFT);
            }
        }
        
    }
    
    private void processMovementTarget(ActionComponent actionC, MovementComponent movementC, TransformComponent transformC) {
        
        
        //movementC.speed = 2;
        
        //At the side of the gameworld (left or right)
        if (actionC.action == TargetComponent.ACTION_LEFT_SCREEN
                || actionC.action == TargetComponent.ACTION_RIGHT_SCREEN) {
            
            randWaitingTime = RandomComponent.rand.nextInt(3);
            
            //set the random position that the target will aim to stop and wait at
            positionToGo = RandomComponent.rand.nextInt(WIDTH_SCREEN_TOT - 2 * WIDTH_ZONE_SAFE - 2 * WIDTH_ZONE_SUSPICION) + WIDTH_ZONE_SAFE + WIDTH_ZONE_SUSPICION;
            actionC.time = 0.0f;
            movementC.speed = 2.0f;
            
        }
        
        //if the walk is to the right and the waiting position has been achieve
        if(actionC.action == TargetComponent.ACTION_WALK_RIGHT && transformC.position.x >= positionToGo
                ||
                //if the walk is to the left and the waiting position has been achieve
                actionC.action == TargetComponent.ACTION_WALK_LEFT && transformC.position.x <= positionToGo) {
            
            //is going to set the action to WAITING for which it's going to enter in the next if statement
            movementC.speed = 0.0f;
            
        }
        
        if (actionC.action == TargetComponent.ACTION_WAITING && actionC.time > randWaitingTime) {
            
            if(actionC.time > randWaitingTime) {
                randDir = RandomComponent.rand.nextInt(2);
                
                switch(randDir) {
                    case 0:
                        positionToGo = WIDTH_SCREEN_TOT;
                        break;
                    case 1:
                        positionToGo = -50;
                        break;
                }
            }
        }
        
        if(actionC.action == TargetComponent.ACTION_WALK_LEFT && transformC.position.x <= positionToGo
                //for a limited time
                && actionC.time > randWaitingTime) {
            actionC.time = 0.0f;
            movementC.speed = 0.0f;
        }
        
        /*
        if (actionC.time > randWaitingTime
                && (actionC.action == TargetComponent.ACTION_LEFT_SCREEN
                || actionC.action == TargetComponent.ACTION_RIGHT_SCREEN)) {

            //TO ADJUST DIFFICULTY
            randWaitingTime = RandomComponent.rand.nextInt(2
            /*(int) (50 + targetC.difficultyAddition)
                    - Math.max(15, (int) (targetC.suspicion_gauge+1) / 5 + 2)
            );

            //set the random positionthat the target will aim to stop
            randPositionTar = RandomComponent.rand.nextInt(WIDTH_SCREEN_TOT - 2 * WIDTH_ZONE_SAFE - 2 * WIDTH_ZONE_SUSPICION) + WIDTH_ZONE_SAFE + WIDTH_ZONE_SUSPICION;
            //setup the time that the AI will wait
            randTimeStay = RandomComponent.rand.nextInt(8); 
            
            randDir = RandomComponent.rand.nextInt(2);

            System.out.println("Wait: " + randWaitingTime);
            System.out.println("STAY: " + randTimeStay);
            System.out.println("Dir: " + randDir);
            if (actionC.action == TargetComponent.ACTION_LEFT_SCREEN) {
                
                movementC.speed = 2;
                actionC.action = TargetComponent.ACTION_WALK_RIGHT;

            } else if (actionC.action == TargetComponent.ACTION_RIGHT_SCREEN) {
                movementC.speed = -2;
                actionC.action = TargetComponent.ACTION_WALK_LEFT;
            }

            actionC.time = 0;

        } else {
            if ((transformC.position.x >= randPositionTar && movementC.speed > 0) || (transformC.position.x <= randPositionTar && movementC.speed < 0)) {
                movementC.speed = 0;
                if (actionC.action != TargetComponent.ACTION_WATCHING) {
                    actionC.action = TargetComponent.ACTION_WATCHING;
                }

                if (actionC.time >= randTimeStay) {
                    if (randDir == 0) {
                        movementC.speed = -2;
                    } else {
                        movementC.speed = 2;
                    }
                }

            }
            if (transformC.position.x > WIDTH_SCREEN_TOT) {
                if (actionC.action != TargetComponent.ACTION_RIGHT_SCREEN) {
                    actionC.action = TargetComponent.ACTION_RIGHT_SCREEN;
                }
                movementC.speed = 0;
            } else if (transformC.position.x < -50) {
                if (actionC.action != TargetComponent.ACTION_LEFT_SCREEN) {
                    actionC.action = TargetComponent.ACTION_LEFT_SCREEN;
                }
                movementC.speed = 0;
            }
        }
        */
        transformC.position.x += movementC.speed;
    }

}
