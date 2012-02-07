package base.graphics.actions;

import base.physics.engine.Atomic3Float;


/**
 * 
 * @author mauro
 */
public class CreateGameRenderable extends GraphicAction {

	public Integer iD;
	public int modelID;
	public Atomic3Float positionInfo;

	public CreateGameRenderable(Integer iD, int modelID, Atomic3Float positionInfo) {
		super(ActionType.CREATE);
		this.iD = iD;
		this.modelID = modelID;
		this.positionInfo = positionInfo;
	}

}
