/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.server;

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
	AtomicIntegerArray position;
	
    public EntityInfo(int iD, int modelID, AtomicIntegerArray positionInfo, int tick) {
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
	
	public float getX(){
		return Float.intBitsToFloat(position.get(0));
	}
	
	public float getY(){
		return Float.intBitsToFloat(position.get(1));
	}
	
	public float getAngle(){
		return Float.intBitsToFloat(position.get(2));
	}
	
	public float getTick(){
		return tick;
	}
}
