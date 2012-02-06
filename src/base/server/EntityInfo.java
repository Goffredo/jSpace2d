/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.server;

import org.jbox2d.common.Transform;

/**
 *
 * @author mauro
 */
class EntityInfo extends ServerAction{
	int id;
	int modelID;
	int tick;
	Transform position;
	
    public EntityInfo(int iD, int modelID, Transform positionInfo, int tick) {
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
		return position.position.x;
	}
	
	public float getY(){
		return position.position.x;
	}
	
	public float getAngle(){
		return position.getAngle();
	}
	
	public float getTick(){
		return tick;
	}
}
