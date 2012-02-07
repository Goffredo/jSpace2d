package base.graphics.engine.objects;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.jbox2d.common.Transform;
import org.lwjgl.opengl.GL11;

import base.graphics.engine.objects.common.Triangle;

public class ObjMesh extends GameRenderable {

	public ObjMesh(ArrayList<Triangle> triangles) {
		super(triangles, new AtomicIntegerArray(3));
	}

	public ObjMesh(ArrayList<Triangle> triangles, AtomicIntegerArray transform) {
		super(triangles, transform);
	}

	@Override
	public void render() {
		GL11.glPushMatrix();

		/*
		 * long time = System.nanoTime(); FloatBuffer rotation =
		 * BufferUtils.createFloatBuffer(16);
		 * 
		 * 
		 * setRotationbuffer(rotation); GL11.glMultMatrix(rotation);
		 * System.out.println(System.nanoTime()-time);
		 */

		// long timer = System.nanoTime();

		trianglesBuffer.rewind();

		GL11.glTranslatef(Float.intBitsToFloat(transform.get(0)), Float.intBitsToFloat(transform.get(1)), 0.0f);
		GL11.glRotatef((float) Math.toDegrees(Float.intBitsToFloat(transform.get(2))), 0, 0, 1);

		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glVertexPointer(3, 0, trianglesBuffer);
		GL11.glNormalPointer(0, normalsBuffer);

		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, trianglesBuffer.capacity() / 3);

		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);

		GL11.glPopMatrix();
	}
/*
	private void setRotationbuffer(FloatBuffer rotation) {
		rotation.put(transform.R.col1.x); // 0
		rotation.put(transform.R.col1.y); // 1
		rotation.put(0); // 2
		rotation.put(0); // 3

		rotation.put(transform.R.col2.x); // 4
		rotation.put(transform.R.col2.y); // 5
		rotation.put(0); // 6
		rotation.put(0); // 7

		rotation.put(0); // 8
		rotation.put(0); // 9
		rotation.put(1); // 10
		rotation.put(0); // 11

		rotation.put(0); // 12
		rotation.put(0); // 13
		rotation.put(0); // 14
		rotation.put(1); // 15
		rotation.flip();
	}
*/
}
