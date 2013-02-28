import java.util.*;
public class test{
	public static void main(String[] args){
		boolean[] asdf = new boolean[3];
		Arrays.fill(asdf,false);
		boolean[] qwerty = new boolean[3];
		qwerty[0]=false;
		qwerty[1]=false;
		qwerty[2] = false;
		if (Arrays.equals(asdf,qwerty))
		System.out.println(Arrays.equals(asdf,qwerty));
	}
}