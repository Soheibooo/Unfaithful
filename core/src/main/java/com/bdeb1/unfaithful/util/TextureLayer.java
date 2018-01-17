package com.bdeb1.unfaithful.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import java.awt.Rectangle;

public class TextureLayer extends Layer {

	private Texture   texture;
	private Rectangle drawingBounds;

	public TextureLayer (Texture texture) {
		this.texture = texture;
		drawingBounds = new Rectangle (0, 0, texture.getWidth (),
		                               texture.getHeight ());
	}

	public void setDrawingBounds (int x, int y, int width, int height) {
		drawingBounds.setBounds (x, y, width, height);
	}

	@Override
	public void draw (Batch batch) {
		batch.draw (texture, drawingBounds.x, drawingBounds.y,
		            drawingBounds.width, drawingBounds.height);
	}

	@Override
	public void dispose() {
		texture.dispose ();
	}
}
