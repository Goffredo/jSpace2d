package base.graphics.engine.objects;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.jbox2d.common.Transform;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;

import base.graphics.engine.objects.common.Triangle;
import base.physics.engine.Atomic3Float;

public abstract class GameRenderable {

	protected FloatBuffer normalsBuffer;
	protected FloatBuffer trianglesBuffer;

	// protected Float[] verticesArray;
	// protected Float[] normalsArray;

	public Atomic3Float transform;

	public GameRenderable(ArrayList<Triangle> triangles, Atomic3Float transform) {
		setTriangles(triangles);
		this.transform = transform;
	}

	public abstract void render();

	/**
	 * @param triangles
	 *            the triangles to set
	 */
	public void setTriangles(ArrayList<Triangle> triangles) {

		trianglesBuffer = BufferUtils.createFloatBuffer(triangles.size() * 9); // 3
																				// components
																				// per
																				// vertex,
																				// 3
																				// vertices
																				// per
																				// triangle
		normalsBuffer = BufferUtils.createFloatBuffer(triangles.size() * 9); // 3
																				// components
																				// per
																				// normal,
																				// 1
																				// normal
																				// per
																				// vertex,
																				// 3
																				// vertices
																				// per
																				// triangle
		// trianglesBuffer =
		// ByteBuffer.allocateDirect(triangles.size()*4*9).asFloatBuffer();
		// normalsBuffer =
		// ByteBuffer.allocateDirect(triangles.size()*4*9).asFloatBuffer();
		// fill Buffers
		Vector3f[] faceVertices = null;
		Vector3f[] faceNormals = null;

		for (int i = 0; i < triangles.size(); i++) {

			faceVertices = triangles.get(i).vertices;
			faceNormals = triangles.get(i).normals;

			trianglesBuffer.put(faceVertices[0].x).put(faceVertices[0].y)
					.put(faceVertices[0].z); // vertex 1
			trianglesBuffer.put(faceVertices[1].x).put(faceVertices[1].y)
					.put(faceVertices[1].z); // vertex 2
			trianglesBuffer.put(faceVertices[2].x).put(faceVertices[2].y)
					.put(faceVertices[2].z); // vertex 3

			normalsBuffer.put(faceNormals[0].x).put(faceNormals[0].y)
					.put(faceNormals[0].z); // normal for vertex 1
			normalsBuffer.put(faceNormals[1].x).put(faceNormals[1].y)
					.put(faceNormals[1].z); // normal for vertex 2
			normalsBuffer.put(faceNormals[2].x).put(faceNormals[2].y)
					.put(faceNormals[2].z); // normal for vertex 3
		}

		// set ready to be read
		trianglesBuffer.flip();
		normalsBuffer.flip();

		// trianglesBuffer = trianglesBuffer.asReadOnlyBuffer();
		// normalsBuffer = normalsBuffer.asReadOnlyBuffer();

		/*
		 * verticesArray = new Float[triangles.size()*9]; normalsArray = new
		 * Float[triangles.size()*9];
		 * 
		 * //fill Buffers Vector3f[] faceVertices = null; Vector3f[] faceNormals
		 * = null;
		 * 
		 * for(int i = 0; i<triangles.size(); i++){
		 * 
		 * faceVertices = triangles.get(i).vertices; faceNormals =
		 * triangles.get(i).normals;
		 * 
		 * for(int j = 0; j<3; j++){ verticesArray[i*9+j*3] = faceVertices[j].x;
		 * verticesArray[i*9+j*3+1] = faceVertices[j].y;
		 * verticesArray[i*9+j*3+2] = faceVertices[j].z;
		 * 
		 * normalsArray[i*9+j*3] = faceNormals[j].x; normalsArray[i*9+j*3+1] =
		 * faceNormals[j].y; normalsArray[i*9+j*3+2] = faceNormals[j].z;
		 * 
		 * } }
		 */
	}

}
