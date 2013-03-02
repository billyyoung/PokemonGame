import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.sound.sampled.*;
import java.util.*;
import java.io.*;

// Menu frame where user gets to pick their pokemon to go to battle
public class SelectFrame extends BaseFrame{
	protected static final int INTRO = 0, INTRO2 = 1, MENU = 2;
	// loading music, variables, pictures, etc.
	protected File introMusic = new File("music/Intro.wav");
	protected File centre = new File("music/PokemonCenter.wav");
	//protected File[] music = {new File("cycling.wav"),new File("Golden Rod City Theme.wav"),new File("nugget bridge.wav"),new File("Pokemon Center.wav"),new File("road to viridian.wav"),new File("routes 11-15.wav"),new File("SS Anne theme.wav"),new File("surf.wav")};
	protected File battleMusic = new File("music/Battle.wav"), battleMusicLoop = new File("music/BattleLoop.wav");
	//protected boolean[] played = new boolean[music.length];
	protected boolean stopPlaying=false;
	protected int playCount=0, count=0;
	protected AudioFormat audioFormat;
	protected AudioInputStream audioInputStream;
	protected SourceDataLine sourceDataLine;	
	protected BillyButton[] pokButtons = new BillyButton[27];
	protected BillyButton doneButton;
	protected ArrayList<Pokemon> chosenPokemon = new ArrayList<Pokemon>();
	protected Pokemon[] poks = new Pokemon[27];
	protected AI a = new AI();
	protected int MODE;
	
	protected ImageIcon centerIcon = new ImageIcon("pictures/SelectPictures/pokemoncenter.png"),introIcon = new ImageIcon("pictures/SelectPictures/PokemonStadium.jpg");
	protected Image centerImage = centerIcon.getImage(), introImage = introIcon.getImage();
	
	protected ImageIcon warningIcon = new ImageIcon("pictures/SelectPictures/warning.bmp");
	protected Image warningImage = warningIcon.getImage();//.getScaledInstance(800,600,Image.SCALE_SMOOTH);
	
	// constructor takes in PokBattle batt so that it can hide it and show it when PokMenu is done
	public SelectFrame() throws IOException{
		super ();
		MODE = INTRO;
		setLayout(null);
		String[] pnames = new String[27];
		
		// creates the pokemon based on the info from the CSV text file
		BufferedReader info = new BufferedReader(new FileReader("pokemonCSV.txt"));
		for (int i=0; i<27; i++){
			poks[i] = new Pokemon(info.readLine());
			pnames[i] = poks[i].getName();
		}
		info.close();
		
		ImageIcon pic;
		Image img;
		// Makes the buttons and adds them to an array (rows of 7
		for (int i=0;i<3;i++){//rows
			for (int j=0;j<7;j++){
				pic = new ImageIcon("pictures/SelectPictures/"+pnames[count]+".gif");
				img = pic.getImage();
				img = img.getScaledInstance(60,60,Image.SCALE_SMOOTH);
				pokButtons[count] = new BillyButton(new ImageIcon(img),pnames[count]);
				pokButtons[count].setSize(60,60);
				pokButtons[count].setLocation(30+j*(img.getWidth(null)+30),200-30+i*(img.getHeight(null)+20));
				count++;
			}
		}
		// row of 6 here
		for (int i=0;i<6;i++){
			pic = new ImageIcon("pictures/SelectPictures"+pnames[count]+".gif");
			img = pic.getImage();
			img = img.getScaledInstance(60,60,Image.SCALE_SMOOTH);
			pokButtons[count] = new BillyButton(new ImageIcon(img),pnames[count]);
			pokButtons[count].setSize(60,60);
			pokButtons[count].setLocation(30+40+i*(img.getWidth(null)+30),200-30+3*(img.getHeight(null)+20));
			count++;
		}
		
		// makes a "continue" button that only shows itself if you've picked 6 pokemon
		doneButton = new BillyButton("CONTINUE");
		doneButton.setSize(100,50);
		doneButton.setLocation(650,500);
		doneButton.setEnabled(false);
		
		// scales the images
		centerImage = centerImage.getScaledInstance(800,600,Image.SCALE_SMOOTH);
		introImage = introImage.getScaledInstance(800,600,Image.SCALE_SMOOTH);
	}
	
	// to be called from outside the SelectFrame object (managed by the state manager in RunThis)
	public void setPokemonForBattle(BattleFrame battleFrame) {
		battleFrame.setPokemon(chosenPokemon.toArray(new Pokemon[6]), poks);
	}
	
	// checks for mouse pressed events
	// such as selecting/deselecting, continuing to battle phase
	public void mousePressed(MouseEvent e) {
    	mb = e.getButton();
    	// selecting/deselecting pokemon
    	if (MODE == MENU){
 			for (int i=0;i<27;i++){			
				// if it hasn't been selected yet and you stil have room, take it
				if (pokButtons[i].isEnabled()){
					if(chosenPokemon.size() < 6 && pokButtons[i].contains(mx,my)){
						pokButtons[i].setEnabled(false);
						chosenPokemon.add(poks[i]);
					}
				}
	
				// if its selected and you want to deselect it, do it
				else if (!pokButtons[i].isEnabled()){
					if (chosenPokemon.size() > 0 && pokButtons[i].contains(mx,my)){
						pokButtons[i].setEnabled(true);
						chosenPokemon.remove(poks[i]);
					}
						// remove guy
				}
			}
			
			// checks the done button and initiates PokBattle
			if (doneButton.contains(mx,my) && doneButton.isEnabled()){
				setVisible(false);
				stopPlaying = true;	
				nextStatePrompt = true;
			}   		
    	}
	}
	
	// checks about keypressed events (if you pressed a key)
	// checks to see if you go past the intro screens
	public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        // if you pressed space/enter during the intro, move on to intro2
        if ((keys[SPACE]||keys[ENTER]) && MODE == INTRO){
        	MODE = INTRO2;
        }
        // if you pressed space/enter during intro2, move on to selecting
        else if ((keys[SPACE]||keys[ENTER]) && MODE == INTRO2){
        	playCount++;
        	stopPlaying = true;
        	MODE = MENU;        	
        }
    }
    
    // updates the screen here with our double-buffer,
    // enables/disables the done button based on pokemon chosen

  	public void update(){
  		if (chosenPokemon.size() == 6){
  			doneButton.setEnabled(true);
  		}else{
  			doneButton.setEnabled(false);
  		}
  		// 

		Graphics g = getGraphics();
		if(dbImage == null){
			dbImage = createImage(getWidth(), getHeight());
			dbg = dbImage.getGraphics();
		}
		
		paint(dbg);
		g.drawImage(dbImage,0,0,null);
 
  	}
  
   // draws stuff here, based on the status of the frame
   // intro has its own pic, intro2 has its own pic, draws all buttons in menu
   public void paint(Graphics g){
		if (MODE == INTRO){
			g.drawImage(introImage,0,0,null);
		}else if (MODE == INTRO2){
			g.drawImage(warningImage,0,0,null);
		}else{
			g.drawImage(centerImage,0,0,null);
	  		for (int i=0;i<27;i++){
	  			pokButtons[i].draw(g);
	  		}
	  		doneButton.draw(g);    
	  		// if you mouse over a pokemon it displays the stats for you	
	  		g.setFont(new Font("Arial",Font.PLAIN,15));	
	  		for (int i=0;i<27;i++){
	  			if (pokButtons[i].contains(mx,my)){
	  				
					g.drawString(poks[i].getName(),150,500);
					g.drawString("HP: "+poks[i].getHP(),250,500);
					g.drawString("Energy: "+poks[i].getEnergy(),320,500);
					g.drawString("Type: "+poks[i].getType(),150,515);
					g.drawString("Resistance: "+poks[i].getRes(),250,515);
					g.drawString("Weakness: "+poks[i].getWeak(),380,515);
					for (int k=0;k<poks[i].getAttackNum();k++){
						g.drawString(poks[i].attack(k).getName()+" EN: "+poks[i].attack(k).getCost()+" DMG: "+poks[i].attack(k).getDmg()+" Special: "+poks[i].attack(k).getSpec(),150,530+15*k);
					}				
	  			}
  			}	
		}  		
  	}
   
	// the playAudio method
	protected void playAudio(){
	    try{
	    	// plays different songs based on the playCount (used for the different screens)
	    	// intro for the intros, pokemon center for picking
	    	File soundFile;
	    	if (isVisible()==false && playCount==2){
	    		soundFile = battleMusic;
	    	}else if (isVisible()==false && playCount!=2){
	    		soundFile = battleMusicLoop;
	    	}else if (isVisible()&&playCount==1){
	    		soundFile = centre;
	    	}else{
	    		soundFile = introMusic;
	    	}
	      	
	      	// gets the audio information
	      	audioInputStream = AudioSystem.getAudioInputStream(soundFile);
	      	audioFormat = audioInputStream.getFormat();

	      	DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audioFormat);
	
	      	sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);
			// play using the PlayThread class	
	      	new PlayThread().start();
	    }catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	// PlayThread class handles all the junk
	protected class PlayThread extends Thread{
		byte [] tempBuffer= new byte[10000];
		public void run(){
	    	try{
		    	sourceDataLine.open(audioFormat);
		    	sourceDataLine.start();
		
		    	int cnt=0;
		    	//Keep looping until the input read method
		    	// returns -1 for empty stream or the
		    	// user clicks the Stop button causing
		    	// stopPlayback to switch from false to
		    	// true.
		    	while((cnt = audioInputStream.read(tempBuffer,0,tempBuffer.length)) != -1 && stopPlaying==false){//playCount == 0)){//so you can only skip songs if its song 0
		      		if(cnt > 0){
		          // Write data to the internal buffer of
		          // the data line where it will be
		          // delivered to the speaker.
		          		sourceDataLine.write(tempBuffer, 0, cnt);
		        	}
		      	}
		      	// Block and wait for internal buffer of the
		      	// data line to empty.
		      	sourceDataLine.drain();
		      	sourceDataLine.close();
		      	stopPlaying=false;
		      	if (isVisible()==false){
		      		playCount++;
		      	}
				playAudio();
		    }catch (Exception e) {
		      	e.printStackTrace();
		      	System.exit(0);
	    	}
	  	}
	}	
}

