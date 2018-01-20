/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdeb1.unfaithful.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.bdeb1.unfaithful.components.ActionComponent;
import com.bdeb1.unfaithful.components.LaptopComponent;
import com.bdeb1.unfaithful.components.TransformComponent;

/**
 *
 * @author Samuel
 */
public class LaptopSystem extends IteratingSystem {

    private ComponentMapper<ActionComponent> actionM;
    private ComponentMapper<TransformComponent> transformM;
    private ComponentMapper<LaptopComponent> laptopM;

    private boolean isHacking = false;

    public LaptopSystem() {
        super(Family.all(
                LaptopComponent.class,
                ActionComponent.class,
                TransformComponent.class
        ).get());
        transformM = ComponentMapper.getFor(TransformComponent.class);
        laptopM = ComponentMapper.getFor(LaptopComponent.class);
        actionM = ComponentMapper.getFor(ActionComponent.class);

    }

    public void setIsHacking(boolean b) {
        isHacking = b;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        transformM.get(entity).isHidden = false;
        LaptopComponent laptopC = laptopM.get(entity);
        ActionComponent actionC = actionM.get(entity);

        // Test usage: System.out.println(deltaTime);
        if (isHacking) {
            actionC.set(laptopC.ACTION_HACKING);

        } else {
            actionC.set(laptopC.ACTION_NOT_HACKING);
        }

    }

}
