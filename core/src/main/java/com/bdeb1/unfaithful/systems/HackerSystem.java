/*
 * Copyright 2018 Samuel Montambault.
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
import com.bdeb1.unfaithful.components.HackerComponent;
import com.bdeb1.unfaithful.components.LaptopComponent;
import com.bdeb1.unfaithful.components.TransformComponent;

/**
 * @author Samuel
 */
public class HackerSystem extends IteratingSystem {
    private ComponentMapper<HackerComponent> hackerM;
    private ComponentMapper<ActionComponent> actionM;
    private ComponentMapper<TransformComponent> transformM;

    
    private boolean isHacking = false;
    public HackerSystem() {
        super(Family.all(
                HackerComponent.class,
                ActionComponent.class,
                TransformComponent.class
        ).get());
        transformM = ComponentMapper.getFor(TransformComponent.class);
        hackerM = ComponentMapper.getFor(HackerComponent.class);
        actionM = ComponentMapper.getFor(ActionComponent.class);
        
        
    }
    public void setIsHacking(boolean b) {
        isHacking = b;
    }
    
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        transformM.get(entity).isHidden = false;
        HackerComponent hackerC = hackerM.get(entity);
        ActionComponent actionC = actionM.get(entity);
        
        // Test usage: System.out.println(deltaTime);
        if(isHacking) {
            actionC.set(HackerComponent.ACTION_HACKING);
            

            //Add constant later for difficult: HACK_MAX_GAUGE
            
            hackerC.hacking_gauge += deltaTime;
        }
        else{
            actionC.set(HackerComponent.ACTION_NOT_HACKING);
            
        }
        
    }
}
