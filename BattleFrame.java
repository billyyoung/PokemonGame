import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.sound.sampled.*;
import java.util.*;
import java.io.*;


// PokBattle, where the battle happens.
// In charge of moving, updating, and healing pokemon
// also includes retreat
public class BattleFrame extends BaseFrame{
	protected static final int BATTLE = 0, LOSE = 1, WIN = 2;
	// the fields
	protected int introCount=360;
	protected int count1 = 0,count2=0,chosen=0,chosenNum=0;
	protected int MODE;
	// poi is the place where the pokemon hit (point of intersection)
	protected int poi=400;
	protected Pokemon[] playerOnePokemonList, playerTwoPokemonList;
	protected Pokemon p1,p2;
	protected AI ai = new AI();
		
	protected Image arenaImage, backgroundImage;
	
	protected int ground = 400;
	protected BillyButton retreat,ok,cancel;
	protected ArrayList<BillyButton> choose = new ArrayList<BillyButton>();
	
	// constructor, loads images, and buttons
	public BattleFrame() {
		super();
		this.setVisible(false);
		MODE = BATTLE;
		ImageIcon arenaIcon = new ImageIcon("pictures/BattlePictures/arena.png");
		ImageIcon backgroundIcon = new ImageIcon("pictures/BattlePictures/sky.jpg");
		
		arenaImage = arenaIcon.getImage(); 
		backgroundImage = backgroundIcon.getImage();
		
		arenaImage = arenaImage.getScaledInstance(800,365,Image.SCALE_SMOOTH);
		backgroundImage = backgroundImage.getScaledInstance(800,600,Image.SCALE_SMOOTH);
		//retreatMenu = ret;
		//retreatMenu.setVisible(false);
		
		//retreat = new BillyButton("RETREAT");
		//retreat.setSize(100,50);
		//retreat.setLocation(650,500);
		
		ok = new BillyButton("OK");
		ok.setSize(100,50);
		ok.setLocation(500,500);	
		ok.setVisible(false);
		ok.setEnabled(false);	
		
		cancel = new BillyButton("CANCEL");
		cancel.setSize(100,50);
		cancel.setLocation(650,500);
		cancel.setVisible(false);
		cancel.setEnabled(false);
		poi = 400;
	}
	
	// checks to see if you used attacks or not and sees if you can actually use it
	// (have enough energy to use it)
	public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        if (keys[ONE] && p1.getEnergy()>=p1.attack(0).getCost()){
        	p1.useAttack(0);
        }else if (keys[TWO] && p1.getAttackNum()>1){
        	if (p1.getEnergy()>=p1.attack(1).getCost()){
        		p1.useAttack(1);
        	}
        }
    }
    // checks to see if you click on things like "retreat",
    // or if you're in retreat menu things like "ok" or "cancel"
	public void mousePressed(MouseEvent e){
    	mb = e.getButton();
    	// clicking retreat in battle
    	/*if (MODE == BATTLE){
	      	if (retreat.contains(mx,my)){
	    		mode = "retreat";
	    		cancel.setEnabled(true);
	    		cancel.setVisible(true);
	    		ok.setVisible(true);
	    		ok.setEnabled(true);
	    	}
    	}  	*/	
    	/*}else if (mode.equals("retreat")){
    		// doing cancel or ok for the next pokemon
    		// Pokemon comes into the battle stunned to make it even
    		if (cancel.contains(mx,my)){
    			mode = "battle";
	    		cancel.setEnabled(false);
	    		cancel.setVisible(false);
	    		ok.setVisible(false);
	    		ok.setEnabled(false);
    		}else if (ok.contains(mx,my)&&chosenNum==1){
	    		cancel.setEnabled(false);
	    		cancel.setVisible(false);
	    		ok.setVisible(false);
	    		ok.setEnabled(false);
	    		p1 = playerOnePokemonList[chosen];
	    		p1.getStunned();
    		}
    		
    		//choosing
    		int count =0;
    		for(Pokemon p : playerOnePokemonList){
    			if (p != p1){
	    			if (mx>=200 && mx <= 200+60 && my >= 50+count*100 && my <= 50+count*100+60){
	    				p1 = p;
	    				break;
	    			}
	    			count++;
    				
    			}
    		}*/
    	//}
	}    
		
	// setting the teams, and getting the first pokemon started
	// (.setPlayer() resets the pokemon, more explained in the Pokemon class)
	public void setPokemon(Pokemon[] play1, Pokemon[] play2){
		playerOnePokemonList = play1;
		playerTwoPokemonList = play2;
		p1 = playerOnePokemonList[count1];
		p1.setPlayer(1);
		
		p2 = playerTwoPokemonList[count2];
		p2.setPlayer(2);
	}
	
	// draws everything here based on the mode
	public void paint(Graphics g){
		if (introCount==0){
			// if you're in battle, you draw the background, the pokemon, their attacks, and the pokemon draw
			// the damage (more explained in the Damage and Pokemon classes).
			if (MODE == BATTLE) {
				g.drawImage(backgroundImage, 0, 0, null);
				g.drawImage(arenaImage, 0, 600-365, null);
				
				for (int i=0;i<p1.getAttackNum();i++){
					g.setFont(new Font("Arial",Font.PLAIN,10));
					g.setColor(Color.black);
					g.drawString((i+1)+") "+p1.attack(i).getName()+"----Cost: "+p1.attack(i).getCost()+"----Damage: "+p1.attack(i).getDmg()+"----Special: "+p1.attack(i).getSpec(),50,200+30*i);
				}	
							
				p1.draw(g);
				p2.draw(g);
				//retreat.draw(g);
				
			// in retreat, you draw the pokemon you can choose, with their 
			// respective health and energy bars
			} else if (MODE == WIN || MODE == LOSE){
				g.setColor(Color.black);
				g.fillRect(0,0,800,600);
				g.setFont(new Font("Arial",Font.PLAIN,60));
				g.setColor(Color.red);
				if (MODE == WIN){
					g.drawString("WIN",100,100);
				}else{
					g.drawString("LOSE",100,100);
				}
			}
			/*}else{
				g.setColor(Color.black);
				g.fillRect(0,0,800,600);
				ok.draw(g);
				cancel.draw(g);
				int count=0;
				
				for (Pokemon p : playerOnePokemonList){
					if (p!=p1){
						p.draw(g,200,50+count*100);
						count++;
						
					}
				}
			}*/
		
		// intro to battle, the classic black swirl before battle begins (in the pokemon games)
		}else{
			g.drawImage(backgroundImage,0,0,null);
			g.drawImage(arenaImage,0,600-365,null);	
			
			// draws lines every 2 degrees of a circle with the verticies of the screen on the circumference
			// then fills the area in between the lines to give a solid look.
			g.setColor(Color.black);
			for (int i=0;i<introCount;i++){
				int[] x = {400,(int)(500*Math.cos(Math.toRadians(i)))+400,(int)(500*Math.cos(Math.toRadians(i+2)))+400};
				int[] y = {300,(int)(500*Math.sin(Math.toRadians(i)))+300,(int)(500*Math.sin(Math.toRadians(i+2)))+300};
				g.drawLine(400,300,(int)(500*Math.cos(Math.toRadians(i)))+400,(int)(500*Math.sin(Math.toRadians(i)))+300);
				g.fillPolygon(x,y,3);
			}
			introCount-=2;
			
		}

		
	}
	
	// updates pokemon and such here
	// moves pokemon, checks if they died or not, if they hit eachother, restores hp and energy every round
  	public void update(){
  		// if you're in battle, you move the pokemon and check to see if they hit
  		// and if they died.
  		if (introCount == 0 && MODE == BATTLE){
  			poi = 100+(int)(600*(p1.getPercent()/(p1.getPercent()+p2.getPercent())));  
	  		p1.move(poi);
	  		p2.move(poi);
	  		p1.hit(p2);
	  		p2.hit(p1);
	  		// changes the poi based on the respective percentages
	  		poi = 100+(int)(600*(p1.getPercent()/(p1.getPercent()+p2.getPercent())));  		
	  		if (!p1.active()){
	  			count1++;
	
	  			if (count1==6){
	  				MODE = LOSE;				
	  			}else{
	  			//fix choosing
	  				for (Pokemon p : playerOnePokemonList){
	  					if (p.active()){
	  						p.addEnergy(10);
	  						p.addHP(20);
	  					}
	  				}
	  				/*for (Pokemon p : playerOnePokemonList){
	  					if (p.active() && p1!=p){
	  						p1 = p;
	  						break;
	  					}
	  				}*/
		  			p1 = playerOnePokemonList[count1];
		  			p1.setPlayer(1);
		  			p2.setPlayer(2);
		  			poi = 100+(int)(600*(p1.getPercent()/(p1.getPercent()+p2.getPercent())));  	
	  			}
	  		}
	  		if (!p2.active()){
	  			count2++;			
	  				//lose
	  			if (count2==21){
	  				MODE = WIN;
	  			}else{
	  				for (Pokemon p : playerOnePokemonList){
	  					if (p.active()){
	  						p.addEnergy(10);
	  						p.addHP(20);
	  					}
	  				}	  				
	  				/*for (Pokemon p : playerTwoPokemonList){
	  					if (p.active() && p2 != p){
	  						p2 = p;
	  						break;
	  					}
	  				}	*/
	  				p2 = playerTwoPokemonList[count2];		
		  			p1.setPlayer(1);
		  			p2.setPlayer(2);
		  			poi = 100+(int)(600*(p1.getPercent()/(p1.getPercent()+p2.getPercent())));  	
	  			}
	  		}
  		}  			
  		//}else if (mode.equals("retreat")){
  			//for (BillyButton b : choose){
  				
  			//}
  		//}
  		//AI.doStuff();
  		
		Graphics g = getGraphics();
		if(dbImage == null){
			dbImage = createImage(getWidth(), getHeight());
			dbg = dbImage.getGraphics();
		}
		paint(dbg);
		g.drawImage(dbImage,0,0,null);
  	}
}