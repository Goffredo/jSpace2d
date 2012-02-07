/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.server;


import base.physics.engine.Atomic3Float;

/**
 * 
 * @author mauro
 */
public class NewEntityInfo extends EntityInfo {

	public NewEntityInfo(Integer iD, int modelID, Atomic3Float positionInfo, int actualTurn) {
		super(iD, modelID, positionInfo, actualTurn);
	}

}
