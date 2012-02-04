package base;

import base.graphics.GraphicAction;
import base.physic.PhysicAction;
import java.util.ArrayList;

/**
 *
 * @author mauro
 */
public class ManagerAction {
    
    
    /*
     * PHYSIC
     */
    private final Object physicLock = new Object();
    ArrayList<PhysicAction> physicActions;
        
    public ArrayList<PhysicAction> getPhysicActions() {
        ArrayList<PhysicAction> tmpActions = new ArrayList<>();
        ArrayList<PhysicAction> returnActions = physicActions;
        synchronized(physicLock){
            physicActions=tmpActions;
        }
        return returnActions;
    }
    
    public void addPhysicAction(PhysicAction a){
        synchronized(physicLock){
            physicActions.add(a);
        }
    }
    
    /*
     * GRAPHICS
     */
    private final Object graphicLock = new Object();
    ArrayList<GraphicAction> graphicActions;
    
    public ArrayList<GraphicAction> getGraphicActions() {
        ArrayList<GraphicAction> tmpActions = new ArrayList<>();
        ArrayList<GraphicAction> returnActions = graphicActions;
        synchronized(graphicLock){
            graphicActions=tmpActions;
        }
        return returnActions;
    }
    
    public void addGraphicsAction(GraphicAction a) {
        synchronized(graphicLock){
            graphicActions.add(a);
        }
    }
    
}
