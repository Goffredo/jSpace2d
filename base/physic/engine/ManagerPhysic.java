package base.physic.engine;


import base.graphics.RemoveGameRenderable;
import base.ManagerAction;
import base.graphics.CreateGameRenderable;
import base.physic.NewBodyAction;
import base.physic.PhysicAction;
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
public class ManagerPhysic {

    ManagerAction actionManager;
    TreeMap<Integer, Body> sortedOggetto2D = new TreeMap<>();
    
    private static final float TIMESTEP = 1.0f / 60.0f;
    World physicWorld;
    float minX = -50;
    float minY = -50;
    float maxX = 50;
    float maxY = 50;
    private int actualTurn = 0;

    public ManagerPhysic(ManagerAction aM) {
        actionManager=aM;
        Vec2 worldGravity = new Vec2();
        physicWorld = new World(worldGravity, true);
        createBorder();
    }

    private void createBorder() {

        { // Up wall
            FixtureDef fd = new FixtureDef();
            PolygonShape sd = new PolygonShape();
            sd.setAsBox(maxX - minX, 10);
            fd.shape = sd;

            BodyDef bd = new BodyDef();
            bd.position = new Vec2(minX, minY);
            physicWorld.createBody(bd).createFixture(fd);

        }

        { // Down wall
            FixtureDef fd = new FixtureDef();
            PolygonShape sd = new PolygonShape();
            sd.setAsBox(maxX - minX, 10);
            fd.shape = sd;

            BodyDef bd = new BodyDef();
            bd.position = new Vec2(minX, maxY);
            physicWorld.createBody(bd).createFixture(fd);

        }

        { // Left wall
            FixtureDef fd = new FixtureDef();
            PolygonShape sd = new PolygonShape();
            sd.setAsBox(10, maxY - minY);
            fd.shape = sd;

            BodyDef bd = new BodyDef();
            bd.position = new Vec2(minX, minY);
            physicWorld.createBody(bd).createFixture(fd);

        }

        { // Right wall
            FixtureDef fd = new FixtureDef();
            PolygonShape sd = new PolygonShape();
            sd.setAsBox(10, maxY - minY);
            fd.shape = sd;

            BodyDef bd = new BodyDef();
            bd.position = new Vec2(maxX, minY);
            physicWorld.createBody(bd).createFixture(fd);

        }
    }

    public void update() throws Exception {
        ArrayList<PhysicAction> myAction=actionManager.getPhysicActions();
        
        for (PhysicAction t:myAction){
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
        physicWorld.step(TIMESTEP, 10, 10);
    }

    private Transform addBody(NewBodyAction t) {
        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        Body body = t.createBody(physicWorld.createBody(bd));

        if (body!=null){
            sortedOggetto2D.put(t.getID(), body);

            System.out.println("New element in world! ID:" + t.getID());
            System.out.println("elements in world:" + physicWorld.getBodyCount());
            return body.getTransform();
        }
        return null;
    }

    private void removeBody(int id) {
        Body deletedBody = sortedOggetto2D.remove(id);
        if (deletedBody != null) {
            physicWorld.destroyBody(deletedBody);
            System.out.println("Removed element in world! ID:" + id);
        }
        System.out.println("Failed to remove element in world! ID:" + id);
        
    }
}
