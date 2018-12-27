package com.bdeb1.unfaithful.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.bdeb1.unfaithful.components.*;

public class MenuSystem extends IteratingSystem {

    private ComponentMapper<MenuComponent> menuM;
    private ComponentMapper<StateComponent> stateM;
    private ComponentMapper<TransformComponent> transformM;
    private ComponentMapper<AnimationComponent> animationM;
    private ComponentMapper<TextureComponent> textureM;

    public MenuSystem() {
        super(Family.all(
                MenuComponent.class,
                StateComponent.class,
                TransformComponent.class
        ).get());
        menuM = ComponentMapper.getFor(MenuComponent.class);
        stateM = ComponentMapper.getFor(StateComponent.class);
        transformM = ComponentMapper.getFor(TransformComponent.class);
        animationM = ComponentMapper.getFor(AnimationComponent.class);
        textureM = ComponentMapper.getFor(TextureComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MenuComponent menuC = menuM.get(entity);
        StateComponent stateC = stateM.get(entity);
        TransformComponent transformC = transformM.get(entity);
    }
}
