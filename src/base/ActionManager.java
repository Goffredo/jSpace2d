package base;

import java.util.ArrayList;

import base.graphics.actions.GraphicAction;
import base.network.NetworkAction;
import base.physics.PhysicsAction;
import base.server.ServerAction;

/**
 * 
 * @author mauro
 */
public class ActionManager {

	/*
	 * GRAPHICS
	 */
	private final Object graphicLock = new Object();
	ArrayList<GraphicAction> graphicActions = new ArrayList<>();
	/*
	 * PHYSIC
	 */
	private final Object physicLock = new Object();
	ArrayList<PhysicsAction> physicActions = new ArrayList<>();

	/*
	 * SERVER
	 */
	private final Object serverLock = new Object();
	ArrayList<ServerAction> serverActions = new ArrayList<>();

	/*
	 * NETWORK
	 */
	private final Object networkLock = new Object();
	ArrayList<NetworkAction> networkActions = new ArrayList<>();

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

	public void addServerAction(ServerAction tempB) {
		synchronized (serverLock) {
			serverActions.add(tempB);
		}
	}

	public ArrayList<ServerAction> getServerActions() {
		ArrayList<ServerAction> tmpActions = new ArrayList<>();
		ArrayList<ServerAction> returnActions = serverActions;
		synchronized (serverLock) {
			serverActions = tmpActions;
		}
		return returnActions;
	}

	public void addNetworkAction(NetworkAction tempC) {
		synchronized (networkLock) {
			networkActions.add(tempC);
		}
	}

	public ArrayList<NetworkAction> getNetworkActions() {
		ArrayList<NetworkAction> tmpActions = new ArrayList<>();
		ArrayList<NetworkAction> returnActions = networkActions;
		synchronized (networkLock) {
			networkActions = tmpActions;
		}
		return returnActions;
	}
}
