package base.graphics.engine.objects;

import base.graphics.engine.objectHelpers.Triangle;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector2f;

public abstract class GameRenderable {

	protected Vector2f position;
	protected float rotation;
	protected ArrayList<Triangle> triangles;

	public GameRenderable(ArrayList<Triangle> triangles){
		setTriangles(triangles);
		setPosition(new Vector2f(0.0f, 0.0f));
		setRotation(0.0f);
	}	

	/**
	 * @return the position
	 */
	public Vector2f getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	/**
	 * @return the rotation
	 */
	public float getRotation() {
		return rotation;
	}
	/**
	 * @param rotation the rotation to set
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	/**
	 * @return the triangles
	 */
	public ArrayList<Triangle> getTriangles() {
		return triangles;
	}

	/**
	 * @param triangles the triangles to set
	 */
	public void setTriangles(ArrayList<Triangle> triangles) {
		this.triangles = triangles;
	}

	public abstract void render();

}
