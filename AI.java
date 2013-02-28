//make retreat stun your guy
import java.util.*;
import java.io.*;

public class AI{
	private ArrayList<Pokemon> comp;
	public AI(){
		
	}
	public Pokemon[] pick(ArrayList<Pokemon> chosen,Pokemon[] poks,int num){
		comp = new ArrayList<Pokemon>();
		Pokemon[] x = new Pokemon[num];
		int count=0;
		while (count<num){
			int k = (int)(Math.random()*26);
			if (!chosen.contains(poks[k])&&!comp.contains(poks[k])){
				comp.add(poks[k]);
				count++;
			}
			
		}
		return comp.toArray(x);
	}
	public void doStuff(){
		
	}
	
}