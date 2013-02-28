import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.sound.sampled.*;
import java.util.*;
import java.io.*;

// The Pokemon class, where most of the nitty-gritty is taken care of

// Fields include status of the pokemon, basic info and the resctive image
public class Pokemon{
	// each pokemon has an arraylist of damage for themselves
	private ArrayList<Damage> dmg = new ArrayList<Damage>();
	private double recoilCounter=4.0,frac,eFrac,speed=10;
	private boolean attacking=false,disabled = false,stunned=false,recoil=false,missed=false;
	private Attack using;
	private int dmgTaken,player,hp,numAttacks,energy,ground=400,x,y=ground-100,fullHP,fullEnergy,baseDmg=2,stunCounter=0;
	private String name,type,resistance, weakness;
	private Attack[] attacks;
	private Image pokeImage;
	// attakcs is an array of the attack
	// its [name,damage,cost,special]
	
	public Pokemon(String in){
		// using StringTokenizer for more efficiency
		// storing the basic information
		StringTokenizer st = new StringTokenizer(in,",");
		name = st.nextToken();
		hp = Integer.parseInt(st.nextToken());
		fullHP = hp;
		energy = 50;
		fullEnergy = 50;	
		type = st.nextToken();
		resistance = st.nextToken();
		weakness = st.nextToken();
		numAttacks = Integer.parseInt(st.nextToken());
		attacks = new Attack[numAttacks];
		int j=6;
		for (int i=0;i<numAttacks;i++){
			attacks[i] = new Attack(st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken());
			j+=4;
		}
	}
	
	// returns the name of the pokemon
	public String toString(){
		return name;
	}
	
	// restarts the pokemon at the original starting spot with normal speed
	public void setPlayer(int p){
		player = p;
		if (player==1){
			x=100;
			pokeImage = (new ImageIcon("Battle Pics\\"+name+" L"+".png")).getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH);
		}else{
			x = 700;
			pokeImage = (new ImageIcon("Battle Pics\\"+name+" R"+".png")).getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH);
			//fullHP = 200;
		}
		speed = 10;
		recoil = false;
	}
	
	// draws the pokemon
	public void draw(Graphics g){		
		if (player==1){
			g.drawImage(pokeImage,x-pokeImage.getWidth(null),y,null);
		}else{
			g.drawImage(pokeImage,x,y,null);
		}
		
		
		// draw hp bar with length based on current hp
		frac = getHP()/(float)(getFull());
		
		// gets the colour based on your hp %
		if (frac > 0.5){
			g.setColor(Color.green);
		}else if (frac <= 0.5 && frac >= 0.25){
			g.setColor(Color.orange);
		}else{
			g.setColor(Color.red);
		}
		
		// draw hp here
		if (player==1){
			g.fillRect(50,30+50,(int)(frac*170),25);
		}else{
			g.fillRect(750-(int)(frac*170),30+50,(int)(frac*170),25);
		}
		
		eFrac = getEnergy()/(float)(getFullEnergy());
		//draw energy
		g.setColor(Color.blue);
		if (player==1){
			g.fillRect(50,30+100,(int)(eFrac*170),25);
		}else{
			g.fillRect(750-(int)(eFrac*170),30+100,(int)(eFrac*170),25);
		}
				
		
		// draw dmg text and remove them if they are done updating
		for (Damage d : dmg){
			d.draw(g);
			d.move();
		}
		ArrayList<Integer> index = new ArrayList<Integer>();
		for (int i=0;i<dmg.size();i++){
			if ((dmg.get(i).getCount()==8 && dmg.get(i).getDmg().length()<=2) || (dmg.get(i).getCount()==10 && dmg.get(i).getDmg().length()>2)){
				index.add(i);
			}
		}
		for (int i : index){
			dmg.remove(i);
		}

		
		
	}
	
	// method overloaded the draw for just drawing a pokemon without dmg
	// just static in one spot with hp and energy bar (used for retreat)
	public void draw(Graphics g,int px, int py){
		g.drawImage(pokeImage,px,py,null);
		
		// draw hp bar
		frac = getHP()/(float)(getFull());
		eFrac = getEnergy()/(float)(getFullEnergy());
		if (frac > 0.5){
			g.setColor(Color.green);
		}else if (frac <= 0.5 && frac >= 0.25){
			g.setColor(Color.orange);
		}else{
			g.setColor(Color.red);
		}
		
		// draw hp
		g.fillRect(x+100,y,(int)(frac*170),25);
		
		//draw energy
		g.setColor(Color.blue);
		g.fillRect(x+100,y+30,(int)(eFrac*170),25);
			
	}
	

	public Attack attack(int i){
		return attacks[i];
	}
	public int getAttackNum(){
		return numAttacks;
	}
	public int getFull(){
		return fullHP;
	}
	public int getFullEnergy(){
		return fullEnergy;
	}
	public int getEnergy(){
		return energy;
	}
	public int getX(){
		return x;
	}
	public void addEnergy(int k){
		if (energy<(50-k)){
			energy += k;
		}else{
			energy = 50;
		}
	}
	public void addHP(int k){
		if (hp<(fullHP-k)){
			hp += k;
		}else{
			hp = fullHP;
		}
	}
	public int getY(){
		return y;
	}
	public int getHP(){
		return hp;
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public String getRes(){
		return resistance;
	}
	public String getType(){
		return type;
	}
	public String getWeak(){
		return weakness;
	}
	
	public double getPercent(){
		return hp/(float)(fullHP);
	}
	
	public void set(int nx, int ny){
		x=nx;
		y=ny;
	}
	public String getName(){
		return name;
	}
	
	public boolean active(){
		if (hp>0){
			return true;
		}else{
			return false;
		}
	}
	
	public void getStunned(){
		stunned = true;
	}
	
	public boolean isStunned(){
		return stunned;
	}
	
	public boolean isDisabled(){
		return disabled;
	}
	
	public void getDisabled(){
		disabled = true;
	}
	public int getPlayer(){
		return player;
	}
	
	public void addDmg(String d){
		if (player==1){
			dmg.add(new Damage(getX()-50,350,d));
		}else{
			dmg.add(new Damage(getX()+45,350,d));
		}
		
	}
	public void addDmg(String d,int x, int y){
		dmg.add(new Damage(x,y,d));
	}
	
	public void useAttack(int atk){
		//if (atk != -1){
			//if (atk<=attacks.length-1){
		attacking=true;
		using = attacks[atk];
			//}			
		//}

	}
	
	public void loseHP(int dmg){
		hp -= dmg;		
	}
	public boolean isDead(){
		return hp>0;
	}
	
	public void move(int poi){
		if (!stunned && !recoil){
			if (player==1){
				x += (int)(speed);
			}else{
				x -= (int)(speed);
			}
		}
		if (recoil){
			if (recoilCounter<=1.5){
				recoil=false;
				recoilCounter=4;
				speed = Math.abs(poi-x)/10;
			}
			if (player==1){
				x-=(int)(Math.pow(recoilCounter,2));
			}else{
				x+=(int)(Math.pow(recoilCounter,2));
			}
			recoilCounter-=0.3;
		}
		/*if (recoil){
			if (recoilCounter<=1.5){
				recoil=false;
				recoilCounter=4;
				speed = Math.abs(poi-x)/10;
			}
			if (player==1){
				x-=(int)(300*(1-(hp/(float)(fullHP)))/5);
			}else{
				x+=(int)(300*(1-(hp/(float)(fullHP)))/5);
			}
			recoilCounter-=0.3;	
		}*/
	}
	
	public void stunCount(boolean usedAttack){
		if (usedAttack){
			stunCounter+=6;
		}else{
			stunCounter++;
		}
		
		if (stunCounter>6){
			stunned=false;
			stunCounter=0;
		}
	}
	
	// YOU HIT THE ENEMY
	public void hit(Pokemon enemy){
		int dmg;
		boolean rate=false;
		if (player==1){
			if (x>=enemy.getX()){
				rate = true;
			}
		}else{
			if (x<=enemy.getX()){
				rate = true;
			}
		}
		if (!stunned && !recoil){
			//if (Math.abs(x-enemy.getX())<=25){
			if (rate){
				if (attacking){
					
					if (enemy.isStunned()){
						enemy.stunCount(true);
					}
					if (chance()){
						if (using.getSpec().equals("stun")){
							enemy.getStunned();
							if (player==1){
								addDmg("Stunned enemy "+enemy.getName()+"!",200,200);
							}else{
								addDmg("Stunned enemy "+enemy.getName()+"!",200,200);
							}								
								
							
						}else if (using.getSpec().equals("wild card")){	
							missed = true;
							if (player==1){
								addDmg(name+" missed with wild card!",200,200);
							}else{
								addDmg(name+" missed with wild card!",200,200);
							}							
						}					
					}
				
						
					//}else if (using.getSpec().equals("wild storm")){
						
					if (using.getSpec().equals("recharge")){
						if (energy<30){
							energy += 20;
						}else{
							energy=50;
						}
						
						if (player==1){
							addDmg(name+" gained 20 Energy using Recharge!",200,200);
						}else{
							addDmg(name+" gained 20 Energy using Recharge!",200,200);
						}							
					}else if (using.getSpec().equals("disable")){
							enemy.getDisabled();
							if (player==1){
								addDmg("Disabled enemy "+enemy.getName()+"!",200,200);
							}else{
								addDmg("Disabled enemy "+enemy.getName()+"!",200,200);
							}
					}
					

					
					if (!missed){
						if (disabled){
							dmg = calculate(enemy,using.getDmg()-10);
							enemy.loseHP(dmg);
							if (using.getDmg()>0){
								enemy.addDmg(dmg+"");
							}
							
						}else{
							dmg = calculate(enemy,using.getDmg());
							enemy.loseHP(dmg);
							if (using.getDmg()>0){
								enemy.addDmg(dmg+"");
							}							
						}					
					}

					energy -= using.getCost();
					attacking=false;
					missed = false;	
					
				}else{
					if (disabled){
						dmg = calculate(enemy,baseDmg-1);
						enemy.loseHP(dmg);
						enemy.addDmg(dmg+"");
					}else{
						dmg = calculate(enemy,baseDmg);
						enemy.loseHP(dmg);
						enemy.addDmg(dmg+"");
					}
					if (enemy.isStunned()){
						enemy.stunCount(false);
					}
					
				}
				if (enemy.isStunned()){
					stunCount(attacking);
				}
				//enemy.loseHP(dmg);
				//enemy.addDmg(dmg);
				recoil=true;
				if (energy<fullEnergy){
					energy++;
				}
			}
		}

	}
	public int calculate(Pokemon enemy,int damage){
		if (type.equals(enemy.getWeak())){
			return (int)(damage*2);
		}else if (type.equals(enemy.getRes())){
			return (int)(damage*0.5);
		}else{
			return damage;
		}
	}
	public boolean chance(){
		return Math.random()<=0.5;
	}
}		