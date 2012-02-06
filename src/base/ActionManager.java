package base;

import java.util.ArrayList;

import base.graphics.GraphicAction;
import base.physic.PhysicsAction;

/**
 * 
 * @author mauro
 */
public class ActionManager {

	ArrayList<GraphicAction> graphicActions = new ArrayList<GraphicAction>();
	/*
	 * GRAPHICS
	 */
	private final Object graphicLock = new Object();

	ArrayList<PhysicsAction> physicActions = new ArrayList<PhysicsAction>();

	/*
	 * PHYSIC
	 */
	private final Object physicLock = new Object();

	public void addGraphicsAction(GraphicAction a) {
		synchronized (graphicLock) {
			graphicActions.add(a);
		}
	}

	public void addPhysicAction(PhysicsAction a) {
		synchronized (physicLock) {
			physicActions.add(a);
		}
	}

	public ArrayList<GraphicAction> getGraphicActions() {
		ArrayList<GraphicAction> tmpActions = new ArrayList<>();
		ArrayList<GraphicAction> returnActions = graphicActions;
		synchronized (graphicLock) {
			graphicActions = tmpActions;
		}
		return returnActions;
	}

	public ArrayList<PhysicsAction> getPhysicActions() {
		ArrayList<PhysicsAction> tmpActions = new ArrayList<>();
		ArrayList<PhysicsAction> returnActions = physicActions;
		synchronized (physicLock) {
			physicActions = tmpActions;
		}
		return returnActions;
	}

}
