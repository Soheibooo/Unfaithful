package com.bdeb1.unfaithful.util;

import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class Layer {

    public abstract void draw(Batch batch);

    public abstract void dispose();
}
