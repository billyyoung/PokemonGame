import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.sound.sampled.*;
import java.util.*;
import java.io.*;


// Damage class, used to configure the damage that "flys" off 
// when your pokemon hit together
public class Damage{
	// fields x,y for position, 
	// String dmg for message (origianlly it was for number damage only but it became for 
	// effects such as "STUN" as well)
	// counter for how long it remains on the screen
	protected int x,y,counter;
	protected String dmg;
	public Damage(int x,int y,String dmg){
		this.x = x;
		this.y = y;
		this.dmg=dmg;
		counter = 0;
	}
	
	// gets the "x" of the message
	public int getX(){
		return x;
	}
	//gets the "y" of the message
	public int getY(){
		return y;
	}
	// gets the message of the object
	public String getDmg(){
		return dmg;
	}
	
	// gets the current counter
	public int getCount(){
		return counter;
	}
	
	// moves the message up
	public void move(){
		if (dmg.length()<=2){
			if (y>250){
				y-=15;
			}
			if (y<=250 && counter < 20){
				counter++;
			}			
		}else{
			if (counter<20){
				counter++;
			}
		}

	}
	
	// draws the message onto the screen
	public void draw(Graphics g){
		// if its something big like skill dmg (10+ dmg) or a status declaration
		// such as "A was stunned", then we use the big font and make it red.
		if (dmg.length()>1){
			g.setFont(new Font("Arial",Font.PLAIN,40));
			g.setColor(Color.red);
		// otherwise use normal black font for the normal 1,2,4 dmg
		}else{
			g.setFont(new Font("Arial",Font.PLAIN,25));
			g.setColor(Color.black);
		}
		
		g.drawString(dmg,x,y);
	}
}