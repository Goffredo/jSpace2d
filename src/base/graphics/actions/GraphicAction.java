/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.graphics.actions;

/**
 *
 * @author mauro
 */
public abstract class GraphicAction {

    public enum ActionType {

        CREATE, REMOVE
    }
    public ActionType actionType;

    public GraphicAction(ActionType actionType) {
        this.actionType = actionType;
    }
}
