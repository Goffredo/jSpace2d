package base.graphics;

import org.jbox2d.common.Transform;

/**
 * 
 * @author mauro
 */
public class CreateGameRenderable extends GraphicAction {

	public Integer iD;
	public int modelID;
	public Transform positionInfo;

	public CreateGameRenderable(Integer iD, int modelID, Transform positionInfo) {
		super(ActionType.CREATE);
		this.iD = iD;
		this.modelID = modelID;
		this.positionInfo = positionInfo;
	}

}
