package base.graphics;

import org.jbox2d.common.Transform;

public class CreateGameRenderable_dummy extends GraphicAction{
	
	public Integer iD;
	public int modelID;
	public Transform positionInfo;
	
	public CreateGameRenderable_dummy(Integer iD, int modelID, Transform positionInfo) {
		this.iD = iD;
		this.modelID = modelID;
		this.positionInfo = positionInfo;
    }
}
