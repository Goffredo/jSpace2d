package base.physics.engine;

import base.ActionManager;
import base.graphics.actions.CreateGameRenderable;
import base.graphics.actions.RemoveGameRenderable;
import base.physics.NewBodyAction;
import base.physics.NewBodyActionServer;
import base.physics.PhysicsAction;
import base.physics.RemoveBodyAction;
import base.server.NewEntityInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

/**
 *
 * @author mauro
 */
public class PhysicsManager {

	private static final float TIMESTEP = 0.0125f;
	ActionManager actionManager;
	private int actualTurn = 0;
	float maxX = 50;
	float maxY = 50;
	float minX = -50;
	float minY = -50;
	World physicWorld;
	TreeMap<Integer, Body> sortedOggetto2D = new TreeMap<>();
	HashMap<Integer, AtomicIntegerArray> transformsForGraphics = new HashMap<>();
	
	public PhysicsManager(ActionManager aM) {
		actionManager = aM;
		Vec2 worldGravity = new Vec2(0.0f, -5.0f);
		physicWorld = new World(worldGravity, true);
		System.out.println("created world");
		createBorder();
	}

	private AtomicIntegerArray addBody(NewBodyAction t) {

		Body body = physicWorld.createBody(t.getBodyDef());
		if (body != null) {
			body.createFixture(t.getFixitureDef());

			int IDvalue = physicWorld.getBodyCount() + 1;
			t.IDbody = new Integer(IDvalue);
			final AtomicIntegerArray toGraphics = new AtomicIntegerArray(3);		
			toGraphics.set(0, Float.floatToIntBits(body.getPosition().x));
			toGraphics.set(1, Float.floatToIntBits(body.getPosition().y));
			toGraphics.set(2, Float.floatToIntBits(body.getAngle()));
			sortedOggetto2D.put(t.getID(), body);
			transformsForGraphics.put(t.getID(), toGraphics);
			System.out.println("New element in world! ID:" + t.getID());
			System.out.println("elements in world:"
					+ physicWorld.getBodyCount());
			return toGraphics;
		}
		return null;
	}

	private void createBorder() {

		{ // Up wall
			FixtureDef fd = new FixtureDef();
			PolygonShape sd = new PolygonShape();
			sd.setAsBox(maxX - minX, 1);
			fd.shape = sd;

			BodyDef bd = new BodyDef();
			bd.position = new Vec2(0, maxY);
			bd.type = BodyType.STATIC;
			physicWorld.createBody(bd).createFixture(fd);

		}

		{ // Down wall
			FixtureDef fd = new FixtureDef();
			PolygonShape sd = new PolygonShape();
			sd.setAsBox(maxX - minX, 1);
			fd.shape = sd;

			BodyDef bd = new BodyDef();
			bd.position = new Vec2(0, minY);
			bd.type = BodyType.STATIC;
			physicWorld.createBody(bd).createFixture(fd);

		}

		{ // Left wall
			FixtureDef fd = new FixtureDef();
			PolygonShape sd = new PolygonShape();
			sd.setAsBox(1, maxY - minY);
			fd.shape = sd;

			BodyDef bd = new BodyDef();
			bd.position = new Vec2(minX, 0);
			bd.type = BodyType.STATIC;
			physicWorld.createBody(bd).createFixture(fd);

		}

		{ // Right wall
			FixtureDef fd = new FixtureDef();
			PolygonShape sd = new PolygonShape();
			sd.setAsBox(1, maxY - minY);
			fd.shape = sd;

			BodyDef bd = new BodyDef();
			bd.position = new Vec2(maxX, 0);
			bd.type = BodyType.STATIC;
			physicWorld.createBody(bd).createFixture(fd);

		}

	}

	private void removeBody(int id) {
		Body deletedBody = sortedOggetto2D.remove(id);
		if (deletedBody != null) {
			physicWorld.destroyBody(deletedBody);
			System.out.println("Removed element in world! ID:" + id);
		}
		System.out.println("Failed to remove element in world! ID:" + id);

	}

	public void update() throws Exception {
		ArrayList<PhysicsAction> myAction = actionManager.getPhysicActions();
		for (PhysicsAction t : myAction) {
			if (t instanceof NewBodyActionServer) {
				NewBodyActionServer actNew = (NewBodyActionServer) t;
				AtomicIntegerArray positionInfo = addBody(actNew);
				if (positionInfo != null) {
					CreateGameRenderable tempA = new CreateGameRenderable(actNew.getID(), actNew.getModelID(), positionInfo);
					actionManager.addGraphicsAction(tempA);
					NewEntityInfo tempB = new NewEntityInfo(actNew.getID(), actNew.getModelID(), positionInfo, actualTurn);
					actionManager.addServerAction(tempB);
				} else {
					throw new Exception("Body creation failed");
				}
			}
			if (t instanceof NewBodyAction) {
				NewBodyAction actNew = (NewBodyAction) t;
				AtomicIntegerArray positionInfo = addBody(actNew);
				if (positionInfo != null) {
					CreateGameRenderable tempA = new CreateGameRenderable(
							actNew.getID(), actNew.getModelID(), positionInfo);
					actionManager.addGraphicsAction(tempA);
				} else {
					throw new Exception("Body creation failed");
				}
			} else if (t instanceof RemoveBodyAction) {
				removeBody(t.getID());
				RemoveGameRenderable tempA = new RemoveGameRenderable(t.getID());
				actionManager.addGraphicsAction(tempA);
			}
		}
		
		Integer key;
		Body body;
		
		for(Map.Entry<Integer, Body> entry : sortedOggetto2D.entrySet()) {
			key = entry.getKey();
			body = entry.getValue();
			AtomicIntegerArray toModify = transformsForGraphics.get(key);

			if(toModify != null){
				toModify.set(0, Float.floatToIntBits(body.getPosition().x));
				toModify.set(1, Float.floatToIntBits(body.getPosition().y));
				toModify.set(2, Float.floatToIntBits(body.getAngle()));
			}
		}
		
		actualTurn++;
		/*
		 * Body body = physicWorld.getBodyList();
		 *
		 * while(body!=null){ System.out.println(body.getTransform().position);
		 * body = body.getNext(); }
		 */

		physicWorld.step(TIMESTEP, 10, 10);
	}
}
