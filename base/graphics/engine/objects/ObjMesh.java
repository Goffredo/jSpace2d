package base.graphics.engine.objects;

import base.graphics.engine.objectHelpers.Triangle;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

public class ObjMesh extends GameRenderable{
	
	public ObjMesh(ArrayList<Triangle> triangles) {
		super(triangles);
	}

	@Override
	public void render() {
		GL11.glPushMatrix();
		GL11.glTranslatef(position.x, position.y, -20.0f);
		GL11.glRotatef(rotation, 0, 0, 1);
		GL11.glRotatef(90.0f, 0, 1, 0);
		
		GL11.glBegin(GL11.GL_TRIANGLES);
		for(Triangle triangle : triangles){
			GL11.glNormal3f(triangle.normals[0].x, triangle.normals[0].y, triangle.normals[0].z);
			GL11.glVertex3f(triangle.vertices[0].x, triangle.vertices[0].y, triangle.vertices[0].z);
			GL11.glNormal3f(triangle.normals[1].x, triangle.normals[1].y, triangle.normals[1].z);
			GL11.glVertex3f(triangle.vertices[1].x, triangle.vertices[1].y, triangle.vertices[1].z);
			GL11.glNormal3f(triangle.normals[2].x, triangle.normals[2].y, triangle.normals[2].z);
			GL11.glVertex3f(triangle.vertices[2].x, triangle.vertices[2].y, triangle.vertices[2].z);
		}
		GL11.glEnd();
		
		GL11.glPopMatrix();
	}

}
