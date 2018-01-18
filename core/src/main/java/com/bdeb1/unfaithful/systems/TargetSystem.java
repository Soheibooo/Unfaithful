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

        //Out of screen
        //ZONE SAFE
        if (transformC.position.x < WIDTH_ZONE_SAFE || transformC.position.x >= WIDTH_SCREEN_TOT - WIDTH_ZONE_SAFE - 50) {
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
        } else { //Zone de Danger tres radioactive
            targetC.suspicion_gauge = 100f;
        }

        if (targetC.suspicion_gauge < TargetComponent.TRIGGER_POINT_SUSPICIOUS) {
            stateC.state = TargetComponent.STATE_UNSUSPICIOUS;

        } else if (targetC.suspicion_gauge >= TargetComponent.TRIGGER_POINT_SUSPICIOUS && targetC.suspicion_gauge < TargetComponent.TRIGGER_POINT_FRENZY) {
            stateC.state = TargetComponent.STATE_SUSPICIOUS;

        } else if (targetC.suspicion_gauge >= TargetComponent.TRIGGER_POINT_FRENZY && targetC.suspicion_gauge < TargetComponent.TRIGGER_POINT_DONE) {
            stateC.state = TargetComponent.STATE_FRENZY;

        } else {
            stateC.state = TargetComponent.STATE_DONE;
        }

        //System.out.println(actionC.time);
        if (actionC.time > randWaitingTime
                && (actionC.action == TargetComponent.ACTION_LEFT_SCREEN
                || actionC.action == TargetComponent.ACTION_RIGHT_SCREEN)) {

            //TO ADJUST DIFFICULTY
            randWaitingTime = RandomComponent.rand.nextInt(2
            /*(int) (50 + targetC.difficultyAddition)
                    - Math.max(15, (int) (targetC.suspicion_gauge+1) / 5 + 2)*/
            );

            randPositionTar = RandomComponent.rand.nextInt(WIDTH_SCREEN_TOT - 2 * WIDTH_ZONE_SAFE - 2 * WIDTH_ZONE_SUSPICION) + WIDTH_ZONE_SAFE + WIDTH_ZONE_SUSPICION;
            randTimeStay = RandomComponent.rand.nextInt(8);
            randDir = RandomComponent.rand.nextInt(2);

            System.out.println("Wait: " + randWaitingTime);
            System.out.println("STAY: " + randTimeStay);
            System.out.println("Dir: " + randDir);
            if (actionC.action == TargetComponent.ACTION_LEFT_SCREEN) {
                System.out.println("Allo");
                movementC.speed = 5;
                actionC.action = TargetComponent.ACTION_WALK_RIGHT;

            } else if (actionC.action == TargetComponent.ACTION_RIGHT_SCREEN) {
                movementC.speed = -5;
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
                        movementC.speed = -5;
                    } else {
                        movementC.speed = 5;
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
    }

}
