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
public class NewEntityInfo extends EntityInfo{

	public NewEntityInfo(Integer iD, int modelID, Transform positionInfo, int actualTurn) {
		super(iD, modelID, positionInfo, actualTurn);
	}

}
