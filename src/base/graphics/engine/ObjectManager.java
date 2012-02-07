package base.graphics.engine;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.lwjgl.opengl.ARBBufferObject;
import org.lwjgl.opengl.ARBVertexBufferObject;

import base.graphics.engine.objects.RAMRenderable;
import base.graphics.engine.objects.VBORenderable;
import base.graphics.engine.objects.common.Mesh;
import base.graphics.engine.objects.common.Triangle;
import base.graphics.engine.objects.loaders.SimpleObjLoader;
import base.physics.engine.Atomic3Float;

public class ObjectManager {

	private Mesh[] models;
	private Path modelLocation;

	public ObjectManager(Path modelLocation) {
		this.modelLocation = modelLocation;
		init();
	}

	private void init() {
		ArrayList<Path> modelPaths = searchForModels();
		System.out.println("size " + modelPaths.size());
		models = new Mesh[modelPaths.size()];

		int modelID = 0;
		Mesh temp;
		for (Path pathToModel : modelPaths) {
			temp = loadMesh(pathToModel);
			models[modelID] = temp;
			modelID++;
		}
	}

	private Mesh loadMesh(Path pathToModel) {
		System.out.println("loading: " + pathToModel.toString());
		ArrayList<Triangle> triangles = SimpleObjLoader.loadGeometry(pathToModel);

		System.out.println("loaded: " + pathToModel.getFileName().toString() + " with " + triangles.size() + " triangles");

		Mesh out = new Mesh(triangles, pathToModel.getFileName().toString());
		return out;
	}

	private ArrayList<Path> searchForModels() {
		ArrayList<Path> out = new ArrayList<>();

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(modelLocation)) {
			for (Path file : stream) {
				String temp = file.getFileName().toString();
				if (temp.endsWith(".obj")) {
					out.add(file);
				}
			}
		} catch (IOException | DirectoryIteratorException x) {
			// IOException can never be thrown
			// by the iteration. In this snippet,
			// it can only be thrown by
			// newDirectoryStream.
			System.err.println(x);
		}

		return out;
	}

	// TODO request by name?
	public RAMRenderable requestRAMMesh(int modelIndex, Atomic3Float transform) {
		Mesh temp = models[modelIndex];
		RAMRenderable out = new RAMRenderable(temp.verticesBuffer, temp.normalsBuffer, temp.interleavedBuffer, transform);
		return out;
	}

	// TODO request by name?
	public VBORenderable requestVBOMesh(int modelIndex, Atomic3Float transform) {
		Mesh temp = models[modelIndex];
		int vertexVBOID = GPUManager.createVBOID();
		temp.verticesBuffer.rewind();
		GPUManager.bufferData(vertexVBOID, temp.verticesBuffer, ARBBufferObject.GL_STATIC_DRAW_ARB);
		int normalVBOID = GPUManager.createVBOID();
		temp.normalsBuffer.rewind();
		GPUManager.bufferData(normalVBOID, temp.normalsBuffer, ARBBufferObject.GL_STATIC_DRAW_ARB);
		VBORenderable out = new VBORenderable(vertexVBOID, normalVBOID, temp.triangles.size(), transform);
		return out;
	}

}
