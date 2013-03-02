import java.io.*;
import java.util.*;

// main, where the frames are run
public class RunThis{
	protected static final int SELECT=0, BATTLE=1;

	public static void delay (long len) {
		try{
			Thread.sleep (len);
		}
		catch (InterruptedException ex) {
			System.out.println(ex);
		}
	}
	
	public static void main(String[] args) throws IOException {
		int STATE = SELECT;
		SelectFrame selectFrame = new SelectFrame();
		BattleFrame battleFrame = new BattleFrame();

		selectFrame.setTitle("CHOOSE YOUR POK�MON");
		battleFrame.setTitle("BATTLE YOUR POK�MON");
		
		selectFrame.playAudio();
		
		while (true){
			if (STATE == SELECT){
				selectFrame.update();
				if (selectFrame.getNextStatePrompt()) {
					selectFrame.setPokemonForBattle(battleFrame);
					STATE = BATTLE;
				}
			} else if (STATE == BATTLE) {
				battleFrame.update();
			}
			delay(10);
		}
	}
}
