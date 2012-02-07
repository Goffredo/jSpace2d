package base.graphics.engine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBBufferObject;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GLContext;

public class GPUManager {

	public static int createVBOID() {
		if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
			IntBuffer buffer = BufferUtils.createIntBuffer(1);
			ARBBufferObject.glGenBuffersARB(buffer);
			return buffer.get(0);
		}
		return 0;
	}

	public static void bufferData(int id, FloatBuffer buffer, int GLHint) {
		if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
			ARBBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, id);
			ARBBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, buffer, GLHint);
		}
	}

	public static void bufferElementData(int id, IntBuffer buffer, int GLHint) {
		if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
			ARBBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, id);
			ARBBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, buffer, GLHint);
		}
	}
}
