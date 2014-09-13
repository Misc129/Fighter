package misc.scripts.fighter.workers;

import misc.scripts.fighter.MiscFighter;

import org.hexbot.api.methods.Camera;
import org.hexbot.api.methods.Walking;
import org.hexbot.api.methods.interactable.Players;
import org.hexbot.api.methods.node.GroundItems;
import org.hexbot.api.methods.node.Inventory;
import org.hexbot.api.util.Filter;
import org.hexbot.api.util.Time;
import org.hexbot.api.wrapper.node.GroundItem;
import org.hexbot.core.concurrent.script.Worker;

public class Loot extends Worker{

	@Override
	public void run() {
		System.out.println("Loot");
		GroundItem g = findLoot();
		if(g == null)
			return;
		if(!g.isVisible()){
			Camera.turnTo(g);
		}
		//if still not visible (out of reach)
		if(!g.isVisible()){
			Walking.walk(g);
			Time.sleep(500);
		}
		
		if(g.interact("Take")){
			Time.sleep(700);
			while(Players.getLocal().isMoving())
				Time.sleep(200);
			Time.sleep(500);
		}
	}

	@Override
	public boolean validate() {
		return findLoot() != null && !Inventory.isFull();
	}

	public static GroundItem findLoot(){
		for(GroundItem g : GroundItems.getAll()){
			for(int id : MiscFighter.lootIds){
				if(g.getId() == id && g.getLocation().canReach()){
					System.out.println("id match found");
					return g;
				}
			}
			for(String name : MiscFighter.lootNames){
				if(g.getName().equals(name) && g.getLocation().canReach()){
					System.out.println("name match found");
					return g;
				}
			}
		}
		return null;
	}

}
