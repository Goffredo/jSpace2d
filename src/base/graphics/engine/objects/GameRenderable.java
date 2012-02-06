package base.graphics.engine.objects;

import base.graphics.engine.objectHelpers.Triangle;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.jbox2d.common.Transform;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;

public abstract class GameRenderable {
	
	protected FloatBuffer normalsBuffer;
	public Transform transform;
	protected FloatBuffer trianglesBuffer;
	
	public GameRenderable(ArrayList<Triangle> triangles, Transform transform){
		setTriangles(triangles);
		this.transform = transform;
	}

	public abstract void render();

	/**
	 * @param triangles the triangles to set
	 */
	public void setTriangles(ArrayList<Triangle> triangles) {
		trianglesBuffer = BufferUtils.createFloatBuffer(triangles.size()*9); //3 components per vertex, 3 vertices per triangle
		normalsBuffer = BufferUtils.createFloatBuffer(triangles.size()*9); //3 components per normal, 1 normal per vertex, 3 vertices per triangle
		
		//fill Buffers
		Vector3f[] faceVertices = null;
		Vector3f[] faceNormals = null;
		
		for(int i = 0; i<triangles.size(); i++){
			
			faceVertices = triangles.get(i).vertices;
			faceNormals = triangles.get(i).normals;
			
			trianglesBuffer.put(faceVertices[0].x).put(faceVertices[0].y).put(faceVertices[0].z); //vertex 1
			trianglesBuffer.put(faceVertices[1].x).put(faceVertices[1].y).put(faceVertices[1].z); //vertex 2
			trianglesBuffer.put(faceVertices[2].x).put(faceVertices[2].y).put(faceVertices[2].z); //vertex 3
			

			normalsBuffer.put(faceNormals[0].x).put(faceNormals[0].y).put(faceNormals[0].z); //normal for vertex 1
			normalsBuffer.put(faceNormals[1].x).put(faceNormals[1].y).put(faceNormals[1].z); //normal for vertex 2
			normalsBuffer.put(faceNormals[2].x).put(faceNormals[2].y).put(faceNormals[2].z); //normal for vertex 3
		}
		
		//set ready to be read
		trianglesBuffer.flip();
		normalsBuffer.flip();
		
	}

}
