package misc.scripts.fighter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import misc.scripts.fighter.util.Monster;
import misc.scripts.fighter.workers.Eat;
import misc.scripts.fighter.workers.Fight;
import misc.scripts.fighter.workers.Loot;
import misc.scripts.fighter.workers.Start;
import misc.scripts.runecrafter.util.Util;

import org.hexbot.api.listeners.Paintable;
import org.hexbot.api.methods.Skills;
import org.hexbot.api.methods.input.Mouse;
import org.hexbot.api.methods.input.Mouse.Speed;
import org.hexbot.api.util.Time;
import org.hexbot.core.concurrent.script.Info;
import org.hexbot.core.concurrent.script.TaskScript;
import org.hexbot.core.concurrent.script.Type;

@Info(
		name = "MiscFighter",
		author = "Misc",
		description = "v2 - Simple Auto fighter",
		type = Type.COMBAT
		)
public class MiscFighter extends TaskScript implements Paintable{

	public static long timeStart, timeLastRemovePoint, timeNextRemovePoint, millis, seconds, minutes, hours;
	public static int innerOrbitAngle = 0, innerOrbitAngle2 = 180, outerOrbitAngle = 90, outerOrbitAngle2 = 270,
			idFood, percentHealthToEat, expAttackStart, expStrengthStart, expDefenceStart, expHitpointsStart,
			expAttackGained, expStrengthGained, expDefenceGained, expHitpointsGained, 
			expAttackPerHour, expStrengthPerHour, expDefencePerHour, expHitpointsPerHour;
	public static boolean doStart = true;

	public static List<Monster> monsters = new ArrayList<Monster>();
	public static List<Integer> lootIds= new ArrayList<Integer>();
	public static List<String> lootNames= new ArrayList<String>();

	private static LinkedList<Point> points = new LinkedList<Point>();

	public MiscFighter(){

		Mouse.setSpeed(Speed.NORMAL);
		submit(new Start(), new Eat(), new Loot(), new Fight());
	}

	private void updatePoints(){
		if(points.isEmpty()){
			points.add(0,Mouse.getLocation());
			timeNextRemovePoint = System.currentTimeMillis() + 2000;
		}
		else if(!Mouse.getLocation().equals(points.get(0))){
			points.add(0,Mouse.getLocation());
		}
		if(!points.isEmpty() && System.currentTimeMillis() > timeNextRemovePoint){
			timeLastRemovePoint = timeNextRemovePoint;
			timeNextRemovePoint = System.currentTimeMillis() + 30;
			points.removeLast();
		}
	}

	private void drawPoints(Graphics g){
		if(points.size() > 1){
			Point prev = points.getFirst();
			for(Point p : points){
				if(!prev.equals(p))
					g.drawLine(prev.x, prev.y, p.x, p.y);
				prev = p;
			}
		}
	}

	private void updateOrbits(){
		if(innerOrbitAngle >= 360)
			innerOrbitAngle = 0;
		if(innerOrbitAngle2 >= 360)
			innerOrbitAngle2 = 0;
		if(outerOrbitAngle >= 360)
			outerOrbitAngle = 0;
		if(outerOrbitAngle2 >= 360)
			outerOrbitAngle2 = 0;
		innerOrbitAngle+=2;
		innerOrbitAngle2+=2;
		outerOrbitAngle-=2;
		outerOrbitAngle2-=2;
	}

	private void drawCursorOrbit(Graphics g){
		g.setColor(Color.green);
		g.drawArc(Mouse.getX() - 10, Mouse.getY() - 10, 20, 20, innerOrbitAngle, 135);
		g.drawArc(Mouse.getX() - 10, Mouse.getY() - 10, 20, 20, innerOrbitAngle2, 135);
		g.drawArc(Mouse.getX() - 15, Mouse.getY() - 15, 30, 30, outerOrbitAngle, 135);
		g.drawArc(Mouse.getX() - 15, Mouse.getY() - 15, 30, 30, outerOrbitAngle2, 135);

	}

	private void drawMouse(Graphics g){
		g.setColor(Color.red);
		g.fillRect(Mouse.getX() - 1, Mouse.getY() - 5, 2, 10);
		g.fillRect(Mouse.getX() - 5, Mouse.getY() - 1, 10, 2);
	}

	private void updateStats(){
		expAttackGained = (Skills.ATTACK.getExperience() - expAttackStart);
		expStrengthGained = (Skills.STRENGTH.getExperience() - expStrengthStart);
		expDefenceGained = (Skills.DEFENSE.getExperience() - expDefenceStart);
		expHitpointsGained = (Skills.HITPOINTS.getExperience() - expHitpointsStart);
		millis = System.currentTimeMillis() - timeStart;
		if(millis != 0){
			expAttackPerHour = (int)(expAttackGained * 3600000D  / (millis));
			expStrengthPerHour = (int)(expStrengthGained * 3600000D / (millis));
			expDefencePerHour = (int)(expDefenceGained * 3600000D / (millis));
			expHitpointsPerHour = (int)(expHitpointsGained * 3600000D / (millis));
		}
		hours = millis / (1000 * 60 * 60);
		millis -= hours * (1000 * 60 * 60);
		minutes = millis / (1000 * 60);
		millis -= minutes * (1000 * 60);
		seconds = millis / 1000;
	}

	@Override
	public void paint(Graphics g) {
		drawMouse(g);

		updatePoints();
		drawPoints(g);

		updateOrbits();
		drawCursorOrbit(g);

		g.setColor(Color.gray);
		g.fillRoundRect(350, 347, 147, 112, 10, 10);
		g.setColor(Color.BLACK);
		g.drawString("MiscFigher", 380, 360);
		updateStats();
		g.drawString("Runtime: " + hours +":"+ minutes + ":" + seconds, 355, 375);
		g.drawString("Attack Xp:"+expAttackGained+" ("+expAttackPerHour+")", 355, 390);
		g.drawString("Strength Xp:"+expStrengthGained+" ("+expStrengthPerHour+")", 355, 405);
		g.drawString("Defence Xp:"+expDefenceGained+" ("+expDefencePerHour+")", 355, 420);
		g.drawString("Hitpoints Xp:"+expHitpointsGained+" ("+expHitpointsPerHour+")", 355, 435);
	}

}
