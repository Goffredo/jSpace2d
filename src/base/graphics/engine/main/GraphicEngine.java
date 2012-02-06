package base.graphics.engine.main;

import base.ActionManager;
import base.graphics.CreateGameRenderable;
import base.graphics.GraphicAction;
import base.graphics.RemoveGameRenderable;
import base.graphics.engine.loaders.SimpleObjLoader;
import java.nio.FloatBuffer;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import base.graphics.engine.objects.GameRenderable;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

public class GraphicEngine implements Runnable{

	private int fps;
	private boolean fullScreen;
	private long lastFPS;
	private long lastFrame;
	private ActionManager manager;
	private DisplayMode mode;
	private FloatBuffer pos;
	private HashMap<Integer,GameRenderable> toDraw = new HashMap<Integer,GameRenderable>();
	private ArrayList<GraphicAction> toProcess = new ArrayList<GraphicAction>();
	private boolean vSync;
	private Vector3f cameraPos = new Vector3f(20, 50, 130);

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

	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
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
		GLU.gluPerspective(45.0f, (float) width / (float) height, 1, 1000);

		float mat_specular[] = { 1.0f, 1.0f, 1.0f, 0.1f };
		float light_position[] = { 0.0f, 1.0f, 1.0f, 0.0f };
		GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		FloatBuffer spec = BufferUtils.createFloatBuffer(4).put(mat_specular);
		spec.flip();
		pos = BufferUtils.createFloatBuffer(4).put(light_position);
		pos.rewind();

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GLU.gluLookAt(cameraPos.x, cameraPos.y, cameraPos.z, 0, 0, -1, 0, 1, 0);

		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, pos);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_LIGHT0);
		GL11.glEnable(GL11.GL_DEPTH_TEST);

	}

	private void processActions() {
		toProcess = manager.getGraphicActions();
		for(GraphicAction action : toProcess){

			switch(action.actionType){

			case	CREATE:
				GameRenderable temp = toDraw.get(((CreateGameRenderable) action).iD);

				if(temp==null){
					System.out.println("Creating graphical object! ID: "+((CreateGameRenderable) action).iD);
					GameRenderable graphicalObject = SimpleObjLoader.loadObjFromFile(Paths.get("Resources/Objects/emptyCube.obj"));
					graphicalObject.transform = ((CreateGameRenderable) action).positionInfo;
					toDraw.put(((CreateGameRenderable) action).iD, graphicalObject);
				}else{
					try {
						throw new Exception("Object ID already present!");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.exit(0);
					}
				}
				break;

			case	REMOVE:
				temp = toDraw.get(((RemoveGameRenderable) action).iD);

				if(temp!=null){
					System.out.println("Removing a suzanne! ID: "+((RemoveGameRenderable) action).iD);
					toDraw.remove(((RemoveGameRenderable) action).iD);
				}else{
					try {
						throw new Exception("Object ID does not exist!");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.exit(0);
					}
				}
			}
		}
	}

	private void render() {
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, pos);

		for(GameRenderable renderable : toDraw.values()){
			
			renderable.render();
			
		}
		GL11.glFlush();
		GL11.glFinish();

		updateFPS();
	}

	@Override
	public void run() {
		
		init(mode, fullScreen, vSync);

		while (!Display.isCloseRequested()) {
			
			processActions();
			
			render();
			
			
			//long timer = System.nanoTime();
			Display.update();
			//System.out.println("render: "+(System.nanoTime()-timer)/1000000);
			//Display.sync(60);
		}

		Display.destroy();

	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			System.out.println("fps: "+fps);
			fps = 0; // reset the FPS counter
			lastFPS += 1000; // add one second
		}
		fps++;
	}

}
