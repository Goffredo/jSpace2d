package base.physics.engine;

import java.util.concurrent.atomic.AtomicIntegerArray;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

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

/**
 * 
 * @author mauro
 */
public class PhysicsManager {

	private final float TIMESTEP;
	ActionManager actionManager;
	private int actualTurn = 0;
	float maxX = 50;
	float maxY = 50;
	float minX = -50;
	float minY = -50;
	World physicWorld;
	HashMap<Integer, InfoBodyContainer> sortedOggetto2D = new HashMap<>();
	

	public PhysicsManager(ActionManager aM, float timestep) {
		TIMESTEP = timestep;
		actionManager = aM;
		Vec2 worldGravity = new Vec2(0.0f, -5.0f);
		physicWorld = new World(worldGravity, true);
		System.out.println("created world");
		createBorder();
	}

	private InfoBodyContainer addBody(NewBodyAction t) {

		Body body = physicWorld.createBody(t.getBodyDef());
		if (body != null) {
			body.createFixture(t.getFixitureDef());

			int IDvalue = physicWorld.getBodyCount() + 1;
			t.IDbody = new Integer(IDvalue);
			
			InfoBodyContainer infoBodyContainer = new InfoBodyContainer(body, new Atomic3Float() );
			infoBodyContainer.updateSharedPosition();
			
			sortedOggetto2D.put(t.ID, infoBodyContainer);
			System.out.println("New element in world! ID:" + t.getID());
			System.out.println("elements in world:"
					+ physicWorld.getBodyCount());
			return infoBodyContainer;
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
		InfoBodyContainer deletedBody = sortedOggetto2D.remove(id);
		if (deletedBody != null) {
			physicWorld.destroyBody(deletedBody.body );
			System.out.println("Removed element in world! ID:" + id);
		}
		System.out.println("Failed to remove element in world! ID:" + id);

	}

	public void update() throws Exception {
		ArrayList<PhysicsAction> myAction = actionManager.getPhysicActions();
		for (PhysicsAction t : myAction) {
			if (t instanceof NewBodyAction) {
				NewBodyAction actNew = (NewBodyAction) t;
				InfoBodyContainer positionInfo = addBody(actNew);
				if (positionInfo != null) {
					CreateGameRenderable tempA = new CreateGameRenderable(actNew.getID(), actNew.getModelID(), positionInfo.atomicFloat);
					actionManager.addGraphicsAction(tempA);
					
					if (t instanceof NewBodyActionServer){
						NewEntityInfo tempB = new NewEntityInfo(actNew.getID(), actNew.getModelID(), positionInfo.atomicFloat, actualTurn);
						actionManager.addServerAction(tempB);
					}
						
				} else {
					throw new Exception("Body creation failed");
				}
			} else if (t instanceof RemoveBodyAction) {
				removeBody(t.getID());
				RemoveGameRenderable tempA = new RemoveGameRenderable(t.getID());
				actionManager.addGraphicsAction(tempA);
			}
		}

		for ( InfoBodyContainer info : sortedOggetto2D.values() ){
			info.updateSharedPosition();
		}

		actualTurn++;

		physicWorld.step(TIMESTEP, 10, 10);
	}
}
