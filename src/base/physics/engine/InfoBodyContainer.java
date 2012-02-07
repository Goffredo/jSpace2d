/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.physics.engine;

import org.jbox2d.dynamics.Body;

/**
 *
 * @author mauro
 */
public class InfoBodyContainer {
	Body body;
	Atomic3Float atomicFloat;
	
	InfoBodyContainer(Body body, Atomic3Float atomicFloat) {
		this.body = body;
		this.atomicFloat = atomicFloat;
	}

	void updateSharedPosition() {
		atomicFloat.set(body.getPosition().x, body.getPosition().y, body.getAngle() );
	}
	
}
