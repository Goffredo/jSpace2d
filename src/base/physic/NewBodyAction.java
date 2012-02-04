/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.physic;

import base.physic.PhysicsAction;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author mauro
 */
public class NewBodyAction extends PhysicsAction{
    private int modelID;
    Body myself;

    public Body createBody(Body bd) {
        { // Down wall
            FixtureDef fd = new FixtureDef();
            PolygonShape sd = new PolygonShape();
            sd.setAsBox(10, 10);
            fd.shape = sd;
            fd.density = 25.0f;

            bd.setTransform(new Vec2(0, 0), 0);
            
            myself.createFixture(fd);
            return myself;
        }
    }
    
    public int getModelID() {
        return modelID;
    }
}
