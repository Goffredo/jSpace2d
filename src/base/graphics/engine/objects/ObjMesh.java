package base.graphics.engine.objects;

import base.graphics.engine.objectHelpers.Triangle;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.jbox2d.common.Transform;
import org.lwjgl.opengl.GL11;

public class ObjMesh extends GameRenderable{
	
	public ObjMesh(ArrayList<Triangle> triangles) {
		super(triangles, new Transform());
	}
	
	public ObjMesh(ArrayList<Triangle> triangles, Transform transform) {
		super(triangles, transform);
	}

	@Override
	public void render() {
		GL11.glPushMatrix();
		
		/*
		long time = System.nanoTime();
		FloatBuffer rotation = BufferUtils.createFloatBuffer(16);
		
		
		setRotationbuffer(rotation);
		GL11.glMultMatrix(rotation);
		System.out.println(System.nanoTime()-time);
		*/
		
		
		GL11.glTranslatef(transform.position.x, transform.position.y, 0.0f);
		GL11.glRotatef((float)Math.toDegrees(transform.getAngle()), 0, 0, 1);
		
		GL11.glBegin(GL11.GL_TRIANGLES);
		
		trianglesBuffer.rewind();
		normalsBuffer.rewind();
		for(int i = 0; i < trianglesBuffer.limit()/3; i++){
			GL11.glNormal3f(normalsBuffer.get(), normalsBuffer.get(), normalsBuffer.get());
			GL11.glVertex3f(trianglesBuffer.get(), trianglesBuffer.get(), trianglesBuffer.get());
		}		
		GL11.glEnd();
		
		GL11.glPopMatrix();
	}

	private void setRotationbuffer(FloatBuffer rotation) {
		rotation.put(transform.R.col1.x); //0
		rotation.put(transform.R.col1.y); //1
		rotation.put(0); //2
		rotation.put(0); //3
		
		rotation.put(transform.R.col2.x); //4
		rotation.put(transform.R.col2.y); //5
		rotation.put(0); //6
		rotation.put(0); //7
		
		rotation.put(0); //8
		rotation.put(0); //9
		rotation.put(1); //10
		rotation.put(0); //11
		
		rotation.put(0); //12
		rotation.put(0); //13
		rotation.put(0); //14
		rotation.put(1); //15
		rotation.flip();
	}

}
