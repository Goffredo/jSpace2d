package base.graphics.engine.main;

import base.graphics.engine.loaders.SimpleObjLoader;
import java.nio.FloatBuffer;
import java.nio.file.Paths;
import base.graphics.engine.objects.ObjMesh;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector2f;

public class GraphicEngine {

	private long lastFPS;
	private int fps;
	private Cube cube;
	private long lastFrame;
	private float angle;
	private ObjMesh testTorus;
	private FloatBuffer pos;

	/**
	 * Manages graphics.
	 * 
	 * @param width
	 *            the desired width of the window to be created
	 * @param height
	 *            the desired height of the window to be created
	 * @param fullscreen
	 *            true to enable, false not to
	 * @param vSync
	 *            true to enable, false not to
	 */

	public GraphicEngine(DisplayMode mode, boolean fullScreen, boolean vSync) {
		cube = new Cube(1.0f, new Vector2f(), 0.0f);
		testTorus = SimpleObjLoader.loadObjFromFile((Paths.get("Resources/suzanne.obj")));

		init(mode, fullScreen, vSync);

	}

	private void render(int delta) {
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GLU.gluLookAt(0.0f, 0.0f, 0, 0, 0, -30.0f, 0, 1, 0);
		GL11.glPushMatrix();
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, pos);
		GL11.glPopMatrix();
		//cube.render();
		testTorus.render();
		updateFPS();

	}

	/**
	 * Initialize the screen, camera and light.
	 * 
	 * @param width
	 *            the desired width of the window to be created
	 * @param height
	 *            the desired height of the window to be created
	 * @param fullscreen
	 *            true to enable, false not to
	 */

	// TODO separate camera in at least a different function, preferably in a
	// different class.

	private void init(DisplayMode mode, boolean fullScreen, boolean vSync) {

		try {
			Display.setDisplayMode(mode);
			Display.setFullscreen(fullScreen);
			Display.setVSyncEnabled(vSync);
			Display.create();

		} catch (Exception e) {
			System.out.println("Error setting up display");
			System.exit(0);
		}

		int width = Display.getDisplayMode().getWidth();
		int height = Display.getDisplayMode().getHeight();

		lastFPS = getTime(); // call before loop to initialise fps timer

		GL11.glEnable(GL11.GL_DEPTH_TEST);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(45.0f, (float) width / (float) height, 1, 100);

		float mat_specular[] = { 1.0f, 1.0f, 1.0f, 0.1f };
		float light_position[] = { 0.0f, 0.0f, -20.0f, 1.0f };
		GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		FloatBuffer spec = BufferUtils.createFloatBuffer(4).put(mat_specular);
		spec.flip();
		pos = BufferUtils.createFloatBuffer(4).put(light_position);
		pos.rewind();

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GLU.gluLookAt(0, 0, 0, 0, 0, -20.0f, 0, 1, 0);
		
		
		
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, pos);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_LIGHT0);
		GL11.glEnable(GL11.GL_DEPTH_TEST);

	}


	/**
	 * Get the time in milliseconds
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0; // reset the FPS counter
			lastFPS += 1000; // add one second
		}
		fps++;
	}

	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	public void start() {
		// TODO Auto-getDelta();

		while (!Display.isCloseRequested()) {
			int delta = getDelta();
			angle += delta / 10.f;
			if (angle >= 360.0)
				angle = 0;
			render(delta);
			cube.setPosition(new Vector2f((float)Math.sin(Math.toRadians(angle))*5.0f,(float)Math.cos(Math.toRadians(angle))*5.0f));
			cube.setRotation(angle);
			
			testTorus.setPosition(new Vector2f((float)Math.sin(Math.toRadians(angle))*5.0f,(float)Math.cos(Math.toRadians(angle))*5.0f));
			testTorus.setRotation(angle);
			Display.update();
                        Display.sync(60);
		}

		Display.destroy();

	}

}
