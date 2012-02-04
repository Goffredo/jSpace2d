package base.graphics.engine.main;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

class Cube {

	private float halfSide;
	private Vector2f position;
	private float rotation;
	private boolean useVBO = false;

	/**
	 * A basic cube, fixed on z=0.
	 * 
	 * @param halfSide
	 *            half the side of the cube.
	 * @param position
	 *            position of the cube on the z=0 plane (x,y)
	 * @param rotation
	 *            rotation around the z axis
	 */

	public Cube(float halfSide, Vector2f position, float rotation) {
		setHalfSide(halfSide);
		setPosition(position);
		setRotation(rotation);
	}

	/**
	 * render the cube.
	 */
	public void render() {

		if (useVBO) {

		} else {

			GL11.glPushMatrix();
			
			GL11.glTranslatef(position.x, position.y, -20.0f);
			GL11.glRotatef(rotation, 0, 0, 1);
			//GL11.glRotatef(45, 1, 0, 1);

			GL11.glBegin(GL11.GL_QUADS);
			GL11.glNormal3d(0, 0, -1);
			GL11.glVertex3f(-halfSide, -halfSide, halfSide);
			GL11.glVertex3f(-halfSide, halfSide, halfSide);
			GL11.glVertex3f(halfSide, halfSide, halfSide);
			GL11.glVertex3f(halfSide, -halfSide, halfSide);
			GL11.glEnd();

			GL11.glBegin(GL11.GL_QUADS);
			GL11.glNormal3d(0, 0, -1);
			GL11.glVertex3f(-halfSide, -halfSide, -halfSide);
			GL11.glVertex3f(-halfSide, halfSide, -halfSide);
			GL11.glVertex3f(halfSide, halfSide, -halfSide);
			GL11.glVertex3f(halfSide, -halfSide, -halfSide);
			GL11.glEnd();
			
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glNormal3d(0, 1, 0);
			GL11.glVertex3f(-halfSide, halfSide, -halfSide);
			GL11.glVertex3f(-halfSide, halfSide, halfSide);
			GL11.glVertex3f(halfSide, halfSide, halfSide);
			GL11.glVertex3f(halfSide, halfSide, -halfSide);
			GL11.glEnd();

			GL11.glBegin(GL11.GL_QUADS);
			GL11.glNormal3d(0, -1, 0);
			GL11.glVertex3f(-halfSide, -halfSide, -halfSide);
			GL11.glVertex3f(-halfSide, -halfSide, halfSide);
			GL11.glVertex3f(halfSide, -halfSide, halfSide);
			GL11.glVertex3f(halfSide, -halfSide, -halfSide);
			GL11.glEnd();

			GL11.glBegin(GL11.GL_QUADS);
			GL11.glNormal3d(1, 0, 0);
			GL11.glVertex3f(halfSide, -halfSide, -halfSide);
			GL11.glVertex3f(halfSide, -halfSide, halfSide);
			GL11.glVertex3f(halfSide, halfSide, halfSide);
			GL11.glVertex3f(halfSide, halfSide, -halfSide);
			GL11.glEnd();

			GL11.glBegin(GL11.GL_QUADS);
			GL11.glNormal3d(-1, 0, 0);
			GL11.glVertex3f(-halfSide, -halfSide, -halfSide);
			GL11.glVertex3f(-halfSide, -halfSide, halfSide);
			GL11.glVertex3f(-halfSide, halfSide, halfSide);
			GL11.glVertex3f(-halfSide, halfSide, -halfSide);
			GL11.glEnd();
			GL11.glPopMatrix();

		}
	}

	/**
	 * @return the halfSide
	 */
	public float getHalfSide() {
		return halfSide;
	}

	/**
	 * @param halfSide
	 *            the halfSide to set
	 */
	public void setHalfSide(float halfSide) {
		this.halfSide = halfSide;
	}

	/**
	 * @return the position
	 */
	public Vector2f getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
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
	 * @param rotation
	 *            the rotation to set
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

}
