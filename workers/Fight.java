package misc.scripts.fighter.workers;

import misc.scripts.fighter.MiscFighter;
import misc.scripts.fighter.util.Monster;

import org.hexbot.api.methods.Camera;
import org.hexbot.api.methods.Walking;
import org.hexbot.api.methods.interactable.Npcs;
import org.hexbot.api.methods.interactable.Players;
import org.hexbot.api.util.Filter;
import org.hexbot.api.util.Time;
import org.hexbot.api.wrapper.interactable.Npc;
import org.hexbot.api.wrapper.interactable.Player;
import org.hexbot.core.concurrent.script.Worker;

public class Fight extends Worker{
	
	@Override
	public void run() {
		Npc npc = getNextToFight();
		if(npc != null){
			if(!npc.isOnScreen())
				Camera.turnTo(npc);
			//if npc is still off screen (out of reach)
			if(!npc.isOnScreen()){
				Walking.walk(npc);
				Time.sleep(1000);
			}
			if(npc.interact("Attack")){
				Time.sleep(1500);
				while(Players.getLocal().isMoving()){
					Time.sleep(200);
				}
			}
		}
	}

	@Override
	public boolean validate() {
		return Players.getLocal().getInteracting() == null && getNextToFight() != null;
	}
	
	public static Npc getNextToFight(){
		return  Npcs.getNearest(new Filter<Npc>(){

			@Override
			public boolean accept(Npc arg0) {
				for(Monster monster : MiscFighter.monsters){
					if(arg0.getId() == monster.getId() && !isGettingAttacked(arg0)){
						return true;
					}
				}
				return false;
			}});
	}
	
	public static boolean isGettingAttacked(Npc npc){
		if(npc == null) return false;
		for(Player player : Players.getLoaded()){
			if(player == null) continue;
			if(!player.equals(Players.getLocal()) && player.getInteracting().equals(npc)){
				return true;
			}
		}
		return false;
	}
	
}
