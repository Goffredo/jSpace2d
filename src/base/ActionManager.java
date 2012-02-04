package base;

import base.graphics.GraphicAction;
import base.physic.PhysicsAction;
import java.util.ArrayList;

/**
 *
 * @author mauro
 */
public class ActionManager {
    
    
    /*
     * PHYSIC
     */
    private final Object physicLock = new Object();
    ArrayList<PhysicsAction> physicActions;
        
    public ArrayList<PhysicsAction> getPhysicActions() {
        ArrayList<PhysicsAction> tmpActions = new ArrayList<>();
        ArrayList<PhysicsAction> returnActions = physicActions;
        synchronized(physicLock){
            physicActions=tmpActions;
        }
        return returnActions;
    }
    
    public void addPhysicAction(PhysicsAction a){
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
