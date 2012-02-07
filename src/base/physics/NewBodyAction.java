/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.physics;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author mauro
 */
public class NewBodyAction extends PhysicsAction {

    public int ID = 0;
    public int modelID = 0;

    public NewBodyAction(int id, int modelID) {
        this.ID = id;
        this.modelID = modelID;
    }

    public int getModelID() {
        return modelID;
    }

    public BodyDef getBodyDef() {
        BodyDef bd = new BodyDef();
        bd.allowSleep = false;
        bd.type = BodyType.DYNAMIC;
        bd.angle = (float) (MathUtils.HALF_PI * Math.random());
        bd.position = new Vec2((float) (50.0f * Math.random()) - 25, (float) (50.0f * Math.random()) - 25);
        return bd;
    }

    public FixtureDef getFixitureDef() {
        FixtureDef fd = new FixtureDef();
        fd.density = 5.0f;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(2.0f, 2.0f);
        fd.shape = shape;
        fd.restitution = 1.1f;
        fd.friction = 0.3f;
        return fd;
    }
}
