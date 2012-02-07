/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.server;

import base.network.UpdatePosition;
import base.ActionManager;
import base.network.NewGameEntity;
import base.physics.NewBodyActionServer;

import java.util.ArrayList;
/**
 *
 * @author mauro
 */
public class Server {
    ActionManager actionManager;
    ArrayList<EntityInfo> entitys = new ArrayList<>();
    
    public Server(ActionManager am){
        actionManager=am;
    }
    
    public void setupDebug(){
        for (int i=0;i<10;i++){
            actionManager.addPhysicAction( new NewBodyActionServer(i, 0) );
        }
    }
    
    
    public void update(){
		/*Here we have to read inputs*/
		
		
		/*Here we have update our list*/
		ArrayList<ServerAction> serverActions = actionManager.getServerActions();
		for (ServerAction a:serverActions){
			if (a instanceof NewEntityInfo){
				NewEntityInfo nI = (NewEntityInfo)a;
				actionManager.addNetworkAction( new NewGameEntity(nI.getID(), nI.getModelID(), nI.getPosition(), nI.getTick()) );
				entitys.add(nI);
			}
		}
		
		/*Here we have update others's list*/
		for (EntityInfo e:entitys){
			actionManager.addNetworkAction( new UpdatePosition(e.id, e.getPosition()) );
		}
    }
}
