package base.game.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import org.jbox2d.common.Vec2;

import base.common.AsyncActionBus;
import base.common.InfoBodyContainer;
import base.game.entity.graphics.actions.G_CreateGameRenderableAction;
import base.game.entity.graphics.actions.G_RemoveGameRenderable;
import base.game.entity.physics.PhysicsHandler;
import base.game.entity.physics.common.BodyBlueprint;
import base.game.player.Player;
import base.worker.Worker;

public class EntityHandler {

	private final LinkedList<Integer> unusedIDs = new LinkedList<>();
	private int currentID = 0;
	private final HashMap<Integer, Entity> entityMap = new HashMap<>();
	
	/*for physic*/
	private final PhysicsHandler phisic;
	
	/*for graphics*/
	private final AsyncActionBus bus;
	
	public EntityHandler(AsyncActionBus graphicBus, AtomicInteger step){
		phisic = new PhysicsHandler(12000000, graphicBus.sharedLock, step);
		this.bus = graphicBus;
		phisic.start();
	}

	public Collection<Worker> update(){
		phisic.update();
		return new ArrayList<>();
	}

	private int getFreeID(){
		if(unusedIDs.size()>0){
			return unusedIDs.poll();
		}else{
			return currentID++; //return current ID and then increment it by 1
		}
	}

	private void removeID(int ID){
		if (ID < currentID){
			unusedIDs.add(currentID);
		}
	}

	public int createEntity(String graphicModelName, BodyBlueprint bodyBlueprint, Player player){
		int id = getFreeID();
		Entity e = new Entity(id, player);
		entityMap.put(id, e);

		ArrayList<InfoBodyContainer> bodys = createPhisic(bodyBlueprint); 
		if ( bodys != null ){
			e.bodyList.addAll(bodys);
			createGraphics(id, bodys, graphicModelName);
			return id;
		}else
			return -3; //phisic error
	}
	
	public void moveEntity(float newX, float newY, int entity){
		entityMap.get(entity).bodyList.get(0).body.setTransform(new Vec2(newX, newY), entityMap.get(entity).bodyList.get(0).body.getAngle());
	}

	public void removeEntity(int id){
		removeID(id);
		Entity e = entityMap.remove(id);
		destroyBody( e.bodyList );
		destroyGraphic( e.entityID );
	}

	private void createGraphics(int ID, ArrayList<InfoBodyContainer> bodies, String graphicModelName) {
		for (InfoBodyContainer info:bodies){
			G_CreateGameRenderableAction a = new G_CreateGameRenderableAction(ID, graphicModelName, info.transform);
			bus.addGraphicsAction(a);
		}
	}

	private void destroyBody(ArrayList<InfoBodyContainer> bodyList) {
		for (InfoBodyContainer b:bodyList){
			phisic.removeBody(b.body);
		}
	}

	private void destroyGraphic(int ID) {
		bus.addGraphicsAction(new G_RemoveGameRenderable(ID));
	}

	private ArrayList<InfoBodyContainer> createPhisic(BodyBlueprint bodyBlueprint){
		return phisic.addBody(bodyBlueprint);
	}


}