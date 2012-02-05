package base.graphics.engine.objects;

import base.graphics.engine.objectHelpers.Triangle;
import java.util.ArrayList;

import org.jbox2d.common.Transform;
import org.lwjgl.opengl.GL11;

public class ObjMesh extends GameRenderable{
	
	public ObjMesh(ArrayList<Triangle> triangles, Transform transform) {
		super(triangles, transform);
	}
	
	public ObjMesh(ArrayList<Triangle> triangles) {
		super(triangles, new Transform());
	}

	@Override
	public void render() {
		GL11.glPushMatrix();
		
		GL11.glBegin(GL11.GL_TRIANGLES);
		for(int i = 0; i < trianglesBuffer.limit()/3; i++){
			GL11.glNormal3f(normalsBuffer.get(), normalsBuffer.get(), normalsBuffer.get());
			GL11.glVertex3f(trianglesBuffer.get(), trianglesBuffer.get(), trianglesBuffer.get());
		}
		trianglesBuffer.rewind();
		normalsBuffer.rewind();
		GL11.glEnd();
		
		GL11.glPopMatrix();
	}

}
