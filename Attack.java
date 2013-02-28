
// Attack class, used to better keep track of known attacks
public class Attack{
	// stores all information here
	private String name,special;
	private int damage,cost;
	public Attack(String name,String cost,String damage,String special){
		this.name = name;
		this.damage = Integer.parseInt(damage);
		this.cost = Integer.parseInt(cost);
		this.special = special;
	}
	// get damage of attack
	public int getDmg(){
		return damage;
	}
	// get the name
	public String getName(){
		return name;
	}
	//get the energy cost
	public int getCost(){
		return cost;
	}
	// get the special
	public String getSpec(){
		return special;
	}
}