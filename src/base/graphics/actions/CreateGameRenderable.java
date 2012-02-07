package base.graphics.actions;

import java.util.concurrent.atomic.AtomicIntegerArray;


/**
 * 
 * @author mauro
 */
public class CreateGameRenderable extends GraphicAction {

	public Integer iD;
	public int modelID;
	public AtomicIntegerArray positionInfo;

	public CreateGameRenderable(Integer iD, int modelID, AtomicIntegerArray positionInfo) {
		super(ActionType.CREATE);
		this.iD = iD;
		this.modelID = modelID;
		this.positionInfo = positionInfo;
	}

}
