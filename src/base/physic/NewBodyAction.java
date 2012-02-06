/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.physic;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

/**
 * 
 * @author mauro
 */
public class NewBodyAction extends PhysicsAction {
	public int modelID = 0;
	public BodyDef bodyDef;
	public FixtureDef fixtureDef;

	public NewBodyAction(BodyDef bodyDef, FixtureDef fixtureDef) {
		this.bodyDef = bodyDef;
		this.fixtureDef = fixtureDef;
	}

	public int getModelID() {
		return modelID;
	}
}
