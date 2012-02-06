/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.graphics;

/**
 *
 * @author mauro
 */
public class RemoveGameRenderable extends GraphicAction{

	public Integer iD;
	
    public RemoveGameRenderable(Integer iD) {
    	super(ActionType.REMOVE);
        this.iD = iD;
    }
    
}
