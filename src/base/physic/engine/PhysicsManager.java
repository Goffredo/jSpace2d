package base.physic.engine;


import base.ActionManager;
import base.graphics.CreateGameRenderable;
import base.graphics.RemoveGameRenderable;
import base.physic.NewBodyAction;
import base.physic.PhysicsAction;
import base.physic.RemoveBodyAction;
import java.util.ArrayList;
import java.util.TreeMap;
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
	TreeMap<Integer, Body> sortedOggetto2D = new TreeMap<Integer, Body>();

	public PhysicsManager(ActionManager aM) {
		actionManager=aM;
		Vec2 worldGravity = new Vec2(0.0f,-10.0f);
		physicWorld = new World(worldGravity, true);
		System.out.println("created world");
		createBorder();
	}

	private Transform addBody(NewBodyAction t) {

		Body body = physicWorld.createBody(t.bodyDef);
		if (body!=null){
			body.createFixture(t.fixtureDef);

			int IDvalue = physicWorld.getBodyCount()+1;
			t.IDbody = new Integer(IDvalue);

			sortedOggetto2D.put(t.getID(), body);

			System.out.println("New element in world! ID:" + t.getID());
			System.out.println("elements in world:" + physicWorld.getBodyCount());
			return body.getTransform();
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
		ArrayList<PhysicsAction> myAction=actionManager.getPhysicActions();

		for (PhysicsAction t:myAction){
			if (t instanceof NewBodyAction){
				NewBodyAction actNew = (NewBodyAction) t;
				Transform positionInfo = addBody(actNew);
				if (positionInfo != null){
					CreateGameRenderable tempA = new CreateGameRenderable(actNew.getID(), actNew.getModelID(), positionInfo);
					actionManager.addGraphicsAction(tempA);
				}else{
					throw new Exception("Body creation failed");
				}
			}else if (t instanceof RemoveBodyAction){
				removeBody( t.getID() );
				RemoveGameRenderable tempA = new RemoveGameRenderable(t.getID());
				actionManager.addGraphicsAction(tempA);
			}
		}

		actualTurn++;
		/*
        Body body = physicWorld.getBodyList();

        while(body!=null){
        	System.out.println(body.getTransform().position);
        	body = body.getNext();
        }*/

		physicWorld.step(TIMESTEP, 10, 10);
	}
}
