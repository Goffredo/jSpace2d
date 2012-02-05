package base.graphics.engine.main;

import base.ActionManager;
import base.graphics.CreateGameRenderable;
import base.graphics.CreateGameRenderable_dummy;
import base.graphics.GraphicAction;
import base.graphics.engine.loaders.SimpleObjLoader;
import java.nio.FloatBuffer;
import java.nio.file.Paths;
import java.util.ArrayList;

import base.graphics.engine.objects.GameRenderable;
import base.graphics.engine.objects.ObjMesh;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector2f;

public class GraphicEngine implements Runnable{

	private long lastFPS;
	private int fps;
	private long lastFrame;
	private float angle;
	private FloatBuffer pos;
	private DisplayMode mode;
	private boolean fullScreen;
	private boolean vSync;
	private ActionManager manager;
	private ArrayList<GraphicAction> toProcess = new ArrayList<GraphicAction>();
	private ArrayList<GameRenderable> toDraw = new ArrayList<GameRenderable>();
	
	/**
	 * Manages graphics.
	 * 
	 * @param mode
	 * 			  DisplayMode to set
	 * @param fullscreen
	 *            true to enable, false not to
	 * @param vSync
	 *            true to enable, false not to
	 */

	public GraphicEngine(DisplayMode mode, boolean fullScreen, boolean vSync, ActionManager manager) {
		this.mode = mode;
		this.fullScreen = fullScreen;
		this.vSync = vSync;
		this.manager = manager;
	}

	private void render(int delta) {
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, pos);
		
		for(GameRenderable renderable : toDraw){
			renderable.render();
		}
		
		updateFPS();
	}

	/**
	 * Initialize the screen, camera and light.
	 * 
	 * @param mode
	 * 			  DisplayMode to set
	 * @param fullscreen
	 *            true to enable, false not to
	 * @param vSync
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
		float light_position[] = { 0.0f, -1.0f, 0.5f, 0.0f };
		GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		FloatBuffer spec = BufferUtils.createFloatBuffer(4).put(mat_specular);
		spec.flip();
		pos = BufferUtils.createFloatBuffer(4).put(light_position);
		pos.rewind();

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GLU.gluLookAt(0, 0, 5, 0, 0, -1, 0, 1, 0);
		
		
		
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

	@Override
	public void run() {
		// TODO Auto-getDelta();


		init(mode, fullScreen, vSync);
		
				while (!Display.isCloseRequested()) {
					int delta = getDelta();
					angle += delta / 10.f;
					if (angle >= 360.0f)
						angle = 0;
					
					processActions();
					render(delta);
					Display.update();					
				}

				Display.destroy();
		
	}

	private void processActions() {
		toProcess = manager.getGraphicActions();
		for(GraphicAction action : toProcess){
			if(action instanceof CreateGameRenderable){
				System.out.println("we have an action!");
			}else{
				if(action instanceof CreateGameRenderable_dummy){
					System.out.println("Creating new suzanne!!");
					toDraw.add(SimpleObjLoader.loadObjFromFile(Paths.get("Resources/Objects/suzanne.obj")));
				}
			}
		}
	}

}
