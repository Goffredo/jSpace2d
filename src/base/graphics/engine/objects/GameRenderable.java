package base.graphics.engine.objects;

import base.physics.engine.Atomic3Float;
import java.util.concurrent.atomic.AtomicIntegerArray;


public abstract class GameRenderable {

	public Atomic3Float transform;

	public GameRenderable(Atomic3Float transform) {
		this.transform = transform;
	}

	public abstract void render();

	public abstract void renderInterleavedDrawArray();

}
