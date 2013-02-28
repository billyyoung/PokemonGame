import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.sound.sampled.*;
import java.util.*;
import java.io.*;


// JButtons wouldn't allow for drawing and JButtons at the same time and JLayeredPane doesn't work
// well so I decided to make my own buttons, very similar to JButtons
public class BillyButton{
	protected ImageIcon img,BWimg;
	protected Image image,BWimage;
	protected boolean enabled,visible;
	protected int x,y,width,height;
	protected String message,mode,filename;
	
	// method overload the constructor so you can have ImageIcon buttons, Image buttons, or String
	// buttons (like JButton);
	public BillyButton(ImageIcon img,String filename){
		// set up images with the black one for when its selected
		this.img = img;
		enabled = true;
		image = img.getImage();
		this.filename = filename;
		BWimg = new ImageIcon("Real Pok Pics\\"+filename+"Chosen.gif");
		BWimage = BWimg.getImage();		
		mode = "ImageIcon";
		visible = true;
	}
	protected BillyButton(Image image,String fileName){
		// set up images with the black one for when its selected
		this.image = image;
		enabled = true;
		mode = "Image";
		this.filename = filename;
		img = new ImageIcon(image);
		BWimg = new ImageIcon("Real Pok Pics\\"+filename+"Chosen.gif");
		BWimage = BWimg.getImage();
		this.filename = filename;
		visible = true;		
	}
	protected BillyButton(String message){
		// makes button, not well done, but gets the job done
		this.message = message;
		enabled = true;
		mode = "String";
		width = message.length()*10+40;
		height = 50;
		visible = true;		
	}
	
	// set size of the button (inherited from JButton =) )
	// changes the size of the button, and resizes the image to fit it.
	public void setSize(int x, int y){
		if (!mode.equals("String")){
			width = x;
			height = x;
			image = image.getScaledInstance(width,height,Image.SCALE_SMOOTH);
			BWimage = BWimage.getScaledInstance(width,height,Image.SCALE_SMOOTH);			
		}
	}
	
	// sets location of the button (like JButton) at x,y
	public void setLocation(int x, int y){
		this.x = x;
		this.y=y;
	}
	
	// draws the button, based on the type of button (ImageIcon, Image, or String)
	public void draw(Graphics g){
		if (visible){
			// if its image/imageicon, draw it
			g.setColor(new Color(50,200,50));
			g.fillRect(x-1,y-1,width+2,height+2);
			if (mode.equals("Image")||mode.equals("ImageIcon")){
				if (enabled){
					g.drawImage(image,x,y,null);
				}else{
					g.drawImage(BWimage,x,y,null);
				}
			// if its string, draw the string
			}else{
				
				g.setFont(new Font("Arial",Font.PLAIN,20));
				if (enabled){
					g.setColor(Color.black);
					g.drawString(message,x+20,y+20);
				}else{
					g.setColor(new Color(255,255,255));
					g.fillRect(x-1,y-1,width+2,height+2);
					g.setColor(Color.black);
					g.drawString(message,x+20,y+20);
				}
			}
		}
		
	}
	
	// BELOW ARE THE PRETTY MUCH BASIC METHODS NEEDED 
	
	// sets the button to be enabled or not based on the parameter boolean e
	public void setEnabled(boolean e){
		enabled = e;
	}
	
	//gets the height of the button
	public int getHeight(){
		return height;
	}
	
	//gets the width of the button	
	public int getWidth(){
		return width;
	}
	
	// checks to see if button is enabled or not
	public boolean isEnabled(){
		return enabled;
	}
	
	//gets the "x" of the button (based on top right corner)
	public int getX(){
		return x;
	}
	
	//gets the "y" of the button (based on top right corner)	
	public int getY(){
		return y;
	}
	
	// sets whether or not we can see the button
	public void setVisible(boolean e){
		visible = e;
	}
	
	// checks if the a point is inside the button (mostly used for mouse click checks;
	public boolean contains(int mx,int my){
		return (mx >= x && mx <= x+width && my >= y && my <= y+height);
	}
}