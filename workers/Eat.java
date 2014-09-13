package misc.scripts.fighter.workers;

import misc.scripts.fighter.MiscFighter;

import org.hexbot.api.methods.Skills;
import org.hexbot.api.methods.interactable.Players;
import org.hexbot.api.methods.node.Inventory;
import org.hexbot.api.util.Time;
import org.hexbot.api.wrapper.node.InventoryItem;
import org.hexbot.core.concurrent.script.Worker;

public class Eat extends Worker{
	
	@Override
	public void run() {
		InventoryItem food = Inventory.getItem(MiscFighter.idFood);
		if(food != null && food.interact("Eat")){
			Time.sleep(1500);
		}
	}

	@Override
	public boolean validate() {
		return needToEat();
	}

	public boolean needToEat(){
		if(Skills.HITPOINTS.getRealLevel() == 0)
			return false;
		return ((double)Skills.HITPOINTS.getCurrentLevel() / (double)Skills.HITPOINTS.getRealLevel()) * 100 < MiscFighter.percentHealthToEat;
	}
	
}
