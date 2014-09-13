package misc.scripts.fighter.util;

import java.util.ArrayList;
import java.util.List;

import org.hexbot.api.methods.interactable.Npcs;
import org.hexbot.api.util.Filter;
import org.hexbot.api.wrapper.interactable.Npc;

public class Util {
	
	public static List<Monster> getAttackableMonsters(){
		Npc[] npcs = Npcs.getLoaded(new Filter<Npc>(){
			@Override
			public boolean accept(Npc n) {
				for(String action : n.getActions()){
					if(action == null) continue;
					if(action.contains("Attack"))
							return true;
				}
				return false;
			}});
		ArrayList<Monster> result = new ArrayList<Monster>();
		for(Npc n : npcs){
			result.add(new Monster(n.getId(), n.getName(), n.getLevel()));
		}
		return result;
	}

}
