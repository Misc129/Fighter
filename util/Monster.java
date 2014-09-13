package misc.scripts.fighter.util;

public class Monster {

	public Monster(int id, String name, int level){
		_id = id;
		_name = name;
		_level = level;
	}
	
	private int _id;
	private String _name;
	private int _level;
	
	public int getId(){
		return _id;
	}
	
	public String getName(){
		return _name;
	}

	public int getLevel(){ 
		return _level;
	}
	
	public boolean equals(Object other){
		if(!(other instanceof Monster))
			return false;
		return ((Monster) other).getId() == _id;
	}
	
	public String toString(){
		return _name + " ("+_level+"):"+_id;
	}

}
