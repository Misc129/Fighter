package misc.scripts.fighter.workers;

import misc.scripts.fighter.GUIFighter;
import misc.scripts.fighter.MiscFighter;

import org.hexbot.api.methods.Camera;
import org.hexbot.api.methods.Skills;
import org.hexbot.api.util.Time;
import org.hexbot.core.concurrent.script.Worker;

public class Start extends Worker{

	@Override
	public void run() {
		MiscFighter.timeStart = MiscFighter.timeLastRemovePoint = MiscFighter.timeNextRemovePoint = System.currentTimeMillis();
		MiscFighter.expAttackStart = Skills.ATTACK.getExperience();
		MiscFighter.expStrengthStart = Skills.STRENGTH.getExperience();
		MiscFighter.expDefenceStart = Skills.DEFENSE.getExperience();
		MiscFighter.expHitpointsStart = Skills.HITPOINTS.getExperience();
		
		GUIFighter gui = new GUIFighter();
		
		while(gui != null && gui.isVisible()){
			Time.sleep(200);
		}
		
		Camera.setPitch(false);
		MiscFighter.doStart = false;
	}

	@Override
	public boolean validate() {
		return MiscFighter.doStart;
	}


}
