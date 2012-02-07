/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.server;

import base.physics.engine.Atomic3Float;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.jbox2d.common.Transform;

/**
 *
 * @author mauro
 */
class EntityInfo extends ServerAction{
	int id;
	int modelID;
	int tick;
	Atomic3Float position;
	
    public EntityInfo(int iD, int modelID, Atomic3Float positionInfo, int tick) {
		this.id = iD;
		this.modelID = modelID;
		this.position = positionInfo;
		this.tick = tick;
    }
    
	public int getID(){
		return id;
	}
	
	public int getModelID(){
		return modelID;
	}
	
	public float[] getPosition(){
		return position.get();
	}
	
	public float getTick(){
		return tick;
	}
}
