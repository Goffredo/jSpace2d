package base.graphics.engine.objects;

import java.util.concurrent.atomic.AtomicIntegerArray;

public abstract class GameRenderable {

	public AtomicIntegerArray transform;

	public GameRenderable(AtomicIntegerArray transform) {
		this.transform = transform;
	}

	public abstract void render();

	public abstract void renderInterleavedDrawArray();

}
