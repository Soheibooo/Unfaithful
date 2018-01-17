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
import com.bdeb1.unfaithful.components.ActionComponent;

/**
 *
 * @author Soheib El-Harrache
 */
public class ActionSystem extends IteratingSystem {
    private ComponentMapper<ActionComponent> actionC;

    public ActionSystem() {
        super(Family.all(
                ActionComponent.class
        ).get());

        actionC = ComponentMapper.getFor(ActionComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        actionC.get(entity).time += deltaTime;
    }
}
